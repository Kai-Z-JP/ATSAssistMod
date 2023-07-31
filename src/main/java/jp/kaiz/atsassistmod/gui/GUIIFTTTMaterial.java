package jp.kaiz.atsassistmod.gui;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityIFTTT;
import jp.kaiz.atsassistmod.gui.parts.*;
import jp.kaiz.atsassistmod.ifttt.IFTTTContainer;
import jp.kaiz.atsassistmod.ifttt.IFTTTContainer.This.Minecraft.RedStoneInput.ModeType;
import jp.kaiz.atsassistmod.ifttt.IFTTTContainer.This.RTM.SimpleDetectTrain.DetectMode;
import jp.kaiz.atsassistmod.ifttt.IFTTTType;
import jp.kaiz.atsassistmod.network.PacketIFTTT;
import jp.kaiz.atsassistmod.utils.ComparisonManager;
import jp.kaiz.atsassistmod.utils.KaizUtils;
import jp.ngt.rtm.modelpack.state.DataType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiCheckBox;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GUIIFTTTMaterial extends GuiScreen {
    private final List<GuiTextField> textFieldList = new ArrayList<>();
    private final TileEntityIFTTT tile;
    private IFTTTType.IFTTTEnumBase type = null;
    private IFTTTType.IFTTTEnumBase oldType = null;
    private IFTTTContainer ifcb = null;
    private int ifcbIndex = -1;

    public GUIIFTTTMaterial(TileEntityIFTTT tile) {
        this.tile = tile;
    }

    //文字の描画
    //横はthis.width
    //縦はthis.height
    //this.fontRenderer.drawString("ここに文字", 横座標, 縦座標, 白なら0xffffff);
    @Override
    public void drawScreen(int mouseX, int mouseZ, float partialTick) {
        super.drawDefaultBackground();
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        GL11.glPushMatrix();
        GL11.glTranslatef(1.0F, 1.0F, 1.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        int tw = 750 / 2, th = 422 / 2;

        if (this.type == null) {
            GL11.glPushMatrix();
            GL11.glTranslatef((this.width - tw) / 2f, (this.height - th) / 2f, 1.0F);
            GL11.glScalef(tw / 256f, tw / 256f, 1.0F);
            this.mc.getTextureManager().bindTexture(GuiTextureManager.IFTTTBaseLayer.texture);
            this.drawTexturedModalRect(0, 0, 0, 0, 256, 256);
            GL11.glPopMatrix();

            this.fontRenderer.drawStringWithShadow("AnyMatch",
                    this.width / 2 - this.fontRenderer.getStringWidth("AnyMatch") / 2 - 161,
                    this.height / 2 - this.fontRenderer.FONT_HEIGHT / 2 - 15,
                    0xffffff);
        } else {
            this.fontRenderer.drawString("IFTTT : " + (this.type.getId() < 200 ? "This" : "That") + " : " + this.type.getName(),
                    this.width / 4, 20, 0xffffff);
            switch (this.type.getId()) {
                case 100://IFTTTType.This.Select
                case 200://IFTTTType.That.Select
                    this.fontRenderer.drawString("Minecraft",
                            this.width / 2 - 170, this.height / 2 - 90, 0xffffff);
                    this.fontRenderer.drawString("RTM",
                            this.width / 2 - 170, this.height / 2 - 25, 0xffffff);
                    this.fontRenderer.drawString("ATSAssist",
                            this.width / 2 - 170, this.height / 2 + 40, 0xffffff);
                    break;
                case 110://RedStoneInput
                    this.fontRenderer.drawString("Input",
                            this.width / 2 - 50, this.height / 2 - 25, 0xffffff);
                    ModeType modeType = ((IFTTTContainer.This.Minecraft.RedStoneInput) this.ifcb).getMode();
                    ((List<GuiButton>) this.buttonList).stream().filter(button -> button.id == 1000).forEach(button -> button.displayString = ((IFTTTContainer.This.Minecraft.RedStoneInput) this.ifcb).getMode().name);
                    this.textFieldList.forEach(textField -> textField.setVisible(modeType.needStr));
                    break;
                case 120://単純列検
                    this.fontRenderer.drawString(I18n.format("ATSAssistMod.IFTTT.DetectMode.name"),
                            this.width / 2 - 75, this.height / 2 - 25, 0xffffff);
                    ((List<GuiButton>) this.buttonList).stream().filter(button -> button.id == 1000).forEach(button -> button.displayString = ((IFTTTContainer.This.RTM.SimpleDetectTrain) this.ifcb).getDetectMode().getDisplayName());
                    break;
                case 121://両数
                    this.fontRenderer.drawString("Cars",
                            this.width / 2 - 50, this.height / 2 - 25, 0xffffff);
                    ((List<GuiButton>) this.buttonList).stream().filter(button -> button.id == 1000).forEach(button -> button.displayString = ((IFTTTContainer.This.RTM.Cars) this.ifcb).getMode().getName());
                    break;
                case 122://速度
                    this.fontRenderer.drawString("Speed",
                            this.width / 2 - 50, this.height / 2 - 25, 0xffffff);
                    ((List<GuiButton>) this.buttonList).stream().filter(button -> button.id == 1000).forEach(button -> button.displayString = ((IFTTTContainer.This.RTM.Speed) this.ifcb).getMode().getName());
                    break;
                case 124://TrainDataMap
                    this.fontRenderer.drawString("DataType",
                            this.width / 2 - 50, this.height / 2 - 50, 0xffffff);
                    this.fontRenderer.drawString("Key",
                            this.width / 2 - 50, this.height / 2 - 25, 0xffffff);
                    this.fontRenderer.drawString("Value",
                            this.width / 2 - 50, this.height / 2, 0xffffff);
                    ((List<GuiButton>) this.buttonList).forEach(button -> {
                        if (button.id == 1000) {
                            button.displayString = ((IFTTTContainer.This.RTM.TrainDataMap) this.ifcb).getDataType().key;
                        } else if (button.id == 1001) {
                            button.displayString = ((IFTTTContainer.This.RTM.TrainDataMap) this.ifcb).getComparisonType().getName();
                        }
                    });

                    this.textFieldList.stream().filter(guiTextField -> guiTextField.y == this.height / 2 - 5).forEach(guiTextField -> guiTextField.setVisible(((Enum<?>) ((IFTTTContainer.This.RTM.TrainDataMap) this.ifcb).getComparisonType()).getDeclaringClass() != ComparisonManager.Boolean.class));
                    break;
                case 130://踏切障検
                    this.fontRenderer.drawString(I18n.format("ATSAssistMod.gui.IFTTTMaterial.130.0"),
                            this.width / 2 - 75, this.height / 2 - 25, 0xffffff);
                    this.fontRenderer.drawString(I18n.format("ATSAssistMod.gui.IFTTTMaterial.130.1"),
                            this.width / 2 - 75, this.height / 2, 0xffffff);
                    this.fontRenderer.drawString("X",
                            this.width / 2 - 40, this.height / 2 - 45, 0xffffff);
                    this.fontRenderer.drawString("Y",
                            this.width / 2 - 2, this.height / 2 - 45, 0xffffff);
                    this.fontRenderer.drawString("Z",
                            this.width / 2 + 33, this.height / 2 - 45, 0xffffff);
                    break;
                case 210://RedStoneOutput
                    this.fontRenderer.drawString(I18n.format("ATSAssistMod.gui.IFTTTMaterial.210.0"),
                            this.width / 2 - 50, this.height / 2 - 50, 0xffffff);
                    this.fontRenderer.drawString(I18n.format("ATSAssistMod.gui.IFTTTMaterial.210.1"),
                            this.width / 2 - 50, this.height / 2 - 25, 0xffffff);
                    ((List<GuiButton>) this.buttonList).stream().filter(button -> button.id == 1000).forEach(button -> button.displayString =
                            I18n.format("ATSAssistMod.gui.IFTTTMaterial.210.button." + (((IFTTTContainer.That.Minecraft.RedStoneOutput) this.ifcb).isTrainCarsOutput() ? "enable" : "disable")));
                    this.textFieldList.forEach(textField -> textField.setVisible(!((IFTTTContainer.That.Minecraft.RedStoneOutput) this.ifcb).isTrainCarsOutput()));
                    break;
                case 211://PlaySound
                    this.fontRenderer.drawString(I18n.format("ATSAssistMod.gui.IFTTTMaterial.211.0"),
                            this.width / 2 - 100, this.height / 2 - 75, 0xffffff);
                    this.fontRenderer.drawString(I18n.format("ATSAssistMod.gui.IFTTTMaterial.211.1"),
                            this.width / 2 - 100, this.height / 2 - 50, 0xffffff);
                    this.fontRenderer.drawString(I18n.format("ATSAssistMod.gui.IFTTTMaterial.211.2"),
                            this.width / 2 - 72, this.height / 2 - 25, 0xffffff);
                    this.fontRenderer.drawString("X",
                            this.width / 2 - 37, this.height / 2 - 25, 0xffffff);
                    this.fontRenderer.drawString("Y",
                            this.width / 2 - 2, this.height / 2 - 25, 0xffffff);
                    this.fontRenderer.drawString("Z",
                            this.width / 2 + 33, this.height / 2 - 25, 0xffffff);
                    ((List<GuiButton>) this.buttonList).stream().filter(button -> button.id == 1000).forEach(button -> ((GuiCheckBox) button).setIsChecked(this.ifcb.isOnce()));
                    break;
                case 212://ExecuteCommand
                    this.fontRenderer.drawString(I18n.format("ATSAssistMod.gui.IFTTTMaterial.212.0"),
                            this.width / 2 - 100, this.height / 2 - 75, 0xffffff);
                    this.fontRenderer.drawString(I18n.format("ATSAssistMod.gui.IFTTTMaterial.212.1"),
                            this.width / 2 - 100, this.height / 2 - 50, 0xffffff);
                    ((List<GuiButton>) this.buttonList).stream().filter(button -> button.id == 1000).forEach(button -> ((GuiCheckBox) button).setIsChecked(this.ifcb.isOnce()));
                    break;
                case 213://SetBlock
                    this.fontRenderer.drawString(I18n.format("ATSAssistMod.gui.IFTTTMaterial.213.0"), this.width / 2 - 100, this.height / 2 - 75, 0xffffff);
                    this.fontRenderer.drawString("x", this.width / 2 - 72, this.height / 2 - 50, 0xffffff);
                    this.fontRenderer.drawString("y", this.width / 2 - 37, this.height / 2 - 50, 0xffffff);
                    this.fontRenderer.drawString("z", this.width / 2 - 2, this.height / 2 - 50, 0xffffff);
                    this.fontRenderer.drawString("id", this.width / 2 + 30, this.height / 2 - 50, 0xffffff);
                    this.fontRenderer.drawString("meta", this.width / 2 + 62, this.height / 2 - 50, 0xffffff);
                    ((List<GuiButton>) this.buttonList).stream().filter(button -> button.id == 1000).forEach(button -> ((GuiCheckBox) button).setIsChecked(this.ifcb.isOnce()));
                    break;
                case 221://DataMap
                    this.fontRenderer.drawString("DataType",
                            this.width / 2 - 50, this.height / 2 - 50, 0xffffff);
                    this.fontRenderer.drawString("Key",
                            this.width / 2 - 50, this.height / 2 - 25, 0xffffff);
                    this.fontRenderer.drawString("Value",
                            this.width / 2 - 50, this.height / 2, 0xffffff);
                    ((List<GuiButton>) this.buttonList).stream().filter(button -> button.id == 1000).forEach(button -> button.displayString = ((IFTTTContainer.That.RTM.DataMap) this.ifcb).getDataType().key);
                    break;
                case 223://TrainSignal
                    this.fontRenderer.drawString("SignalLevel",
                            this.width / 2 - 50, this.height / 2 - 25, 0xffffff);
                    break;
                case 230://JS
                    this.fontRenderer.drawString("Script Name",
                            this.width / 2 - 100, this.height / 2 - 70, 0xffffff);
                    this.fontRenderer.drawString("Script Text",
                            this.width / 2 - 100, this.height / 2 - 35, 0xffffff);
            }
        }
        super.drawScreen(mouseX, mouseZ, partialTick);
        this.textFieldList.forEach(GuiTextField::drawTextBox);
        GL11.glPopMatrix();
    }

    private void addDetail(int baseButtonID, int widthBase, int heightBase, int number, IFTTTContainer ifcb, int size) {
        if (number >= 6) {
            return;
        }
        widthBase += number < 3 ? 95 * number : 95 * (number - 3);
        heightBase += number < 3 ? size < 3 ? 25 : 0 : 50;

        this.buttonList.add(
                new GuiDummyButtonIFTTTContainer(widthBase, heightBase, ifcb));
        this.buttonList.add(
                new GuiButtonEdit(baseButtonID + number, widthBase + 41, heightBase + 34));
        this.buttonList.add(
                new GuiButtonDelete(baseButtonID + number, widthBase + 65, heightBase + 34));
    }

    private void addAddButton(int baseButtonID, int widthBase, int heightBase, int number, int size) {
        if (number >= 6) {
            return;
        }
        widthBase += 95 * (number < 3 ? number : (number - 3));
        heightBase += number < 3 ? size < 3 ? 25 : 0 : 50;

        this.buttonList.add(
                new GuiButtonAdd(baseButtonID + number, widthBase, heightBase));
    }

    //チェックボックスも
    //ボタンはここ
    //this.buttonList.add(new GuiButton(id,横座標,縦座標,横長さ,縦長さ,文字列))
    @Override
    public void initGui() {
        super.initGui();
        super.buttonList.clear();
        this.textFieldList.clear();

        this.oldType = this.type;

        this.buttonList.add(new GuiButtonExit(90, this.width / 2 + 175, this.height / 2 - 101));
        if (this.type == null) {
            List<IFTTTContainer> thisList = this.tile.getThisList();
            int i0 = 0;
            int thisSize = thisList.size();
            if (!thisList.isEmpty()) {
                for (i0 = 0; i0 < thisList.size(); i0++) {
                    this.addDetail(100, this.width / 2 - 110, this.height / 2 - 100, i0, thisList.get(i0), thisSize);
                }
            }
            this.addAddButton(100, this.width / 2 - 73, this.height / 2 - 86, i0, thisSize);

            List<IFTTTContainer> thatList = this.tile.getThatList();
            int i1 = 0;
            int thatSize = thatList.size();
            if (!thatList.isEmpty()) {
                for (i1 = 0; i1 < thatList.size(); i1++) {
                    this.addDetail(200, this.width / 2 - 110, this.height / 2 + 5, i1, thatList.get(i1), thatSize);
                }
            }
            this.addAddButton(200, this.width / 2 - 73, this.height / 2 + 19, i1, thatSize);

            this.buttonList.add(new GuiCheckBox(300, this.width / 2 - 138, this.height / 2 - 20, "", this.tile.isAnyMatch()));
        } else {
            switch (this.type.getId()) {
                case 100: {//IFTTTType.This.Select
                    this.addSelectButton(IFTTTType.This.Minecraft.values(), this.width / 2 - 170, this.height / 2 - 75);
                    this.addSelectButton(IFTTTType.This.RTM.values(), this.width / 2 - 170, this.height / 2 - 10);
                    this.addSelectButton(IFTTTType.This.ATSAssist.values(), this.width / 2 - 170, this.height / 2 + 55);
                    this.buttonList.add(new GuiButton(990, this.width / 2 - 50, this.height - 25, 100, 20, I18n.format("ATSAssistMod.gui.IFTTTMaterial.common.button.990")));
                    break;
                }
                case 110: {//RedStoneInput
                    this.buttonList.add(new GuiButton(1000, this.width / 2 - 15, this.height / 2 - 30, 30, 20, ""));
                    this.addGuiTextField(((IFTTTContainer.This.Minecraft.RedStoneInput) this.ifcb).getValue(), this.width / 2 + 30, this.height / 2 - 30, 2, 30);
                    this.addDownCommon();
                    break;
                }
                case 120: {//単純列検
                    this.buttonList.add(new GuiButton(1000, this.width / 2 + 30, this.height / 2 - 30, 60, 20, ""));
                    this.addDownCommon();
                    break;
                }
                case 121: {//両数
                    this.buttonList.add(new GuiButton(1000, this.width / 2 - 15, this.height / 2 - 30, 30, 20, ""));
                    this.addGuiTextField(((IFTTTContainer.This.RTM.Cars) this.ifcb).getValue(), this.width / 2 + 30, this.height / 2 - 30, Byte.MAX_VALUE, 50);
                    this.addDownCommon();
                    break;
                }
                case 122: {//速度
                    this.buttonList.add(new GuiButton(1000, this.width / 2 - 15, this.height / 2 - 30, 30, 20, ""));
                    this.addGuiTextField(((IFTTTContainer.This.RTM.Speed) this.ifcb).getValue(), this.width / 2 + 30, this.height / 2 - 30, Byte.MAX_VALUE, 50);
                    this.addDownCommon();
                    break;
                }
                case 124: {//TrainDataMap
                    this.buttonList.add(new GuiButton(1000, this.width / 2 + 30, this.height / 2 - 55, 30, 20, ""));
                    this.buttonList.add(new GuiButton(1001, this.width / 2 - 15, this.height / 2 - 5, 30, 20, ""));
                    this.addGuiTextField(((IFTTTContainer.This.RTM.TrainDataMap) this.ifcb).getKey(), this.width / 2 + 30, this.height / 2 - 30, Byte.MAX_VALUE, 50);
                    this.addGuiTextField(((IFTTTContainer.This.RTM.TrainDataMap) this.ifcb).getValue(), this.width / 2 + 30, this.height / 2 - 5, Byte.MAX_VALUE, 50);
                    this.addDownCommon();
                    break;
                }
                case 130: {//踏切障検
                    this.addGuiTextField(((IFTTTContainer.This.ATSAssist.CrossingObstacleDetection) this.ifcb).getStartCC()[0], this.width / 2 - 50, this.height / 2 - 30, Byte.MAX_VALUE, 30);
                    this.addGuiTextField(((IFTTTContainer.This.ATSAssist.CrossingObstacleDetection) this.ifcb).getStartCC()[1], this.width / 2 - 15, this.height / 2 - 30, Byte.MAX_VALUE, 30);
                    this.addGuiTextField(((IFTTTContainer.This.ATSAssist.CrossingObstacleDetection) this.ifcb).getStartCC()[2], this.width / 2 + 20, this.height / 2 - 30, Byte.MAX_VALUE, 30);
                    this.addButton(new GuiButton(1000, this.width / 2 + 55, this.height / 2 - 30, 20, 20, "V"));
                    this.addGuiTextField(((IFTTTContainer.This.ATSAssist.CrossingObstacleDetection) this.ifcb).getEndCC()[0], this.width / 2 - 50, this.height / 2 - 5, Byte.MAX_VALUE, 30);
                    this.addGuiTextField(((IFTTTContainer.This.ATSAssist.CrossingObstacleDetection) this.ifcb).getEndCC()[1], this.width / 2 - 15, this.height / 2 - 5, Byte.MAX_VALUE, 30);
                    this.addGuiTextField(((IFTTTContainer.This.ATSAssist.CrossingObstacleDetection) this.ifcb).getEndCC()[2], this.width / 2 + 20, this.height / 2 - 5, Byte.MAX_VALUE, 30);
                    this.addButton(new GuiButton(1001, this.width / 2 + 55, this.height / 2 - 5, 20, 20, "V"));
                    this.addDownCommon();
                    break;
                }
                case 200: {//IFTTTType.This.Select
                    this.addSelectButton(IFTTTType.That.Minecraft.values(), this.width / 2 - 170, this.height / 2 - 75);
                    this.addSelectButton(IFTTTType.That.RTM.values(), this.width / 2 - 170, this.height / 2 - 10);
                    this.addSelectButton(IFTTTType.That.ATSAssist.values(), this.width / 2 - 170, this.height / 2 + 55);
                    this.buttonList.add(new GuiButton(990, this.width / 2 - 50, this.height - 25, 100, 20, I18n.format("ATSAssistMod.gui.IFTTTMaterial.common.button.990")));
                    break;
                }
                case 210: {//RedStoneOutput
                    this.buttonList.add(new GuiButton(1000, this.width / 2 + 30, this.height / 2 - 55, 30, 20, ""));
                    this.addGuiTextField(((IFTTTContainer.That.Minecraft.RedStoneOutput) this.ifcb).getOutputLevel(), this.width / 2 + 30, this.height / 2 - 30, Byte.MAX_VALUE, 50);
                    this.addDownCommon();
                    break;
                }
                case 211: {//PlaySound
                    this.buttonList.add(new GuiCheckBox(1000, this.width / 2 + 45, this.height / 2 - 80, "", false));
                    this.addGuiTextField(((IFTTTContainer.That.Minecraft.PlaySound) this.ifcb).getSoundName(), this.width / 2 - 50, this.height / 2 - 55, Byte.MAX_VALUE, 100);
                    this.addGuiTextField(((IFTTTContainer.That.Minecraft.PlaySound) this.ifcb).getRadius(), this.width / 2 - 85, this.height / 2 - 5, Byte.MAX_VALUE, 30);
                    this.addGuiTextField(((IFTTTContainer.That.Minecraft.PlaySound) this.ifcb).getPos()[0], this.width / 2 - 50, this.height / 2 - 5, Byte.MAX_VALUE, 30);
                    this.addGuiTextField(((IFTTTContainer.That.Minecraft.PlaySound) this.ifcb).getPos()[1], this.width / 2 - 15, this.height / 2 - 5, Byte.MAX_VALUE, 30);
                    this.addGuiTextField(((IFTTTContainer.That.Minecraft.PlaySound) this.ifcb).getPos()[2], this.width / 2 + 20, this.height / 2 - 5, Byte.MAX_VALUE, 30);
                    this.addButton(new GuiButton(1001, this.width / 2 + 55, this.height / 2 - 5, 20, 20, "V"));
                    this.addDownCommon();
                    break;
                }
                case 212: {//ExecuteCommand
                    this.buttonList.add(new GuiCheckBox(1000, this.width / 2 + 45, this.height / 2 - 80, "", false));
                    this.addGuiTextField(((IFTTTContainer.That.Minecraft.ExecuteCommand) this.ifcb).getCommand(), this.width / 2 - 100, this.height / 2 - 30, Byte.MAX_VALUE, 200);
                    this.addDownCommon();
                    break;
                }
                case 213: {//SetBlock
                    this.buttonList.add(new GuiCheckBox(1000, this.width / 2 + 45, this.height / 2 - 80, "", false));
                    IFTTTContainer.That.Minecraft.SetBlock ifcb = ((IFTTTContainer.That.Minecraft.SetBlock) this.ifcb);
                    int h = this.height / 2 - 30;
                    List<int[]> posList = ifcb.getPosList();
                    for (int i = 0, posListSize = posList.size(); i < posListSize; i++) {
                        int[] pos = posList.get(i);
                        int w = this.width / 2 - 85;
                        for (int n : pos) {
                            this.addGuiTextField(n, w, h, Byte.MAX_VALUE, 30);
                            w += 35;
                        }
                        if (posListSize < 5) {
                            this.buttonList.add(new GuiButton(2000 + i, w, h, 20, 20, "+"));
                        }
                        w += 25;
                        if (posListSize > 1) {
                            this.buttonList.add(new GuiButton(3000 + i, w, h, 20, 20, "-"));
                        }
                        w += 25;
                        this.buttonList.add(new GuiButton(4000 + i, w, h, 20, 20, "V"));
                        h += 25;
                    }
                    this.addDownCommon();
                    break;
                }
                case 221: {//DataMap
                    this.buttonList.add(new GuiButton(1000, this.width / 2 + 30, this.height / 2 - 55, 30, 20, ""));
                    this.addGuiTextField(((IFTTTContainer.That.RTM.DataMap) this.ifcb).getKey(), this.width / 2 + 30, this.height / 2 - 30, Byte.MAX_VALUE, 50);
                    this.addGuiTextField(((IFTTTContainer.That.RTM.DataMap) this.ifcb).getValue(), this.width / 2 + 30, this.height / 2 - 5, Byte.MAX_VALUE, 50);
                    this.addDownCommon();
                    break;
                }
                case 223: {//TrainSignal
                    this.addGuiTextField(((IFTTTContainer.That.RTM.TrainSignal) this.ifcb).getSignal(), this.width / 2 + 30, this.height / 2 - 30, 3, 50);
                    this.addDownCommon();
                    break;
                }
                case 230: {//JS
                    this.buttonList.add(new GuiButton(1000, this.width / 2 + 80, this.height / 2 - 25, 20, 20, "V"));
                    this.addGuiTextField(((IFTTTContainer.That.ATSAssist.JavaScript) this.ifcb).getScriptName(), this.width / 2 - 100, this.height / 2 - 60, Integer.MAX_VALUE - 1, 175);
                    this.addGuiTextField(((IFTTTContainer.That.ATSAssist.JavaScript) this.ifcb).getJSText(), this.width / 2 - 100, this.height / 2 - 25, Integer.MAX_VALUE - 1, 175);
                    this.addDownCommon();
                    break;
                }
            }
        }
    }

    private void addDownCommon() {
        this.buttonList.add(new GuiButton(91, this.width / 2 - 110, this.height - 30, 100, 20, I18n.format("ATSAssistMod.gui.IFTTTMaterial.common.button.91." + (this.ifcbIndex == -1 ? 0 : 1))));
        this.buttonList.add(new GuiButton(990, this.width / 2 + 10, this.height - 30, 100, 20, I18n.format("ATSAssistMod.gui.IFTTTMaterial.common.button.990")));
    }

    private void addSelectButton(IFTTTType.IFTTTEnumBase[] e, int baseWidth, int baseHeight) {
        List<IFTTTType.IFTTTEnumBase> eList = Arrays.asList(e);
        for (int i = 0; i < e.length; i++) {
            GuiButton guiButton = null;
            if (i < 3) {
                guiButton = new GuiButton(eList.get(i).getId(), baseWidth + 120 * i, baseHeight, 100, 20, eList.get(i).getName());
            } else if (i < 6) {
                guiButton = new GuiButton(eList.get(i).getId(), baseWidth + 120 * (i - 3), baseHeight + 25, 100, 20, eList.get(i).getName());
            }
            this.buttonList.add(guiButton);
        }
    }

    private void addGuiTextField(Object str, int xPosition, int yPosition, int maxLength, int width) {
        GuiTextField text = new GuiTextField(0, this.fontRenderer, xPosition, yPosition, width, 20);
        text.setFocused(false);
        text.setMaxStringLength(maxLength);
        text.setText(String.valueOf(str));
        this.textFieldList.add(text);
    }

    public int getTextFieldInt(int number) {
        int i = 0;
        try {
            i = Integer.parseInt(this.getTextFieldText(number));
        } catch (NumberFormatException ignored) {
        }
        return i;
    }

    public String getTextFieldText(int number) {
        return StringUtils.defaultString(this.textFieldList.get(number).getText());
    }

    public int textFieldLength() {
        return this.textFieldList.size();
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
        if (this.oldType != this.type) {
            this.initGui();
        }
    }

    @Override
    public void keyTyped(char par1, int par2) {
        if (par2 == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
            return;
        } else if (par2 == Keyboard.KEY_TAB) {
            for (int i = 0; i < this.textFieldList.size(); i++) {
                GuiTextField textField = this.textFieldList.get(i);
                if (textField.isFocused()) {
                    textField.setFocused(false);
                    int next = i == this.textFieldList.size() - 1 ? 0 : i + 1;
                    this.textFieldList.get(next).setFocused(true);
                    break;
                }
            }
            return;
        }
        this.textFieldList.forEach(textField -> textField.textboxKeyTyped(par1, par2));
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id >= 100 && button.id < 106) {
            if (this.tile.getThisList().size() > button.id - 100) {
                if (button instanceof GuiButtonDelete) {
                    ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketIFTTT(this.tile, this.tile.getThisList().get(button.id - 100), button.id - 100, 2));
                    this.tile.getThisList().remove(button.id - 100);
                    this.initGui();
                    return;
                } else {
                    this.type = (this.ifcb = SerializationUtils.clone(this.tile.getThisList().get(button.id - 100))).getType();
                }
            } else {
                this.type = IFTTTType.This.Select;
            }
            this.ifcbIndex = button.id - 100;
        } else if (button.id >= 200 && button.id < 206) {
            if (this.tile.getThatList().size() > button.id - 200) {
                if (button instanceof GuiButtonDelete) {
                    ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketIFTTT(this.tile, this.tile.getThatList().get(button.id - 200), button.id - 200, 2));
                    this.tile.getThatList().remove(button.id - 200);
                    this.initGui();
                    return;
                } else {
                    this.type = (this.ifcb = SerializationUtils.clone(this.tile.getThatList().get(button.id - 200))).getType();
                }
            } else {
                this.type = IFTTTType.That.Select;
            }
            this.ifcbIndex = button.id - 200;
        } else if (button.id >= 110 && button.id < 300) {
            IFTTTContainer ifc;
            switch (button.id) {
                case 110:
                    ifc = new IFTTTContainer.This.Minecraft.RedStoneInput();
                    break;
                case 120:
                    ifc = new IFTTTContainer.This.RTM.SimpleDetectTrain();
                    break;
                case 121:
                    ifc = new IFTTTContainer.This.RTM.Cars();
                    break;
                case 122:
                    ifc = new IFTTTContainer.This.RTM.Speed();
                    break;
                case 124:
                    ifc = new IFTTTContainer.This.RTM.TrainDataMap();
                    break;
                case 130:
                    ifc = new IFTTTContainer.This.ATSAssist.CrossingObstacleDetection();
                    break;
                case 210:
                    ifc = new IFTTTContainer.That.Minecraft.RedStoneOutput();
                    break;
                case 211:
                    ifc = new IFTTTContainer.That.Minecraft.PlaySound(tile);
                    break;
                case 212:
                    ifc = new IFTTTContainer.That.Minecraft.ExecuteCommand();
                    break;
                case 213:
                    ifc = new IFTTTContainer.That.Minecraft.SetBlock();
                    break;
                case 221:
                    ifc = new IFTTTContainer.That.RTM.DataMap();
                    break;
                case 223:
                    ifc = new IFTTTContainer.That.RTM.TrainSignal();
                    break;
                case 230:
                    ifc = new IFTTTContainer.That.ATSAssist.JavaScript();
                    break;
                default:
                    ifc = null;
            }
            if (ifc != null) {
                this.changeIFC(ifc);
            }
        } else if (button.id == 300) {
            this.tile.setAnyMatch(!this.tile.isAnyMatch());
            ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketIFTTT(this.tile, this.ifcb, this.ifcbIndex, 3));
        } else if (button.id >= 1000) {
            int[] pos = this.getPosFromClipboard();
            if (this.type != null) {
                switch (this.type.getId()) {
                    case 110:
                        switch (button.id) {
                            case 1000:
                                ModeType modeType = ((IFTTTContainer.This.Minecraft.RedStoneInput) this.ifcb).getMode();
                                ((IFTTTContainer.This.Minecraft.RedStoneInput) this.ifcb).setMode((ModeType) KaizUtils.getNextEnum(modeType));
                                break;
                        }
                        break;
                    case 120:
                        switch (button.id) {
                            case 1000:
                                DetectMode modeType = ((IFTTTContainer.This.RTM.SimpleDetectTrain) this.ifcb).getDetectMode();
                                ((IFTTTContainer.This.RTM.SimpleDetectTrain) this.ifcb).setDetectMode((DetectMode) KaizUtils.getNextEnum(modeType));
                                break;
                        }
                        break;
                    case 121:
                        switch (button.id) {
                            case 1000:
                                ComparisonManager.Integer modeType = ((IFTTTContainer.This.RTM.Cars) this.ifcb).getMode();
                                ((IFTTTContainer.This.RTM.Cars) this.ifcb).setMode((ComparisonManager.Integer) KaizUtils.getNextEnum(modeType));
                                break;
                        }
                        break;
                    case 122:
                        switch (button.id) {
                            case 1000:
                                ComparisonManager.Integer modeType = ((IFTTTContainer.This.RTM.Speed) this.ifcb).getMode();
                                ((IFTTTContainer.This.RTM.Speed) this.ifcb).setMode(
                                        modeType == ComparisonManager.Integer.GREATER_EQUAL ? ComparisonManager.Integer.LESS_EQUAL : ComparisonManager.Integer.GREATER_EQUAL);
                                break;
                        }
                        break;
                    case 124:
                        switch (button.id) {
                            case 1000:
                                ((IFTTTContainer.This.RTM.TrainDataMap) this.ifcb).nextDataType();
                                break;
                            case 1001:
                                ((IFTTTContainer.This.RTM.TrainDataMap) this.ifcb).nextComparisonType();
                                break;
                        }
                        break;
                    case 130:
                        switch (button.id) {
                            case 1000:
                                if (pos != null) {
                                    ((IFTTTContainer.This.ATSAssist.CrossingObstacleDetection) this.ifcb).setStartCC(pos[0], pos[1], pos[2]);
                                }
                                break;
                            case 1001:
                                if (pos != null) {
                                    ((IFTTTContainer.This.ATSAssist.CrossingObstacleDetection) this.ifcb).setEndCC(pos[0], pos[1], pos[2]);
                                }
                                break;
                        }
                        break;
                    case 210:
                        switch (button.id) {
                            case 1000:
                                ((IFTTTContainer.That.Minecraft.RedStoneOutput) this.ifcb).
                                        setTrainCarsOutput(!((IFTTTContainer.That.Minecraft.RedStoneOutput) this.ifcb).isTrainCarsOutput());
                                return;
                        }
                        break;
                    case 211:
                        switch (button.id) {
                            case 1001:
                                if (pos != null) {
                                    ((IFTTTContainer.That.Minecraft.PlaySound) this.ifcb).setPos(pos[0], pos[1], pos[2]);
                                }
                                break;
                        }
                    case 212:
                        switch (button.id) {
                            case 1000:
                                this.ifcb.setOnce(((GuiCheckBox) button).isChecked());
                                return;
                        }
                        break;
                    case 213: {
                        switch (button.id) {
                            case 1000:
                                this.ifcb.setOnce(((GuiCheckBox) button).isChecked());
                                return;
                        }
                        List<int[]> posList = IntStream.range(0, this.textFieldList.size() / 5)
                                .mapToObj(i -> this.textFieldList.subList(i * 5, Math.min((i + 1) * 5, this.textFieldList.size())).stream().map(GuiTextField::getText).mapToInt(Integer::parseInt).toArray())
                                .collect(Collectors.toList());
                        IntStream.range(0, posList.size()).forEach(i -> ((IFTTTContainer.That.Minecraft.SetBlock) this.ifcb).setPos(posList.get(i), i));
                        if (button.id >= 2000 && button.id < 3000) {
                            ((IFTTTContainer.That.Minecraft.SetBlock) this.ifcb).addPos(new int[]{0, 0, 0, 0, 0}, button.id - 2000);
                        } else if (button.id >= 3000 && button.id < 4000) {
                            ((IFTTTContainer.That.Minecraft.SetBlock) this.ifcb).removePos(button.id - 3000);
                        } else if (button.id >= 4000 && button.id < 5000) {
                            int idx = button.id - 4000;
                            if (pos != null) {
                                this.textFieldList.get(idx * 5).setText(String.valueOf(pos[0]));
                                this.textFieldList.get(idx * 5 + 1).setText(String.valueOf(pos[1]));
                                this.textFieldList.get(idx * 5 + 2).setText(String.valueOf(pos[2]));
                                break;
                            }
                        } else {
                            break;
                        }
                        this.oldType = null;

                        break;
                    }
                    case 221:
                        switch (button.id) {
                            case 1000:
                                DataType dataType = ((IFTTTContainer.That.RTM.DataMap) this.ifcb).getDataType();
                                ((IFTTTContainer.That.RTM.DataMap) this.ifcb).setDataType((DataType) KaizUtils.getNextEnum(dataType));
                                break;
                        }
                        break;
                    case 230:
                        switch (button.id) {
                            case 1000:
                                String clipBoard = GuiScreen.getClipboardString();
                                ((IFTTTContainer.That.ATSAssist.JavaScript) this.ifcb).setJSText(clipBoard);
                                this.textFieldList.get(1).setText(clipBoard);
                                break;
                        }
                        break;
                }
            }
        } else {
            switch (button.id) {
                case 990://MainMenu
                    this.type = IFTTTType.getType(button.id);
                    break;
                case 90:
                    this.mc.displayGuiScreen(null);
                    break;
                case 91:
                    this.ifcb.setFromGui(this);
                    ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketIFTTT(this.tile, this.ifcb, this.ifcbIndex, 0));
                    this.mc.displayGuiScreen(null);
                    break;
            }
        }
    }

    private int[] getPosFromClipboard() {
        String clipboardText = GuiScreen.getClipboardString();
        return clipboardText.matches("^-?(\\d+ *,? +){2}-?\\d+$") ? Arrays.stream(clipboardText.split(" *,? +")).mapToInt(Integer::parseInt).toArray() : null;
    }

    private void changeIFC(IFTTTContainer ifc) {
        this.type = (this.ifcb = ifc).getType();
        this.ifcbIndex = -1;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
