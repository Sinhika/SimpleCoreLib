package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid=SimpleCoreLib.MODID, bus=EventBusSubscriber.Bus.MOD)
public class SimpleCoreDataGenerator
{
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator gen = event.getGenerator();
		// server datagen
    	gen.addProvider(event.includeServer(), new SimpleCoreBlockTags(gen, event.getExistingFileHelper()));
    	gen.addProvider(event.includeServer(), new SimpleCoreItemTags(gen, event.getExistingFileHelper()));
    	gen.addProvider(event.includeServer(), new SimpleCoreLootTableProvider(gen));
    	// client datagen
    	gen.addProvider(event.includeClient(), new SimpleCoreBlockStateProvider(gen, event.getExistingFileHelper()));
    	gen.addProvider(event.includeClient(), new SimpleCoreItemModelProvider(gen, event.getExistingFileHelper()));
	} // end gatherData()
	
} // end class
