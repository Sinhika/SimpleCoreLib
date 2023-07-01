package mod.alexndr.simplecorelib.api.helpers;

import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootPool.Builder;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.resources.ResourceLocation;

/**
 * Code that handles injecting stuff into chest loot pools.
 * Based heavily on Botania's LootHandler, without which I never would have
 * figured this out.
 * 
 */
public final class ChestLootHandler
{
    // lets figure out how to write a LootPool
    public static LootPool getInjectPool(String modid, String entryName) 
    {
        return LootPool.lootPool()
                .add(getInjectEntry(modid, entryName, 1))
                .setBonusRolls(UniformGenerator.between(0.0F, 1.0F))
                .name(modid + "_inject")
                .build();
    }

    private static LootPoolEntryContainer.Builder<?> getInjectEntry(String modid, String name, int weight) {
        ResourceLocation table = new ResourceLocation(modid, "inject/" + name);
        return LootTableReference.lootTableReference(table).setWeight(weight);
    }

} // end-class
