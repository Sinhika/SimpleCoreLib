package mod.alexndr.simplecorelib.init;

import com.mojang.serialization.MapCodec;
import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.content.ChestLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModCodecs
{
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM =
            DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SimpleCoreLib.MODID);

    // TODO
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ChestLootModifier>> CHEST_LOOT
            = GLM.register("village_house_lootmod", ChestLootModifier.CODEC);


} // end class
