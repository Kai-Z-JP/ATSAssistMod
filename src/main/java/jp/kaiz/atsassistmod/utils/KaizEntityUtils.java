package jp.kaiz.atsassistmod.utils;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class KaizEntityUtils {
    public static World getWorld(Entity entity) {
        return entity.worldObj;
    }

    public static int getX(Entity entity) {
        return entity.serverPosX;
    }

    public static double getPosX(Entity entity) {
        return entity.posX;
    }

    public static int getY(Entity entity) {
        return entity.serverPosY;
    }

    public static double getPosY(Entity entity) {
        return entity.posY;
    }

    public static int getZ(Entity entity) {
        return entity.serverPosZ;
    }

    public static double getPosZ(Entity entity) {
        return entity.posZ;
    }
}
