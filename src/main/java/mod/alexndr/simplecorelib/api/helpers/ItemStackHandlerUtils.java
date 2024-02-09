package mod.alexndr.simplecorelib.api.helpers;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * Implements helper functions comparable to ContainerHelper, but for ItemStackHandler-based inventories.
 *
 */
public class ItemStackHandlerUtils 
{
	public static ItemStack removeItem(ItemStackHandler inv, int slot, int count) 
	{
		if (slot < 0 || slot > inv.getSlots())
		{
			return ItemStack.EMPTY;
		}
		ItemStack items = inv.getStackInSlot(slot).copy();
		if (items.isEmpty() || count <= 0)
		{
			return ItemStack.EMPTY;
		}
		ItemStack newItems = items.split(count);
		inv.setStackInSlot(slot, items);
		return newItems;
	} // end removeItem()

	public static ItemStack takeItem(ItemStackHandler inv, int slot) 
	{
		//return ? inv.set(slot, ItemStack.EMPTY) : ItemStack.EMPTY;
		if (slot < 0 || slot > inv.getSlots())
		{
			return ItemStack.EMPTY;
		}
		ItemStack items = inv.getStackInSlot(slot).copy();
		inv.setStackInSlot(slot, ItemStack.EMPTY);
		return items;
	} // end takeItem()

} // end ItemStackHandlerUtils
