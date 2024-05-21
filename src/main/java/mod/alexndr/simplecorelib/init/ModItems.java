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

    // BlockItems
    public static final DeferredItem<BlockItem> test_furnace_item = ITEMS.registerSimpleBlockItem("test_furnace",
            ModBlocks.test_furnace);
    public static final DeferredItem<BlockItem> original_copper_ore_item =
            ITEMS.registerSimpleBlockItem("original_copper_ore", ModBlocks.original_copper_ore);
    public static final DeferredItem<BlockItem> test_plate_item = ITEMS.registerSimpleBlockItem("test_plate",
            ModBlocks.test_plate);
    public static final DeferredItem<BlockItem> test_bars_item = ITEMS.registerSimpleBlockItem("test_bars",
            ModBlocks.test_bars);

    // Items
    public static final DeferredItem<ShearsItem> test_shears = ITEMS.registerItem("test_shears",
                                                                        TestShearsItem::new);
    
} // end-class
