package mod.alexndr.simplecorelib.helpers;

import java.util.function.ToIntFunction;

import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;

public final class LightUtils
{
    public static ToIntFunction<BlockState> setFixedLight(int foo)
    {
        return (bar) -> { return foo; };
    }
    
    public static ToIntFunction<BlockState> setSwitchedLight(BooleanProperty prop, int foo)
    {
        return (bar) -> {return bar.getValue(prop) ? foo : 0;};
    }
   
} // end class
