package jp.kaiz.atsassistmod.controller;

import jp.kaiz.atsassistmod.controller.trainprotection.TrainProtection;
import jp.kaiz.atsassistmod.controller.trainprotection.TrainProtectionType;
import jp.ngt.rtm.entity.train.EntityTrainBase;

import java.util.ArrayList;
import java.util.List;

public class TrainController {
    private final List<SpeedOrder> speedOrderList = new ArrayList<>();
    private final List<Integer> speedLimit = new ArrayList<>();
    private int maxSpeed = 0;

    private boolean ATO = false;
    private boolean acceleratorControlling = false;
    public final TASCController tascController = new TASCController();
    private boolean brakingControlling = false;

    private TrainProtection tp;

    private EntityTrainBase train;

    private double[] coordinates;

    private final int savedEntityID;

    private byte controllerNotchA = -1, controllerNotchB = 1;

    private boolean controllerControl = false;

    private boolean emergencyBrake = false;

    private boolean manualDrive = false;

    //null処理めんどいからこれとインスタンス比較で
    public static final TrainController NULL = new TrainController();

    public TrainController() {
        this.savedEntityID = -1;
        this.setTrainProtection(TrainProtectionType.NONE);
    }

    public TrainController(EntityTrainBase train) {
        this.savedEntityID = train.getEntityId();
        this.train = train;
        this.setTrainProtection(TrainProtectionType.NONE);
    }

    public void setEB() {
        this.emergencyBrake = true;
        if (this.train != null) {
            this.train.setNotch(-8);
        }
    }

    public void setManualDrive(boolean manualDrive) {
        this.manualDrive = manualDrive;
    }

    public boolean isManualDrive() {
        return this.manualDrive;
    }

    public void setControllerNotch(byte notch) {
        this.controllerControl = true;
        if (notch > 0) {
            this.controllerNotchA = notch;
            this.controllerNotchB = 1;
        } else if (notch < 0) {
            this.controllerNotchA = 0;
            this.controllerNotchB = notch;
        } else {
            this.controllerNotchA = 0;
            this.controllerNotchB = 1;
        }
    }

    public int getSavedEntityID() {
        return savedEntityID;
    }

