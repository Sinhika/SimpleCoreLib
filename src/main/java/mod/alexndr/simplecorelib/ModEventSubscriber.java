package mod.alexndr.simplecorelib;

import mod.alexndr.simplecorelib.init.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ModEventSubscriber
{
    private static final Logger LOGGER = LogManager.getLogger(SimpleCoreLib.MODID + " Mod Event Subscriber");
    
//   /**
//     * This method will be called by Forge when it is time for the mod to register its Items.
//     * This method will always be called after the Block registry method.
//     */
    public static void onRegisterItems(final RegisterEvent event)
    {
        if (event.getRegistryKey() == Registries.ITEM)
        {
	         // Automatically register BlockItems for all our Blocks
	        ModBlocks.BLOCKS.getEntries().stream()
                    .map(DeferredHolder::get)
	                // You can do extra filtering here if you don't want some blocks to have an BlockItem automatically registered for them
	                // .filter(block -> needsItemBlock(block))
	                // Register the BlockItem for the block
	                .forEach(block -> {
	                    // Create the new BlockItem with the block and it's properties
	                    // Register the BlockItem
	                    event.register(Registries.ITEM,  helper -> {
	                        helper.register(BuiltInRegistries.BLOCK.getKey(block),
                                            new BlockItem(block, new Item.Properties()));
	                    });
	                });
	        LOGGER.debug("Registered BlockItems");
        }
    }


} // end class
