package jp.kaiz.atsassistmod.controller.trainprotection;

public class ATSPsController extends TrainProtection {

    @Override
    public TrainProtectionType getType() {
        return TrainProtectionType.ATSPs;
    }
}
