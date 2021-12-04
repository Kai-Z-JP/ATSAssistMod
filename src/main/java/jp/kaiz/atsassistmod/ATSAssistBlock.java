package jp.kaiz.atsassistmod;

import jp.kaiz.atsassistmod.block.GroundUnit;
import jp.kaiz.atsassistmod.block.IFTTT;
import jp.ngt.ngtlib.item.ItemBlockCustom;
import jp.ngt.ngtlib.util.NGTRegHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ATSAssistBlock {
    public static Block blockGroundUnit, blockStationAnnounce, blockIFTTT;

    public void preInit() {
        //Block登録
        blockGroundUnit = NGTRegHandler.register(new GroundUnit(), "groundunit", "atsa:groundunit", CreativeTabATSAssist.ATSA, ItemBlockCustom.class, ATSAssistCore.MODID);
        blockIFTTT = NGTRegHandler.register(new IFTTT(), "ifttt", "atsa:ifttt", CreativeTabATSAssist.ATSA, ATSAssistCore.MODID);
//        blockStationAnnounce = NGTRegHandler.register(new StationAnnounce(), "stationannounce", "atsa:stationannounce", CreativeTabATSAssist.ATSA, ItemBlockCustom.class, ATSAssistCore.MODID);
    }


    @SideOnly(Side.CLIENT)
    public static void initClient() {
        //Blockテクスチャ登録
        ATSAssistBlock.registerBlockModel(blockIFTTT, 0, "ifttt");
        ATSAssistBlock.registerBlockModels(blockGroundUnit, "groundunit", 0, 1, 2, 4, 5, 6, 7, 9, 10, 11, 13, 14, 15);

    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockModel(Block block, int meta, String name) {
        ATSAssistItem.registerItemModel(Item.getItemFromBlock(block), meta, name);
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockModels(Block block, String name, int... metas) {
        for (int i : metas) {
            ATSAssistBlock.registerBlockModel(block, i, name + i);
        }
    }
}
