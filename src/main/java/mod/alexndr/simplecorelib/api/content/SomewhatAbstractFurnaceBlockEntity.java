package mod.alexndr.simplecorelib.api.content;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

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

    @Override protected int getBurnDuration(@NotNull ItemStack fuel)
    {
        if (!hasFuelMultiplier) {
            return super.getBurnDuration(fuel);
        }
        else {
            return ( (int) Math.ceil(((double) super.getBurnDuration(fuel)) * fuelMultiplier));
        }
    } // end getBurnDuration()

    public boolean isCustomFuel(ItemStack stack) {
        return stack.getBurnTime(null) > 0;
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
        AbstractFurnaceBlockEntity.getTotalCookTime(level, blockEntity);
    }

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
                    canBurn(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity))
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
                    canBurn(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity))
            {
                blockEntity.cookingProgress++;
                if (blockEntity.cookingProgress == blockEntity.cookingTotalTime)
                {
                    blockEntity.cookingProgress = 0;
                    blockEntity.cookingTotalTime = getSmeltTime(level, blockEntity);
                    if (burn(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity))
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
            state = state.setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(blockEntity.isLit()));
            level.setBlock(pos, state, 3);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }
    } // end serverTick()

} // end class
