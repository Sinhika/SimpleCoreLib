package mod.alexndr.simplecorelib.config;

import net.minecraftforge.fml.config.ModConfig;

public final class ConfigHelper
{
    public static void bakeServer(final ModConfig config)
    {
        SimpleCoreLibConfig.testShearDurability = ConfigHolder.SERVER.serverTestShearDurability.get();
    } // end bakeServer()
   
    
    public static void bakeClient(final ModConfig config)
    {
    } // end bakeClient
} // end class
