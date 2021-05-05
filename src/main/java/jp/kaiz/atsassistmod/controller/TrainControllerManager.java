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
        trackingTrainMap.forEach((key, tcs) -> {
            long fid = key;
            Formation formation = FormationManager.getInstance().getFormation(fid);
            EntityTrainBase controlCar = null;
            if (formation != null && formation.size() != 0) {
                for (FormationEntry formationEntry : formation.entries) {
                    if (formationEntry == null || formationEntry.train == null) {
                        continue;
                    }
                    if (formationEntry.train.isControlCar()) {
                        controlCar = formationEntry.train;
                        if (controlCar.getEntityId() == tcs.getSavedEntityID()) {
                            try {
                                tcs.onUpdate();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            ATSAssistCore.NETWORK_WRAPPER.sendToAll(new PacketTrainControllerToClient(tcs, fid));
                            break;
                        } else {
                            delList.add(fid);
                        }
                    }
                }
                if (controlCar == null) {
                    delList.add(fid);
                }
            }
        });
        delList.forEach(aLong -> {
            ATSAssistCore.NETWORK_WRAPPER.sendToAll(new PacketTrainControllerToClient(aLong));
            trackingTrainMap.remove(aLong);
        });
    }
}
