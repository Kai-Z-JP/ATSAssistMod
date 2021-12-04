package jp.kaiz.atsassistmod.network;

import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.ATSAssistBlock;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
import jp.ngt.ngtlib.block.BlockUtil;
import jp.ngt.ngtlib.network.PacketNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGroundUnitTileInit implements IMessage, IMessageHandler<PacketGroundUnitTileInit, IMessage> {
    public long pos;
    public int id;


    public PacketGroundUnitTileInit() {
    }

    public PacketGroundUnitTileInit(int id, TileEntityGroundUnit tileEntity) {
        this.pos = tileEntity.getPos().toLong();
        this.id = id;
    }

    public PacketGroundUnitTileInit(long pos) {
        this.pos = pos;
        this.id = -1;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = buf.readLong();
        this.id = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.pos);
        buf.writeInt(this.id);
    }

    @Override
    public IMessage onMessage(PacketGroundUnitTileInit message, MessageContext ctx) {
        BlockPos blockPos = BlockPos.fromLong(message.pos);
        if (message.id == -1) {
            World world = ATSAssistCore.proxy.getWorld();
            ATSAssistCore.proxy.getPlayer().openGui(ATSAssistCore.INSTANCE, ATSAssistCore.guiId_GroundUnit, world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            return null;
        } else {
            World world = ctx.getServerHandler().player.getServerWorld();
            BlockUtil.setBlock(world, blockPos, ATSAssistBlock.blockGroundUnit, message.id, 3);
            TileEntityGroundUnit tile = (TileEntityGroundUnit) world.getTileEntity(blockPos);
            PacketNBT.sendToClient(tile);
            tile.markDirty();
            return new PacketGroundUnitTileInit(message.pos);
        }
    }
}
