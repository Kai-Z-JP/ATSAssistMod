package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.ATSAssistBlock;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityIFTTT;
import jp.kaiz.atsassistmod.ifttt.IFTTTContainer;
import jp.kaiz.atsassistmod.ifttt.IFTTTUtil;
import net.minecraft.world.World;

public class PacketIFTTT implements IMessage, IMessageHandler<PacketIFTTT, IMessage> {
	private int x, y, z;
	private byte[] serialized;
	private int ifcbIndex;
	private int type;

	public PacketIFTTT() {
	}

	public PacketIFTTT(TileEntityIFTTT tile, IFTTTContainer ifcb, int ifcbIndex, int type) {
		this.x = tile.xCoord;
		this.y = tile.yCoord;
		this.z = tile.zCoord;
		this.serialized = IFTTTUtil.convertClass(ifcb);
		this.ifcbIndex = ifcbIndex;
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.serialized = buf.readBytes(buf.readInt()).array();
		this.ifcbIndex = buf.readInt();
		this.type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeInt(this.serialized.length);
		buf.writeBytes(this.serialized);
		buf.writeInt(this.ifcbIndex);
		buf.writeInt(this.type);
	}

	@Override
	public IMessage onMessage(PacketIFTTT message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		TileEntityIFTTT tile = (TileEntityIFTTT) world.getTileEntity(message.x, message.y, message.z);
		if (message.ifcbIndex == -1) {
			tile.addIFTTT(IFTTTUtil.convertClass(message.serialized));
		} else if (message.type == 2) {
			tile.removeIFTTT(IFTTTUtil.convertClass(message.serialized), message.ifcbIndex);
		} else {
			tile.setIFTTT(IFTTTUtil.convertClass(message.serialized), message.ifcbIndex);
		}
		tile.markDirty();
		tile.getDescriptionPacket();
		world.markBlockForUpdate(message.x, message.y, message.z);
		world.notifyBlockChange(message.x, message.y, message.z, ATSAssistBlock.blockIFTTT);
		return null;
	}
}
