package mod.alexndr.simplecorelib.content;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.function.Supplier;

/**
 * @todo SEE  net.neoforged.neoforge.common.loot.AddTableLootModifier and NeoForgeMod. ADD_TABLE_LOOT_MODIFIER_TYPE.
 *
 */
public class ChestLootModifier extends LootModifier
{
    public static final Supplier<MapCodec<ChestLootModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.mapCodec(
                    inst -> LootModifier.codecStart(inst).and(
                                    LootItem.CODEC.fieldOf("additional_items").forGetter(m -> m.additional_items))
                            .apply(inst, ChestLootModifier::new)));

    private final LootItem additional_items;

    public ChestLootModifier(LootItemCondition[] conditionsIn, LootItem additionalItems)
    {
        super(conditionsIn);
        additional_items = additionalItems;
    }

    /**
     * Applies the modifier to the generated loot (all loot conditions have already been checked
     * and have returned true).
     *
     * @param generatedLoot the list of ItemStacks that will be dropped, generated by loot tables
     * @param context       the LootContext, identical to what is passed to loot tables
     * @return modified loot drops
     */
    @Override protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
                                                           LootContext context)
    {
        // TODO append something from additional_items entries to generatedLoot
        return generatedLoot;
    }

    /**
     * Returns the registered codec for this modifier
     */
    @Override public MapCodec<? extends IGlobalLootModifier> codec()
    {
        return ChestLootModifier.CODEC.get();
    }
} // end class
