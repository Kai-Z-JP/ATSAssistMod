package jp.kaiz.atsassistmod.controller;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.network.PacketTrainControllerToClient;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import jp.ngt.rtm.entity.train.util.Formation;
import jp.ngt.rtm.entity.train.util.FormationEntry;
import jp.ngt.rtm.entity.train.util.FormationManager;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
 * 【JavaScriptから呼び出そうとしている方へ】
 * <p>
 * クライアント側から呼ばないでください！！！！！
 * 全てサーバー側で処理をしています。
 * クライアント側で呼んでも一切動作しません！！！！！！！！！！！！！！！
 * <p>
 * ⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
 * ■■■■■■⬜⬜⬜⬜⬜⬜■■■■⬜⬜⬜⬜■■⬜⬜⬜⬜■■⬜⬜■⬜■■■■■■■■⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜⬜■■■■⬜⬜⬜■■■■■■⬜⬜⬜⬜⬜⬜■■■■■■⬜■■■■■⬜⬜⬜⬜⬜■■■■⬜⬜⬜⬜■■⬜⬜⬜⬜⬜■■■⬜⬜⬜⬜⬜⬜⬜⬜■■■■■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■■■■■■⬜⬜■■⬜⬜⬜⬜■■⬜■■■■■■■■⬜⬜⬜⬜⬜⬜■■■■⬜⬜⬜■■⬜⬜■■■■■■⬜⬜⬜⬜■■■■■■
 * ■⬜⬜⬜⬜■■⬜⬜⬜⬜■■⬜⬜⬜■■⬜⬜■■■⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■⬜⬜⬜■■⬜⬜⬜■■⬜⬜■■■⬜⬜⬜⬜■■■⬜⬜⬜⬜⬜⬜⬜■■⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■■■⬜⬜⬜■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜■■⬜⬜■■⬜⬜■⬜⬜⬜⬜■■⬜⬜⬜■⬜⬜⬜⬜⬜
 * ■⬜⬜⬜⬜⬜■■⬜⬜■■⬜⬜⬜⬜⬜■⬜⬜■⬜■⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■■⬜■■⬜⬜⬜⬜⬜■⬜⬜■■■⬜⬜⬜⬜■■■⬜⬜⬜⬜⬜⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■⬜■⬜⬜⬜■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜
 * ■⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜■⬜⬜■⬜⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜■■■⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜■⬜■⬜⬜⬜■■■■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■■■⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜
 * ■⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜■⬜⬜■■⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜⬜⬜■■■⬜⬜⬜■■■■■■⬜⬜⬜⬜⬜⬜■■■■■■⬜■■■■■⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜■⬜⬜■⬜⬜■⬜■■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■■■■■■⬜⬜■⬜⬜■■⬜■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■■■⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜■■■■■■
 * ■⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜■⬜⬜⬜■⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜■⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜■⬜⬜■⬜⬜■⬜■■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜■⬜■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■■⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜
 * ■⬜⬜⬜⬜⬜■■⬜⬜■■⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜■■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■⬜⬜■■⬜⬜⬜⬜⬜■⬜⬜■⬜⬜■■■⬜⬜■■⬜⬜⬜⬜⬜⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜
 * ■⬜⬜⬜⬜■■⬜⬜⬜⬜■■⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜■■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜■■⬜⬜⬜■⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■■⬜⬜■■⬜⬜⬜■■⬜⬜■⬜⬜⬜■■⬜⬜■■⬜⬜⬜⬜⬜⬜⬜■■⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜■■⬜⬜■■⬜⬜■⬜⬜⬜⬜■■⬜⬜⬜■⬜⬜⬜⬜⬜
 * ■■■■■■⬜⬜⬜⬜⬜⬜■■■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■■■■⬜⬜⬜⬜⬜■■■■⬜⬜⬜■■■■■■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■⬜⬜⬜■■■■⬜⬜⬜⬜■⬜⬜⬜■⬜⬜⬜■■⬜⬜⬜⬜⬜⬜⬜⬜■■■■⬜⬜⬜■■■■■■⬜■⬜⬜⬜■■■■■■⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■■■■⬜⬜⬜■⬜⬜⬜■■■■■■⬜⬜⬜⬜■■■■■■
 * <p>
 * /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
 */
public class TrainControllerManager {
	private static Map<Long, TrainController> trackingTrainMap = new HashMap<>();


	public static TrainController getTrainController(EntityTrainBase train) {
		if (train.getFormation() == null) {
			return TrainController.NULL;
		}
		if (!trackingTrainMap.containsKey(train.getFormation().id)) {
			trackingTrainMap.put(train.getFormation().id, new TrainController());
		}
		return trackingTrainMap.get(train.getFormation().id);
	}

	//クライアントサイドでは一切処理なし
	public static void onTick() {
		if (trackingTrainMap.isEmpty()) {
			return;
		}
		List<Long> delList = new ArrayList<>();
		for (Entry<Long, TrainController> entry : trackingTrainMap.entrySet()) {
			long fid = entry.getKey();
			Formation formation = FormationManager.getInstance().getFormation(fid);
			EntityTrainBase controlCar = null;
			if (formation != null && formation.size() != 0) {
				for (FormationEntry formationEntry : formation.entries) {
					if (formationEntry == null) {
						continue;
					}
					controlCar = formationEntry.train;
					if (controlCar == null) {
						continue;
					}
					if (controlCar.isControlCar()) {
						entry.getValue().onUpdate(controlCar);
						if (controlCar.riddenByEntity instanceof EntityPlayerMP) {
							ATSAssistCore.NETWORK_WRAPPER.sendTo(new PacketTrainControllerToClient(entry.getValue(), controlCar.getEntityId()), (EntityPlayerMP) controlCar.riddenByEntity);
						}
						break;
					} else {
						controlCar = null;
					}
				}
			}
			if (controlCar == null) {
				delList.add(fid);
			}
		}
		delList.forEach(trackingTrainMap::remove);
	}


}
