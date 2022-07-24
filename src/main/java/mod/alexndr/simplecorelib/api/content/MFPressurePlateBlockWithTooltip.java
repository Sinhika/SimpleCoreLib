package mod.alexndr.simplecorelib.api.content;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;

public class MFPressurePlateBlockWithTooltip extends MultifunctionPressurePlateBlock
{

    protected String hoverTextKey;
    
    public MFPressurePlateBlockWithTooltip(int pMaxWeight, Sensitivity pSensitify, int pPressedTime,
            Properties pProperties, String pHoverTextKey)
    {
        super(pMaxWeight, pSensitify, pPressedTime, pProperties);
        this.hoverTextKey = pHoverTextKey;
    }

    @Override
    public void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag)
    {
         super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
         pTooltip.add(Component.translatable(hoverTextKey).withStyle(ChatFormatting.GREEN));
    }

    
} // end class
