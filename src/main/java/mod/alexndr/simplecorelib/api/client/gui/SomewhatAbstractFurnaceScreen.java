package mod.alexndr.simplecorelib.api.client.gui;

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
    protected static final ResourceLocation LIT_PROGRESS_SPRITE = new ResourceLocation("container/furnace/lit_progress");
    protected static final ResourceLocation BURN_PROGRESS_SPRITE = new ResourceLocation("container/furnace/burn_progress");

    public SomewhatAbstractFurnaceScreen(T menu,
                                         AbstractFurnaceRecipeBookComponent recipeBookComponent,
                                         Inventory playerInventory,
                                         Component title,
                                         ResourceLocation texture)
    {
        super(menu, recipeBookComponent, playerInventory, title, texture, LIT_PROGRESS_SPRITE, BURN_PROGRESS_SPRITE);
    }
} // end class
