package mod.alexndr.simplecorelib.mixins;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface AbstractFurnaceBlockEntityInvoker
{
    @Invoker("burn")
    public static boolean simplecorelib$callBurn(RegistryAccess registryAccess,
                                                   @Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> inventory,
                                                   int maxStackSize, AbstractFurnaceBlockEntity furnace)
    {
        throw new AssertionError();
    }

    @Invoker("canBurn")
    public static boolean simplecorelib$callCanBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<?> recipe,
                                  NonNullList<ItemStack> inventory, int maxStackSize,
                                  AbstractFurnaceBlockEntity furnace)
    {
        throw new AssertionError();
    }
} // end interface
