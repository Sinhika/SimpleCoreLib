package mod.alexndr.simplecorelib.helpers;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public final class TagUtils
{
    public static ITag.INamedTag<Item> forgeTag(String name)
    {
        return ItemTags.makeWrapperTag("forge:" + name);
    }
}
