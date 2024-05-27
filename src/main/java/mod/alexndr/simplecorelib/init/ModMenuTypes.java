package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestFurnaceContainerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModMenuTypes
{
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, SimpleCoreLib.MODID);
    
    public static final Supplier<MenuType<TestFurnaceContainerMenu>> test_furnace
    	= MENU_TYPES.register("test_furnace",
    			() -> new MenuType<>(TestFurnaceContainerMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
