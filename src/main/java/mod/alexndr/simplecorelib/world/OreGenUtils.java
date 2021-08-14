package mod.alexndr.simplecorelib.world;

import java.util.List;

import mod.alexndr.simplecorelib.config.ModOreConfig;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;

public class OreGenUtils
{
//	public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_COPPER_TARGET_LIST = ImmutableList.of(
//			OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, Features.States.COPPER_ORE),
//			OreConfiguration.target(OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES,
//					Features.States.DEEPSLATE_COPPER_ORE));

// public static final ConfiguredFeature<?, ?> ORE_COPPER = register("ore_copper",
//		Feature.ORE.configured(new OreConfiguration(ORE_COPPER_TARGET_LIST, 10))
//			.rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(96)).squared().count(6));

//	public static final ConfiguredFeature<?, ?> ORE_LAPIS = register("ore_lapis",
//			Feature.ORE.configured(new OreConfiguration(ORE_LAPIS_TARGET_LIST, 7))
//					.rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(30)).squared());

	public static ConfiguredFeature<?, ?> buildOverworldOreFeature(List<TargetBlockState> target_list, ModOreConfig cfg)
	{
		if (cfg.getRange_type() == ModOreConfig.TRIANGLE)
		{
			return Feature.ORE.configured(new OreConfiguration(target_list, cfg.getVein_size()))
					.rangeTriangle(cfg.getBottom(), cfg.getTop()).squared().count(cfg.getVein_count());
		} else
		{
			return Feature.ORE.configured(new OreConfiguration(target_list, cfg.getVein_size()))
					.rangeUniform(cfg.getBottom(), cfg.getTop()).squared().count(cfg.getVein_count());
		}
	} // end buildOverworldOreFeature()

//	public static final ConfiguredFeature<?, ?> ORE_QUARTZ_NETHER = register("ore_quartz_nether", 
//			Feature.ORE.configured(
//					new OreConfiguration(OreConfiguration.Predicates.NETHERRACK, Features.States.NETHER_QUARTZ_ORE, 14))
//			.range(Features.Decorators.RANGE_10_10).squared().count(16));

	public static ConfiguredFeature<?, ?> buildNetherOreFeature(BlockState bstate, ModOreConfig cfg)
	{
		return Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NETHERRACK, bstate, cfg.getVein_size()))
				.range(Features.Decorators.RANGE_10_10).squared().count(cfg.getVein_count());
	}

	
//	public static final ConfiguredFeature<?, ?> ORE_DEBRIS_LARGE = register("ore_debris_large",
//			Feature.SCATTERED_ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NETHER_ORE_REPLACEABLES,
//							Features.States.ANCIENT_DEBRIS, 3, 1.0F))
//					.rangeTriangle(VerticalAnchor.absolute(8), VerticalAnchor.absolute(24)).squared());
//	public static final ConfiguredFeature<?, ?> ORE_DEBRIS_SMALL = register("ore_debris_small",
//			Feature.SCATTERED_ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NETHER_ORE_REPLACEABLES,
//					Features.States.ANCIENT_DEBRIS, 2, 1.0F)).range(Features.Decorators.RANGE_8_8).squared());
	
	public static ConfiguredFeature<?, ?> buildNetherRockFeature(BlockState bstate, ModOreConfig cfg)
	{
		if (cfg.getRange_type() == ModOreConfig.TRIANGLE)
		{
			return Feature.SCATTERED_ORE.configured(
					new OreConfiguration(OreConfiguration.Predicates.NETHER_ORE_REPLACEABLES, bstate, cfg.getVein_size()))
						.rangeTriangle(cfg.getBottom(), cfg.getTop()).squared().count(cfg.getVein_count());
		} 
		else
		{
			return Feature.SCATTERED_ORE.configured(
					new OreConfiguration(OreConfiguration.Predicates.NETHER_ORE_REPLACEABLES, bstate, cfg.getVein_size()))
						.range(Features.Decorators.RANGE_8_8).squared().count(cfg.getVein_count());
		}
	}

	public static void registerFeature(String modid, String name, ConfiguredFeature<?, ?> cfg_feature)
	{
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(modid, name), cfg_feature);
	}
} // end class
