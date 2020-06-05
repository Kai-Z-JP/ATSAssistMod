package jp.kaiz.atsassistmod;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import jp.kaiz.atsassistmod.event.ATSAssistEventHandler;

@Mod(modid = ATSAssistCore.MODID, version = ATSAssistCore.VERSION, name = ATSAssistCore.MODID)
public class ATSAssistCore {
    //変更するとブロック消える
    public static final String MODID = "ATSAssistMod";

    public static final String VERSION = "1.4.1_b4";

    @Mod.Instance(MODID)
    public static ATSAssistCore INSTANCE;

    private static int guiId = 0;
    public static final int guiId_GroundUnit;
    public static final int guiId_StationAnnounce;
    public static final int guiId_TrainProtectionSelector;
    public static final int guiId_DataMapToRS;

    @SidedProxy(clientSide = "jp.kaiz.atsassistmod.ClientProxy", serverSide = "jp.kaiz.atsassistmod.CommonProxy")
    public static CommonProxy proxy;

    public static final SimpleNetworkWrapper NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    //preInit init postInitの順
    @EventHandler
    public void init(FMLInitializationEvent event) {
        new ATSAssistNetwork().init();

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
        new ATSAssistBlock().preInit(MODID);

        //Item登録
        new ATSAssistItem().preInit(MODID);

        proxy.preInit();
    }

    static {
        guiId_GroundUnit = guiId++;
        guiId_StationAnnounce = guiId++;
        guiId_TrainProtectionSelector = guiId++;
        guiId_DataMapToRS = guiId++;
    }
}