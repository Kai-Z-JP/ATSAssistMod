package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.controller.TrainControllerManager;
import jp.kaiz.atsassistmod.controller.trainprotection.TrainProtectionType;
import jp.ngt.rtm.entity.train.EntityTrainBase;

public class PacketTrainProtectionSetter implements IMessage, IMessageHandler<PacketTrainProtectionSetter, IMessage> {
    private TrainProtectionType type;

    public PacketTrainProtectionSetter() {
    }

    public PacketTrainProtectionSetter(TrainProtectionType type) {
        this.type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.type = TrainProtectionType.getType(buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.type.id);
    }

    @Override
    public IMessage onMessage(PacketTrainProtectionSetter message, MessageContext ctx) {
        TrainControllerManager.getTrainController((EntityTrainBase) ctx.getServerHandler().playerEntity.ridingEntity).enableTrainProtection(message.type);
        return null;
    }
}
