package jp.kaiz.atsassistmod.network;

import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.controller.TrainControllerManager;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketManualDrive implements IMessage, IMessageHandler<PacketManualDrive, IMessage> {
    private boolean manualDrive;

    public PacketManualDrive() {
    }

    public PacketManualDrive(boolean manualDrive) {
        this.manualDrive = manualDrive;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.manualDrive = byteBuf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeBoolean(this.manualDrive);
    }

    @Override
    public IMessage onMessage(PacketManualDrive message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().player;
        Entity entity = player.getRidingEntity();
        if (player.isRiding() && entity instanceof EntityTrainBase && ((EntityTrainBase) entity).isControlCar()) {
            TrainControllerManager.getTrainController((EntityTrainBase) entity).setManualDrive(message.manualDrive);
        }
        return null;
    }
}
