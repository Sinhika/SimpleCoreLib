package mod.alexndr.simplecorelib.recipes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Recipe class for Silent's Mechanisms Alloy Smetler. Code borrowed and modified
 * from Silent's Mechanisms
 * (https://github.com/SilentChaos512/Silents-Mechanisms), which is under MIT
 * License. This class exists only for the purpose of data generation, and should
 * only be invoked during datagen, lest it conflict with the one in Silent's Mechanisms.
 * 
 * @author SilentChaos512, Sinhika.
 *
 */
public class AlloySmeltingRecipe implements IRecipe<IInventory>
{
    private static final int INPUT_SLOT_COUNT = 4;
    
    protected final String modid;

    // as this is forced to load AFTER silents_mechanisms, and before any SimpleOres
    // add-ons
    // that might use recipes, it's safe to say that the recipe type should be
    // registered
    // before it is used, and we do not need deferred registration.
    public static ResourceLocation ALLOY_SMELTING = new ResourceLocation("silents_mechanisms", "alloy_smelting");

    public static final IRecipeType<AlloySmeltingRecipe> RECIPE_TYPE = new IRecipeType<AlloySmeltingRecipe>()
    {
        @Override
        public String toString()
        {
            return ALLOY_SMELTING.toString();
        }
    };

    // as this is forced to load AFTER silents_mechanisms, and before any SimpleOres
    // add-ons
    // that might use recipes, it's safe to say that the recipe type should be
    // registered
    // before it is used, and we do not need deferred registration.
    public static final Serializer SERIALIZER = new Serializer();

    private final ResourceLocation recipeId;
    private int processTime;
    private final Map<Ingredient, Integer> ingredients = new LinkedHashMap<>();
    private ItemStack result;

    public AlloySmeltingRecipe(ResourceLocation recipeId)
    {
        this.modid = recipeId.getNamespace();
        this.recipeId = recipeId;
    }

    public int getProcessTime()
    {
        return processTime;
    }

    public Map<Ingredient, Integer> getIngredientMap()
    {
        return ImmutableMap.copyOf(ingredients);
    }

    @Override
    public boolean matches(IInventory inv, World worldIn)
    {
        for (Ingredient ingredient : ingredients.keySet())
        {
            int required = ingredients.get(ingredient);
            int found = getTotalCount(inv, ingredient);
            if (found < required)
            {
                return false;
            }
        } // end-for

        // Check for non-matching items
        for (int i = 0; i < INPUT_SLOT_COUNT; ++i)
        {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty())
            {
                boolean foundMatch = false;
                for (Ingredient ingredient : ingredients.keySet())
                {
                    if (ingredient.test(stack))
                    {
                        foundMatch = true;
                        break;
                    }
                }
                if (!foundMatch)
                {
                    return false;
                }
            } // end-if !stack empty
        } // end-for
        return true;
    } // end matches()

    @Override
    public ItemStack getCraftingResult(IInventory inv)
    {
        // DO NOT USE
        return result.copy();
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        // DO NOT USE
        return result;
    }

    @Override
    public ResourceLocation getId()
    {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType()
    {
        return RECIPE_TYPE;
    }

    
    @Override
    public boolean isDynamic()
    {
        return true;
    }

    /**
     * Gets the total number of matching items in all slots in the inventory.
     *
     * @param inventory  The inventory
     * @param ingredient The items to match ({@link net.minecraft.item.crafting.Ingredient}, etc.)
     * @return The number of items in all matching item stacks
     */
    public static int getTotalCount(IInventory inventory, Predicate<ItemStack> ingredient) {
        int total = 0;
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty() && ingredient.test(stack)) {
                total += stack.getCount();
            }
        }
        return total;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AlloySmeltingRecipe> 
    {

        @Override
        public AlloySmeltingRecipe read(ResourceLocation recipeId, JsonObject json)
        {
            AlloySmeltingRecipe recipe = new AlloySmeltingRecipe(recipeId);
            recipe.processTime = JSONUtils.getInt(json, "process_time", 400);
            recipe.result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));

            JSONUtils.getJsonArray(json, "ingredients").forEach(element -> {
                Ingredient ingredient = deserializeIngredient(element);
                int count = JSONUtils.getInt(element.getAsJsonObject(), "count", 1);
                recipe.ingredients.put(ingredient, count);
            });

            return recipe;
        }

        private static Ingredient deserializeIngredient(JsonElement element)
        {
            if (element.isJsonObject()) {
                JsonObject json = element.getAsJsonObject();
                if (json.has("value"))
                    return Ingredient.deserialize(json.get("value"));
                if (json.has("values"))
                    return Ingredient.deserialize(json.get("values"));
            }
            return Ingredient.deserialize(element);
        }

        @Override
        public AlloySmeltingRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
        {
            AlloySmeltingRecipe recipe = new AlloySmeltingRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.result = buffer.readItemStack();

            int ingredientCount = buffer.readByte();
            for (int i = 0; i < ingredientCount; ++i) {
                Ingredient ingredient = Ingredient.read(buffer);
                int count = buffer.readByte();
                recipe.ingredients.put(ingredient, count);
            }

            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, AlloySmeltingRecipe recipe)
        {
            buffer.writeVarInt(recipe.processTime);
            buffer.writeItemStack(recipe.result);

            buffer.writeByte(recipe.ingredients.size());
            recipe.ingredients.forEach((ingredient, count) -> {
                ingredient.write(buffer);
                buffer.writeByte(count);
            });
        }
    } // end-class Serializer
    
} // end-class
