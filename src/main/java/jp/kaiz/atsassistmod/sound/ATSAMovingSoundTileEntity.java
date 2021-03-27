package jp.kaiz.atsassistmod.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class ATSAMovingSoundTileEntity extends MovingSound {
    protected final TileEntity entity;

    public ATSAMovingSoundTileEntity(TileEntity tileEntity, ResourceLocation src, boolean repeat, float volume) {
        super(src);
        this.entity = tileEntity;
        this.setPos(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        this.repeat = repeat;
        this.volume = volume;
    }

    public ATSAMovingSoundTileEntity(TileEntity tileEntity, int[] pos, ResourceLocation src, boolean repeat, float volume) {
        super(src);
        this.entity = tileEntity;
        this.setPos(pos[0], pos[1], pos[2]);
        this.repeat = repeat;
        this.volume = volume;
    }

    private void setPos(int x, int y, int z) {
        this.xPosF = (float) x + 0.5F;
        this.yPosF = (float) y + 0.5F;
        this.zPosF = (float) z + 0.5F;
    }

    @Override
    public void update() {
        if (this.entity.isInvalid()) {
            this.donePlaying = true;
        }
    }

    public void stop() {
        this.donePlaying = true;
    }
}
