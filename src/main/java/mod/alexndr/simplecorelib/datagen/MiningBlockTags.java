package mod.alexndr.simplecorelib.datagen;

import java.util.Collection;

import mod.alexndr.simplecorelib.helpers.TagUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MiningBlockTags extends BlockTagsProvider
{

	public MiningBlockTags(DataGenerator gen, String modId, ExistingFileHelper existingFileHelper)
	{
		super(gen, modId, existingFileHelper);
	}

	@Override
	protected void addTags()
	{
		super.addTags();
		registerOreTags();
		registerMiningTags();
	}

	/**
	 * Override this, obviously.
	 */
	protected void registerOreTags() 
    {}
    
	/**
	 * Override this, obviously.
	 */
	protected void registerMiningTags()
    {}

	/**
	 * Generate tags for new tool tier system.
	 * 
	 * @param blocks  blocks that require a pickaxe to mine; includes those that require a wooden pickaxe.
	 * @param stone_blocks  blocks that require a stone pickaxe to mine.
	 * @param iron_blocks  blocks that require an iron pickaxe to mine.
	 * @param diamond_blocks  blocks that require a diamond pickaxe to mine.
	 * @param netherite_blocks blocks that require a netherite pickaxe to mine. (Forge tag only)
	 */
	protected void registerMineableTags(Collection<Block> blocks, 
			Collection<Block> stone_blocks, Collection<Block> iron_blocks, 
			Collection<Block> diamond_blocks, Collection<Block> netherite_blocks)
	{
		TagsProvider.TagAppender<Block> foo = this.tag(TagUtils.modBlockTag("minecraft", "mineable/pickaxe"));
		blocks.stream().forEach(b -> foo.add(b));
		
		if (stone_blocks != null && !stone_blocks.isEmpty()) {
			TagsProvider.TagAppender<Block> stone = this.tag(TagUtils.modBlockTag("minecraft", "needs_stone_tool"));
			stone_blocks.stream().forEach(b -> stone.add(b));
		}
		if (iron_blocks != null && !iron_blocks.isEmpty()) {
			TagsProvider.TagAppender<Block> iron = this.tag(TagUtils.modBlockTag("minecraft", "needs_iron_tool"));
			iron_blocks.stream().forEach(b -> iron.add(b));
		}
		if (diamond_blocks != null && !diamond_blocks.isEmpty()) {
			TagsProvider.TagAppender<Block> diamond = this.tag(TagUtils.modBlockTag("minecraft", "needs_diamond_tool"));
			diamond_blocks.stream().forEach(b -> diamond.add(b));
		}
		// NOTE: needs_netherite_tool is a FORGE tag, not a vanilla Minecraft tag.
		if (netherite_blocks != null && !netherite_blocks.isEmpty()) {
			TagsProvider.TagAppender<Block> netherite = this.tag(TagUtils.forgeBlockTag("needs_netherite_tool"));
			netherite_blocks.stream().forEach(b -> netherite.add(b));
		}
	} // end registerMineableTags
	
	
	/** 
	 * Creates ores_in_ground forge tags for blocks.
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
			TagsProvider.TagAppender<Block> stone = this.tag(TagUtils.forgeBlockTag("ores_in_ground/stone"));
			ore_blocks.stream().forEach(b -> stone.add(b));
		}
		if (deepslate_ore_blocks != null && !deepslate_ore_blocks.isEmpty()) {
			TagsProvider.TagAppender<Block> deepslate = this.tag(TagUtils.forgeBlockTag("ores_in_ground/deepslate"));
			deepslate_ore_blocks.stream().forEach(b -> deepslate.add(b));
		}
		if (netherrack_ore_blocks != null && !netherrack_ore_blocks.isEmpty()) {
			TagsProvider.TagAppender<Block> netherrack = this.tag(TagUtils.forgeBlockTag("ores_in_ground/netherrack"));
			netherrack_ore_blocks.stream().forEach(b -> netherrack.add(b));
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
			TagsProvider.TagAppender<Block> sparse = this.tag(TagUtils.forgeBlockTag("ore_rates/sparse"));
			sparse_ores.stream().forEach(b -> sparse.add(b));
		}
		if (singular_ores != null && !singular_ores.isEmpty()) {
			TagsProvider.TagAppender<Block> singular = this.tag(TagUtils.forgeBlockTag("ore_rates/singular"));
			singular_ores.stream().forEach(b -> singular.add(b));
		}
		if (dense_ores != null && !dense_ores.isEmpty()) {
			TagsProvider.TagAppender<Block> dense = this.tag(TagUtils.forgeBlockTag("ore_rates/dense"));
			dense_ores.stream().forEach(b -> dense.add(b));
		}
	} // end registerOreRateTags
	
} // end class
