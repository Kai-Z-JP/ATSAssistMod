package jp.kaiz.atsassistmod.item;

import jp.kaiz.atsassistmod.ATSAssistCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DataMapEditor extends Item {
    public DataMapEditor() {
        setUnlocalizedName(ATSAssistCore.MODID + ":" + "itemDataMapEditor");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos blockPos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote && player.isSneaking()) {
            player.openGui(ATSAssistCore.INSTANCE, ATSAssistCore.guiId_DataMapEditor, world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }
        return EnumActionResult.SUCCESS;
    }
}
