package mod.alexndr.simplecorelib.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

import mod.alexndr.simplecorelib.SimpleCoreLib;

import java.util.function.Supplier;

/**
 * This class holds all our ItemGroups (Formerly called CreativeTabs).
 * Static initialisers are fine here.
 *
 * @author Cadiboo
 */
public final class ModItemGroups {

	public static final ItemGroup MOD_ITEM_GROUP = 
	        new ModItemGroup(SimpleCoreLib.MODID, 
	                        () -> new ItemStack(ModBlocks.test_furnace.get()));

	public static final class ModItemGroup extends ItemGroup {

		@Nonnull
		private final Supplier<ItemStack> iconSupplier;

		public ModItemGroup(@Nonnull final String name, @Nonnull final Supplier<ItemStack> iconSupplier) {
			super(name);
			this.iconSupplier = iconSupplier;
		}

		@Override
		@Nonnull
		public ItemStack makeIcon() {
			return iconSupplier.get();
		}
	}
} // end class