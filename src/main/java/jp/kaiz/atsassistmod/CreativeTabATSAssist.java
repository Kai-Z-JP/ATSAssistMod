package jp.kaiz.atsassistmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabATSAssist extends CreativeTabs {
    public static final CreativeTabs ATSA = new CreativeTabATSAssist("atsa_utils");

    private CreativeTabATSAssist(String label) {
        super(label);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        return new ItemStack(Item.getItemFromBlock(ATSAssistBlock.blockIFTTT));
    }


    @Override
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return "ATSAssist";
    }
}
