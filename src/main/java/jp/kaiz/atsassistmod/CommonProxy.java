package jp.kaiz.atsassistmod;

import cpw.mods.fml.common.registry.GameRegistry;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityIFTTT;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

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
}
