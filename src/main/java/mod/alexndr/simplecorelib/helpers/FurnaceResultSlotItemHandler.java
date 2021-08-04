package mod.alexndr.simplecorelib.helpers;

import mod.alexndr.simplecorelib.content.VeryAbstractFurnaceTileEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import PlayerEntity;
import TileEntity;

/**
 * Based on vanilla FurnaceResultSlot class, but for SlotItemHandlers.
 *
 */
public class FurnaceResultSlotItemHandler extends SlotItemHandler
{
    private final Player player;
    private int removeCount;
    private final BlockEntity tile;
    
    public FurnaceResultSlotItemHandler(Player player, BlockEntity tileEntity, IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
        this.tile = tileEntity;
        this.player = player;
    }

    /**
     * No, you can't stick items in the output slot.
     */
    @Override
    public boolean mayPlace(ItemStack stack)
    {
        return false;
    }

    
    @Override
    public ItemStack remove(int amount)
    {
        if (this.hasItem()) {
            this.removeCount += Math.min(amount, this.getItem().getCount());
         }
        return super.remove(amount);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount)
    {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack)
    {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);
        if (!this.player.level.isClientSide && this.tile instanceof VeryAbstractFurnaceTileEntity) 
        {
           ((VeryAbstractFurnaceTileEntity)this.tile).grantExperience(this.player);
        }
        this.removeCount = 0;
        net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerSmeltedEvent(this.player, stack);
    } // end onCrafting

    @Override
    public ItemStack onTake(Player thePlayer, ItemStack stack)
    {
        this.checkTakeAchievements(stack);
        super.onTake(thePlayer, stack);
        return stack;
    }

    
     
} // end class
