package mod.alexndr.simplecorelib.content;

import mod.alexndr.simplecorelib.init.ModBlocks;
import mod.alexndr.simplecorelib.init.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TestFurnaceTileEntity extends VeryAbstractFurnaceTileEntity
{

    public TestFurnaceTileEntity()
    {
        super(ModTileEntityTypes.test_furnace.get(), IRecipeType.SMELTING);
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent(ModBlocks.test_furnace.get().getDescriptionId());
    }

    @Override
    public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player)
    {
        return new TestFurnaceContainer(windowId, inventory, this);
    }

}
