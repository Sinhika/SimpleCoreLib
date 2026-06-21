package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.api.datagen.SimpleBlockLootSubProvider;
import mod.alexndr.simplecorelib.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class CoreBlockLootSubProvider extends SimpleBlockLootSubProvider {
	public CoreBlockLootSubProvider(HolderLookup.Provider lookupProvider) {
		super(lookupProvider);
	}

	@Override
	protected Iterable<Block> getKnownBlocks()
	{
		// The contents of our DeferredRegister.
		return ModBlocks.BLOCKS.getEntries()
				.stream()
				// Cast to Block here, otherwise it will be a ? extends Block and Java will complain.
				.map(e -> (Block) e.value())
				.toList();
	}

	@Override
	protected void generate()
	{
		this.dropNameableBlockEntity(ModBlocks.test_furnace.get());
		this.dropMultiItemsWithFortune(ModBlocks.original_copper_ore.get(), Items.RAW_COPPER, 1, 3);
		this.dropSelf(ModBlocks.test_plate.get());
		this.dropSelf(ModBlocks.test_bars.get());
		this.dropSelf(ModBlocks.test_cube_all.get());
		this.dropSelf(ModBlocks.test_cube_column.get());
		this.dropSelf(ModBlocks.test_sided_cube.get());
	}

    @Override
	public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
	{
        super.generate(consumer);
    }
} // end class CoreBlockLootSubProvider