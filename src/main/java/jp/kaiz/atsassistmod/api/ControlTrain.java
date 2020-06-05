package jp.kaiz.atsassistmod.api;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.TASCDataManager;
import jp.kaiz.atsassistmod.network.PacketSetNotch;
import jp.kaiz.atsassistmod.network.PacketSetNotchController;
import jp.ngt.rtm.entity.train.EntityTrainBase;

import java.util.HashMap;
import java.util.Map;

//外部読み込み　変更禁止
public class ControlTrain {
    private static Map<Integer, TASCDataManager> trainData = new HashMap<>();

    @Deprecated
    public static void setNotch(byte notch) {
        if (notch <= 5 && notch >= -8) {
            ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketSetNotch(notch));
        }
    }

    @Deprecated
    public static void setNotch(int notch) {
        if (notch <= 5 && notch >= -8) {
            ControlTrain.setNotch((byte) notch);
        }
    }

    public static void setControllerNotch(byte notch) {
        if (notch <= 5 && notch >= -8) {
            ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketSetNotchController(notch));
        }
    }

    public static void setControllerNotch(int notch) {
        if (notch <= 5 && notch >= -8) {
            ControlTrain.setControllerNotch((byte) notch);
        }
    }

    @Deprecated
    public static void setNotch(EntityTrainBase entity, byte notch) {
        if (notch <= 5 && notch >= -8) {
            ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketSetNotch(entity, notch));
        }
    }

    @Deprecated
    public static void setNotch(EntityTrainBase entity, int notch) {
        if (notch <= 5 && notch >= -8) {
            ControlTrain.setNotch(entity, (byte) notch);
        }
    }

    @Deprecated
    public static boolean isTASCBreking(EntityTrainBase entity) {
        int entityID = entity.getEntityId();
        if (trainData.containsKey(entityID)) {
            TASCDataManager data = trainData.get(entityID);
            return data.isTASCBraking();
        }
        return false;
    }

    @Deprecated
    public static boolean TASC(EntityTrainBase entity, int tT) {
        if (entity == null) {
            return false;
        }
        if (!entity.isControlCar()) {
            return false;
        }
        if (entity.getFormation() == null) {
            return false;
        }
        if (entity.getFormation().size() <= 0) {
            return false;
        }

        int entityID = entity.getEntityId();
        if (!trainData.containsKey(entityID)) {
            trainData.put(entityID, new TASCDataManager());
        }
        TASCDataManager data = trainData.get(entityID);

        data.setTrainType(tT);

        int signal = entity.getSignal();
        int trainType = trainData.get(entityID).getTrainType();

        if (trainType >= 11 && trainType <= 19) {
            switch (signal) {
                case 101:
                    if (trainType <= 11) {
                        data.setAutoStopByTrainType(true);
                    }
                    break;
                case 102:
                    if (trainType <= 12) {
                        data.setAutoStopByTrainType(true);
                    }
                    break;
                case 103:
                    if (trainType <= 13) {
                        data.setAutoStopByTrainType(true);
                    }
                    break;
                case 104:
                    if (trainType <= 14) {
                        data.setAutoStopByTrainType(true);
                    }
                    break;
                case 105:
                    if (trainType <= 15) {
                        data.setAutoStopByTrainType(true);
                    }
                    break;
                case 106:
                    if (trainType <= 16) {
                        data.setAutoStopByTrainType(true);
                    }
                    break;
                case 107:
                    if (trainType <= 17) {
                        data.setAutoStopByTrainType(true);
                    }
                    break;
                case 108:
                    if (trainType <= 18) {
                        data.setAutoStopByTrainType(true);
                    }
                    break;
                case 109:
                    data.setAutoStopByTrainType(true);
                    break;
            }
        }

        //TASC電源
        switch (signal) {
            case 126://TASC オフ
                data.setAutoStop(false);
                data.setTASCBraking(false);
                data.setStopPositionDistance(10000);
                data.setCoordinates(null);
                data.setAutoStopByTrainType(false);
                break;
            case 127://TASC オン
                data.setAutoStop(true);
                data.setTASCBraking(false);
                data.setStopPositionDistance(10000);
                //今回の座標
                double[] XYZ = {entity.posX, entity.posY, entity.posZ};
                //今回の座標を記録
                data.setCoordinates(XYZ);
                break;
            default:
                break;
        }

        if (!data.isAutoStopByTrainType()) {
            trainData.put(entityID, data);
            return false;
        }
        if (!data.isAutoStop()) {
            trainData.put(entityID, data);
            return false;
        }

        //秒速m/s
        float speed = entity.getSpeed() * 20;

        switch (signal) {
            case 116://-3m
                //オーバーした場合非常停止
                ControlTrain.setNotch(-8);
                data.setAutoStop(false);
                data.setStopPositionDistance(-3);
                data.setCoordinates(null);
                return true;
            case 117://-1m~1m
                //スピードが0.5m/s(1.2km/h)以下の時にTASCオフ
                if (speed <= 0) {
                    data.setAutoStop(false);
                    return false;
                }
                break;
            case 118://10m
                data.setStopPositionDistance(10);
                break;
            case 119://50m
                data.setStopPositionDistance(50);
                break;
            case 120://100m
                data.setStopPositionDistance(100);
                break;
            case 121://200m
                data.setStopPositionDistance(200);
                break;
            case 122://300m
                data.setStopPositionDistance(300);
                break;
            case 123://400m
                data.setStopPositionDistance(400);
                break;
            case 124://500m
                data.setStopPositionDistance(500);
                break;
            case 125://600m
                data.setStopPositionDistance(600);
                break;
            default:
                break;
        }

        //半分の速度で通過するときに必要な秒数　＝　減速に必要な秒数
        double deceleration_second = data.getStopPositionDistance() / (speed / 2);

        //必要減速度
        double necessary_Deceleration = speed / deceleration_second;

        //必要減速度がブレーキ五段以上の場合にTASCブレーキング開始

        if (data.getStopPositionDistance() <= 0) {
            ControlTrain.setNotch(-5);
        } else if (necessary_Deceleration > 4) {
            ControlTrain.setNotch(-8);
            data.setTASCBraking(true);
        } else if (necessary_Deceleration > 1.4) {
            ControlTrain.setNotch(-8);
            data.setTASCBraking(true);
        } else if (necessary_Deceleration > 1.2) {
            ControlTrain.setNotch(-7);
            data.setTASCBraking(true);
        } else if (necessary_Deceleration > 1) {
            ControlTrain.setNotch(-6);
            data.setTASCBraking(true);
        } else if (necessary_Deceleration > 0.8 && data.isTASCBraking()) {
            ControlTrain.setNotch(-5);
            data.setTASCBraking(true);
        } else if (necessary_Deceleration > 0.6 && data.isTASCBraking()) {
            ControlTrain.setNotch(-4);
        } else if (necessary_Deceleration > 0.4 && data.isTASCBraking()) {
            ControlTrain.setNotch(-3);
        } else if (necessary_Deceleration > 0.2 && data.isTASCBraking()) {
            ControlTrain.setNotch(-2);
        } else if (necessary_Deceleration > 0.08 && data.isTASCBraking()) {
            ControlTrain.setNotch(-1);
        } else if (necessary_Deceleration > 0 && data.isTASCBraking()) {
            ControlTrain.setNotch(0);
        } else if (necessary_Deceleration < 0 && data.isTASCBraking()) {
            ControlTrain.setNotch(-6);
        } else if (data.isTASCBraking()) {
            ControlTrain.setNotch(0);
        }

        //記録されている座標がない場合終了
        if (data.getCoordinates() == null) {
            trainData.put(entityID, data);
            return true;
        }

        //前回の座標を取得
        double[] oldXYZ = data.getCoordinates();
        //今回の座標
        double[] XYZ = {entity.posX, entity.posY, entity.posZ};

        //移動距離を計算
        double movingDistance = Math.sqrt(Math.pow((oldXYZ[0] - XYZ[0]), 2) /*+ Math.pow((oldXYZ[1]-XYZ[1]),2)*/ + Math.pow((oldXYZ[2] - XYZ[2]), 2));
        //今回の座標を記録
        data.setCoordinates(XYZ);

        //距離を引き算
        data.setStopPositionDistance(data.getStopPositionDistance() - movingDistance);

        trainData.put(entityID, data);
        return true;
    }

    public static void logger(Object object) {
        System.out.println(object);
    }
}
