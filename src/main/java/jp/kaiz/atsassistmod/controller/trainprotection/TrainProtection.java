package jp.kaiz.atsassistmod.controller.trainprotection;

import jp.ngt.rtm.entity.train.EntityTrainBase;

public class TrainProtection {
    protected EntityTrainBase train;

    public void onTick(EntityTrainBase train, double distance) throws Exception {
        this.train = train;
    }

    public int getNotch(float speedH) {
        return 1;

    }

    public TrainProtectionType getType() {
        return TrainProtectionType.NONE;
    }
}
