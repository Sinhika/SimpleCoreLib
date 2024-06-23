package mod.alexndr.simplecorelib.api.datagen;

import java.util.Set;
import java.util.function.BiConsumer;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

/**
 * A LootTableProvider class with helper functions. Based on work by sciwhiz12
 * on the BaseDefense module.
 * 
 * @author Sinhika
 *
 */
public abstract class SimpleBlockLootSubProvider extends BlockLootSubProvider
{

    protected SimpleBlockLootSubProvider(Set<Item> pExplosionResistant) 
    {
		super(pExplosionResistant, FeatureFlags.REGISTRY.allFlags());
	}

    protected SimpleBlockLootSubProvider()
    {
    	super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }
    
	@Override
	public void generate(HolderLookup.Provider pRegistries, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> foo)
	{
		this.generate();
		// this.map.foreach(foo.accept);
		for (ResourceKey<LootTable> resource: this.map.keySet())
		{
			LootTable.Builder loottable$builder = this.map.get(resource);
			if (loottable$builder == null) {
				continue;
			}
            foo.accept(resource, loottable$builder);
		}
		// clear map
		this.map.clear();
	} // end generate(Bi)

	/**
	 * Create a block loot table that drops 1 or 2 slabs depending if the source block is
	 * a single slab or a double slab.
	 * 
	 * @param b slab block being harvested.
	 */
	protected void dropSlab(Block b)
	{
		this.add(b, (pfoo) -> { return this.createSlabItemTable(pfoo);});
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
	
	protected LootTable.Builder createMultiOreDrops(Block pBlock, Item item, int min_count, int max_count) 
	{
	    return createSilkTouchDispatchTable(pBlock, 
	    		this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
	    				.apply(SetItemCountFunction.setCount(UniformGenerator.between(min_count, max_count)))
	    					.apply(ApplyBonusCount.addUniformBonusCount(Enchantments.FORTUNE))));
	}
	

    /**
     * Create a block loot table that drops an item-block for the block, but with any
     * custom names copied over. Used for machines and other blocks that might have names.
     * 
     * @param bb block being harvested
     */
    protected void dropNameableBlockEntity(Block bb)
    {
    	this.add(bb,  (pfoo) -> {return this.createNameableBlockEntityTable(pfoo);});
    }

    // 
    /**
     * Create a block loot table that drops a single door from a door double-block.
     * @param b
     */
    protected void doorDropTable(Block b)
    {
    	this.add(b, (pbar) -> {return this.createDoorTable(pbar);});
    } 
    
} // end class AbstractLootTableProvider
