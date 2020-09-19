package jp.kaiz.atsassistmod;

import cpw.mods.fml.common.registry.GameRegistry;
import jp.kaiz.atsassistmod.item.DataMapEditor;
import jp.kaiz.atsassistmod.item.TrainProtectionSelector;
import net.minecraft.item.Item;

public class ATSAssistItem {
	public static Item itemTrainProtectionSelector;
	public static Item itemDataMapEditor;

	public void preInit() {
		ATSAssistItem.itemTrainProtectionSelector = new TrainProtectionSelector();
		GameRegistry.registerItem(ATSAssistItem.itemTrainProtectionSelector, ATSAssistCore.MODID + ":" + "trainProtectionSelector");
		ATSAssistItem.itemDataMapEditor = new DataMapEditor();
		GameRegistry.registerItem(ATSAssistItem.itemDataMapEditor, ATSAssistCore.MODID + ":" + "itemDataMapEditor");
	}
}
