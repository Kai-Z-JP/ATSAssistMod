package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.controller.TrainControllerManager;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

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
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        Entity entity = player.ridingEntity;
        if (player.isRiding() && entity instanceof EntityTrainBase && ((EntityTrainBase) entity).isControlCar()) {
            TrainControllerManager.getTrainController((EntityTrainBase) entity).setManualDrive(message.manualDrive);
        }
        return null;
    }
}
