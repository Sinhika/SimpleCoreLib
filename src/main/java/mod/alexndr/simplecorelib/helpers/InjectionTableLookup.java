package mod.alexndr.simplecorelib.helpers;

import java.util.HashMap;

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
     * Note: assumes 'bastion', 'simple_dungeon', 'abandoned_mineshaft', 'desert_pyramid',
     *  'jungle_temple', "spawn_bonus_chest", 'underwater_ruin', 'shipwreck', 'nether',
     *  'stronghold', 'underwater_ruin', and 'village_house' are always provided.
     *  
     */
    public InjectionTableLookup()
    {
        super();
        this.put("bastion_bridge", "bastion");
        this.put("bastion_hoglin_stable", "bastion");
        this.put("bastion_other", "bastion");
        this.put("bastion_treasure", "bastion");
        this.put("buried_treasure", "simple_dungeon");
        this.put("end_city_treasure", null);
        this.put("igloo_chest", null);
        this.put("jungle_temple_dispenser", null);
        this.put("nether_bridge", "nether");
        this.put("pillager_outpost", "simple_dungeon");
        this.put("ruined_portal", null);
        this.put("shipwreck_map", "shipwreck");
        this.put("shipwreck_supply", "shipwreck");
        this.put("shipwreck_treasure", "shipwreck");
        this.put("stronghold_corridor", "stronghold");
        this.put("stronghold_crossing", "stronghold");
        this.put("stronghold_library", "stronghold");
        this.put("underwater_ruin_big", "underwater_ruin");
        this.put("underwater_ruin_small", "underwater_ruin");
        this.put("woodland_mansion", "simple_dungeon");
        this.put("village_savanna_house", "village_house");
        this.put("village_plains_house", "village_house");
        this.put("village_desert_house", "village_house");
        this.put("village_snowy_house", "village_house");
        this.put("village_taiga_house", "village_house");
        this.put("village_armorer", null);
        this.put("village_butcher", null);
        this.put("village_cartographer", null);
        this.put("village_fisher", null);
        this.put("village_fletcher", null);
        this.put("village_mason", null);
        this.put("village_shepherd", null);
        this.put("village_tannery", null);
        this.put("village_temple", null);
        this.put("village_toolsmith", null);
        this.put("village_weaponsmith", null);
       
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
} // end class InjectionTableLookup
