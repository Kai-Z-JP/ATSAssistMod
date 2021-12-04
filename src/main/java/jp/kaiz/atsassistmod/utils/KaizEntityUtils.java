package jp.kaiz.atsassistmod.utils;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class KaizEntityUtils {
    public static World getWorld(Entity entity) {
        return entity.world;
    }

    public static int getX(Entity entity) {
        return entity.getPosition().getX();
    }

    public static double getPosX(Entity entity) {
        return entity.posX;
    }

    public static int getY(Entity entity) {
        return entity.getPosition().getY();
    }

    public static double getPosY(Entity entity) {
        return entity.posY;
    }

    public static int getZ(Entity entity) {
        return entity.getPosition().getZ();
    }

    public static double getPosZ(Entity entity) {
        return entity.posZ;
    }
}
