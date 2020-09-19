package jp.kaiz.atsassistmod.item;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.CreativeTabATSAssist;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DataMapEditor extends Item {
	public DataMapEditor() {
		setCreativeTab(CreativeTabATSAssist.tabUtils);
		setUnlocalizedName(ATSAssistCore.MODID + ":" + "itemDataMapEditor");
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float p, float q, float r) {
		if (world.isRemote && player.isSneaking()) {
			player.openGui(ATSAssistCore.INSTANCE, ATSAssistCore.guiId_DataMapEditor, world, x, y, z);
		}
		return false;
	}
}
