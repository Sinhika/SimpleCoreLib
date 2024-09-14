package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestShearsItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ShearsItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems
{
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(SimpleCoreLib.MODID);

    // Items
    public static final DeferredItem<ShearsItem> test_shears = ITEMS.registerItem("test_shears",
                                                                        TestShearsItem::new);
    
} // end-class
