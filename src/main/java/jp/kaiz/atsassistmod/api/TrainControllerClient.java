package jp.kaiz.atsassistmod.api;

import jp.ngt.rtm.entity.train.EntityTrain;

public class TrainControllerClient {
	private static int atoI, tascI, atcI, atacsI, entityID;
	private static boolean atoB, tascB, atacsB;

	public static void set(boolean atoB, boolean tascB, boolean atacsB, int atoI, int tascI, int atcI, int atacsI, int entityID) {
		TrainControllerClient.atoI = atoI;
		TrainControllerClient.tascI = tascI;
		TrainControllerClient.atcI = atcI;

		TrainControllerClient.atacsI = atacsI;

		TrainControllerClient.atoB = atoB;
		TrainControllerClient.tascB = tascB;
		TrainControllerClient.atacsB = atacsB;

		TrainControllerClient.entityID = entityID;
	}

	//必ずisEnableを確認
	public static boolean isEnable(EntityTrain train) {
		return TrainControllerClient.entityID == train.getEntityId();
	}

	public static boolean isEnable(int entityID) {
		return TrainControllerClient.entityID == entityID;
	}

	public static boolean isATO() {
		return atoB;
	}

	public static boolean isTASC() {
		return tascB;
	}

	public static boolean isATACS() {
		return atacsB;
	}

	public static int getATOSpeed() {
		return atoI;
	}

	public static int getTASCSpeed() {
		return tascI;
	}

	public static int getATCSpeed() {
		return atcI;
	}

	public static int getATACSSpeed() {
		return atacsI;
	}

	public static void setEntityID(int entityID) {
		TrainControllerClient.entityID = entityID;
	}
}
