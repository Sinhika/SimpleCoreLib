package mod.alexndr.simplecorelib.api.helpers;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Assorted common utility code
 *
 * @author Cadiboo
 */
public final class ArmorUtils
{
    /**
     * Is player wearing a partial set of the same armor material?
     * 
     * @param[in] slot_list list of slots required.
     * @return boolean
     */
    public static boolean isPlayerWearingPartialSet(Player player, @Nonnull ArmorMaterial material,
            @Nonnull Iterable<EquipmentSlot> slot_list)
    {
        List<ItemStack> armorList = (List<ItemStack>) player.getArmorSlots();
        for (EquipmentSlot slot : slot_list)
        {
            ItemStack stack = armorList.get(slot.getIndex());
            if (stack.isEmpty()) { return false; }
            if (!(stack.getItem() instanceof ArmorItem)) { return false; }
            ArmorItem piece = (ArmorItem) stack.getItem();
            ArmorMaterial pieceMaterial = piece.getMaterial().value();
            if (pieceMaterial != material)
            {
                return false;
            }
        }
        return true;
    } // end ()

    /**
     * Is player wearing a full set of the same armor material?
     * @return boolean
     */
    public static boolean isPlayerWearingFullSet(Player player, @Nonnull ArmorMaterial material)
    {
        Iterable<ItemStack> armorList = player.getArmorSlots();
        for (ItemStack stack : armorList)
        {
            if (stack.isEmpty()) { return false; }
            if (! (stack.getItem() instanceof ArmorItem)) { return false; }
            ArmorItem piece = (ArmorItem) stack.getItem();
            ArmorMaterial pieceMaterial = piece.getMaterial();
            if (pieceMaterial != material) {
                return false;
            }
        } // end-for
        return true;
    } // end isPlayerWearingFullSet()
} // end class ModUtil
