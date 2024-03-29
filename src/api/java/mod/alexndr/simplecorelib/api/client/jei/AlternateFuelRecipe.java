package mod.alexndr.simplecorelib.api.client.jei;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Preconditions;

import mezz.jei.api.recipe.vanilla.IJeiFuelingRecipe;
import net.minecraft.world.item.ItemStack;

/**
 * Cut & paste copy of FuelingRecipe class, to avoid accessing the non-API bits of JEI.
 * Subclass for unique fuel types, changing BURN_TIME_STANDARD as necessary.
 * 
 * @author Sinhika
 *
 */
public abstract class AlternateFuelRecipe implements IJeiFuelingRecipe
{
    protected final List<ItemStack> inputs;
	protected final int burnTime;
	
	protected int BURN_TIME_STANDARD;  // default standard recipe cook time, NOT fuel burn time.
	
	public AlternateFuelRecipe(Collection<ItemStack> input, int burnTime)
	{
		Preconditions.checkArgument(burnTime > 0, "burn time must be greater than 0");
		this.inputs = new ArrayList<>(input);
		this.burnTime = burnTime;
		this.BURN_TIME_STANDARD = 200;
	}

	
    @Override
    public int getBurnTime()
    {
        return burnTime;
    }

	@Override
    public List<ItemStack> getInputs() {
		return inputs;
	}

	public int getSingleItemBurnTime()
	{
	    return BURN_TIME_STANDARD;
	}

 } // end class
