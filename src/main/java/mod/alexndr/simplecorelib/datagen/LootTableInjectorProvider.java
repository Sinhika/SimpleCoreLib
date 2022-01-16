package mod.alexndr.simplecorelib.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public abstract class LootTableInjectorProvider extends AbstractLootTableProvider
{

    public LootTableInjectorProvider(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
    }
    
    public void addInjectionTable(String modid, String table_name, LootPool.Builder pool)
    {
        this.addTable(new ResourceLocation(modid, "inject/" + table_name), 
                LootTable.lootTable().withPool(pool), LootContextParamSets.CHEST);
    }
    
    public LootPool.Builder createChestPool(int minRolls, int maxRolls, float chanceRoll) 
    {
        return LootPool.lootPool()
                .name("main")
                .setRolls(UniformGenerator.between((float)minRolls, (float)maxRolls))
                .when(LootItemRandomChanceCondition.randomChance(chanceRoll));
    } // end createChestPool
    

} // end class
