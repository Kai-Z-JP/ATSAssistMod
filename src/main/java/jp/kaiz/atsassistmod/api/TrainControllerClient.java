package jp.kaiz.atsassistmod.api;

import jp.kaiz.atsassistmod.controller.trainprotection.TrainProtectionType;

public class TrainControllerClient {
    private int atoI, tascI, atcI, atacsI, tpType;
    private boolean atoB, tascB;

    public TrainControllerClient() {
    }

    public TrainControllerClient(boolean atoB, boolean tascB, int tpType, int atoI, int tascI, int atcI, int atacsI) {
        this.set(atoB, tascB, tpType, atoI, tascI, atcI, atacsI);
    }


    public void set(boolean atoB, boolean tascB, int tpType, int atoI, int tascI, int atcI, int atacsI) {
        this.atoI = atoI;
        this.tascI = tascI;
        this.atcI = atcI;

        this.atacsI = atacsI;

        this.atoB = atoB;
        this.tascB = tascB;
        this.tpType = tpType;
    }

    public boolean isATO() {
        return atoB;
    }

    public boolean isTASC() {
        return tascB;
    }

    public boolean isATACS() {
        return this.tpType == TrainProtectionType.ATACS.id;
    }

    public void setTrainProtectionType(TrainProtectionType type) {
        this.tpType = type.id;
    }

    public TrainProtectionType getTrainProtectionType() {
        return TrainProtectionType.getType(this.tpType);
    }

    public int getATOSpeed() {
        return this.atoI;
    }

    public int getTASCDistance() {
        return this.tascI;
    }

    public int getATCSpeed() {
        return this.atcI;
    }

    public int getATACSSpeed() {
        return this.atacsI;
    }
}
