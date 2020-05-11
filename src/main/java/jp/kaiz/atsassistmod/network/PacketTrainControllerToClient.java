package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.api.TrainControllerClientManager;
import jp.kaiz.atsassistmod.controller.TrainController;

public class PacketTrainControllerToClient implements IMessage, IMessageHandler<PacketTrainControllerToClient, IMessage> {
    byte type;
    int atoI, tascI, atcI, atacsI, tpType;
    boolean atoB, tascB;
    long formationID;

    public PacketTrainControllerToClient() {
    }

    public PacketTrainControllerToClient(long entityID) {
        this.type = 0;

        this.formationID = entityID;
    }

    public PacketTrainControllerToClient(TrainController controller, long entityID) {
        this.type = 1;

        this.formationID = entityID;

        //ATO
        this.atoB = controller.isATO();
        this.atoI = controller.getATOSpeedLimit();

        //TASC
        this.tascB = controller.tascController.isEnable();
        this.tascI = (int) controller.tascController.getStopDistance();

        //ATC
        this.atcI = controller.getSpeedLimit();

        //ATACS
        this.tpType = controller.getTrainProtectionType().id;
        this.atacsI = controller.getATACSSpeedLimit();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.type = buf.readByte();
        this.formationID = buf.readLong();
        if (this.type == 1) {
            this.atoB = buf.readBoolean();
            this.tascB = buf.readBoolean();
            this.tpType = buf.readInt();
            this.atoI = buf.readInt();
            this.tascI = buf.readInt();
            this.atcI = buf.readInt();
            this.atacsI = buf.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.type);
        buf.writeLong(this.formationID);
        if (this.type == 1) {
            buf.writeBoolean(this.atoB);
            buf.writeBoolean(this.tascB);
            buf.writeInt(this.tpType);
            buf.writeInt(this.atoI);
            buf.writeInt(this.tascI);
            buf.writeInt(this.atcI);
            buf.writeInt(this.atacsI);
        }
    }

    @Override
    public IMessage onMessage(PacketTrainControllerToClient message, MessageContext ctx) {
        if (message.type == 0) {
            TrainControllerClientManager.removeTCC(message.formationID);
        } else if (message.type == 1) {
            TrainControllerClientManager.setTCC(message.formationID, message.atoB, message.tascB, message.tpType, message.atoI, message.tascI, message.atcI, message.atacsI);
        }
        return null;
    }
}
