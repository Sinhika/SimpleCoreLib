package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.helpers.TagUtils;
import mod.alexndr.simplecorelib.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SimpleCoreItemTags extends ItemTagsProvider
{

	public SimpleCoreItemTags(DataGenerator gen, ExistingFileHelper existingFileHelper)
	{
		super(gen, new SimpleCoreBlockTags(gen, existingFileHelper), SimpleCoreLib.MODID, existingFileHelper);
	}

	@Override
	protected void addTags()
	{
		this.tag(TagUtils.forgeTag("shears")).add(ModItems.test_shears.get());
	}

		
} // end class
