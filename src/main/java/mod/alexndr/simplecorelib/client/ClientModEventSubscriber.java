package mod.alexndr.simplecorelib.client;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.client.gui.TestFurnaceScreen;
import mod.alexndr.simplecorelib.init.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = SimpleCoreLib.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEventSubscriber
{

   @SubscribeEvent
   public static void registerScreens(RegisterMenuScreensEvent event)
   {
        event.register(ModMenuTypes.test_furnace.get(), TestFurnaceScreen::new);
   }

} // end class
