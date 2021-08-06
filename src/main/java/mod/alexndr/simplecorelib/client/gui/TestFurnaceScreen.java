package mod.alexndr.simplecorelib.client.gui;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestFurnaceContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class TestFurnaceScreen extends VeryAbstractFurnaceScreen<TestFurnaceContainerMenu>
{
    private final static int name_color =  0x0ffffff;

    public TestFurnaceScreen(TestFurnaceContainerMenu screenContainer, Inventory inv, Component titleIn)
    {
        super(screenContainer, inv, 
                new ResourceLocation(SimpleCoreLib.MODID, "textures/gui/container/test_furnace_gui.png"), 
                        titleIn, name_color);
    }

} // end class
