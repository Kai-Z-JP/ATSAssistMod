package jp.kaiz.atsassistmod.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import jp.kaiz.atsassistmod.controller.TrainControllerManager;


public class ATSAssistEventHandler {
    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event) {
        if (event.side == Side.SERVER) {
            TrainControllerManager.onTick();
        }
    }
}
