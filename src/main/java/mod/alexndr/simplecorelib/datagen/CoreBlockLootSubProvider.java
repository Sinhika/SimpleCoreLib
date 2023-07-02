package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.api.datagen.SimpleBlockLootSubProvider;
import mod.alexndr.simplecorelib.init.ModBlocks;
import net.minecraft.world.item.Items;

public class CoreBlockLootSubProvider extends SimpleBlockLootSubProvider
{
	@Override
	protected void generate() {
		this.dropSelf(ModBlocks.test_furnace.get());
		this.dropSelf(ModBlocks.test_plate.get());
		this.dropOther(ModBlocks.original_copper_ore.get(), Items.RAW_COPPER);
	}
	
} // end class CoreBlockLootSubProvider