package mod.alexndr.simplecorelib.content;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class VeryAbstractBlastFurnaceTileEntity extends VeryAbstractFurnaceTileEntity
{

    public VeryAbstractBlastFurnaceTileEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos blockpos, BlockState blockstate)
    {
        super(tileEntityTypeIn, blockpos, blockstate, RecipeType.BLASTING);
    }

    @Override
    protected int getBurnDuration(ItemStack fuelstack)
    {
        int retval = super.getBurnDuration(fuelstack) / 2;
//        LOGGER.debug("[" + getDisplayName().getString() + "]VeryAbstractBlastFurnaceTileEntity.getBurnDuration: returns " + retval + " for " + fuelstack.toString());
        return retval;
    }

    
} // end class
