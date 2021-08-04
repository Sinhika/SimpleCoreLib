package mod.alexndr.simplecorelib.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mod.alexndr.simplecorelib.content.VeryAbstractFurnaceContainer;
import mod.alexndr.simplecorelib.content.VeryAbstractFurnaceTileEntity;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class VeryAbstractFurnaceScreen<T extends VeryAbstractFurnaceContainer<?>> extends AbstractContainerScreen<T>
{

    private static ResourceLocation BACKGROUND_TEXTURE;
    private int displayNameColor;
    
    public VeryAbstractFurnaceScreen(T screenContainer, Inventory inv, 
                                    ResourceLocation texture, Component titleIn, int nameColor)
    {
        super(screenContainer, inv, titleIn);
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
    	this.font.draw(matStack, this.inventory.getDisplayName().getString(), 8.0F, 
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
    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(PoseStack matStack, final float partialTicks, final int mouseX, final int mouseY)
    {
    	RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    	getMinecraft().getTextureManager().bind(BACKGROUND_TEXTURE);
    	int startX = this.leftPos;
    	int startY = this.topPos;
    
    	// Screen#blit draws a part of the current texture (assumed to be 256x256) to the screen
    	// The parameters are (x, y, u, v, width, height)
    
    	this.blit(matStack, startX, startY, 0, 0, this.imageWidth, this.imageHeight);
    
    	final VeryAbstractFurnaceTileEntity tileEntity = menu.tileEntity;
    	if (tileEntity.smeltTimeProgress > 0) {
    		// Draw progress arrow
    		int arrowWidth = getSmeltTimeScaled();
    		this.blit(matStack,
    				startX + 79, startY + 34,
    				176, 14,
    				arrowWidth, 14
    		);
    	}
    	if (tileEntity.isBurning()) {
    		// Draw flames
    		int flameHeight = getFuelBurnTimeScaled();
    		this.blit(matStack,
    				startX + 56, startY + 50 - flameHeight,
    				176, 14 - flameHeight,
    				14, flameHeight
    		);
    	}
    }

    private int getSmeltTimeScaled()
    {
    	final VeryAbstractFurnaceTileEntity tileEntity = this.menu.tileEntity;
    	final short smeltTimeProgress = tileEntity.smeltTimeProgress;
    	final short maxSmeltTime = tileEntity.maxSmeltTime;
    	if (smeltTimeProgress <= 0 || maxSmeltTime <= 0)
    		return 0;
    	return (smeltTimeProgress * 24) / maxSmeltTime; // 24 is the width of the arrow
    }

    private int getFuelBurnTimeScaled()
    {
    	final VeryAbstractFurnaceTileEntity tileEntity = this.menu.tileEntity;
    	if (tileEntity.maxFuelBurnTime <= 0)
    		return 0;
    	return (tileEntity.fuelBurnTimeLeft * 16) / tileEntity.maxFuelBurnTime; // 14 is the height of the flames
    }

}