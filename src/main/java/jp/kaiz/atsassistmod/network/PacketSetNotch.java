package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.world.World;

public class PacketSetNotch implements IMessage, IMessageHandler<PacketSetNotch, IMessage> {
    private int entityID;
    private byte notch;
    private int type;

    public PacketSetNotch() {
    }

    public PacketSetNotch(byte notch) {
        this.notch = notch;
        this.type = PacketCause.PLAYER.id;
    }

    public PacketSetNotch(EntityTrainBase train, byte notch) {
        this.entityID = train.getEntityId();
        this.notch = notch;
        this.type = PacketCause.ENTITY_ID.id;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.type);
        if (this.type == PacketCause.PLAYER.id) {
            buffer.writeByte(this.notch);
        } else if (this.type == PacketCause.ENTITY_ID.id) {
            buffer.writeInt(this.entityID);
            buffer.writeByte(this.notch);
        }
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.type = buffer.readInt();
        if (this.type == PacketCause.PLAYER.id) {
            this.notch = buffer.readByte();
        } else if (this.type == PacketCause.ENTITY_ID.id) {
            this.entityID = buffer.readInt();
            this.notch = buffer.readByte();
        }
    }

    @Override
    public IMessage onMessage(PacketSetNotch message, MessageContext ctx) {
        World world = ctx.getServerHandler().playerEntity.worldObj;
        if (message.type == PacketCause.PLAYER.id) {
            ATSAssistAPIHandlerServer.INSTANCE.onUseAPI(ctx.getServerHandler().playerEntity, message.notch);
        } else if (message.type == PacketCause.ENTITY_ID.id) {
            EntityTrainBase entity = (EntityTrainBase) world.getEntityByID(message.entityID);
            ATSAssistAPIHandlerServer.INSTANCE.onUseAPI(entity, message.notch);
        }
        return null;
    }
}
