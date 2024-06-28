package mod.alexndr.simplecorelib.api.helpers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class TagUtils
{
    // ITEM TAGS
    public static TagKey<Item> modTag(String modid, String name)
    {
        return ItemTags.create(new ResourceLocation(modid, name));
    }
    
    public static TagKey<Item> forgeTag(String name)
    {
        return modTag("neoforge", name);
    }
    public static TagKey<Item> cTag(String name)
    {
        return modTag("c", name);
    }
    public static TagKey<Item> mcTag(String name)
    {
        return modTag("minecraft", name);
    }

    public static TagKey<Item> silentsTag(String name) {
        return modTag("silents_mechanisms", name);
    }
    
    // BLOCK TAGS
    public static TagKey<Block> modBlockTag(String modid, String name) {
        return BlockTags.create(new ResourceLocation(modid, name));
    }
    
    public static TagKey<Block> forgeBlockTag(String name) {  return modBlockTag("neoforge", name); }

    public static TagKey<Block> cBlockTag(String name) {  return modBlockTag("c.tags", name); }
    public static TagKey<Block> mcBlockTag(String name) {  return modBlockTag("minecraft", name); }

    public static TagKey<Block> mcMiningTag(String name, int harvest_level)
    {
    	String needs_name;
    	switch (harvest_level) 
    	{
	    	case 1: needs_name = "needs_stone_tool"; break;
	    	case 2: needs_name = "needs_iron_tool"; break;
	    	case 3: needs_name = "needs_diamond_tool"; break;
	    	case 4: needs_name = "needs_netherite_tool"; break;
	    	default: return null;
    	}
    	return modBlockTag("minecraft", needs_name);
    }
} // end class TagUtils
