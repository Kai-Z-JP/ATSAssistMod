package jp.kaiz.atsassistmod.controller.trainprotection;

public class StationPremisesController extends TrainProtection {
    public int getNotch(float speedH) {
        if (speedH > 25) {
            return -8;
        } else {
            return 1;
        }
    }

    public TrainProtectionType getType() {
        return TrainProtectionType.STATION_PREMISES;
    }
}
