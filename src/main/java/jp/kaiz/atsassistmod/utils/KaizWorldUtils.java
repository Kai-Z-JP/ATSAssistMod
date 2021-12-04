package jp.kaiz.atsassistmod.utils;

import jp.ngt.ngtlib.block.BlockUtil;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class KaizWorldUtils {
    public static Block getBlock(World world, int x, int y, int z) {
        return BlockUtil.getBlock(world, x, y, z);
    }

    public static TileEntity getTileEntity(World world, int x, int y, int z) {
        return BlockUtil.getTileEntity(world, x, y, z);
    }

    public static int getBlockMeta(World world, int x, int y, int z) {
        return BlockUtil.getMetadata(world, x, y, z);
    }

    public static void playSound(World world, String soundName, double x, double y, double z, float volume, float pitch) {
        world.playSound(null, x, y, z, new SoundEvent(new ResourceLocation(soundName)), SoundCategory.MASTER, volume, pitch);
    }

    public static void setBlock(World world, int x, int y, int z, int id) {
        BlockUtil.setBlock(world, x, y, z, Block.getBlockById(id), 0, 3);
    }

    public static void setBlock(World world, int x, int y, int z, int id, int meta) {
        BlockUtil.setBlock(world, x, y, z, Block.getBlockById(id), meta, 3);
    }
}
