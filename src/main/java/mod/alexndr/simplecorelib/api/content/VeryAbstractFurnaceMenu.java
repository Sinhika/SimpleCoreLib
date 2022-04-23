package mod.alexndr.simplecorelib.api.content;

import javax.annotation.Nonnull;

import mod.alexndr.simplecorelib.api.helpers.FurnaceResultSlotItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

//public abstract class VeryAbstractFurnaceMenu<T extends VeryAbstractFurnaceBlock> extends RecipeBookMenu<Container>
public abstract class VeryAbstractFurnaceMenu extends AbstractContainerMenu
{
    public static final int INGREDIENT_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    public static final int RESULT_SLOT = 2;
    public static final int SLOT_COUNT = 3;
    public static final int DATA_COUNT = 4;

    public static final int DATA_FUEL_TIME_LEFT = 0;
    public static final int DATA_FUEL_TIME_MAX = 1;
    public static final int DATA_COOKING_PROGRESS = 2;
    public static final int DATA_COOKING_TOTAL_TIME = 3;
    public static final int NUM_DATA_VALUES = 4;

    protected final BlockEntity blockEntity;
    protected IItemHandler playerInventory;
    protected final RecipeType<? extends AbstractCookingRecipe> recipeType;
    protected final Player playerEntity;
    protected Container recipeInv;
    protected ContainerData data;
    
    /**
     * Constructor called logical-server-side from {@link MythrilFurnaceTileEntity#createMenu}
     * and logical-client-side from {@link #ModFurnaceContainer(int, PlayerInventory, PacketBuffer)}
     */
	protected VeryAbstractFurnaceMenu(MenuType<?> menutype, int id, BlockPos pos, Inventory playerInventory,
	                                  Player player, RecipeType<? extends AbstractCookingRecipe> recipetype) 
	{
		super(menutype, id);
        this.blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
		this.recipeType = recipetype;
		this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        if (blockEntity != null && blockEntity instanceof VeryAbstractFurnaceTileEntity) 
        {
            this.recipeInv = new RecipeWrapper( ((VeryAbstractFurnaceTileEntity) this.blockEntity).inventory);
            this.data = ((VeryAbstractFurnaceTileEntity) this.blockEntity).dataAccess;
            
            // Add tracking for data (Syncs to client/updates value when it changes)
            this.addDataSlots(this.data);

            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(
                    h -> { 
                        addSlot(new SlotItemHandler(h, INGREDIENT_SLOT, 56, 17)); 
                        addSlot(new SlotItemHandler(h, FUEL_SLOT, 56, 53)); 
                        addSlot(new FurnaceResultSlotItemHandler(player, h, blockEntity,
                                RESULT_SLOT, 116, 35));
                        });
        }
		// Player inventory constants
        final int playerInventoryStartX = 8;
        final int playerInventoryStartY = 84;
        layoutPlayerInventorySlots(playerInventoryStartX, playerInventoryStartY);
	} // end ctor
	
	protected int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx)
    {
        for (int i = 0; i < amount; i++)
        {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    protected int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount,
            int dy)
    {
        for (int j = 0; j < verAmount; j++)
        {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

	protected void layoutPlayerInventorySlots(int leftCol, int topRow)
	{
	       // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);	    
	}
	

    /**
     * Generic & dynamic version of {@link Container#transferStackInSlot(PlayerEntity, int)}.
     * Handle when the stack in slot {@code index} is shift-clicked.
     * Normally this moves the stack between the player inventory and the other inventory(s).
     *
     * @param player the player passed in
     * @param index  the index passed in
     * @return the {@link ItemStack}
     */
    @Nonnull
    @Override
    public ItemStack quickMoveStack(final Player player, final int index)
    {
    	ItemStack returnStack = ItemStack.EMPTY;
    	final Slot slot = this.slots.get(index);
    	if (slot != null && slot.hasItem()) {
    		final ItemStack slotStack = slot.getItem();
    		returnStack = slotStack.copy();
    
    		final int containerSlots = this.slots.size() - player.getInventory().items.size();
    		if (index < containerSlots) {
    			if (!moveItemStackTo(slotStack, containerSlots, this.slots.size(), true)) {
    				return ItemStack.EMPTY;
    			}
    		} else if (!moveItemStackTo(slotStack, 0, containerSlots, false)) {
    			return ItemStack.EMPTY;
    		}
    		if (slotStack.getCount() == 0) {
    			slot.set(ItemStack.EMPTY);
    		} else {
    			slot.setChanged();
    		}
    		if (slotStack.getCount() == returnStack.getCount()) {
    			return ItemStack.EMPTY;
    		}
    		slot.onTake(player, slotStack);
    	}
    	return returnStack;
    }

    
	public boolean recipeMatches(Recipe<? super Container> recipe) 
    {
		return recipe.matches(recipeInv, this.blockEntity.getLevel());
	}


	@Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()),
                          player, blockEntity.getBlockState().getBlock());
     }


	public void clearCraftingContent() 
	{
		this.getSlot(INGREDIENT_SLOT).set(ItemStack.EMPTY);
		this.getSlot(RESULT_SLOT).set(ItemStack.EMPTY);
	}


	public int getResultSlotIndex() {
		return RESULT_SLOT;
	}


	public int getGridWidth() {
		return 1;
	}


	public int getGridHeight() {
		return 1;
	}

	public int getSize() {
		return 3;
	}

	public int getBurnProgress() 
	{
		int i = this.data.get(DATA_COOKING_PROGRESS);
		int j = this.data.get(DATA_COOKING_TOTAL_TIME);
		return j != 0 && i != 0 ? i * 24 / j : 0;
	}

	public int getLitProgress() 
	{
		int i = this.data.get(DATA_FUEL_TIME_MAX);
		int j = this.data.get(DATA_FUEL_TIME_LEFT);
		if (i == 0)
		{
			i = 200;
		}
		return  j * 13 / i;
	}

	public boolean isLit() {
		return this.data.get(DATA_FUEL_TIME_LEFT) > 0;
	}
	
} // end class
