package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.helpers.LightUtils;
import mod.alexndr.simplecorelib.content.TestFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = 
            DeferredRegister.create(ForgeRegistries.BLOCKS, SimpleCoreLib.MODID);
    
    // Test Blocks
    public static final RegistryObject<TestFurnaceBlock> test_furnace = BLOCKS.register("test_furnace",
            () -> new TestFurnaceBlock(Block.Properties.of(Material.STONE)
                    .strength(3.5F, 12.0F).requiresCorrectToolForDrops()
                    .lightLevel(LightUtils.setSwitchedLight(BlockStateProperties.LIT, 13))));
                 
    public static final RegistryObject<OreBlock> original_copper_ore = BLOCKS.register("original_copper_ore", 
            () -> new OreBlock(Block.Properties.of(Material.STONE).strength(2.0F).requiresCorrectToolForDrops()));
   
} // end class
