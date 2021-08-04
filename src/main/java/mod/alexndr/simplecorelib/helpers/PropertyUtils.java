package mod.alexndr.simplecorelib.helpers;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

/**
 * A bunch of predicates and other utility functions for setting properties. 
 * @author Sinhika
 *
 */
public final class PropertyUtils
{
    /**
     * Taken from vanilla Blocks class, where it is private for no useful reason.
     * @param bstate
     * @param breader
     * @param blockpos
     * @return
     */
    public static boolean always(BlockState bstate, BlockGetter breader, BlockPos blockpos)
    {
        return true;
    }

    /**
     * Taken from vanilla Blocks class, where it is private for no useful reason.
     * @param bstate
     * @param breader
     * @param blockpos
     * @return
     */
    public static boolean never(BlockState bstate, BlockGetter breader, BlockPos blockpos)
    {
        return false;
    }

    /**
     * Taken from vanilla Blocks class, where it is private for no useful reason.
     * @param bstate
     * @param breader
     * @param blockpos
     * @param entity
     * @return
     */
    public static Boolean never(BlockState bstate, BlockGetter breader, BlockPos blockpos,
            EntityType<?> entity)
    {
        return (boolean) false;
    }

    /**
     * Taken from vanilla Blocks class, where it is private for no useful reason.
     * @param bstate
     * @param breader
     * @param blockpos
     * @param entity
     * @return
     */
    public static Boolean always(BlockState bstate, BlockGetter breader, BlockPos blockpos,
            EntityType<?> entity)
    {
        return (boolean) true;
    }
    
} // end class
