package mod.alexndr.simplecorelib.world;

import java.util.List;

import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

/**
 * TODO: COMPLETE REWORK FOR 1.18.1.
 * @see net.minecraft.data.worldgen.features.OreFeatures
 * @see net.minecraft.data.worldgen.placement.OrePlacements
 * @author Sinhika
 *
 */
public final class OreGenUtils
{
	/**
	 * Creates a target list for use in creating a ConfiguredFeature in the Overworld.
	 * @param stoneOre - ore that replaces stone
	 * @param deepslateOre - ore that replaces deepslate
	 * @return target list.
	 */
	public static List<OreConfiguration.TargetBlockState> BuildStandardOreTargetList(Block stoneOre, Block deepslateOre)
	{
		return List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, stoneOre.defaultBlockState()), 
				OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.defaultBlockState()));
	} // end BuildOreTargetList
	
	
	/**
	 * Creates a target list for use in creating a ConfiguredFeature in the Nether.
	 * 
	 * @param netherOre - ore that replaces nether substrate.
	 * @param netherrackOnly - true if only netherrack is to be replaced, false if nether_base_stone to be replaced.
	 * @return target list.
	 */
	public static List<OreConfiguration.TargetBlockState> BuildNetherOreTargetList(Block netherOre, boolean netherrackOnly)
	{
		if (netherrackOnly) {
			return List.of(OreConfiguration.target(OreFeatures.NETHERRACK, netherOre.defaultBlockState()));
		}
		else {
			return List.of(OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, netherOre.defaultBlockState()));
		}
	} // end BuildNetherOreTargetList
	
	/**
	 * 
	 * @param target_list - list of valid blockstates that can be replaced by which ore.
	 * @param vein_size - vein size in blocks?
	 * @param air_decay - chance of not generating if exposed to air.
	 * @return a configured ore feature.
	 */
	public static ConfiguredFeature<OreConfiguration, ?> ConfigureOreFeature(List<OreConfiguration.TargetBlockState> target_list,
																			int vein_size, float air_decay)
	{
		return Feature.ORE.configured(new OreConfiguration(target_list, vein_size, air_decay));
	}
	
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
//	public static ConfiguredFeature<?, ?> buildTargettedOreFeature(List<TargetBlockState> target_list, ModOreConfig cfg)
//	{
//		// TODO
//	} // end buildTargettedOreFeature()

	/**
	 * For backward compatibility: calls buildTargettedOreFeature(). Use that method instead.
	 * @see buildTargettedOreFeature() 
	 */
//	public static ConfiguredFeature<?, ?> buildOverworldOreFeature(List<TargetBlockState> target_list, ModOreConfig cfg)
//	{
//		return buildTargettedOreFeature(target_list, cfg);
//	}
	
	/**
	 * Old default for nether ores - just replaces netherrack. Normally prefer to use buildNetherRockFeature().
	 * @param bstate - ore blockstate that replaces netherrack.
	 * @param cfg - ModOreConfig object that holds the parameters for the ore vein feature.
	 * @return a ConfiguredFeature<?,?>, ready for ore generation.
	 */
//	@Deprecated
//	public static ConfiguredFeature<?, ?> buildNetherOreFeature(BlockState bstate, ModOreConfig cfg)
//	{
//		return Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NETHERRACK, bstate, cfg.getVein_size()))
//				.range(ModOreConfig2RangeDecorator(cfg)).squared().count(cfg.getVein_count());
//	} // end buildNetherOreFeature

	/**
	 * new default for nether ores - replaces base_nether_stone. 
	 * @param bstate - ore blockstate that replaces netherrack.
	 * @param cfg - ModOreConfig object that holds the parameters for the ore vein feature.
	 * @return a ConfiguredFeature<?,?>, ready for ore generation.
	 */
//	public static ConfiguredFeature<?, ?> buildNetherRockFeature(BlockState bstate, ModOreConfig cfg)
//	{
//		return Feature.ORE.configured(
//				new OreConfiguration(OreConfiguration.Predicates.NETHER_ORE_REPLACEABLES, bstate, cfg.getVein_size()))
//					.range(ModOreConfig2RangeDecorator(cfg)).squared().count(cfg.getVein_count());
//	}

//	public static void registerFeature(String modid, String name, ConfiguredFeature<?, ?> cfg_feature)
//	{
//		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(modid, name), cfg_feature);
//	}
} // end class
