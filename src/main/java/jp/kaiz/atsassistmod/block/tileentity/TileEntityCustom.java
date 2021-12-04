package jp.kaiz.atsassistmod.block.tileentity;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.ngt.ngtlib.event.TickProcessQueue;
import jp.ngt.ngtlib.network.PacketNBT;
import jp.ngt.rtm.RTMItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TileEntityCustom extends TileEntity implements ITickable {
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        final World world = this.getWorld();
        TickProcessQueue.getInstance(Side.SERVER).add(world1 -> {
            TileEntityCustom.this.sendPacket();
            return true;
        }, 40);//遅延させると届く、下のはレールで使えないため
        return new SPacketUpdateTileEntity(this.pos, -1, this.getUpdateTag());//null返すと届かない？
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    protected void sendPacket() {
        if (this.world == null || !this.world.isRemote) {
            PacketNBT.sendToClient(this);
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean isRenderBeam() {
        EntityPlayer player = ATSAssistCore.proxy.getPlayer();
        return player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(RTMItem.crowbar);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}
