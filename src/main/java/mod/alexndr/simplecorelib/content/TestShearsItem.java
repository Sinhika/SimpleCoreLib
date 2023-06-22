package mod.alexndr.simplecorelib.content;

import mod.alexndr.simplecorelib.config.SimpleCoreLibConfig;
import mod.alexndr.simplecorelib.init.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;

public class TestShearsItem extends ShearsItem
{

    public TestShearsItem()
    {
        super(new Item.Properties().durability(SimpleCoreLibConfig.testShearDurability).tab(CreativeModeTabs.MOD_ITEM_GROUP));
    }

    @Override
    public int getMaxDamage(ItemStack stack)
    {
        return SimpleCoreLibConfig.testShearDurability;
    }

} // end class
