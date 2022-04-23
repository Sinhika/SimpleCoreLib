package mod.alexndr.simplecorelib.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ClientConfig
{
    ClientConfig(final ForgeConfigSpec.Builder builder) 
    {
        builder.push("General");
        builder.pop();
    } // end ctor
} // end-class

