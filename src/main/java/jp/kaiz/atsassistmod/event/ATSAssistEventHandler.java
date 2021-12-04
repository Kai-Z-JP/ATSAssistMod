package jp.kaiz.atsassistmod.event;

import jp.kaiz.atsassistmod.controller.TrainControllerManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;


public class ATSAssistEventHandler {
    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event) {
        if (event.side == Side.SERVER) {
            TrainControllerManager.onTick();
        }
    }
}
