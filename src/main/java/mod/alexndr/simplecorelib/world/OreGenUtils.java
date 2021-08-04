package mod.alexndr.simplecorelib.world;

import mod.alexndr.simplecorelib.config.ModOreConfig;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import ConfiguredFeature;

public class OreGenUtils
{
    public static ConfiguredFeature<?, ?> buildOverworldOreFeature(
                                Feature<OreFeatureConfig> feature, BlockState bstate, ModOreConfig cfg)
    {
        return feature.configured(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, bstate, cfg.getVein_size()))
                .range(cfg.getCfg().maximum).squared().count(cfg.getVein_count());
    } // end buildOverworldOreFeature()


    public static ConfiguredFeature<?, ?> buildNetherOreFeature(
                            Feature<OreFeatureConfig> feature, BlockState bstate, ModOreConfig cfg)
    {
        return feature
                .configured(
                        new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, bstate, cfg.getVein_size()))
                .range(cfg.getCfg().maximum).squared().count(cfg.getVein_count());
    }

    public static ConfiguredFeature<?, ?> buildNetherRockFeature(
                                    Feature<OreFeatureConfig> feature, BlockState bstate, ModOreConfig cfg)
    {
        return feature.configured(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHER_ORE_REPLACEABLES, bstate, cfg.getVein_size()))
                .range(cfg.getCfg().maximum).squared().count(cfg.getVein_count());
    }
    
    public static void registerFeature(String modid, String name, ConfiguredFeature<?, ?> cfg_feature)
    {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(modid, name), cfg_feature);
    }
} // end class
