package mod.alexndr.simplecorelib.world;

import mod.alexndr.simplecorelib.config.ModOreConfig;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import ConfiguredFeature;

public class OreGenUtils
{
    public static ConfiguredFeature<?, ?> buildOverworldOreFeature(
                                Feature<OreConfiguration> feature, BlockState bstate, ModOreConfig cfg)
    {
        return feature.configured(
                new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, bstate, cfg.getVein_size()))
                .range(cfg.getCfg().maximum).squared().count(cfg.getVein_count());
    } // end buildOverworldOreFeature()


    public static ConfiguredFeature<?, ?> buildNetherOreFeature(
                            Feature<OreConfiguration> feature, BlockState bstate, ModOreConfig cfg)
    {
        return feature
                .configured(
                        new OreConfiguration(OreConfiguration.Predicates.NETHERRACK, bstate, cfg.getVein_size()))
                .range(cfg.getCfg().maximum).squared().count(cfg.getVein_count());
    }

    public static ConfiguredFeature<?, ?> buildNetherRockFeature(
                                    Feature<OreConfiguration> feature, BlockState bstate, ModOreConfig cfg)
    {
        return feature.configured(
                    new OreConfiguration(OreConfiguration.Predicates.NETHER_ORE_REPLACEABLES, bstate, cfg.getVein_size()))
                .range(cfg.getCfg().maximum).squared().count(cfg.getVein_count());
    }
    
    public static void registerFeature(String modid, String name, ConfiguredFeature<?, ?> cfg_feature)
    {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(modid, name), cfg_feature);
    }
} // end class
