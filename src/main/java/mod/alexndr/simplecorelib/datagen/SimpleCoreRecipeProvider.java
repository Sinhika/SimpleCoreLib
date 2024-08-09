package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.datagen.RecipeSetBuilder;
import mod.alexndr.simplecorelib.api.datagen.SimpleRecipeProvider;
import mod.alexndr.simplecorelib.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SimpleCoreRecipeProvider extends SimpleRecipeProvider
{

    public SimpleCoreRecipeProvider(PackOutput pOutput,
                                    CompletableFuture<HolderLookup.Provider> pRegistries)
    {
        super(pOutput, pRegistries);
    }

    @Override protected void buildRecipes(RecipeOutput pRecipeOutput)
    {
        oreSmelting(pRecipeOutput, List.of(ModBlocks.original_copper_ore), RecipeCategory.BUILDING_BLOCKS,
                Items.COPPER_INGOT, 5, 200, "copper_ingot");
    }
} // end class
