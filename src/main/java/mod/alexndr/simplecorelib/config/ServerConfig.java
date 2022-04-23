package mod.alexndr.simplecorelib.config;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraftforge.common.ForgeConfigSpec;

public final class ServerConfig
{
    // general
    final ForgeConfigSpec.BooleanValue serverEnableTestFurnace;
    final ForgeConfigSpec.BooleanValue serverEnableTestOreGen;
    
    ServerConfig(final ForgeConfigSpec.Builder builder)
    {
        builder.push("General");
        serverEnableTestFurnace = builder.comment("true enables test_furnace")
                .translation(SimpleCoreLib.MODID + ".config.enableTestFurnace")
                .define("EnableTestFurnace", false);
        serverEnableTestOreGen = builder.comment("true enables ore gen test")
                .translation(SimpleCoreLib.MODID + ".config.enableTestOreGen")
                .define("EnableTestFurnace", false);
        builder.pop();
        
    } // end ctor()
    
} // end class
