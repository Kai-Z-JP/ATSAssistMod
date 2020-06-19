package jp.kaiz.atsassistmod.controller;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.network.PacketTrainControllerToClient;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import jp.ngt.rtm.entity.train.util.Formation;
import jp.ngt.rtm.entity.train.util.FormationEntry;
import jp.ngt.rtm.entity.train.util.FormationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TrainControllerManager {
	private static final Map<Long, TrainController> trackingTrainMap = new HashMap<>();


	public static TrainController getTrainController(EntityTrainBase train) {
		if (train == null || train.getFormation() == null) {
			return TrainController.NULL;
		}
		if (!trackingTrainMap.containsKey(train.getFormation().id)) {
			trackingTrainMap.put(train.getFormation().id, new TrainController(train));
		}
		return trackingTrainMap.get(train.getFormation().id);
	}

	//クライアントサイドでは一切処理なし
	public static void onTick() {
		if (trackingTrainMap.isEmpty()) {
			return;
		}
		List<Long> delList = new ArrayList<>();
		TrainController[] tcs = new TrainController[trackingTrainMap.size()];
		TCThreadManager tsm = new TCThreadManager();
		int i = 0;
		for (Entry<Long, TrainController> entry : trackingTrainMap.entrySet()) {
			long fid = entry.getKey();
			Formation formation = FormationManager.getInstance().getFormation(fid);
			EntityTrainBase controlCar = null;
			if (formation != null && formation.size() != 0) {
				for (FormationEntry formationEntry : formation.entries) {
					if (formationEntry == null || formationEntry.train == null) {
						continue;
					}
					if (formationEntry.train.isControlCar()) {
						controlCar = formationEntry.train;
						tcs[i] = entry.getValue();
						if (controlCar.getEntityId() == tcs[i].getSavedEntityID()) {
							tcs[i].init(tsm);

							Thread thread = new Thread(tcs[i]);
							thread.setName("Server thread");
							thread.start();
//						    entry.getValue().onUpdate(controlCar);
							ATSAssistCore.NETWORK_WRAPPER.sendToAll(new PacketTrainControllerToClient(tcs[i], fid));
							i++;
							break;
						} else {
							i++;
						}
					}
				}
				if (controlCar == null) {
					delList.add(fid);
				}
			}
		}
		for (Long aLong : delList) {
			ATSAssistCore.NETWORK_WRAPPER.sendToAll(new PacketTrainControllerToClient(aLong));
			trackingTrainMap.remove(aLong);
		}
//		tsm.waitSync();
	}
}
