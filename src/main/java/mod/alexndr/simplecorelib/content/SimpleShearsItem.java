package mod.alexndr.simplecorelib.content;

import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlockState;

import net.minecraft.item.Item.Properties;

public class SimpleShearsItem extends ShearsItem
{

    public SimpleShearsItem(Properties builder)
    {
        super(builder);
    }

    /**
     * Called when this item is used when targetting a Block
     */
    @Override
    public ActionResultType useOn(ItemUseContext context)
    {
        // what block are we clicking on?
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        PlayerEntity player = context.getPlayer();
        World worldIn = context.getLevel();
        
        // there are no shears, there is only Null...
        if (stack.isEmpty()) {
            return ActionResultType.PASS;
        }
        // can we do stuff to this block?
        if (!player.mayUseItemAt(pos, context.getClickedFace(), stack))
        {
            return ActionResultType.PASS;
        }
        // tell me about the block we clicked on.
        IForgeBlockState targetBlock = worldIn.getBlockState(pos);
        // is it a pumpkin? Then carve it!
        if (targetBlock.getBlockState().getBlock() instanceof PumpkinBlock)
        {
            if (!worldIn.isClientSide) 
            {
                Direction direction = context.getClickedFace();
                Direction direction1 = direction.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : direction;
                worldIn.playSound((PlayerEntity)null, pos, SoundEvents.PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlock(pos, Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, direction1), 11);
                ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + 0.5D + (double)direction1.getStepX() * 0.65D, (double)pos.getY() + 0.1D, (double)pos.getZ() + 0.5D + (double)direction1.getStepZ() * 0.65D, new ItemStack(Items.PUMPKIN_SEEDS, 4));
                itementity.setDeltaMovement(0.05D * (double)direction1.getStepX() + worldIn.random.nextDouble() * 0.02D, 0.05D, 0.05D * (double)direction1.getStepZ() + worldIn.random.nextDouble() * 0.02D);
                worldIn.addFreshEntity(itementity);
                stack.hurtAndBreak(1, player, (p_220282_1_) -> {
                   p_220282_1_.broadcastBreakEvent(context.getHand());
                });
             }
            
        }
        return super.useOn(context);
    } // end onItemUse()

     
} // end-class

