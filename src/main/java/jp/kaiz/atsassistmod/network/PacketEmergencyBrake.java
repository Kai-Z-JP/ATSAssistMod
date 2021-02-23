package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.controller.TrainControllerManager;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PacketEmergencyBrake implements IMessage, IMessageHandler<PacketEmergencyBrake, IMessage> {

    public PacketEmergencyBrake() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    @Override
    public IMessage onMessage(PacketEmergencyBrake message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        Entity entity = player.ridingEntity;
        if (player.isRiding() && entity instanceof EntityTrainBase && ((EntityTrainBase) entity).isControlCar()) {
            TrainControllerManager.getTrainController((EntityTrainBase) entity).setEB();
        }
        return null;
    }
}
