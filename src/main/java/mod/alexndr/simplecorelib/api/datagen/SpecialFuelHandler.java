package mod.alexndr.simplecorelib.api.datagen;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.ObjIntConsumer;

/**
 * Encapsulate utility functions for defining and creating a custom FurnaceFuel datamap.
 */
public abstract class SpecialFuelHandler
{
     protected static void add(ObjIntConsumer<Either<Item, TagKey<Item>>> consumer,
                            ItemLike item, int time)
    {
        consumer.accept(Either.left(item.asItem()), time);
    }

    protected static void add(ObjIntConsumer<Either<Item, TagKey<Item>>> consumer,
                            TagKey<Item> tag, int time)
    {
        consumer.accept(Either.right(tag), time);
    }

    protected static boolean isNeverAFurnaceFuel(Item pItem) {
        return pItem.builtInRegistryHolder().is(ItemTags.NON_FLAMMABLE_WOOD);
    }

    protected static void add(Map<Item, Integer> pMap, TagKey<Item> pItemTag, int pBurnTime) {
        for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(pItemTag)) {
            if (!isNeverAFurnaceFuel(holder.value())) {
                pMap.put(holder.value(), pBurnTime);
            }
        }
    }

    protected static void add(Map<Item, Integer> pMap, ItemLike pItem, int pBurnTime) {
        Item item = pItem.asItem();
        if (isNeverAFurnaceFuel(item)) {
            if (SharedConstants.IS_RUNNING_IN_IDE) {
                throw (IllegalStateException) Util.pauseInIde(
                        new IllegalStateException(
                                "A developer tried to explicitly make fire resistant item " + item.getName(null).getString() + " a furnace fuel. That will not work!"
                        )
                );
            }
        } else {
            pMap.put(item, pBurnTime);
        }
    }

    public abstract void buildFuels(ObjIntConsumer<Either<Item, TagKey<Item>>> map1);

} // end class
