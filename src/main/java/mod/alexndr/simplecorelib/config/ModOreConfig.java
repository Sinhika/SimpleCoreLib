package mod.alexndr.simplecorelib.config;

import net.minecraft.world.level.levelgen.VerticalAnchor;

public class ModOreConfig
{
	protected int range_type;
    protected int vein_size;
    protected int vein_count;
    protected VerticalAnchor bottom, top;
    
    public static final int UNIFORM = 0;
    public static final int TRIANGLE = 1;
    public static final int RANGE_4_4 = 4;
    public static final int RANGE_8_8 = 8;
    public static final int RANGE_10_10 = 10;
    public static final int FULL_RANGE = 128;
    
    public ModOreConfig(int range_type, int vein_size, int vein_count, VerticalAnchor lower, VerticalAnchor upper )
    {
        this.range_type = range_type;
        this.vein_size = vein_size;
        this.vein_count = vein_count;
        this.top = upper;
        this.bottom = lower;
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

    
} // end class ModOreConfig
