package mod.alexndr.simplecorelib.api.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public abstract class SimpleRecipeProvider extends RecipeProvider
{
    public SimpleRecipeProvider(PackOutput pOutput,
                                CompletableFuture<HolderLookup.Provider> pRegistries)
    {
        super(pOutput, pRegistries);
    }

} // end class
