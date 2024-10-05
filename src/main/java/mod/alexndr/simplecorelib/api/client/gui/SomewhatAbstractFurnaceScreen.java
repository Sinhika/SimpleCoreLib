package mod.alexndr.simplecorelib.api.client.gui;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class SomewhatAbstractFurnaceScreen<T extends AbstractFurnaceMenu> extends AbstractFurnaceScreen<T>
{
    // for furnaces
    protected static final ResourceLocation LIT_PROGRESS_SPRITE =
            new ResourceLocation(SimpleCoreLib.MODID, "container/furnace/lit_progress");
    protected static final ResourceLocation BURN_PROGRESS_SPRITE =
            new ResourceLocation(SimpleCoreLib.MODID, "container/furnace/burn_progress");

    // for blast_furnace
    protected static final ResourceLocation BLAST_FURNACE_LIT_PROGRESS_SPRITE =
            new ResourceLocation(SimpleCoreLib.MODID, "container/blast_furnace/lit_progress");
    protected static final ResourceLocation BLAST_FURNACE_BURN_PROGRESS_SPRITE =
            new ResourceLocation(SimpleCoreLib.MODID, "container/blast_furnace/burn_progress");

    // for smoker
    protected static final ResourceLocation SMOKER_LIT_PROGRESS_SPRITE =
            new ResourceLocation(SimpleCoreLib.MODID, "container/smoker/lit_progress");
    protected static final ResourceLocation SMOKER_BURN_PROGRESS_SPRITE =
            new ResourceLocation(SimpleCoreLib.MODID, "container/smoker/burn_progress");

    public SomewhatAbstractFurnaceScreen(T menu,
                                         AbstractFurnaceRecipeBookComponent recipeBookComponent,
                                         Inventory playerInventory,
                                         Component title,
                                         ResourceLocation texture)
    {
        super(menu, recipeBookComponent, playerInventory, title, texture,
                SomewhatAbstractFurnaceScreen.LIT_PROGRESS_SPRITE, SomewhatAbstractFurnaceScreen.BURN_PROGRESS_SPRITE);
    }

    public SomewhatAbstractFurnaceScreen(T menu,
                                         AbstractFurnaceRecipeBookComponent recipeBookComponent,
                                         Inventory playerInventory,
                                         Component title,
                                         ResourceLocation texture,
                                         ResourceLocation litProgressSprite,
                                         ResourceLocation burnProgressSprite)
    {
        super(menu, recipeBookComponent, playerInventory, title, texture, litProgressSprite, burnProgressSprite);
    }
} // end class
