package mod.alexndr.simplecorelib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;

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
    } // end SimpleOres()

} // end class SimpleOres
