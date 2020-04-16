package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.api.TrainControllerClient;
import jp.kaiz.atsassistmod.controller.TrainController;

public class PacketTrainControllerToClient implements IMessage, IMessageHandler<PacketTrainControllerToClient, IMessage> {
	int atoI, tascI, atcI, atacsI;
	boolean atoB, tascB, atacsB;
	int entityID;

	public PacketTrainControllerToClient() {
	}

	public PacketTrainControllerToClient(TrainController controller, int entityID) {
		//ATO
		this.atoB = controller.isATO();
		this.atoI = controller.getATOSpeedLimit();

		//TASC
		this.tascB = controller.tascController.isEnable();
		this.tascI = (int) controller.tascController.getStopDistance();

		//ATC
		this.atcI = controller.getSpeedLimit();

		//ATACS
		this.atacsB = controller.isATACS();
		this.atacsI = controller.getATACSSpeedLimit();

		this.entityID = entityID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.atoB = buf.readBoolean();
		this.tascB = buf.readBoolean();
		this.atacsB = buf.readBoolean();
		this.atoI = buf.readInt();
		this.tascI = buf.readInt();
		this.atcI = buf.readInt();
		this.atacsI = buf.readInt();
		this.entityID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.atoB);
		buf.writeBoolean(this.tascB);
		buf.writeBoolean(this.atacsB);
		buf.writeInt(this.atoI);
		buf.writeInt(this.tascI);
		buf.writeInt(this.atcI);
		buf.writeInt(this.atacsI);
		buf.writeInt(this.entityID);
	}

	@Override
	public IMessage onMessage(PacketTrainControllerToClient message, MessageContext ctx) {
		TrainControllerClient.set(message.atoB, message.tascB, message.atacsB, message.atoI, message.tascI, message.atcI, message.atacsI, message.entityID);
		return null;
	}
}
