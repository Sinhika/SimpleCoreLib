package mod.alexndr.simplecorelib.api.content;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import mod.alexndr.simplecorelib.api.helpers.SidedWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.capabilities.Capabilities;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.util.LazyOptional;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * Abstracts the mechanics of a conventional-ish furnace (1 input, 1 fuel, 1 output slot).
 * Not useable for more exotic furnaces, such as the Fusion furnace.
 * 
 * @author Sinhika
 *
 */
public abstract class VeryAbstractFurnaceTileEntity extends BlockEntity
{
    protected static final Logger LOGGER = LogManager.getLogger();
    
    public static final int INPUT_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;
    
    protected static final String INVENTORY_TAG = "inventory";
    protected static final String SMELT_TIME_LEFT_TAG = "smeltTimeProgress";
    protected static final String MAX_SMELT_TIME_TAG = "maxSmeltTime";
    protected static final String FUEL_BURN_TIME_LEFT_TAG = "fuelBurnTimeLeft";
    protected static final String MAX_FUEL_BURN_TIME_TAG = "maxFuelBurnTime";

    public static final int DATA_FUEL_TIME_LEFT = 0;
    public static final int DATA_FUEL_TIME_MAX = 1;
    public static final int DATA_COOKING_PROGRESS = 2;
    public static final int DATA_COOKING_TOTAL_TIME = 3;
    public static final int NUM_DATA_VALUES = 4;

    public static final int BURN_TIME_STANDARD = 200;
    
    protected final RecipeType<? extends AbstractCookingRecipe> recipeType;
    protected final Map<ResourceLocation, Integer> recipe2xp_map = Maps.newHashMap();
    
    // implement recipe-caching like all the cool kids.
    protected AbstractCookingRecipe cachedRecipe;
    protected ItemStack failedMatch = ItemStack.EMPTY;
    
    protected double fuelMultiplier = 1.0;
    protected boolean hasFuelMultiplier = false;
    
    public int smeltTimeProgress = -1;
    public int maxSmeltTime = -1;
    public int fuelBurnTimeLeft = 0;
    public int maxFuelBurnTime = 0;
    protected boolean lastBurning = false;

    public final ContainerData dataAccess = new ContainerData() 
    {
        @Override
        public int get(int index) {
            switch (index)
            {
            case DATA_FUEL_TIME_LEFT:
                return VeryAbstractFurnaceTileEntity.this.fuelBurnTimeLeft;
            case DATA_FUEL_TIME_MAX:
                return VeryAbstractFurnaceTileEntity.this.maxFuelBurnTime;
            case DATA_COOKING_PROGRESS:
                return VeryAbstractFurnaceTileEntity.this.smeltTimeProgress;
            case DATA_COOKING_TOTAL_TIME:
                return VeryAbstractFurnaceTileEntity.this.maxSmeltTime;
            default:
                return 0;
            }
        } // end get()

        @Override
        public void set(int index, int value) {
            switch (index)
            {
            case DATA_FUEL_TIME_LEFT:
                VeryAbstractFurnaceTileEntity.this.fuelBurnTimeLeft = value;
                break;
            case DATA_FUEL_TIME_MAX:
                VeryAbstractFurnaceTileEntity.this.maxFuelBurnTime = value;
                break;
            case DATA_COOKING_PROGRESS:
                VeryAbstractFurnaceTileEntity.this.smeltTimeProgress = value;
                break;
            case DATA_COOKING_TOTAL_TIME:
                VeryAbstractFurnaceTileEntity.this.maxSmeltTime = value;
            }
        } // end set()

        @Override
        public int getCount() {
            return NUM_DATA_VALUES;
        }
    }; // end VeryAbstractFurnaceTileEntity$ContainerData
    

    public final ItemStackHandler inventory = new ItemStackHandler(3) 
    {
            @Override
            protected void onContentsChanged(final int slot) {
                setChanged();
            } // end ()
            
           @Override
            public boolean isItemValid(final int slot, @Nonnull final ItemStack stack) 
            {
                switch (slot) {
                    case FUEL_SLOT:
                        return isFuel(stack);
                    case INPUT_SLOT:
                        return isInput(stack);
                    case OUTPUT_SLOT:
                        return isOutput(stack);
                    default:
                        return false;
                }
            } // end ItemStackHander(3).isItemValid()
    }; // end ItemStackHandler(3)

