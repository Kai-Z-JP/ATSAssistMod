package jp.kaiz.atsassistmod.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ATSAMovingSoundEntity extends MovingSound {
    protected final Entity entity;

    public ATSAMovingSoundEntity(Entity entity, ResourceLocation src, boolean repeat, float volume) {
        super(src);
        this.entity = entity;
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
        if (this.entity.isEntityAlive()) {
            this.setPos((int) this.entity.posX, (int) this.entity.posY, (int) this.entity.posZ);
        } else {
            this.stop();
        }
    }

    public void stop() {
        this.donePlaying = true;
    }
}
