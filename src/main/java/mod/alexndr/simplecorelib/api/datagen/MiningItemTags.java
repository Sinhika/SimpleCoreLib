package mod.alexndr.simplecorelib.api.datagen;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MiningItemTags extends ItemTagsProvider 
{

	public MiningItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, 
			CompletableFuture<TagLookup<Block>> blockTagProvider, String modId,
			ExistingFileHelper existingFileHelper) 
	{
		super(output, lookupProvider, blockTagProvider, modId, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider) 
	{
		registerOreTags();
	}

	/**
	 * Override this, obviously.
	 */
	protected void registerOreTags() 
    {}
    

	/** 
	 * Creates ores_in_ground forge tags for item-blocks.
	 * 
	 * @param ore_blocks
	 * @param deepslate_ore_blocks
	 * @param netherrack_ore_blocks
	 */
	protected void registerOresInGroundTags(Collection<DropExperienceBlock> ore_blocks, 
										  Collection<DropExperienceBlock> deepslate_ore_blocks,
										  Collection<DropExperienceBlock> netherrack_ore_blocks )
	{
		if (ore_blocks != null && !ore_blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> stone_item = this.tag(Tags.Items.ORES_IN_GROUND_STONE);
			ore_blocks.stream().forEach(b -> stone_item.add(b.asItem()));
		}
		if (deepslate_ore_blocks != null && !deepslate_ore_blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> deepslate = this.tag(Tags.Items.ORES_IN_GROUND_DEEPSLATE);
			deepslate_ore_blocks.stream().forEach(b -> deepslate.add(b.asItem()));
		}
		if (netherrack_ore_blocks != null && !netherrack_ore_blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> netherrack = this.tag(Tags.Items.ORES_IN_GROUND_NETHERRACK);
			netherrack_ore_blocks.stream().forEach(b -> netherrack.add(b.asItem()));
		}
	} // end registerOresInGroundTags

	/**
	 * Creates forge:ore_rates tags for blocks.
	 * 
	 * @param sparse_ores ore blocks that drop less than a standard unit. e.g., nether_gold_ore drops nuggets.
	 * @param singular_ores blocks that drop a single unit (the usual case).
	 * @param dense_ores blocks that drop multiple units, e.g. redstone_ore or copper_ore.
	 */
	protected void registerOreRateTags(Collection<DropExperienceBlock> sparse_ores, Collection<DropExperienceBlock> singular_ores, 
									   Collection<DropExperienceBlock> dense_ores)
	{
		if (sparse_ores != null && !sparse_ores.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> sparse = this.tag(Tags.Items.ORE_RATES_SPARSE);
			sparse_ores.stream().forEach(b -> sparse.add(b.asItem()));
		}
		if (singular_ores != null && !singular_ores.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> singular = this.tag(Tags.Items.ORE_RATES_SINGULAR);
			singular_ores.stream().forEach(b -> singular.add(b.asItem()));
		}
		if (dense_ores != null && !dense_ores.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> dense = this.tag(Tags.Items.ORE_RATES_DENSE);
			dense_ores.stream().forEach(b -> dense.add(b.asItem()));
		}
	} // end registerOreRateTags

} // end class
