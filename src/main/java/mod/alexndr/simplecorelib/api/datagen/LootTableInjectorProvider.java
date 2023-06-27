package mod.alexndr.simplecorelib.api.datagen;

import java.util.List;
import java.util.Set;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public abstract class LootTableInjectorProvider extends LootTableProvider
{

    public LootTableInjectorProvider(PackOutput pOutput, Set<ResourceLocation> pRequiredTables, 
    								 List<LootTableProvider.SubProviderEntry> pSubProviders)
    {
        super(pOutput, pRequiredTables, pSubProviders);
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
