package mod.alexndr.simplecorelib.api.datagen;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction.NameSource;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

/**
 * A LootTableProvider class with helper functions. Based on work by sciwhiz12
 * on the BaseDefense module.
 * 
 * @author Sinhika
 *
 */
public abstract class BlockLootTableProvider extends AbstractLootTableProvider
{
    protected static final LootItemCondition.Builder SILK_TOUCH = 
            MatchTool.toolMatches(ItemPredicate.Builder.item()
                         .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));

    public BlockLootTableProvider(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
    }

    /**
     * Creates a standard "drop yourself" block loot table, with no special conditions other
     * the standard "survives_explosion".
     * 
     * @param b block being harvested/to be dropped.
     */
    protected void standardDropTable(Block b)
    {
         blockTable(b, LootTable.lootTable().withPool(createStandardDrops(b)));
    }

    /**
     * Create a block loot table that drops an item instead of the block itself. Used 
     * for example, for gems that drop from gem ore blocks. Assumed to be affected by
     * Fortune-enchanted tools.
     * 
     * @param b block being harvested
     * @param ii item dropped by block.
     */
    protected void specialDropTable(Block b, Item ii)
    {
        blockTable(b, LootTable.lootTable().withPool(createItemWithFortuneDrops(b, ii)));
    }

    /**
     * Create a block loot table that drops multiple items instead of the block itself
     * (e.g., redstone_ore, copper_ore). Assumed to be affected by Fortune-enchanted tools.
     * 
     * @param b block being harvested.
     * @param ii items dropped by block
     * @param mincount minimum number of items dropped.
     * @param maxcount maximum number of items dropped.
     */
    protected void multipleDropTable(Block b, Item ii, int mincount, int maxcount)
    {
    	blockTable(b, LootTable.lootTable().withPool(
    			createMultiItemsWithFortuneDrops(b, ii, (float) mincount, (float) maxcount)));
    }
    
    /**
     * Create a block loot table that drops an item-block for the block, but with any
     * custom names copied over. Used for machines and other blocks that might have names.
     * 
     * @param b block being harvested
     * @param ii item-block that is dropped.
     */
    protected void copyNameDropTable(Block b, Item ii)
    {
        blockTable(b, LootTable.lootTable().withPool(createItemWithNameCopy(ii)));
    }

    /**
     * Create a block loot table that drops 1 or 2 slabs depending if the source block is
     * a single slab or a double slab.
     * 
     * @param b slab block being harvested.
     */
    protected void slabDropTable(Block b)
    {
		blockTable(b, LootTable.lootTable().withPool(createSlabDrops(b)));
    }
    
    /**
     * Create a block loot table that drops a single door from a door double-block.
     * @param b
     * @param lootTable
     */
    protected void doorDropTable(Block b)
    {
        blockTable(b, BlockLoot.createDoorTable(b));
    } 
    
    protected void blockTable(Block b, LootTable.Builder lootTable) 
    {
        addTable(b.getLootTable(), lootTable, LootContextParamSets.BLOCK);
    }

    protected LootPool.Builder createStandardDrops(ItemLike itemProvider)
    {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .when(ExplosionCondition.survivesExplosion())
                .add(LootItem.lootTableItem(itemProvider));
    }

    protected LootPool.Builder createSlabDrops(Block b)
    {
    	return LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
				.add(withExplosionDecay(b, LootItem.lootTableItem(b)
						.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(b)
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))));
    }
    
    
    protected LootPool.Builder createItemWithNameCopy(Item itemProvider)
    {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .when(ExplosionCondition.survivesExplosion())
                .add(LootItem.lootTableItem(itemProvider))
                .apply(CopyNameFunction.copyName(NameSource.BLOCK_ENTITY));
    }
    
    protected LootPool.Builder createItemWithFortuneDrops(Block blockIn, Item itemIn)
    {
        return droppingWithSilkTouch(blockIn, withExplosionDecay(blockIn,
                LootItem.lootTableItem(itemIn).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
    
    protected LootPool.Builder createMultiItemsWithFortuneDrops(Block blockIn, Item itemIn,
    														    float min_count, float max_count)
    {
    	return droppingWithSilkTouch(blockIn, 
    			withExplosionDecay(blockIn, 
    					LootItem.lootTableItem(itemIn)
    					.apply(SetItemCountFunction.setCount(UniformGenerator.between(min_count, max_count)))
    					.apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
    
    protected static <T> T withExplosionDecay(ItemLike itemIn, FunctionUserBuilder<T> consumer)
    {
        return (T) (consumer.apply(ApplyExplosionDecay.explosionDecay()));
    }

     @SuppressWarnings({ "unchecked", "rawtypes" })
    protected static LootPool.Builder dropping(Block blockIn, LootItemCondition.Builder builderIn,
            LootPoolEntryContainer.Builder<?> entryBuilderIn)
    {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(
                        ((LootPoolSingletonContainer.Builder) LootItem.lootTableItem(blockIn)
                                .when(builderIn))
                                .otherwise(entryBuilderIn));
    }

    protected static LootPool.Builder droppingWithSilkTouch(Block blockIn, LootPoolEntryContainer.Builder<?> builderIn)
    {
        return dropping(blockIn, SILK_TOUCH, builderIn);
    }
    
} // end class AbstractLootTableProvider
