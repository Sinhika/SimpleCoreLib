package mod.alexndr.simplecorelib.api.datagen;

import mod.alexndr.simplecorelib.api.helpers.NameUtils;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class SimpleRecipeProvider extends RecipeProvider
{
    protected final String modid;

    public SimpleRecipeProvider(PackOutput pOutput,
                                CompletableFuture<HolderLookup.Provider> pRegistries,
                                String modid)
    {
        super(pOutput, pRegistries);
        this.modid = modid;
    }

    /**
     * build both the smelting and blasting recipes for a cook-type recipe.
     *
     * @param consumer Used by a RecipeProvider to generate ore-to-ingot smelting and blasting recipes.
     * @param pIngredients list of ore item ingredients.
     * @param ingotOut ingot or gem created from smelting/blasting oreIn.
     * @param experienceIn  smelting/blasting xp.
     * @param cookingTimeIn smelting cook time. Blasting time is automatically 1/2 that.
     * @param pGroup output group type string, e.g. 'copper_ingot', etc.
     */
    public void buildOre2IngotRecipes(RecipeOutput consumer,  List<ItemLike> pIngredients, ItemLike ingotOut,
                                      float experienceIn, int cookingTimeIn, String pGroup)
    {
        oreSmelting(consumer, pIngredients, RecipeCategory.MISC,
                ingotOut, experienceIn, cookingTimeIn, pGroup);
        oreBlasting(consumer, pIngredients, RecipeCategory.MISC, ingotOut, experienceIn, cookingTimeIn,
                pGroup);
    }


    /**
     * Used by a RecipeProvider to generate vanilla recycling to nuggets of a list of items-as-ingredients.
     *
     * @param consumer passed in from RecipeProvider to builder() call.
     * @param ingredients items that can be recycled to yield nugget.
     * @param nugget output item
     */
    public void buildVanillaRecyclingRecipes(RecipeOutput consumer,  List<ItemLike>  ingredients,
                                             ItemLike nugget, float experienceIn, int cookingTimeIn, String pGroup)
    {
        buildOre2IngotRecipes(consumer, ingredients, nugget, experienceIn, cookingTimeIn, pGroup);
    } // end buildVanillaRecyclingRecipes


    /**
     * Used by a RecipeProvider to generate basic storage recipes for an ingot/block/nugget set.
     * These recipes are non-conditional.
     *
     * @param consumer passed in from RecipeProvider to builder() call.
     * @param ingot item
     * @param block block
     * @param nugget item
     */
    public void buildSimpleStorageRecipes(RecipeOutput consumer,
                                          ItemLike ingot, ItemLike block,
                                          ItemLike nugget)
    {
        // block <=> ingots
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, ingot, RecipeCategory.BUILDING_BLOCKS, block);

        // ingot <=> nuggets
        if (nugget != null)
        {
            nineBlockStorageRecipes(consumer, RecipeCategory.MISC, nugget, RecipeCategory.MISC, ingot);
        }
    } // end buildSimpleStorageRecipes

    /**
     *
     * @param consumer  passed in from RecipeProvider
     * @param nugget
     * @param medium_chunk
     * @param large_chunk
     */
    public void buildChunkConversionRecipes(RecipeOutput consumer, ItemLike nugget,
                                            ItemLike medium_chunk, ItemLike large_chunk)
    {
        String nugget_name = getItemName(nugget);

        if (medium_chunk != null)
        {
            String mchunk_name = getItemName(medium_chunk);

            // nuggets to medium chunk,
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC,medium_chunk)
                    .define('S', nugget)
                    .pattern("SS")
                    .pattern("SS")
                    .unlockedBy("has_item", has(nugget))
                    .save(consumer, new ResourceLocation(mchunk_name+ "_from_nuggets"));

            // medium chunk to nuggets
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 4)
                    .requires(medium_chunk)
                    .unlockedBy("has_item", has(medium_chunk))
                    .save(consumer, make_resource(nugget_name + "_from_medium_chunk"));
        }
        if (large_chunk != null && medium_chunk != null)
        {
            String lchunk_name = NameUtils.fromItem(large_chunk.asItem()).getPath();
            String mchunk_name = NameUtils.fromItem(medium_chunk.asItem()).getPath();

            // large chunk to medium chunks
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,medium_chunk, 2)
                    .requires(large_chunk)
                    .unlockedBy("has_item", has(large_chunk))
                    .save(consumer, make_resource(mchunk_name + "_from_large_chunk"));

            // medium chunks + nugget to large chunk
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,large_chunk)
                    .requires(medium_chunk, 2)
                    .requires(nugget)
                    .unlockedBy("has_item", has(nugget))
                    .save(consumer, make_resource(lchunk_name + "_from_medium_chunks"));

            // nuggets & medium chunk to large chunk.
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,large_chunk)
                    .requires(nugget, 5)
                    .requires(medium_chunk)
                    .unlockedBy("has_item", has(medium_chunk))
                    .save(consumer, make_resource(lchunk_name + "_from_nuggets"));

        } // end-if large_chunk && medium_chunk
        else if (large_chunk != null)
        {
            // large chunk to nuggets
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,nugget, 8)
                    .requires(large_chunk)
                    .unlockedBy("has_item", has(large_chunk))
                    .save(consumer, make_resource(nugget_name + "_from_large_chunk"));
        } // end else-if just large_chunk exists.
    } // end buildChunkConversionRecipes


    public ResourceLocation make_resource(String path)
    {
        return new ResourceLocation(this.modid, path);
    }

} // end class
