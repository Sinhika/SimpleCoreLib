package mod.alexndr.simplecorelib.content;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;

public class TestFurnaceContainer extends VeryAbstractFurnaceContainer<TestFurnaceBlock>
{

    public TestFurnaceContainer(ContainerType<?> type, int id, PlayerInventory playerInventory,
            VeryAbstractFurnaceTileEntity tileEntity, RegistryObject<TestFurnaceBlock> block)
    {
        super(type, id, playerInventory, tileEntity, block);
        // TODO Auto-generated constructor stub
    }

} // end class
