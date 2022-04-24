package mod.alexndr.simplecorelib.init;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.config.ConfigHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShearsItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems
{
    public static final DeferredRegister<Item> ITEMS = 
            DeferredRegister.create(ForgeRegistries.ITEMS, SimpleCoreLib.MODID);

    public static final RegistryObject<ShearsItem> test_shears = ITEMS.register("test_shears",
    		() -> new ShearsItem(new Item.Properties().durability(ConfigHolder.SERVER.serverTestShearDurability.get())
    		        .tab(ModItemGroups.MOD_ITEM_GROUP)));
    
} // end-class
