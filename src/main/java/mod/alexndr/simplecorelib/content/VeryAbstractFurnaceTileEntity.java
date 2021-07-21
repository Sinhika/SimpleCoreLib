package mod.alexndr.simplecorelib.content;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

/**
 * Abstracts the mechanics of a conventional-ish furnace (1 input, 1 fuel, 1 output slot).
 * Not useable for more exotic furnaces, such as the Fusion furnace.
 * 
 * @author Sinhika
 *
 */
public abstract class VeryAbstractFurnaceTileEntity extends LockableTileEntity
        implements ITickableTileEntity, INamedContainerProvider
{
    public static final int FUEL_SLOT = 0;
    public static final int INPUT_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;
    
    protected static final String INVENTORY_TAG = "inventory";
    protected static final String SMELT_TIME_LEFT_TAG = "smeltTimeLeft";
    protected static final String MAX_SMELT_TIME_TAG = "maxSmeltTime";
    protected static final String FUEL_BURN_TIME_LEFT_TAG = "fuelBurnTimeLeft";
    protected static final String MAX_FUEL_BURN_TIME_TAG = "maxFuelBurnTime";
    
    protected final IRecipeType<? extends AbstractCookingRecipe> recipeType;
    protected final Map<ResourceLocation, Integer> recipe2xp_map = Maps.newHashMap();
    
    // implement recipe-caching like all the cool kids.
    protected AbstractCookingRecipe cachedRecipe;
    protected ItemStack failedMatch = ItemStack.EMPTY;
    
    protected double fuelMultiplier = 1.0;

    public short smeltTimeProgress = -1;
    public short maxSmeltTime = -1;
    public int fuelBurnTimeLeft = -1;
    public int maxFuelBurnTime = -1;
    protected boolean lastBurning = false;

    public final ItemStackHandler inventory = new ItemStackHandler(3) 
    {
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
    
            @Override
            protected void onContentsChanged(final int slot) {
                super.onContentsChanged(slot);
                // Mark the tile entity as having changed whenever its inventory changes.
                // "markDirty" tells vanilla that the chunk containing the tile entity has
                // changed and means the game will save the chunk to disk later.
                VeryAbstractFurnaceTileEntity.this.setChanged();
            } // end ()
    }; // end ItemStackHandler(3)

    protected final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
    protected final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalUp = LazyOptional.of(() -> new RangedWrapper(this.inventory, INPUT_SLOT, INPUT_SLOT + 1));
    protected final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalDown = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_SLOT, OUTPUT_SLOT + 1));
    protected final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalSides = LazyOptional.of(() -> new RangedWrapper(this.inventory, FUEL_SLOT, INPUT_SLOT + 1));

    public VeryAbstractFurnaceTileEntity(TileEntityType<?> tileEntityTypeIn, IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn)
    {
        super(tileEntityTypeIn);
        this.recipeType = recipeTypeIn;
        // TODO Auto-generated constructor stub
    }

    abstract public boolean isFuel(ItemStack stack);
    
    /**
     * @return If the stack is not empty and has a smelting recipe associated with it
     */
    abstract protected boolean isInput(final ItemStack stack);

    /**
     * @return If the stack's item is equal to the result of smelting our input
     */
    abstract protected boolean isOutput(final ItemStack stack);
    
    public boolean isBurning()
    {
        return this.fuelBurnTimeLeft > 0;
    }

    /**
     * @return If the fuel was burnt
     */
    abstract protected boolean burnFuel(); 

    /**
     * @return The smelting recipe for the inventory; implements recipe caching.
     */
    @SuppressWarnings("unchecked")
    private Optional<AbstractCookingRecipe> getRecipe(final IInventory inventory)
    {
        if (cachedRecipe != null && cachedRecipe.matches(inventory, level))
        {
            return Optional.of(cachedRecipe);
        }
        else
        {
            AbstractCookingRecipe rec 
                = level.getRecipeManager().getRecipeFor((IRecipeType<AbstractCookingRecipe>) recipeType, inventory, level).orElse(null);
            if (rec == null) {
                failedMatch = inventory.getItem(0); // i.e., input.
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
    private Optional<AbstractCookingRecipe> getRecipe(final ItemStack input)
    {
        if (input.isEmpty() || input == failedMatch) {
            return Optional.empty();
        }
        // Due to vanilla's code we need to pass an IInventory into RecipeManager#getRecipe so we make one here.
        return getRecipe(new Inventory(input));
    }

    public void setRecipeUsed(@Nullable IRecipe<?> recipe)
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
        final Inventory dummyInventory = new Inventory(input);
        Optional<ItemStack> maybe_result = getRecipe(dummyInventory).map(recipe -> recipe.assemble(dummyInventory));

        ItemStack result = maybe_result.orElse(ItemStack.EMPTY);
        return Optional.of(result);
    }
    
    protected int getBurnDuration(ItemStack fuelstack) 
    {
        if (!fuelstack.isEmpty()) 
        {
            // improved fuel efficiency processing here.
            return (int) Math.ceil( ((double) ForgeHooks.getBurnTime(fuelstack, null)) * fuelMultiplier);
        }
        else {
            return 0;
        }
    } // end getBurnDuration
    
    protected boolean canBurn(ItemStack result)
    {
        if (!this.inventory.getStackInSlot(OUTPUT_SLOT).isEmpty() && !result.isEmpty())
        {
            ItemStack outstack = inventory.getStackInSlot(OUTPUT_SLOT);
            if (outstack.isEmpty())
            {
                return true;
            }
            else if (!outstack.sameItem(result))
            {
                return false;
            }
            else if (outstack.getCount() + result.getCount() <= this.getMaxStackSize()
                    && outstack.getCount() + result.getCount() <= outstack.getMaxStackSize())
            { // Forge fix: make furnace respect stack sizes in furnace recipes
                return true;
            }
            else
            {
                // Forge fix: make furnace respect stack sizes in furnace recipes
                return outstack.getCount() + result.getCount() <= result.getMaxStackSize(); 
            }
        } // end-if not output empty and not result empty.
        else
        {
            return false;
        }
    } // end canBurn()
    
    /**
     * Mimics the code in {@link AbstractFurnaceTileEntity#getTotalCookTime()}
     *
     * @return The custom smelt time or 200 if there is no recipe for the input
     */
    protected short getSmeltTime(final ItemStack input)
    {
        return getRecipe(input)
                .map(AbstractCookingRecipe::getCookingTime)
                .orElse(200)
                .shortValue();
    }

    protected void burn(ItemStack result)  // result = itemstack1
    {
        if (!result.isEmpty() && this.canBurn(result))
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
        } // end-if canBurn result
    } // end burn()
    
    
    @Override
    public void tick()
    {
        boolean hasFuel = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning())
        {
            --this.fuelBurnTimeLeft;
        }
        if (!this.level.isClientSide) 
        {
            ItemStack input = inventory.getStackInSlot(INPUT_SLOT).copy();
            ItemStack fuel = inventory.getStackInSlot(FUEL_SLOT).copy();
            final ItemStack result = getResult(input).orElse(ItemStack.EMPTY);
            
            if (this.isBurning() || !input.isEmpty() && !fuel.isEmpty())
            {
                if (!this.isBurning() && this.canBurn(result))
                {
                    this.fuelBurnTimeLeft = this.getBurnDuration(fuel);
                    this.maxFuelBurnTime = this.fuelBurnTimeLeft;
                    if (this.isBurning())
                    {
                        flag1 = true;
                        if (fuel.hasContainerItem()) 
                        {
                            inventory.setStackInSlot(FUEL_SLOT, fuel.getContainerItem());
                        }
                        else if (!fuel.isEmpty()) 
                        {
                            fuel.shrink(1);
                            inventory.setStackInSlot(FUEL_SLOT, fuel); // Update the data
                        }
                    } // end-if isBurning
                } // end-if canBurn
                
                if (this.isBurning() && this.canBurn(result))
                {
                    ++this.smeltTimeProgress;
                    if (this.smeltTimeProgress == this.maxSmeltTime) 
                    {
                        this.smeltTimeProgress = 0;
                        this.maxSmeltTime = this.getSmeltTime(input);
                        this.burn(result);
                        flag1 = true;
                    } // end-if progess == maxTime
                } // end-if burning and canBurn
                else {
                    this.smeltTimeProgress = 0;
                }
            } // end-if isBurning & fuel & inputs
            else if (! this.isBurning() && this.smeltTimeProgress > 0)
            {
                this.smeltTimeProgress = (short) MathHelper.clamp(this.smeltTimeProgress - 2, 0, this.maxSmeltTime);
            } // end-else if ! burning & smeltTimeProgress
            if (hasFuel != this.isBurning())
            {
                flag1 = true;
                final BlockState newState = this.getBlockState().setValue(BlockStateProperties.LIT, this.isBurning());
        
                // Flag 2: Send the change to clients
                level.setBlock(worldPosition, newState, 3);
            }
        } // end-if ! clientSide
        
        if (flag1) {
            this.setChanged();
         }
        
    } // end tick()

} // end class
