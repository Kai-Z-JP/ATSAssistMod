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

    public static int getBlockMeta(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    public static void playSound(World world, String soundName, double x, double y, double z, float volume, float pitch) {
        world.playSoundEffect(x, y, z, soundName, volume, pitch);
    }

    public static void setBlock(World world, int x, int y, int z, int id) {
        world.setBlock(x, y, z, Block.getBlockById(id));
    }

    public static void setBlock(World world, int x, int y, int z, int id, int meta) {
        world.setBlock(x, y, z, Block.getBlockById(id), meta, 3);
    }

    public static int getBlockId(World world, int x, int y, int z) {
        return Block.getIdFromBlock(world.getBlock(x, y, z));
    }
}
