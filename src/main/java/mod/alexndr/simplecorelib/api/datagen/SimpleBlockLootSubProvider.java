package mod.alexndr.simplecorelib.api.datagen;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

/**
 * A LootTableProvider class with helper functions. Based on work by sciwhiz12
 * on the BaseDefense module.
 * 
 * @author Sinhika
 *
 */
public abstract class SimpleBlockLootSubProvider extends BlockLootSubProvider
{
//    protected static final LootItemCondition.Builder SILK_TOUCH = 
//            MatchTool.toolMatches(ItemPredicate.Builder.item()
//                         .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
	protected static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.PIGLIN_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(Collectors.toSet());

    protected SimpleBlockLootSubProvider(Set<Item> pExplosionResistant, FeatureFlagSet pEnabledFeatures) {
		super(pExplosionResistant, pEnabledFeatures);
	}

    protected SimpleBlockLootSubProvider()
    {
    	super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
    }
    
//	/**
//     * Creates a standard "drop yourself" block loot table, with no special conditions other
//     * the standard "survives_explosion".
//     * 
//     * @param b block being harvested/to be dropped.
//     */
//    protected void standardDropTable(Block b)
//    {
//         blockTable(b, LootTable.lootTable().withPool(createStandardDrops(b)));
//    }
//
//    /**
//     * Create a block loot table that drops an item instead of the block itself. Used 
//     * for example, for gems that drop from gem ore blocks. Assumed to be affected by
//     * Fortune-enchanted tools.
//     * 
//     * @param b block being harvested
//     * @param ii item dropped by block.
//     */
//    protected void specialDropTable(Block b, Item ii)
//    {
//        blockTable(b, LootTable.lootTable().withPool(createItemWithFortuneDrops(b, ii)));
//    }
//
//    /**
//     * Create a block loot table that drops multiple items instead of the block itself
//     * (e.g., redstone_ore, copper_ore). Assumed to be affected by Fortune-enchanted tools.
//     * 
//     * @param b block being harvested.
//     * @param ii items dropped by block
//     * @param mincount minimum number of items dropped.
//     * @param maxcount maximum number of items dropped.
//     */
//    protected void multipleDropTable(Block b, Item ii, int mincount, int maxcount)
//    {
//    	blockTable(b, LootTable.lootTable().withPool(
//    			createMultiItemsWithFortuneDrops(b, ii, (float) mincount, (float) maxcount)));
//    }
//    
//    /**
//     * Create a block loot table that drops an item-block for the block, but with any
//     * custom names copied over. Used for machines and other blocks that might have names.
//     * 
//     * @param b block being harvested
//     * @param ii item-block that is dropped.
//     */
//    protected void copyNameDropTable(Block b, Item ii)
//    {
//        blockTable(b, LootTable.lootTable().withPool(createItemWithNameCopy(ii)));
//    }
//
//    /**
//     * Create a block loot table that drops 1 or 2 slabs depending if the source block is
//     * a single slab or a double slab.
//     * 
//     * @param b slab block being harvested.
//     */
//    protected void slabDropTable(Block b)
//    {
//		blockTable(b, LootTable.lootTable().withPool(createSlabDrops(b)));
//    }
//    
//    /**
//     * Create a block loot table that drops a single door from a door double-block.
//     * @param b
//     * @param lootTable
//     */
//    protected void doorDropTable(Block b)
//    {
//        blockTable(b, BlockLoot.createDoorTable(b));
//    } 
//    
//    protected void blockTable(Block b, LootTable.Builder lootTable) 
//    {
//        addTable(b.getLootTable(), lootTable, LootContextParamSets.BLOCK);
//    }
//
//    protected LootPool.Builder createStandardDrops(ItemLike itemProvider)
//    {
//        return LootPool.lootPool().setRolls(ConstantValue.exactly(1))
//                .when(ExplosionCondition.survivesExplosion())
//                .add(LootItem.lootTableItem(itemProvider));
//    }
//
//    protected LootPool.Builder createSlabDrops(Block b)
//    {
//    	return LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
//				.add(withExplosionDecay(b, LootItem.lootTableItem(b)
//						.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
//								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(b)
//										.setProperties(StatePropertiesPredicate.Builder.properties()
//												.hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))));
//    }
//    
//    
//    protected LootPool.Builder createItemWithNameCopy(Item itemProvider)
//    {
//        return LootPool.lootPool().setRolls(ConstantValue.exactly(1))
//                .when(ExplosionCondition.survivesExplosion())
//                .add(LootItem.lootTableItem(itemProvider))
//                .apply(CopyNameFunction.copyName(NameSource.BLOCK_ENTITY));
//    }
//    
//    protected LootPool.Builder createItemWithFortuneDrops(Block blockIn, Item itemIn)
//    {
//        return droppingWithSilkTouch(blockIn, withExplosionDecay(blockIn,
//                LootItem.lootTableItem(itemIn).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
//    }
//    
//    protected LootPool.Builder createMultiItemsWithFortuneDrops(Block blockIn, Item itemIn,
//    														    float min_count, float max_count)
//    {
//    	return droppingWithSilkTouch(blockIn, 
//    			withExplosionDecay(blockIn, 
//    					LootItem.lootTableItem(itemIn)
//    					.apply(SetItemCountFunction.setCount(UniformGenerator.between(min_count, max_count)))
//    					.apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
//    }
//    
//    protected static <T extends FunctionUserBuilder<T>> T withExplosionDecay(ItemLike itemIn, FunctionUserBuilder<T> consumer)
//    {
//        return (T) (consumer.apply(ApplyExplosionDecay.explosionDecay()));
//    }
//
//     @SuppressWarnings({ "unchecked", "rawtypes" })
//    protected static LootPool.Builder dropping(Block blockIn, LootItemCondition.Builder builderIn,
//            LootPoolEntryContainer.Builder<?> entryBuilderIn)
//    {
//        return LootPool.lootPool().setRolls(ConstantValue.exactly(1))
//                .add(
//                        ((LootPoolSingletonContainer.Builder) LootItem.lootTableItem(blockIn)
//                                .when(builderIn))
//                                .otherwise(entryBuilderIn));
//    }
//
//    protected static LootPool.Builder droppingWithSilkTouch(Block blockIn, LootPoolEntryContainer.Builder<?> builderIn)
//    {
//        return dropping(blockIn, SILK_TOUCH, builderIn);
//    }
    
} // end class AbstractLootTableProvider
