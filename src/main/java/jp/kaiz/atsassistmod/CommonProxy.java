package jp.kaiz.atsassistmod;

import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityIFTTT;
import jp.kaiz.atsassistmod.network.PacketPlaySounds;
import jp.kaiz.atsassistmod.network.PacketPlaySoundsEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

public class CommonProxy {

    /*
     * TileEntityのserver側登録メソッド。
     */
    public void registerTileEntity() {
        GameRegistry.registerTileEntity(TileEntityGroundUnit.None.class, "TileGU_None");

        GameRegistry.registerTileEntity(TileEntityGroundUnit.ATCSpeedLimitNotice.class, "TileGU_ATC_SpeedLimit_Notice");

        GameRegistry.registerTileEntity(TileEntityGroundUnit.ATCSpeedLimitCancel.class, "TileGU_ATC_SpeedLimit_Cancel");

        GameRegistry.registerTileEntity(TileEntityGroundUnit.TASCStopPositionNotice.class, "TileGU_TASC_StopPosition_Notice");

        GameRegistry.registerTileEntity(TileEntityGroundUnit.TASCDisable.class, "TileGU_TASC_Cancel");

        GameRegistry.registerTileEntity(TileEntityGroundUnit.TASCStopPositionCorrection.class, "TileGU_TASC_StopPosition_Correction");

        GameRegistry.registerTileEntity(TileEntityGroundUnit.TASCStopPosition.class, "TileGU_TASC_StopPosition");

        GameRegistry.registerTileEntity(TileEntityGroundUnit.ATODepartureSignal.class, "TileGU_ATO_Departure_Signal");

        GameRegistry.registerTileEntity(TileEntityGroundUnit.ATODisable.class, "TileGU_ATO_Cancel");

        GameRegistry.registerTileEntity(TileEntityGroundUnit.ATOChangeSpeed.class, "TileGU_ATO_Change_Speed");

        GameRegistry.registerTileEntity(TileEntityGroundUnit.TrainStateSet.class, "TileGU_TrainState");

        GameRegistry.registerTileEntity(TileEntityGroundUnit.ChangeTrainProtection.class, "TileGU_ATACS_Enable");

        GameRegistry.registerTileEntity(TileEntityGroundUnit.ATACSDisable.class, "TileGU_ATACS_Disable");

        GameRegistry.registerTileEntity(TileEntityIFTTT.class, "TileATSA_IFTTT");
    }

    public void preInit() {
    }

    public void init() {
    }

    public World getWorld() {
        return null;
    }

    public EntityPlayer getPlayer() {
        return null;
    }

    public Minecraft getMinecraft() {
        return null;
    }

    public void playSounds(TileEntity tile, List<int[]> posList, List<Object> orderList, float volume) {
        ATSAssistCore.NETWORK_WRAPPER.sendToAll(new PacketPlaySounds(tile, posList, orderList, volume));
    }

    public void playSounds(Entity entity, List<Object> orderList, float volume) {
        ATSAssistCore.NETWORK_WRAPPER.sendToAll(new PacketPlaySoundsEntity(entity, orderList, volume));
    }
}
