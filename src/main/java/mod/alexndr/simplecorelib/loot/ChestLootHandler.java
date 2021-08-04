package mod.alexndr.simplecorelib.loot;

import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;

import LootPool;

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
                .bonusRolls(0, 1)
                .name(modid + "_inject")
                .build();
    }

    private static LootEntry.Builder<?> getInjectEntry(String modid, String name, int weight) {
        ResourceLocation table = new ResourceLocation(modid, "inject/" + name);
        return TableLootEntry.lootTableReference(table).setWeight(weight);
    }

} // end-class
