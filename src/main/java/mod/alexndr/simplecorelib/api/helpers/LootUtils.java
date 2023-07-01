package mod.alexndr.simplecorelib.api.helpers;

import com.google.gson.JsonParseException;

import net.minecraftforge.event.LootTableLoadEvent;

public final class LootUtils
{
     
    /**
     * guts of a standard LootLoad event handler that does table injection.
     * 
     * @param event
     * @param lookup_table
     */
    public static void LootLoadHandler(String modid, LootTableLoadEvent event, InjectionTableLookup lookup_table )
    {
        String prefix = "minecraft:chests/";
        String name = event.getName().toString();
        
        if (name.startsWith(prefix)) 
        {
            String file = name.substring(name.indexOf(prefix) + prefix.length());
            
            // village chests are a bit more complicated now, but use the old
            // village_blacksmith chest loot table anyway.
            if (file.startsWith("village/village_")) 
            {
                String village = "village/";
                file = file.substring(file.indexOf(village) + village.length());
            }
            String real_file = lookup_table.get(file);
            if (real_file != null ) 
            {
                try {
                   event.getTable().addPool(ChestLootHandler.getInjectPool(modid, real_file));
                }
                catch (JsonParseException e)
                {
                    ; // okay, we just don't load a non-existent injector.
                }
            }
        } // end-if chest loot
    } // end LootLoadHandler
    
} // end class
