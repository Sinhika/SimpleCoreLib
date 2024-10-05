package mod.alexndr.simplecorelib.client.jei;

import mod.alexndr.simplecorelib.api.client.jei.AlternateFuelRecipe;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public class TestFurnaceFuelRecipe extends AlternateFuelRecipe
{
    public TestFurnaceFuelRecipe(Collection<ItemStack> input, int burnTime)
    {
        super(input, burnTime);
    }
}
