package mod.alexndr.simplecorelib.helpers;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;

public final class TagUtils
{
    // ITEM TAGS
    public static Tag.Named<Item> modTag(String modid, String name)
    {
        return ItemTags.bind(modid + ":" + name);
    }
    
    public static Tag.Named<Item> forgeTag(String name)
    {
        return modTag("forge", name);
    }

    public static Tag.Named<Item> silentsTag(String name) {
        return modTag("silents_mechanisms", name);
    }
    
    // BLOCK TAGS
    public static Tag.Named<Block> modBlockTag(String modid, String name) {
        return BlockTags.bind(modid + ":" + name);
    }
    
    public static Tag.Named<Block> forgeBlockTag(String name) {
        return modBlockTag("forge", name);
    }
    
} // end class TagUtils
