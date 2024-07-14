package mod.alexndr.simplecorelib.api.datagen;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import mod.alexndr.simplecorelib.api.helpers.TagUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class MiningBlockTags extends BlockTagsProvider
{

	public MiningBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, ExistingFileHelper existingFileHelper)
	{
		super(output, lookupProvider, modId, existingFileHelper);
	}

	@Override
	protected void addTags(Provider pProvider) 
	{
		registerOreTags();
		registerMiningTags();	
	}
	

	/**
	 * Override this, obviously.
	 */
	abstract protected void registerOreTags();
    
	/**
	 * Override this, obviously.
	 */
	abstract protected void registerMiningTags();

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
		if (blocks != null && !blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> foo = this.tag(BlockTags.MINEABLE_WITH_PICKAXE);
			blocks.stream().forEach(b -> foo.add(b));
		}
		
		if (stone_blocks != null && !stone_blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> stone = this.tag(BlockTags.NEEDS_STONE_TOOL);
			stone_blocks.stream().forEach(b -> stone.add(b));
		}
		if (iron_blocks != null && !iron_blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> iron = this.tag(BlockTags.NEEDS_IRON_TOOL);
			iron_blocks.stream().forEach(b -> iron.add(b));
		}
		if (diamond_blocks != null && !diamond_blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> diamond = this.tag(BlockTags.NEEDS_DIAMOND_TOOL);
			diamond_blocks.stream().forEach(b -> diamond.add(b));
		}
		// NOTE: needs_netherite_tool is a FORGE tag, not a vanilla Minecraft tag.
		if (netherite_blocks != null && !netherite_blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> netherite = this.tag(TagUtils.forgeBlockTag("needs_netherite_tool"));
			netherite_blocks.stream().forEach(b -> netherite.add(b));
		}
	} // end registerMineableTags
	
    
    /**
     * As registerMineableTags(), but for shovel-diggable things like dirts.
     * 
     * @param blocks - all the blocks that go under the mineable/shovel tag.
     */
    protected void registerShovelableTags(Collection<Block> blocks)
    {
		if (blocks != null && !blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> foo = this.tag(BlockTags.MINEABLE_WITH_SHOVEL);
			blocks.stream().forEach(b -> foo.add(b));
		}
      } // end registerShovelableTags()
	
    
    /**
     * As registerMineableTags(), but for axe-harvestable things like logs. Note that logs 
     * are already covered if included in "minecraft:logs" tag.
     * 
     * @param blocks - all the blocks that go under the mineable/axe tag.
     */
    protected void registerAxeableTags(Collection<Block> blocks)
    {
		if (blocks != null && !blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> foo = this.tag(BlockTags.MINEABLE_WITH_AXE);
			blocks.stream().forEach(b -> foo.add(b));
		}
    } // end registerAxeableTags()
    

	/** 
	 * Creates ores_in_ground forge tags for blocks.
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
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> stone = this.tag(Tags.Blocks.ORES_IN_GROUND_STONE);
			ore_blocks.stream().forEach(b -> stone.add(b));
		}
		if (deepslate_ore_blocks != null && !deepslate_ore_blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> deepslate = this.tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE);
			deepslate_ore_blocks.stream().forEach(b -> deepslate.add(b));
		}
		if (netherrack_ore_blocks != null && !netherrack_ore_blocks.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> netherrack = this.tag(Tags.Blocks.ORES_IN_GROUND_NETHERRACK);
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
	protected void registerOreRateTags(Collection<DropExperienceBlock> sparse_ores, Collection<DropExperienceBlock> singular_ores, 
									   Collection<DropExperienceBlock> dense_ores)
	{
		if (sparse_ores != null && !sparse_ores.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> sparse = this.tag(Tags.Blocks.ORE_RATES_SPARSE);
			sparse_ores.stream().forEach(b -> sparse.add(b));
		}
		if (singular_ores != null && !singular_ores.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> singular = this.tag(Tags.Blocks.ORE_RATES_SINGULAR);
			singular_ores.stream().forEach(b -> singular.add(b));
		}
		if (dense_ores != null && !dense_ores.isEmpty()) {
			IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> dense = this.tag(Tags.Blocks.ORE_RATES_DENSE);
			dense_ores.stream().forEach(b -> dense.add(b));
		}
	} // end registerOreRateTags

} // end class
