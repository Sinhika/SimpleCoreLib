package mod.alexndr.simplecorelib.helpers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Assorted common utility code
 *
 * @author Cadiboo
 */
public final class ArmorUtils
{

    /**
     * Is player wearing a full set of the same armor material?
     * @return boolean
     */
    public static boolean isPlayerWearingFullSet(PlayerEntity player, @Nonnull IArmorMaterial material)
    {
        Iterable<ItemStack> armorList = player.getArmorInventoryList();
        for (ItemStack stack : armorList)
        {
            if (stack.isEmpty()) { return false; }
            if (! (stack.getItem() instanceof ArmorItem)) { return false; }
            ArmorItem piece = (ArmorItem) stack.getItem();
            IArmorMaterial pieceMaterial = piece.getArmorMaterial();
            if (pieceMaterial != material) {
                return false;
            }
        } // end-for
        return true;
    } // end isPlayerWearingFullSet()
} // end class ModUtil