    public void addSpeedOrder(SpeedOrder speedOrder) {
        this.speedOrderList.add(speedOrder);
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void removeSpeedLimit() {
        if (!this.speedLimit.isEmpty()) {
            this.speedLimit.remove(0);
        }
    }

    public void removeAllSpeedLimit() {
        if (!this.speedLimit.isEmpty()) {
            this.speedLimit.clear();
        }
    }

    //速度制限
    public int getSpeedLimit() {
        //未設定の時はInteger.MAX_VALUEを返す
        return this.speedLimit.stream().mapToInt(v -> v).min().orElse(Integer.MAX_VALUE);

    }

    //ATOの目標速度 制限とは別
    public int getATOSpeedLimit() {
        int minLimit = this.speedLimit.stream().mapToInt(v -> v).min().orElse(this.maxSpeed);
        return Math.min(this.getTrainProtectionSpeedLimit(), Math.min(minLimit, this.maxSpeed));
    }

    //ATACS速度
    public int getTrainProtectionSpeedLimit() {
        //未設定の時はInteger.MAX_VALUEを返す
        return this.tp.getDisplaySpeed();
    }

    public void enableATO(int speed) {
        this.ATO = true;
        this.maxSpeed = speed;
    }

    public void disableATO() {
        this.ATO = false;
    }

    public boolean isATO() {
        return ATO;
    }

    public void setTrainProtection(TrainProtectionType type) {
        try {
            this.tp = type.aClass.newInstance();
            if (this.train != null) {
                this.train.getResourceState().getDataMap().setString("ATSAssist_CurrentTP", type.name, 1);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public TrainProtectionType getTrainProtectionType() {
        return this.tp.getType();
    }

    //Tick毎の処理
    public void onUpdate() throws Exception {
        if (train == null) {
            return;
        }

        //移動距離
        double movedDistance = this.getMovedDistance();

        //時速 km/h
        float speedH = train.getSpeed() * 72f;

        //ノッチ制御を一回のみ行うように
        List<Integer> brakeNotch = new ArrayList<>();
        List<Integer> acceleratorNotch = new ArrayList<>();

        List<SpeedOrder> removeList = new ArrayList<>();
        //制限速度
        this.speedOrderList.forEach(speedOrder -> {
            if (speedOrder.isEnable()) {
                //制限速度区間に入った時
                this.speedLimit.add(speedOrder.getTargetSpeed());
                removeList.add(speedOrder);
            } else {
                speedOrder.moveDistance(movedDistance);
                //ATO有効時予告に基づきブレーキノッチ追加
                if (speedOrder.isAutoBrake() || (this.ATO && !this.isManualDrive())) {
                    brakeNotch.add(speedOrder.getNeedNotch(speedH));
                }
            }
        });

        this.speedOrderList.removeAll(removeList);

        //スピードオーバー時
        if (speedH > this.getSpeedLimit()) {
            float overSpeed = speedH - this.getSpeedLimit();
            //5km/h以上オーバーの時は-7段ブレーキを
            if (overSpeed < 5f) {
                brakeNotch.add(-4);
            } else {
                brakeNotch.add(-7);
            }
        } else {
            //ATO有効時
            if (this.ATO && !this.isManualDrive()) {
                //10km/h下回っていたら加速 制限時速より2km/h下まで加速で解除
                if (!this.acceleratorControlling) {
                    if ((this.getATOSpeedLimit() - speedH) > 10) {
                        acceleratorNotch.add(5);
                    } else if (speedH == 0) {
                        acceleratorNotch.add(5);
                    }
                } else if (this.getATOSpeedLimit() - speedH < 2) {
                    acceleratorNotch.add(0);
                }
            }
        }


        //TASC
        this.tascController.changeTargetDistance(movedDistance);
        if (this.tascController.isEnable()) {
            int needNotch = this.tascController.getNeedNotch(speedH);
            if (this.tascController.isBreaking()) {
                this.disableATO();
                if (!this.isManualDrive()) {
                    brakeNotch.add(needNotch);
                }
            }
        }

        //保安装置全処理
        this.tp.onTick(train, movedDistance);
        int notch = this.tp.getNotch(speedH);
        brakeNotch.add(notch);

        if (this.emergencyBrake) {
            int notchLevel = this.train.getNotch();
            if (notchLevel != -8) {
                this.emergencyBrake = false;
                this.brakingControlling = false;
            } else {
                return;
            }
        }

        //ノッチ制御最終処理

        //最大ブレーキ(値は小さい)のを計算
        int minBrakeNotch = brakeNotch.stream().mapToInt(v -> v).min().orElse(1);

        //ControllerNotch マジもんのコントローラー用
        if (this.train.getFirstPassenger() == null) {
            this.controllerNotchA = -1;
            this.controllerNotchB = 1;
            if (this.controllerControl) {
                this.controllerControl = false;
                this.brakingControlling = false;
//                this.acceleratorControlling = false;
            }
        } else {
            if (this.controllerControl) {
                minBrakeNotch = Math.min(minBrakeNotch, this.controllerNotchB);
            }
        }


        //0以上はブレーキ指定なし アクセル許可
        if (minBrakeNotch > 0) {
            //最大アクセルを計算
            int maxAcceleratorNotch = acceleratorNotch.stream().mapToInt(v -> v).max().orElse(-1);

            if (this.train.getFirstPassenger() != null) {
                if (this.controllerControl) {
                    maxAcceleratorNotch = Math.max(maxAcceleratorNotch, this.controllerNotchA);
                }
            }

            if (maxAcceleratorNotch < 0) {
                if (this.brakingControlling) {
                    this.brakingControlling = false;
                    this.train.setNotch(0);
                }
            } else if (maxAcceleratorNotch == 0) {
                this.brakingControlling = false;
                //明示的Notch0
                if (!this.controllerControl || this.controllerNotchA != 0) {
                    this.acceleratorControlling = false;
                }

                this.train.setNotch(0);
            } else {
                this.brakingControlling = false;
                this.acceleratorControlling = true;
                this.train.setNotch(maxAcceleratorNotch);
            }
        } else if (minBrakeNotch == 0) {
            if (this.tascController.isEnable()) {
                if (this.tascController.isStopPosition()) {
//                    train.setNotch(-6);
                    this.brakingControlling = false;
                    this.ATO = false;
                    if (speedH <= 0F) {
                        this.tascController.disable();
                        if (!this.isManualDrive()) {
                            return;
                        }
                    }
//                } else if (speedH < 1) {
//                    train.setNotch(-5);
//                    this.brakingControlling = false;
//                    this.ATO = false;
//                    this.tascController.setBraking(false);
//                    if (speedH <= 0F) {
//                        this.tascController.disable();
//                    }
//                    return;
                }
            }

            this.acceleratorControlling = false;
            //明示的Notch0
            this.brakingControlling = false;
            this.train.setNotch(0);
        } else {
            this.acceleratorControlling = false;
            if (this.tascController.isEnable()) {
                if (this.tascController.isStopPosition()) {
//                    train.setNotch(-6);
                    this.brakingControlling = false;
                    this.ATO = false;
                    if (speedH <= 0F) {
                        this.tascController.disable();
                        if (!this.isManualDrive()) {
                            return;
                        }
                    }
//                } else if (speedH < 1) {
//                    train.setNotch(-5);
//                    this.brakingControlling = false;
//                    this.ATO = false;
//                    this.tascController.setBraking(false);
//                    if (speedH <= 0F) {
//                        this.tascController.disable();
//                    }
//                    return;
                }
            }
            this.brakingControlling = true;
            this.train.setNotch(minBrakeNotch);
        }

        if (this.tascController.isEnable()) {
            if (this.tascController.isStopPosition()) {
                this.ATO = false;
                if (this.isManualDrive()) {
                    this.tascController.disable();
                }
            }
        }
    }

    private double getMovedDistance() {
        if (this.coordinates == null) {
            this.coordinates = new double[]{train.posX, train.posY, train.posZ};
            return 0d;
        } else {
            //前回の座標を取得
            double[] oldXYZ = this.coordinates;
            //今回の座標
            double[] XYZ = {train.posX, train.posY, train.posZ};
            //今回の座標を記録
            this.coordinates = XYZ;

            //移動距離を計算
            //勾配計算ナシ
            return Math.sqrt(Math.pow((oldXYZ[0] - XYZ[0]), 2) /*+ Math.pow((oldXYZ[1]-XYZ[1]),2)*/ + Math.pow((oldXYZ[2] - XYZ[2]), 2));
        }
    }
}
