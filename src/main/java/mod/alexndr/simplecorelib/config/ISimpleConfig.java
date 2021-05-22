package mod.alexndr.simplecorelib.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides some necessary functions that our configs will need to respond to 
 * various condition checks.
 *
 */
public interface ISimpleConfig
{
    // recipe flags
    public static Map<String, Boolean> flags = new HashMap<>();
    
   /**
     * get the value of flag "n".
     * @param n - name of flag
     * @return boolean value.
     */
    default public boolean getFlag(String n)
    {
        Boolean obj = flags.get(n);
        return obj != null && obj;
    }
    
    /**
     * clear the flag collection.
     */
    default public void clear()
    {
        flags.clear();
    }
    
    /**
     * add a flag to the flag collection
     * @param n - name of flag
     * @param val - boolean value of flag
     */
    default public void putFlag(String n, boolean val)
    {
        flags.put(n, val);
    }
} // end interface
