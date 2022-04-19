package mod.alexndr.simplecorelib.config;

import net.minecraft.world.level.levelgen.VerticalAnchor;

public class ModOreConfig
{
	protected int range_type;
    protected int vein_size;
    protected int vein_count;
    protected VerticalAnchor bottom, top;
    protected boolean is_common = true;
    protected float air_decay_rate;
    
    public static final int UNIFORM = 0;
    public static final int TRIANGLE = 1;
    public static final int RANGE_4_4 = 4;
    public static final int RANGE_8_8 = 8;
    public static final int RANGE_10_10 = 10;
    public static final int FULL_RANGE = 128;
    
    public ModOreConfig(int range_type, int vein_size, int vein_count, boolean common, VerticalAnchor lower, VerticalAnchor upper )
    {
        this.range_type = range_type;
        this.vein_size = vein_size;
        this.vein_count = vein_count;
        this.top = upper;
        this.bottom = lower;
        this.is_common = common;
        this.air_decay_rate = 0.0F;
    }

    public boolean get_commonality()
    {
    	return is_common;
    }
    
    public int getRange_type()
    {
    	return range_type;
    }
    
    public int getVein_size()
    {
        return vein_size;
    }

    public int getVein_count()
    {
        return vein_count;
    }

	public VerticalAnchor getBottom()
	{
		return bottom;
	}

	public VerticalAnchor getTop()
	{
		return top;
	}

    public float getAirDecay()
    {
        return air_decay_rate;
    }
    
    public void setAirDecay(float adr)
    {
        this.air_decay_rate = adr;
    }
    
} // end class ModOreConfig
