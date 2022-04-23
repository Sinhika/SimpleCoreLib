package mod.alexndr.simplecorelib.api.content;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class VeryAbstractSmokerTileEntity extends VeryAbstractFurnaceTileEntity
{

    public VeryAbstractSmokerTileEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos blockpos, BlockState blockstate)
    {
        super(tileEntityTypeIn, blockpos, blockstate, RecipeType.SMOKING);
    }

    @Override
    protected int getBurnDuration(ItemStack fuelstack)
    {
        int retval = super.getBurnDuration(fuelstack) / 2;
//        LOGGER.debug("[" + getDisplayName().getString() + "]VeryAbstractSmokerTileEntity.getBurnDuration: returns " + retval + " for " + fuelstack.toString());
        return retval;
    }

} // end class
