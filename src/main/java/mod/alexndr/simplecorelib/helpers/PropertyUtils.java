package mod.alexndr.simplecorelib.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

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
    public static boolean always(BlockState bstate, IBlockReader breader, BlockPos blockpos)
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
    public static boolean never(BlockState bstate, IBlockReader breader, BlockPos blockpos)
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
    public static Boolean never(BlockState bstate, IBlockReader breader, BlockPos blockpos,
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
    public static Boolean always(BlockState bstate, IBlockReader breader, BlockPos blockpos,
            EntityType<?> entity)
    {
        return (boolean) true;
    }
    
} // end class
