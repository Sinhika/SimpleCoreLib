package mod.alexndr.simplecorelib.api.datagen;

import net.minecraft.advancements.Criterion;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ICondition;


/**
 * Stash functions to assemble standard recipe sets (tools, armors, storage) here.
 * Used by RecipeProviders.
 * 
 * @author Sinhika
 *
 */
public class RecipeSetBuilder extends AbstractRecipeSetBuilder
{
    public RecipeSetBuilder(String modid)
    {
        super(modid);
    }

     public void buildOre2IngotRecipes(RecipeOutput consumer, Ingredient oreIn, ItemLike ingotOut,
            Criterion<?> criterion, float experienceIn, int cookingTimeIn)
    {
        buildOre2IngotRecipes(consumer, oreIn, ingotOut, criterion, experienceIn, cookingTimeIn, null);
    }
    
    
    /**
     * build both the smelting and blasting recipes for a cook-type recipe.
     *  
     * @param consumer Used by a RecipeProvider to generate ore-to-ingot smelting and blasting recipes.
     * @param oreIn ore ingredient.
     * @param ingotOut ingot or gem created from smelting/blasting oreIn.
     * @param criterion required to get the recipe advancement; usually hasItem()
     * @param experienceIn  smelting/blasting xp.
     * @param cookingTimeIn smelting cook time. Blasting time is automatically 1/2 that.
     * @param suffix to be appended to recipe_name string.
     */
    public void buildOre2IngotRecipes(RecipeOutput consumer, Ingredient oreIn, ItemLike ingotOut,
                                      Criterion<?> criterion, float experienceIn, int cookingTimeIn, String suffix)
    {
        String recipe_name = ingotOut.asItem().toString() + "_from_smelting";
        if (suffix != null) {
            recipe_name = recipe_name.concat(suffix);
        }
        SimpleCookingRecipeBuilder.smelting(oreIn, RecipeCategory.MISC, ingotOut, experienceIn, cookingTimeIn)
            .unlockedBy(recipe_name, criterion)
            .save(consumer, make_resource(recipe_name));

       recipe_name = ingotOut.asItem().toString() + "_from_blasting";
       if (suffix != null) {
           recipe_name = recipe_name.concat(suffix);
       }
       SimpleCookingRecipeBuilder.blasting(oreIn, RecipeCategory.MISC, ingotOut, experienceIn, cookingTimeIn/2)
           .unlockedBy(recipe_name, criterion)
           .save(consumer, make_resource(recipe_name));
    }
    
    /**
     * Used by a RecipeProvider to generate vanilla recycling to nuggets of a list of items-as-ingredients.
     * 
     * @param consumer passed in from RecipeProvider to builder() call.
     * @param ingredients items that can be recycled to yield nugget.
     * @param nugget output item
     * @param criterion required to get the recipe advancement; usually hasItem()
     */
    public void buildVanillaRecyclingRecipes(RecipeOutput consumer, Ingredient ingredients, 
                                             ItemLike nugget, Criterion<?> criterion, 
                                             float experienceIn, int cookingTimeIn)
    {
        String recipe_name = nugget.asItem().toString() + "_from_smelting";
        SimpleCookingRecipeBuilder.smelting(ingredients, RecipeCategory.MISC, nugget, experienceIn, cookingTimeIn)
            .unlockedBy(recipe_name, criterion)
            .save(consumer, make_resource(recipe_name));
            
        recipe_name = nugget.asItem().toString() + "_from_blasting";
        SimpleCookingRecipeBuilder.blasting(ingredients, RecipeCategory.MISC, nugget, experienceIn, cookingTimeIn/2)
            .unlockedBy(recipe_name, criterion)
            .save(consumer, make_resource(recipe_name));
    } // end buildVanillaRecyclingRecipes
    
