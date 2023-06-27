package mod.alexndr.simplecorelib.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.datagen.MiningItemTags;
import mod.alexndr.simplecorelib.init.ModBlocks;
import mod.alexndr.simplecorelib.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * Again, more complicated than necessary, serves to test MiningItemTags class logic.
 * @author Sinhika
 *
 */
public class SimpleCoreItemTags extends MiningItemTags
{

	public SimpleCoreItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
			CompletableFuture<TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper)
	{
		super(output, lookupProvider, blockTagProvider, SimpleCoreLib.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider lookupProvider)
	{
		super.addTags(lookupProvider);
		this.tag(Tags.Items.SHEARS).add(ModItems.test_shears.get());
	}

    @Override
    protected void registerOreTags()
    {
        this.registerOresInGroundTags(List.of(ModBlocks.original_copper_ore.get()), null, null);
        this.registerOreRateTags(null, List.of(ModBlocks.original_copper_ore.get()), null);
    }

		
} // end class
