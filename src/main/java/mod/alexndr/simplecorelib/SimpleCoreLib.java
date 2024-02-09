package mod.alexndr.simplecorelib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.alexndr.simplecorelib.config.ConfigHolder;
import mod.alexndr.simplecorelib.init.ModBlocks;
import mod.alexndr.simplecorelib.init.ModItems;
import mod.alexndr.simplecorelib.init.ModMenuTypes;
import mod.alexndr.simplecorelib.init.ModTileEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SimpleCoreLib.MODID)
public class SimpleCoreLib
{
    // modid 
    public static final String MODID = "simplecorelib";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    
    // texture atlas for JEI sprites
    public static final ResourceLocation SIMPLE_TEXTURE_ATLAS = new ResourceLocation(MODID, "textures/atlas/gui.png");

    public SimpleCoreLib()
    {
        LOGGER.info("Hello from SimpleCoreLib!");
        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register Configs
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        
        // Register Deferred Registers (Does not need to be before Configs)
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        // comment out next line to hide test objects from Creative menu.
        //CreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModMenuTypes.CONTAINER_TYPES.register(modEventBus);
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
    } // end SimpleOres()

} // end class SimpleOres
