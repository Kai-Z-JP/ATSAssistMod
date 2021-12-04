package jp.kaiz.atsassistmod;

import jp.kaiz.atsassistmod.event.ATSAssistEventHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = ATSAssistCore.MODID, version = ATSAssistCore.VERSION, name = ATSAssistCore.MODID)
public class ATSAssistCore {
    //変更するとブロック消える
    public static final String MODID = "atsassistmod";

    public static final String VERSION = "1.6.0";

    @Mod.Instance(MODID)
    public static ATSAssistCore INSTANCE;

    private static int guiId = 0;
    public static final int guiId_GroundUnit;
    public static final int guiId_StationAnnounce;
    public static final int guiId_TrainProtectionSelector;
    public static final int guiId_IFTTT;
    public static final int guiId_DataMapEditor;

    @SidedProxy(clientSide = "jp.kaiz.atsassistmod.ClientProxy", serverSide = "jp.kaiz.atsassistmod.CommonProxy")
    public static CommonProxy proxy;

    public static final SimpleNetworkWrapper NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    //preInit init postInitの順
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        new ATSAssistNetwork().init();

        //proxy噛まさなくてもいいかも
        proxy.registerTileEntity();
        proxy.init();
        ATSAssistEventHandler handler = new ATSAssistEventHandler();
        FMLCommonHandler.instance().bus().register(handler);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("[ATSAssist]Loading...");

        //Block登録
        new ATSAssistBlock().preInit();

        //Item登録
        new ATSAssistItem().preInit();

        proxy.preInit();
    }

    static {
        guiId_GroundUnit = guiId++;
        guiId_StationAnnounce = guiId++;
        guiId_TrainProtectionSelector = guiId++;
        guiId_IFTTT = guiId++;
        guiId_DataMapEditor = guiId++;
    }
}