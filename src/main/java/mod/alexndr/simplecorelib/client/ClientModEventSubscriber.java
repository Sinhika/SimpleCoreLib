package mod.alexndr.simplecorelib.client;

import mod.alexndr.simplecorelib.client.gui.TestFurnaceScreen;
import mod.alexndr.simplecorelib.init.ModMenuTypes;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

//@EventBusSubscriber(modid = SimpleCoreLib.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEventSubscriber
{

   public static void registerScreens(RegisterMenuScreensEvent event)
   {
       // uncomment to register client gui of test_furnace.
        event.register(ModMenuTypes.test_furnace.get(), TestFurnaceScreen::new);
   }

} // end class
