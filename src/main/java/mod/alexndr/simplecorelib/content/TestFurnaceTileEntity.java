package mod.alexndr.simplecorelib.content;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

public class TestFurnaceTileEntity extends VeryAbstractFurnaceTileEntity
{

    public TestFurnaceTileEntity(TileEntityType<?> tileEntityTypeIn,
            IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn)
    {
        super(tileEntityTypeIn, recipeTypeIn);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ITextComponent getDisplayName()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
