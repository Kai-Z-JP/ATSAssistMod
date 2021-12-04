package jp.kaiz.atsassistmod.network;

import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.controller.TrainController;
import jp.kaiz.atsassistmod.controller.TrainControllerManager;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
        EntityPlayer player = ctx.getServerHandler().player;
        if (player.isRiding() && player.getRidingEntity() instanceof EntityTrainBase) {
            EntityTrainBase train = (EntityTrainBase) player.getRidingEntity();
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