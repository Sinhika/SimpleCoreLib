package mod.alexndr.simplecorelib.config;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.config.SimpleConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = SimpleCoreLib.MODID, bus = EventBusSubscriber.Bus.MOD)
public class SimpleCoreLibConfig extends SimpleConfig
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue serverTestShearDurability;

    static {
        // general
        BUILDER.push("General");
        serverTestShearDurability = BUILDER
                .comment("test shears durability value (tests initializing properties from config)")
                .translation(SimpleCoreLib.MODID + ".config.test_shears_durability")
                .defineInRange("TestShearDurability", 1500, 1, 99999);
        BUILDER.pop();
    }
    public static final ModConfigSpec SPEC = BUILDER.build();

    public static int testShearDurability = 10;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        testShearDurability = serverTestShearDurability.get();
    }
} // end class
