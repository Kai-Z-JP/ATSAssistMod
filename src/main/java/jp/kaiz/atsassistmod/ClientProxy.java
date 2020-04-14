package jp.kaiz.atsassistmod;

import cpw.mods.fml.client.FMLClientHandler;
import jp.kaiz.atsassistmod.event.ATSAssistEventHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		ATSAssistEventHandlerClient handler = new ATSAssistEventHandlerClient(Minecraft.getMinecraft());
		MinecraftForge.EVENT_BUS.register(handler);
//		FMLCommonHandler.instance().bus().register(handler); //よくわからんけどなくてもいい？
	}

	@Override
	public void init() {

	}

	@Override
	public World getWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public EntityPlayer getPlayer() {
		return FMLClientHandler.instance().getClient().thePlayer;
	}
}
