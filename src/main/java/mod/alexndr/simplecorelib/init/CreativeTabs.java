package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


/**
 * This class holds all our CreativeTabs (Formerly called ItemGroups).
 * Static initialisers are fine here.
 *
 */
public final class CreativeTabs {

	// formerly MOD_ITEM_GROUP
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = 
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SimpleCoreLib.MODID);
	
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SIMPLECORE_TAB
			= CREATIVE_MODE_TABS.register("simplecore_tab",
			() -> CreativeModeTab.builder()
				.title(Component.translatable("item_group." + SimpleCoreLib.MODID + ".simplecore_tab"))
				.icon(() -> ModBlocks.original_copper_ore.get().asItem().getDefaultInstance())
				.displayItems((parameters, output) -> {
					output.acceptAll(ModBlocks.BLOCKS.getEntries().stream()
										.map(DeferredHolder::get)
										.map(b -> (new ItemStack(b.asItem())))
										.toList()
										);
					output.acceptAll(ModItems.ITEMS.getEntries().stream()
							.map(DeferredHolder::get)
							.map(ItemStack::new)
							.toList()
							);
				}).build());
} // end class
