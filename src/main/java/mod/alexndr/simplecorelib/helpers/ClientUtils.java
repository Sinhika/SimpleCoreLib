package mod.alexndr.simplecorelib.helpers;

import java.util.Random;

import net.minecraft.util.math.BlockPos;

/**
 * small functions that are used client-side.
 * @author Sinhika
 *
 */
public final class ClientUtils
{

    /**
     * Standard equation for generating random particle positions for blocks that spew random
     * particles. Used as a helper function in animateTick().
     * @param pos as provided by animateTick()
     * @param rand as provided by animateTick()
     * @return array with args for World.addParticle position & vector.
     */
    public static double[] findBlockParticleVector(BlockPos pos, Random rand)
    {
        double pv[] = new double[6];
        
        float f1 = (float)pos.getX() - 0.5F;
        float f2 = (float)pos.getY() - 0.5F;
        float f3 = (float)pos.getZ() - 0.5F;
        float f4 = rand.nextFloat() * 2.0f;
        float f5 = rand.nextFloat() * 2.0f;
        float f6 = rand.nextFloat() * 2.0f;
        pv[0] = (double)(f1 + f4);
        pv[1] = (double)(f2 + f5);
        pv[2] = (double)(f3 + f6);
        pv[3] = 0.0D;
        pv[4] = 0.0D;
        pv[5] = 0.0D;
        
        return pv;
    } // end findBlockParticleVector()
    
} // end class ClientUtils
