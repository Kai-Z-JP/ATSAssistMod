package jp.kaiz.atsassistmod.voicetext;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.utils.KaizUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class VoiceText {
    private static final String BASE_URL = "https://api.voicetext.jp/v1/tts";
    private String key;

    private String text;
    private Speaker speaker;
    private Format format;
    private Emotion emotion;
    private int emotionLevel;
    private int pitch;
    private int speed;
    private int volume;

    public VoiceText(String key) {
        this.key = key;
    }

    public VoiceText setText(String text) {
        this.text = text;
        return this;
    }

    public VoiceText setSpeaker(Speaker speaker) {
        this.speaker = speaker;
        return this;
    }

    public VoiceText setFormat(Format format) {
        this.format = format;
        return this;
    }

    public VoiceText setEmotion(Emotion emotion, int level) {
        this.emotion = emotion;
        this.emotionLevel = MathHelper.clamp(level, 1, 4);
        return this;
    }

    public VoiceText setPitch(int pitch) {
        this.pitch = MathHelper.clamp(pitch, 50, 200);
        return this;
    }

    public VoiceText setSpeed(int speed) {
        this.speed = MathHelper.clamp(speed, 50, 400);
        return this;
    }

    public VoiceText setVolume(int volume) {
        this.volume = MathHelper.clamp(volume, 50, 200);
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void playSound() {
        new Thread(() -> {
            AudioInputStream ais = this.getAudioInputStream();
            if (ais != null) {
                KaizUtils.playSound(ais);
            }
        }).start();
    }

    @SideOnly(Side.CLIENT)
    public void playSound(float x, float y, float z, float volume) {
        new Thread(() -> {
            AudioInputStream ais = this.getAudioInputStream();
            if (ais != null) {
                KaizUtils.playSound(ais, x, y, z, volume);
            }
        }).start();
    }

    public byte[] getBytes() {
        if (this.text == null || this.speaker == null) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(("text=" + this.text).getBytes(StandardCharsets.UTF_8));
            baos.write('&');
            baos.write(("speaker=" + this.speaker.toString()).getBytes());
            if (this.format != null) {
                baos.write('&');
                baos.write(("format=" + this.format).getBytes());
            }
            if (this.emotion != null) {
                baos.write('&');
                baos.write(("emotion=" + this.emotion.toString()).getBytes());
                baos.write(("emotion_level=" + this.emotionLevel).getBytes());
            }
            if (this.pitch != 0) {
                baos.write('&');
                baos.write(("pitch=" + this.pitch).getBytes());
            }
            if (this.speed != 0) {
                baos.write('&');
                baos.write(("speed=" + this.speed).getBytes());
            }
            if (this.volume != 0) {
                baos.write('&');
                baos.write(("volume=" + this.volume).getBytes());
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public AudioInputStream getAudioInputStream() {
        if (this.key == null || this.text == null || this.speaker == null) {
            return null;
        }
        return VoiceText.getAudioInputStream(this.key, this.getBytes());
    }

    public static AudioInputStream getAudioInputStream(String key, byte[] bytes) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(BASE_URL).openConnection();
            conn.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((key + ":").getBytes()));
            conn.setRequestProperty("User-Agent", String.format("ATSAssist_%s:FromMinecraft", ATSAssistCore.VERSION));
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.getOutputStream().write(bytes);

            if (conn.getResponseCode() != 200) {
                System.out.println(conn.getResponseMessage());
                return null;
            }

            return AudioSystem.getAudioInputStream(new BufferedInputStream(conn.getInputStream()));
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
            return null;
        }
    }
}
