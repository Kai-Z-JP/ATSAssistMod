package jp.kaiz.atsassistmod;

import jp.kaiz.atsassistmod.item.TrainProtectionSelector;
import jp.ngt.ngtlib.util.NGTRegHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ATSAssistItem {
    public static Item itemTrainProtectionSelector;
    public static Item itemDataMapEditor;

    public void preInit() {
        itemTrainProtectionSelector = NGTRegHandler.register(new TrainProtectionSelector(), "train_protection_selector", "atsa:train_protection_selector", CreativeTabATSAssist.ATSA, ATSAssistCore.MODID);
//        itemDataMapEditor = NGTRegHandler.register(new DataMapEditor(), "item_datamap_editor", "atsa:item_datamap_editor", CreativeTabATSAssist.ATSA, ATSAssistCore.MODID);
    }


    @SideOnly(Side.CLIENT)
    public static void initClient() {
        //Itemテクスチャ登録
        ATSAssistItem.registerItemModel(itemTrainProtectionSelector, 0, "train_protection_selector");
    }


    @SideOnly(Side.CLIENT)
    public static void registerItemModel(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ATSAssistCore.MODID + ":" + name, "inventory"));
    }
}
