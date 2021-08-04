package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestFurnaceTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModTileEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = 
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SimpleCoreLib.MODID);
    
    public static RegistryObject<BlockEntityType<TestFurnaceTileEntity>> test_furnace = 
            TILE_ENTITY_TYPES.register("test_furnace", 
                            () -> BlockEntityType.Builder.of(TestFurnaceTileEntity::new, 
                                                                ModBlocks.test_furnace.get())
             .build(null));
    
}
