package mod.alexndr.simplecorelib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.alexndr.simplecorelib.init.ModBlocks;
import mod.alexndr.simplecorelib.init.ModContainerTypes;
import mod.alexndr.simplecorelib.init.ModTileEntityTypes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SimpleCoreLib.MODID)
public class SimpleCoreLib
{
    // modid 
    public static final String MODID = "simplecorelib";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public SimpleCoreLib()
    {
        LOGGER.info("Hello from SimpleCoreLib!");
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register Deferred Registers (Does not need to be before Configs)
        ModBlocks.BLOCKS.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
    } // end SimpleOres()

} // end class SimpleOres
