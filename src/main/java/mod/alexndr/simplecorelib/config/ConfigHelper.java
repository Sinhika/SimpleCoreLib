package mod.alexndr.simplecorelib.config;

import mod.alexndr.simplecorelib.api.config.ModOreConfig;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraftforge.fml.config.ModConfig;

public final class ConfigHelper
{
    public static void bakeServer(final ModConfig config)
    {
        SimpleCoreLibConfig.enableTestFurnace = ConfigHolder.SERVER.serverEnableTestFurnace.get();
        SimpleCoreLibConfig.enableTestOreGen = ConfigHolder.SERVER.serverEnableTestOreGen.get();
        
        SimpleCoreLibConfig.original_copper_cfg =  new ModOreConfig(ModOreConfig.UNIFORM, 6, 10, true, 
                                                        VerticalAnchor.absolute(0), VerticalAnchor.absolute(90));
    } // end bakeServer()
   
    
    public static void bakeClient(final ModConfig config)
    {
    } // end bakeClient
} // end class
