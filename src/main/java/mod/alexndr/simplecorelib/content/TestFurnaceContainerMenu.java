package mod.alexndr.simplecorelib.content;

import mod.alexndr.simplecorelib.init.ModContainerTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fmllegacy.network.IContainerFactory;

public class TestFurnaceContainerMenu extends VeryAbstractFurnaceMenu
{

    /**
     * Logical-client-side constructor, called from {@link ContainerType#create(IContainerFactory)}
     * Calls the logical-server-side constructor with the TileEntity at the pos in the PacketBuffer
     */
    public TestFurnaceContainerMenu(final int windowId, final Inventory playerInventory) 
    {
    	// MenuType<?> menutype, RecipeType<? extends AbstractCookingRecipe> recipetype, int id, Inventory inv
        super(ModContainerTypes.test_furnace.get(), RecipeType.SMELTING, windowId, playerInventory);
    }

    /**
     * Constructor called logical-server-side from {@link TestFurnaceTileEntity#createMenu}
     * and logical-client-side from {@link #ModFurnaceContainer(int, PlayerInventory, PacketBuffer)}
     */
    public TestFurnaceContainerMenu(final int windowId, final Inventory playerInventory, final TestFurnaceTileEntity tileEntity) 
    {
    	// MenuType<?> menutype, RecipeType<? extends AbstractCookingRecipe> recipetype, int id, Inventory playerInventory,
		// ItemStackHandler container, ContainerData containerdata,  Container tilecontainer)
    	
        super(ModContainerTypes.test_furnace.get(), RecipeType.SMELTING, windowId, playerInventory, tileEntity.inventory,  
        	  tileEntity.dataAccess, tileEntity);
    } // end-server-side ctor


} // end class
