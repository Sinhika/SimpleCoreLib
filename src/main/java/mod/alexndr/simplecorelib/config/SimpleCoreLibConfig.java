package mod.alexndr.simplecorelib.config;

import mod.alexndr.simplecorelib.api.config.SimpleConfig;

public class SimpleCoreLibConfig extends SimpleConfig
{
    public static SimpleCoreLibConfig INSTANCE = new SimpleCoreLibConfig();
    
    // variable
    public static boolean enableTestOreGen;
    
//    public static Lazy<ModOreConfig> original_copper_cfg = 
//            Lazy.of( ()->new ModOreConfig(ModOreConfig.UNIFORM, ConfigHolder.SERVER.serverOreVeinSize.get(), 
//                    ConfigHolder.SERVER.serverOreVeinCount.get(), true, 
//                    VerticalAnchor.absolute(ConfigHolder.SERVER.serverOreBottomHeight.get()), 
//                    VerticalAnchor.absolute(ConfigHolder.SERVER.serverOreTopHeight.get())));
    
} // end class
