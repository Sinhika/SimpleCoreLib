package mod.alexndr.simplecorelib.api.content.block_entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractYieldEnhancingSmokerBlockEntity extends AbstractYieldEnhancingFurnaceBlockEntity
{

    public AbstractYieldEnhancingSmokerBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos blockpos, BlockState blockstate)
    {
        super(tileEntityTypeIn, RecipeType.SMOKING, blockpos, blockstate);
    }

    @Override
    protected int getBurnDuration(ItemStack fuelstack)
    {
        int retval = super.getBurnDuration(fuelstack) / 2;
//        LOGGER.debug("[" + getDisplayName().getString() + "]AbstractModSmokerTileEntity.getBurnDuration: returns " + retval + " for " + fuelstack.toString());
        return retval;
    }

} // end class
