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
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class OreGenUtils
{
	protected static RangeDecoratorConfiguration ModOreConfig2RangeDecorator(ModOreConfig cfg)
	{
		switch (cfg.getRange_type())
		{
		case ModOreConfig.FULL_RANGE:
			return Features.Decorators.FULL_RANGE;
		case ModOreConfig.RANGE_10_10:
			return Features.Decorators.RANGE_10_10;
		case ModOreConfig.RANGE_4_4:
			return Features.Decorators.RANGE_4_4;
		case ModOreConfig.RANGE_8_8:
			return Features.Decorators.RANGE_8_8;
		case ModOreConfig.TRIANGLE:
			return new RangeDecoratorConfiguration(TrapezoidHeight.of(cfg.getBottom(), cfg.getTop()));
		case ModOreConfig.UNIFORM:
		default:
			return new RangeDecoratorConfiguration(UniformHeight.of(cfg.getBottom(), cfg.getTop()));
		}
	} // end ModOreConfig2RangeDecorator
	
	
	/**
	 * Build an ore features with a specific target list it can replace, using OreConfiguration.target().
	 *  
	 * @param target_list - the list of TargetBlockStates that specific ores can replace.
	 * Example:
	 * 		public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_COPPER_TARGET_LIST = ImmutableList.of(
	 *			OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, Features.States.COPPER_ORE),
	 *  		OreConfiguration.target(OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES, Features.States.DEEPSLATE_COPPER_ORE));
     *
	 * @param cfg - ModOreConfig object that holds the parameters for the ore vein feature.
	 * @return a ConfiguredFeature<?,?>, ready for ore generation.
	 * 
	 */
	public static ConfiguredFeature<?, ?> buildTargettedOreFeature(List<TargetBlockState> target_list, ModOreConfig cfg)
	{
			return Feature.ORE.configured(new OreConfiguration(target_list, cfg.getVein_size()))
					.range(ModOreConfig2RangeDecorator(cfg)).squared().count(cfg.getVein_count());
	} // end buildTargettedOreFeature()

	/**
	 * For backward compatibility: calls buildTargettedOreFeature(). Use that method instead.
	 * @see buildTargettedOreFeature() 
	 */
	@Deprecated
	public static ConfiguredFeature<?, ?> buildOverworldOreFeature(List<TargetBlockState> target_list, ModOreConfig cfg)
	{
		return buildTargettedOreFeature(target_list, cfg);
	}
	
	/**
	 * Old default for nether ores - just replaces netherrack. Normally prefer to use buildNetherRockFeature().
	 * @param bstate - ore blockstate that replaces netherrack.
	 * @param cfg - ModOreConfig object that holds the parameters for the ore vein feature.
	 * @return a ConfiguredFeature<?,?>, ready for ore generation.
	 */
	@Deprecated
	public static ConfiguredFeature<?, ?> buildNetherOreFeature(BlockState bstate, ModOreConfig cfg)
	{
		return Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NETHERRACK, bstate, cfg.getVein_size()))
				.range(ModOreConfig2RangeDecorator(cfg)).squared().count(cfg.getVein_count());
	} // end buildNetherOreFeature

	/**
	 * new default for nether ores - replaces base_nether_stone. 
	 * @param bstate - ore blockstate that replaces netherrack.
	 * @param cfg - ModOreConfig object that holds the parameters for the ore vein feature.
	 * @return a ConfiguredFeature<?,?>, ready for ore generation.
	 */
	public static ConfiguredFeature<?, ?> buildNetherRockFeature(BlockState bstate, ModOreConfig cfg)
	{
		return Feature.SCATTERED_ORE.configured(
				new OreConfiguration(OreConfiguration.Predicates.NETHER_ORE_REPLACEABLES, bstate, cfg.getVein_size()))
					.range(ModOreConfig2RangeDecorator(cfg)).squared().count(cfg.getVein_count());
	}

	public static void registerFeature(String modid, String name, ConfiguredFeature<?, ?> cfg_feature)
	{
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(modid, name), cfg_feature);
	}
} // end class
