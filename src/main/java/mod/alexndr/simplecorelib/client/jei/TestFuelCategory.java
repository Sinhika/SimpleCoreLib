package mod.alexndr.simplecorelib.client.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.client.jei.VeryAbstractFurnaceVariantCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TestFuelCategory extends VeryAbstractFurnaceVariantCategory<TestFurnaceFuelRecipe>
{
    public static final ResourceLocation UID = new ResourceLocation(SimpleCoreLib.MODID, "test_fuel");

    public TestFuelCategory(IGuiHelper guiHelper)
    {
        super(guiHelper);
        localizedName = Component.translatable("gui.simplecorelib.category.fuel");
    }

    /**
     * @return the type of recipe that this category handles.
     * @since 9.5.0
     */
    @Override public RecipeType<TestFurnaceFuelRecipe> getRecipeType()
    {
        return JEITestFurnacePlugin.TEST_FUEL;
    }

    @Override public void setRecipe(IRecipeLayoutBuilder builder, TestFurnaceFuelRecipe recipe, IFocusGroup focuses)
    {
        super.setRecipe(builder, recipe, focuses);
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 17).addItemStacks(recipe.getInputs());
    }

    /**
     * Draw extras or additional info about the recipe.
     * Use the mouse position for things like button highlights.
     * Tooltips are handled by {@link #getTooltipStrings(Object, IRecipeSlotsView, double, double)}
     *
     * @param recipe          the current recipe being drawn.
     * @param recipeSlotsView a view of the current recipe slots being drawn.
     * @param guiGraphics     the current {@link GuiGraphics} for rendering.
     * @param mouseX          the X position of the mouse, relative to the recipe.
     * @param mouseY          the Y position of the mouse, relative to the recipe.
     * @see IGuiHelper for useful functions.
     * @see IRecipeSlotsView for information about the ingredients that are currently being drawn.
     * @since 9.3.0
     */
    @Override public void draw(TestFurnaceFuelRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics,
                               double mouseX, double mouseY)
    {
        IDrawableAnimated flame = this.getFlame();
        flame.draw(guiGraphics, 1, 0);
        drawCookTime(recipe, guiGraphics, 24, 13);
    }

    protected void drawCookTime(TestFurnaceFuelRecipe recipe, GuiGraphics guiGraphics, int x, int y)
    {
        this.smeltCountText = createSmeltCountText(recipe.getBurnTime());
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        int stringWidth = fontRenderer.width(smeltCountText);
        guiGraphics.drawString(fontRenderer, smeltCountText, getWidth() - stringWidth, y, 0xFF808080, false);
    }
} // end class
