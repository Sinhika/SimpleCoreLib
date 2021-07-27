package mod.alexndr.simplecorelib.content;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;

public abstract class VeryAbstractSmokerTileEntity extends VeryAbstractFurnaceTileEntity
{

    public VeryAbstractSmokerTileEntity(TileEntityType<?> tileEntityTypeIn,
            IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn)
    {
        super(tileEntityTypeIn, IRecipeType.SMOKING);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected int getBurnDuration(ItemStack fuelstack)
    {
        return super.getBurnDuration(fuelstack) / 2;
    }

} // end class
