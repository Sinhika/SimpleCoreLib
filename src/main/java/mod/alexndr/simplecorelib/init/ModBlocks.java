package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.content.MFPressurePlateBlockWithTooltip;
import mod.alexndr.simplecorelib.api.content.MultifunctionPressurePlateBlock;
import mod.alexndr.simplecorelib.api.helpers.LightUtils;
import mod.alexndr.simplecorelib.content.TestFurnaceBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks
{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SimpleCoreLib.MODID);
    
    // Test Blocks
    public static final DeferredBlock<TestFurnaceBlock> test_furnace = BLOCKS.register("test_furnace",
            () -> new TestFurnaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(3.5F, 12.0F).requiresCorrectToolForDrops()
                    .lightLevel(LightUtils.setSwitchedLight(BlockStateProperties.LIT, 13)
                    		)));
                 
    public static final DeferredBlock<DropExperienceBlock> original_copper_ore = BLOCKS.register("original_copper_ore",
            () -> new DropExperienceBlock(ConstantInt.of(1), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(2.0F)
            								.requiresCorrectToolForDrops()));
   
    public static final DeferredBlock<MultifunctionPressurePlateBlock> test_plate = BLOCKS.register("test_plate",
            () -> new MFPressurePlateBlockWithTooltip(15, MultifunctionPressurePlateBlock.Sensitivity.LIVING, 20, 
                                                      BlockBehaviour.Properties.of().mapColor(DyeColor.BLACK)
                                                        .noCollission().strength(0.5F).sound(SoundType.METAL),
                                                      BlockSetType.IRON,
                                                        "tips.test_plate"));
    
    public static final DeferredBlock<IronBarsBlock> test_bars = BLOCKS.register("test_bars",
            () -> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)));
    
} // end class
