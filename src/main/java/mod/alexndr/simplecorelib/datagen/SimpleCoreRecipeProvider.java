package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.datagen.SimpleRecipeProvider;
import mod.alexndr.simplecorelib.init.ModBlocks;
import mod.alexndr.simplecorelib.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SimpleCoreRecipeProvider extends SimpleRecipeProvider
{

    public SimpleCoreRecipeProvider(PackOutput pOutput,
                                    CompletableFuture<HolderLookup.Provider> pRegistries)
    {
        super(pOutput, pRegistries, SimpleCoreLib.MODID);
    }

    @Override protected void buildRecipes(RecipeOutput pRecipeOutput)
    {
//        oreSmelting(pRecipeOutput, List.of(ModBlocks.original_copper_ore), RecipeCategory.BUILDING_BLOCKS,
//                Items.COPPER_INGOT, 5, 200, "copper_ingot");
        buildOre2IngotRecipes(pRecipeOutput, List.of(ModBlocks.original_copper_ore.get()), Items.COPPER_INGOT,
                0.5F, 200, "copper_ingot");
        buildVanillaRecyclingRecipes(pRecipeOutput, List.of(ModBlocks.test_furnace.get(), ModBlocks.test_plate.get(),
                ModBlocks.test_bars.get(), ModItems.test_shears.get()), ModBlocks.original_copper_ore.asItem(),
                0.6F, 200, "recycle test items");
    }
} // end class
