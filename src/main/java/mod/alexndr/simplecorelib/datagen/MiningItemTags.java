package mod.alexndr.simplecorelib.datagen;

import java.util.Collection;

import mod.alexndr.simplecorelib.helpers.TagUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.OreBlock;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MiningItemTags extends ItemTagsProvider 
{

	public MiningItemTags(DataGenerator gen, BlockTagsProvider blockTags, String modId,
			ExistingFileHelper existingFileHelper) 
	{
		super(gen, blockTags, modId, existingFileHelper);
	}

	@Override
	protected void addTags() 
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
	protected void registerOresInGroundTags(Collection<OreBlock> ore_blocks, 
										  Collection<OreBlock> deepslate_ore_blocks,
										  Collection<OreBlock> netherrack_ore_blocks )
	{
		if (ore_blocks != null && !ore_blocks.isEmpty()) {
			TagsProvider.TagAppender<Item> stone_item = this.tag(TagUtils.forgeTag("ores_in_ground/stone"));
			ore_blocks.stream().forEach(b -> stone_item.add(b.asItem()));
		}
		if (deepslate_ore_blocks != null && !deepslate_ore_blocks.isEmpty()) {
			TagsProvider.TagAppender<Item> deepslate = this.tag(TagUtils.forgeTag("ores_in_ground/deepslate"));
			deepslate_ore_blocks.stream().forEach(b -> deepslate.add(b.asItem()));
		}
		if (netherrack_ore_blocks != null && !netherrack_ore_blocks.isEmpty()) {
			TagsProvider.TagAppender<Item> netherrack = this.tag(TagUtils.forgeTag("ores_in_ground/netherrack"));
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
	protected void registerOreRateTags(Collection<OreBlock> sparse_ores, Collection<OreBlock> singular_ores, 
									   Collection<OreBlock> dense_ores)
	{
		if (sparse_ores != null && !sparse_ores.isEmpty()) {
			TagsProvider.TagAppender<Item> sparse = this.tag(TagUtils.forgeTag("ore_rates/sparse"));
			sparse_ores.stream().forEach(b -> sparse.add(b.asItem()));
		}
		if (singular_ores != null && !singular_ores.isEmpty()) {
			TagsProvider.TagAppender<Item> singular = this.tag(TagUtils.forgeTag("ore_rates/singular"));
			singular_ores.stream().forEach(b -> singular.add(b.asItem()));
		}
		if (dense_ores != null && !dense_ores.isEmpty()) {
			TagsProvider.TagAppender<Item> dense = this.tag(TagUtils.forgeTag("ore_rates/dense"));
			dense_ores.stream().forEach(b -> dense.add(b.asItem()));
		}
	} // end registerOreRateTags

} // end class
