package mod.alexndr.simplecorelib.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.alexndr.simplecorelib.init.ModCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

/**
 *  Thanks to <a href="https://github.com/railcraft-reborn/railcraft">"Railcraft Reborn"</a> making their source
 *  code available so that I could see a working example of loot table injection as a GlobalLootModifier
 *  and learn from it. All the code below is my own; I did not cut & paste. However, there are only
 *  so many ways to make code do the same thing when working within a defined API.
 *
 */
public class ChestLootModifier extends mod.alexndr.simplecorelib.api.loot.AbstractChestLootModifier
{
    public static final MapCodec<ChestLootModifier> CODEC =
            RecordCodecBuilder.mapCodec(inst -> LootModifier.codecStart(inst)
                    .and(ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("lootTable")
                            .forGetter((m) -> m.lootTable))
                    .apply(inst, ChestLootModifier::new));

    public ChestLootModifier(LootItemCondition[] conditionsIn, ResourceKey<LootTable> lootTable)
    {
        super(conditionsIn, lootTable);
    }

    /**
     * Returns the registered codec for this modifier
     */
    @Override @NotNull
    public MapCodec<? extends IGlobalLootModifier> codec()
    {
        return ModCodecs.CHEST_LOOT.get();
    }
} // end class