    /**
     * Used by a RecipeProvider to generate basic storage recipes for an ingot/block/nugget set.
     * These recipes are non-conditional.
     * 
     * @param consumer passed in from RecipeProvider to builder() call.
     * @param ingot item
     * @param block block
     * @param nugget item
     * @param criterion required to get the recipe advancement; usually hasItem()
     */
    public void buildSimpleStorageRecipes(RecipeOutput consumer, 
                                          ItemLike ingot, ItemLike block,
                                          ItemLike nugget, Criterion<?> criterion)
    {
        // block <=> ingots
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ingot.asItem(), 9)
            .requires(block.asItem())
            .unlockedBy("has_item", criterion)
            .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block.asItem())
            .define('S', ingot)
            .pattern("SSS")
            .pattern("SSS")
            .pattern("SSS")
            .unlockedBy("has_item", criterion)
            .save(consumer);
        
        // ingot <=> nuggets
        if (nugget != null)
        {
            String ingot_name = ingot.asItem().toString();
                    
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,nugget.asItem(), 9)
                .requires(ingot)
                .unlockedBy("has_item", criterion)
                .save(consumer);
            
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ingot)
                .define('S', nugget.asItem())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .unlockedBy("has_item", criterion)
                .save(consumer, make_resource(ingot_name + "_from_nuggets"));
        }
    } // end buildSimpleStorageRecipes
    
    
    public void buildChunkConversionRecipes(RecipeOutput consumer, ItemLike nugget,
                                            ItemLike medium_chunk, ItemLike large_chunk, 
                                            Criterion<?> criterion)
    {
        String nugget_name = nugget.asItem().toString();
        
        if (medium_chunk != null)
        {
            String mchunk_name = medium_chunk.asItem().toString();
            
            // nuggets to medium chunk,
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC,medium_chunk)
                .define('S', nugget.asItem())
                .pattern("SS")
                .pattern("SS")
                .unlockedBy("has_item", criterion)
                .save(consumer, make_resource(mchunk_name + "_from_nuggets"));
                
            // medium chunk to nuggets
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,nugget.asItem(), 4)
                .requires(medium_chunk)
                .unlockedBy("has_item", criterion)
                .save(consumer, make_resource(nugget_name + "_from_medium_chunk"));
        }
        if (large_chunk != null && medium_chunk != null)
        {
            String lchunk_name = large_chunk.asItem().toString();
            String mchunk_name = medium_chunk.asItem().toString();
            
            // large chunk to medium chunks
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,medium_chunk.asItem(), 2)
                .requires(large_chunk)
                .unlockedBy("has_item", criterion)
                .save(consumer, make_resource(mchunk_name + "_from_large_chunk"));
            
            // medium chunks + nugget to large chunk
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,large_chunk.asItem())
                .requires(medium_chunk, 2)
                .requires(nugget)
                .unlockedBy("has_item", criterion)
                .save(consumer, make_resource(lchunk_name + "_from_medium_chunks"));
            
            // nuggets & medium chunk to large chunk.
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,large_chunk.asItem())
                .requires(nugget, 5)
                .requires(medium_chunk)
                .unlockedBy("has_item", criterion)
                .save(consumer, make_resource(lchunk_name + "_from_nuggets"));
           
        } // end-if large_chunk && medium_chunk
        else if (large_chunk != null)
        {
            // large chunk to nuggets
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,nugget.asItem(), 8)
                .requires(large_chunk)
                .unlockedBy("has_item", criterion)
                .save(consumer, make_resource(nugget_name + "_from_large_chunk"));
        } // end else-if just large_chunk exists.
    } // end buildChunkConversionRecipes
    
    
    /**
     * Used by a RecipeProvider to generate rod and mod bow recipes (that follow the usual pattern), 
     * optionally with a condition.
     *
     */
    public void buildModBowRecipe(RecipeOutput consumer, ResourceLocation bow_name, Ingredient rod_material,
            Item rod, Ingredient keystone, Criterion<?> criterion)
    {
            Item bow = BuiltInRegistries.ITEM.get(bow_name);
            Ingredient string = Ingredient.of(Tags.Items.STRINGS);
                    
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC,rod)
                .define('S', rod_material)
                .pattern("S")
                .pattern("S")
                .unlockedBy("has_item", criterion)
                .save(consumer);
            
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, bow)
                .define('X', rod)
                .define('Y', string)
                .define('Z', keystone)
                .pattern(" XY")
                .pattern("Z Y")
                .pattern(" XY")
                .unlockedBy("has_item", criterion)
                .save(consumer);
    } // end buildModBowRecipe()
    
    
    /**
     * Used by a RecipeProvider to generate recipe sets for armor sets. Based heavily on
     * Botania's registerSimpleArmorSet() method.
     * 
     * @param consumer passed in from RecipeProvider to builder() call.
     * @param item what the armor is made from.
     * @param variant first part of armor piece name, like 'copper' or 'ubermetal'
     * @param criterion required to get the recipe advancement; usually hasItem()
     */
    public void buildSimpleArmorSet(RecipeOutput consumer, Ingredient item, 
                                    String variant, Criterion<?> criterion)
    {
        ResourceLocation helmet_name = make_resource(variant + "_helmet");
        ResourceLocation chestplate_name = make_resource(variant + "_chestplate");
        ResourceLocation leggings_name = make_resource(variant + "_leggings");
        ResourceLocation boots_name = make_resource(variant + "_boots");
        
        Item helmet = BuiltInRegistries.ITEM.get(helmet_name);
        Item chestplate = BuiltInRegistries.ITEM.get(chestplate_name);
        Item leggings = BuiltInRegistries.ITEM.get(leggings_name);
        Item boots = BuiltInRegistries.ITEM.get(boots_name);
    
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet)
            .define('S', item)
            .pattern("SSS")
            .pattern("S S")
            .pattern("   ")
            .unlockedBy("has_item", criterion)
            .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate)
            .define('S',item)
            .pattern("S S")
            .pattern("SSS")
            .pattern("SSS")
            .unlockedBy("has_item", criterion)
            .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings)
            .define('S', item)
            .pattern("SSS")
            .pattern("S S")
            .pattern("S S")
            .unlockedBy("has_item", criterion)
            .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots)
            .define('S', item)
            .pattern("   ")
            .pattern("S S")
            .pattern("S S")
            .unlockedBy("has_item", criterion)
            .save(consumer);
    } // end buildSimpleArmorSet()
    
    /**
     * Used by a RecipeProvider to generate recipe sets for aesthetic blocks.
     */
    public void buildSimpleAestheticBlocks(RecipeOutput consumer, Ingredient item,
    		String variant, Criterion<?> criterion)
    {
    	ResourceLocation bar_name = make_resource(variant + "_bars");
    	ResourceLocation bricks_name = make_resource(variant + "_bricks");
    	ResourceLocation brick_stairs_name = make_resource(variant + "_brick_stairs");
    	ResourceLocation door_name = make_resource(variant + "_door");
    	ResourceLocation brick_slab_name = make_resource(variant + "_brick_slab");

        DefaultedRegistry<Block> blockReg = BuiltInRegistries.BLOCK;
    	Block bar = blockReg.containsKey(bar_name) ? blockReg.get(bar_name) : null;
    	Block bricks = blockReg.containsKey(bricks_name) ? blockReg.get(bricks_name) : null;
    	Block brick_stairs = blockReg.containsKey(brick_stairs_name) ? blockReg.get(brick_stairs_name) : null;
    	Block door = blockReg.containsKey(door_name) ? blockReg.get(door_name) : null;
    	Block brick_slab = blockReg.containsKey(brick_slab_name) ? blockReg.get(brick_slab_name) : null;
    			
        // sword
        if (bar != null) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, bar, 16)
                    .define('S', item)
                    .pattern("SSS")
                    .pattern("SSS")
                    .unlockedBy("has_item", criterion)
                    .save(consumer, bar_name);
        }
        if (bricks != null) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, bricks)
                    .define('S', item)
                    .pattern("SS")
                    .pattern("SS")
                    .unlockedBy("has_item", criterion)
                    .save(consumer, bricks_name);
        }
        if (brick_stairs != null && bricks != null)  // we need bricks to build stairs. 
        {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, brick_stairs, 4)
                    .define('S', Ingredient.of(bricks.asItem()))
                    .pattern("S  ")
                    .pattern("SS ")
                    .pattern("SSS")
                    .unlockedBy("has_item", criterion)
                    .save(consumer, brick_stairs_name);

            // stonecutting recipe.
            SingleItemRecipeBuilder.stonecutting(
                    Ingredient.of(bricks.asItem()), RecipeCategory.BUILDING_BLOCKS, brick_stairs)
                    .unlockedBy("has_item", criterion)
                    .save(consumer, new ResourceLocation(brick_stairs_name + "_stonecutting"));
        }
        if (brick_slab != null && bricks != null) // we need bricks to have and make brick slabs. 
        {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, brick_slab, 6)
                    .define('S', Ingredient.of(bricks.asItem()))
                    .pattern("   ")
                    .pattern("   ")
                    .pattern("SSS")
                    .unlockedBy("has_item", criterion)
                    .save(consumer, brick_slab_name);

            // stone cutting recipes.
            SingleItemRecipeBuilder.stonecutting(
                            Ingredient.of(bricks.asItem()), RecipeCategory.BUILDING_BLOCKS, brick_slab, 2)
                    .unlockedBy("has_item", criterion)
                    .save(consumer, new ResourceLocation(brick_slab_name + "_stonecutting"));

        } // end-if brick_slab.
        
        if (door != null) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, door, 3)
                    .define('S', item)
                    .pattern(" SS")
                    .pattern(" SS")
                    .pattern(" SS")
                    .unlockedBy("has_item", criterion)
                    .save(consumer, door_name);
        }
   	
    }  // end buildSimpleAestheticBlocks()
    
    
    public void buildSimplePressurePlate(RecipeOutput consumer, Ingredient item,
            Block pp, Criterion<?> criterion)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pp.asItem())
            .define('S', item)
            .pattern("   ")
            .pattern("SS ")
            .pattern("   ")
            .unlockedBy("has_item", criterion)
            .save(consumer);
    }
    
    /**
     * Used by a RecipeProvider to generate recipe sets for tool sets. Based heavily on
     * Botania's registerToolSetRecipes() method.
     * 
     * @param consumer passed in from RecipeProvider to builder() call.
     * @param item what the tools are made from, besides sticks.
     * @param variant first part of the tool name, like 'copper' or 'ubermetal'
     * @param criterion required to get the recipe advancement; usually hasItem().
     * @param has_shears true if there is a shears for this variant, false if no shears.
     */
    public void buildSimpleToolSet(RecipeOutput consumer, Ingredient item,
            String variant, Criterion<?> criterion, boolean has_shears )
    {
        ResourceLocation sword_name = make_resource(variant + "_sword");
        ResourceLocation pickaxe_name = make_resource(variant + "_pickaxe");
        ResourceLocation axe_name = make_resource(variant + "_axe");
        ResourceLocation shovel_name = make_resource(variant + "_shovel");
        ResourceLocation hoe_name = make_resource(variant + "_hoe");
        ResourceLocation shears_name = has_shears ? make_resource(variant + "_shears") : null;
        
        Ingredient stick = Ingredient.of(Tags.Items.RODS_WOODEN);
        DefaultedRegistry<Item> itemReg = BuiltInRegistries.ITEM;
        
        Item sword = itemReg.containsKey(sword_name) ? itemReg.get(sword_name) : null;
        Item pickaxe = itemReg.containsKey(pickaxe_name) ? itemReg.get(pickaxe_name) : null;
        Item axe = itemReg.containsKey(axe_name) ? itemReg.get(axe_name) : null;
        Item shovel = itemReg.containsKey(shovel_name) ? itemReg.get(shovel_name) : null;
        Item hoe = itemReg.containsKey(hoe_name) ? itemReg.get(hoe_name) : null;
        Item shears = has_shears 
                ? (itemReg.containsKey(shears_name) ? itemReg.get(shears_name) : null )
                : null;

        // sword
        if (sword != null) {
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword)
                .define('S', item)
                .define('T', stick)
                .pattern(" S ")
                .pattern(" S ")
                .pattern(" T ")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        }

        // axe
        if (axe != null) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe)
                .define('S', item)
                .define('T', stick)
                .pattern("SS ")
                .pattern("ST ")
                .pattern(" T ")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        }

        // hoe
        if (hoe != null) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe)
                .define('S', item)
                .define('T', stick)
                .pattern("SS ")
                .pattern(" T ")
                .pattern(" T ")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        }

        // pickaxe
        if (pickaxe != null) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe)
                .define('S', item)
                .define('T', stick)
                .pattern("SSS")
                .pattern(" T ")
                .pattern(" T ")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        }

        // shovel
        if (shovel != null)
        {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel)
                .define('S', item)
                .define('T', stick)
                .pattern(" S ")
                .pattern(" T ")
                .pattern(" T ")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        }

        if (has_shears && shears != null)
        {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shears)
                .define('S', item)
                .pattern(" S")
                .pattern("S ")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        }
    } // end buildSimpleToolSet()
    
    
} // end class
