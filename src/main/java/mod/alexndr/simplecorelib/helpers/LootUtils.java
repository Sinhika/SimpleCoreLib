package mod.alexndr.simplecorelib.helpers;

import java.util.HashMap;

import com.google.gson.JsonParseException;

import mod.alexndr.simplecorelib.loot.ChestLootHandler;
import net.minecraftforge.event.LootTableLoadEvent;

public class LootUtils
{

    /**
     * Look up shortcuts for chest loot tables.
     * @author Sinhika
     *
     */
    @SuppressWarnings("serial")
    public class InjectionTableLookup extends HashMap<String, String>
    {
        /**
         * constructor initializes lookup table with chest names that map to abbreviated tables.
         */
        public InjectionTableLookup()
        {
            super();
            this.put("bastion_bridge", "bastion");
            this.put("bastion_hoglin_stable", "bastion");
            this.put("bastion_other", "bastion");
            this.put("bastion_treasure", "bastion");
            this.put("jungle_temple_dispenser", null);
            this.put("nether_bridge", "nether");
            this.put("shipwreck_map", "shipwreck");
            this.put("shipwreck_supply", "shipwreck");
            this.put("shipwreck_treasure", "shipwreck");
            this.put("stronghold_corridor", "stronghold");
            this.put("stronghold_crossing", "stronghold");
            this.put("stronghold_library", "stronghold");
            this.put("underwater_ruin_big", "underwater_ruin");
            this.put("underwater_ruin_small", "underwater_ruin");
        } // end ctor

        /**
         * All unmapped chest names should have key=value. Use getOrDefault() if 
         * we want a different default.
         */
        @Override
        public String get(Object key)
        {
            if (! this.containsKey(key)) {
                return (String) key;
            }
            return super.get(key);
        }
    } // end sub-class InjectionTableLookup

    /**
     * guts of a standard LootLoad event handler that does table injection.
     * 
     * @param event
     * @param lookup_table
     */
    void LootLoadHandler(String modid, LootTableLoadEvent event, LootUtils.InjectionTableLookup lookup_table )
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
