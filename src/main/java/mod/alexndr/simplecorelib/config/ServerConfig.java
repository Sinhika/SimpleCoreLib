package mod.alexndr.simplecorelib.config;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraftforge.common.ForgeConfigSpec;

public final class ServerConfig
{
    // general
    final ForgeConfigSpec.BooleanValue serverEnableTestFurnace;
    final ForgeConfigSpec.BooleanValue serverEnableTestOreGen;
    final ForgeConfigSpec.IntValue serverOreVeinSize;
    final ForgeConfigSpec.IntValue serverOreVeinCount;
    final ForgeConfigSpec.IntValue serverOreBottomHeight;
    final ForgeConfigSpec.IntValue serverOreTopHeight;
    public final ForgeConfigSpec.IntValue serverTestShearDurability;
    
    ServerConfig(final ForgeConfigSpec.Builder builder)
    {
        builder.push("General");
        serverEnableTestFurnace = builder.comment("true enables test_furnace")
                .translation(SimpleCoreLib.MODID + ".config.enable_test_furnace")
                .define("EnableTestFurnace", false);
        serverEnableTestOreGen = builder.comment("true enables ore gen test")
                .translation(SimpleCoreLib.MODID + ".config.enable_test_oregen")
                .define("EnableTestOreGen", false);
        serverTestShearDurability = builder.comment("test shears durability value (tests initializing properties from config)")
                .translation(SimpleCoreLib.MODID + ".config.test_shears_durability")
                .defineInRange("TestShearDurability", 1500, 1, 99999);
        builder.pop();
        builder.push("Test Ore Gen");
        serverOreVeinSize = builder.comment("Test ore vein size")
                .translation(SimpleCoreLib.MODID + ".config.ore_vein_size")
                .defineInRange("OreVeinSize", 6, 1, 99); 
        serverOreVeinCount = builder.comment("Test ore vein count")
                .translation(SimpleCoreLib.MODID + ".config.ore_vein_count")
                .defineInRange("OreVeinCount", 10, 1, 99); 
        serverOreBottomHeight = builder.comment("Test ore bottom Height")
                .translation(SimpleCoreLib.MODID + ".config.ore_bottom_height")
                .defineInRange("OreBottomHeight", -63, -63, 254); 
        serverOreTopHeight = builder.comment("Test ore top height")
                .translation(SimpleCoreLib.MODID + ".config.ore_top_height")
                .defineInRange("OreTopHeight", 128, -62, 255); 
        
        builder.pop();
    } // end ctor()
    
} // end class
