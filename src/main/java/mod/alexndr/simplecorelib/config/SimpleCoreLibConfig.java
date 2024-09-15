package mod.alexndr.simplecorelib.config;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import mod.alexndr.simplecorelib.api.config.SimpleConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class SimpleCoreLibConfig extends SimpleConfig
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue serverShowTestShears;
    private static final ModConfigSpec.IntValue serverTestShearDurability;
    private static final ModConfigSpec.BooleanValue serverShowTestFurnace;

    private static final ModConfigSpec.BooleanValue serverShowTestCubeAll;
    private static final ModConfigSpec.BooleanValue serverShowTestCubeColumn;
    private static final ModConfigSpec.BooleanValue serverShowTestOreBlock;
    private static final ModConfigSpec.BooleanValue serverShowTestPlate;
    private static final ModConfigSpec.BooleanValue serverShowTestBars;
    private static final ModConfigSpec.BooleanValue serverShowTestSidedCube;

    static {
        // general
        BUILDER.push("General");
        serverShowTestCubeAll = BUILDER
                .comment("show texture test_cube_all objects in Creative Tab")
                .define("Show test_cube_all", false);
        serverShowTestCubeColumn = BUILDER
                .comment("show texture test_cube_column objects in Creative Tab")
                .define("Show test_cube_column", false);
        serverShowTestShears = BUILDER
                .comment("show texture test_shears in Creative Tab")
                .define("Show test_shears", false);
        serverTestShearDurability = BUILDER
                .comment("test shears durability value (tests initializing properties from config)")
                .translation(SimpleCoreLib.MODID + ".config.test_shears_durability")
                .defineInRange("TestShearDurability", 1500, 1, 99999);
        serverShowTestFurnace = BUILDER
                .comment("show texture test_furnace in Creative Tab")
                .define("Show test_furnace", false);
        serverShowTestOreBlock = BUILDER
                .comment("show texture original_copper_ore in Creative Tab")
                .define("Show original_copper_ore", false);
        serverShowTestPlate = BUILDER
                .comment("show texture test_plate in Creative Tab")
                .define("Show test_plate", false);
        serverShowTestBars = BUILDER
                .comment("show texture test_bars in Creative Tab")
                .define("Show test_bars", false);
        serverShowTestSidedCube = BUILDER
                .comment("show texture test_sided_cube in Creative Tab")
                .define("Show test_sided_cube", false);

        BUILDER.pop();
    }
    public static final ModConfigSpec SPEC = BUILDER.build();

    public static int testShearDurability = 10;
    public static boolean ShowTestShears;
    public static boolean ShowTestFurnace;
    public static boolean showTestCubeAll;
    public static boolean showTestCubeColumn;
    public static boolean ShowTestOreBlock;
    public static boolean ShowTestPlate;
    public static boolean ShowTestBars;
    public static boolean ShowTestSidedCube;

    public static void onLoad(final ModConfigEvent event)
    {
        testShearDurability = serverTestShearDurability.get();
        showTestCubeAll = serverShowTestCubeAll.get();
        ShowTestShears = serverShowTestShears.get();
        ShowTestFurnace = serverShowTestFurnace.get();
        showTestCubeColumn = serverShowTestCubeColumn.get();
        ShowTestOreBlock = serverShowTestOreBlock.get();
        ShowTestPlate = serverShowTestPlate.get();
        ShowTestBars = serverShowTestBars.get();
        ShowTestSidedCube = serverShowTestSidedCube.get();
    }

} // end class
