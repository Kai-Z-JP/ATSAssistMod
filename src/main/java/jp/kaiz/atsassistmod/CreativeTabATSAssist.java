package jp.kaiz.atsassistmod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabATSAssist extends CreativeTabs {
    public static final CreativeTabs tabUtils = new CreativeTabATSAssist("ATSAssist_utils");

    private CreativeTabATSAssist(String label) {
        super(label);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return Item.getItemFromBlock(ATSAssistBlock.blockIFTTT);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return "ATSAssist";
    }
}
