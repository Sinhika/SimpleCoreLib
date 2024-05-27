package mod.alexndr.simplecorelib.content;

import mod.alexndr.simplecorelib.init.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeType;

public class TestFurnaceContainerMenu extends AbstractFurnaceMenu
{

    public TestFurnaceContainerMenu(int pContainerId, Inventory pPlayerInventory)
    {
        super(ModMenuTypes.test_furnace.get(), RecipeType.SMELTING, RecipeBookType.FURNACE, pContainerId, pPlayerInventory);
    }

    public TestFurnaceContainerMenu(int pContainerId, Inventory pPlayerInventory, Container pFurnaceContainer,
                                    ContainerData pFurnaceData)
    {
        super(ModMenuTypes.test_furnace.get(), RecipeType.SMELTING, RecipeBookType.FURNACE, pContainerId, pPlayerInventory,
                pFurnaceContainer, pFurnaceData);
    }
} // end class
