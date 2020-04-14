package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
import net.minecraft.world.World;

public class PacketGroundUnitTileInit implements IMessage, IMessageHandler<PacketGroundUnitTileInit, IMessage> {
	public int x, y, z;
	public int id;


	public PacketGroundUnitTileInit() {
	}

	public PacketGroundUnitTileInit(int id, TileEntityGroundUnit tileEntity) {
		this.x = tileEntity.xCoord;
		this.y = tileEntity.yCoord;
		this.z = tileEntity.zCoord;
		this.id = id;
	}

	public PacketGroundUnitTileInit(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = -1;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeInt(this.id);
	}

	@Override
	public IMessage onMessage(PacketGroundUnitTileInit message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		world.setBlock(message.x, message.y, message.z, ATSAssistCore.blockGroundUnit, message.id, 3);
		TileEntityGroundUnit tile = (TileEntityGroundUnit) world.getTileEntity(message.x, message.y, message.z);
		tile.markDirty();
		tile.getDescriptionPacket();
		world.markBlockForUpdate(message.x, message.y, message.z);
		world.notifyBlockChange(message.x, message.y, message.z, ATSAssistCore.blockGroundUnit);
		return new PacketGroundUnitTileInit(message.x, message.y, message.z);
	}
}
