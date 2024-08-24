package mod.alexndr.simplecorelib;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SimpleCoreLib.MODID)
public class SimpleCoreLib
{
    // modid 
    public static final String MODID = "simplecorelib";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    
    // texture atlas for JEI sprites
    public static final ResourceLocation SIMPLE_TEXTURE_ATLAS = new ResourceLocation(MODID, "textures/atlas/gui.png");

    public SimpleCoreLib(IEventBus modEventBus, ModContainer modContainer)
    {
        LOGGER.info("Hello from SimpleCoreLib!");

        // uncomment to register test block-items.
        // register event listeners.
//        modEventBus.addListener(ModEventSubscriber::onRegisterItems);
//        modEventBus.addListener(SimpleCoreLibConfig::onLoad);

        // client events
//        modEventBus.addListener( RegisterMenuScreensEvent.class, ClientModEventSubscriber::registerScreens);

        // uncomment to register test blocks and items
        // Register Deferred Registers (Does not need to be before Configs)
//        ModBlocks.BLOCKS.register(modEventBus);
//        ModItems.ITEMS.register(modEventBus);

        // comment out next line to hide test objects from Creative menu.
//        CreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        // uncomment to register test_furnace GUI and blockentity.
//        ModMenuTypes.MENU_TYPES.register(modEventBus);
//        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);

        // Register Configs
        // uncomment to test configs.
//        modContainer.registerConfig(ModConfig.Type.COMMON, SimpleCoreLibConfig.SPEC);

    } // end SimpleOres()

} // end class SimpleOres
