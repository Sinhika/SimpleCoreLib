package mod.alexndr.simplecorelib.content;

import javax.annotation.Nonnull;

import mod.alexndr.simplecorelib.helpers.FurnaceResultSlotItemHandler;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

//public abstract class VeryAbstractFurnaceMenu<T extends VeryAbstractFurnaceBlock> extends RecipeBookMenu<Container>
public abstract class VeryAbstractFurnaceMenu extends AbstractContainerMenu
{
	   public static final int FUEL_SLOT = 0;
	   public static final int INGREDIENT_SLOT = 1;
	   public static final int RESULT_SLOT = 2;
	   public static final int SLOT_COUNT = 3;
	   public static final int DATA_COUNT = 4;

	   public static final int DATA_FUEL_TIME_LEFT = 0;
	   public static final int DATA_FUEL_TIME_MAX = 1;
	   public static final int DATA_COOKING_PROGRESS = 2;
	   public static final int DATA_COOKING_TOTAL_TIME = 3;
	   public static final int NUM_DATA_VALUES = 4;
	   

	   protected final ItemStackHandler container;
	   protected final Container tileContainer;
	   protected final ContainerData data;
	   protected final Level level;
	   protected final RecipeType<? extends AbstractCookingRecipe> recipeType;

	   
	/**
	 * Client-side constructor. 
	 * @param menutype
	 * @param recipetype
	 * @param recipeBookType
	 * @param id
	 * @param inv
	 */
	protected VeryAbstractFurnaceMenu(MenuType<?> menutype, RecipeType<? extends AbstractCookingRecipe> recipetype,
									  int id, Inventory inv) 
	{
		this(menutype, recipetype, id, inv, new ItemStackHandler(3), 
				new SimpleContainerData(4), new SimpleContainer(3));
	} // end client ctor

	
    /**
     * Constructor called logical-server-side from {@link MythrilFurnaceTileEntity#createMenu}
     * and logical-client-side from {@link #ModFurnaceContainer(int, PlayerInventory, PacketBuffer)}
     */
	protected VeryAbstractFurnaceMenu(MenuType<?> menutype, 
									  RecipeType<? extends AbstractCookingRecipe> recipetype,
									  int id, Inventory playerInventory, 
									  ItemStackHandler container, ContainerData containerdata,
									  Container tilecontainer) 
	{
		super(menutype, id);
		
		this.recipeType = recipetype;
		//checkContainerSize(container, 3);
		// checkContainerDataCount(containerdata, 4);
		this.container = container;
		this.data = containerdata;
		this.level = playerInventory.player.level;
		this.tileContainer = tilecontainer;
		
        // Add tracking for data (Syncs to client/updates value when it changes)
		this.addDataSlots(containerdata);
		
        // Tile inventory slot(s)
        this.addSlot(new SlotItemHandler(container, INGREDIENT_SLOT, 56, 17));
        this.addSlot(new SlotItemHandler(container, FUEL_SLOT, 56, 53));
        this.addSlot(new FurnaceResultSlotItemHandler(playerInventory.player, container, tilecontainer,
        		RESULT_SLOT, 116, 35));

//		this.addSlot(new Slot(container, INGREDIENT_SLOT, 56, 17));
//		this.addSlot(new FurnaceFuelSlot(this, container, 1, 56, 53));
//		this.addSlot(new FurnaceResultSlot(playerInventory.player, container, 2, 116, 35));
//
		// Player inventory constants
        final int playerInventoryStartX = 8;
        final int playerInventoryStartY = 84;
        final int slotSizePlus2 = 18; // slots are 16x16, plus 2 (for spacing/borders) is 18x18

        // Player Top Inventory slots
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, playerInventoryStartX + (column * slotSizePlus2), playerInventoryStartY + (row * slotSizePlus2)));
            }
        }

        final int playerHotbarY = playerInventoryStartY + slotSizePlus2 * 3 + 4;
        // Player Hotbar slots
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
        }
        
	} // end ctor
	

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
		return recipe.matches(this.tileContainer, this.level);
	}


	@Override
    public boolean stillValid(Player player) {
        return this.tileContainer.stillValid(player);
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
		return  (i - j) * 13 / i;
	}

	public boolean isLit() {
		return this.data.get(DATA_FUEL_TIME_LEFT) > 0;
	}
	
} // end class
