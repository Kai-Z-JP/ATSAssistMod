package jp.kaiz.atsassistmod.controller.trainprotection;

import jp.ngt.rtm.entity.train.EntityTrainBase;

public class RATSController extends TrainProtection {
    private int limitSpeed;

    @Override
    public void onTick(EntityTrainBase train, double distance) throws Exception {
        super.onTick(train, distance);
        switch (train.getSignal()) {
            case 2:
                this.limitSpeed = 30;
                break;
            case 3:
                this.limitSpeed = 45;
                break;
            case 4:
                this.limitSpeed = 65;
                break;
            case 5:
                this.limitSpeed = 95;
                break;
            default:
                this.limitSpeed = Integer.MAX_VALUE;
                break;
        }
    }

    @Override
    public int getNotch(float speedH) {
        if (this.limitSpeed == Integer.MAX_VALUE) {
            return 1;
        }
        float overSpeed = speedH - this.limitSpeed;
        if (overSpeed > 5) {
            return -7;
        } else if (overSpeed > 0) {
            return -4;
        } else {
            return 1;
        }
    }

    @Override
    public TrainProtectionType getType() {
        return TrainProtectionType.RATS;
    }

    @Override
    public int getDisplaySpeed() {
        return this.limitSpeed;
    }
}
