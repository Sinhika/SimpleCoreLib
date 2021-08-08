package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.helpers.TagUtils;
import mod.alexndr.simplecorelib.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SimpleCoreBlockTags extends BlockTagsProvider
{

	public SimpleCoreBlockTags(DataGenerator gen, ExistingFileHelper existingFileHelper) 
	{
		super(gen, SimpleCoreLib.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() 
	{
		this.tag(TagUtils.modBlockTag("minecraft", "mineable/pickaxe"))
			.add(ModBlocks.test_furnace.get());
	}

	
} // end class
