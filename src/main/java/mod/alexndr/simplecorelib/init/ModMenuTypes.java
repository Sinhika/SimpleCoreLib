package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestFurnaceContainerMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModMenuTypes
{
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = 
            DeferredRegister.create(ForgeRegistries.CONTAINERS, SimpleCoreLib.MODID);
    
    public static final RegistryObject<MenuType<? extends AbstractContainerMenu>> test_furnace 
    	= CONTAINER_TYPES.register("test_furnace", 
    			() -> IForgeContainerType.create(TestFurnaceContainerMenu::new));
}
