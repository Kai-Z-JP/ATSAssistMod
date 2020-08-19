package jp.kaiz.atsassistmod;

import cpw.mods.fml.common.registry.GameRegistry;
import jp.kaiz.atsassistmod.block.GroundUnit;
import jp.kaiz.atsassistmod.block.IFTTT;
import jp.kaiz.atsassistmod.block.StationAnnounce;
import jp.kaiz.atsassistmod.item.ItemBlockWithMetadataCustom;
import net.minecraft.block.Block;

public class ATSAssistBlock {
    public static Block blockGroundUnit, blockStationAnnounce, blockIFTTT;

    public void preInit() {
        //Block登録
        blockGroundUnit = new GroundUnit();
        GameRegistry.registerBlock(blockGroundUnit, ItemBlockWithMetadataCustom.class, "tile" + "." + ATSAssistCore.MODID + ":" + "groundUnit");
        blockIFTTT = new IFTTT();
        GameRegistry.registerBlock(blockIFTTT, ItemBlockWithMetadataCustom.class, "tile" + "." + ATSAssistCore.MODID + ":" + "IFTTT");
        blockStationAnnounce = new StationAnnounce();
        GameRegistry.registerBlock(blockStationAnnounce, ItemBlockWithMetadataCustom.class, "tile" + "." + ATSAssistCore.MODID + ":" + "stationAnnounceBase");
    }
}
