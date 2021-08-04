package mod.alexndr.simplecorelib.config;

import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;

import TopSolidRangeConfig;

public class ModOreConfig
{
    protected RangeDecoratorConfiguration cfg;
    protected int vein_size;
    protected int vein_count;
    
    public ModOreConfig(RangeDecoratorConfiguration cfg, int vein_size, int vein_count)
    {
        this.cfg = cfg;
        this.vein_size = vein_size;
        this.vein_count = vein_count;
    }

    public RangeDecoratorConfiguration getCfg()
    {
        return cfg;
    }

    public int getVein_size()
    {
        return vein_size;
    }

    public int getVein_count()
    {
        return vein_count;
    } 
    
    
} // end class ModOreConfig
