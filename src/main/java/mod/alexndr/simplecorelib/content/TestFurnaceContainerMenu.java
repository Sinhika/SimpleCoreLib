package mod.alexndr.simplecorelib.content;

import java.util.Objects;

import mod.alexndr.simplecorelib.init.ModBlocks;
import mod.alexndr.simplecorelib.init.ModContainerTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.network.IContainerFactory;

public class TestFurnaceContainerMenu extends VeryAbstractFurnaceContainerMenu<TestFurnaceBlock>
{

    /**
     * Logical-client-side constructor, called from {@link ContainerType#create(IContainerFactory)}
     * Calls the logical-server-side constructor with the TileEntity at the pos in the PacketBuffer
     */
    public TestFurnaceContainerMenu(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) 
    {
        this(windowId, playerInventory, TestFurnaceContainerMenu.getTileEntity(playerInventory, data));
    }

    /**
     * Constructor called logical-server-side from {@link TestFurnaceTileEntity#createMenu}
     * and logical-client-side from {@link #ModFurnaceContainer(int, PlayerInventory, PacketBuffer)}
     */
    public TestFurnaceContainerMenu(final int windowId, final Inventory playerInventory, final TestFurnaceTileEntity tileEntity) 
    {
        super(ModContainerTypes.test_furnace.get(), windowId, playerInventory, tileEntity, ModBlocks.test_furnace);
    } // end-server-side ctor

    protected static TestFurnaceTileEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) 
    {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
        Objects.requireNonNull(data, "data cannot be null!");
        final BlockEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof TestFurnaceTileEntity)
            return (TestFurnaceTileEntity) tileAtPos;
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }
} // end class
