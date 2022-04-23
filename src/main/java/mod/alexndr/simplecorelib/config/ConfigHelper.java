package mod.alexndr.simplecorelib.config;

import net.minecraftforge.fml.config.ModConfig;

public final class ConfigHelper
{
    public static void bakeServer(final ModConfig config)
    {
        SimpleCoreLibConfig.enableTestFurnace = ConfigHolder.SERVER.serverEnableTestFurnace.get();
        SimpleCoreLibConfig.enableTestOreGen = ConfigHolder.SERVER.serverEnableTestOreGen.get();
        
    } // end bakeServer()
   
    
    public static void bakeClient(final ModConfig config)
    {
    } // end bakeClient
} // end class
