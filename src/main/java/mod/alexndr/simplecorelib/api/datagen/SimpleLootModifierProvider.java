package mod.alexndr.simplecorelib.api.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
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

    protected void AddDungeonAliases(ResourceKey<LootTable> customTable)
    {
        this.add(BuiltInLootTables.PILLAGER_OUTPOST, customTable);
        this.add(BuiltInLootTables.WOODLAND_MANSION, customTable);
    }

    protected void AddStrongholdAliases(ResourceKey<LootTable> customTable)
    {
        this.add(BuiltInLootTables.STRONGHOLD_CORRIDOR, customTable);
        this.add(BuiltInLootTables.STRONGHOLD_CROSSING, customTable);
    }

    protected void AddVillageHouseAliases(ResourceKey<LootTable> customTable)
    {
        this.add(BuiltInLootTables.VILLAGE_SNOWY_HOUSE, customTable);
        this.add(BuiltInLootTables.VILLAGE_TAIGA_HOUSE, customTable);
        this.add(BuiltInLootTables.VILLAGE_SAVANNA_HOUSE, customTable);
        this.add(BuiltInLootTables.VILLAGE_DESERT_HOUSE, customTable);
        this.add(BuiltInLootTables.VILLAGE_PLAINS_HOUSE, customTable);
    }

    protected void AddNetherAliases(ResourceKey<LootTable> customTable)
    {
        this.add(BuiltInLootTables.BASTION_BRIDGE, customTable);
        this.add(BuiltInLootTables.BASTION_HOGLIN_STABLE, customTable);
        this.add(BuiltInLootTables.BASTION_OTHER, customTable);
        this.add(BuiltInLootTables.BASTION_TREASURE, customTable);
        this.add(BuiltInLootTables.NETHER_BRIDGE, customTable);
    }

    protected void AddOceanAliases(ResourceKey<LootTable> customTable)
    {
        this.add(BuiltInLootTables.SHIPWRECK_MAP, customTable);
        this.add(BuiltInLootTables.SHIPWRECK_SUPPLY, customTable);
        this.add(BuiltInLootTables.SHIPWRECK_TREASURE, customTable);
        this.add(BuiltInLootTables.UNDERWATER_RUIN_BIG, customTable);
        this.add(BuiltInLootTables.UNDERWATER_RUIN_SMALL, customTable);
    }

} // end class
