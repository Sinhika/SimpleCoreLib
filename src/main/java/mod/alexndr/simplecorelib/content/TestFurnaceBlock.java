package mod.alexndr.simplecorelib.content;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class TestFurnaceBlock extends VeryAbstractFurnaceBlock
{

    public TestFurnaceBlock(Properties builder)
    {
        super(builder);
        // TODO Auto-generated constructor stub
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
            BlockRayTraceResult hit)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
