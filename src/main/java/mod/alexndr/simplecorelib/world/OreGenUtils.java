package mod.alexndr.simplecorelib.world;

import java.util.List;

import mod.alexndr.simplecorelib.config.ModOreConfig;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

/**
 * COMPLETE REWORK FOR 1.18.1.
 * @see net.minecraft.data.worldgen.features.OreFeatures
 * @see net.minecraft.data.worldgen.placement.OrePlacements
 * @author Sinhika
 * 
 * HOW TO USE:
 <code>
    // example: tin ore
 	ModOreConfig cfg = new ModOreConfig(ModOreConfig.TRIANGLE, 7, 20, true, VerticalAnchor.absolute(20), VerticalAnchor.absolute(90);
 	
 	// get the target list.
 	List<OreConfiguration.TargetBlockState> tlist = 
 		BuildStandardOreTargetList(ModBlocks.tin_ore.get(), ModBlocks.deepslate_tin_ore.get());
 		
 	// build the ore feature.
 	ConfiguredFeature<OreConfiguration, ?> ore_cfg = ConfigureOreFeature(tlist, cfg.getVein_size(), 0.0F);
 	
 	// register the feature.
 	public static ConfiguredFeature<?, ?> ORE_TIN = FeatureUtils.register("ore_tin", ore_cfg);
 	
 	// bulid the ore placement.
 	PlacedFeature pfeature = ConfigurePlacedFeature(cfg, ORE_TIN);
 	
 	// register the placement.
 	public static PlacedFeature ORE_TIN_LOWER = PlacementUtils.register("ore_tin_lower", pfeature);
 </code>
 OR as one ugly mess:
 <code>
 	public static ConfiguredFeature<?, ?> ORE_TIN = 
 		FeatureUtils.register("ore_tin", 
 		   						ConfigureOreFeature(BuildStandardOreTargetList(ModBlocks.tin_ore.get(), 
 																			   ModBlocks.deepslate_tin_ore.get()), 
 													cfg.getVein_size(), 0.0F));
 													
 	public static PlacedFeature ORE_TIN_LOWER = PlacementUtils.register("ore_tin_lower", ConfigurePlacedFeature(cfg, ORE_TIN));												
 	
 </code> 
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
	 * Creates a ConfiguredFeature<OreConfiguration,?>, ready for registration with FeatureUtils.register().
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
	 * Creates a PlacedFeature, ready for registration with PlacementUtils.register().
	 * 
	 * @param cfg - mod config parameters.
	 * @param orecfg - previously-configured ConfiguredFeature<>
	 * @return a PlacedFeature.
	 */
	public static PlacedFeature ConfigurePlacedFeature(ModOreConfig cfg, ConfiguredFeature<OreConfiguration, ?> orecfg)
	{
		List<PlacementModifier> pmodifiers = null;
		HeightRangePlacement hplace;
		
		switch(cfg.getRange_type())
		{
		case ModOreConfig.UNIFORM:
			hplace = MakeUniformPlacement(cfg.getBottom(), cfg.getTop());
			if (cfg.get_commonality()) {
				pmodifiers = OrePlacements.commonOrePlacement(cfg.getVein_count(), hplace);
			}
			else {
				pmodifiers = OrePlacements.rareOrePlacement(cfg.getVein_count(), hplace);
			}
			break;
		case ModOreConfig.TRIANGLE:
			hplace = MakeTrianglePlacement(cfg.getBottom(), cfg.getTop());
			if (cfg.get_commonality()) {
				pmodifiers = OrePlacements.commonOrePlacement(cfg.getVein_count(), hplace);
			}
			else {
				pmodifiers = OrePlacements.rareOrePlacement(cfg.getVein_count(), hplace);
			}
			break;
		case ModOreConfig.RANGE_4_4:
			if (cfg.get_commonality()) {
				pmodifiers = OrePlacements.commonOrePlacement(cfg.getVein_count(), PlacementUtils.RANGE_4_4);
			}
			else {
				pmodifiers = OrePlacements.rareOrePlacement(cfg.getVein_count(), PlacementUtils.RANGE_4_4);
			}
			break;
		case ModOreConfig.RANGE_8_8:
			if (cfg.get_commonality()) {
				pmodifiers = OrePlacements.commonOrePlacement(cfg.getVein_count(), PlacementUtils.RANGE_8_8);
			}
			else {
				pmodifiers = OrePlacements.rareOrePlacement(cfg.getVein_count(), PlacementUtils.RANGE_8_8);
			}
			break;
		case ModOreConfig.RANGE_10_10:
			if (cfg.get_commonality()) {
				pmodifiers = OrePlacements.commonOrePlacement(cfg.getVein_count(), PlacementUtils.RANGE_10_10);
			}
			else {
				pmodifiers = OrePlacements.rareOrePlacement(cfg.getVein_count(), PlacementUtils.RANGE_10_10);
			}
			break;
		case ModOreConfig.FULL_RANGE:
			if (cfg.get_commonality()) {
				pmodifiers = OrePlacements.commonOrePlacement(cfg.getVein_count(), PlacementUtils.FULL_RANGE);
			}
			else {
				pmodifiers = OrePlacements.rareOrePlacement(cfg.getVein_count(), PlacementUtils.FULL_RANGE);
			}
			break;
		} // end switch
		
		return orecfg.placed(pmodifiers);
	} // end ConfigurePlacedFeature
	
	/**
	 * Create a standard triangular height distribution.
	 * @param lower - VerticalAnchor of lower end
	 * @param upper - VerticalAnchor of upper end
	 * @return a HeightRangePlacement.
	 */
    public static HeightRangePlacement MakeTrianglePlacement( VerticalAnchor lower, VerticalAnchor upper )
    {
    	return HeightRangePlacement.triangle(lower, upper);
    }
    
	/**
	 * Create a standard triangular height distribution.
	 * @param lower - VerticalAnchor of lower end
	 * @param upper - VerticalAnchor of upper end
	 * @return a HeightRangePlacement.
	 */
    public static HeightRangePlacement MakeUniformPlacement( VerticalAnchor lower, VerticalAnchor upper )
    {
        return HeightRangePlacement.uniform(lower, upper);
    }
} // end class
