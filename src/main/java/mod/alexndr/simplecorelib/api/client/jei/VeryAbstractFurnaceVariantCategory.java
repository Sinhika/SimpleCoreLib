package mod.alexndr.simplecorelib.api.client.jei;

import java.text.NumberFormat;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.client.ClientModEventSubscriber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
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

	protected final IDrawableStatic background;
	protected final IDrawableStatic staticFlame;
	protected final IDrawableAnimated animatedFlame;
	protected final IDrawableStatic flameTransparentBackground;
	protected Component localizedName;

    protected Component smeltCountText;
    protected T fuelRecipe;
    
	public VeryAbstractFurnaceVariantCategory(IGuiHelper guiHelper) 
	{
		staticFlame = guiHelper.createDrawable(RECIPE_GUI_VANILLA, 82, 114, 14, 14);
		animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
		
        // width of the recipe depends on the text, which is different in each language
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        
		// can't call createSmeltCountText() yet because fuelRecipe does not exist.
		this.smeltCountText = Component.translatable("gui.jei.category.fuel.smeltCount",10000000);
        int stringWidth = fontRenderer.width(smeltCountText.getString());

        background = guiHelper.drawableBuilder(VeryAbstractFurnaceVariantCategory.RECIPE_GUI_VANILLA, 0, 134, 18, 34)
            .addPadding(0, 0, 0, stringWidth + 20)
            .build();

        flameTransparentBackground = ClientModEventSubscriber.textures.getFlameIcon();
	}

    public Component getSmeltCountText()
    {
        return smeltCountText;
    }

    public IDrawableAnimated getFlame()
    {
        return animatedFlame;
    }

    @Override
    public Component getTitle()
    {
        return localizedName;
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public IDrawable getIcon()
    {
        return flameTransparentBackground;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses)
    {
//        setRecipe(builder, recipe, focuses);
        this.fuelRecipe = recipe;
    }


    public Component createSmeltCountText(int burnTime)
    {
        if (burnTime == fuelRecipe.getSingleItemBurnTime())
        {
            return Component.translatable("gui.jei.category.fuel.smeltCount.single");
        } 
        else
        {
            NumberFormat numberInstance = NumberFormat.getNumberInstance();
            numberInstance.setMaximumFractionDigits(2);
            String smeltCount = numberInstance.format(burnTime / ((float) fuelRecipe.getSingleItemBurnTime()));
            return Component.translatable("gui.jei.category.fuel.smeltCount", smeltCount);
        }
    } // end createSmeltCountText()

} // end class
