package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import jp.kaiz.atsassistmod.ATSAssistCore;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class PacketGroundUnitTileInitToClient implements IMessageHandler<PacketGroundUnitTileInit, IMessage> {
	@Override
	public IMessage onMessage(PacketGroundUnitTileInit message, MessageContext ctx) {
		//受け取るときにClientWorldがあるときはクラス変えないと起動しない
		World world = ATSAssistCore.proxy.getWorld();
		Minecraft.getMinecraft().thePlayer.openGui(ATSAssistCore.INSTANCE, ATSAssistCore.guiId_GroundUnit, world, message.x, message.y, message.z);
		return null;
	}
}
