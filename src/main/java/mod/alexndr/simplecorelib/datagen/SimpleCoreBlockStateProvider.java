package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.content.MultifunctionPressurePlateBlock;
import mod.alexndr.simplecorelib.api.content.VeryAbstractFurnaceBlock;
import mod.alexndr.simplecorelib.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SimpleCoreBlockStateProvider extends BlockStateProvider
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
		this.getVariantBuilder(ModBlocks.test_furnace.get())
			.partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.NORTH).with(BlockStateProperties.LIT, false)
			.setModels(new ConfiguredModel(testFurnaceModel))
			.partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.SOUTH).with(BlockStateProperties.LIT, false)
			.setModels(new ConfiguredModel(testFurnaceModel, 0, 180, false))
			.partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.WEST).with(BlockStateProperties.LIT, false)
			.setModels(new ConfiguredModel(testFurnaceModel, 0, 270, false))
			.partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.EAST).with(BlockStateProperties.LIT, false)
			.setModels(new ConfiguredModel(testFurnaceModel, 0, 90, false))
			.partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.NORTH).with(BlockStateProperties.LIT, true)
			.setModels(new ConfiguredModel(testFurnaceModel_lit))
			.partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.SOUTH).with(BlockStateProperties.LIT, true)
			.setModels(new ConfiguredModel(testFurnaceModel_lit, 0, 180, false))
			.partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.WEST).with(BlockStateProperties.LIT, true)
			.setModels(new ConfiguredModel(testFurnaceModel_lit, 0, 270, false))
			.partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.EAST).with(BlockStateProperties.LIT, true)
			.setModels(new ConfiguredModel(testFurnaceModel_lit, 0, 90, false));

		this.simpleBlock(ModBlocks.original_copper_ore.get(), new ConfiguredModel(testOriginalCopperOre));
		
		this.getVariantBuilder(ModBlocks.test_plate.get())
		    .partialState().with(MultifunctionPressurePlateBlock.POWER, 0).setModels(new ConfiguredModel(testPlateModel))
		    .partialState().with(MultifunctionPressurePlateBlock.POWER, 1).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 2).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 3).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 4).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 5).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 6).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 7).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 8).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 9).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 10).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 11).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 12).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 13).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 14).setModels(new ConfiguredModel(testPlateModel_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 15).setModels(new ConfiguredModel(testPlateModel_down));
		
	} // end registerStatesAndModels()

} // end class
