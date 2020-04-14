package jp.kaiz.atsassistmod.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockWithMetadataCustom extends ItemBlockWithMetadata {
	//名前わける用
	//ドロップを常に0にすれば不要 言語ファイル変更必要
	public ItemBlockWithMetadataCustom(Block block) {
		super(block, block);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName() + "." + itemStack.getItemDamage();
	}
}