package jp.kaiz.atsassistmod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.CreativeTabATSAssist;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityIFTTT;
import jp.ngt.rtm.electric.IBlockConnective;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class IFTTT extends BlockContainer implements IBlockConnective {
    private IIcon icon;

    public IFTTT() {
        super(Material.rock);
        setCreativeTab(CreativeTabATSAssist.tabUtils);
        //modidないとテクスチャおかしくなる
        setBlockName(ATSAssistCore.MODID + ":" + "IFTTT");
        setBlockTextureName(ATSAssistCore.MODID + ":" + "IFTTT");
        setStepSound(Block.soundTypeStone);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
        //ブロックを右クリックした際の動作
        player.openGui(ATSAssistCore.INSTANCE, ATSAssistCore.guiId_IFTTT, player.worldObj, x, y, z);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.icon = register.registerIcon(this.getTextureName());
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityIFTTT();
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        return ((TileEntityIFTTT) world.getTileEntity(x, y, z)).getRedStoneOutput();
    }

    @Override
    public boolean canConnect(World world, int i, int i1, int i2) {
        return true;
    }

    @Override
    public boolean shouldCheckWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        return false;
    }
}
