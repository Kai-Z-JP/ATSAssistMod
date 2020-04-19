package jp.kaiz.atsassistmod.api;

public class TrainControllerClient {
    private int atoI, tascI, atcI, atacsI;
    private boolean atoB, tascB, atacsB;

    public TrainControllerClient(boolean atoB, boolean tascB, boolean atacsB, int atoI, int tascI, int atcI, int atacsI) {
        this.set(atoB, tascB, atacsB, atoI, tascI, atcI, atacsI);
    }


    public void set(boolean atoB, boolean tascB, boolean atacsB, int atoI, int tascI, int atcI, int atacsI) {
        this.atoI = atoI;
        this.tascI = tascI;
        this.atcI = atcI;

        this.atacsI = atacsI;

        this.atoB = atoB;
        this.tascB = tascB;
        this.atacsB = atacsB;
    }

    public boolean isATO() {
        return atoB;
    }

    public boolean isTASC() {
        return tascB;
    }

    public boolean isATACS() {
        return atacsB;
    }

    public int getATOSpeed() {
        return atoI;
    }

    public int getTASCDistance() {
        return tascI;
    }

    public int getATCSpeed() {
        return atcI;
    }

    public int getATACSSpeed() {
        return atacsI;
    }
}
