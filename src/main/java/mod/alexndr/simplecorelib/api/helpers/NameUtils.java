package mod.alexndr.simplecorelib.api.helpers;

import com.google.common.base.Preconditions;

import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public final class NameUtils
{
    /**
     * Gets the registry name of the stack's item, throwing an exception if it is
     * null
     *
     * @param stack The ItemStack
     * @return The registry name
     * @throws NullPointerException if registry name is null
     */
    public static ResourceLocation fromItem(ItemStack stack)
    {
        ResourceLocation name = stack.getItem().getRegistryName();
        Preconditions.checkNotNull(name, "Name is null, make sure the object has been registered correctly");
        return name;
    }
} // end-class
