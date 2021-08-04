package mod.alexndr.simplecorelib.helpers;

import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Implements helper functions comparable to ContainerHelper, but for ItemStackHandler-based inventories.
 *
 */
public class ItemStackHandlerContainerUtils 
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

} // end ItemStackHandlerContainerUtils
