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
} // end class InjectionTableLookup
