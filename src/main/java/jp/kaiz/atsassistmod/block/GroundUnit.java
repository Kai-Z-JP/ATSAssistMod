package jp.kaiz.atsassistmod.block;

import jp.kaiz.atsassistmod.ATSAssistBlock;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.CreativeTabATSAssist;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
import jp.ngt.ngtlib.block.BlockContainerCustomWithMeta;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GroundUnit extends BlockContainerCustomWithMeta {

    public GroundUnit() {
        super(Material.ROCK);
        setCreativeTab(CreativeTabATSAssist.ATSA);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //ブロックを右クリックした際の動作
        playerIn.openGui(ATSAssistCore.INSTANCE, ATSAssistCore.guiId_GroundUnit, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return GroundUnitType.getType(metadata).getNewInstance();
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return ((TileEntityGroundUnit) worldIn.getTileEntity(pos)).getRedStoneOutput();
    }


    @Override
    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ATSAssistBlock.blockGroundUnit);
    }
}
