package jp.kaiz.atsassistmod;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import jp.kaiz.atsassistmod.block.GroundUnit;
import jp.kaiz.atsassistmod.block.StationAnnounce;
import jp.kaiz.atsassistmod.event.ATSAssistEventHandler;
import jp.kaiz.atsassistmod.gui.ATSAssistGUIHandler;
import jp.kaiz.atsassistmod.item.ItemBlockWithMetadataCustom;
import jp.kaiz.atsassistmod.item.TrainProtectionSelector;
import jp.kaiz.atsassistmod.network.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@Mod(modid = ATSAssistCore.MODID, version = ATSAssistCore.VERSION, name = ATSAssistCore.MODID)
public class ATSAssistCore {
    //変更するとブロック消える
    public static final String MODID = "ATSAssistMod";

    public static final String VERSION = "1.4.1_b1.0";

    @Mod.Instance(MODID)
    public static ATSAssistCore INSTANCE;

    private static int guiId = 0;
    public static final int guiId_GroundUnit;
    public static final int guiId_StationAnnounce;
    public static final int guiId_TrainProtectionSelector;

    public static Block blockGroundUnit;
    public static Block blockStationAnnounce;

    public static Item itemTrainProtectionSelector;

    @SidedProxy(clientSide = "jp.kaiz.atsassistmod.ClientProxy", serverSide = "jp.kaiz.atsassistmod.CommonProxy")
    public static CommonProxy proxy;

    public static final SimpleNetworkWrapper NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    //preInit init postInitの順
    @EventHandler
    public void init(FMLInitializationEvent event) {
        NETWORK_WRAPPER.registerMessage(PacketSetNotch.class, PacketSetNotch.class, 1, Side.SERVER);
        NETWORK_WRAPPER.registerMessage(PacketGroundUnitTileInit.class, PacketGroundUnitTileInit.class, 2, Side.SERVER);
        NETWORK_WRAPPER.registerMessage(PacketGroundUnitTileInitToClient.class, PacketGroundUnitTileInit.class, 3, Side.CLIENT);
        NETWORK_WRAPPER.registerMessage(PacketGroundUnitTile.class, PacketGroundUnitTile.class, 4, Side.SERVER);
        NETWORK_WRAPPER.registerMessage(PacketTrainControllerToClient.class, PacketTrainControllerToClient.class, 5, Side.CLIENT);
        NETWORK_WRAPPER.registerMessage(PacketTrainProtectionSetter.class, PacketTrainProtectionSetter.class, 6, Side.SERVER);
        NETWORK_WRAPPER.registerMessage(PacketSetNotchController.class, PacketSetNotchController.class, 7, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new ATSAssistGUIHandler());
        //proxy噛まさなくてもいいかも
        proxy.registerTileEntity();
        proxy.init();
        ATSAssistEventHandler handler = new ATSAssistEventHandler();
        FMLCommonHandler.instance().bus().register(handler);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("[ATSAssist]Loading...");

        //Block登録
        blockGroundUnit = new GroundUnit();
        GameRegistry.registerBlock(blockGroundUnit, ItemBlockWithMetadataCustom.class, "tile" + "." + MODID + ":" + "groundUnit");
        blockStationAnnounce = new StationAnnounce();
        GameRegistry.registerBlock(blockStationAnnounce, ItemBlockWithMetadataCustom.class, "tile" + "." + MODID + ":" + "stationAnnounceBase");

        //Item登録
        itemTrainProtectionSelector = new TrainProtectionSelector();
        GameRegistry.registerItem(itemTrainProtectionSelector, MODID + ":" + "trainProtectionSelector");

        proxy.preInit();
    }

    static {
        guiId_GroundUnit = guiId++;
        guiId_StationAnnounce = guiId++;
        guiId_TrainProtectionSelector = guiId++;
    }
}