    public class FurnaceHandler extends SidedWrapper
    {
        public FurnaceHandler(IItemHandlerModifiable inventory, Direction side)
        {
            super(inventory, side);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) 
        {
            // no, you can't put stuff in the output slot.
            if (slot == OUTPUT_SLOT) 
            {
                return stack;
            }
            // that leaves fuel && input slot, which are covered by isItemValid().
            if (slot == INPUT_SLOT && this.side == Direction.UP) 
            {
                return super.insertItem(slot, stack, simulate);
            }
            if (slot == FUEL_SLOT && isHorizontalSide(this.side)) {
                return super.insertItem(slot, stack, simulate);
            }
            return stack;
          
        } // end insertItem()

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            // you can extract buckets from the fuel slot.
            if (slot == FUEL_SLOT && this.side == Direction.DOWN)
            {
                ItemStack pstack = this.getStackInSlot(FUEL_SLOT);
                if (pstack.is(Items.BUCKET) || pstack.is(Items.WATER_BUCKET))
                {
                    return super.extractItem(slot, amount, simulate);
                }
                else {
                    return ItemStack.EMPTY;
                }
            }
            // you can extract anything from the output slot.
            if (slot == OUTPUT_SLOT && this.side == Direction.DOWN) {
                return super.extractItem(slot, amount, simulate);
            }
            
