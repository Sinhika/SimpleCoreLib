package mod.alexndr.simplecorelib.api.datagen;

import mod.alexndr.simplecorelib.SimpleCoreLib;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class SimpleBlockModelProvider extends BlockModelProvider
{

    public SimpleBlockModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
    }

    protected BlockModelBuilder bars(String name, String parent)
    {
        return withExistingParent(name, new ResourceLocation(SimpleCoreLib.MODID, BLOCK_FOLDER + "/" + parent));
    }
    
    public BlockModelBuilder barsPost(String name, ResourceLocation texture)
    {
        return bars(name, "template_bars_post")
                .texture("particle", texture)
                .texture("bars", texture);
    }
    
    public BlockModelBuilder barsPostEnds(String name, ResourceLocation texture)
    {
        return bars(name, "template_bars_post_ends")
                .texture("particle", texture)
                .texture("edge", texture);
    }
    
    public BlockModelBuilder barsCap(String name, ResourceLocation texture)
    {
        return bars(name, "template_bars_cap")
                .texture("particle", texture)
                .texture("bars", texture)
                .texture("edge", texture);
    }
    
    public BlockModelBuilder barsCapAlt(String name, ResourceLocation texture)
    {
        return bars(name, "template_bars_cap_alt")
                .texture("particle", texture)
                .texture("bars", texture)
                .texture("edge", texture);
    }
    
    public BlockModelBuilder barsSide(String name, ResourceLocation texture)
    {
        return bars(name, "template_bars_side")
                .texture("particle", texture)
                .texture("bars", texture)
                .texture("edge", texture);
        
    }
    
    public BlockModelBuilder barsSideAlt(String name, ResourceLocation texture)
    {
        return bars(name, "template_bars_side_alt")
                .texture("particle", texture)
                .texture("bars", texture)
                .texture("edge", texture);
    }
} // end class
