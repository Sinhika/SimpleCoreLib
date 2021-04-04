package mod.alexndr.simplecorelib.datagen;

import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ILootFunctionConsumer;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyName.Source;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.util.IItemProvider;

/**
 * A LootTableProvider class with helper functions. Based on work by sciwhiz12
 * on the BaseDefense module.
 * 
 * @author Sinhika
 *
 */
public abstract class BlockLootTableProvider extends AbstractLootTableProvider
{
    private static final ILootCondition.IBuilder SILK_TOUCH = 
            MatchTool.toolMatches(ItemPredicate.Builder.item()
                         .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));

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

    protected void blockTable(Block b, LootTable.Builder lootTable) 
    {
        addTable(b.getLootTable(), lootTable, LootParameterSets.BLOCK);
    }

    LootPool.Builder createStandardDrops(IItemProvider itemProvider)
    {
        return LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                .when(SurvivesExplosion.survivesExplosion())
                .add(ItemLootEntry.lootTableItem(itemProvider));
    }

    protected LootPool.Builder createItemWithNameCopy(Item itemProvider)
    {
        return LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                .when(SurvivesExplosion.survivesExplosion())
                .add(ItemLootEntry.lootTableItem(itemProvider))
                .apply(CopyName.copyName(Source.BLOCK_ENTITY));
    }
    
    LootPool.Builder createItemWithFortuneDrops(Block blockIn, Item itemIn)
    {
        return droppingWithSilkTouch(blockIn, withExplosionDecay(blockIn,
                ItemLootEntry.lootTableItem(itemIn).apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
    
    static <T> T withExplosionDecay(IItemProvider itemIn, ILootFunctionConsumer<T> consumer)
    {
        return (T) (consumer.apply(ExplosionDecay.explosionDecay()));
    }

     @SuppressWarnings({ "unchecked", "rawtypes" })
    static LootPool.Builder dropping(Block blockIn, ILootCondition.IBuilder builderIn,
            LootEntry.Builder<?> entryBuilderIn)
    {
        return LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                .add(
                        ((StandaloneLootEntry.Builder) ItemLootEntry.lootTableItem(blockIn)
                                .when(builderIn))
                                .otherwise(entryBuilderIn));
    }

    static LootPool.Builder droppingWithSilkTouch(Block blockIn, LootEntry.Builder<?> builderIn)
    {
        return dropping(blockIn, SILK_TOUCH, builderIn);
    }
   
    
} // end class AbstractLootTableProvider
