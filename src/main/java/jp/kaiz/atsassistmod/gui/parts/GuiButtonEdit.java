package jp.kaiz.atsassistmod.gui.parts;

import jp.kaiz.atsassistmod.gui.GuiTextureManager;

public class GuiButtonEdit extends GuiSmallButtonBase {

    public GuiButtonEdit(int id, int xPos, int yPos) {
        super(id, xPos, yPos, "Edit", GuiTextureManager.EditButton.texture);
    }
}
