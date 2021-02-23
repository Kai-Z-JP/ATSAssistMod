package jp.kaiz.atsassistmod.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.network.PacketEmergencyBrake;
import jp.ngt.rtm.event.RTMKeyHandlerClient;

@SideOnly(Side.CLIENT)
public class ATSAssistKeyHandler {
    @SubscribeEvent
    public void onKeyDown(InputEvent.KeyInputEvent event) {
        if (RTMKeyHandlerClient.KEY_EB.isPressed()) {
            ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketEmergencyBrake());
        }
    }
}
