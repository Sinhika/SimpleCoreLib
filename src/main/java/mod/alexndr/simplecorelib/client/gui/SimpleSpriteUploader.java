package mod.alexndr.simplecorelib.client.gui;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;

/**
 * Borrows heavily from mezz's JeiSpriteUploader class.
 * @author Sinhika
 *
 */
public class SimpleSpriteUploader extends TextureAtlasHolder
{
	private final Set<ResourceLocation> registeredSprites = new HashSet<>();


	public SimpleSpriteUploader(TextureManager textureMgr, ResourceLocation atlasLocation)
	{
		super(textureMgr, atlasLocation, "gui");
	}

	public void registerSprite(ResourceLocation location) {
		registeredSprites.add(location);
	}

	@Override
	protected Stream<ResourceLocation> getResourcesToLoad() {
		return Collections.unmodifiableSet(registeredSprites).stream();
	}

	/**
	 * Overridden to make it public
	 */
	@Override
	public TextureAtlasSprite getSprite(ResourceLocation location) {
		return super.getSprite(location);
	}
} // end class
