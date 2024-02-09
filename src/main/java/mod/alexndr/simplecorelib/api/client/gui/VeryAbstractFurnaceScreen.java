package mod.alexndr.simplecorelib.api.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import mod.alexndr.simplecorelib.api.content.VeryAbstractFurnaceMenu;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

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
    public void render(GuiGraphics gg, final int mouseX, final int mouseY, final float partialTicks)
    {
    	this.renderBackground(gg);
    	super.render(gg, mouseX, mouseY, partialTicks);
    	this.renderTooltip(gg, mouseX, mouseY); // formerly renderHoveredTooltip
    }

    /**
     * Draw a centered string with dropShadow = false, a function that is missing from GuiGraphics.
     * @param pFont
     * @param pText
     * @param pX
     * @param pY
     * @param pColor
     */
    public void drawCenteredStringNoShadow(GuiGraphics gg, Font pFont, String pText, int pX, int pY, int pColor) 
    {
    	gg.drawString(pFont, pText, pX - pFont.width(pText) / 2, pY, pColor, false);
    }
    
    /**
     * Probably corresponds to ContainerScreen.renderLabels() in 1.16.1.
     * Formerly drawGuiContainerForegroundLayer() in 1.15.2.
     * @param gg
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void renderLabels(GuiGraphics gg, final int mouseX, final int mouseY)
    {
    	// Copied from AbstractFurnaceScreen#drawGuiContainerForegroundLayer
    	String s = this.title.getString();
    	this.drawCenteredStringNoShadow(gg, this.font, s, this.imageWidth / 2, 6, displayNameColor);
    	gg.drawString( this.font, this.playerInventoryTitle.getString(), 8, this.imageHeight - 96 + 2, displayNameColor, false);
    }

    /**
     * Corresponds to AbstractFurnaceScreen.renderBg() in 1.16.1.
     * Formerly drawGuiContainerBackgroundLayer() in 1.15.2
     * @param gg
     * @param partialTicks
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void renderBg(GuiGraphics gg, final float partialTicks, final int mouseX, final int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.BACKGROUND_TEXTURE);

    	int startX = this.leftPos;
    	int startY = this.topPos;
    
    	// Screen#blit draws a part of the current texture (assumed to be 256x256) to the screen
    	// The parameters are ( x, y, u, v, width, height)
    
    	gg.blit(this.BACKGROUND_TEXTURE, startX, startY, 0, 0, this.imageWidth, this.imageHeight);
    
    	if (this.menu.getBurnProgress() > 0) {
    		// Draw progress arrow
    		int arrowWidth = getSmeltTimeScaled();
    		gg.blit(this.BACKGROUND_TEXTURE, startX + 79, startY + 34, 176, 14, arrowWidth, 14);
    	}
    	if (this.menu.isLit()) {
    		// Draw flames
    		int flameHeight = getFuelBurnTimeScaled();
    		gg.blit(this.BACKGROUND_TEXTURE,startX + 56, startY + 50 - flameHeight, 176, 14 - flameHeight,14, flameHeight);
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