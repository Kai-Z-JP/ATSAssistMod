package jp.kaiz.atsassistmod.event;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.network.PacketEmergencyBrake;
import jp.ngt.rtm.event.RTMKeyHandlerClient;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ATSAssistKeyHandler {
    @SubscribeEvent
    public void onKeyDown(InputEvent.KeyInputEvent event) {
        if (RTMKeyHandlerClient.KEY_EB.isPressed()) {
            ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketEmergencyBrake());
        }
    }
}
