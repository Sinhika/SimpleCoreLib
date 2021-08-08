package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.VeryAbstractFurnaceBlock;
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
		
		// item models
		this.itemModels().withExistingParent("test_furnace", new ResourceLocation(SimpleCoreLib.MODID, "block/test_furnace"));
		
		
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

	}

} // end class
