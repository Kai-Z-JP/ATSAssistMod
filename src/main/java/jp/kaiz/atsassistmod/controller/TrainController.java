package jp.kaiz.atsassistmod.controller;

import jp.kaiz.atsassistmod.controller.trainprotection.ATACSController;
import jp.kaiz.atsassistmod.controller.trainprotection.TrainProtection;
import jp.ngt.rtm.entity.train.EntityTrainBase;

import java.util.ArrayList;
import java.util.List;

public class TrainController implements Runnable {
	private final List<SpeedOrder> speedOrderList = new ArrayList<>();
	private final List<Integer> speedLimit = new ArrayList<>();
	private int maxSpeed = 0;

	private boolean ATO = false;
	private boolean acceleratorControlling = false;
	public final TASCController tascController = new TASCController();
	private boolean brakingControlling = false;

	private ATACSController atacsController = new ATACSController();
	private boolean atacsBreaking;
	private boolean ATACS;

	private TrainProtection TPType = TrainProtection.NONE;

	private EntityTrainBase train;
	private TCThreadManager tsm;

	private double[] coordinates;

	//null処理めんどいからこれとインスタンス比較で
	public static final TrainController NULL = new TrainController();

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

	//速度制限
	public int getSpeedLimit() {
		//未設定の時はInteger.MAX_VALUEを返す
		return this.speedLimit.stream().mapToInt(v -> v).min().orElse(Integer.MAX_VALUE);

	}

	//ATOの目標速度 制限とは別
	public int getATOSpeedLimit() {
		int minLimit = this.speedLimit.stream().mapToInt(v -> v).min().orElse(this.maxSpeed);
		return Math.min(this.getATACSSpeedLimit(), Math.min(minLimit, this.maxSpeed));
	}

	//ATACS速度
	public int getATACSSpeedLimit() {
		//未設定の時はInteger.MAX_VALUEを返す
		return this.atacsController.getDisplaySpeed();
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

	public boolean isATACS() {
		return this.TPType == TrainProtection.ATACS;
	}

	public void enableATACS() {
		this.TPType = TrainProtection.ATACS;
	}

	public void disableATACS() {
		this.TPType = TrainProtection.NONE;
	}

	public void init(EntityTrainBase train, TCThreadManager tsm) {
		this.train = train;
		this.tsm = tsm;
		this.tsm.addSync();
	}

	public void run() {
		this.onUpdate();
	}


	//Tick毎の処理
	public void onUpdate() {
		//移動距離
		double movedDistance = this.getMovedDistance();

		//時速 km/h
		float speedH = train.getSpeed() * 72f;

		//ノッチ制御を一回のみ行うように
		List<Integer> brakeNotch = new ArrayList<>();
		List<Integer> acceleratorNotch = new ArrayList<>();

		List<SpeedOrder> removeList = new ArrayList<>();
		//制限速度
		for (SpeedOrder speedOrder : this.speedOrderList) {
			if (speedOrder.isEnable()) {
				//制限速度区間に入った時
				this.speedLimit.add(speedOrder.getTargetSpeed());
				removeList.add(speedOrder);
			} else {
				speedOrder.moveDistance(movedDistance);
				//ATO有効時予告に基づきブレーキノッチ追加
				if (this.ATO || this.TPType == TrainProtection.ATACS) {
					brakeNotch.add(speedOrder.getNeedNotch(speedH));
				}
			}
		}

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
			if (this.ATO) {
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
				brakeNotch.add(needNotch);
			}
		}


		//ATACS
		switch (TPType) {
			case NONE:
			case ATACS:
				this.atacsController.onTick(train, movedDistance);
				if (this.atacsBreaking && speedH < this.atacsController.getDisplaySpeed()) {
					this.atacsBreaking = false;
				} else if (speedH > this.atacsController.getEmergencySpeed()) {
					brakeNotch.add(-8);
					this.atacsBreaking = true;
				} else if (speedH > this.atacsController.getPatternSpeed()) {
					brakeNotch.add(-7);
					this.atacsBreaking = true;
				} else if (this.atacsController.getDisplaySpeed() == 0) {
					brakeNotch.add(-5);
					this.atacsBreaking = true;
				}
				break;
		}


		//ノッチ制御最終処理

		//最大ブレーキ(値は小さい)のを計算
		int minBrakeNotch = brakeNotch.stream().mapToInt(v -> v).min().orElse(1);


		//0以上はブレーキ指定なし アクセル許可
		if (minBrakeNotch > 0) {
			//最大アクセルを計算
			int maxAcceleratorNotch = acceleratorNotch.stream().mapToInt(v -> v).max().orElse(-1);

			if (maxAcceleratorNotch < 0) {
				if (this.brakingControlling) {
					this.brakingControlling = false;
					train.setNotch(0);
				}
			} else if (maxAcceleratorNotch == 0) {
				this.brakingControlling = false;
				//明示的Notch0
				this.acceleratorControlling = false;

				train.setNotch(0);
			} else {
				this.brakingControlling = false;
				this.acceleratorControlling = true;
				train.setNotch(maxAcceleratorNotch);
			}
		} else if (minBrakeNotch == 0) {
			if (this.tascController.isEnable()) {
				if (this.tascController.isBreaking()) {
					if (this.tascController.isStopPosition()) {
						train.setNotch(-5);
						this.brakingControlling = false;
						this.ATO = false;
						this.tascController.disable();
						tsm.delSync();
						return;
					}
				}
			}

			this.acceleratorControlling = false;
			//明示的Notch0
			this.brakingControlling = false;
			train.setNotch(0);
		} else {
			this.acceleratorControlling = false;
			if (this.tascController.isEnable()) {
				if (this.tascController.isBreaking()) {
					if (this.tascController.isStopPosition()) {
						if (speedH <= 0F) {
							train.setNotch(-5);
							this.brakingControlling = false;
							this.ATO = false;
							this.tascController.disable();
							tsm.delSync();
							return;
						}
					}
				}
			}
			this.brakingControlling = true;
			train.setNotch(minBrakeNotch);
		}
		tsm.delSync();
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
