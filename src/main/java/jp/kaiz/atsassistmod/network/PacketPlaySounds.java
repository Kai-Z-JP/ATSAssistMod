package jp.kaiz.atsassistmod.network;

import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityIFTTT;
import jp.kaiz.atsassistmod.utils.KaizUtils;
import jp.ngt.ngtlib.util.NGTUtilClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class PacketPlaySounds implements IMessage, IMessageHandler<PacketPlaySounds, IMessage> {

    private long tilePos;
    private final List<int[]> posList = new ArrayList<>();
    private final List<Object> sourceList = new ArrayList<>();
    private float volume;

    public PacketPlaySounds() {
    }

    public PacketPlaySounds(TileEntity tile, int[][] posArray, Object[] sourceArray, float volume) {
        this.tilePos = tile.getPos().toLong();
        Collections.addAll(this.posList, posArray);
        Collections.addAll(this.sourceList, sourceArray);
        this.volume = volume;
    }

    public PacketPlaySounds(TileEntity tile, List<int[]> posList, List<Object> sourceList, float volume) {
        this.tilePos = tile.getPos().toLong();
        this.posList.addAll(posList);
        this.sourceList.addAll(sourceList);
        this.volume = volume;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.tilePos);
        buf.writeInt(this.posList.size());
        this.posList.forEach(pos -> {
            buf.writeInt(pos[0]);
            buf.writeInt(pos[1]);
            buf.writeInt(pos[2]);
        });
        buf.writeInt(this.sourceList.size());
        this.sourceList.forEach(source -> ByteBufUtils.writeUTF8String(buf, source.toString()));
        buf.writeFloat(this.volume);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.tilePos = buf.readLong();
        int posSize = buf.readInt();
        IntStream.range(0, posSize).forEach(i -> {
            int x = buf.readInt();
            int y = buf.readInt();
            int z = buf.readInt();
            this.posList.add(new int[]{x, y, z});
        });
        int sourceSize = buf.readInt();
        IntStream.range(0, sourceSize).forEach(i -> this.sourceList.add(ByteBufUtils.readUTF8String(buf)));
        this.volume = buf.readFloat();
    }

    @Override
    public IMessage onMessage(PacketPlaySounds message, MessageContext ctx) {
        BlockPos blockPos = BlockPos.fromLong(message.tilePos);
        TileEntity tile = NGTUtilClient.getMinecraft().world.getTileEntity(blockPos);
        if (tile instanceof TileEntityIFTTT) {
            KaizUtils.playSounds(tile, message.posList, message.sourceList, message.volume);
        }
        return null;
    }
}
