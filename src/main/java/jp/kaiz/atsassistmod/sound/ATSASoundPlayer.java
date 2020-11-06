package jp.kaiz.atsassistmod.sound;

import jp.ngt.ngtlib.util.NGTUtil;
import jp.ngt.ngtlib.util.NGTUtilClient;
import jp.ngt.rtm.sound.MovingSoundTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class ATSASoundPlayer {
	private ATSASoundPlayer() {
	}

	public void playSound(TileEntity tile, ResourceLocation src, boolean repeat) {
	}

	public void playSound(TileEntity tile, int[] pos, ResourceLocation src, boolean repeat) {
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
		private MovingSoundTileEntity sound;

		@Override
		public void playSound(TileEntity tile, ResourceLocation src, boolean repeat) {
			if (this.sound != null) {
				this.stopSound();
			}
			this.sound = new ATSAMovingSoundTileEntity(tile, src, repeat);
			this.sound.setVolume(10.0F);
			NGTUtilClient.playSound(this.sound);
		}

		@Override
		public void playSound(TileEntity tile, int[] pos, ResourceLocation src, boolean repeat) {
			if (pos == null) {
				this.playSound(tile, src, repeat);
				return;
			}
			if (this.sound != null) {
				this.stopSound();
			}
			this.sound = new ATSAMovingSoundTileEntity(tile, pos, src, repeat);
			this.sound.setVolume(10.0F);
			NGTUtilClient.playSound(this.sound);
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
