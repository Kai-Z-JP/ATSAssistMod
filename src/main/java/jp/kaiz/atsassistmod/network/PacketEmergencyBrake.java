package jp.kaiz.atsassistmod.network;

import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.controller.TrainControllerManager;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
        EntityPlayer player = ctx.getServerHandler().player;
        Entity entity = player.getRidingEntity();
        if (player.isRiding() && entity instanceof EntityTrainBase && ((EntityTrainBase) entity).isControlCar()) {
            TrainControllerManager.getTrainController((EntityTrainBase) entity).setEB();
        }
        return null;
    }
}
