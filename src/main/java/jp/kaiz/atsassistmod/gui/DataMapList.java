package jp.kaiz.atsassistmod.gui;

import cpw.mods.fml.client.GuiScrollingList;
import jp.kaiz.atsassistmod.utils.DataEntrySet;
import net.minecraft.client.renderer.Tessellator;

import java.util.ArrayList;

public class DataMapList extends GuiScrollingList {
	private final GUIDataMapEditor parent;
	private final ArrayList<DataEntrySet> dataEntrySetList;

	public DataMapList(GUIDataMapEditor parent, ArrayList<DataEntrySet> dataEntrySetList, int listWidth) {
		super(parent.getMinecraftInstance(), listWidth, parent.height, 55, parent.height - 26, parent.width / 2 - 200, 35);
		this.parent = parent;
		this.dataEntrySetList = dataEntrySetList;
	}

	@Override
	protected int getSize() {
		return this.dataEntrySetList.size();
	}

	@Override
	protected void elementClicked(int var1, boolean var2) {
		this.parent.selectDataEntrySetIndex(var1);
	}

	@Override
	protected boolean isSelected(int var1) {
		return this.parent.dataEntrySetIndexSelected(var1);
	}

	@Override
	protected void drawBackground() {
		this.parent.drawDefaultBackground();
	}

	@Override
	protected int getContentHeight() {
		return (this.getSize()) * 35 + 1;
	}

	@Override
	protected void drawSlot(int listIndex, int var2, int var3, int var4, Tessellator var5) {
		DataEntrySet des = this.dataEntrySetList.get(listIndex);
		this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(des.key, listWidth - 10), this.left + 3, var3 + 2, 0xFFFFFF);
		this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(des.value.getType().name(), listWidth - 10), this.left + 3, var3 + 12, 0xCCCCCC);
		this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(des.value.get().toString(), listWidth - 10), this.left + 3, var3 + 22, 0xCCCCCC);
	}
}
