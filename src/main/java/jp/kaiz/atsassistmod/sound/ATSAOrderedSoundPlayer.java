package jp.kaiz.atsassistmod.sound;

import jp.kaiz.atsassistmod.ATSAssistCore;
import net.minecraft.tileentity.TileEntity;

import java.util.Arrays;
import java.util.List;

public class ATSAOrderedSoundPlayer {
    public static void playSounds(TileEntity tile, List<int[]> posList, List<Object> orderList, float volume) {
        if (posList != null && orderList != null) {
            ATSAssistCore.proxy.playSounds(tile, posList, orderList, volume);
        }

    }

    public static void playSounds(TileEntity tile, int[][] posArray, Object[] orderArray, float volume) {
        if (posArray == null || orderArray == null) {
            return;
        }

        playSounds(tile, Arrays.asList(posArray), Arrays.asList(orderArray), volume);
    }
}
