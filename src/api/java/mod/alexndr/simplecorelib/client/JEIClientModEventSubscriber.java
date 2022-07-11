package mod.alexndr.simplecorelib.client;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.client.gui.SimpleSpriteUploader;
import mod.alexndr.simplecorelib.client.gui.Textures;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

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
