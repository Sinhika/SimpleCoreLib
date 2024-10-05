package mod.alexndr.simplecorelib.client.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class TestFuelRecipeMaker
{
    private static final Logger LOGGER = LogManager.getLogger();

    private TestFuelRecipeMaker() {}

    public static List<TestFurnaceFuelRecipe> getFuelRecipes(IIngredientManager ingredientManager, IJeiHelpers helpers)
    {
        Collection<ItemStack> allItemStacks = ingredientManager.getAllIngredients(VanillaTypes.ITEM_STACK);
        List<TestFurnaceFuelRecipe> fuelRecipes = new ArrayList<>();
        for (ItemStack stack : allItemStacks)
        {
            int burnTime = getBurnTime(stack);
            if (burnTime > 0) {
                fuelRecipes.add(new TestFurnaceFuelRecipe(Collections.singleton(stack), burnTime));
            }
        }
        return fuelRecipes;
    } // end getFuelRecipes()

    private static int getBurnTime(ItemStack itemStack)
    {
        try {
            return itemStack.getBurnTime(RecipeType.SMELTING);
        }
        catch (RuntimeException | LinkageError e)
        {
            String itemStackInfo = itemStack.toString();
            LOGGER.error("Failed to check if item is fuel {}.", itemStackInfo, e);
            return 0;
        }
    }
} // end class
