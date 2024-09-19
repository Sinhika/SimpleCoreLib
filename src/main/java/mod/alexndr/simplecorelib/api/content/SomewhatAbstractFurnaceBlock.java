package mod.alexndr.simplecorelib.api.content;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nullable;

public abstract class SomewhatAbstractFurnaceBlock extends AbstractFurnaceBlock
{
    protected SomewhatAbstractFurnaceBlock(Properties properties)
    {
        super(properties);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createCustomFurnaceTicker(
            Level level, BlockEntityType<T> serverType, BlockEntityType<? extends SomewhatAbstractFurnaceBlockEntity> clientType
    )
    {
        return level.isClientSide
               ? null
               : createTickerHelper(serverType, clientType, SomewhatAbstractFurnaceBlockEntity::serverTick);
    }
} // end class
