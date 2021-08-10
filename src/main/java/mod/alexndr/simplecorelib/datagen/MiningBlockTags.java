package mod.alexndr.simplecorelib.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
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

	protected void registerMineableTags()
	{
		
	} // end registerMineableTags
	
	// TODO add methods that take lists of blocks and create mineable/mining tags.
} // end class
