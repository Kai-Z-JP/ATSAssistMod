package jp.kaiz.atsassistmod.utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class KaizTileEntityUtils {
    public static World getWorld(TileEntity tileEntity) {
        return tileEntity.getWorldObj();
    }

    public static int getX(TileEntity tileEntity) {
        return tileEntity.xCoord;
    }

    public static int getY(TileEntity tileEntity) {
        return tileEntity.yCoord;
    }

    public static int getZ(TileEntity tileEntity) {
        return tileEntity.zCoord;
    }
}
