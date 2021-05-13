package jp.kaiz.atsassistmod.api;

import jp.ngt.rtm.entity.train.EntityTrainBase;

import java.util.HashMap;
import java.util.Map;

public class TrainControllerClientManager {
    private static final Map<Long, TrainControllerClient> tcClientMap = new HashMap<>();

    public static void setTCC(long formationID, boolean atoB, boolean tascB, int tpType, int atoI, int tascI, int atcI, int tpLimit, boolean manualDrive) {
        if (tcClientMap.containsKey(formationID)) {
            tcClientMap.get(formationID).set(atoB, tascB, tpType, atoI, tascI, atcI, tpLimit, manualDrive);
        } else {
            TrainControllerClient tcc = new TrainControllerClient(atoB, tascB, tpType, atoI, tascI, atcI, tpLimit, manualDrive);
            tcClientMap.put(formationID, tcc);
        }
    }

    public static TrainControllerClient getTCC(EntityTrainBase train) {
        if (train == null || train.getFormation() == null) {
            return null;
        }
        return tcClientMap.getOrDefault(train.getFormation().id, null);
    }

    public static TrainControllerClient createTCC(EntityTrainBase train) {
        if (train == null || train.getFormation() == null) {
            return null;
        }
        TrainControllerClient tcc = new TrainControllerClient();
        tcClientMap.put(train.getFormation().id, tcc);
        return tcc;
    }

    public static void removeTCC(long formationID) {
        tcClientMap.remove(formationID);
    }
}
