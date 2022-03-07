package mod.alexndr.simplecorelib.content;

import mod.alexndr.simplecorelib.init.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeType;

public class TestFurnaceContainerMenu extends VeryAbstractFurnaceMenu
{

    /**
     * Constructor called logical-server-side from {@link TestFurnaceTileEntity#createMenu}
     * and logical-client-side from {@link #ModFurnaceContainer(int, PlayerInventory, PacketBuffer)}
     */
    public TestFurnaceContainerMenu(final int windowId, final Inventory playerInventory, final BlockPos pos, Player playerEntity) 
    {
    	// MenuType<?> menutype, RecipeType<? extends AbstractCookingRecipe> recipetype, int id, Inventory playerInventory,
		// ItemStackHandler container, ContainerData containerdata,  Container tilecontainer)
    	
        super(ModMenuTypes.test_furnace.get(), windowId, pos, playerInventory, playerEntity, RecipeType.SMELTING);
    } // end-server-side ctor


} // end class
