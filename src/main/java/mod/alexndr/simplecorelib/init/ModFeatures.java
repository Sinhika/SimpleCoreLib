package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.helpers.OreGenUtils;
import mod.alexndr.simplecorelib.config.SimpleCoreLibConfig;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Register ore gen testing features
 * @author Sinhika
 *
 */
public class ModFeatures
{
    /** Feature<?> registry */
    public static final DeferredRegister<Feature<?>> FEATURES = 
            DeferredRegister.create(ForgeRegistries.Keys.FEATURES, SimpleCoreLib.MODID);

    /** ConfiguredFeature<?, ?> registry */
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, SimpleCoreLib.MODID);
    
    public static RegistryObject<ConfiguredFeature<OreConfiguration, ?>> ORE_ORIGINAL_COPPER =
            CONFIGURED_FEATURES.register("ore_original_copper", 
                    () -> OreGenUtils.createConfiguredOreFeature(
                            OreGenUtils.BuildStandardOreTargetList(ModBlocks.original_copper_ore.get(), ModBlocks.original_copper_ore.get()),
                            SimpleCoreLibConfig.original_copper_cfg));
    
    /** PlacedFeature registry */
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, SimpleCoreLib.MODID);
    
    public static RegistryObject<PlacedFeature> ORIGINAL_COPPER_DEPOSIT = 
            PLACED_FEATURES.register("original_copper_deposit", 
                    ()-> OreGenUtils.createPlacedOreFeature(ORE_ORIGINAL_COPPER.getHolder().get(), 
                                                            SimpleCoreLibConfig.original_copper_cfg));
    
} // end class
