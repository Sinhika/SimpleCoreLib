package mod.alexndr.simplecorelib.client.gui;

import mod.alexndr.simplecorelib.content.TestFurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TestFurnaceScreen extends VeryAbstractFurnaceScreen<TestFurnaceContainer>
{

    public TestFurnaceScreen(TestFurnaceContainer screenContainer, PlayerInventory inv, ResourceLocation texture,
            ITextComponent titleIn, int nameColor)
    {
        super(screenContainer, inv, texture, titleIn, nameColor);
        // TODO Auto-generated constructor stub
    }

}
