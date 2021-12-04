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
        return this.tileEntity.getPos().getX();
    }

    public int getY() {
        return this.tileEntity.getPos().getY();
    }

    public int getZ() {
        return this.tileEntity.getPos().getZ();
    }

    public int[] getPos() {
        return new int[]{this.getX(), this.getY(), this.getZ()};
    }

    public World getWorld() {
        return this.tileEntity.getWorld();
    }

    public int getBlockMetadata() {
        return this.tileEntity.getBlockMetadata();
    }

    public Block getBlockType() {
        return this.tileEntity.getBlockType();
    }

    public int getBlockPowerInput() {
        return this.getWorld().getStrongPower(tileEntity.getPos());
    }
}