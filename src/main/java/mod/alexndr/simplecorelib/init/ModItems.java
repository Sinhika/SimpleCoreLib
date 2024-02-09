package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestShearsItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShearsItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public final class ModItems
{
    public static final DeferredRegister<Item> ITEMS = 
            DeferredRegister.create(ForgeRegistries.ITEMS, SimpleCoreLib.MODID);

    public static final RegistryObject<ShearsItem> test_shears = ITEMS.register("test_shears", () -> new TestShearsItem());
    
} // end-class
