package mod.alexndr.simplecorelib.datagen;

import java.util.List;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.datagen.MiningBlockTags;
import mod.alexndr.simplecorelib.api.helpers.TagUtils;
import mod.alexndr.simplecorelib.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * More complicated than strictly necessary, but also serves to test that we didn't bollix up
 * the MiningBlockTags logic
 * @author Sinhika.
 *
 */
public class SimpleCoreBlockTags extends MiningBlockTags
{

	public SimpleCoreBlockTags(DataGenerator gen, ExistingFileHelper existingFileHelper) 
	{
		super(gen, SimpleCoreLib.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() 
	{
		super.addTags();  // always call super here, it calls registerOres() and registerMiningTags().
		registerMiscTags();
	}

	@Override
	protected void registerMiningTags() 
	{
		registerMineableTags(List.of(ModBlocks.test_furnace.get(), ModBlocks.original_copper_ore.get(), ModBlocks.test_plate.get()), 
				List.of(ModBlocks.original_copper_ore.get()), List.of(), List.of(), List.of());
	}

    @Override
    protected void registerOreTags()
    {
        registerOresInGroundTags(List.of(ModBlocks.original_copper_ore.get()), null, null);
        registerOreRateTags(null, List.of(ModBlocks.original_copper_ore.get()), null);
    }

    private void registerMiscTags()
    {
        this.tag(TagUtils.modBlockTag("minecraft", "pressure_plates"))
            .add(ModBlocks.test_plate.get());
        
    }

} // end class
