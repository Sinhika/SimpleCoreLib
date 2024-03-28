package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestFurnaceTileEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModTileEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = 
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SimpleCoreLib.MODID);
    
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TestFurnaceTileEntity>> test_furnace =
            TILE_ENTITY_TYPES.register("test_furnace", 
                            () -> BlockEntityType.Builder.of(TestFurnaceTileEntity::new, ModBlocks.test_furnace.get())
                                    .build(null));


}
