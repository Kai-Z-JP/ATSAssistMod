package jp.kaiz.atsassistmod.controller;

import jp.ngt.rtm.entity.train.EntityTrainBase;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
 * 【JavaScriptから呼び出そうとしている方へ】
 * <p>
 * クライアント側から呼ばないでください！！！！！
 * 全てサーバー側で処理をしています。
 * クライアント側で呼んでも一切動作しません！！！！！！！！！！！！！！！
 * <p>
 * ⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜
 * ■■■■■■⬜⬜⬜⬜⬜⬜■■■■⬜⬜⬜⬜■■⬜⬜⬜⬜■■⬜⬜■⬜■■■■■■■■⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜⬜■■■■⬜⬜⬜■■■■■■⬜⬜⬜⬜⬜⬜■■■■■■⬜■■■■■⬜⬜⬜⬜⬜■■■■⬜⬜⬜⬜■■⬜⬜⬜⬜⬜■■■⬜⬜⬜⬜⬜⬜⬜⬜■■■■■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■■■■■■⬜⬜■■⬜⬜⬜⬜■■⬜■■■■■■■■⬜⬜⬜⬜⬜⬜■■■■⬜⬜⬜■■⬜⬜■■■■■■⬜⬜⬜⬜■■■■■■
 * ■⬜⬜⬜⬜■■⬜⬜⬜⬜■■⬜⬜⬜■■⬜⬜■■■⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■⬜⬜⬜■■⬜⬜⬜■■⬜⬜■■■⬜⬜⬜⬜■■■⬜⬜⬜⬜⬜⬜⬜■■⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■■■⬜⬜⬜■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜■■⬜⬜■■⬜⬜■⬜⬜⬜⬜■■⬜⬜⬜■⬜⬜⬜⬜⬜
 * ■⬜⬜⬜⬜⬜■■⬜⬜■■⬜⬜⬜⬜⬜■⬜⬜■⬜■⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■■⬜■■⬜⬜⬜⬜⬜■⬜⬜■■■⬜⬜⬜⬜■■■⬜⬜⬜⬜⬜⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■⬜■⬜⬜⬜■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜
 * ■⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜■⬜⬜■⬜⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜■■■⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜■⬜■⬜⬜⬜■■■■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■■■⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜
 * ■⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜■⬜⬜■■⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜⬜⬜■■■⬜⬜⬜■■■■■■⬜⬜⬜⬜⬜⬜■■■■■■⬜■■■■■⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜■⬜⬜■⬜⬜■⬜■■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■■■■■■⬜⬜■⬜⬜■■⬜■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■■■⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜■■■■■■
 * ■⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜■⬜⬜⬜■⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜■⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜■⬜⬜■⬜⬜■⬜■■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜■⬜■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■■⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜
 * ■⬜⬜⬜⬜⬜■■⬜⬜■■⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜■■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■⬜⬜■■⬜⬜⬜⬜⬜■⬜⬜■⬜⬜■■■⬜⬜■■⬜⬜⬜⬜⬜⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜
 * ■⬜⬜⬜⬜■■⬜⬜⬜⬜■■⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜■■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜■■⬜⬜⬜■⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■■⬜⬜■■⬜⬜⬜■■⬜⬜■⬜⬜⬜■■⬜⬜■■⬜⬜⬜⬜⬜⬜⬜■■⬜⬜⬜■⬜⬜■⬜⬜⬜⬜⬜⬜■■⬜⬜■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜■■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜■■⬜⬜■■⬜⬜■⬜⬜⬜⬜■■⬜⬜⬜■⬜⬜⬜⬜⬜
 * ■■■■■■⬜⬜⬜⬜⬜⬜■■■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■■■■⬜⬜⬜⬜⬜■■■■⬜⬜⬜■■■■■■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜■⬜⬜⬜⬜⬜■⬜⬜⬜■■■■⬜⬜⬜⬜■⬜⬜⬜■⬜⬜⬜■■⬜⬜⬜⬜⬜⬜⬜⬜■■■■⬜⬜⬜■■■■■■⬜■⬜⬜⬜■■■■■■⬜⬜■⬜⬜⬜⬜⬜■■⬜⬜⬜⬜■⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜■■■■⬜⬜⬜■⬜⬜⬜■■■■■■⬜⬜⬜⬜■■■■■■
 * <p>
 * /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
 */
public class TrainController implements Runnable {
	private List<SpeedOrder> speedOrderList = new ArrayList<>();
	private List<Integer> speedLimit = new ArrayList<>();
	private int maxSpeed = 0;

	private boolean ATO = false;
	private boolean acceleratorControlling = false;
	private TASCSpeedOrder tascSpeedOrder = new TASCSpeedOrder(-1D);
	private boolean brakingControlling = false;

	private ATACSController atacsController = new ATACSController();
	private boolean atacsBreaking;
	private boolean ATACS;

	private EntityTrainBase train;
	private TCSyncManager tsm;

	private double[] coordinates;

	//null処理めんどいからこれとインスタンス比較で
	public static final TrainController NULL = new TrainController();

	public void addSpeedOrder(SpeedOrder speedOrder) {
		this.speedOrderList.add(speedOrder);
	}

