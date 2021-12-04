package jp.kaiz.atsassistmod.sound;

import jp.ngt.ngtlib.sound.NGTSound;
import jp.ngt.ngtlib.util.NGTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class ATSASoundPlayer {
    private ATSASoundPlayer() {
    }

    public void playSound(TileEntity tile, ResourceLocation src, boolean repeat) {
    }

    public void playSound(TileEntity tile, int[] pos, ResourceLocation src, boolean repeat, float volume) {
    }

    public void stopSound() {
    }

    public boolean isPlaying() {
        return false;
    }

    public static ATSASoundPlayer create() {
        return NGTUtil.isServer() ? new ATSASoundPlayer() : new ATSASoundPlayerClient();
    }

    private static class ATSASoundPlayerClient extends ATSASoundPlayer {
        private ATSAMovingSoundTileEntity sound;

        @Override
        public void playSound(TileEntity tile, ResourceLocation src, boolean repeat) {
            this.stopSound();
            this.sound = new ATSAMovingSoundTileEntity(tile, src, repeat, 10.0F);
            NGTSound.playSound(this.sound);
        }

        @Override
        public void playSound(TileEntity tile, int[] pos, ResourceLocation src, boolean repeat, float volume) {
            if (pos == null) {
                return;
            }
            this.stopSound();
            this.sound = new ATSAMovingSoundTileEntity(tile, pos, src, repeat, volume);
            NGTSound.playSound(this.sound);
        }

        @Override
        public void stopSound() {
            if (this.sound != null) {
                this.sound.stop();
                this.sound = null;
            }
        }

        @Override
        public boolean isPlaying() {
            return this.sound != null;
        }
    }
}
