package mod.alexndr.simplecorelib.content;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;

public abstract class VeryAbstractSmokerTileEntity extends VeryAbstractFurnaceTileEntity
{

    public VeryAbstractSmokerTileEntity(BlockEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn, RecipeType.SMOKING);
    }

    @Override
    protected int getBurnDuration(ItemStack fuelstack)
    {
        int retval = super.getBurnDuration(fuelstack) / 2;
//        LOGGER.debug("[" + getDisplayName().getString() + "]VeryAbstractSmokerTileEntity.getBurnDuration: returns " + retval + " for " + fuelstack.toString());
        return retval;
    }

} // end class
