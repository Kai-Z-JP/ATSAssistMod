package jp.kaiz.atsassistmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.CreativeTabATSAssist;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.stream.IntStream;

public class GroundUnit extends BlockContainer {

    private final IIcon[] iicon = new IIcon[16];

    public GroundUnit() {
        super(Material.rock);
        setCreativeTab(CreativeTabATSAssist.tabUtils);
        //modidないとテクスチャおかしくなる
        setBlockName(ATSAssistCore.MODID + ":" + "groundUnit");
        setBlockTextureName(ATSAssistCore.MODID + ":" + "groundUnit");
        setStepSound(Block.soundTypeStone);
    }

//    0:無動作
//
//    1:ATC制限予告
//    2:ATC制限解除
//
//    4:TASC停車距離設定
//    5:TASC電源OFF
//    6:TASC停車距離補正
//
//    9:ATO電源ON 出発信号
//    10:ATO電源OFF
//    11:ATO最高速度変更
//
//    13:列車状態変更
//    7:停車検知
//
//	  14:ATACS on
//	  15:atacs off

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
        //ブロックを右クリックした際の動作
        player.openGui(ATSAssistCore.INSTANCE, ATSAssistCore.guiId_GroundUnit, player.worldObj, x, y, z);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return iicon[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		IntStream.range(0, 16).forEach(i -> this.iicon[i] = register.registerIcon(this.getTextureName() + "_" + i));
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return GroundUnitType.getType(metadata).getNewInstance();
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int direction) {
		return 0;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int direction) {
		return this.isProvidingStrongPower(world, x, y, z, direction);
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
		return ((TileEntityGroundUnit) world.getTileEntity(x, y, z)).getRedStoneOutput();
	}

	//メタデータによりドロップ品を変える 変えないほうが便利かも
//	@Override
//	public int damageDropped(int meta) {
//		return meta;
//	}
}
