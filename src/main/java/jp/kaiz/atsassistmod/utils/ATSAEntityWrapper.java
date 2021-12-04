package jp.kaiz.atsassistmod.utils;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ATSAEntityWrapper {
    private final Entity entity;

    public ATSAEntityWrapper(Entity entity) {
        this.entity = entity;
    }

    public int getX() {
        return this.entity.getPosition().getX();
    }

    public double getPosX() {
        return this.entity.posX;
    }

    public int getY() {
        return this.entity.getPosition().getY();
    }

    public double getPosY() {
        return this.entity.posY;
    }

    public int getZ() {
        return this.entity.getPosition().getZ();
    }

    public double getPosZ() {
        return this.entity.posZ;
    }

    public World getWorld() {
        return this.entity.getEntityWorld();
    }
}
