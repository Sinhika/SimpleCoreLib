package mod.alexndr.simplecorelib.datagen;

import java.util.List;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.datagen.MiningItemTags;
import mod.alexndr.simplecorelib.api.helpers.TagUtils;
import mod.alexndr.simplecorelib.init.ModBlocks;
import mod.alexndr.simplecorelib.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * Again, more complicated than necessary, serves to test MiningItemTags class logic.
 * @author Sinhika
 *
 */
public class SimpleCoreItemTags extends MiningItemTags
{

	public SimpleCoreItemTags(DataGenerator gen, ExistingFileHelper existingFileHelper)
	{
		super(gen, new SimpleCoreBlockTags(gen, existingFileHelper), SimpleCoreLib.MODID, existingFileHelper);
	}

	@Override
	protected void addTags()
	{
		super.addTags();
		this.tag(TagUtils.forgeTag("shears")).add(ModItems.test_shears.get());
	}

    @Override
    protected void registerOreTags()
    {
        this.registerOresInGroundTags(List.of(ModBlocks.original_copper_ore.get()), null, null);
        this.registerOreRateTags(null, List.of(ModBlocks.original_copper_ore.get()), null);
    }

		
} // end class
