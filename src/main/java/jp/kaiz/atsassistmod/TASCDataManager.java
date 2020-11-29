package jp.kaiz.atsassistmod;

public class TASCDataManager {
    private int trainType;
    private double stopPositionDistance;
    private double[] coordinates = new double[3];

    private boolean TASCBraking;
    private boolean autoStop;
    private boolean autoStopByTrainType;

    public int getTrainType() {
        return trainType;
    }

    public void setTrainType(int trainType) {
        this.trainType = trainType;
    }

    public double getStopPositionDistance() {
        return stopPositionDistance;
    }

    public void setStopPositionDistance(double stopPositionDistance) {
        this.stopPositionDistance = stopPositionDistance;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isTASCBraking() {
        return TASCBraking;
    }

    public void setTASCBraking(boolean TASCBraking) {
        this.TASCBraking = TASCBraking;
    }

    public boolean isAutoStop() {
        return autoStop;
    }

    public void setAutoStop(boolean autoStop) {
        this.autoStop = autoStop;
    }

    public boolean isAutoStopByTrainType() {
        return autoStopByTrainType;
    }

    public void setAutoStopByTrainType(boolean autoStopByTrainType) {
        this.autoStopByTrainType = autoStopByTrainType;
    }
}
