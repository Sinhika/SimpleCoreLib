package mod.alexndr.simplecorelib.helpers;

import net.minecraft.world.inventory.DataSlot;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

/**
 * An {@link IntReferenceHolder} that uses {@link IntSupplier}s for its getter and setter
 *
 * @author Cadiboo
 */
public class FunctionalIntReferenceHolder extends DataSlot {

	private final IntSupplier getter;
	private final IntConsumer setter;

	public FunctionalIntReferenceHolder(final IntSupplier getter, final IntConsumer setter) {
		this.getter = getter;
		this.setter = setter;
	}

	@Override
	public int get() {
		return this.getter.getAsInt();
	}

	@Override
	public void set(final int newValue) {
		this.setter.accept(newValue);
	}

}
