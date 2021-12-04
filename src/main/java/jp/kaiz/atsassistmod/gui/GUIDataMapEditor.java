package jp.kaiz.atsassistmod.gui;

import jp.kaiz.atsassistmod.utils.DataEntrySet;
import jp.ngt.rtm.modelpack.IResourceSelector;
import jp.ngt.rtm.modelpack.state.DataMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUIDataMapEditor extends GuiScreen {
    protected final List<GuiTextField> textFieldList = new ArrayList<>();
    private final IResourceSelector iModelSelector;
    private int selected = -1;
    private DataMapList dataMapList;
    private final ArrayList<DataEntrySet> dataEntrySetList = new ArrayList<>();
    private DataEntrySet selectedData;
    private int listWidth;

    public GUIDataMapEditor(IResourceSelector iModelSelector, DataMap dataMap) {
        this.iModelSelector = iModelSelector;
        dataMap.getEntries().forEach((s, dataEntry) -> this.dataEntrySetList.add(new DataEntrySet(s, dataEntry)));
    }

    //文字の描画
    //横はthis.width
    //縦はthis.height
    //this.fontRenderer.drawString("ここに文字", 横座標, 縦座標, 白なら0xffffff);
    @Override
    public void drawScreen(int mouseX, int mouseZ, float partialTick) {
        super.drawDefaultBackground();
        this.textFieldList.forEach(GuiTextField::drawTextBox);
        this.dataMapList.drawScreen(mouseX, mouseZ, partialTick);
        this.fontRenderer.drawStringWithShadow("DataMapEditor",
                this.width / 2 - 100, 20, 0xffffff);
        this.fontRenderer.drawStringWithShadow("Type: " + this.iModelSelector.getResourceState().type.name,
                this.width / 2 - 90, 30, 0xffffff);
        this.fontRenderer.drawStringWithShadow("Name: " + this.iModelSelector.getResourceState().getResourceName(),
                this.width / 2 - 90, 40, 0xffffff);
        super.drawScreen(mouseX, mouseZ, partialTick);
    }

    //チェックボックスも
    //ボタンはここ
    //this.buttonList.add(new GuiButton(id,横座標,縦座標,横長さ,縦長さ,文字列))
    @Override
    public void initGui() {
        super.initGui();
        super.buttonList.clear();
        this.textFieldList.clear();
//		for (DataEntrySet dataEntrySet : this.dataEntrySetList) {
//			this.listWidth = Math.max(listWidth, getfontRenderer().getStringWidth(dataEntrySet.key) + 10);
//			this.listWidth = Math.max(listWidth, getfontRenderer().getStringWidth(dataEntrySet.value.get().toString()) + 10);
//		}
//		this.listWidth = Math.min(listWidth, 150);
        this.listWidth = 100;
        this.dataMapList = new DataMapList(this, this.dataEntrySetList, this.listWidth);
        this.dataMapList.registerScrollButtons(this.buttonList, 7, 8);
    }

    @Override
    public void keyTyped(char par1, int par2) {
        if (par2 == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
            return;
        }
        this.textFieldList.forEach(textField -> textField.textboxKeyTyped(par1, par2));
    }

    @Override
    public void actionPerformed(GuiButton button) {

    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.textFieldList.forEach(GuiTextField::updateCursorCounter);
    }

    @Override
    public void mouseClicked(int x, int y, int btn) throws IOException {
        super.mouseClicked(x, y, btn);
        this.textFieldList.forEach(guiTextField -> guiTextField.mouseClicked(x, y, btn));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    Minecraft getMinecraftInstance() {
        return this.mc;
    }

    FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }


    /**
     * @param var1
     */
    public void selectDataEntrySetIndex(int var1) {
        this.selected = var1;
        if (var1 >= 0 && var1 <= this.dataEntrySetList.size()) {
            this.selectedData = this.dataEntrySetList.get(this.selected);
        } else {
            this.selectedData = null;
        }
    }

    public boolean dataEntrySetIndexSelected(int var1) {
        return var1 == this.selected;
    }
}
