package jp.kaiz.atsassistmod.block;

import jp.kaiz.atsassistmod.ATSAssistCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.lang.reflect.Field;

public class StationAnnounceBase extends BlockContainer {

	public StationAnnounceBase() {
		super(Material.rock);
		setCreativeTab(CreativeTabs.tabBlock);
		//modidないとテクスチャおかしくなる
		setBlockName(ATSAssistCore.MODID + ":" + "stationAnnounceBase");
		setBlockTextureName(ATSAssistCore.MODID + ":" + "stationAnnounceBase");
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
		//ブロックを右クリックした際の動作
		if (world.isRemote) {
			try {
				SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();

				Field field = soundHandler.getClass().getDeclaredField("field_147697_e");
				field.setAccessible(true);
				SoundRegistry sReg = (SoundRegistry) field.get(soundHandler);
				for (Object key : sReg.getKeys()) {
					if (key instanceof ResourceLocation) {
						ResourceLocation sLoc = (ResourceLocation) key;
						System.out.println(sLoc.toString());
					}
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
