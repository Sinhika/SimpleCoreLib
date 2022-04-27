package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.content.VeryAbstractFurnaceBlock;
import mod.alexndr.simplecorelib.api.datagen.SimpleBlockStateProvider;
import mod.alexndr.simplecorelib.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SimpleCoreBlockStateProvider extends SimpleBlockStateProvider
{

	public SimpleCoreBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
	{
		super(gen, SimpleCoreLib.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels()
	{
		// block models
		ModelFile testFurnaceModel = this.models().orientable("test_furnace", 
														 new ResourceLocation(SimpleCoreLib.MODID, "block/test_furnace_side"),
														 new ResourceLocation(SimpleCoreLib.MODID, "block/test_furnace_front"),
														 new ResourceLocation(SimpleCoreLib.MODID, "block/test_furnace_top"));
		ModelFile testFurnaceModel_lit = this.models().orientable("block/test_furnace_on", 
				 new ResourceLocation(SimpleCoreLib.MODID, "block/test_furnace_side"),
				 new ResourceLocation(SimpleCoreLib.MODID, "block/test_furnace_front_lit"),
				 new ResourceLocation(SimpleCoreLib.MODID, "block/test_furnace_top"));
		
        ModelFile testOriginalCopperOre = this.models().cubeAll("original_copper_ore", 
		                                                new ResourceLocation(SimpleCoreLib.MODID, "block/original_copper_ore"));
        
        ModelFile testPlateModel = this.models().pressurePlate("test_plate", new ResourceLocation("minecraft", "block/obsidian"));
        ModelFile testPlateModel_down = this.models().pressurePlateDown("test_plate_down", new ResourceLocation("minecraft", "block/obsidian"));
		
		// item models
		this.itemModels().withExistingParent("test_furnace", new ResourceLocation(SimpleCoreLib.MODID, "block/test_furnace"));
		this.itemModels().withExistingParent("original_copper_ore", 
		                                     new ResourceLocation(SimpleCoreLib.MODID, "block/original_copper_ore"));
		this.itemModels().withExistingParent("test_plate", new ResourceLocation(SimpleCoreLib.MODID, "block/test_plate"));
		
		
		// blockstates
		this.buildFurnaceBlockState(ModBlocks.test_furnace.get(), testFurnaceModel, testFurnaceModel_lit);

		this.simpleBlock(ModBlocks.original_copper_ore.get(), new ConfiguredModel(testOriginalCopperOre));
		
		this.buildWeightedPressurePlateBlockState(ModBlocks.test_plate.get(), testPlateModel, testPlateModel_down);
		
	} // end registerStatesAndModels()

} // end class
