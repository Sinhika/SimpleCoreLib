package mod.alexndr.simplecorelib.client;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.client.gui.TestFurnaceScreen;
import mod.alexndr.simplecorelib.content.TestFurnaceContainerMenu;
import mod.alexndr.simplecorelib.init.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = SimpleCoreLib.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEventSubscriber
{

    /**
     * We need to register our renderers on the client because rendering code does not exist on the server
     * and trying to use it on a dedicated server will crash the game.
     * <p>
     * This method will be called by Forge when it is time for the mod to do its client-side setup
     * This method will always be called after the Registry events.
     * This means that all Blocks, Items, TileEntityTypes, etc. will all have been registered already
     */
    @SubscribeEvent
    public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) 
    {
        // Register ContainerType Screens
        event.enqueueWork( () -> {
            MenuScreens.register((MenuType<TestFurnaceContainerMenu>) ModMenuTypes.test_furnace.get(), TestFurnaceScreen::new);
        });
    }

} // end class
