package mod.alexndr.simplecorelib.api.client;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.client.gui.SimpleSpriteUploader;
import mod.alexndr.simplecorelib.api.client.gui.Textures;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

@EventBusSubscriber(modid = SimpleCoreLib.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class JEIClientModEventSubscriber
{
    public static Textures textures;

    @SubscribeEvent
    public static void onRegisterClientReloadListenersEvent(final RegisterClientReloadListenersEvent event)
    {
        if (ModList.get().isLoaded("jei"))
        {
            // add things to texture atlas.
            Minecraft minecraft = Minecraft.getInstance();
            SimpleSpriteUploader spriteUploader = new SimpleSpriteUploader(minecraft.textureManager, SimpleCoreLib.SIMPLE_TEXTURE_ATLAS);
            textures = new Textures(spriteUploader);
            event.registerReloadListener(spriteUploader);
        }
    } // end onRegisterClientReloadListenersEvent

} // end class
