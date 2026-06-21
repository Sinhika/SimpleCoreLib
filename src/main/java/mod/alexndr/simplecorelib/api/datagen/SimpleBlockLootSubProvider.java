package mod.alexndr.simplecorelib.api.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

/**
 * A LootTableProvider class with helper functions. Based on work by sciwhiz12
 * on the BaseDefense module.
 * 
 * @author Sinhika
 *
 */
public abstract class SimpleBlockLootSubProvider extends BlockLootSubProvider
{
	public SimpleBlockLootSubProvider( HolderLookup.Provider lookupProvider)
	{
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

	// The contents of this Iterable are used for validation.
	// We return an Iterable over our block registry's values here.
	@Override
	protected abstract Iterable<Block> getKnownBlocks();
//	{
//		// The contents of our DeferredRegister.
//		return MyRegistries.BLOCK_REGISTRY.getEntries()
//				.stream()
//				// Cast to Block here, otherwise it will be a ? extends Block and Java will complain.
//				.map(e -> (Block) e.value())
//				.toList();
//	}

	/**
	 * Create a block loot table that drops 1 or 2 slabs depending if the source block is
	 * a single slab or a double slab.
	 * 
	 * @param b slab block being harvested.
	 */
	protected void dropSlab(Block b)
	{
		this.add(b, this::createSlabItemTable);
	}

	/**
	* Create a block loot table that drops multiple items instead of the block itself
	* (e.g., redstone_ore, copper_ore). Assumed to be affected by Fortune-enchanted tools.
	* 
	* @param bb block being harvested.
	* @param item items dropped by block
	* @param min_count minimum number of items dropped.
	* @param max_count maximum number of items dropped.
	*/
	protected void dropMultiItemsWithFortune(Block bb, Item item, int min_count, int max_count)
	{
		this.add(bb, (a) -> { return this.createMultiOreDrops(a, item, min_count, max_count);});
	}
	
	protected LootTable.Builder createMultiOreDrops(Block block, Item item, float min_count, float max_count)
	{
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
		return this.createSilkTouchDispatchTable(
				block,
				(LootPoolEntryContainer.Builder<?>) this.applyExplosionDecay(
						block,
						LootItem.lootTableItem(item)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(min_count, max_count)))
								.apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
				)
		);
	}

    /**
     * Create a block loot table that drops an item-block for the block, but with any
     * custom names copied over. Used for machines and other blocks that might have names.
     * 
     * @param bb block being harvested
     */
    protected void dropNameableBlockEntity(Block bb)
    {
    	this.add(bb, this::createNameableBlockEntityTable);
    }

    // 
    /**
     * Create a block loot table that drops a single door from a door double-block.
     * @param b
     */
    protected void doorDropTable(Block b)
    {
    	this.add(b, this::createDoorTable);
    } 
    
} // end class AbstractLootTableProvider
