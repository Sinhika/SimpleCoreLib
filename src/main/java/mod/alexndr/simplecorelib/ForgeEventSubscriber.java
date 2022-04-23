package mod.alexndr.simplecorelib;

import mod.alexndr.simplecorelib.world.OreGeneration;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * Subscribe to events from the FORGE EventBus that should be handled on both PHYSICAL sides in this class
 *
 */
@EventBusSubscriber(modid = SimpleCoreLib.MODID, bus = EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber
{
    /**
     * Biome loading triggers ore generation.
     */
    @SubscribeEvent(priority=EventPriority.HIGH)
    public static void onBiomeLoading(BiomeLoadingEvent evt)
    {
        if (evt.getCategory() != Biome.BiomeCategory.THEEND
                && evt.getCategory() != Biome.BiomeCategory.NETHER)
        {
            OreGeneration.generateOverworldOres(evt);
        }
    } // end onBiomeLoading()

} // end class
