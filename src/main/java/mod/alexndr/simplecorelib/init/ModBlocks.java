package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestFurnaceBlock;
import mod.alexndr.simplecorelib.helpers.LightUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = 
            DeferredRegister.create(ForgeRegistries.BLOCKS, SimpleCoreLib.MODID);
    
    // Test Blocks
    public static final RegistryObject<TestFurnaceBlock> onyx_furnace = BLOCKS.register("test_furnace",
            () -> new TestFurnaceBlock(Block.Properties.of(Material.STONE)
                    .strength(3.5F, 12.0F).requiresCorrectToolForDrops()
                    .lightLevel(LightUtils.setFixedLight(13)).harvestTool(ToolType.PICKAXE)));    
   
} // end class
