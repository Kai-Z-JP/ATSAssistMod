package jp.kaiz.atsassistmod.controller;

//結構違うから継承しなくていい
public class TASCController {
	private double stopDistance;
	private boolean enable = false;
	private boolean breaking = false;

	private static final double DISABLE_DISTANCE = -1.0D;

	public TASCController() {
		this.stopDistance = DISABLE_DISTANCE;
	}

	public void changeTargetDistance(double movedDistance) {
		this.stopDistance = Math.max((this.stopDistance - movedDistance), DISABLE_DISTANCE);

	}

	public void setStopDistance(double stopDistance) {
		this.stopDistance = stopDistance;
	}

	public double getStopDistance() {
		return stopDistance;
	}

	public void enable(double targetDistance) {
		this.stopDistance = targetDistance;
		this.enable = true;
	}

	public void disable() {
		this.breaking = false;
		this.stopDistance = DISABLE_DISTANCE;
		this.enable = false;
	}

	public boolean isEnable() {
		return this.enable;
	}

	public boolean isBreaking() {
		return this.breaking;
	}

	public boolean isStopPosition() {
		return this.stopDistance < 1.0D;
	}

	public int getNeedNotch(float nowSpeedH) {
		double deceleration = this.getReqDeceleration(nowSpeedH);


		if (this.isStopPosition()) {
			this.breaking = true;
			return deceleration <= 0 ? -7 : 5;
		}

		if (deceleration > 4) {
			this.breaking = true;
			return -8;
		} else if (deceleration > 1.4) {
			this.breaking = true;
			return -8;
		} else if (deceleration > 1.2) {
			this.breaking = true;
			return -7;
		} else if (deceleration > 1) {
			this.breaking = true;
			return -6;
		} else if (deceleration > 0.8 && this.breaking) {
			return -5;
		} else if (deceleration > 0.6 && this.breaking) {
			return -4;
//		} else if (deceleration > 0.4 && this.breaking) {
//			return -3;
//		} else if (deceleration > 0.2 && this.breaking) {
//			return -2;
//		} else if (deceleration > 0.08 && this.breaking) {
//			return -1;
		} else if (deceleration > 0 && this.breaking) {
			this.breaking = false;
			return 0;
		} else if (this.breaking) {
			return -6;
		} else {
			return 1;
		}
	}

	private double getReqDeceleration(float nowSpeedH) {
		//秒速 m/s
//		float speedS = nowSpeedH / 3.6f;
//		double decelerationSecond = this.targetDistance / (speedS / 2f);
//		return speedS / decelerationSecond;
		return Math.pow(nowSpeedH, 2) / (this.stopDistance * 7.2d) / 3.6d;
	}

	public void setBraking(boolean b) {
		this.breaking = b;
	}
}