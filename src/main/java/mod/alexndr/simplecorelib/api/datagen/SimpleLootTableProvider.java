package mod.alexndr.simplecorelib.api.datagen;

import java.util.List;
import java.util.Set;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;

public class SimpleLootTableProvider extends LootTableProvider
{

    public SimpleLootTableProvider(PackOutput pOutput, List<LootTableProvider.SubProviderEntry> pSubProviders)
    {
        super(pOutput, Set.of(), pSubProviders);
    }
 } // end class