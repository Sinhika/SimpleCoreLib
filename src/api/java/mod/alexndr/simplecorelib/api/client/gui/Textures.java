package mod.alexndr.simplecorelib.api.client.gui;

import mezz.jei.api.gui.drawable.IDrawableStatic;
import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraft.resources.ResourceLocation;

public final class Textures
{
	private final SimpleSpriteUploader spriteUploader;
	private final IDrawableStatic flameIcon;

	public Textures(SimpleSpriteUploader spUploader)
	{
		this.spriteUploader = spUploader;
		this.flameIcon = registerGuiSprite("flame", 14, 14);
		
	}
	
	private ResourceLocation registerSprite(String name) {
		ResourceLocation location = new ResourceLocation(SimpleCoreLib.MODID, name);
		spriteUploader.registerSprite(location);
		return location;
	}

	private DrawableSprite registerGuiSprite(String name, int width, int height) {
		ResourceLocation location = registerSprite(name);
		return new DrawableSprite(spriteUploader, location, width, height);
	}

	public IDrawableStatic getFlameIcon() {
		return flameIcon;
	}
	
	public SimpleSpriteUploader getSpriteUploader() {
		return spriteUploader;
	}
} // end class
