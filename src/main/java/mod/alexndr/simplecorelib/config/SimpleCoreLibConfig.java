package mod.alexndr.simplecorelib.config;

import java.util.Optional;

import mod.alexndr.simplecorelib.api.config.ModOreConfig;
import mod.alexndr.simplecorelib.api.config.SimpleConfig;

public class SimpleCoreLibConfig extends SimpleConfig
{
    public static SimpleCoreLibConfig INSTANCE = new SimpleCoreLibConfig();
    
    // variable
    public static boolean enableTestFurnace;
    public static boolean enableTestOreGen;
    
    public static Optional<ModOreConfig> original_copper_cfg;
    
} // end class
