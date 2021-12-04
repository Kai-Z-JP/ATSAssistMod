package jp.kaiz.atsassistmod.block;

import jp.kaiz.atsassistmod.ATSAssistBlock;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.CreativeTabATSAssist;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityIFTTT;
import jp.ngt.rtm.electric.IBlockConnective;
import net.minecraft.block.BlockContainer;
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

public class IFTTT extends BlockContainer implements IBlockConnective {

    public IFTTT() {
        super(Material.ROCK);
        setCreativeTab(CreativeTabATSAssist.ATSA);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //ブロックを右クリックした際の動作
        playerIn.openGui(ATSAssistCore.INSTANCE, ATSAssistCore.guiId_IFTTT, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityIFTTT();
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return ((TileEntityIFTTT) worldIn.getTileEntity(pos)).getRedStoneOutput();
    }

    @Override
    public boolean canConnect(World world, int i, int i1, int i2) {
        return true;
    }


    @Override
    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ATSAssistBlock.blockIFTTT);
    }
}
