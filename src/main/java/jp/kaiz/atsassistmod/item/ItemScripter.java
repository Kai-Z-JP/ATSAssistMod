package jp.kaiz.atsassistmod.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.CreativeTabATSAssist;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemScripter extends Item {
    private IIconRegister iconRegister;

    public ItemScripter() {
        setCreativeTab(CreativeTabATSAssist.tabUtils);
        setUnlocalizedName(ATSAssistCore.MODID + ":" + "itemScripter");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_) {
        this.itemIcon = p_94581_1_.registerIcon(ATSAssistCore.MODID.toLowerCase() + ":" + "ItemScripter");
    }
}
