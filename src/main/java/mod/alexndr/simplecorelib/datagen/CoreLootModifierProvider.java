package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.loot.ChestLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.concurrent.CompletableFuture;

public class CoreLootModifierProvider extends mod.alexndr.simplecorelib.api.datagen.SimpleLootModifierProvider
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
        this.add(BuiltInLootTables.VILLAGE_PLAINS_HOUSE, CoreLootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.VILLAGE_DESERT_HOUSE, CoreLootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.VILLAGE_SHEPHERD, CoreLootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.VILLAGE_TOOLSMITH, CoreLootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.VILLAGE_SAVANNA_HOUSE, CoreLootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.VILLAGE_SNOWY_HOUSE, CoreLootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.VILLAGE_TAIGA_HOUSE, CoreLootTableInjectorSubProvider.VILLAGE_HOUSE);
        this.add(BuiltInLootTables.SPAWN_BONUS_CHEST, CoreLootTableInjectorSubProvider.VILLAGE_HOUSE);
    }

    @Override protected void add(ResourceKey<LootTable> targetLootTable, ResourceKey<LootTable> customLootTable)
    {
        this.add(targetLootTable.location().getPath(),
                 new ChestLootModifier(getCondition(targetLootTable.location()), customLootTable));
    }

} // end class
