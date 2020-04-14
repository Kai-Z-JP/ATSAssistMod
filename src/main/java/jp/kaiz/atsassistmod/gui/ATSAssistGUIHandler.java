package jp.kaiz.atsassistmod.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
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
			return new GroundUnitGUI((TileEntityGroundUnit) player.worldObj.getTileEntity(x, y, z));
		}
		return null;
	}
}
