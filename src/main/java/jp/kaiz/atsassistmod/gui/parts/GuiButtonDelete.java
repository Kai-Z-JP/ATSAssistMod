package jp.kaiz.atsassistmod.gui.parts;

import jp.kaiz.atsassistmod.gui.GuiTextureManager;

public class GuiButtonDelete extends GuiSmallButtonBase {

	public GuiButtonDelete(int id, int xPos, int yPos) {
		super(id, xPos, yPos, "Del", GuiTextureManager.DeleteButton.texture);
	}
}
