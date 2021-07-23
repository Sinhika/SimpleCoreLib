package mod.alexndr.simplecorelib.client.gui;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestFurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TestFurnaceScreen extends VeryAbstractFurnaceScreen<TestFurnaceContainer>
{
    private final static int name_color =  0x0ffffff;

    public TestFurnaceScreen(TestFurnaceContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, 
                new ResourceLocation(SimpleCoreLib.MODID, "textures/gui/container/test_furnace_gui.png"), 
                        titleIn, name_color);
        // TODO Auto-generated constructor stub
    }

}
