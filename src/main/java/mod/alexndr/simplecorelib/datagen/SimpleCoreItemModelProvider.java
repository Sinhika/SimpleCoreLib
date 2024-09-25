package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SimpleCoreItemModelProvider extends ItemModelProvider
{

	public SimpleCoreItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper)
	{
		super(output, SimpleCoreLib.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		this.withExistingParent("test_item", "generated")
				.texture("layer0", new ResourceLocation(SimpleCoreLib.MODID, "item/test_item"));
		this.withExistingParent("test_item2", "generated")
				.texture("layer0", new ResourceLocation(SimpleCoreLib.MODID, "item/test_item2"));

		this.withExistingParent("test_shears", "shears");
	}

} // end class
