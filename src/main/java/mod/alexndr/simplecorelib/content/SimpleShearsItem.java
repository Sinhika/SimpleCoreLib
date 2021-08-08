package mod.alexndr.simplecorelib.content;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.PumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

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
    public InteractionResult useOn(UseOnContext context)
    {
        // what block are we clicking on?
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        Level worldIn = context.getLevel();
        
        // there are no shears, there is only Null...
        if (stack.isEmpty()) {
            return InteractionResult.PASS;
        }
        // can we do stuff to this block?
        if (!player.mayUseItemAt(pos, context.getClickedFace(), stack))
        {
            return InteractionResult.PASS;
        }
        // tell me about the block we clicked on.
        BlockState targetBlock = worldIn.getBlockState(pos);
        // is it a pumpkin? Then carve it!
        if (targetBlock.getBlock() instanceof PumpkinBlock)
        {
            if (!worldIn.isClientSide) 
            {
                Direction direction = context.getClickedFace();
                Direction direction1 = direction.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : direction;
                worldIn.playSound((Player)null, pos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlock(pos, Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, direction1), 11);
                ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + 0.5D + (double)direction1.getStepX() * 0.65D, (double)pos.getY() + 0.1D, (double)pos.getZ() + 0.5D + (double)direction1.getStepZ() * 0.65D, new ItemStack(Items.PUMPKIN_SEEDS, 4));
                itementity.setDeltaMovement(0.05D * (double)direction1.getStepX() + worldIn.random.nextDouble() * 0.02D, 0.05D, 0.05D * (double)direction1.getStepZ() + worldIn.random.nextDouble() * 0.02D);
                worldIn.addFreshEntity(itementity);
                stack.hurtAndBreak(1, player, (p_220282_1_) -> {
                   p_220282_1_.broadcastBreakEvent(context.getHand());
                });
                worldIn.gameEvent(player, GameEvent.SHEAR, pos);
                player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
            }
            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }
        return super.useOn(context);
    } // end onItemUse()

     
} // end-class

