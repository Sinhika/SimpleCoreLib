package mod.alexndr.simplecorelib.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import mod.alexndr.simplecorelib.content.VeryAbstractFurnaceMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class VeryAbstractFurnaceScreen<T extends VeryAbstractFurnaceMenu> extends AbstractContainerScreen<T>
{

    private final ResourceLocation BACKGROUND_TEXTURE;
    private int displayNameColor;
    
    public VeryAbstractFurnaceScreen(T screenMenu, Inventory inv, 
                                    ResourceLocation texture, Component titleIn, int nameColor)
    {
        super(screenMenu, inv, titleIn);
        BACKGROUND_TEXTURE = texture;
        displayNameColor = nameColor;
    }

    @Override
    public void render(PoseStack matStack, final int mouseX, final int mouseY, final float partialTicks)
    {
    	this.renderBackground(matStack);
    	super.render(matStack, mouseX, mouseY, partialTicks);
    	this.renderTooltip(matStack, mouseX, mouseY); // formerly renderHoveredTooltip
    }

    /**
     * Probably corresponds to ContainerScreen.renderLabels() in 1.16.1.
     * Formerly drawGuiContainerForegroundLayer() in 1.15.2.
     * @param matStack
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void renderLabels(PoseStack matStack, final int mouseX, final int mouseY)
    {
    	// Copied from AbstractFurnaceScreen#drawGuiContainerForegroundLayer
    	String s = this.title.getString();
    	this.font.draw(matStack, s, (float) (this.imageWidth / 2 - this.font.width(s) / 2), 6.0F, displayNameColor);
    	this.font.draw(matStack, this.playerInventoryTitle.getString(), 8.0F, 
    	                     (float) (this.imageHeight - 96 + 2), displayNameColor);
    }

    /**
     * Corresponds to AbstractFurnaceScreen.renderBg() in 1.16.1.
     * Formerly drawGuiContainerBackgroundLayer() in 1.15.2
     * @param matStack
     * @param partialTicks
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void renderBg(PoseStack matStack, final float partialTicks, final int mouseX, final int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.BACKGROUND_TEXTURE);

    	int startX = this.leftPos;
    	int startY = this.topPos;
    
    	// Screen#blit draws a part of the current texture (assumed to be 256x256) to the screen
    	// The parameters are (x, y, u, v, width, height)
    
    	this.blit(matStack, startX, startY, 0, 0, this.imageWidth, this.imageHeight);
    
    	if (this.menu.getBurnProgress() > 0) {
    		// Draw progress arrow
    		int arrowWidth = getSmeltTimeScaled();
    		this.blit(matStack, startX + 79, startY + 34, 176, 14, arrowWidth, 14);
    	}
    	if (this.menu.isLit()) {
    		// Draw flames
    		int flameHeight = getFuelBurnTimeScaled();
    		this.blit(matStack, startX + 56, startY + 50 - flameHeight, 176, 14 - flameHeight,14, flameHeight);
    	}
    }

    private int getSmeltTimeScaled()
    {
    	return this.menu.getBurnProgress();
    }

    private int getFuelBurnTimeScaled()
    {
    	return this.menu.getLitProgress();    
    }

}