package mod.alexndr.simplecorelib.client.gui;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.client.gui.SomewhatAbstractFurnaceScreen;
import mod.alexndr.simplecorelib.content.TestFurnaceContainerMenu;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TestFurnaceScreen extends SomewhatAbstractFurnaceScreen<TestFurnaceContainerMenu>
{
    // private final static int name_color =  0x0ffffff;
    private static final ResourceLocation TEXTURE = new ResourceLocation(SimpleCoreLib.MODID,
                                                                        "textures/gui/container/test_furnace_gui.png");

    public TestFurnaceScreen(TestFurnaceContainerMenu pMenu, Inventory pPlayerInventory, Component pTitle)
    {
        super(pMenu, new SmeltingRecipeBookComponent(), pPlayerInventory,  pTitle, TEXTURE);
    }

} // end class
