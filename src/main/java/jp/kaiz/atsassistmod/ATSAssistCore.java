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
import jp.kaiz.atsassistmod.network.*;
import net.minecraft.block.Block;

@Mod(modid = ATSAssistCore.MODID, version = ATSAssistCore.VERSION, name = ATSAssistCore.MODID)
public class ATSAssistCore {
	//変更するとブロック消える
	public static final String MODID = "ATSAssistMod";

	public static final String VERSION = "1.4beta_v8";

	@Mod.Instance(MODID)
	public static ATSAssistCore INSTANCE;

	private static int guiId = 0;
	public static final int guiId_GroundUnit;

	public static Block blockGroundUnit;
	public static Block stationAnnounceBase;

	@SidedProxy(clientSide = "jp.kaiz.atsassistmod.ClientProxy", serverSide = "jp.kaiz.atsassistmod.CommonProxy")
	public static CommonProxy proxy;

	public static final SimpleNetworkWrapper NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
//	public static String gip;

	//preInit init postInitの順

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NETWORK_WRAPPER.registerMessage(PacketATSAssistKey.class, PacketATSAssistKey.class, 1, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(PacketGroundUnitTileInit.class, PacketGroundUnitTileInit.class, 2, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(PacketGroundUnitTileInitToClient.class, PacketGroundUnitTileInit.class, 3, Side.CLIENT);
		NETWORK_WRAPPER.registerMessage(PacketGroundUnitTile.class, PacketGroundUnitTile.class, 4, Side.SERVER);
		NETWORK_WRAPPER.registerMessage(PacketTrainControllerToClient.class, PacketTrainControllerToClient.class, 5, Side.CLIENT);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new ATSAssistGUIHandler());
		//proxy噛まさなくてもいいかも
		proxy.registerTileEntity();
		proxy.init();
		//鯖側だけだと動作しない
		ATSAssistEventHandler handler = new ATSAssistEventHandler();
//		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);
	}


	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println("[ATSAssist]Loading...");
//		try {
//			//ip認証 公開時削除予定
//			//アルゴリズム 最強SHA-512
//			String algorithm = "SHA-512";
//			//生成
//			byte[] bytes = MessageDigest.getInstance(algorithm).digest(getIp().getBytes(StandardCharsets.UTF_8));
//			//Stringにしてぶちこむ
//			gip = DatatypeConverter.printHexBinary(bytes);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		blockGroundUnit = new GroundUnit();
		GameRegistry.registerBlock(blockGroundUnit, ItemBlockWithMetadataCustom.class, "tile" + "." + MODID + ":" + "groundUnit");
		stationAnnounceBase = new StationAnnounce();
//		GameRegistry.registerBlock(stationAnnounceBase, ItemBlockWithMetadataCustom.class, "tile" + "." + MODID + ":" + "stationAnnounceBase");
		proxy.preInit();
	}

	static {
		//強制バージョンアップ
//		try {
//			URL url = new URL("https://files.kaiz.jp/mod/ATSAssistMod.version");
//			URLConnection conn = url.openConnection();
//			/////////////////////////////////////////////////////
//			conn.setRequestProperty("User-agent", "Mozilla/5.0");
//			/////////////////////////////////////////////////////
//			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String string = br.readLine();
//
//			if (!string.equals(ATSAssistCore.VERSION)) {
//				URLConnection codeSource = ATSAssistCore.class.getProtectionDomain().getCodeSource().getLocation().openConnection();
//				if (codeSource instanceof JarURLConnection) {
//					URL myModZipFile = ((JarURLConnection) codeSource).getJarFileURL();
//					System.out.println(myModZipFile);
//					File file = new File(myModZipFile.getFile());
//					System.out.println(file);
//					URL newModFile = new URL(br.readLine());
//
//					URLConnection newModFileConn = newModFile.openConnection();
//					/////////////////////////////////////////////////////
//					newModFileConn.setRequestProperty("User-agent", "Mozilla/5.0");
//					/////////////////////////////////////////////////////
//
//					// Input Stream
//					ReadableByteChannel readableByteChannel = Channels.newChannel(newModFileConn.getInputStream());
//
//					// Output Stream
//					FileChannel fileChannel = new FileOutputStream(file).getChannel();
//
//					fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
//					br.close();
//
//					CrashReport crashReport = CrashReport.makeCrashReport(new Exception("[ATSAssist] Update Complete. Please restart Minecraft. This is NOT bug!!!"),
//							" This is NOT bug!!!");
//					crashReport.makeCategory("Initialization");
//					crashReport = Minecraft.getMinecraft().addGraphicsAndWorldToCrashReport(crashReport);
//					Minecraft.getMinecraft().displayCrashReport(crashReport);
//				}
//			} else {
//				br.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		guiId_GroundUnit = guiId++;
	}

//	private static String getIp() throws Exception {
//		URL checkIP = new URL("http://checkip.amazonaws.com");
//		BufferedReader in = null;
//		try {
//			in = new BufferedReader(new InputStreamReader(
//					checkIP.openStream()));
//			return in.readLine();
//		} finally {
//			if (in != null) {
//				try {
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
}