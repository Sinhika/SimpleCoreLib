package mod.alexndr.simplecorelib.content;

import javax.annotation.Nonnull;

import mod.alexndr.simplecorelib.helpers.FunctionalIntReferenceHolder;
import mod.alexndr.simplecorelib.helpers.FurnaceResultSlotItemHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.items.SlotItemHandler;

public abstract class VeryAbstractFurnaceContainer<T extends VeryAbstractFurnaceBlock> extends AbstractContainerMenu
{
    protected RegistryObject<T> my_block; 
    public VeryAbstractFurnaceTileEntity tileEntity;
    protected ContainerLevelAccess canInteractWithCallable;
    
    /**
     * Constructor called logical-server-side from {@link MythrilFurnaceTileEntity#createMenu}
     * and logical-client-side from {@link #ModFurnaceContainer(int, PlayerInventory, PacketBuffer)}
     */
    public VeryAbstractFurnaceContainer(MenuType<?> type, int id, final Inventory playerInventory, 
                                       final VeryAbstractFurnaceTileEntity tileEntity, 
                                       final RegistryObject<T> block)
    {
        super(type, id);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos());
        this.my_block = block;
        
        // Add tracking for data (Syncs to client/updates value when it changes)
        this.addDataSlot(new FunctionalIntReferenceHolder(() -> tileEntity.smeltTimeProgress, v -> tileEntity.smeltTimeProgress = (short) v));
        this.addDataSlot(new FunctionalIntReferenceHolder(() -> tileEntity.maxSmeltTime, v -> tileEntity.maxSmeltTime = (short) v));
        this.addDataSlot(new FunctionalIntReferenceHolder(() -> tileEntity.fuelBurnTimeLeft, v -> tileEntity.fuelBurnTimeLeft = (int) v));
        this.addDataSlot(new FunctionalIntReferenceHolder(() -> tileEntity.maxFuelBurnTime, v -> tileEntity.maxFuelBurnTime = (int) v));

        // Tile inventory slot(s)
        this.addSlot(new SlotItemHandler(tileEntity.inventory, VeryAbstractFurnaceTileEntity.FUEL_SLOT, 56, 53));
        this.addSlot(new SlotItemHandler(tileEntity.inventory, VeryAbstractFurnaceTileEntity.INPUT_SLOT, 56, 17));
        this.addSlot(new FurnaceResultSlotItemHandler(playerInventory.player, tileEntity, tileEntity.inventory,
                VeryAbstractFurnaceTileEntity.OUTPUT_SLOT, 116, 35));

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
    } // end server-side ctor

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
    
    		final int containerSlots = this.slots.size() - player.inventory.items.size();
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

    @Override
    public boolean stillValid(@Nonnull final Player player)
    {
    	return stillValid(canInteractWithCallable, player, my_block.get());
    }

}