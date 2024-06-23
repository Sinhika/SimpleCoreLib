package mod.alexndr.simplecorelib.api.datagen;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;

public class SimpleLootTableProvider extends LootTableProvider
{

    public SimpleLootTableProvider(PackOutput pOutput, List<LootTableProvider.SubProviderEntry> pSubProviders,
                                   CompletableFuture<HolderLookup.Provider> pRegistries)
    {
        super(pOutput, Set.of(), pSubProviders, pRegistries);
    }
 } // end class