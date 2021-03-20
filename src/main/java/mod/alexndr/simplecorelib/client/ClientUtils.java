package mod.alexndr.simplecorelib.client;

import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;

public class ClientUtils
{
    public static void setupBowModelProperties(Item bow) 
    {
        ItemModelsProperties.register(bow, new ResourceLocation("pull"), (p0, p1, p2) -> {
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
        ItemModelsProperties.register(bow, new ResourceLocation("pulling"), (p0, p1, p2) -> {
            return p2 != null && p2.isUsingItem() && p2.getUseItem() == p0 ? 1.0F : 0.0F;
        });
    }

} // end class
