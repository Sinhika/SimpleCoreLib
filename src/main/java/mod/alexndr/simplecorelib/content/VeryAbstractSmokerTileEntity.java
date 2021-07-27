package mod.alexndr.simplecorelib.content;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;

public abstract class VeryAbstractSmokerTileEntity extends VeryAbstractFurnaceTileEntity
{

    public VeryAbstractSmokerTileEntity(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn, IRecipeType.SMOKING);
    }

    @Override
    protected int getBurnDuration(ItemStack fuelstack)
    {
        return super.getBurnDuration(fuelstack) / 2;
    }

} // end class
