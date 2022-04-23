package mod.alexndr.simplecorelib.api.client;

import java.util.Random;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

/**
 * small functions that are used client-side.
 * @author Sinhika
 *
 */
public final class ClientUtils
{
    /**
     * register client-side bow model properties so that bow draw ("pull") is properly rendered.
     * @param bow
     */
    public static void setupBowModelProperties(Item bow) 
    {
        ItemProperties.register(bow, new ResourceLocation("pull"), (p0, p1, p2, p4) -> {
            if (p2 == null)
            {
                return 0.0F;
            }
            else
            {
                return p2.getUseItem() != p0 ? 0.0F
                        : (float) (p0.getUseDuration() - p2.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(bow, new ResourceLocation("pulling"), (p0, p1, p2, p4) -> {
            return p2 != null && p2.isUsingItem() && p2.getUseItem() == p0 ? 1.0F : 0.0F;
        });
    } // end setupBowModelProperties()
    
    /**
     * like findBlockParticleVector, but allows the displacement factors to be specified.
     * @param pos as provided by animateTick()
     * @param rand as provided by animateTick()
     * @param fact1 factor subtracted from first 3 terms.
     * @param fact2 factor multiplied by next 3 terms.
     * @return array with args for World.addParticle position & vector.
     *     
     */
    public static double[] findBlockParticleVector(BlockPos pos, Random rand, float fact1, float fact2)
    {
        double pv[] = new double[6];
        
        float f1 = (float)pos.getX() - fact1;
        float f2 = (float)pos.getY() - fact1;
        float f3 = (float)pos.getZ() - fact1;
        float f4 = rand.nextFloat() * fact2;
        float f5 = rand.nextFloat() * fact2;
        float f6 = rand.nextFloat() * fact2;
        pv[0] = (double)(f1 + f4);
        pv[1] = (double)(f2 + f5);
        pv[2] = (double)(f3 + f6);
        pv[3] = 0.0D;
        pv[4] = 0.0D;
        pv[5] = 0.0D;
        
        return pv;
    } // end findBlockParticleVector(pos, rand, f1, f2)
    
    /**
     * Standard equation for generating random particle positions for blocks that spew random
     * particles. Used as a helper function in animateTick().
     * @param pos as provided by animateTick()
     * @param rand as provided by animateTick()
     * @return array with args for World.addParticle position & vector.
     */
    public static double[] findBlockParticleVector(BlockPos pos, Random rand)
    {
        return findBlockParticleVector(pos, rand, 0.5F, 2.0F);
    } // end findBlockParticleVector(pos, rand)

} // end class
