package mod.alexndr.simplecorelib.world;

import mod.alexndr.simplecorelib.config.ModOreConfig;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class OreGenUtils
{
    public static ConfiguredFeature<?, ?> buildNetherOreFeature(BlockState bstate, ModOreConfig cfg)
    {
        return Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK,
                    bstate, cfg.getVein_size())).range(cfg.getCfg().maximum).square().func_242731_b(cfg.getVein_count());
//                .withPlacement(Placement.field_242907_l.configure(cfg.getCfg())
//                        .func_242728_a().func_242731_b(cfg.getVein_count()));
    }

    public static ConfiguredFeature<?, ?> buildNetherRockFeature(BlockState bstate, ModOreConfig cfg)
    {
        return Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_NETHER,
                    bstate, cfg.getVein_size())).range(cfg.getCfg().maximum).square().func_242731_b(cfg.getVein_count());
//                .withPlacement(Placement.field_242907_l.configure(cfg.getCfg())
//                        .func_242728_a().func_242731_b(cfg.getVein_count()));
    }
    
    public static ConfiguredFeature<?, ?> buildOverworldOreFeature(BlockState bstate, ModOreConfig cfg)
    {
        return Feature.ORE.withConfiguration(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, 
                        bstate, cfg.getVein_size())).range(cfg.getCfg().maximum).square().func_242731_b(cfg.getVein_count());
        /*
         * .withPlacement(Placement.field_242907_l.configure(cfg.getCfg()).func_242728_a
         * () .func_242731_b(cfg.getVein_count()));
         */    
    } // end buildOverworldOreFeature()
    
} // end class
