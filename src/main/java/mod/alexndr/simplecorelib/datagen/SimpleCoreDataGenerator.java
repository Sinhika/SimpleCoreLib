package mod.alexndr.simplecorelib.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.datagen.SimpleLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid=SimpleCoreLib.MODID, bus=EventBusSubscriber.Bus.MOD)
public class SimpleCoreDataGenerator
{
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();		
        
        // server datagen
		// uncomment for test block & item tags.
//        SimpleCoreBlockTags blockTags = new SimpleCoreBlockTags(packOutput, lookupProvider, existingFileHelper);
//        gen.addProvider(event.includeServer(), blockTags);
//    	gen.addProvider(event.includeServer(),
//    			new SimpleCoreItemTags(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));

		// uncomment for loot table tests
//    	gen.addProvider(event.includeServer(),
//    			new SimpleLootTableProvider(packOutput, List.of(
//    					new LootTableProvider.SubProviderEntry(CoreBlockLootSubProvider::new, LootContextParamSets.BLOCK)), lookupProvider));

		// uncomment to enable test recipes
//    	gen.addProvider(event.includeServer(),
//				new SimpleCoreRecipeProvider(packOutput, lookupProvider));
    	// client datagen
    	gen.addProvider(event.includeClient(), new SimpleCoreBlockStateProvider(packOutput, existingFileHelper));
    	gen.addProvider(event.includeClient(), new SimpleCoreItemModelProvider(packOutput, existingFileHelper));
	} // end gatherData()
	
} // end class
