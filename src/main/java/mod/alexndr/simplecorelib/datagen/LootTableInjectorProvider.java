package mod.alexndr.simplecorelib.datagen;

import java.util.function.BiConsumer;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class LootTableInjectorProvider implements LootTableSubProvider
{
    private static final ResourceKey<LootTable> VILLAGE_HOUSE = getInjectionTableId(SimpleCoreLib.MODID, "village_house");

    @Override
    public void generate(HolderLookup.Provider pRegistries,
                         BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pGenerator)
    {
        pGenerator.accept(VILLAGE_HOUSE, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .name("simpleores_testloot")
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.test_shears.get()).setWeight(1))
                ));
    }

    public static ResourceKey<LootTable> getInjectionTableId(String modid, String table_name)
    {
        return ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(modid, "chest/" + table_name));
    }

//    public void addInjectionTable(BiConsumer<ResourceLocation, LootTable.Builder> foo, ResourceLocation tableId, LootPool.Builder pool)
//    {
//    	foo.accept(tableId, LootTable.lootTable().withPool(pool));
//    }

    public LootPool.Builder createChestPool(String Name, int minRolls, int maxRolls, float chanceRoll)
    {
        return LootPool.lootPool()
                .name(Name)
                .setRolls(UniformGenerator.between((float)minRolls, (float)maxRolls))
                .when(LootItemRandomChanceCondition.randomChance(chanceRoll));
    } // end createChestPool
    

} // end class
