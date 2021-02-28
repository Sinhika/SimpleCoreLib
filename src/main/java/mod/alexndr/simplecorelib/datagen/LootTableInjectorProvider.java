package mod.alexndr.simplecorelib.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.util.ResourceLocation;

public abstract class LootTableInjectorProvider extends AbstractLootTableProvider
{

    public LootTableInjectorProvider(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
        // TODO Auto-generated constructor stub
    }
    
    void addInjectionTable(String modid, String table_name, LootPool.Builder pool)
    {
        this.addTable(new ResourceLocation(modid, "inject/" + table_name), 
                LootTable.builder().addLootPool(pool), LootParameterSets.CHEST);
    }
    
    LootPool.Builder createChestPool(int minRolls, int maxRolls, float chanceRoll) 
    {
        return LootPool.builder()
                .name("main")
                .rolls(RandomValueRange.of((float)minRolls, (float)maxRolls))
                .acceptCondition(RandomChance.builder(chanceRoll));
    } // end createChestPool
    

} // end class
