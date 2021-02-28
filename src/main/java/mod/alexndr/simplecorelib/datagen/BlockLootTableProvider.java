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
            MatchTool.builder(ItemPredicate.Builder.create()
                         .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));

    public BlockLootTableProvider(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
    }

    protected void standardDropTable(Block b)
    {
        blockTable(b, LootTable.builder().addLootPool(createStandardDrops(b)));
    }

    protected void specialDropTable(Block b, Item ii)
    {
        blockTable(b, LootTable.builder().addLootPool(createItemWithFortuneDrops(b, ii)));
    }
    
    void blockTable(Block b, LootTable.Builder lootTable) 
    {
        addTable(b.getLootTable(), lootTable, LootParameterSets.BLOCK);
    }

    LootPool.Builder createStandardDrops(IItemProvider itemProvider)
    {
        return LootPool.builder().rolls(ConstantRange.of(1))
                .acceptCondition(SurvivesExplosion.builder())
                .addEntry(ItemLootEntry.builder(itemProvider));
    }

    LootPool.Builder createItemWithFortuneDrops(Block blockIn, Item itemIn)
    {
        return droppingWithSilkTouch(blockIn, withExplosionDecay(blockIn,
                ItemLootEntry.builder(itemIn).acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))));
    }
    
    static <T> T withExplosionDecay(IItemProvider itemIn, ILootFunctionConsumer<T> consumer)
    {
        return (T) (consumer.acceptFunction(ExplosionDecay.builder()));
    }

     @SuppressWarnings({ "unchecked", "rawtypes" })
    static LootPool.Builder dropping(Block blockIn, ILootCondition.IBuilder builderIn,
            LootEntry.Builder<?> entryBuilderIn)
    {
        return LootPool.builder().rolls(ConstantRange.of(1))
                .addEntry(
                        ((StandaloneLootEntry.Builder) ItemLootEntry.builder(blockIn)
                                .acceptCondition(builderIn))
                                .alternatively(entryBuilderIn));
    }

    static LootPool.Builder droppingWithSilkTouch(Block blockIn, LootEntry.Builder<?> builderIn)
    {
        return dropping(blockIn, SILK_TOUCH, builderIn);
    }
   
    
} // end class AbstractLootTableProvider
