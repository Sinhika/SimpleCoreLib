package mod.alexndr.simplecorelib.api.datagen;

import mod.alexndr.simplecorelib.api.content.MultifunctionPressurePlateBlock;
import mod.alexndr.simplecorelib.api.content.VeryAbstractFurnaceBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class SimpleBlockStateProvider extends BlockStateProvider
{

    public SimpleBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper)
    {
        super(gen, modid, exFileHelper);
    }

    public void buildFurnaceBlockState(VeryAbstractFurnaceBlock pFurnace, ModelFile pFurnaceModel, ModelFile pFurnaceModel_lit)
    {
        this.getVariantBuilder(pFurnace)
            .partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.NORTH).with(BlockStateProperties.LIT, false)
            .setModels(new ConfiguredModel(pFurnaceModel))
            .partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.SOUTH).with(BlockStateProperties.LIT, false)
            .setModels(new ConfiguredModel(pFurnaceModel, 0, 180, false))
            .partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.WEST).with(BlockStateProperties.LIT, false)
            .setModels(new ConfiguredModel(pFurnaceModel, 0, 270, false))
            .partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.EAST).with(BlockStateProperties.LIT, false)
            .setModels(new ConfiguredModel(pFurnaceModel, 0, 90, false))
            .partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.NORTH).with(BlockStateProperties.LIT, true)
            .setModels(new ConfiguredModel(pFurnaceModel_lit))
            .partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.SOUTH).with(BlockStateProperties.LIT, true)
            .setModels(new ConfiguredModel(pFurnaceModel_lit, 0, 180, false))
            .partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.WEST).with(BlockStateProperties.LIT, true)
            .setModels(new ConfiguredModel(pFurnaceModel_lit, 0, 270, false))
            .partialState().with(VeryAbstractFurnaceBlock.FACING, Direction.EAST).with(BlockStateProperties.LIT, true)
            .setModels(new ConfiguredModel(pFurnaceModel_lit, 0, 90, false));
       
    }
    
    
    public void buildWeightedPressurePlateBlockState(MultifunctionPressurePlateBlock pBlock, ModelFile pPlate, ModelFile pPlate_down)
    {
        this.getVariantBuilder(pBlock)
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 0).setModels(new ConfiguredModel(pPlate))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 1).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 2).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 3).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 4).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 5).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 6).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 7).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 8).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 9).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 10).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 11).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 12).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 13).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 14).setModels(new ConfiguredModel(pPlate_down))
            .partialState().with(MultifunctionPressurePlateBlock.POWER, 15).setModels(new ConfiguredModel(pPlate_down));
    }

} // end class
