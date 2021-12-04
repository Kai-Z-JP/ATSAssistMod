package jp.kaiz.atsassistmod.network;

import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.controller.TrainControllerManager;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
        EntityPlayer player = ctx.getServerHandler().player;
        if (player.getRidingEntity() instanceof EntityTrainBase) {
            EntityTrainBase train = (EntityTrainBase) player.getRidingEntity();
            if (train.isControlCar()) {
                TrainControllerManager.getTrainController(train).setControllerNotch(message.notch);
            }
        }
        return null;
    }
}
