package jp.kaiz.atsassistmod;

import cpw.mods.fml.common.registry.GameRegistry;
import jp.kaiz.atsassistmod.block.GroundUnit;
import jp.kaiz.atsassistmod.block.StationAnnounce;
import jp.kaiz.atsassistmod.item.ItemBlockWithMetadataCustom;
import net.minecraft.block.Block;

public class ATSAssistBlock {
    public static Block blockGroundUnit;
    public static Block blockStationAnnounce;

    public void preInit(String modid) {
        //Block登録
        blockGroundUnit = new GroundUnit();
        GameRegistry.registerBlock(blockGroundUnit, ItemBlockWithMetadataCustom.class, "tile" + "." + modid + ":" + "groundUnit");
        blockStationAnnounce = new StationAnnounce();
        GameRegistry.registerBlock(blockStationAnnounce, ItemBlockWithMetadataCustom.class, "tile" + "." + modid + ":" + "stationAnnounceBase");
    }
}
