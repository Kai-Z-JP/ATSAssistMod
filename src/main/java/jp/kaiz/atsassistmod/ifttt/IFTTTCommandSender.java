package jp.kaiz.atsassistmod.ifttt;

import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.utils.KaizUtils;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class IFTTTCommandSender extends CommandBlockLogic {
	private final TileEntity tile;
	private int returnValue;

	public IFTTTCommandSender(TileEntity tile) {
		this.tile = tile;
	}

	@Override
	public String getCommandSenderName() {
		return "ATSA IFTTT Executer" + String.format("(%s, %s, %s)", this.tile.xCoord, this.tile.yCoord, this.tile.zCoord);
	}

	@Override
	public IChatComponent func_145748_c_() {
		return new ChatComponentText(this.getCommandSenderName());
	}

	@Override
	public void addChatMessage(IChatComponent p_145747_1_) {
	}

	@Override
	public void func_145756_e() {

	}

	@Override
	public int func_145751_f() {
		return 0;
	}

	@Override
	public void func_145757_a(ByteBuf p_145757_1_) {

	}

	@Override
	public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_) {
		return p_70003_1_ <= 2;
	}

	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		return new ChunkCoordinates(this.tile.xCoord, this.tile.yCoord, this.tile.zCoord);
	}

	@Override
	public World getEntityWorld() {
		return this.tile != null ? this.tile.getWorldObj() : null;
	}

	public void executeCommand(String command) {
		if (!KaizUtils.isServer()) {
			this.returnValue = 0;
		}

		MinecraftServer server = MinecraftServer.getServer();

		if (server != null && server.isCommandBlockEnabled()) {
			ICommandManager icommandmanager = server.getCommandManager();
			this.returnValue = icommandmanager.executeCommand(this, command);
		} else {
			this.returnValue = 0;
		}
	}
}
