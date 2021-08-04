package mod.alexndr.simplecorelib.content;

import mod.alexndr.simplecorelib.init.ModBlocks;
import mod.alexndr.simplecorelib.init.ModTileEntityTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class TestFurnaceTileEntity extends VeryAbstractFurnaceTileEntity
{

    public TestFurnaceTileEntity()
    {
        super(ModTileEntityTypes.test_furnace.get(), RecipeType.SMELTING);
    }

    @Override
    public Component getDisplayName()
    {
        return new TranslatableComponent(ModBlocks.test_furnace.get().getDescriptionId());
    }

    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player)
    {
        return new TestFurnaceContainer(windowId, inventory, this);
    }

}
