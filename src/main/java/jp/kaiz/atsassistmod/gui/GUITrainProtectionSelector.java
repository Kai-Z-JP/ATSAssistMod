package jp.kaiz.atsassistmod.gui;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.api.TrainControllerClient;
import jp.kaiz.atsassistmod.api.TrainControllerClientManager;
import jp.kaiz.atsassistmod.controller.trainprotection.TrainProtectionType;
import jp.kaiz.atsassistmod.gui.parts.GuiScreenCustom;
import jp.kaiz.atsassistmod.network.PacketManualDrive;
import jp.kaiz.atsassistmod.network.PacketTrainDriveMode;
import jp.kaiz.atsassistmod.network.PacketTrainProtectionSetter;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiCheckBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUITrainProtectionSelector extends GuiScreenCustom {
    private final List<TrainProtectionType> validTPList = new ArrayList<>();
    private TrainControllerClient tcc;
    private final EntityTrainBase train;

    public GUITrainProtectionSelector() {
        this.train = (EntityTrainBase) Minecraft.getMinecraft().player.getRidingEntity();
        this.tcc = TrainControllerClientManager.getTCC(this.train);
        String tpList = this.train.getResourceState().getDataMap().getString("ATSAssist_TP");
        if (tpList.isEmpty()) {
            //順番考えてるからvaluesにするな
            this.validTPList.add(TrainProtectionType.ATACS);
            this.validTPList.add(TrainProtectionType.ATSPs);
            this.validTPList.add(TrainProtectionType.RATS);
            this.validTPList.add(TrainProtectionType.RnATS);
        } else {
            if (tpList.contains("ATACS")) {
                this.validTPList.add(TrainProtectionType.ATACS);
            }
            if (tpList.contains("ATS-Ps")) {
                this.validTPList.add(TrainProtectionType.ATSPs);
            }
            if (tpList.contains("R-ATS")) {
                this.validTPList.add(TrainProtectionType.RATS);
            }
            if (tpList.contains("Rn-ATS")) {
                this.validTPList.add(TrainProtectionType.RnATS);
            }
        }
    }

    //文字の描画
    //横はthis.width
    //縦はthis.height
    //this.fontRendererObj.drawString("ここに文字", 横座標, 縦座標, 白なら0xffffff);
    @Override
    public void drawScreen(int mouseX, int mouseZ, float partialTick) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseZ, partialTick);
        int heightBase = this.height / 2 - 50;
        int widthBaseL = this.width / 2 - 135;
        int widthBaseR0 = this.width / 2 - 10;
        int widthBaseR1 = this.width / 2 + 80;

        //TASC/ATO
        this.fontRenderer.drawStringWithShadow(/*"運転切替"*/I18n.format("ATSAssistMod.gui.TrainProtectionSelector.text.0"), widthBaseL + 20, heightBase - 25, 0xffffff);
        this.fontRenderer.drawStringWithShadow(/*"運転モード: "*/I18n.format("ATSAssistMod.gui.TrainProtectionSelector.text.7"), widthBaseL, heightBase, 0xffffff);
        this.fontRenderer.drawStringWithShadow(/*"((手動)/(TASC)/(TASC/ATO))"*/
                I18n.format("ATSAssistMod.gui.TrainProtectionSelector.text." + (this.tcc != null ? this.tcc.isATO() ? 3 : this.tcc.isTASC() ? 2 : 1 : 1)
                ), widthBaseL + 55, heightBase, 0xffffff);
        this.fontRenderer.drawStringWithShadow(/*手動運転固定*/I18n.format("ATSAssistMod.gui.TrainProtectionSelector.text.6"), widthBaseL, heightBase + 25, 0xffffff);

        this.fontRenderer.drawStringWithShadow(/*"HUD非表示"*/I18n.format("ATSAssistMod.gui.TrainProtectionSelector.text.4"), widthBaseL, heightBase + 100, 0xffffff);

        //保安装置
        this.fontRenderer.drawStringWithShadow(/*"保安装置切替"*/I18n.format("ATSAssistMod.gui.TrainProtectionSelector.text.5"), widthBaseR0 + 50, heightBase - 25, 0xffffff);
        //開放
        this.fontRenderer.drawStringWithShadow(TrainProtectionType.NONE.getDisplayName(), widthBaseR0, heightBase, 0xffffff);
        //開放
        this.fontRenderer.drawStringWithShadow(TrainProtectionType.STATION_PREMISES.getDisplayName(), widthBaseR0, heightBase + 25, 0xffffff);
        for (TrainProtectionType type : this.validTPList) {
            this.fontRenderer.drawStringWithShadow(type.getDisplayName(), widthBaseR1, heightBase, 0xffffff);
            heightBase = heightBase + 25;
        }

        for (GuiButton button : (List<GuiButton>) this.buttonList) {
            switch (button.id) {
                case 10:
                    button.displayString = this.tcc == null ? "X" : !this.tcc.isTASC() & !this.tcc.isATO() ? "X" : "";
                    break;
                case 11:
                    button.displayString = this.tcc == null ? "" : this.tcc.isTASC() & !this.tcc.isATO() ? "X" : "";
                    button.enabled = this.tcc != null && (this.tcc.isTASC() || this.tcc.isATO());
                    break;
                case 12:
                    button.displayString = this.tcc == null ? "" : this.tcc.isATO() ? "X" : "";
                    button.enabled = this.tcc != null && this.tcc.isATO();
                    break;
                case 20:
                    button.displayString = this.tcc == null ? "X" : this.tcc.getTrainProtectionType() == TrainProtectionType.NONE ? "X" : "";
                    break;
                case 21:
                    button.displayString = this.tcc == null ? "" : this.tcc.getTrainProtectionType() == TrainProtectionType.STATION_PREMISES ? "X" : "";
                    break;
                case 30:
                case 31:
                case 32:
                case 33:
                    button.displayString = this.tcc == null ? "" : this.tcc.getTrainProtectionType() == this.validTPList.get(button.id - 30) ? "X" : "";
                    break;
            }
        }
    }

    //チェックボックスも
    //ボタンはここ
    //this.buttonList.add(new GuiButton(id,横座標,縦座標,横長さ,縦長さ,文字列))
    @Override
    public void initGui() {
        super.buttonList.clear();
        super.initGui();
        int heightBase = this.height / 2 - 55;
        int widthBaseL = this.width / 2 - 80;
        int widthBaseR0 = this.width / 2 + 40;
        int widthBaseR1 = this.width / 2 + 130;

        this.buttonList.addAll(Arrays.asList(
                new GuiCheckBox(100, widthBaseL + 3, heightBase + 103, "", this.tcc != null && this.tcc.isNotShowHud()),
                new GuiCheckBox(101, widthBaseL + 3, heightBase + 28, "", this.tcc != null && this.tcc.isManualDrive())
        ));

        int buttonIDL = 20;
        this.buttonList.addAll(Arrays.asList(
                new GuiButton(buttonIDL++, widthBaseR0, heightBase, 20, 20, ""),
                new GuiButton(buttonIDL++, widthBaseR0, heightBase + 25, 20, 20, "")));

        int buttonIDR = 30;
        for (TrainProtectionType type : this.validTPList) {
            this.buttonList.add(new GuiButton(buttonIDR++, widthBaseR1, heightBase, 20, 20, ""));
            heightBase += 25;
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.displayString.isEmpty()) {
            switch (button.id) {
                case 10:
                case 11:
                case 12:
                    this.sendPacketTrainDrive(button.id);
                    break;
                case 20:
                    this.sendPacketTrainProtection(TrainProtectionType.NONE);
                    break;
                case 21:
                    this.sendPacketTrainProtection(TrainProtectionType.STATION_PREMISES);
                    break;
                case 30:
                case 31:
                case 32:
                case 33:
                    this.sendPacketTrainProtection(this.validTPList.get(button.id - 30));
                    break;
                case 100:
                    this.tcc = this.tcc == null ? TrainControllerClientManager.createTCC(this.train) : TrainControllerClientManager.getTCC(this.train);
                    this.tcc.setNotShowHud(((GuiCheckBox) button).isChecked());
                    break;
                case 101:
                    ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketManualDrive(((GuiCheckBox) button).isChecked()));
                    break;
            }
        }
    }

    private void sendPacketTrainProtection(TrainProtectionType type) {
        this.tcc = this.tcc == null ? TrainControllerClientManager.createTCC(this.train) : TrainControllerClientManager.getTCC(this.train);

        this.tcc.setTrainProtectionType(type);
        ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketTrainProtectionSetter(type));
    }

    private void sendPacketTrainDrive(int mode) {
        this.tcc = this.tcc == null ? TrainControllerClientManager.createTCC(this.train) : TrainControllerClientManager.getTCC(this.train);
        switch (mode) {
            case 10:
                this.tcc.setTASC(false);
            case 11:
                this.tcc.setATO(false);
            case 12:
                break;
        }
        ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketTrainDriveMode(mode - 10));
    }
}
