package mod.alexndr.simplecorelib;

import net.neoforged.fml.common.EventBusSubscriber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(modid = SimpleCoreLib.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber
{
    private static final Logger LOGGER = LogManager.getLogger(SimpleCoreLib.MODID + " Mod Event Subscriber");
    
//   /**
//     * This method will be called by Forge when it is time for the mod to register its Items.
//     * This method will always be called after the Block registry method.
//     */
//    @SubscribeEvent
//    public static void onRegisterItems(final RegisterEvent event)
//    {
//        if (event.getRegistryKey() == Registries.ITEM)
//        {
//	         // Automatically register BlockItems for all our Blocks
//	        ModBlocks.BLOCKS.getEntries().stream()
//                    .map(DeferredHolder::get)
//	                // You can do extra filtering here if you don't want some blocks to have an BlockItem automatically registered for them
//	                // .filter(block -> needsItemBlock(block))
//	                // Register the BlockItem for the block
//	                .forEach(block -> {
//	                    // Create the new BlockItem with the block and it's properties
//	                    final BlockItem blockItem = new BlockItem(block, new Item.Properties());
//	                    // Register the BlockItem
//	                    event.register(Registries.ITEM,  helper -> {
//	                        helper.register(BuiltInRegistries.BLOCK.getKey(block), blockItem);
//	                    });
//	                });
//	        LOGGER.debug("Registered BlockItems");
//        }
//    }


} // end class
