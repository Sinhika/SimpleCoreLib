package mod.alexndr.simplecorelib;

import mod.alexndr.simplecorelib.config.ConfigHelper;
import mod.alexndr.simplecorelib.config.ConfigHolder;
import mod.alexndr.simplecorelib.init.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(modid = SimpleCoreLib.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber
{
    private static final Logger LOGGER = LogManager.getLogger(SimpleCoreLib.MODID + " Mod Event Subscriber");
    
   /**
     * This method will be called by Forge when it is time for the mod to register its Items.
     * This method will always be called after the Block registry method.
     */
    @SubscribeEvent
    public static void onRegisterItems(final RegisterEvent event) 
    {
        if (event.getRegistryKey() == Registries.ITEM)
        {
	         // Automatically register BlockItems for all our Blocks
	        ModBlocks.BLOCKS.getEntries().stream()
	                .map(DeferredBlock<Block>::get)
	                // You can do extra filtering here if you don't want some blocks to have an BlockItem automatically registered for them
	                // .filter(block -> needsItemBlock(block))
	                // Register the BlockItem for the block
	                .forEach(block -> {
	                    // Create the new BlockItem with the block and it's properties
	                    final BlockItem blockItem = new BlockItem(block, new Item.Properties());
	                    // Register the BlockItem
	                    event.register(Registries.ITEM,  helper -> {
	                        helper.register(BuiltInRegistries.BLOCK.getKey(block), blockItem);
	                    });
	                });
	        LOGGER.debug("Registered BlockItems");
        }
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent event)
    {
        final ModConfig config = event.getConfig();

        // Rebake the configs when they change
        if (config.getSpec() == ConfigHolder.SERVER_SPEC)
        {
            ConfigHelper.bakeServer(config);
        }
        if (config.getSpec() == ConfigHolder.CLIENT_SPEC)
        {
            ConfigHelper.bakeClient(config);
        }
    } // onModConfigEvent

} // end class
