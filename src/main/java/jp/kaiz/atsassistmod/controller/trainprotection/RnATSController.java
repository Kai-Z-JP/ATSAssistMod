package jp.kaiz.atsassistmod.controller.trainprotection;

import jp.ngt.rtm.entity.train.EntityTrainBase;

public class RnATSController extends TrainProtection {
	private int limitSpeed;

	@Override
	public void onTick(EntityTrainBase train, double distance) throws Exception {
		super.onTick(train, distance);
		switch (train.getSignal()) {
			case 1:
				this.limitSpeed = 0;
				break;
			case 2:
				this.limitSpeed = 15;
				break;
			case 3:
				this.limitSpeed = 25;
				break;
			case 4:
				this.limitSpeed = 35;
				break;
			case 5:
				this.limitSpeed = 45;
				break;
			case 6:
				this.limitSpeed = 55;
				break;
			case 7:
				this.limitSpeed = 65;
				break;
			case 8:
				this.limitSpeed = 75;
				break;
			case 9:
				this.limitSpeed = 85;
				break;
			case 10:
				this.limitSpeed = 95;
				break;
			case 11:
				this.limitSpeed = 100;
				break;
			case 12:
				this.limitSpeed = 110;
				break;
			case 13:
				this.limitSpeed = 120;
				break;
			case 14:
				this.limitSpeed = 130;
				break;
			default:
				this.limitSpeed = 25;
				break;
		}
	}

	@Override
	public int getNotch(float speedH) {
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
		return TrainProtectionType.RnATS;
	}

    @Override
    public int getDisplaySpeed() {
        return this.limitSpeed;
    }
}
