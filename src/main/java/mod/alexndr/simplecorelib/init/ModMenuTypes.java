package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestFurnaceContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModMenuTypes
{
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = 
            DeferredRegister.create(ForgeRegistries.CONTAINERS, SimpleCoreLib.MODID);
    
    public static final RegistryObject<MenuType<TestFurnaceContainerMenu>> test_furnace 
    	= CONTAINER_TYPES.register("test_furnace", 
    			() -> IForgeMenuType.create((windowId, inv, data) 
    			                        -> new TestFurnaceContainerMenu(windowId, inv, data.readBlockPos(), inv.player))); 
}