	public List<SpeedOrder> getSpeedOrderList() {
		return speedOrderList;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public void addSpeedLimit(int speedLimit, EntityTrainBase train) {
		if (this.getSpeedLimit() > speedLimit) {
			//RTMCore.proxy.playSound(train, new ResourceLocation(""), 1.0F, 1.0F);
		}
		this.speedLimit.add(speedLimit);
	}

	public void removeSpeedLimit() {
		if (!this.speedLimit.isEmpty()) {
			this.speedLimit.remove(0);
		}
	}

	//速度制限
	public int getSpeedLimit() {
		//未設定の時はInteger.MAX_VALUEを返す
		if (this.speedLimit.isEmpty()) {
			return Integer.MAX_VALUE;
		} else {
			return this.speedLimit.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);
		}
	}

	//ATOの目標速度 制限とは別
	public int getATOSpeedLimit() {
		if (this.speedLimit.isEmpty()) {
			return this.maxSpeed;
		} else {
			int minLimit = this.speedLimit.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);
			return Math.min(minLimit, this.maxSpeed);
		}
	}

	//ATACS速度
	public int getATACSSpeedLimit() {
		//未設定の時はInteger.MAX_VALUEを返す
		return this.atacsController.getDisplaySpeed();
	}

	public void enableTASC(double distance) {
		this.tascSpeedOrder.setEnable();
		this.tascSpeedOrder.setTargetDistance(distance);
	}

	public void disableTASC() {
		this.tascSpeedOrder.setDisable();
	}

	public boolean isTASC() {
		return this.tascSpeedOrder.isEnable();
	}

	public int getTASCStopDistance() {
		return (int) this.tascSpeedOrder.getTargetDistance();
	}

	public void setTASCStopDistance(double distance) {
		this.tascSpeedOrder.setTargetDistance(distance);
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
		return ATACS;
	}

	public void enableATACS() {
		this.ATACS = true;
	}

	public void disableATACS() {
		this.ATACS = false;
	}

	public void run() {
		this.onUpdate();
	}

	public void init(EntityTrainBase train, TCSyncManager tsm) {
		this.train = train;
		this.tsm = tsm;
		this.tsm.addSync();
	}


	//Tick毎の処理
	public void onUpdate() {
		double movingDistance;
		if (this.coordinates == null) {
			this.coordinates = new double[]{train.posX, train.posY, train.posZ};
			movingDistance = 0d;
		} else {
			//前回の座標を取得
			double[] oldXYZ = this.coordinates;
			//今回の座標
			double[] XYZ = {train.posX, train.posY, train.posZ};

			//移動距離を計算
			//勾配計算ナシ
			movingDistance = Math.sqrt(Math.pow((oldXYZ[0] - XYZ[0]), 2) /*+ Math.pow((oldXYZ[1]-XYZ[1]),2)*/ + Math.pow((oldXYZ[2] - XYZ[2]), 2));
			//今回の座標を記録
			this.coordinates = XYZ;
		}


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
				this.addSpeedLimit(speedOrder.getTargetSpeed(), train);
				removeList.add(speedOrder);
			} else {
				speedOrder.changeTargetDistance(movingDistance);
				//ATO有効時予告に基づきブレーキノッチ追加
				if (this.ATO) {
					brakeNotch.add(speedOrder.getNeedNotch(speedH));
				}
			}
		}

		removeList.forEach(this.speedOrderList::remove);

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

		//ATACS
		if (this.isATACS()) {
			this.atacsController.onTick(train, movingDistance);
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
		}

		//TASC
		this.tascSpeedOrder.changeTargetDistance(movingDistance);
		if (this.tascSpeedOrder.isEnable()) {
			int needNotch = this.tascSpeedOrder.getNeedNotch(speedH);
			if (this.tascSpeedOrder.isBreaking()) {
				brakeNotch.add(needNotch);
			}
		}

		//ノッチ制御最終処理

		//最大ブレーキ(値は小さい)のを計算
		int minBrakeNotch;
		if (brakeNotch.isEmpty()) {
			minBrakeNotch = 1;
		} else {
			minBrakeNotch = brakeNotch.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);
		}

		//0以上はブレーキ指定なし アクセル許可
		if (minBrakeNotch > 0) {
			//最大アクセルを計算
			int maxAcceleratorNotch;
			if (acceleratorNotch.isEmpty()) {
				maxAcceleratorNotch = -1;
			} else {
				maxAcceleratorNotch = acceleratorNotch.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
			}

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
			if (this.tascSpeedOrder.isEnable()) {
				if (this.tascSpeedOrder.isBreaking()) {
					if (this.tascSpeedOrder.isStopPosition()) {
						train.setNotch(-5);
						this.brakingControlling = false;
						this.ATO = false;
						this.tascSpeedOrder.setDisable();
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
			if (this.tascSpeedOrder.isEnable()) {
				if (this.tascSpeedOrder.isBreaking()) {
					if (this.tascSpeedOrder.isStopPosition()) {
						if (speedH <= 0F) {
							train.setNotch(-5);
							this.brakingControlling = false;
							this.ATO = false;
							this.tascSpeedOrder.setDisable();
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
}
