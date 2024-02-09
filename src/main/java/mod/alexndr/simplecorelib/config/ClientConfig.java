package mod.alexndr.simplecorelib.config;

import net.neoforged.neoforge.common.NeoForgeConfigSpec;

public final class ClientConfig
{
    ClientConfig(final NeoForgeConfigSpec.Builder builder) 
    {
        builder.push("General");
        builder.pop();
    } // end ctor
} // end-class

