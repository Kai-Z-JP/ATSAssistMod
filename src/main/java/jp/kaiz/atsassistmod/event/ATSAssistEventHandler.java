package jp.kaiz.atsassistmod.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import jp.kaiz.atsassistmod.controller.TrainControllerManager;


public class ATSAssistEventHandler {
	@SubscribeEvent
	public void onTick(TickEvent.ServerTickEvent event) {
		if (event.side == Side.SERVER) {
			TrainControllerManager.onTick();
		}
	}

//	@SubscribeEvent
//	public void onConnectFromClient(FMLNetworkEvent.ServerConnectionFromClientEvent event) {
//		if (!event.isLocal) {
//			try {
//				List<String> strings = new ArrayList<>();
//				URL url = new URL("https://files.kaiz.jp/kcf/atsassist1.4.kcf");
//				URLConnection conn = url.openConnection();
//				/////////////////////////////////////////////////////
//				conn.setRequestProperty("User-agent", "Mozilla/5.0");
//				/////////////////////////////////////////////////////
//				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//				String string;
//				while ((string = br.readLine()) != null) {
//					if (!string.startsWith("//")) {
//						strings.add(string);
//					}
//				}
//				br.close();
//
//				if (!strings.contains(ATSAssistCore.gip)) {
//					((NetHandlerPlayServer) event.handler).
//							kickPlayerFromServer(
//									"[ATSAssistMod]" +
//											"\nErrorCode: 91" +
//											"\n" +
//											"\nSorry ! An error has occurred ! Please contact Twitter@Kaiz_JP for more information !" +
//											"\n" +
//											"\nエラーが発生しました。詳しくはTwitter@Kaiz_JPまでお問い合わせください。");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
