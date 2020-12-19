package mod.alexndr.simplecorelib.world;

import mod.alexndr.simplecorelib.config.ModOreConfig;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class OreGenUtils
{
    public static ConfiguredFeature<?, ?> buildOverworldOreFeature(
                                Feature<OreFeatureConfig> feature, BlockState bstate, ModOreConfig cfg)
    {
        return feature.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, bstate, cfg.getVein_size()))
                .range(cfg.getCfg().maximum).square().func_242731_b(cfg.getVein_count());
    } // end buildOverworldOreFeature()


    public static ConfiguredFeature<?, ?> buildNetherOreFeature(
                            Feature<OreFeatureConfig> feature, BlockState bstate, ModOreConfig cfg)
    {
        return feature
                .withConfiguration(
                        new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, bstate, cfg.getVein_size()))
                .range(cfg.getCfg().maximum).square().func_242731_b(cfg.getVein_count());
    }

    public static ConfiguredFeature<?, ?> buildNetherRockFeature(
                                    Feature<OreFeatureConfig> feature, BlockState bstate, ModOreConfig cfg)
    {
        return feature.withConfiguration(
                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_NETHER, bstate, cfg.getVein_size()))
                .range(cfg.getCfg().maximum).square().func_242731_b(cfg.getVein_count());
    }
    
} // end class
