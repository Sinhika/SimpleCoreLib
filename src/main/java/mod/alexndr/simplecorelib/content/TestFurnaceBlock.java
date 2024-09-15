package mod.alexndr.simplecorelib.content;

import com.mojang.serialization.MapCodec;
import mod.alexndr.simplecorelib.api.content.SomewhatAbstractFurnaceBlock;
import mod.alexndr.simplecorelib.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class TestFurnaceBlock extends SomewhatAbstractFurnaceBlock
{
    public static final MapCodec<TestFurnaceBlock> CODEC = simpleCodec(TestFurnaceBlock::new);

    public TestFurnaceBlock(BlockBehaviour.Properties builder)
    {
        super(builder);
    }

    @Override protected @NotNull MapCodec<? extends AbstractFurnaceBlock> codec()
    {
        return CODEC;
    }

    @Nullable @Override public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            @NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType)
    {
        return SomewhatAbstractFurnaceBlock.createFurnaceTicker(pLevel, pBlockEntityType, ModTileEntityTypes.test_furnace.get());
    }

    /**
     * Called to open this furnace's container.
     *
     * @param pLevel
     * @param pPos
     * @param pPlayer
     */
    @Override protected void openContainer(Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer)
    {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof TestFurnaceTileEntity) {
            pPlayer.openMenu((MenuProvider)blockentity);
            pPlayer.awardStat(Stats.INTERACT_WITH_FURNACE);
        }
    }


    @Nullable @Override public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState)
    {
        return new TestFurnaceTileEntity(pPos, pState);
    }

    /**
     * Called periodically clientside on blocks near the player to show effects (like furnace fire particles).
     *
     * @param stateIn
     * @param worldIn
     * @param pos
     * @param rand
     */
    @Override public void animateTick(BlockState stateIn, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull RandomSource rand)
    {
        if (stateIn.getValue(LIT)) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = pos.getY();
            double d2 = (double) pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                worldIn.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F,
                        false);
            }

            Direction direction = stateIn.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            // double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
            double d6 = rand.nextDouble() * 6.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;
            worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    } // end animateTick
} // end class
