package mod.alexndr.simplecorelib.api.content;

import mod.alexndr.simplecorelib.mixins.AbstractFurnaceBlockEntityAccessor;
import mod.alexndr.simplecorelib.mixins.AbstractFurnaceBlockEntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SomewhatAbstractFurnaceBlockEntity extends AbstractFurnaceBlockEntity
{
    public static final int BURN_TIME_STANDARD = 200;
    protected double fuelMultiplier = 1.0;
    protected boolean hasFuelMultiplier = false;

    protected SomewhatAbstractFurnaceBlockEntity(BlockEntityType<?> type,
                                                 BlockPos pos,
                                                 BlockState blockState,
                                                 RecipeType<? extends AbstractCookingRecipe> recipeType)
    {
        super(type, pos, blockState, recipeType);
    }

    protected RecipeType<? extends AbstractCookingRecipe> getRecipeType()
    {
        return ((AbstractFurnaceBlockEntityAccessor) this).simplecorelib$getRecipeType();
    }

    @Override
    protected int getBurnDuration(@NotNull ItemStack fuel)
    {
        int bt =  getBurnTime(fuel, ((AbstractFurnaceBlockEntityAccessor) this).simplecorelib$getRecipeType());
        if (!hasFuelMultiplier) {
            return bt;
        }
        else {
            return ( (int) Math.ceil(((double) bt) * fuelMultiplier));
        }
    } // end getBurnDuration()

    /**
     * Override for unusual fuels, like Nether Furnace or Necrotic Furnace uses.
     * @param stack
     * @return
     */
    public boolean isCustomFuel(ItemStack stack) {
        return getBurnTime(stack, this.getRecipeType()) > 0;
    }

    /**
     * Returns the fuel burn time for this item stack. If it is zero, this item is not a fuel.
     *      * <p>
     *      * Will never return a negative value.
     *      *
     *      * @return the fuel burn time for this item stack in a furnace.
     *      * @apiNote This method by default returns the {@code burn_time} specified in
     *      *          the {@code furnace_fuels.json} file.
     * Override for custom fuels and burn time handlers (such as Nether Furnace fuels).
     */
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType)
    {
        // default:
        return stack.getBurnTime(recipeType);
    }

    /**
     * replaces AbstractFurnaceBlockEntity.getTotalCookTime() in SomewhatAbstractFurnaceBlockEntity.serverTick().
     * this is where you set faster or slower cooking times for custom furnaces.
     *
     * @param level
     * @param blockEntity
     * @return
     */
    protected int getSmeltTime(Level level, SomewhatAbstractFurnaceBlockEntity blockEntity)
    {
        // default
        return AbstractFurnaceBlockEntity.getTotalCookTime(level, blockEntity);
    }

    /**
     * Returns {@code true} if automation can insert the given item in the given slot from the given side.
     *
     * @param index
     * @param stack
     * @param direction
     */
    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction)
    {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemstack = this.items.get(1);
            return isCustomFuel(stack) || stack.is(Items.BUCKET) && !itemstack.is(Items.BUCKET);
        }
    } // end canPlaceItemThroughFace()

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setItem(int index, ItemStack stack)
    {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, stack);
        this.items.set(index, stack);
        stack.limitSize(this.getMaxStackSize(stack));
        if (index == 0 && !flag) {
            this.cookingTotalTime = getSmeltTime(this.level, this);
            this.cookingProgress = 0;
            this.setChanged();
        }
    } // end setItem()


    public static void serverTick(Level level, BlockPos pos, BlockState state, SomewhatAbstractFurnaceBlockEntity blockEntity)
    {
        boolean flag = blockEntity.isLit();
        boolean flag1 = false;
        if (blockEntity.isLit()) {
            blockEntity.litTime--;
        }
        ItemStack itemstack = blockEntity.items.get(1);
        boolean flag2 = !blockEntity.items.get(0).isEmpty();
        boolean flag3 = !itemstack.isEmpty();
        if (blockEntity.isLit() || flag3 && flag2)
        {
            RecipeHolder<?> recipeholder;
            if (flag2) {
                recipeholder = blockEntity.quickCheck.getRecipeFor(blockEntity, level).orElse(null);
            }
            else {
                recipeholder = null;
            }
            int i = blockEntity.getMaxStackSize();
            if (!blockEntity.isLit() &&
                    AbstractFurnaceBlockEntityInvoker.simplecorelib$callCanBurn(level.registryAccess(),
                            recipeholder, blockEntity.items, i, blockEntity))
            {
                blockEntity.litTime = blockEntity.getBurnDuration(itemstack);
                blockEntity.litDuration = blockEntity.litTime;
                if (blockEntity.isLit())
                {
                    flag1 = true;
                    if (itemstack.hasCraftingRemainingItem()) {
                        blockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                    }
                    else if (flag3)
                    {
                        Item item = itemstack.getItem();
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            blockEntity.items.set(1, itemstack.getCraftingRemainingItem());
                        }
                    } // end-else-if
                } // end-if
            } // end-if
            if (blockEntity.isLit() &&
                    AbstractFurnaceBlockEntityInvoker.simplecorelib$callCanBurn(level.registryAccess(),
                            recipeholder, blockEntity.items, i, blockEntity))
            {
                blockEntity.cookingProgress++;
                if (blockEntity.cookingProgress == blockEntity.cookingTotalTime)
                {
                    blockEntity.cookingProgress = 0;
                    blockEntity.cookingTotalTime = blockEntity.getSmeltTime(level, blockEntity);
                    if (AbstractFurnaceBlockEntityInvoker.simplecorelib$callBurn(level.registryAccess(), recipeholder,
                            blockEntity.items, i, blockEntity))
                    {
                        blockEntity.setRecipeUsed(recipeholder);
                    }

                    flag1 = true;
                } // end-if
            } // end-if
            else {
                blockEntity.cookingProgress = 0;
            } // end-else
        }
        else if (!blockEntity.isLit() && blockEntity.cookingProgress > 0)
        {
            blockEntity.cookingProgress = Mth.clamp(blockEntity.cookingProgress - 2, 0, blockEntity.cookingTotalTime);
        }
        if (flag != blockEntity.isLit())
        {
            flag1 = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, blockEntity.isLit());
            level.setBlock(pos, state, 3);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }
    } // end serverTick()

} // end class
