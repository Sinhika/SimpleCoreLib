package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModTileEntityTypes
{
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = 
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SimpleCoreLib.MODID);
    
    public static RegistryObject<TileEntityType<TestFurnaceTileEntity>> test_furnace = 
            TILE_ENTITY_TYPES.register("test_furnace", 
                            () -> TileEntityType.Builder.of(TestFurnaceTileEntity::new, 
                                                                ModBlocks.test_furnace.get())
             .build(null));
    
}
