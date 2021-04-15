package jp.kaiz.atsassistmod.utils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.ngt.ngtlib.util.NGTUtil;
import jp.ngt.ngtlib.util.NGTUtilClient;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.tileentity.TileEntity;
import org.apache.commons.lang3.EnumUtils;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class KaizUtils {
    public static Enum<?> getNextEnum(Enum<?> e) {
        List<?> enumList = EnumUtils.getEnumList(e.getDeclaringClass());
        int index = enumList.indexOf(e);
        return (Enum<?>) enumList.get(enumList.size() > index + 1 ? index + 1 : 0);
    }

    public static boolean isServer() {
        return FMLCommonHandler.instance().getSide() == Side.SERVER;
    }

    public static void playSound(AudioInputStream ais) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private static SoundSystem sndSystem;

    public static void playSound(AudioInputStream ais, float x, float y, float z, float radius) {
        if (sndSystem == null) {
            SoundManager sndManager = (SoundManager) NGTUtil.getField(SoundHandler.class, NGTUtilClient.getMinecraft().getSoundHandler(), "field_147694_f");
            sndSystem = (SoundSystem) NGTUtil.getField(SoundManager.class, sndManager, "field_148620_e");
        }
        try {
            String name = UUID.randomUUID().toString();
            sndSystem.rawDataStream(ais.getFormat(), false, name, x, y, z, 2, radius);

            int bytecount = SoundSystemConfig.getStreamingBufferSize();
            byte[] buff = new byte[bytecount];
            sndSystem.feedRawAudioData(name, buff);
            sndSystem.play(name);
            while (ais.read(buff, 0, bytecount) != -1) {
                sndSystem.feedRawAudioData(name, buff);
            }
            System.out.println(String.valueOf(sndSystem.playing(name)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
