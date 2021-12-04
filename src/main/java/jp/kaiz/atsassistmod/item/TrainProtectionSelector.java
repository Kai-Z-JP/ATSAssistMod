package jp.kaiz.atsassistmod.item;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.CreativeTabATSAssist;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class TrainProtectionSelector extends Item {
    public TrainProtectionSelector() {
        setCreativeTab(CreativeTabATSAssist.ATSA);
        setUnlocalizedName(ATSAssistCore.MODID + ":" + "TrainProtectionSelector");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        player.openGui(ATSAssistCore.INSTANCE, ATSAssistCore.guiId_TrainProtectionSelector, world,
                MathHelper.ceil(player.posX),
                MathHelper.ceil(player.posY),
                MathHelper.ceil(player.posZ));
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
    }
}
