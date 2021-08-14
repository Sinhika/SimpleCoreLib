package mod.alexndr.simplecorelib.datagen;

import java.util.Collection;

import mod.alexndr.simplecorelib.helpers.TagUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
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
		
	}

	protected void registerMineableTags(Collection<Block> blocks, 
			Collection<Block> stone_blocks, Collection<Block> iron_blocks, 
			Collection<Block> diamond_blocks, Collection<Block> netherite_blocks)
	{
		TagsProvider.TagAppender<Block> foo = this.tag(TagUtils.modBlockTag("minecraft", "mineable/pickaxe"));
		blocks.stream().forEach(b -> foo.add(b));
		
		TagsProvider.TagAppender<Block> stone = this.tag(TagUtils.modBlockTag("minecraft", "needs_stone_tool"));
		stone_blocks.stream().forEach(b -> stone.add(b));

		TagsProvider.TagAppender<Block> iron = this.tag(TagUtils.modBlockTag("minecraft", "needs_iron_tool"));
		iron_blocks.stream().forEach(b -> iron.add(b));

		TagsProvider.TagAppender<Block> diamond = this.tag(TagUtils.modBlockTag("minecraft", "needs_diamond_tool"));
		diamond_blocks.stream().forEach(b -> diamond.add(b));

		TagsProvider.TagAppender<Block> netherite = this.tag(TagUtils.modBlockTag("minecraft", "needs_netherite_tool"));
		netherite_blocks.stream().forEach(b -> netherite.add(b));
	} // end registerMineableTags
	
	// TODO add methods that take lists of blocks and create mineable/mining tags.
} // end class
