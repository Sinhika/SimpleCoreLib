package mod.alexndr.simplecorelib.content;

import java.util.Objects;

import mod.alexndr.simplecorelib.init.ModBlocks;
import mod.alexndr.simplecorelib.init.ModContainerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.IContainerFactory;

public class TestFurnaceContainer extends VeryAbstractFurnaceContainer<TestFurnaceBlock>
{

    /**
     * Logical-client-side constructor, called from {@link ContainerType#create(IContainerFactory)}
     * Calls the logical-server-side constructor with the TileEntity at the pos in the PacketBuffer
     */
    public TestFurnaceContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) 
    {
        this(windowId, playerInventory, TestFurnaceContainer.getTileEntity(playerInventory, data));
    }

    /**
     * Constructor called logical-server-side from {@link TestFurnaceTileEntity#createMenu}
     * and logical-client-side from {@link #ModFurnaceContainer(int, PlayerInventory, PacketBuffer)}
     */
    public TestFurnaceContainer(final int windowId, final PlayerInventory playerInventory, final TestFurnaceTileEntity tileEntity) 
    {
        super(ModContainerTypes.test_furnace.get(), windowId, playerInventory, tileEntity, ModBlocks.test_furnace);
    } // end-server-side ctor

    protected static TestFurnaceTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) 
    {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
        Objects.requireNonNull(data, "data cannot be null!");
        final TileEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof TestFurnaceTileEntity)
            return (TestFurnaceTileEntity) tileAtPos;
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }
} // end class