            return ItemStack.EMPTY;
        } // end extractItem()
    } // end FurnaceHandler
    
	// CAPABILITY STUFF
	protected LazyOptional<ItemStackHandler> inventoryCapabilityInternal = LazyOptional.of(() -> this.inventory);
    protected LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalUp = LazyOptional.of(() -> new FurnaceHandler(this.inventory, Direction.UP));
    protected LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalSides = LazyOptional.of(() -> new FurnaceHandler(this.inventory, Direction.EAST));
    protected LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalDown = LazyOptional.of(() -> new FurnaceHandler(this.inventory, Direction.DOWN));
	

    /**
     * Retrieves the Optional handler for the capability requested on the specific side.
     *
     * @param cap  The capability to check
     * @param side The Direction to check from. CAN BE NULL! Null is defined to represent 'internal' or 'self'
     * @return The requested an optional holding the requested capability.
     */
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side)
    {
        if (cap == Capabilities.ITEM_HANDLER && side == null)
        {
            return inventoryCapabilityInternal.cast();
        }
        if (cap == Capabilities.ITEM_HANDLER && side != null) 
        {
            switch (side) {
                case DOWN:
                    return inventoryCapabilityExternalDown.cast();
                case UP:
                    return inventoryCapabilityExternalUp.cast();
                case NORTH:
                case SOUTH:
                case WEST:
                case EAST:
                    return inventoryCapabilityExternalSides.cast();
            }
        }
        return super.getCapability(cap, side);
    } // end getCapability
    
    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();
        
        // We need to invalidate our capability references so that any cached references (by other mods) don't
        // continue to reference our capabilities and try to use them and/or prevent them from being garbage collected
        
        inventoryCapabilityExternalUp.invalidate();
        inventoryCapabilityExternalDown.invalidate();
        inventoryCapabilityExternalSides.invalidate();
        inventoryCapabilityInternal.invalidate();
    }

    @Override
    public void reviveCaps() 
    {
       super.reviveCaps();
       inventoryCapabilityInternal = LazyOptional.of(() -> this.inventory);
       inventoryCapabilityExternalUp = LazyOptional.of(() -> new FurnaceHandler(this.inventory, Direction.UP));
       inventoryCapabilityExternalSides = LazyOptional.of(() -> new FurnaceHandler(this.inventory, Direction.EAST));
       inventoryCapabilityExternalDown = LazyOptional.of(() -> new FurnaceHandler(this.inventory, Direction.DOWN));
    }


	public VeryAbstractFurnaceTileEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos blockpos, BlockState blockstate,
    		RecipeType<? extends AbstractCookingRecipe> recipeTypeIn)
    {
        super(tileEntityTypeIn, blockpos, blockstate);
        this.fuelMultiplier = 1.0;
        this.hasFuelMultiplier = false;
        this.recipeType = recipeTypeIn;
    }

   public boolean isFuel(ItemStack stack)
    {
        return FurnaceBlockEntity.isFuel(stack) || stack.is(Items.BUCKET);
    }

   
    /**
     * @return If the stack is not empty and has a smelting recipe associated with it
     */
    protected boolean isInput(final ItemStack stack)
    {
        if (stack.isEmpty())
            return false;
        return getRecipe(stack).isPresent();
    }

    /**
     * @return If the stack's item is equal to the result of smelting our input
     */
    protected boolean isOutput(final ItemStack stack)
    {
        final Optional<ItemStack> result = getResult(inventory.getStackInSlot(INPUT_SLOT));
        return result.isPresent() && ItemStack.isSameItem(result.get(), stack);
    }
    
    public boolean isBurning()
    {
        return this.fuelBurnTimeLeft > 0;
    }

    
    /**
     * @return The smelting recipe for the inventory; implements recipe caching.
     */
    @SuppressWarnings("unchecked")
    protected Optional<AbstractCookingRecipe> getRecipe(final Container inv0)
    {
        if (cachedRecipe != null && cachedRecipe.matches(inv0, level))
        {
            return Optional.of(cachedRecipe);
        }
        else
        {
            AbstractCookingRecipe rec 
                = level.getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>) recipeType, inv0, level).orElse(null);
            if (rec == null) {
                failedMatch = inv0.getItem(0); // i.e., input.
            }
            else {
                failedMatch = ItemStack.EMPTY;
            }
            cachedRecipe = rec;
            return Optional.ofNullable(rec);
        } // end else
    } // end getRecipe()

    /**
     * @return The smelting recipe for the input stack
     */
    protected Optional<AbstractCookingRecipe> getRecipe(final ItemStack input)
    {
        if (input.isEmpty() || input == failedMatch) {
            return Optional.empty();
        }
        // Due to vanilla's code we need to pass an IInventory into RecipeManager#getRecipe so we make one here.
        return getRecipe(new SimpleContainer(input));
    }

    public void setRecipeUsed(@Nullable Recipe<?> recipe)
    {
        if (recipe != null)
        {
            this.recipe2xp_map.compute(recipe.getId(), (p_214004_0_, p_214004_1_) -> {
                return 1 + (p_214004_1_ == null ? 0 : p_214004_1_);
            });
        }
    } // end setRecipeUsed()

    /** 
     * Given an input stack, determine what the output of smelting it would be.
     * @param input
     * @return
     */
    protected Optional<ItemStack> getResult(final ItemStack input)
    {
        // Due to vanilla's code we need to pass an IInventory into
        // RecipeManager#getRecipe and
        // AbstractCookingRecipe#getCraftingResult() so we make one here.
        final SimpleContainer dummyInventory = new SimpleContainer(input);
        Optional<ItemStack> maybe_result = getRecipe(dummyInventory).map(
        		recipe -> recipe.assemble(dummyInventory, level.registryAccess()));

        return Optional.of(maybe_result.orElse(ItemStack.EMPTY));
    }
    
    /**
     * Given a stack of fuel, gets the burn duration of one item.
     * @param fuelstack - fuel items to be checked.
     * @return burn duration in ticks.
     */
    protected int getBurnDuration(ItemStack fuelstack) 
    {
        int returnval = 0;
        
        // ForgeHooks.getBurnTime() handles empty stack case, so we don't have to.
        if (!hasFuelMultiplier)
        {
            returnval = CommonHooks.getBurnTime(fuelstack, recipeType);
        }
        else {
            // improved fuel efficiency processing here.
            returnval = (int) Math.ceil(((double) CommonHooks.getBurnTime(fuelstack, recipeType)) * fuelMultiplier);
        }
        // LOGGER.debug("[" + getDisplayName().getString() + "]VeryAbstractFurnaceTileEntity.getBurnDuration: returns " + returnval + " for " + fuelstack.toString());
        return returnval;
    } // end getBurnDuration
    
    /**
     * Is it physically possible to put the smelting result in the output slot?
     * @param result - hypothetical smelting product.
     * @return true if result can be added to output slot, false if it cannot.
     */
    protected boolean canSmelt(ItemStack result)
    {
        if (!this.inventory.getStackInSlot(INPUT_SLOT).isEmpty() && !result.isEmpty())
        {
            ItemStack outstack = inventory.getStackInSlot(OUTPUT_SLOT);
            if (outstack.isEmpty())
            {
                return true;
            }
            else if (!outstack.is(result.getItem()))
            {
                return false;
            }
            else
            { // Forge fix: make furnace respect stack sizes in furnace recipes
                return (outstack.getCount() + result.getCount() <= outstack.getMaxStackSize());
            }
 
        } // end-if not output empty and not result empty.
        else
        {
            return false;
        }
    } // end canSmelt()
    
    /**
     * Mimics the code in {@link AbstractFurnaceTileEntity#getTotalCookTime()}
     *
     * @return The custom smelt time or 200 if there is no recipe for the input
     */
    protected short getSmeltTime(final ItemStack input)
    {
        return getRecipe(input)
                .map(AbstractCookingRecipe::getCookingTime)
                .orElse(BURN_TIME_STANDARD)
                .shortValue();
    }

    protected void smelt(ItemStack result)  // result = itemstack1
    {
        if (!result.isEmpty() && this.canSmelt(result))
        {
            ItemStack inputStack = inventory.getStackInSlot(INPUT_SLOT).copy();  // itemstack
            ItemStack outstack = inventory.getStackInSlot(OUTPUT_SLOT).copy(); // itemstack2
            if (outstack.isEmpty())
            {
                inventory.setStackInSlot(OUTPUT_SLOT, result.copy());
            }
            else if (outstack.getItem() == result.getItem())
            {
                outstack.grow(result.getCount());
                inventory.setStackInSlot(OUTPUT_SLOT, outstack);
            }
            if (!this.level.isClientSide) 
            {
                this.setRecipeUsed(getRecipe(inputStack).orElse(null));
            }
            final ItemStack fuelStack = inventory.getStackInSlot(FUEL_SLOT).copy();
            if (inputStack.getItem() == Blocks.WET_SPONGE.asItem() && !fuelStack.isEmpty() && fuelStack.getItem() == Items.BUCKET)
            {
                inventory.setStackInSlot(FUEL_SLOT, new ItemStack(Items.WATER_BUCKET));
            }
            inputStack.shrink(1);
            inventory.setStackInSlot(INPUT_SLOT, inputStack);
        } // end-if canSmelt result
    } // end burn()
    
    
    public static void serverTick(Level level, BlockPos blockpos, BlockState blockstate, VeryAbstractFurnaceTileEntity tile)
    {
        boolean hasFuel = tile.isBurning();
        boolean flag1 = false;
        if (tile.isBurning())
        {
            --tile.fuelBurnTimeLeft;
        }
        if ( blockstate.getValue(VeryAbstractFurnaceBlock.LIT) != tile.isBurning() )
        {
            blockstate = blockstate.setValue(VeryAbstractFurnaceBlock.LIT, Boolean.valueOf(tile.isBurning()));
            level.setBlock(blockpos, blockstate, Block.UPDATE_ALL);
        }
        ItemStack input = tile.inventory.getStackInSlot(INPUT_SLOT).copy();
        ItemStack fuel = tile.inventory.getStackInSlot(FUEL_SLOT).copy();
        final ItemStack result = tile.getResult(input).orElse(ItemStack.EMPTY);
            
        if ((tile.isBurning() || !fuel.isEmpty()) && !input.isEmpty() )
        {
            if (!tile.isBurning() && tile.canSmelt(result))
            {
                tile.fuelBurnTimeLeft = tile.getBurnDuration(fuel);
                tile.maxFuelBurnTime = tile.fuelBurnTimeLeft;
                if (tile.isBurning())
                {
                    flag1 = true;
                    if (fuel.hasCraftingRemainingItem()) 
                    {
                        tile.inventory.setStackInSlot(FUEL_SLOT, fuel.getCraftingRemainingItem());
                    }
                    else if (!fuel.isEmpty()) 
                    {
                        fuel.shrink(1);
                        tile.inventory.setStackInSlot(FUEL_SLOT, fuel); // Update the data
                    }
                } // end-if isBurning
            } // end-if !isBurning but canSmelt
            
            if (tile.isBurning() && tile.canSmelt(result))
            {
                if (tile.smeltTimeProgress <= 0) // never smelted before
                {
                    tile.maxSmeltTime = tile.getSmeltTime(tile.inventory.getStackInSlot(INPUT_SLOT));
                    tile.smeltTimeProgress = 0;
                }
                ++tile.smeltTimeProgress;
                if (tile.smeltTimeProgress >= tile.maxSmeltTime) 
                {
                    tile.smelt(result);
                    tile.smeltTimeProgress = 0;
                    if (!tile.inventory.getStackInSlot(INPUT_SLOT).isEmpty()) 
                    {
                        tile.maxSmeltTime = tile.getSmeltTime(tile.inventory.getStackInSlot(INPUT_SLOT));
                    }
                    else {
                        tile.maxSmeltTime = 0;
                    }
                    flag1 = true;
                } // end-if progess == maxTime
            } // end-if burning and canBurn
            else {
                tile.smeltTimeProgress = 0;
            }
        } // end-if isBurning & fuel & inputs
        else if (! tile.isBurning() && tile.smeltTimeProgress > 0)
        {
            tile.smeltTimeProgress = (short) Mth.clamp(tile.smeltTimeProgress - 2, 0, tile.maxSmeltTime);
        } // end-else if ! burning & smeltTimeProgress
        if (hasFuel != tile.isBurning())
        {
            flag1 = true;
            blockstate = blockstate.setValue(VeryAbstractFurnaceBlock.LIT, Boolean.valueOf(tile.isBurning()));
    
            // Flag 2: Send the change to clients
            level.setBlock(blockpos, blockstate, Block.UPDATE_ALL);
        }
        
        if (flag1) {
            setChanged(level, blockpos, blockstate);
         }
    } // end serverTick()

    @Override
    public void onLoad()
    {
        super.onLoad();
        // We set this in onLoad instead of the constructor so that TileEntities
        // constructed from NBT (saved tile entities) have this set to the proper value
        if (level != null && !level.isClientSide)
            lastBurning = isBurning();
    }

    /**
     * Read saved data from disk into the tile.
     */
    @Override
    public void load(final CompoundTag compound)
    {
        super.load(compound);
        this.inventory.deserializeNBT(compound.getCompound(INVENTORY_TAG));
        this.smeltTimeProgress = compound.getInt(SMELT_TIME_LEFT_TAG);
        this.maxSmeltTime = compound.getInt(MAX_SMELT_TIME_TAG);
        this.fuelBurnTimeLeft = compound.getInt(FUEL_BURN_TIME_LEFT_TAG);
        this.maxFuelBurnTime = compound.getInt(MAX_FUEL_BURN_TIME_TAG);

        // We set this in read() instead of the constructor so that TileEntities
        // constructed from NBT (saved tile entities) have this set to the proper value
        if (this.hasLevel() && !this.level.isClientSide) {
            lastBurning = this.isBurning();
        }

        // get recipe2xp map
        int ii = compound.getShort("RecipesUsedSize");
        for(int jj = 0; jj < ii; ++jj) {
           ResourceLocation resourcelocation 
               = new ResourceLocation(compound.getString("RecipeLocation" + jj));
           int kk = compound.getInt("RecipeAmount" + jj);
           this.recipe2xp_map.put(resourcelocation, kk);
        }
        
        // blockstate?
        if (this.hasLevel()) {
            this.level.setBlockAndUpdate(getBlockPos(), this.getBlockState()
                                            .setValue(BlockStateProperties.LIT, 
                                                    Boolean.valueOf(this.isBurning())));
        }
        
    } // end load()

    /**
     * Write data from the tile into a compound tag for saving to disk.
     */
    @Nonnull
    @Override
    public void saveAdditional(final CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.put(INVENTORY_TAG, this.inventory.serializeNBT());
        compound.putInt(SMELT_TIME_LEFT_TAG, this.smeltTimeProgress);
        compound.putInt(MAX_SMELT_TIME_TAG, this.maxSmeltTime);
        compound.putInt(FUEL_BURN_TIME_LEFT_TAG, this.fuelBurnTimeLeft);
        compound.putInt(MAX_FUEL_BURN_TIME_TAG, this.maxFuelBurnTime);
        
        // write recipe2xp map
        compound.putShort("RecipesUsedSize", (short)this.recipe2xp_map.size());
        int ii = 0;
        for(Entry<ResourceLocation, Integer> entry : this.recipe2xp_map.entrySet()) 
        {
           compound.putString("RecipeLocation" + ii, entry.getKey().toString());
           compound.putInt("RecipeAmount" + ii, entry.getValue());
           ++ii;
        }
    } // end saveAdditional()

    /**
     * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the
     * chunk or when many blocks change at once.
     * This compound comes back to you client-side in {@link #handleUpdateTag}
     * The default implementation ({@link TileEntity#handleUpdateTag}) calls {@link #writeInternal)}
     * which doesn't save any of our extra data so we override it to call {@link #write} instead
     */
    @Override
    @Nonnull
    public CompoundTag getUpdateTag()
    {
        return this.saveWithFullMetadata();
    }

    /**
     * Called when the chunk's TE update tag, gotten from {@link #getUpdateTag()}, is received on the client.
     * Used to handle this tag in a special way. By default this simply calls {@link #readFromNBT(NBTTagCompound)}.
     *
     * @param tag The {@link NBTTagCompound} sent from {@link #getUpdateTag()}
     */
    @Override
    public void handleUpdateTag(CompoundTag tag)
    {
        this.load(tag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    
    /**
     * Called when you receive a TileEntityData packet for the location this
     * TileEntity is currently in. On the client, the NetworkManager will always
     * be the remote server. On the server, it will be whomever is responsible for
     * sending the packet.
     *
     * @param net The NetworkManager the packet originated from
     * @param pkt The data packet
     */
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        CompoundTag nbtTag = pkt.getTag();
        this.load(nbtTag);
    }


//    /**
//     * Called from {@link NetworkHooks#openGui}
//     * (which is called from {@link ElectricFurnaceBlock#onBlockActivated} on the logical server)
//     *
//     * @return The logical-server-side Container for this TileEntity
//     */
//    @Nonnull
//    public abstract AbstractContainerMenu createMenu(final int windowId, final Inventory inv);
//
//	@Override
//	public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
//		return createMenu(windowId, inv);
//	}
    
    public void grantExperience(Player player)
    {
        List<Recipe<?>> list = Lists.newArrayList();

        for (Entry<ResourceLocation, Integer> entry : this.recipe2xp_map.entrySet())
        {
            player.level().getRecipeManager().byKey(entry.getKey()).ifPresent((p_213993_3_) -> {
                list.add(p_213993_3_);
                spawnExpOrbs(player, entry.getValue(), ((AbstractCookingRecipe) p_213993_3_).getExperience());
            });
        }
        player.awardRecipes(list);
        this.recipe2xp_map.clear();
    }
    
    protected static void spawnExpOrbs(Player player, int pCount, float experience)
    {
        if (experience == 0.0F) {
            pCount = 0;
        }
        else if (experience < 1.0F)
        {
            int i = Mth.floor((float) pCount * experience);
            if (i < Mth.ceil((float) pCount * experience)
                    && Math.random() < (double) ((float) pCount * experience - (float) i))
            {
                ++i;
            }
            pCount = i;
        }

        while (pCount > 0)
        {
            int j = ExperienceOrb.getExperienceValue(pCount);
            pCount -= j;
            player.level().addFreshEntity(new ExperienceOrb(player.level(), player.getX(), player.getY() + 0.5D,
                    player.getZ() + 0.5D, j));
        }
    } // end spawnExpOrbs()

 } // end class
