package mod.alexndr.simplecorelib.api.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public abstract class SimpleLootModifierProvider extends GlobalLootModifierProvider
{
    public SimpleLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries,
                                      String modid)
    {
        super(output, registries, modid);
    }

    protected abstract void add(ResourceKey<LootTable> targetLootTable, ResourceKey<LootTable> customLootTable);

    protected LootItemCondition[] getCondition(ResourceLocation lootTable)
    {
        var condition = LootTableIdCondition.builder(lootTable);
        return new LootItemCondition[]{condition.build()};
    }
}
