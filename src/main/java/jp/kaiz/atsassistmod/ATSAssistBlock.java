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
        GameRegistry.registerBlock(blockGroundUnit = new GroundUnit(), ItemBlockWithMetadataCustom.class, "tile" + "." + ATSAssistCore.MODID + ":" + "groundUnit");
        GameRegistry.registerBlock(blockIFTTT = new IFTTT(), ItemBlockWithMetadataCustom.class, "tile" + "." + ATSAssistCore.MODID + ":" + "IFTTT");
        GameRegistry.registerBlock(blockStationAnnounce = new StationAnnounce(), ItemBlockWithMetadataCustom.class, "tile" + "." + ATSAssistCore.MODID + ":" + "stationAnnounceBase");
    }
}
