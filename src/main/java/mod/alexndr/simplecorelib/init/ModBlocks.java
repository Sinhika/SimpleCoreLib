package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestFurnaceBlock;
import mod.alexndr.simplecorelib.helpers.LightUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = 
            DeferredRegister.create(ForgeRegistries.BLOCKS, SimpleCoreLib.MODID);
    
    // Test Blocks
    public static final RegistryObject<TestFurnaceBlock> test_furnace = BLOCKS.register("test_furnace",
            () -> new TestFurnaceBlock(Block.Properties.of(Material.STONE)
                    .strength(3.5F, 12.0F).requiresCorrectToolForDrops()
                    .lightLevel(LightUtils.setSwitchedLight(BlockStateProperties.LIT, 13))
                    .harvestLevel(0).harvestTool(ToolType.PICKAXE)));    
   
} // end class
