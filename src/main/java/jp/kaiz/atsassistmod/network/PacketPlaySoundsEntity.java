package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.utils.KaizUtils;
import jp.ngt.ngtlib.util.NGTUtilClient;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PacketPlaySoundsEntity implements IMessage, IMessageHandler<PacketPlaySoundsEntity, IMessage> {

    private int entityId;
    private final List<Object> sourceList = new ArrayList<>();
    private float volume;

    public PacketPlaySoundsEntity() {
    }

    public PacketPlaySoundsEntity(Entity entity, List<Object> sourceList, float volume) {
        this.entityId = entity.getEntityId();
        this.sourceList.addAll(sourceList);
        this.volume = volume;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeInt(this.sourceList.size());
        this.sourceList.forEach(source -> ByteBufUtils.writeUTF8String(buf, source.toString()));
        buf.writeFloat(this.volume);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        int sourceSize = buf.readInt();
        IntStream.range(0, sourceSize).forEach(i -> this.sourceList.add(ByteBufUtils.readUTF8String(buf)));
        this.volume = buf.readFloat();
    }

    @Override
    public IMessage onMessage(PacketPlaySoundsEntity message, MessageContext ctx) {
        Entity entity = NGTUtilClient.getMinecraft().theWorld.getEntityByID(message.entityId);
        if (entity != null) {
            KaizUtils.playSounds(entity, message.sourceList, message.volume);
        }
        return null;
    }
}
