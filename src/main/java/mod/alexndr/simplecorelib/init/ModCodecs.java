package mod.alexndr.simplecorelib.init;

import com.mojang.serialization.MapCodec;
import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.loot.ChestLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModCodecs
{
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SimpleCoreLib.MODID);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> CHEST_LOOT
            = GLM.register("village_house_loot", () -> ChestLootModifier.CODEC);


} // end class
