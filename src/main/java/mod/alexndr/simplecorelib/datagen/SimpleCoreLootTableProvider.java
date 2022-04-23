package mod.alexndr.simplecorelib.datagen;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;

import mod.alexndr.simplecorelib.api.datagen.BlockLootTableProvider;
import mod.alexndr.simplecorelib.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;

public class SimpleCoreLootTableProvider extends BlockLootTableProvider
{

	public SimpleCoreLootTableProvider(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}

	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() 
	{
		tables.clear();
		standardDropTable(ModBlocks.test_furnace.get());
		specialDropTable(ModBlocks.original_copper_ore.get(), Items.RAW_COPPER);
		return tables;
	}

} // end class
