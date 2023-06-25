package mod.alexndr.simplecorelib.content;

import mod.alexndr.simplecorelib.config.SimpleCoreLibConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;

public class TestShearsItem extends ShearsItem
{

    public TestShearsItem()
    {
        super(new Item.Properties().durability(SimpleCoreLibConfig.testShearDurability));
    }

    @Override
    public int getMaxDamage(ItemStack stack)
    {
        return SimpleCoreLibConfig.testShearDurability;
    }

} // end class
