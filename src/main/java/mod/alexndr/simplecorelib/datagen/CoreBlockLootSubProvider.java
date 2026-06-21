package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.api.datagen.SimpleBlockLootSubProvider;
import mod.alexndr.simplecorelib.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;

public class CoreBlockLootSubProvider extends SimpleBlockLootSubProvider
{
	@Override
	protected void generate() {
		this.dropNameableBlockEntity(ModBlocks.test_furnace.get());
		this.dropMultiItemsWithFortune(ModBlocks.original_copper_ore.get(), Items.RAW_COPPER, 1, 3);
		this.dropSelf(ModBlocks.test_plate.get());
		this.dropSelf(ModBlocks.test_bars.get());
		this.dropSelf(ModBlocks.test_cube_all.get());
		this.dropSelf(ModBlocks.test_cube_column.get());
		this.dropSelf(ModBlocks.test_sided_cube.get());
	}

    @Override
    public void generate(HolderLookup.Provider pRegistries, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> foo) {
        super.generate(pRegistries, foo);
    }
} // end class CoreBlockLootSubProvider