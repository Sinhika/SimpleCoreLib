package mod.alexndr.simplecorelib.api.helpers;

import java.util.HashMap;

/**
 * Look up shortcuts for chest loot tables.
 * Major change in functionality: missing keys return null instead of defaulting to self.
 * 
 * @author Sinhika
 *
 */
@SuppressWarnings("serial")
public abstract class InjectionTableLookup extends HashMap<String, String>
{
    /**
     * constructor initializes lookup table with chest names that map to abbreviated tables.
     * Assume no tables initially, and add in the subclasses, because doing otherwise is a
     * maintenance nightmare.
     *  
     */
    public InjectionTableLookup()
    {
        super();
    } // end ctor

    public void AddAllAliases()
    {
        AddNetherAliases();
        AddOceanAliases();
        AddVillageHouseAliases();
        AddStrongholdAliases();
        AddDungeonAliases();
    } // end AddAllAliases()
    
    /**
     * use 'simple_dungeon' for 'pillager_outpost' and 'woodland_mansion'.
     */
    public void AddDungeonAliases()
    {
        this.put("pillager_outpost", "simple_dungeon");
        this.put("woodland_mansion", "simple_dungeon");
    }
    
    /**
     * use 'stronghold' for crossing and corridor.
     */
    public void AddStrongholdAliases()
    {
        this.put("stronghold_corridor", "stronghold");
        this.put("stronghold_crossing", "stronghold");
    }
    
    /**
     * use 'village_house' for all village_*_house tables.
     */
    public void AddVillageHouseAliases()
    {
        this.put("village_savanna_house", "village_house");
        this.put("village_plains_house", "village_house");
        this.put("village_desert_house", "village_house");
        this.put("village_snowy_house", "village_house");
        this.put("village_taiga_house", "village_house");
    }
    
    /**
     * use 'bastion' for all nether chests.
     */
    public void AddNetherAliases()
    {
        this.put("bastion_bridge", "bastion");
        this.put("bastion_hoglin_stable", "bastion");
        this.put("bastion_other", "bastion");
        this.put("bastion_treasure", "bastion");
        this.put("nether_bridge", "bastion");
    }

    /**
     * use 'shipwreck' for all 'shipwreck_*' tables, and 'underwater_ruin' for all 'underwater_ruin_*'.
     * 
     */
    public void AddOceanAliases()
    {
        this.put("shipwreck_map", "shipwreck");
        this.put("shipwreck_supply", "shipwreck");
        this.put("shipwreck_treasure", "shipwreck");
        this.put("underwater_ruin_big", "underwater_ruin");
        this.put("underwater_ruin_small", "underwater_ruin");
    }
} // end class InjectionTableLookup
