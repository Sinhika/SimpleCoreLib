package mod.alexndr.simplecorelib.api.content;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class MFPressurePlateBlockWithTooltip extends MultifunctionPressurePlateBlock
{

    protected String hoverTextKey;
    
    public MFPressurePlateBlockWithTooltip(int pMaxWeight, Sensitivity pSensitify, int pPressedTime,
    		BlockBehaviour.Properties pProperties, BlockSetType pSetType, String pHoverTextKey)
    {
        super(pMaxWeight, pSensitify, pPressedTime, pProperties, pSetType);
        this.hoverTextKey = pHoverTextKey;
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltip, TooltipFlag pFlag)
    {
         super.appendHoverText(pStack, pContext, pTooltip, pFlag);
         pTooltip.add(Component.translatable(hoverTextKey).withStyle(ChatFormatting.GREEN));
    }

    
} // end class
