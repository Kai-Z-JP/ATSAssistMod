package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PacketATSAssistKey implements IMessage, IMessageHandler<PacketATSAssistKey, IMessage> {
	private String playerName;
	private int entityID;
	private byte notch;
	private int type;
	public static final int setNotchByPlayer = 0;
	public static final int setNotchByEntity = 1;

	public PacketATSAssistKey() {
	}

	public PacketATSAssistKey(EntityPlayer player, byte notch) {
		this.playerName = player.getCommandSenderName();
		this.notch = notch;
		this.type = setNotchByPlayer;
	}

	public PacketATSAssistKey(EntityTrainBase entity, byte notch) {
		this.entityID = entity.getEntityId();
		this.notch = notch;
		this.type = setNotchByEntity;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(this.type);
		if (this.type == setNotchByPlayer) {
			ByteBufUtils.writeUTF8String(buffer, this.playerName);
			buffer.writeByte(this.notch);
		} else if (this.type == setNotchByEntity) {
			buffer.writeInt(this.entityID);
			buffer.writeByte(this.notch);
		}
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.type = buffer.readInt();
		if (this.type == setNotchByPlayer) {
			this.playerName = ByteBufUtils.readUTF8String(buffer);
			this.notch = buffer.readByte();
		} else if (this.type == setNotchByEntity) {
			this.entityID = buffer.readInt();
			this.notch = buffer.readByte();
		}
	}

	@Override
	public IMessage onMessage(PacketATSAssistKey message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		if (message.type == setNotchByPlayer) {
			EntityPlayer player = world.getPlayerEntityByName(message.playerName);
			ATSAssistAPIHandlerServer.INSTANCE.onUseAPI(player, message.notch);
		} else if (message.type == setNotchByEntity) {
			EntityTrainBase entity = (EntityTrainBase) world.getEntityByID(message.entityID);
			ATSAssistAPIHandlerServer.INSTANCE.onUseAPI(entity, message.notch);
		}
		return null;
	}
}
