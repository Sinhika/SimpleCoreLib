package mod.alexndr.simplecorelib.content;

import mod.alexndr.simplecorelib.init.ModBlocks;
import mod.alexndr.simplecorelib.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

public class TestFurnaceTileEntity extends VeryAbstractFurnaceTileEntity
{

    public TestFurnaceTileEntity(BlockPos blockpos, BlockState blockstate)
    {
        super(ModTileEntityTypes.test_furnace.get(), blockpos, blockstate, RecipeType.SMELTING);
    }

	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory inventory) {
        return new TestFurnaceContainerMenu(windowId, inventory, this);
	}

	@Override
	protected Component getDefaultName() {
        return new TranslatableComponent(ModBlocks.test_furnace.get().getDescriptionId());
	}

} // end class
