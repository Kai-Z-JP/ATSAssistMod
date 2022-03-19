package jp.kaiz.atsassistmod.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityIFTTT;
import jp.kaiz.atsassistmod.ifttt.IFTTTContainer;
import jp.kaiz.atsassistmod.ifttt.IFTTTUtil;
import jp.ngt.ngtlib.network.PacketNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketIFTTT implements IMessage, IMessageHandler<PacketIFTTT, IMessage> {
    private long pos;
    private byte[] serialized;
    private int ifcbIndex;
    private int type;
    private boolean anyMatch;

    public PacketIFTTT() {
    }

    public PacketIFTTT(TileEntityIFTTT tile, IFTTTContainer ifcb, int ifcbIndex, int type) {
        this.pos = tile.getPos().toLong();
        this.serialized = IFTTTUtil.convertClass(ifcb);
        this.ifcbIndex = ifcbIndex;
        this.type = type;
        this.anyMatch = tile.isAnyMatch();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = buf.readLong();
        this.ifcbIndex = buf.readInt();
        this.type = buf.readInt();
        this.serialized = ByteBufUtil.getBytes(buf);
        this.anyMatch = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.pos);
        buf.writeInt(this.ifcbIndex);
        buf.writeInt(this.type);
        buf.writeBytes(this.serialized);
        buf.writeBoolean(this.anyMatch);
    }

    @Override
    public IMessage onMessage(PacketIFTTT message, MessageContext ctx) {
        BlockPos blockPos = BlockPos.fromLong(message.pos);
        World world = ctx.getServerHandler().player.getServerWorld();
        TileEntityIFTTT tile = (TileEntityIFTTT) world.getTileEntity(blockPos);
        if (message.type == 3) {
            tile.setAnyMatch(message.anyMatch);
        } else if (message.ifcbIndex == -1) {
            tile.addIFTTT(IFTTTUtil.convertClass(message.serialized));
        } else if (message.type == 2) {
            tile.removeIFTTT(IFTTTUtil.convertClass(message.serialized), message.ifcbIndex);
        } else {
            tile.setIFTTT(IFTTTUtil.convertClass(message.serialized), message.ifcbIndex);
        }
        PacketNBT.sendToClient(tile);
        tile.markDirty();
        return null;
    }
}
