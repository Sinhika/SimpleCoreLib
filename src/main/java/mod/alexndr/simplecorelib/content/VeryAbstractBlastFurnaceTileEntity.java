package mod.alexndr.simplecorelib.content;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;

public abstract class VeryAbstractBlastFurnaceTileEntity extends VeryAbstractFurnaceTileEntity
{

    public VeryAbstractBlastFurnaceTileEntity(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn, IRecipeType.BLASTING);
    }

    @Override
    protected int getBurnDuration(ItemStack fuelstack)
    {
        int retval = super.getBurnDuration(fuelstack) / 2;
//        LOGGER.debug("[" + getDisplayName().getString() + "]VeryAbstractBlastFurnaceTileEntity.getBurnDuration: returns " + retval + " for " + fuelstack.toString());
        return retval;
    }

    
} // end class
