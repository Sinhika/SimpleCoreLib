package mod.alexndr.simplecorelib.helpers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

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
    public static boolean isPlayerWearingPartialSet(PlayerEntity player, @Nonnull IArmorMaterial material,
            @Nonnull Iterable<EquipmentSlotType> slot_list)
    {
        List<ItemStack> armorList = (List<ItemStack>) player.getArmorSlots();
        for (EquipmentSlotType slot : slot_list)
        {
            ItemStack stack = armorList.get(slot.getIndex());
            if (stack.isEmpty()) { return false; }
            if (!(stack.getItem() instanceof ArmorItem)) { return false; }
            ArmorItem piece = (ArmorItem) stack.getItem();
            IArmorMaterial pieceMaterial = piece.getMaterial();
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
    public static boolean isPlayerWearingFullSet(PlayerEntity player, @Nonnull IArmorMaterial material)
    {
        Iterable<ItemStack> armorList = player.getArmorSlots();
        for (ItemStack stack : armorList)
        {
            if (stack.isEmpty()) { return false; }
            if (! (stack.getItem() instanceof ArmorItem)) { return false; }
            ArmorItem piece = (ArmorItem) stack.getItem();
            IArmorMaterial pieceMaterial = piece.getMaterial();
            if (pieceMaterial != material) {
                return false;
            }
        } // end-for
        return true;
    } // end isPlayerWearingFullSet()
} // end class ModUtil
