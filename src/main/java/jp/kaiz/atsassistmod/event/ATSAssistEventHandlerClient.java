package jp.kaiz.atsassistmod.event;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.render.TrainGuiRender;
import jp.ngt.ngtlib.util.NGTUtilClient;
import jp.ngt.rtm.entity.train.EntityBogie;
import jp.ngt.rtm.entity.train.parts.EntityVehiclePart;
import jp.ngt.rtm.entity.vehicle.EntityVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SideOnly(Side.CLIENT)
public class ATSAssistEventHandlerClient {
    private final TrainGuiRender guiRender;
    public static final List<ISound> soundList = new CopyOnWriteArrayList<>();

    public ATSAssistEventHandlerClient(Minecraft mc) {
        this.guiRender = new TrainGuiRender(mc);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderGui(RenderGameOverlayEvent.Pre event) {
        this.guiRender.onRenderGui(event);
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityVehicleBase || event.getEntity() instanceof EntityBogie || event.getEntity() instanceof EntityVehiclePart) {
            event.getEntity().setRenderDistanceWeight(16 * ATSAssistCore.proxy.getMinecraft().gameSettings.renderDistanceChunks);
            event.getEntity().ignoreFrustumCheck = true;
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        SoundHandler soundHandler = NGTUtilClient.getMinecraft().getSoundHandler();
        soundList.forEach(soundHandler::playSound);
        soundList.clear();
    }
}
