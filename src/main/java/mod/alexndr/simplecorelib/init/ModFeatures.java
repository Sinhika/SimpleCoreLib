package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

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
 
    /** PlacedFeature registry */
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, SimpleCoreLib.MODID);
    
   
    
} // end class
