package mod.alexndr.simplecorelib.world;

import mod.alexndr.simplecorelib.config.SimpleCoreLibConfig;
import mod.alexndr.simplecorelib.init.ModFeatures;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGeneration
{
    public static void generateOverworldOres(BiomeLoadingEvent evt)
    {
        if (SimpleCoreLibConfig.enableTestOreGen)
        {
            evt.getGeneration().addFeature(Decoration.UNDERGROUND_ORES, ModFeatures.ORIGINAL_COPPER_DEPOSIT.getHolder().get());
        }
    } // end generate
} // end class
