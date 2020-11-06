package jp.kaiz.atsassistmod.block;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.CreativeTabATSAssist;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class StationAnnounce extends BlockContainer {

	public StationAnnounce() {
		super(Material.rock);
		setCreativeTab(CreativeTabATSAssist.tabUtils);
		//modidないとテクスチャおかしくなる
		setBlockName(ATSAssistCore.MODID + ":" + "stationAnnounce");
		setBlockTextureName(ATSAssistCore.MODID + ":" + "stationAnnounce");
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
		//ブロックを右クリックした際の動作
		return true;
	}
}
