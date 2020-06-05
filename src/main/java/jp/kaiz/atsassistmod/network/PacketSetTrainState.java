package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PacketSetTrainState implements IMessage, IMessageHandler<PacketSetTrainState, IMessage> {
    private int id, entityID;
    private byte states;
    private int cause;


    public PacketSetTrainState() {
    }

    public PacketSetTrainState(int id, byte states) {
        this.cause = PacketCause.PLAYER.id;
        this.id = id;
        this.states = states;
    }

    public PacketSetTrainState(int entityID, int id, byte states) {
        this.cause = PacketCause.ENTITY_ID.id;
        this.entityID = entityID;
        this.id = id;
        this.states = states;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.cause = buf.readInt();
        if (this.cause == PacketCause.PLAYER.id) {
            this.id = buf.readInt();
            this.states = buf.readByte();
        } else if (this.cause == PacketCause.ENTITY_ID.id) {
            this.entityID = buf.readInt();
            this.id = buf.readInt();
            this.states = buf.readByte();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.cause);
        if (this.cause == PacketCause.PLAYER.id) {
            buf.writeInt(this.id);
            buf.writeByte(this.states);
        } else if (this.cause == PacketCause.ENTITY_ID.id) {
            buf.writeInt(this.entityID);
            buf.writeInt(this.id);
            buf.writeByte(this.states);
        }
    }

    @Override
    public IMessage onMessage(PacketSetTrainState message, MessageContext ctx) {
        Entity entity = null;
        if (message.cause == PacketCause.PLAYER.id) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            if (player.isRiding()) {
                entity = player.ridingEntity;
            }
        } else if (message.cause == PacketCause.ENTITY_ID.id) {
            World world = ctx.getServerHandler().playerEntity.worldObj;
            entity = world.getEntityByID(message.entityID);
        }

        if (entity instanceof EntityTrainBase) {
            EntityTrainBase train = (EntityTrainBase) entity;
            train.setTrainStateData(message.id, message.states);
        }

        return null;
    }
}
