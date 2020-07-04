package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.controller.TrainController;
import jp.kaiz.atsassistmod.controller.TrainControllerManager;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.entity.player.EntityPlayer;

public class PacketTrainDriveMode implements IMessage, IMessageHandler<PacketTrainDriveMode, IMessage> {
    private int mode;

    public PacketTrainDriveMode() {
    }

    public PacketTrainDriveMode(int driveMode) {
        //0手動
        //1TASC
        //2TASC/ATO
        this.mode = driveMode;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.mode = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.mode);
    }

    @Override
    public IMessage onMessage(PacketTrainDriveMode message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        if (player.isRiding() && player.ridingEntity instanceof EntityTrainBase) {
            EntityTrainBase train = (EntityTrainBase) player.ridingEntity;
            TrainController tc = TrainControllerManager.getTrainController(train);
            if (train.isControlCar()) {
                switch (message.mode) {
                    case 0:
                        tc.disableATO();
                        tc.tascController.disable();
                        break;
                    case 1:
                        tc.disableATO();
                        break;
                    case 2:
                        break;
                }
            }
        }
        return null;
    }
}