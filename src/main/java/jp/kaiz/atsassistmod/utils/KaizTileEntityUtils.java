package jp.kaiz.atsassistmod.utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class KaizTileEntityUtils {
    public static World getWorld(TileEntity tileEntity) {
        return tileEntity.getWorld();
    }

    public static int getX(TileEntity tileEntity) {
        return tileEntity.getPos().getX();
    }

    public static int getY(TileEntity tileEntity) {
        return tileEntity.getPos().getY();
    }

    public static int getZ(TileEntity tileEntity) {
        return tileEntity.getPos().getZ();
    }
}
