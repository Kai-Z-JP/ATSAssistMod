package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
import net.minecraft.world.WorldServer;

public class PacketGroundUnitTile implements IMessage, IMessageHandler<PacketGroundUnitTile, IMessage> {
	private int id;
	private int x;
	private int y;
	private int z;
	boolean linkRedStone;
	boolean lateCancel;
	private int par1;
	private int par2;
	private byte[] par1ByteArray;

	public PacketGroundUnitTile() {
	}

	public PacketGroundUnitTile(TileEntityGroundUnit tile, boolean linkRedStone) {
		this.id = tile.getType().id;
		this.x = tile.xCoord;
		this.y = tile.yCoord;
		this.z = tile.zCoord;
		this.linkRedStone = linkRedStone;
	}

	public PacketGroundUnitTile(TileEntityGroundUnit tile, boolean linkRedStone, boolean lateCancel) {
		this(tile, linkRedStone);
		this.lateCancel = lateCancel;
	}

	public PacketGroundUnitTile(TileEntityGroundUnit tile, boolean linkRedStone, int par1) {
		this(tile, linkRedStone);
		this.par1 = par1;
	}

	public PacketGroundUnitTile(TileEntityGroundUnit tile, boolean linkRedStone, int speed, int distance) {
		this(tile, linkRedStone);
		this.par1 = speed;
		this.par2 = distance;
	}

	public PacketGroundUnitTile(TileEntityGroundUnit tile, boolean linkRedStone, byte[] par1ByteArray) {
		this(tile, linkRedStone);
		this.par1ByteArray = par1ByteArray;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.linkRedStone = buf.readBoolean();
		switch (this.id) {
			case 1:
				//距離とスピード
				this.par1 = buf.readInt();
				this.par2 = buf.readInt();
				break;
			case 2:
				//遅れて実行するか
				this.lateCancel = buf.readBoolean();
				break;
			case 4://距離のみ
			case 6://距離のみ
			case 9://スピードのみ
			case 11://スピードのみ
				this.par1 = buf.readInt();
				break;
			case 13:
				this.par1ByteArray = buf.readBytes(12).array();
				break;
			case 5:
			case 7:
			case 10:
			case 14:
			case 15:
				//なし
				break;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.id);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeBoolean(this.linkRedStone);
		switch (this.id) {
			case 1:
				//距離とスピード
				buf.writeInt(this.par1);
				buf.writeInt(this.par2);
				break;
			case 2:
				//遅れて実行するか
				buf.writeBoolean(this.lateCancel);
				break;
			case 4://距離のみ
			case 6://距離のみ
			case 9://スピードのみ
			case 11://スピードのみ
				//スピードのみ
				buf.writeInt(this.par1);
				break;
			case 13:
				buf.writeBytes(this.par1ByteArray);
				break;
			case 5:
			case 7:
			case 10:
			case 14:
			case 15:
				//なし
				break;
		}
	}

	@Override
	public IMessage onMessage(PacketGroundUnitTile message, MessageContext ctx) {
		WorldServer world = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
		TileEntityGroundUnit tile = (TileEntityGroundUnit) world.getTileEntity(message.x, message.y, message.z);
		tile.setLinkRedStone(message.linkRedStone);
		switch (message.id) {
			case 1:
				//距離とスピード
				((TileEntityGroundUnit.Speed) tile).setSpeedLimit(message.par1);
				((TileEntityGroundUnit.Distance) tile).setDistance(message.par2);
				break;
			case 2:
				((TileEntityGroundUnit.ATCSpeedLimitCancel) tile).setLateCancel(message.lateCancel);
				break;
			case 4:
			case 6:
				//距離のみ
				((TileEntityGroundUnit.Distance) tile).setDistance(message.par1);
				break;
			case 9:
			case 11:
				//スピードのみ
				((TileEntityGroundUnit.Speed) tile).setSpeedLimit(message.par1);
				break;
			case 13:
				((TileEntityGroundUnit.TrainStateSet) tile).setStates(message.par1ByteArray);
				break;
			case 5:
			case 7:
			case 10:
				//なし
				break;
		}
		tile.markDirty();
		tile.getDescriptionPacket();
		world.markBlockForUpdate(message.x, message.y, message.z);
		world.notifyBlockChange(message.x, message.y, message.z, ATSAssistCore.blockGroundUnit);
		return null;
	}
}
