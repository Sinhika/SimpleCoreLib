package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.content.MFPressurePlateBlockWithTooltip;
import mod.alexndr.simplecorelib.api.content.MultifunctionPressurePlateBlock;
import mod.alexndr.simplecorelib.api.helpers.LightUtils;
import mod.alexndr.simplecorelib.content.TestFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = 
            DeferredRegister.create(ForgeRegistries.BLOCKS, SimpleCoreLib.MODID);
    
    // Test Blocks
    public static final RegistryObject<TestFurnaceBlock> test_furnace = BLOCKS.register("test_furnace",
            () -> new TestFurnaceBlock(Block.Properties.of()
                    .strength(3.5F, 12.0F).requiresCorrectToolForDrops()
                    .lightLevel(LightUtils.setSwitchedLight(BlockStateProperties.LIT, 13))));
                 
    public static final RegistryObject<DropExperienceBlock> original_copper_ore = BLOCKS.register("original_copper_ore", 
            () -> new DropExperienceBlock(Block.Properties.of().strength(2.0F).requiresCorrectToolForDrops()));
   
    public static final RegistryObject<MultifunctionPressurePlateBlock> test_plate = BLOCKS.register("test_plate", 
            () -> new MFPressurePlateBlockWithTooltip(15, MultifunctionPressurePlateBlock.Sensitivity.LIVING, 20, 
                                                      Block.Properties.of()
                                                        .noCollission().strength(0.5F).sound(SoundType.METAL),
                                                        "tips.test_plate"));
    
    public static final RegistryObject<IronBarsBlock> test_bars = BLOCKS.register("test_bars",
            () -> new IronBarsBlock(Block.Properties.of()));
    
} // end class
