package mod.alexndr.simplecorelib.client.gui;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.TestFurnaceContainerMenu;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TestFurnaceScreen extends AbstractFurnaceScreen<TestFurnaceContainerMenu>
{
    private final static int name_color =  0x0ffffff;
    private static final ResourceLocation LIT_PROGRESS_SPRITE = new ResourceLocation("container/furnace/lit_progress");
    private static final ResourceLocation BURN_PROGRESS_SPRITE = new ResourceLocation("container/furnace/burn_progress");
    private static final ResourceLocation TEXTURE = new ResourceLocation(SimpleCoreLib.MODID,
                                                                        "textures/gui/container/test_furnace_gui.png");

    public TestFurnaceScreen(TestFurnaceContainerMenu pMenu, Inventory pPlayerInventory, Component pTitle)
    {
        super(pMenu,new SmeltingRecipeBookComponent(), pPlayerInventory,  pTitle, TEXTURE,LIT_PROGRESS_SPRITE,
                BURN_PROGRESS_SPRITE);
    }

} // end class
