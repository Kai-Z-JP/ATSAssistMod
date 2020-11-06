package jp.kaiz.atsassistmod.block.tileentity;

import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

public abstract class TileEntityStationAnnounce extends TileEntityCustom {
    //編成単位での管理
    protected long channelID;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.channelID = tag.getLong("channelID");
        this.readNBT(tag);
    }

    protected abstract void readNBT(NBTTagCompound tag);

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setLong("channelID", this.channelID);
        this.writeNBT(tag);
    }

    protected abstract void writeNBT(NBTTagCompound tag);

    @Override
    public void updateEntity() {
    }

    protected abstract void onTick(EntityTrainBase train);

    public static class Base extends TileEntityStationAnnounce {
        //編成単位での管理
        protected long formationID;

        @Override
        protected void readNBT(NBTTagCompound tag) {
            this.formationID = tag.getLong("formationID");
        }

        @Override
        protected void writeNBT(NBTTagCompound tag) {
            tag.setLong("formationID", this.formationID);
        }

        @Override
        public void updateEntity() {
            if (!this.worldObj.isRemote) {
                AxisAlignedBB detect = AxisAlignedBB.getBoundingBox(
                        this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 3, this.zCoord + 1);
                List<?> list = this.worldObj.getEntitiesWithinAABB(EntityTrainBase.class, detect);
                if (!list.isEmpty()) {
                    EntityTrainBase train = (EntityTrainBase) list.get(0);
                    if (train.isControlCar()) {
                        if (this.formationID != train.getFormation().id) {
                            this.onTick(train);
                            this.formationID = train.getFormation().id;
                        }
                        return;
                    }
                }
                this.formationID = 0;
            }
        }

        @Override
        protected void onTick(EntityTrainBase train) {

        }
    }
}
