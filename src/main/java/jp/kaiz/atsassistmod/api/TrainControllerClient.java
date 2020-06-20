package jp.kaiz.atsassistmod.api;

import jp.kaiz.atsassistmod.controller.trainprotection.TrainProtectionType;

public class TrainControllerClient {
	private int atoI, tascI, atcI, tpLimit, tpType;
	private boolean atoB, tascB;
	private boolean dontShowHUD;

	public TrainControllerClient() {
	}

	public TrainControllerClient(boolean atoB, boolean tascB, int tpType, int atoI, int tascI, int atcI, int tpLimit) {
		this.set(atoB, tascB, tpType, atoI, tascI, atcI, tpLimit);
	}


	public void set(boolean atoB, boolean tascB, int tpType, int atoI, int tascI, int atcI, int tpLimit) {
		this.atoI = atoI;
		this.tascI = tascI;
		this.atcI = atcI;

		this.tpLimit = tpLimit;

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

	public void setATO(boolean b) {
		this.atoB = b;
	}

	public void setTASC(boolean b) {
		this.tascB = b;
	}

	public boolean isATACS() {
		return this.tpType == TrainProtectionType.ATACS.id;
	}

	public void setTrainProtectionType(TrainProtectionType type) {
		this.tpType = type.id;
	}

	public void setDontShowHUD(boolean dontShowHUD) {
		this.dontShowHUD = dontShowHUD;
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
		return this.tpLimit;
	}

	public int getTrainProtectionSpeed() {
		return this.tpLimit;
	}

	public boolean isDontShowHUD() {
		return this.dontShowHUD;
	}
}
