package mod.alexndr.simplecorelib.api.datagen;

import java.util.function.BiConsumer;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public abstract class LootTableInjectorProvider implements LootTableSubProvider
{

    public ResourceLocation getInjectionTableId(String modid, String table_name)
    {
    	return new ResourceLocation(modid, "inject/" + table_name);
    }
    
    public void addInjectionTable(BiConsumer<ResourceLocation, LootTable.Builder> foo, ResourceLocation tableId, LootPool.Builder pool)
    {
    	foo.accept(tableId, LootTable.lootTable().withPool(pool));
    }
    
    public LootPool.Builder createChestPool(int minRolls, int maxRolls, float chanceRoll) 
    {
        return LootPool.lootPool()
                .name("main")
                .setRolls(UniformGenerator.between((float)minRolls, (float)maxRolls))
                .when(LootItemRandomChanceCondition.randomChance(chanceRoll));
    } // end createChestPool
    

} // end class
