package mod.alexndr.simplecorelib.datagen;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import mod.alexndr.simplecorelib.helpers.NameUtils;
import mod.alexndr.simplecorelib.recipes.CrushingRecipe;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

/**
 * Recipe builder for Silent's Mechanisms crusher. Code borrowed and modified
 * from Silent's Mechanisms
 * (https://github.com/SilentChaos512/Silents-Mechanisms), which is under MIT
 * License.
 * 
 * @author SilentChaos512, Sinhika.
 *
 */
@Deprecated
public final class CrushingRecipeBuilder extends AbstractRecipeSetBuilder
{
    private final Map<ItemStack, Float> results = new LinkedHashMap<>();
    private final Ingredient ingredient;
    private final int processTime;

    private CrushingRecipeBuilder(String modid, Ingredient ingredient, int processTime)
    {
        super(modid);
        this.ingredient = ingredient;
        this.processTime = processTime;
    }

    public static CrushingRecipeBuilder builder(String modid, IItemProvider ingredient, int processTime)
    {
        return builder(modid, Ingredient.fromItems(ingredient), processTime);
    }

    public static CrushingRecipeBuilder builder(String modid, ITag.INamedTag<Item> ingredient, int processTime)
    {
        return builder(modid, Ingredient.fromTag(ingredient), processTime);
    }

    public static CrushingRecipeBuilder builder(String modid, Ingredient ingredient, int processTime)
    {
        return new CrushingRecipeBuilder(modid, ingredient, processTime);
    }

    public static CrushingRecipeBuilder crushingChunks(String modid, IItemProvider chunks, IItemProvider dust,
            int processTime, float extraChance)
    {
        return builder(modid, chunks, processTime).result(dust, 1).result(dust, 1, extraChance);
    }
    
    public static CrushingRecipeBuilder crushingChunks(String modid, ITag.INamedTag<Item> chunks, IItemProvider dust,
            int processTime, float extraChance)
    {
        return builder(modid, chunks, processTime).result(dust, 1).result(dust, 1, extraChance);
    }


    public static CrushingRecipeBuilder crushingIngot(String modid, ITag.INamedTag<Item> ingot, IItemProvider dust,
            int processTime)
    {
        return builder(modid, ingot, processTime).result(dust, 1);
    }

    public static CrushingRecipeBuilder crushingIngot(String modid, IItemProvider ingot, IItemProvider dust,
            int processTime)
    {
        return builder(modid, ingot, processTime).result(dust, 1);
    }

    public static CrushingRecipeBuilder crushingOre(String modid, ITag.INamedTag<Item> ore, IItemProvider chunks, int processTime,
            @Nullable IItemProvider extra, float extraChance)
    {
        CrushingRecipeBuilder builder = builder(modid, ore, processTime);
        builder.result(chunks, 2);
        if (extra != null)
        {
            builder.result(extra, 1, extraChance);
        }
        return builder;
    }

    public static CrushingRecipeBuilder crushingOre(String modid, IItemProvider ore, IItemProvider chunks, int processTime,
            @Nullable IItemProvider extra, float extraChance)
    {
        CrushingRecipeBuilder builder = builder(modid, ore, processTime);
        builder.result(chunks, 2);
        if (extra != null)
        {
            builder.result(extra, 1, extraChance);
        }
        return builder;
    }
    
    public CrushingRecipeBuilder result(IItemProvider item, int count, float chance)
    {
        results.put(new ItemStack(item, count), chance);
        return this;
    }

    public CrushingRecipeBuilder result(IItemProvider item, int count)
    {
        return result(item, count, 1f);
    }

    public void build(Consumer<IFinishedRecipe> consumer)
    {
        ResourceLocation resultId = NameUtils.fromItem(results.keySet().iterator().next());
        ResourceLocation id = new ResourceLocation(modid, "crushing/" + resultId.getPath());
        build(consumer, id);
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id)
    {
        consumer.accept(new Result(id, this));
    }

    public class Result implements IFinishedRecipe
    {
        private final ResourceLocation id;
        private final CrushingRecipeBuilder builder;

        public Result(ResourceLocation id, CrushingRecipeBuilder builder)
        {
            this.id = id;
            this.builder = builder;
        }

        @Override
        public void serialize(JsonObject json)
        {
            json.addProperty("process_time", builder.processTime);
            json.add("ingredient", builder.ingredient.serialize());

            JsonArray results = new JsonArray();
            builder.results.forEach((stack, chance) -> results.add(serializeResult(stack, chance)));
            json.add("results", results);
        }

        private JsonObject serializeResult(ItemStack stack, float chance)
        {
            JsonObject json = new JsonObject();
            json.addProperty("item", NameUtils.fromItem(stack).toString());
            if (stack.getCount() > 1)
            {
                json.addProperty("count", stack.getCount());
            }
            if (chance < 1)
            {
                json.addProperty("chance", chance);
            }
            return json;
        }

        @Override
        public ResourceLocation getID()
        {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer()
        {
            return CrushingRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson()
        {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID()
        {
            return null;
        }
    } // end class Result

    
} // end class
