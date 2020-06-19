package jp.kaiz.atsassistmod.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityIFTTT;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ATSAssistGUIHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == ATSAssistCore.guiId_GroundUnit) {
            return new GUIGroundUnit((TileEntityGroundUnit) player.worldObj.getTileEntity(x, y, z));
        } else if (ID == ATSAssistCore.guiId_StationAnnounce) {
            return new GUIStationAnnounce();
        } else if (ID == ATSAssistCore.guiId_IFTTT) {
            return new GUIIFTTT((TileEntityIFTTT) player.worldObj.getTileEntity(x, y, z));
        } else if (ID == ATSAssistCore.guiId_TrainProtectionSelector) {
            EntityTrainBase train;
            if (player.isRiding() && player.ridingEntity instanceof EntityTrainBase) {
                train = (EntityTrainBase) player.ridingEntity;
                if (train.isControlCar()) {
                    return new GUITrainProtectionSelector(player);
                }
            }
        }
        return null;
    }
}
