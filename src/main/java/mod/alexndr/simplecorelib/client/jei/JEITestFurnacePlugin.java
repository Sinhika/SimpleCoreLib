package mod.alexndr.simplecorelib.client.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.client.gui.TestFurnaceScreen;
import mod.alexndr.simplecorelib.init.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JEITestFurnacePlugin implements IModPlugin
{
    ResourceLocation UUID = new ResourceLocation(SimpleCoreLib.MODID, "test_furnace_jei");

    // custom fuel test
    public static final RecipeType<TestFurnaceFuelRecipe> TEST_FUEL = RecipeType.create(SimpleCoreLib.MODID,
            "test_fuel", TestFurnaceFuelRecipe.class);

    @Override
    public @NotNull ResourceLocation getPluginUid()
    {
        return this.UUID;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.test_furnace.get()), RecipeTypes.SMELTING);
    }

    /**
     * Register the categories handled by this plugin.
     * These are registered before recipes so they can be checked for validity.
     *
     * @param registration
     */
    @Override public void registerCategories(IRecipeCategoryRegistration registration)
    {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registration.addRecipeCategories(new TestFuelCategory(guiHelper));
    }

    /**
     * Register modded recipes.
     *
     * @param registration
     */
    @Override public void registerRecipes(IRecipeRegistration registration)
    {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IIngredientManager ingredientManager = registration.getIngredientManager();
        registration.addRecipes(TEST_FUEL, TestFuelRecipeMaker.getFuelRecipes(ingredientManager, jeiHelpers));
        registration.addIngredientInfo(new ItemStack(ModBlocks.test_furnace.asItem()), VanillaTypes.ITEM_STACK,
                Component.translatable("simplecorelib.test_furnace.info"));
    }

    /**
     * Register various GUI-related things for your mod.
     * This includes adding clickable areas in your guis to open JEI,
     * and adding areas on the screen that JEI should avoid drawing.
     *
     * @param registration
     */
    @Override public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        registration.addRecipeClickArea(TestFurnaceScreen.class, 78, 32, 28, 23, RecipeTypes.SMELTING, TEST_FUEL);
    }
} // end class
