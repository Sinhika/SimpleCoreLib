package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.config.FlagCondition;
import mod.alexndr.simplecorelib.config.ISimpleConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;

/**
 * Condition builders for SimpleOres-type mods. Inspired by Forge's IConditionBuilder.
 * @author Sinhika
 *
 */
public interface ISimpleConditionBuilder
{
    /**
     * Builds an ICondition representing FlagCondition...
     *
     */
    public ICondition flag(String name);

    default ICondition impl_flag(String modid, ISimpleConfig cfg, String name )
    {
        return new FlagCondition(cfg, name, new ResourceLocation(modid, "flag"));
    }
} // end interface
