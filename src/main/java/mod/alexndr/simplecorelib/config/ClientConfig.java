package mod.alexndr.simplecorelib.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ClientConfig
{
    ClientConfig(final ModConfigSpec.Builder builder)
    {
        builder.push("General");
        builder.pop();
    } // end ctor
} // end-class

