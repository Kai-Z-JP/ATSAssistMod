package jp.kaiz.atsassistmod.utils;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ATSATileEntityWrapper {
    private final TileEntity tileEntity;

    public ATSATileEntityWrapper(TileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    public int getX() {
        return this.tileEntity.xCoord;
    }

    public int getY() {
        return this.tileEntity.yCoord;
    }

    public int getZ() {
        return this.tileEntity.zCoord;
    }

    public int[] getPos() {
        return new int[]{this.getX(), this.getY(), this.getZ()};
    }

    public World getWorld() {
        return this.tileEntity.getWorldObj();
    }

    public int getBlockMetadata() {
        return this.tileEntity.getBlockMetadata();
    }

    public Block getBlockType() {
        return this.tileEntity.getBlockType();
    }

    public int getBlockPowerInput() {
        return this.getWorld().getBlockPowerInput(this.getX(), this.getY(), this.getZ());
    }
}