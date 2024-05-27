package mod.alexndr.simplecorelib.content;

import mod.alexndr.simplecorelib.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TestFurnaceTileEntity extends AbstractFurnaceBlockEntity
{
    private static final String DISPLAY_NAME = "block.simplecorelib.test_furnace";

    public TestFurnaceTileEntity(BlockPos blockpos, BlockState blockstate)
    {
        super(ModTileEntityTypes.test_furnace.get(), blockpos, blockstate, RecipeType.SMELTING);
    }

    @Override protected @NotNull Component getDefaultName()
    {
        return Component.translatable(DISPLAY_NAME);
    }

    @Override protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory)
    {
        return new TestFurnaceContainerMenu(pContainerId, pInventory, this, this.dataAccess);
    }
} // end class
