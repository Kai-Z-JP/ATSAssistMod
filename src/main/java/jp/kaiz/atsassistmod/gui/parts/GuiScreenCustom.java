package jp.kaiz.atsassistmod.gui.parts;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import java.util.ArrayList;
import java.util.List;

public class GuiScreenCustom extends GuiScreen {
    protected final List<GuiTextField> textFieldList = new ArrayList<>();

    @Override
    public void drawScreen(int mouseX, int mouseZ, float partialTick) {
        super.drawDefaultBackground();
        this.textFieldList.forEach(GuiTextField::drawTextBox);
        super.drawScreen(mouseX, mouseZ, partialTick);
    }

    @Override
    public void initGui() {
        super.initGui();

        super.buttonList.clear();
        this.textFieldList.clear();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.textFieldList.forEach(GuiTextField::updateCursorCounter);
    }

    @Override
    public void mouseClicked(int x, int y, int btn) {
        super.mouseClicked(x, y, btn);
        this.textFieldList.forEach(guiTextField -> guiTextField.mouseClicked(x, y, btn));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
