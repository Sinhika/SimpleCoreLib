package mod.alexndr.simplecorelib.config;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class ServerConfig
{
    // general
    public final ModConfigSpec.IntValue serverTestShearDurability;
    
    ServerConfig(final ModConfigSpec.Builder builder)
    {
        builder.push("General");
        serverTestShearDurability = builder.comment("test shears durability value (tests initializing properties from config)")
                .translation(SimpleCoreLib.MODID + ".config.test_shears_durability")
                .defineInRange("TestShearDurability", 1500, 1, 99999);
        builder.pop();
    } // end ctor()
    
} // end class
