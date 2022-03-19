package jp.kaiz.atsassistmod.sound;

import jp.ngt.ngtlib.io.ResourceLocationCustom;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class ATSAMovingSoundEntity extends MovingSound {
    protected final Entity entity;

    public ATSAMovingSoundEntity(Entity entity, ResourceLocation src, boolean repeat, float volume) {
        super(getSoundEvent(src.toString()), SoundCategory.MASTER);
        this.entity = entity;
        this.repeat = repeat;
        this.volume = volume;
    }

    private void setPos(int x, int y, int z) {
        this.xPosF = (float) x + 0.5F;
        this.yPosF = (float) y + 0.5F;
        this.zPosF = (float) z + 0.5F;
    }

    private static SoundEvent getSoundEvent(String sound) {
        SoundEvent se = SoundEvent.REGISTRY.getObject(new ResourceLocation("minecraft", sound));
        return se == null ? new ATSAMovingSoundEntity.SoundEventDummy(sound) : se;
    }

    @Override
    public void update() {
        if (this.entity.isEntityAlive()) {
            this.setPos((int) this.entity.posX, (int) this.entity.posY, (int) this.entity.posZ);
        } else {
            this.stop();
        }
    }

    public void stop() {
        this.donePlaying = true;
    }


    private static class SoundEventDummy extends SoundEvent {
        public SoundEventDummy(String sound) {
            this(new ResourceLocationCustom(sound));
        }

        private SoundEventDummy(ResourceLocation soundNameIn) {
            super(soundNameIn);
        }
    }
}
