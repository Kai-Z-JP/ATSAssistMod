package jp.kaiz.atsassistmod;

import cpw.mods.fml.common.registry.GameRegistry;
import jp.kaiz.atsassistmod.item.DataMapEditor;
import jp.kaiz.atsassistmod.item.ItemScripter;
import jp.kaiz.atsassistmod.item.TrainProtectionSelector;
import net.minecraft.item.Item;

public class ATSAssistItem {
    public static Item itemTrainProtectionSelector;
    public static Item itemDataMapEditor;
    public static Item itemScripter;

    public void preInit() {
        GameRegistry.registerItem(itemTrainProtectionSelector = new TrainProtectionSelector(), ATSAssistCore.MODID + ":" + "trainProtectionSelector");
        GameRegistry.registerItem(itemDataMapEditor = new DataMapEditor(), ATSAssistCore.MODID + ":" + "itemDataMapEditor");
        GameRegistry.registerItem(itemScripter = new ItemScripter(), ATSAssistCore.MODID + ":" + "itemScripter");
    }
}
