package jp.kaiz.atsassistmod;

import cpw.mods.fml.common.registry.GameRegistry;
import jp.kaiz.atsassistmod.item.TrainProtectionSelector;
import net.minecraft.item.Item;

public class ATSAssistItem {
    public static Item itemTrainProtectionSelector;

    public void preInit(String modid) {
        ATSAssistItem.itemTrainProtectionSelector = new TrainProtectionSelector();
        GameRegistry.registerItem(ATSAssistItem.itemTrainProtectionSelector, modid + ":" + "trainProtectionSelector");
    }
}
