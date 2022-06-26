package mod.alexndr.simplecorelib.api.helpers;

/**
 * COMPLETE REWORK FOR 1.18.1.  
 * Further revamped for 1.18.2.
 * @see net.minecraft.data.worldgen.features.OreFeatures
 * @see net.minecraft.data.worldgen.placement.OrePlacements
 * @author Sinhika
 * 
 * HOW TO USE:
 <code>
    // example: original_copper_ore
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
//	public static List<OreConfiguration.TargetBlockState> BuildStandardOreTargetList(Block stoneOre, Block deepslateOre)
//	{
//		return List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, stoneOre.defaultBlockState()), 
//				OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.defaultBlockState()));
//	} // end BuildOreTargetList
	
	
	/**
	 * Creates a target list for use in creating a ConfiguredFeature in the Nether.
	 * 
	 * @param netherOre - ore that replaces nether substrate.
	 * @param netherrackOnly - true if only netherrack is to be replaced, false if nether_base_stone to be replaced.
	 * @return target list.
	 */
//	public static List<OreConfiguration.TargetBlockState> BuildNetherOreTargetList(Block netherOre, boolean netherrackOnly)
//	{
//		if (netherrackOnly) {
//			return List.of(OreConfiguration.target(OreFeatures.NETHERRACK, netherOre.defaultBlockState()));
//		}
//		else {
//			return List.of(OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, netherOre.defaultBlockState()));
//		}
//	} // end BuildNetherOreTargetList
	
	/**
	 * Creates an OreConfiguration, ready for use with createConfiguration() factory method.
	 * @param target_list - list of valid blockstates that can be replaced by which ore.
	 * @param vein_size - vein size in blocks?
	 * @param air_decay - chance of not generating if exposed to air.
	 * @return a configured ore feature.
	 */
//	public static OreConfiguration ConfigureOreFeature(List<OreConfiguration.TargetBlockState> target_list,
//																			int vein_size, float air_decay)
//	{
//		return new OreConfiguration(target_list, vein_size, air_decay);
//	}
	
    /**
     * Factory function that supplies a ConfiguredFeature for a RegistryObject. 
     * @param target_list
     * @param cfg
     * @return
     */
//	public static ConfiguredFeature<OreConfiguration, ?> createConfiguredOreFeature(
//	        List<OreConfiguration.TargetBlockState> target_list, Lazy<ModOreConfig> cfg)
//	{
//	    return new ConfiguredFeature<>(Feature.ORE, 
//	            OreGenUtils.ConfigureOreFeature(target_list, cfg.get().getVein_size(), cfg.get().getAirDecay()));
//	}
	
	/**
	 * Factory function that supplies a PlacedFeature for a RegistryObject.
	 * @param cf
	 * @param placements
	 * @return
	 */
//	public static PlacedFeature createPlacedOreFeature( Holder<ConfiguredFeature<OreConfiguration, ?>> cf, 
//	                                                    Lazy<ModOreConfig> opt_cfg)
//	{
//	    return new PlacedFeature(Holder.hackyErase(cf), List.copyOf(ConfigurePlacementModifiers(opt_cfg.get())));
//	}
	
	/**
	 * Creates a List<PlacementModifier>, ready for registration with PlacementUtils.register().
	 * 
	 * @param cfg - mod config parameters.
	 * @return a List<PlacementModifier>.
	 */
//	public static List<PlacementModifier> ConfigurePlacementModifiers(ModOreConfig cfg)
//	{
//		List<PlacementModifier> pmodifiers = null;
//		HeightRangePlacement hplace;
//		
//		switch(cfg.getRange_type())
//		{
//		case ModOreConfig.UNIFORM:
//			hplace = MakeUniformPlacement(cfg.getBottom(), cfg.getTop());
//			if (cfg.get_commonality()) {
//				pmodifiers = OrePlacements.commonOrePlacement(cfg.getVein_count(), hplace);
//			}
//			else {
//				pmodifiers = OrePlacements.rareOrePlacement(cfg.getVein_count(), hplace);
//			}
//			break;
//		case ModOreConfig.TRIANGLE:
//			hplace = MakeTrianglePlacement(cfg.getBottom(), cfg.getTop());
//			if (cfg.get_commonality()) {
//				pmodifiers = OrePlacements.commonOrePlacement(cfg.getVein_count(), hplace);
//			}
//			else {
//				pmodifiers = OrePlacements.rareOrePlacement(cfg.getVein_count(), hplace);
//			}
//			break;
//		case ModOreConfig.RANGE_4_4:
//			if (cfg.get_commonality()) {
//				pmodifiers = OrePlacements.commonOrePlacement(cfg.getVein_count(), PlacementUtils.RANGE_4_4);
//			}
//			else {
//				pmodifiers = OrePlacements.rareOrePlacement(cfg.getVein_count(), PlacementUtils.RANGE_4_4);
//			}
//			break;
//		case ModOreConfig.RANGE_8_8:
//			if (cfg.get_commonality()) {
//				pmodifiers = OrePlacements.commonOrePlacement(cfg.getVein_count(), PlacementUtils.RANGE_8_8);
//			}
//			else {
//				pmodifiers = OrePlacements.rareOrePlacement(cfg.getVein_count(), PlacementUtils.RANGE_8_8);
//			}
//			break;
//		case ModOreConfig.RANGE_10_10:
//			if (cfg.get_commonality()) {
//				pmodifiers = OrePlacements.commonOrePlacement(cfg.getVein_count(), PlacementUtils.RANGE_10_10);
//			}
//			else {
//				pmodifiers = OrePlacements.rareOrePlacement(cfg.getVein_count(), PlacementUtils.RANGE_10_10);
//			}
//			break;
//		case ModOreConfig.FULL_RANGE:
//			if (cfg.get_commonality()) {
//				pmodifiers = OrePlacements.commonOrePlacement(cfg.getVein_count(), PlacementUtils.FULL_RANGE);
//			}
//			else {
//				pmodifiers = OrePlacements.rareOrePlacement(cfg.getVein_count(), PlacementUtils.FULL_RANGE);
//			}
//			break;
//		} // end switch
//		
//		return pmodifiers;
//	} // end ConfigurePlacementModifiers
	
	/**
	 * Create a standard triangular height distribution.
	 * @param lower - VerticalAnchor of lower end
	 * @param upper - VerticalAnchor of upper end
	 * @return a HeightRangePlacement.
	 */
//    public static HeightRangePlacement MakeTrianglePlacement( VerticalAnchor lower, VerticalAnchor upper )
//    {
//    	return HeightRangePlacement.triangle(lower, upper);
//    }
    
	/**
	 * Create a standard triangular height distribution.
	 * @param lower - VerticalAnchor of lower end
	 * @param upper - VerticalAnchor of upper end
	 * @return a HeightRangePlacement.
	 */
//    public static HeightRangePlacement MakeUniformPlacement( VerticalAnchor lower, VerticalAnchor upper )
//    {
//        return HeightRangePlacement.uniform(lower, upper);
//    }
} // end class
