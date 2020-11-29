package jp.kaiz.atsassistmod.network;

import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.entity.player.EntityPlayer;

public class ATSAssistAPIHandlerServer {
	public static final ATSAssistAPIHandlerServer INSTANCE = new ATSAssistAPIHandlerServer();

	private ATSAssistAPIHandlerServer() {
	}

	public void onUseAPI(EntityPlayer player, byte notch) {
		EntityTrainBase train = this.getRidingTrain(player);
		if (train != null) {
			train.setNotch(notch);
		}
	}

	public void onUseAPI(EntityTrainBase entityID, byte notch) {
		if (entityID != null) {
			entityID.setNotch(notch);
		}
	}


	private EntityTrainBase getRidingTrain(EntityPlayer player) {
		return player.isRiding() && player.ridingEntity instanceof EntityTrainBase ? (EntityTrainBase) player.ridingEntity : null;
	}
}
