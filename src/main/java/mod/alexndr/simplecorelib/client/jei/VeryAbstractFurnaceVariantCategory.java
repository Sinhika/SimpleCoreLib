package mod.alexndr.simplecorelib.client.jei;

import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraft.resources.ResourceLocation;

/**
 * Class copied and adapted from mezz's FurnaceVariantCategory class, just to avoid requiring access to the non-api
 * parts of JEI at runtime. JEI is under the MIT license, so it is open source.
 * 
 * @author Sinhika
 *
 * @param <T>
 */
public abstract class VeryAbstractFurnaceVariantCategory<T> implements IRecipeCategory<T>
{
	public static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation(SimpleCoreLib.MODID, "textures/gui/gui_vanilla.png");

	protected static final int inputSlot = 0;
	protected static final int fuelSlot = 1;
	protected static final int outputSlot = 2;

	protected final IDrawableStatic staticFlame;
	protected final IDrawableAnimated animatedFlame;

	public VeryAbstractFurnaceVariantCategory(IGuiHelper guiHelper) {
		staticFlame = guiHelper.createDrawable(RECIPE_GUI_VANILLA, 82, 114, 14, 14);
		animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
	}

} // end class
