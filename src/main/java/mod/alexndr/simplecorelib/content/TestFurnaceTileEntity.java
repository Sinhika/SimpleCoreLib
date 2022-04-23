package mod.alexndr.simplecorelib.content;

import mod.alexndr.simplecorelib.api.content.VeryAbstractFurnaceTileEntity;
import mod.alexndr.simplecorelib.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

public class TestFurnaceTileEntity extends VeryAbstractFurnaceTileEntity
{

    public TestFurnaceTileEntity(BlockPos blockpos, BlockState blockstate)
    {
        super(ModTileEntityTypes.test_furnace.get(), blockpos, blockstate, RecipeType.SMELTING);
    }

} // end class
