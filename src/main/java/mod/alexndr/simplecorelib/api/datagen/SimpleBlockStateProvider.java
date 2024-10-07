package mod.alexndr.simplecorelib.api.datagen;

import mod.alexndr.simplecorelib.api.content.block.MultifunctionPressurePlateBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder.PartBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public abstract class SimpleBlockStateProvider extends BlockStateProvider
{
    private final SimpleBlockModelProvider simpleBlockModels;

    public SimpleBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper)
    {
        super(output, modid, exFileHelper);
        this.simpleBlockModels = new SimpleBlockModelProvider(output, modid, exFileHelper) {
            @Override public CompletableFuture<?> run(CachedOutput p_236071_) {return null;}
            @Override protected void registerModels() {}
        };
    }
    
    @Override
    public @NotNull SimpleBlockModelProvider models()
    {
        return this.simpleBlockModels;
    }

    @Override
    public @NotNull ItemModelProvider itemModels()
    {
        return super.itemModels();
    }

    public ItemModelBuilder basicBlockItem(Block block)
    {
        ResourceLocation blockItemRL = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(block.asItem()));
        
        return this.itemModels().getBuilder(block.asItem().toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(blockItemRL.getNamespace(), "block/" + blockItemRL.getPath()));
    }
           

    public void buildBarsBlockState(IronBarsBlock pBars, ResourceLocation texture) 
    {
        barsBlockInternal(pBars, BuiltInRegistries.BLOCK.getKey(pBars).toString(), texture);
    }
    
  
    protected void barsBlockInternal(IronBarsBlock pBars, String basename, ResourceLocation texture)
    {
        ModelFile post = models().barsPost(basename + "_post", texture);
        ModelFile post_ends = models().barsPostEnds(basename + "_post_ends", texture);
        ModelFile cap = models().barsCap(basename + "_cap", texture);
        ModelFile cap_alt = models().barsCapAlt(basename + "_cap_alt", texture);
        ModelFile side = models().barsSide(basename + "_side", texture);
        ModelFile side_alt = models().barsSideAlt(basename + "_side_alt", texture);
        barsBlock(pBars, post, post_ends, cap, cap_alt, side, side_alt);
    }
  
    
    public void barsBlock(IronBarsBlock pBars, ModelFile post, ModelFile post_ends, ModelFile cap, ModelFile cap_alt,
            ModelFile side, ModelFile side_alt)
    {
        // post_ends have no connections in any direction.
        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(pBars).part().modelFile(post_ends).addModel().end();

        // post may be stacked, but have no side connections
        PartBuilder p = builder.part().modelFile(post).addModel();
        for (Entry<Direction, BooleanProperty> e : PipeBlock.PROPERTY_BY_DIRECTION.entrySet())
        {
            Direction dir = e.getKey();
            if (dir.getAxis().isHorizontal())
            {
               p.condition(e.getValue(), false);
            }
       }
        p.end();
        
        // cap is connected north or east (rotation = 90).
        p = builder.part().modelFile(cap).addModel();
        for (Entry<Direction, BooleanProperty> e : PipeBlock.PROPERTY_BY_DIRECTION.entrySet())
        {
            Direction dir = e.getKey();
            if (dir.getAxis().isHorizontal())
            {
                if (dir == Direction.NORTH) {
                    p.condition(e.getValue(), true);
                }
                else {
                    p.condition(e.getValue(), false);
                }
            }
        }
        p.end();
        p = builder.part().modelFile(cap).rotationY(90).addModel();
        for (Entry<Direction, BooleanProperty> e : PipeBlock.PROPERTY_BY_DIRECTION.entrySet())
        {
            Direction dir = e.getKey();
            if (dir.getAxis().isHorizontal())
            {
                if (dir == Direction.EAST) {
                    p.condition(e.getValue(), true);
                }
                else {
                    p.condition(e.getValue(), false);
                }
            }
        }
        p.end();
        
       // cap_alt is connected south or west (rot=90)
        p = builder.part().modelFile(cap_alt).addModel();
        for (Entry<Direction, BooleanProperty> e : PipeBlock.PROPERTY_BY_DIRECTION.entrySet())
        {
            Direction dir = e.getKey();
            if (dir.getAxis().isHorizontal())
            {
                if (dir == Direction.SOUTH) {
                    p.condition(e.getValue(), true);
                }
                else {
                    p.condition(e.getValue(), false);
                }
            }
        }
        p.end();
        p = builder.part().modelFile(cap_alt).rotationY(90).addModel();
        for (Entry<Direction, BooleanProperty> e : PipeBlock.PROPERTY_BY_DIRECTION.entrySet())
        {
            Direction dir = e.getKey();
            if (dir.getAxis().isHorizontal())
            {
                if (dir == Direction.WEST) {
                    p.condition(e.getValue(), true);
                }
                else {
                    p.condition(e.getValue(), false);
                }
            }
        }
        p.end();
       
        // side has only north=true; side.rot(90) has only east = true
        // side_alt has only south=true; side_alt.rot(90) has only west = true
        for (Entry<Direction, BooleanProperty> e : PipeBlock.PROPERTY_BY_DIRECTION.entrySet())
        {
            Direction dir = e.getKey();
            if (dir.getAxis().isHorizontal())
            {
                boolean alt = (dir == Direction.SOUTH) || (dir == Direction.WEST);
                builder.part().modelFile(alt ? side_alt : side).rotationY(dir.getAxis() == Axis.X ? 90 : 0)
                        .addModel().condition(e.getValue(), true).end();
            } // end-if
        } // end-for
    } // end ()
    
    
    public void buildFurnaceBlockState(AbstractFurnaceBlock pFurnace, ModelFile pFurnaceModel, ModelFile pFurnaceModel_lit)
    {
        this.getVariantBuilder(pFurnace)
            .partialState().with(AbstractFurnaceBlock.FACING, Direction.NORTH).with(BlockStateProperties.LIT, false)
            .setModels(new ConfiguredModel(pFurnaceModel))
            .partialState().with(AbstractFurnaceBlock.FACING, Direction.SOUTH).with(BlockStateProperties.LIT, false)
            .setModels(new ConfiguredModel(pFurnaceModel, 0, 180, false))
            .partialState().with(AbstractFurnaceBlock.FACING, Direction.WEST).with(BlockStateProperties.LIT, false)
            .setModels(new ConfiguredModel(pFurnaceModel, 0, 270, false))
            .partialState().with(AbstractFurnaceBlock.FACING, Direction.EAST).with(BlockStateProperties.LIT, false)
            .setModels(new ConfiguredModel(pFurnaceModel, 0, 90, false))
            .partialState().with(AbstractFurnaceBlock.FACING, Direction.NORTH).with(BlockStateProperties.LIT, true)
            .setModels(new ConfiguredModel(pFurnaceModel_lit))
            .partialState().with(AbstractFurnaceBlock.FACING, Direction.SOUTH).with(BlockStateProperties.LIT, true)
            .setModels(new ConfiguredModel(pFurnaceModel_lit, 0, 180, false))
            .partialState().with(AbstractFurnaceBlock.FACING, Direction.WEST).with(BlockStateProperties.LIT, true)
            .setModels(new ConfiguredModel(pFurnaceModel_lit, 0, 270, false))
            .partialState().with(AbstractFurnaceBlock.FACING, Direction.EAST).with(BlockStateProperties.LIT, true)
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
