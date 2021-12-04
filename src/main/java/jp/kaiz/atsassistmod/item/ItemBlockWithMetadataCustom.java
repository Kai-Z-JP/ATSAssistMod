package jp.kaiz.atsassistmod.item;

import jp.ngt.ngtlib.item.ItemBlockCustom;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockWithMetadataCustom extends ItemBlockCustom {
    //名前わける用
    //ドロップを常に0にすれば不要 言語ファイル変更必要
    public ItemBlockWithMetadataCustom(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return super.getUnlocalizedName() + "." + itemStack.getItemDamage();
    }
}