package mod.alexndr.simplecorelib.datagen;

import java.util.function.Consumer;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

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

    public ConditionalAdvancement.Builder build_advancement_with_condition(ResourceLocation recipe_id, ICondition condition,
                                                                            CriterionTriggerInstance criterion)
    {
        return ConditionalAdvancement.builder()
                .addCondition(condition)
                .addAdvancement(
                        Advancement.Builder.advancement()
                            .parent(new ResourceLocation("minecraft", "recipes/root"))
                            .rewards(AdvancementRewards.Builder.recipe(recipe_id))
                            .addCriterion("has_item", criterion));
    }
  
    
     public void buildOre2IngotRecipes(Consumer<FinishedRecipe> consumer, Ingredient oreIn, ItemLike ingotOut,
            CriterionTriggerInstance criterion, float experienceIn, int cookingTimeIn)
    {
        buildOre2IngotRecipes(consumer, oreIn, ingotOut, criterion, experienceIn, cookingTimeIn, null);
    }
    
    
    /**
     * build both the smelting and blasting recipes for a cook-type recipe.
     *  
     * @param consumer Used by a RecipeProvider to generate ore-to-ingot smelting and blasting recipes.
     * 
     * @param consumer passed in from RecipeProvider to builder() call.
     * @param oreIn ore ingredient.
     * @param ingotOut ingot or gem created from smelting/blasting oreIn.
     * @param criterion required to get the recipe advancement; usually hasItem()
     * @param experienceIn  smelting/blasting xp.
     * @param cookingTimeIn smelting cook time. Blasting time is automatically 1/2 that.
     * @param suffix to be appended to recipe_name string.
     */
    public void buildOre2IngotRecipes(Consumer<FinishedRecipe> consumer, Ingredient oreIn, ItemLike ingotOut,
            CriterionTriggerInstance criterion, float experienceIn, int cookingTimeIn, String suffix)
    {
        String recipe_name = ingotOut.asItem().toString() + "_from_smelting";
        if (suffix != null) {
            recipe_name = recipe_name.concat(suffix);
        }
        SimpleCookingRecipeBuilder.smelting(oreIn, ingotOut, experienceIn, cookingTimeIn)
            .unlockedBy(recipe_name, criterion)
            .save(consumer, make_resource(recipe_name));

       recipe_name = ingotOut.asItem().toString() + "_from_blasting";
       if (suffix != null) {
           recipe_name = recipe_name.concat(suffix);
       }
       SimpleCookingRecipeBuilder.blasting(oreIn, ingotOut, experienceIn, cookingTimeIn/2)
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
    public void buildVanillaRecyclingRecipes(Consumer<FinishedRecipe> consumer, Ingredient ingredients, 
                                             ItemLike nugget, CriterionTriggerInstance criterion, 
                                             float experienceIn, int cookingTimeIn)
    {
        String recipe_name = nugget.asItem().toString() + "_from_smelting";
        SimpleCookingRecipeBuilder.smelting(ingredients, nugget, experienceIn, cookingTimeIn)
            .unlockedBy(recipe_name, criterion)
            .save(consumer, make_resource(recipe_name));
            
        recipe_name = nugget.asItem().toString() + "_from_blasting";
        SimpleCookingRecipeBuilder.blasting(ingredients, nugget, experienceIn, cookingTimeIn/2)
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
    public void buildSimpleStorageRecipes(Consumer<FinishedRecipe> consumer, 
                                          ItemLike ingot, ItemLike block,
                                          ItemLike nugget, CriterionTriggerInstance criterion)
    {
        // block <=> ingots
        ShapelessRecipeBuilder.shapeless(ingot.asItem(), 9)
            .requires(block.asItem())
            .unlockedBy("has_item", criterion)
            .save(consumer);
        ShapedRecipeBuilder.shaped(block.asItem())
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
                    
            ShapelessRecipeBuilder.shapeless(nugget.asItem(), 9)
                .requires(ingot)
                .unlockedBy("has_item", criterion)
                .save(consumer);
            
            ShapedRecipeBuilder.shaped(ingot)
                .define('S', nugget.asItem())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .unlockedBy("has_item", criterion)
                .save(consumer, make_resource(ingot_name + "_from_nuggets"));
        }
    } // end buildSimpleStorageRecipes
    
    
    public void buildChunkConversionRecipes(Consumer<FinishedRecipe> consumer, ItemLike nugget,
                                            ItemLike medium_chunk, ItemLike large_chunk, 
                                            CriterionTriggerInstance criterion)
    {
        String nugget_name = nugget.asItem().toString();
        
        if (medium_chunk != null)
        {
            String mchunk_name = medium_chunk.asItem().toString();
            
            // nuggets to medium chunk,
            ShapedRecipeBuilder.shaped(medium_chunk)
                .define('S', nugget.asItem())
                .pattern("SS")
                .pattern("SS")
                .unlockedBy("has_item", criterion)
                .save(consumer, make_resource(mchunk_name + "_from_nuggets"));
                
            // medium chunk to nuggets
            ShapelessRecipeBuilder.shapeless(nugget.asItem(), 4)
                .requires(medium_chunk)
                .unlockedBy("has_item", criterion)
                .save(consumer, make_resource(nugget_name + "_from_medium_chunk"));
        }
        if (large_chunk != null && medium_chunk != null)
        {
            String lchunk_name = large_chunk.asItem().toString();
            String mchunk_name = medium_chunk.asItem().toString();
            
            // large chunk to medium chunks
            ShapelessRecipeBuilder.shapeless(medium_chunk.asItem(), 2)
                .requires(large_chunk)
                .unlockedBy("has_item", criterion)
                .save(consumer, make_resource(mchunk_name + "_from_large_chunk"));
            
            // medium chunks + nugget to large chunk
            ShapelessRecipeBuilder.shapeless(large_chunk.asItem())
                .requires(medium_chunk, 2)
                .requires(nugget)
                .unlockedBy("has_item", criterion)
                .save(consumer, make_resource(lchunk_name + "_from_medium_chunks"));
            
            // nuggets & medium chunk to large chunk.
            ShapelessRecipeBuilder.shapeless(large_chunk.asItem())
                .requires(nugget, 5)
                .requires(medium_chunk)
                .unlockedBy("has_item", criterion)
                .save(consumer, make_resource(lchunk_name + "_from_nuggets"));
           
        } // end-if large_chunk && medium_chunk
        else if (large_chunk != null)
        {
            // large chunk to nuggets
            ShapelessRecipeBuilder.shapeless(nugget.asItem(), 8)
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
    public void buildModBowRecipe(Consumer<FinishedRecipe> consumer, ResourceLocation bow_name, Ingredient rod_material,
            Item rod, Ingredient keystone, CriterionTriggerInstance criterion, ICondition condition)
    {
            Item bow = ForgeRegistries.ITEMS.getValue(bow_name);
            Ingredient string = Ingredient.of(Tags.Items.STRING);
                    
            ShapedRecipeBuilder.shaped(rod)
                .define('S', rod_material)
                .pattern("S")
                .pattern("S")
                .unlockedBy("has_item", criterion)
                .save(consumer);
            
            if (condition==null)
            {
                ShapedRecipeBuilder.shaped(bow)
                    .define('X', rod)
                    .define('Y', string)
                    .define('Z', keystone)
                    .pattern(" XY")
                    .pattern("Z Y")
                    .pattern(" XY")
                    .unlockedBy("has_item", criterion)
                    .save(consumer);
            } // end-if no condition
            else {
                ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(ShapedRecipeBuilder.shaped(bow)
                    .define('X', rod)
                    .define('Y', string)
                    .define('Z', keystone)
                    .pattern(" XY")
                    .pattern("Z Y")
                    .pattern(" XY")
                    .unlockedBy("has_item", criterion)               
                    ::save)
                .setAdvancement(bow_name, build_advancement_with_condition(bow_name, condition, criterion))
                .build(consumer, bow_name);
            } // end-else condition
    } // end buildModBowRecipe()
    
    
    /**
     * Used by a RecipeProvider to generate recipe sets for armor sets. Based heavily on
     * Botania's registerSimpleArmorSet() method.
     * 
     * @param consumer passed in from RecipeProvider to builder() call.
     * @param item what the armor is made from.
     * @param variant first part of armor piece name, like 'copper' or 'ubermetal'
     * @param criterion required to get the recipe advancement; usually hasItem()
     * @param condition null for no conditions, ICondition object for a conditional recipe.
     */
    public void buildSimpleArmorSet(Consumer<FinishedRecipe> consumer, Ingredient item, 
                                    String variant, CriterionTriggerInstance criterion, ICondition condition)
    {
        ResourceLocation helmet_name = make_resource(variant + "_helmet");
        ResourceLocation chestplate_name = make_resource(variant + "_chestplate");
        ResourceLocation leggings_name = make_resource(variant + "_leggings");
        ResourceLocation boots_name = make_resource(variant + "_boots");
        
        Item helmet = ForgeRegistries.ITEMS.getValue(helmet_name);
        Item chestplate = ForgeRegistries.ITEMS.getValue(chestplate_name);
        Item leggings = ForgeRegistries.ITEMS.getValue(leggings_name);
        Item boots = ForgeRegistries.ITEMS.getValue(boots_name);
    
        if (condition==null) 
        {
            ShapedRecipeBuilder.shaped(helmet)
                .define('S', item)
                .pattern("SSS")
                .pattern("S S")
                .pattern("   ")
                .unlockedBy("has_item", criterion)
                .save(consumer);
            ShapedRecipeBuilder.shaped(chestplate)
                .define('S',item)
                .pattern("S S")
                .pattern("SSS")
                .pattern("SSS")
                .unlockedBy("has_item", criterion)
                .save(consumer);
            ShapedRecipeBuilder.shaped(leggings)
                .define('S', item)
                .pattern("SSS")
                .pattern("S S")
                .pattern("S S")
                .unlockedBy("has_item", criterion)
                .save(consumer);
            ShapedRecipeBuilder.shaped(boots)
                .define('S', item)
                .pattern("   ")
                .pattern("S S")
                .pattern("S S")
                .unlockedBy("has_item", criterion)
                .save(consumer);
        } // end condition null
        else 
        {
            ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(ShapedRecipeBuilder.shaped(helmet)
                        .define('S', item)
                        .pattern("SSS")
                        .pattern("S S")
                        .pattern("   ")
                        .unlockedBy("has_item", criterion)
                        ::save)
                .setAdvancement(helmet_name, build_advancement_with_condition(helmet_name, condition, criterion))
                .build(consumer, helmet_name);
            
            ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(ShapedRecipeBuilder.shaped(chestplate)
                        .define('S',item)
                        .pattern("S S")
                        .pattern("SSS")
                        .pattern("SSS")
                        .unlockedBy("has_item", criterion)
                        ::save)
                .setAdvancement(chestplate_name, build_advancement_with_condition(chestplate_name, condition, criterion))
                .build(consumer, chestplate_name);
            ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(ShapedRecipeBuilder.shaped(leggings)
                        .define('S', item)
                        .pattern("SSS")
                        .pattern("S S")
                        .pattern("S S")
                        .unlockedBy("has_item", criterion)
                        ::save)
                .setAdvancement(leggings_name, build_advancement_with_condition(leggings_name, condition, criterion))
                .build(consumer, leggings_name);
            ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(ShapedRecipeBuilder.shaped(boots)
                        .define('S', item)
                        .pattern("   ")
                        .pattern("S S")
                        .pattern("S S")
                        .unlockedBy("has_item", criterion)
                        ::save)
                .setAdvancement(boots_name, build_advancement_with_condition(boots_name, condition, criterion))
                .build(consumer, boots_name);
        } // else has condition
    } // end buildSimpleArmorSet()
    
    /**
     * Used by a RecipeProvider to generate recipe sets for aesthetic blocks.
     */
    public void buildSimpleAestheticBlocks(Consumer<FinishedRecipe> consumer, Ingredient item,
    		String variant, CriterionTriggerInstance criterion, ICondition condition)
    {
    	ResourceLocation bar_name = make_resource(variant + "_bars");
    	ResourceLocation bricks_name = make_resource(variant + "_bricks");
    	ResourceLocation brick_stairs_name = make_resource(variant + "_brick_stairs");
    	ResourceLocation door_name = make_resource(variant + "_door");
    	ResourceLocation brick_slab_name = make_resource(variant + "brick_slab");
    	
    	IForgeRegistry<Block> blockReg = ForgeRegistries.BLOCKS;
    	Block bar = blockReg.containsKey(bar_name) ? blockReg.getValue(bar_name) : null;
    	Block bricks = blockReg.containsKey(bricks_name) ? blockReg.getValue(bricks_name) : null;
    	Block brick_stairs = blockReg.containsKey(brick_stairs_name) ? blockReg.getValue(brick_stairs_name) : null;
    	Block door = blockReg.containsKey(door_name) ? blockReg.getValue(door_name) : null;
    	Block brick_slab = blockReg.containsKey(brick_slab_name) ? blockReg.getValue(brick_slab_name) : null;
    			
        // sword
        if (bar != null) {
            ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(
                    ShapedRecipeBuilder.shaped(bar, 16)
                        .define('S', item)
                        .pattern("SSS")
                        .pattern("SSS")
                        .unlockedBy("has_item", criterion)
                        ::save)
                .setAdvancement(bar_name, build_advancement_with_condition(bar_name, condition, criterion))
                .build(consumer, bar_name);
        }
        if (bricks != null) {
            ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(
                    ShapedRecipeBuilder.shaped(bricks)
                        .define('S', item)
                        .pattern("SS")
                        .pattern("SS")
                        .unlockedBy("has_item", criterion)
                        ::save)
                .setAdvancement(bricks_name, build_advancement_with_condition(bricks_name, condition, criterion))
                .build(consumer, bricks_name);
        }
        if (brick_stairs != null && bricks != null)  // we need bricks to build stairs. 
        {
            ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(
                    ShapedRecipeBuilder.shaped(brick_stairs, 4)
                        .define('S', Ingredient.of(bricks.asItem()))
                        .pattern("S  ")
                        .pattern("SS ")
                        .pattern("SSS")
                        .unlockedBy("has_item", criterion)
                        ::save)
                .setAdvancement(brick_stairs_name, build_advancement_with_condition(brick_stairs_name, condition, criterion))
                .build(consumer, brick_stairs_name);
            
            // stonecutting recipe.
            ConditionalRecipe.builder().addCondition(condition)
            .addRecipe(
            		SingleItemRecipeBuilder.stonecutting(Ingredient.of(bricks.asItem()), brick_stairs)
                    .unlockedBy("has_item", criterion)
                    ::save)
            .build(consumer, brick_stairs_name);
            
        }
        if (brick_slab != null && bricks != null) // we need bricks to have and make brick slabs. 
        {
            ConditionalRecipe.builder().addCondition(condition)
            .addRecipe(
                ShapedRecipeBuilder.shaped(brick_slab, 6)
                    .define('S', Ingredient.of(bricks.asItem()))
                    .pattern("   ")
                    .pattern("   ")
                    .pattern("SSS")
                    .unlockedBy("has_item", criterion)
                    ::save)
            .setAdvancement(brick_slab_name, build_advancement_with_condition(brick_slab_name, condition, criterion))
            .build(consumer, brick_slab_name);
        	
            // stone cutting recipes.
            ConditionalRecipe.builder().addCondition(condition)
            .addRecipe(
            		SingleItemRecipeBuilder.stonecutting(Ingredient.of(bricks.asItem()), brick_slab, 2)
                    .unlockedBy("has_item", criterion)
                    ::save)
            .build(consumer, brick_slab_name);
        } // end-if brick_slab.
        
        if (door != null) {
            ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(
                    ShapedRecipeBuilder.shaped(door, 3)
                        .define('S', item)
                        .pattern(" SS")
                        .pattern(" SS")
                        .pattern(" SS")
                        .unlockedBy("has_item", criterion)
                        ::save)
                .setAdvancement(door_name, build_advancement_with_condition(door_name, condition, criterion))
                .build(consumer, door_name);
        }
   	
    }  // end buildSimpleAestheticBlocks()
    
    /**
     * Used by a RecipeProvider to generate recipe sets for tool sets. Based heavily on
     * Botania's registerToolSetRecipes() method.
     * 
     * @param consumer passed in from RecipeProvider to builder() call.
     * @param item what the tools are made from, besides sticks.
     * @param variant first part of the tool name, like 'copper' or 'ubermetal'
     * @param criterion required to get the recipe advancement; usually hasItem().
     * @param condition null for no conditions, ICondition object for a conditional recipe.
     * @param has_shears true if there is a shears for this variant, false if no shears.
     */
    public void buildSimpleToolSet(Consumer<FinishedRecipe> consumer, Ingredient item,
            String variant, CriterionTriggerInstance criterion, ICondition condition, boolean has_shears )
    {
        ResourceLocation sword_name = make_resource(variant + "_sword");
        ResourceLocation pickaxe_name = make_resource(variant + "_pickaxe");
        ResourceLocation axe_name = make_resource(variant + "_axe");
        ResourceLocation shovel_name = make_resource(variant + "_shovel");
        ResourceLocation hoe_name = make_resource(variant + "_hoe");
        ResourceLocation shears_name = has_shears ? make_resource(variant + "_shears") : null;
        
        Ingredient stick = Ingredient.of(Tags.Items.RODS_WOODEN);
        IForgeRegistry<Item> itemReg = ForgeRegistries.ITEMS;
        
        Item sword = itemReg.containsKey(sword_name) ? itemReg.getValue(sword_name) : null;
        Item pickaxe = itemReg.containsKey(pickaxe_name) ? itemReg.getValue(pickaxe_name) : null;
        Item axe = itemReg.containsKey(axe_name) ? itemReg.getValue(axe_name) : null;
        Item shovel = itemReg.containsKey(shovel_name) ? itemReg.getValue(shovel_name) : null;
        Item hoe = itemReg.containsKey(hoe_name) ? itemReg.getValue(hoe_name) : null;
        Item shears = has_shears 
                ? (itemReg.containsKey(shears_name) ? itemReg.getValue(shears_name) : null )
                : null;

        if (condition==null) 
        {
            // sword
            if (sword != null) {
                ShapedRecipeBuilder.shaped(sword)
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
                ShapedRecipeBuilder.shaped(axe)
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
                ShapedRecipeBuilder.shaped(hoe)
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
                ShapedRecipeBuilder.shaped(pickaxe)
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
                ShapedRecipeBuilder.shaped(shovel)
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
                ShapedRecipeBuilder.shaped(shears)
                    .define('S', item)
                    .pattern(" S")
                    .pattern("S ")
                    .unlockedBy("has_item", criterion)
                    .save(consumer);
            }
        } // end condition null
        else 
        {
            // sword
            if (sword != null) {
                ConditionalRecipe.builder().addCondition(condition)
                    .addRecipe(
                        ShapedRecipeBuilder.shaped(sword)
                            .define('S', item)
                            .define('T', stick)
                            .pattern(" S ")
                            .pattern(" S ")
                            .pattern(" T ")
                            .unlockedBy("has_item", criterion)
                            ::save)
                    .setAdvancement(sword_name, build_advancement_with_condition(sword_name, condition, criterion))
                    .build(consumer, sword_name);
            }
            
            // pickaxe
            if (pickaxe != null) {
                ConditionalRecipe.builder().addCondition(condition)
                    .addRecipe(
                        ShapedRecipeBuilder.shaped(pickaxe)
                            .define('S', item)
                            .define('T', stick)
                            .pattern("SSS")
                            .pattern(" T ")
                            .pattern(" T ")
                            .unlockedBy("has_item", criterion)
                            ::save)
                    .setAdvancement(pickaxe_name, build_advancement_with_condition(pickaxe_name, condition, criterion))
                    .build(consumer, pickaxe_name);
            }
            
            // axe
            if (axe != null) {
                ConditionalRecipe.builder().addCondition(condition)
                    .addRecipe(
                        ShapedRecipeBuilder.shaped(axe)
                            .define('S', item)
                            .define('T', stick)
                            .pattern("SS ")
                            .pattern("ST ")
                            .pattern(" T ")
                            .unlockedBy("has_item", criterion)
                            ::save)
                   .setAdvancement(axe_name, build_advancement_with_condition(axe_name, condition, criterion))
                   .build(consumer, axe_name);
            }
            // shovel
            if (shovel != null) {
                ConditionalRecipe.builder().addCondition(condition)
                    .addRecipe(
                        ShapedRecipeBuilder.shaped(shovel)
                            .define('S', item)
                            .define('T', stick)
                            .pattern(" S ")
                            .pattern(" T ")
                            .pattern(" T ")
                            .unlockedBy("has_item", criterion)
                            ::save)
                    .setAdvancement(shovel_name, build_advancement_with_condition(shovel_name, condition, criterion))
                    .build(consumer, shovel_name);
            }
            // hoe
            if (hoe != null) {
                ConditionalRecipe.builder().addCondition(condition)
                    .addRecipe(
                        ShapedRecipeBuilder.shaped(hoe)
                            .define('S', item)
                            .define('T', stick)
                            .pattern("SS ")
                            .pattern(" T ")
                            .pattern(" T ")
                            .unlockedBy("has_item", criterion)
                            ::save)
                    .setAdvancement(hoe_name, build_advancement_with_condition(hoe_name, condition, criterion))
                    .build(consumer, hoe_name);
            }
            // shears
            if (has_shears && shears != null) {
                ConditionalRecipe.builder().addCondition(condition)
                    .addRecipe(
                            ShapedRecipeBuilder.shaped(shears)
                            .define('S', item)
                            .pattern(" S")
                            .pattern("S ")
                            .unlockedBy("has_item", criterion)
                            ::save)
                    .setAdvancement(shears_name, build_advancement_with_condition(shears_name, condition, criterion))
                    .build(consumer, shears_name);
            }
        } // else has condition
    } // end buildSimpleToolSet()
    
} // end class
