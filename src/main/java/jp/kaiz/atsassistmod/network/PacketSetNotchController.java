package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.controller.TrainControllerManager;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSetNotchController implements IMessage, IMessageHandler<PacketSetNotchController, IMessage> {

    private byte notch;

    public PacketSetNotchController() {
    }

    public PacketSetNotchController(byte notch) {
        this.notch = notch;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeByte(this.notch);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.notch = buffer.readByte();
    }

    @Override
    public IMessage onMessage(PacketSetNotchController message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        if (player.isRiding() && player.ridingEntity instanceof EntityTrainBase) {
            EntityTrainBase train = (EntityTrainBase) player.ridingEntity;
            if (train.isControlCar()) {
                if (TrainControllerManager.containsTrainController(train)) {
                    TrainControllerManager.getTrainController(train).setControllerNotch(message.notch);
                } else {
                    train.setNotch(message.notch);
                }
            }
        }
        return null;
    }
}
