package jp.kaiz.atsassistmod.utils;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class KaizWorldUtils {
    public static Block getBlock(World world, int x, int y, int z) {
        return world.getBlock(x, y, z);
    }

    public static TileEntity getTileEntity(World world, int x, int y, int z) {
        return world.getTileEntity(x, y, z);
    }

    public int getBlockMeta(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }
}
