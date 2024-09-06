package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.loot.ChestLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CoreLootModifierProvider extends GlobalLootModifierProvider
{
    public CoreLootModifierProvider(PackOutput output,
                                    CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries, SimpleCoreLib.MODID);
    }

    /**
     * Call {@link #add} here, which will pass in the necessary information to write the jsons.
     */
    @Override
    protected void start()
    {
        this.add(BuiltInLootTables.VILLAGE_PLAINS_HOUSE, LootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.VILLAGE_DESERT_HOUSE, LootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.VILLAGE_SHEPHERD, LootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.VILLAGE_TOOLSMITH, LootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.VILLAGE_SAVANNA_HOUSE, LootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.VILLAGE_SNOWY_HOUSE, LootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.VILLAGE_TAIGA_HOUSE, LootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.SPAWN_BONUS_CHEST, LootTableInjectorSubProvider.VILLAGE_HOUSE);
    }

    protected void add(ResourceKey<LootTable> targetLootTable, ResourceKey<LootTable> customLootTable)
    {
        this.add(targetLootTable.location().getPath(),
                 new ChestLootModifier(getCondition(targetLootTable.location()), customLootTable));
    }

    protected LootItemCondition[] getCondition(ResourceLocation lootTable)
    {
        var condition = LootTableIdCondition.builder(lootTable);
        return new LootItemCondition[] { condition.build()};
    }

} // end class
