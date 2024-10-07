package mod.alexndr.simplecorelib.api.content.block;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.AABB;

/**
 * Incorporate both weighted pressure plate semantics and that of stone and wood pressure plates,
 * and another type that only responds to players.
 * 
 * @author Sinhika
 *
 */
public class MultifunctionPressurePlateBlock extends WeightedPressurePlateBlock
{
    protected final MultifunctionPressurePlateBlock.Sensitivity sensitivity;
    protected final boolean is_weighted;
    protected final int pressTime;
    
    /**
     * 
     * @param pMaxWeight - 15 * number of entities per signal strength. light weighted plate=15, heavy weighted plate=150.
     * @param pSensitify - @see MultifunctionPressurePlateBlock.Sensitivity
     * @param pPressedTime - game ticks to deactivate when no longer pressed. heavy/light weighted plate=10; stone/wooden plate=20.
     * @param pProperties - usually @code{Block.Properties.of(Material.STONE).noCollission().strength(0.5F).sound(SoundType.STONE)}
     */
    public MultifunctionPressurePlateBlock(int pMaxWeight, MultifunctionPressurePlateBlock.Sensitivity pSensitify, 
    									   int pPressedTime, BlockBehaviour.Properties pProperties,
    									   BlockSetType pSetType)
    {
        super(pMaxWeight, pSetType, pProperties);
        this.sensitivity = pSensitify;
        this.pressTime = pPressedTime;
        this.is_weighted = List.of(Sensitivity.EVERYTHING_WEIGHTED, Sensitivity.MOBS_WEIGHTED, Sensitivity.PLAYERS_WEIGHTED,
                                   Sensitivity.LIVING_WEIGHTED)
                             .contains(sensitivity);
    } // end ctor
    
    
    @Override
    protected int getPressedTime()
    {
        return this.pressTime;
    }


    @Override
    protected int getSignalStrength(Level pLevel, BlockPos pPos)
    {
        List<? extends Entity> list;
        AABB aabb = TOUCH_AABB.move(pPos);
        
        switch(this.sensitivity) 
        {
            case EVERYTHING:
            case EVERYTHING_WEIGHTED:
               list = pLevel.getEntities((Entity)null, aabb);
               break;
            case LIVING:
            case LIVING_WEIGHTED:
                list = pLevel.getEntitiesOfClass(LivingEntity.class, aabb, arg0 -> ! (arg0 instanceof ArmorStand));
                break;
            case MOBS:
            case MOBS_WEIGHTED:
               list = pLevel.getEntitiesOfClass(Mob.class, aabb);
               break;
            case PLAYERS:
            case PLAYERS_WEIGHTED:
                list = pLevel.getEntitiesOfClass(Player.class, aabb);
                break;
            default:
               return 0;
        } // end-switch
        
        if (is_weighted)
        {
            int i = Math.min(list.size(), this.maxWeight);
            if (i > 0)
            {
                float f = (float) Math.min(this.maxWeight, i) / (float) this.maxWeight;
                return Mth.ceil(f * 15.0F);
            } 
            else
            {
                return 0;
            }
        }        
        else
        {
            if (!list.isEmpty())
            {
                for (Entity entity : list)
                {
                    if (!entity.isIgnoringBlockTriggers())
                    {
                        return 15;
                    }
                }
            }
            return 0;
        } // end-else
    } // end getSignalStrenth()

    @Override
    protected int getSignalForState(BlockState pState)
    {
        if (is_weighted)
        {
            return pState.getValue(POWER);
        }
        else {
            return pState.getValue(POWER) > 0 ? 15 : 0;
        }
    } // end getSignalForState()


    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltip, TooltipFlag pFlag)
    {
        super.appendHoverText(pStack, pContext, pTooltip, pFlag);
        String tipKey = null;
        
        switch(this.sensitivity) 
        {
            case EVERYTHING:
            case EVERYTHING_WEIGHTED:
               tipKey = "tips.pressure_plate.everything";
               break;
            case LIVING:
            case LIVING_WEIGHTED:
                tipKey = "tips.pressure_plate.living";
                break;
            case MOBS:
            case MOBS_WEIGHTED:
                tipKey = "tips.pressure_plate.mobs";
               break;
            case PLAYERS:
            case PLAYERS_WEIGHTED:
                tipKey = "tips.pressure_plate.players";
                break;
        } // end-switch
        pTooltip.add(Component.translatable(tipKey).withStyle(ChatFormatting.GREEN));
    }



    /**
     * Enum that describes what the pressure-plate is sensitive to. WEIGHTED values also count entities by maxWeight/15 units to
     * determine signal strength. Non-weighted plates are either ON (15) or OFF (0).
     * 
     * @author Sinhika
     *
     */
    public static enum Sensitivity {
        EVERYTHING,         // any entity whatsoever
        LIVING,             // living entities that are not armorstands.
        MOBS,               // non-player living entities only.
        PLAYERS,            // player entities only.
        EVERYTHING_WEIGHTED,
        LIVING_WEIGHTED,
        MOBS_WEIGHTED,
        PLAYERS_WEIGHTED;
     }
} // end class
