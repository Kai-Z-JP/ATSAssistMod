package jp.kaiz.atsassistmod.sound;

import jp.ngt.rtm.sound.MovingSoundTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class ATSAMovingSoundTileEntity extends MovingSoundTileEntity {
	public ATSAMovingSoundTileEntity(TileEntity par1Entity, ResourceLocation par2Sound, boolean par3Repeat) {
		super(par1Entity, par2Sound, par3Repeat);
	}


	public ATSAMovingSoundTileEntity(TileEntity par1Entity, int[] pos, ResourceLocation par2Sound, boolean par3Repeat) {
		super(par1Entity, par2Sound, par3Repeat);
		this.xPosF = (float) pos[0] + 0.5F;
		this.yPosF = (float) pos[1] + 0.5F;
		this.zPosF = (float) pos[2] + 0.5F;
	}
}
