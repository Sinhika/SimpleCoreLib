package mod.alexndr.simplecorelib.api.content.block_entity;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.mixins.AbstractFurnaceBlockEntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class AbstractYieldEnhancingFurnaceBlockEntity extends SomewhatAbstractFurnaceBlockEntity
{
    protected int YieldChance = 0;
    protected int YieldAmount = 0;
    protected static Random generator = new Random();

    public AbstractYieldEnhancingFurnaceBlockEntity(BlockEntityType<?> tileEntityTypeIn,
                                                    RecipeType<? extends AbstractCookingRecipe> recipeTypeIn,
                                                    BlockPos blockpos, BlockState blockstate)
    {
        super(tileEntityTypeIn, blockpos, blockstate, recipeTypeIn);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state,
                                  AbstractYieldEnhancingFurnaceBlockEntity blockEntity)
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
            int maxStackSize = blockEntity.getMaxStackSize();
            if (!blockEntity.isLit() &&
                    AbstractFurnaceBlockEntityInvoker.simplecorelib$callCanBurn(level.registryAccess(),
                            recipeholder, blockEntity.items, maxStackSize, blockEntity))
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
                            recipeholder, blockEntity.items, maxStackSize, blockEntity))
            {
                blockEntity.cookingProgress++;
                if (blockEntity.cookingProgress == blockEntity.cookingTotalTime)
                {
                    blockEntity.cookingProgress = 0;
                    blockEntity.cookingTotalTime = blockEntity.getSmeltTime(level, blockEntity);
                    if (customBurn(level.registryAccess(), recipeholder,
                            blockEntity.items, maxStackSize, blockEntity))
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

    } // end serverTick

    public static boolean customBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<?> recipe,
                                      NonNullList<ItemStack> inventory, int maxStackSize,
                                     AbstractYieldEnhancingFurnaceBlockEntity furnace)
    {
        //SimpleCoreLib.LOGGER.debug("entered AbstractYieldEnhandingFurnaceBlockEntity.customBurn\n");

        if (recipe != null)
        {
            ItemStack itemstack = inventory.get(0);
            ItemStack itemstack1 = ((RecipeHolder<? extends AbstractCookingRecipe>) recipe).value().assemble(furnace, registryAccess);
            ItemStack itemstack2 = inventory.get(2);

            // enhanced yield processing
            if (furnace.YieldChance > 0 && furnace.YieldAmount > 0)
            {
                int r = generator.nextInt(100);
                if(r <= furnace.YieldChance && ! itemstack1.isEmpty())
                {
                    int k = furnace.YieldAmount;
                    if ((k + itemstack1.getCount()) < itemstack1.getMaxStackSize())
                    {
                        itemstack1.grow(k);
                    }
                    else if ((itemstack1.getMaxStackSize() - itemstack1.getCount()) > 0)
                    {
                        itemstack1.grow(itemstack1.getMaxStackSize() - itemstack1.getCount());
                    }
                }
            }
            if (itemstack2.isEmpty()) {
                inventory.set(2, itemstack1.copy());
            }
            else if (ItemStack.isSameItemSameComponents(itemstack2, itemstack1)) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (itemstack.is(Blocks.WET_SPONGE.asItem()) && !inventory.get(1).isEmpty() && inventory.get(1).is(Items.BUCKET)) {
                inventory.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
            return true;
        }
        else {
            return false;
        }
    } // end customBurn ()

} // end class
