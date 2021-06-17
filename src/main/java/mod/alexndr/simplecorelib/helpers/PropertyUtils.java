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
     * @param p_235426_0_
     * @param p_235426_1_
     * @param p_235426_2_
     * @return
     */
    public static boolean always(BlockState p_235426_0_, IBlockReader p_235426_1_, BlockPos p_235426_2_)
    {
        return true;
    }

    /**
     * Taken from vanilla Blocks class, where it is private for no useful reason.
     * @param p_235436_0_
     * @param p_235436_1_
     * @param p_235436_2_
     * @return
     */
    public static boolean never(BlockState p_235436_0_, IBlockReader p_235436_1_, BlockPos p_235436_2_)
    {
        return false;
    }

    /**
     * Taken from vanilla Blocks class, where it is private for no useful reason.
     * @param p_235427_0_
     * @param p_235427_1_
     * @param p_235427_2_
     * @param p_235427_3_
     * @return
     */
    public static Boolean never(BlockState p_235427_0_, IBlockReader p_235427_1_, BlockPos p_235427_2_,
            EntityType<?> p_235427_3_)
    {
        return (boolean) false;
    }

    /**
     * Taken from vanilla Blocks class, where it is private for no useful reason.
     * @param p_235437_0_
     * @param p_235437_1_
     * @param p_235437_2_
     * @param p_235437_3_
     * @return
     */
    public static Boolean always(BlockState p_235437_0_, IBlockReader p_235437_1_, BlockPos p_235437_2_,
            EntityType<?> p_235437_3_)
    {
        return (boolean) true;
    }
    
} // end class
