package jp.kaiz.atsassistmod.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.CreativeTabATSAssist;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TrainProtectionSelector extends Item {
    public TrainProtectionSelector() {
        setCreativeTab(CreativeTabATSAssist.tabUtils);
        setUnlocalizedName(ATSAssistCore.MODID + ":" + "TrainProtectionSelector");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        player.openGui(ATSAssistCore.INSTANCE, ATSAssistCore.guiId_TrainProtectionSelector, world,
                MathHelper.ceiling_double_int(player.posX),
                MathHelper.ceiling_double_int(player.posY),
                MathHelper.ceiling_double_int(player.posZ));
        return itemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_) {
        this.itemIcon = p_94581_1_.registerIcon(ATSAssistCore.MODID.toLowerCase() + ":" + "TrainProtectionSelector");
    }
}
