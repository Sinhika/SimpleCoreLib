package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraft.data.PackOutput;
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
		this.withExistingParent("test_shears", "shears");
	}

} // end class
