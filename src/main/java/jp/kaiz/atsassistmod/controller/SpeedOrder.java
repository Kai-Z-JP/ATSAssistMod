package jp.kaiz.atsassistmod.controller;

public class SpeedOrder {
    private final int targetSpeedH;
    private double targetDistance;
    private boolean autoBrake;
    private boolean enable = false;
    private boolean breaking = false;

    public SpeedOrder(int targetSpeed, double targetDistance) {
        this.targetSpeedH = targetSpeed;
        this.targetDistance = targetDistance;
    }

    public SpeedOrder(int targetSpeed, double targetDistance, boolean autoBrake) {
        this.targetSpeedH = targetSpeed;
        this.targetDistance = targetDistance;
        this.autoBrake = autoBrake;
    }

    public int getTargetSpeed() {
        return this.targetSpeedH;
    }

    public void moveDistance(double movedDistance) {
        if (!enable) {
            if (targetDistance <= 0) {
                targetDistance = 0;
                this.enable = true;
            } else {
                this.targetDistance = this.targetDistance - movedDistance;
            }
        }
    }

    public boolean isEnable() {
        return enable;
    }

    public boolean isAutoBrake() {
        return autoBrake;
    }

    public int getNeedNotch(float nowSpeedH) {
        double deceleration = this.getReqDeceleration(nowSpeedH);

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
        } else if (deceleration > 0.8) {
            this.breaking = true;
            return -5;
        } else if (deceleration > 0.6 && this.breaking) {
            return -4;
        } else if (this.breaking) {
            return 0;
        } else {
            return 1;
        }
    }

    private double getReqDeceleration(float nowSpeedH) {
        if (this.targetSpeedH - 2 > nowSpeedH) {
            return 0;
        }
        //秒速 m/s
        float downSpeed1 = (nowSpeedH - (this.targetSpeedH - 2)) / 3.6f;
        float downSpeed2 = (nowSpeedH + (this.targetSpeedH - 2)) / 3.6f;
        double decelerationSecond = (this.targetDistance - 10) / (downSpeed2 / 2f);
        return downSpeed1 / decelerationSecond;
    }
}