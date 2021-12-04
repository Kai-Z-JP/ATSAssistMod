package jp.kaiz.atsassistmod.sound;

import jp.ngt.ngtlib.io.ResourceLocationCustom;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class ATSAMovingSoundTileEntity extends MovingSound {
    protected final TileEntity entity;


    public ATSAMovingSoundTileEntity(TileEntity tileEntity, ResourceLocation src, boolean repeat, float volume) {
        super(getSoundEvent(src.toString()), SoundCategory.MASTER);
        this.entity = tileEntity;
        this.setPos(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ());
        this.repeat = repeat;
        this.volume = volume;
    }

    public ATSAMovingSoundTileEntity(TileEntity tileEntity, int[] pos, ResourceLocation src, boolean repeat, float volume) {
        super(getSoundEvent(src.toString()), SoundCategory.MASTER);
        this.entity = tileEntity;
        this.setPos(pos[0], pos[1], pos[2]);
        this.repeat = repeat;
        this.volume = volume;
    }

    private static SoundEvent getSoundEvent(String sound) {
        SoundEvent se = SoundEvent.REGISTRY.getObject(new ResourceLocation("minecraft", sound));
        return se == null ? new SoundEventDummy(sound) : se;
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


    private static class SoundEventDummy extends SoundEvent {
        public SoundEventDummy(String sound) {
            this((ResourceLocation) (new ResourceLocationCustom(sound)));
        }

        private SoundEventDummy(ResourceLocation soundNameIn) {
            super(soundNameIn);
        }
    }
}
