package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SimpleCoreItemModelProvider extends ItemModelProvider
{

	public SimpleCoreItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
	{
		super(generator, SimpleCoreLib.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		this.withExistingParent("test_shears", "shears");
	}

} // end class
