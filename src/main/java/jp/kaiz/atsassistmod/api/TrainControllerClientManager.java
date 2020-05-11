package jp.kaiz.atsassistmod.api;

import jp.ngt.rtm.entity.train.EntityTrainBase;

import java.util.HashMap;
import java.util.Map;

public class TrainControllerClientManager {
    private static final Map<Long, TrainControllerClient> tcClientMap = new HashMap<>();

    public static void setTCC(long formationID, boolean atoB, boolean tascB, int tpType, int atoI, int tascI, int atcI, int atacsI) {
        if (tcClientMap.containsKey(formationID)) {
            tcClientMap.get(formationID).set(atoB, tascB, tpType, atoI, tascI, atcI, atacsI);
        } else {
            TrainControllerClient tcc = new TrainControllerClient(atoB, tascB, tpType, atoI, tascI, atcI, atacsI);
            tcClientMap.put(formationID, tcc);
        }
    }

    public static TrainControllerClient getTCC(EntityTrainBase train) {
        if (train == null || train.getFormation() == null) {
            return null;
        }
        return tcClientMap.getOrDefault(train.getFormation().id, null);
    }

    public static void removeTCC(long formationID) {
        tcClientMap.remove(formationID);
    }
}
