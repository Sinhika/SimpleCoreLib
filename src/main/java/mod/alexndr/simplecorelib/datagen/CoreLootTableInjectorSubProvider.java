package mod.alexndr.simplecorelib.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.datagen.SimpleLootInjectorSubProvider;
import mod.alexndr.simplecorelib.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class CoreLootTableInjectorSubProvider extends SimpleLootInjectorSubProvider
{
    public static final ResourceKey<LootTable> VILLAGE_HOUSE = getInjectionTableId(SimpleCoreLib.MODID, "village_house");

    @Override
    public void generate(HolderLookup.@NotNull Provider pRegistries,
                         BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pGenerator)
    {
        pGenerator.accept(VILLAGE_HOUSE, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .name("simpleores_testloot")
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.test_shears.get()).setWeight(1))
                ));
    }



} // end class
