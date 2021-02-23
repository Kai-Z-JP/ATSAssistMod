package jp.kaiz.atsassistmod.event;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.network.PacketFormationSync;
import jp.kaiz.atsassistmod.render.TrainGuiRender;
import jp.ngt.rtm.entity.train.EntityBogie;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import jp.ngt.rtm.entity.train.parts.EntityVehiclePart;
import jp.ngt.rtm.entity.vehicle.EntityVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

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

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityVehicleBase || event.entity instanceof EntityBogie || event.entity instanceof EntityVehiclePart) {
            event.entity.renderDistanceWeight = 16 * ATSAssistCore.proxy.getMinecraft().gameSettings.renderDistanceChunks;
            event.entity.ignoreFrustumCheck = true;
        }
        if (event.entity instanceof EntityTrainBase) {
            if (((EntityTrainBase) event.entity).getFormation() == null) {
                ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketFormationSync((EntityTrainBase) event.entity, false));
            }
        }
    }
}
