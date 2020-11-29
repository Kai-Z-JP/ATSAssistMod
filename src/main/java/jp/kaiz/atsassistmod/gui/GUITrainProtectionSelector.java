package jp.kaiz.atsassistmod.gui;

import cpw.mods.fml.client.config.GuiCheckBox;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.api.TrainControllerClient;
import jp.kaiz.atsassistmod.api.TrainControllerClientManager;
import jp.kaiz.atsassistmod.controller.trainprotection.TrainProtectionType;
import jp.kaiz.atsassistmod.gui.parts.GuiScreenCustom;
import jp.kaiz.atsassistmod.network.PacketTrainDriveMode;
import jp.kaiz.atsassistmod.network.PacketTrainProtectionSetter;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
import java.util.List;

public class GUITrainProtectionSelector extends GuiScreenCustom {
    private final List<TrainProtectionType> validTPList = new ArrayList<>();
    private TrainControllerClient tcc;
    private final EntityTrainBase train;

    public GUITrainProtectionSelector() {
        this.train = (EntityTrainBase) Minecraft.getMinecraft().thePlayer.ridingEntity;
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
        int widthBaseL = this.width / 2 - 130;
        int widthBaseR0 = this.width / 2 - 10;
        int widthBaseR1 = this.width / 2 + 80;

        //TASC/ATO
        this.fontRendererObj.drawStringWithShadow(/*"運転切替"*/I18n.format("ATSAssistMod.gui.TrainProtectionSelector.text.0"), widthBaseL + 20, heightBase - 25, 0xffffff);
        this.fontRendererObj.drawStringWithShadow(/*"手動"*/I18n.format("ATSAssistMod.gui.TrainProtectionSelector.text.1"), widthBaseL, heightBase, 0xffffff);
        this.fontRendererObj.drawStringWithShadow(/*"TASC"*/I18n.format("ATSAssistMod.gui.TrainProtectionSelector.text.2"), widthBaseL, heightBase + 25, 0xffffff);
        this.fontRendererObj.drawStringWithShadow(/*"TASC/ATO"*/I18n.format("ATSAssistMod.gui.TrainProtectionSelector.text.3"), widthBaseL, heightBase + 50, 0xffffff);

        this.fontRendererObj.drawStringWithShadow(/*"HUD非表示"*/I18n.format("ATSAssistMod.gui.TrainProtectionSelector.text.4"), widthBaseL, heightBase + 100, 0xffffff);

        //保安装置
        this.fontRendererObj.drawStringWithShadow(/*"保安装置切替"*/I18n.format("ATSAssistMod.gui.TrainProtectionSelector.text.5"), widthBaseR0 + 50, heightBase - 25, 0xffffff);
        //開放
        this.fontRendererObj.drawStringWithShadow(TrainProtectionType.NONE.getDisplayName(), widthBaseR0, heightBase, 0xffffff);
        //開放
        this.fontRendererObj.drawStringWithShadow(TrainProtectionType.STATION_PREMISES.getDisplayName(), widthBaseR0, heightBase + 25, 0xffffff);
        for (TrainProtectionType type : this.validTPList) {
            this.fontRendererObj.drawStringWithShadow(type.getDisplayName(), widthBaseR1, heightBase, 0xffffff);
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
        GuiCheckBox checkBox0 = new GuiCheckBox(100, widthBaseL + 3, heightBase + 103, "", false);
        checkBox0.setIsChecked(this.tcc != null && this.tcc.isDontShowHUD());

        this.buttonList.add(checkBox0);

        this.buttonList.add(new GuiButton(10, widthBaseL, heightBase, 20, 20, ""));
        this.buttonList.add(new GuiButton(11, widthBaseL, heightBase + 25, 20, 20, ""));
        this.buttonList.add(new GuiButton(12, widthBaseL, heightBase + 50, 20, 20, ""));

        int buttonIDL = 20;
        this.buttonList.add(new GuiButton(buttonIDL++, widthBaseR0, heightBase, 20, 20, ""));
        this.buttonList.add(new GuiButton(buttonIDL++, widthBaseR0, heightBase + 25, 20, 20, ""));

        int buttonIDR = 30;
        for (TrainProtectionType type : this.validTPList) {
            this.buttonList.add(new GuiButton(buttonIDR++, widthBaseR1, heightBase, 20, 20, ""));
            heightBase += 25;
        }
    }

    private GuiButton getTrainProtectionButton(TrainProtectionType type, int id, int width, int height) {
        return type == this.tcc.getTrainProtectionType() ? new GuiButton(id, width, height, 20, 20, "X") : new GuiButton(id, width, height, 20, 20, "");
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
                    this.tcc.setDontShowHUD(((GuiCheckBox) button).isChecked());
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
