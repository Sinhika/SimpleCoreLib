package mod.alexndr.simplecorelib.api.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public abstract class SimpleLootInjectorSubProvider implements LootTableSubProvider
{
    public static ResourceKey<LootTable> getInjectionTableId(String modid, String table_name)
    {
        return ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(modid, "chest/" + table_name));
    }

    public LootPool.Builder createChestPool(String Name, int minRolls, int maxRolls, float chanceRoll)
    {
        return LootPool.lootPool()
                .name(Name)
                .setRolls(UniformGenerator.between((float)minRolls, (float)maxRolls))
                .when(LootItemRandomChanceCondition.randomChance(chanceRoll));
    } // end createChestPool


} // end class
