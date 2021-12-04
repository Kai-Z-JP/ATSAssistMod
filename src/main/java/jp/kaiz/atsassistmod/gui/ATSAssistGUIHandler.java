package jp.kaiz.atsassistmod.gui;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityIFTTT;
import jp.ngt.ngtlib.block.BlockUtil;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import jp.ngt.rtm.modelpack.IResourceSelector;
import jp.ngt.rtm.modelpack.state.DataMap;
import jp.ngt.rtm.modelpack.state.ResourceState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ATSAssistGUIHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == ATSAssistCore.guiId_GroundUnit) {
            return new GUIGroundUnit((TileEntityGroundUnit) BlockUtil.getTileEntity(player.getEntityWorld(), x, y, z));
        } else if (ID == ATSAssistCore.guiId_StationAnnounce) {
            return new GUIStationAnnounce();
        } else if (ID == ATSAssistCore.guiId_IFTTT) {
            return new GUIIFTTTMaterial((TileEntityIFTTT) BlockUtil.getTileEntity(player.getEntityWorld(), x, y, z));
        } else if (ID == ATSAssistCore.guiId_TrainProtectionSelector) {
            EntityTrainBase train;
            if (player.isRiding() && player.getRidingEntity() instanceof EntityTrainBase) {
                train = (EntityTrainBase) player.getRidingEntity();
                if (train.isControlCar()) {
                    if (train.getFormation() == null) {
                        player.sendMessage(new TextComponentString("編成データがありません。車両を置きなおしてください。"));
                        return null;
                    }
                    return new GUITrainProtectionSelector();
                }
            }
        } else if (ID == ATSAssistCore.guiId_DataMapEditor) {
            TileEntity tileEntity = BlockUtil.getTileEntity(world, x, y, z);
            if (tileEntity instanceof IResourceSelector) {
                ResourceState resourceState = ((IResourceSelector) tileEntity).getResourceState();
                if (resourceState != null) {
                    DataMap dataMap = resourceState.dataMap;
                    if (dataMap != null) {
//						dataMap.setString("test" + new Random().nextInt(), "test", 1);
                        return new GUIDataMapEditor((IResourceSelector) tileEntity, dataMap);
                    }
                }
            }
        }
        return null;
    }
}
