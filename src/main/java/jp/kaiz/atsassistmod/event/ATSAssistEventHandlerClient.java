package jp.kaiz.atsassistmod.event;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jp.kaiz.atsassistmod.render.TrainGuiRender;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@SideOnly(Side.CLIENT)
public class ATSAssistEventHandlerClient {
	private final TrainGuiRender guiRender;

	public ATSAssistEventHandlerClient(Minecraft mc) {
		this.guiRender = new TrainGuiRender(mc);
	}


	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onRenderGui(RenderGameOverlayEvent.Pre event) {
		this.guiRender.onRenderGui(event);
	}
}
