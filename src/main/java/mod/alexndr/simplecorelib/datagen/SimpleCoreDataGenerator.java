package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid=SimpleCoreLib.MODID, bus=EventBusSubscriber.Bus.MOD)
public class SimpleCoreDataGenerator
{
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator gen = event.getGenerator();
        if (event.includeServer())
        {
        	gen.addProvider(new SimpleCoreLootTableProvider(gen));
        }
        if (event.includeClient())
        {
        }
	} // end gatherData()
	
} // end class
