package mod.alexndr.simplecorelib.helpers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public final class TagUtils
{
    // ITEM TAGS
    public static ITag.INamedTag<Item> modTag(String modid, String name)
    {
        return ItemTags.makeWrapperTag(modid + ":" + name);
    }
    
    public static ITag.INamedTag<Item> forgeTag(String name)
    {
        return modTag("forge", name);
    }

    public static ITag.INamedTag<Item> silentsTag(String name) {
        return modTag("silents_mechanisms", name);
    }
    
    // BLOCK TAGS
    public static ITag.INamedTag<Block> modBlockTag(String modid, String name) {
        return BlockTags.makeWrapperTag(modid + ":" + name);
    }
    
    public static ITag.INamedTag<Block> forgeBlockTag(String name) {
        return modBlockTag("forge", name);
    }
    
} // end class TagUtils
