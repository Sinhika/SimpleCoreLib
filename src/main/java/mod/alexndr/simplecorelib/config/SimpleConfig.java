package mod.alexndr.simplecorelib.config;

import java.util.HashMap;
import java.util.Map;

public class SimpleConfig implements ISimpleConfig
{
    // recipe flags
    protected Map<String, Boolean> flags = new HashMap<>();
    
    /**
     * get the value of flag "n".
     * @param n - name of flag
     * @return boolean value.
     */
    public boolean getFlag(String n)
    {
        Boolean obj = flags.get(n);
        return obj != null && obj;
    }
    
    /**
     * clear the flag collection.
     */
    public void clear()
    {
        flags.clear();
    }
    
    /**
     * add a flag to the flag collection
     * @param n - name of flag
     * @param val - boolean value of flag
     */
    public void putFlag(String n, boolean val)
    {
        flags.put(n, val);
    }

} // end class
