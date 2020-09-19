package jp.kaiz.atsassistmod.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityIFTTT;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import jp.ngt.rtm.modelpack.IModelSelector;
import jp.ngt.rtm.modelpack.state.DataMap;
import jp.ngt.rtm.modelpack.state.ResourceState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.Random;

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
			return new GUIIFTTTMaterial((TileEntityIFTTT) player.worldObj.getTileEntity(x, y, z));
		} else if (ID == ATSAssistCore.guiId_TrainProtectionSelector) {
			EntityTrainBase train;
			if (player.isRiding() && player.ridingEntity instanceof EntityTrainBase) {
				train = (EntityTrainBase) player.ridingEntity;
				if (train.isControlCar()) {
					if (train.getFormation() == null) {
						player.addChatComponentMessage(new ChatComponentText("編成データがありません。車両を置きなおしてください。"));
						return null;
					}
					return new GUITrainProtectionSelector();
				}
			}
		} else if (ID == ATSAssistCore.guiId_DataMapEditor) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof IModelSelector) {
				ResourceState resourceState = ((IModelSelector) tileEntity).getResourceState();
				if (resourceState != null) {
					DataMap dataMap = resourceState.dataMap;
					if (dataMap != null) {
						dataMap.setString("test" + new Random().nextInt(), "test", 1);
						return new GUIDataMapEditor((IModelSelector) tileEntity, dataMap);
					}
				}
			}
		}
		return null;
	}
}
