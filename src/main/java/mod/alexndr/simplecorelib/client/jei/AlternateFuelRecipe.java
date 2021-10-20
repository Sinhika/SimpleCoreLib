package mod.alexndr.simplecorelib.client.jei;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Preconditions;

import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

public class AlternateFuelRecipe
{
	private final List<ItemStack> inputs;
	private final Component smeltCountText;
	private final IDrawableAnimated flame;
	
	public static int BURN_TIME_STANDARD = 200;
	
	public AlternateFuelRecipe(IGuiHelper guiHelper, Collection<ItemStack> input, int burnTime)
	{
		Preconditions.checkArgument(burnTime > 0, "burn time must be greater than 0");
		this.inputs = new ArrayList<>(input);
		this.smeltCountText = createSmeltCountText(burnTime);
		this.flame = guiHelper.drawableBuilder(VeryAbstractFurnaceVariantCategory.RECIPE_GUI_VANILLA, 82, 114, 14, 14)
			.buildAnimated(burnTime, IDrawableAnimated.StartDirection.TOP, true);
	}

	public static void init(int special_burn_time_standard)
	{
		BURN_TIME_STANDARD = special_burn_time_standard;
	}
	
	public List<ItemStack> getInputs() {
		return inputs;
	}

	public Component getSmeltCountText() {
		return smeltCountText;
	}

	public IDrawableAnimated getFlame() {
		return flame;
	}

	public static Component createSmeltCountText(int burnTime) {
		if (burnTime == BURN_TIME_STANDARD) {
			return new TranslatableComponent("gui.jei.category.fuel.smeltCount.single");
		} 
		else {
			NumberFormat numberInstance = NumberFormat.getNumberInstance();
			numberInstance.setMaximumFractionDigits(2);
			String smeltCount = numberInstance.format(burnTime / ((float) BURN_TIME_STANDARD));
			return new TranslatableComponent("gui.jei.category.fuel.smeltCount", smeltCount);
		}
	}
} // end class
