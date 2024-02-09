package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestFurnaceContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public final class ModMenuTypes
{
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = 
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, SimpleCoreLib.MODID);
    
    public static final RegistryObject<MenuType<TestFurnaceContainerMenu>> test_furnace 
    	= CONTAINER_TYPES.register("test_furnace", 
    			() -> IMenuTypeExtension.create((windowId, inv, data) 
    			                        -> new TestFurnaceContainerMenu(windowId, inv, data.readBlockPos(), inv.player))); 
}
