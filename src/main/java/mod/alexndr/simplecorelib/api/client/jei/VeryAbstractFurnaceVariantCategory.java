package mod.alexndr.simplecorelib.api.client.jei;

import java.text.NumberFormat;

import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

/**
 * Class copied and adapted from mezz's FurnaceVariantCategory class, just to avoid requiring access to the non-api
 * parts of JEI at runtime. JEI is under the MIT license, so it is open source.
 * 
 * @author Sinhika
 *
 * @param <T>  extends AlternateFuelRecipe
 */
public abstract class VeryAbstractFurnaceVariantCategory<T extends AlternateFuelRecipe> implements IRecipeCategory<T> 
{
	public static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation(SimpleCoreLib.MODID, "textures/gui/gui_vanilla.png");

	protected static final int inputSlot = 0;
	protected static final int fuelSlot = 1;
	protected static final int outputSlot = 2;

	protected final IDrawableStatic staticFlame;
	protected final IDrawableAnimated animatedFlame;
    protected Component smeltCountText;
    protected T fuelRecipe;
    
	public VeryAbstractFurnaceVariantCategory(IGuiHelper guiHelper) 
	{
		staticFlame = guiHelper.createDrawable(RECIPE_GUI_VANILLA, 82, 114, 14, 14);
		animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
		this.smeltCountText = createSmeltCountText(10000000);
}

    public Component getSmeltCountText()
    {
        return smeltCountText;
    }

    public IDrawableAnimated getFlame()
    {
        return animatedFlame;
    }

    public static Component createSmeltCountText(int burnTime)
    {
        if (burnTime == AlternateFuelRecipe.getSingleItemBurnTime())
        {
            return new TranslatableComponent("gui.jei.category.fuel.smeltCount.single");
        } 
        else
        {
            NumberFormat numberInstance = NumberFormat.getNumberInstance();
            numberInstance.setMaximumFractionDigits(2);
            String smeltCount = numberInstance.format(burnTime / ((float) AlternateFuelRecipe.getSingleItemBurnTime()));
            return new TranslatableComponent("gui.jei.category.fuel.smeltCount", smeltCount);
        }
    } // end createSmeltCountText()

} // end class
