package jp.kaiz.atsassistmod.gui;

import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.api.TrainControllerClient;
import jp.kaiz.atsassistmod.api.TrainControllerClientManager;
import jp.kaiz.atsassistmod.controller.trainprotection.TrainProtectionType;
import jp.kaiz.atsassistmod.network.PacketTrainProtectionSetter;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class GUITrainProtectionSelector extends GuiScreen {
    private final List<TrainProtectionType> validTPList = new ArrayList<>();
    private TrainControllerClient tcc;
    private final EntityPlayer player;

    public GUITrainProtectionSelector(EntityPlayer player) {
        this.player = player;
        EntityTrainBase train = (EntityTrainBase) player.ridingEntity;
        this.tcc = TrainControllerClientManager.getTCC(train);
        String tpList = train.getResourceState().dataMap.getString("ATSAssist_TP");
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
        this.fontRendererObj.drawStringWithShadow("運転切替", widthBaseL + 20, heightBase - 25, 0xffffff);
        this.fontRendererObj.drawStringWithShadow("手動", widthBaseL, heightBase, 0xffffff);
        this.fontRendererObj.drawStringWithShadow("TASC", widthBaseL, heightBase + 25, 0xffffff);
        this.fontRendererObj.drawStringWithShadow("TASC/ATO", widthBaseL, heightBase + 50, 0xffffff);

        //保安装置
        this.fontRendererObj.drawStringWithShadow("保安装置切替", widthBaseR0 + 50, heightBase - 25, 0xffffff);
        //開放
        this.fontRendererObj.drawStringWithShadow(TrainProtectionType.NONE.name, widthBaseR0, heightBase, 0xffffff);
        //開放
        this.fontRendererObj.drawStringWithShadow(TrainProtectionType.STATION_PREMISES.name, widthBaseR0, heightBase + 25, 0xffffff);
        for (TrainProtectionType type : this.validTPList) {
            this.fontRendererObj.drawStringWithShadow(type.name, widthBaseR1, heightBase, 0xffffff);
            heightBase = heightBase + 25;
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

        if (this.tcc == null) {
            this.buttonList.add(new GuiButton(10, widthBaseL, heightBase, 20, 20, "X"));
            this.buttonList.add(new GuiButton(11, widthBaseL, heightBase + 25, 20, 20, ""));
            this.buttonList.add(new GuiButton(12, widthBaseL, heightBase + 50, 20, 20, ""));

            int buttonIDL = 20;
            this.buttonList.add(new GuiButton(buttonIDL++, widthBaseR0, heightBase, 20, 20, "X"));
            this.buttonList.add(new GuiButton(buttonIDL++, widthBaseR0, heightBase + 25, 20, 20, ""));

            int buttonIDR = 30;
            for (TrainProtectionType type : this.validTPList) {
                this.buttonList.add(new GuiButton(buttonIDR++, widthBaseR1, heightBase, 20, 20, ""));
                heightBase = heightBase + 25;
            }
            return;
        }

        if (this.tcc.isATO()) {
            this.buttonList.add(new GuiButton(10, widthBaseL, heightBase, 20, 20, ""));
            this.buttonList.add(new GuiButton(11, widthBaseL, heightBase + 25, 20, 20, ""));
            this.buttonList.add(new GuiButton(12, widthBaseL, heightBase + 50, 20, 20, "X"));
        } else {
            if (this.tcc.isTASC()) {
                this.buttonList.add(new GuiButton(10, widthBaseL, heightBase, 20, 20, ""));
                this.buttonList.add(new GuiButton(11, widthBaseL, heightBase + 25, 20, 20, "X"));
                this.buttonList.add(new GuiButton(12, widthBaseL, heightBase + 50, 20, 20, ""));
            } else {
                this.buttonList.add(new GuiButton(10, widthBaseL, heightBase, 20, 20, "X"));
                this.buttonList.add(new GuiButton(11, widthBaseL, heightBase + 25, 20, 20, ""));
                this.buttonList.add(new GuiButton(12, widthBaseL, heightBase + 50, 20, 20, ""));
            }
        }

        int buttonIDL = 20;
        this.buttonList.add(this.getTrainProtectionButton(TrainProtectionType.NONE, buttonIDL++, widthBaseR0, heightBase));
        this.buttonList.add(this.getTrainProtectionButton(TrainProtectionType.STATION_PREMISES, buttonIDL++, widthBaseR0, heightBase + 25));

        int buttonIDR = 30;
        for (TrainProtectionType type : this.validTPList) {
            this.buttonList.add(this.getTrainProtectionButton(type, buttonIDR++, widthBaseR1, heightBase));
            heightBase = heightBase + 25;
        }
    }

    private GuiButton getTrainProtectionButton(TrainProtectionType type, int id, int width, int height) {
        return type == this.tcc.getTrainProtectionType() ? new GuiButton(id, width, height, 20, 20, "X") : new GuiButton(id, width, height, 20, 20, "");
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void mouseClicked(int x, int y, int btn) {
        super.mouseClicked(x, y, btn);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.displayString.isEmpty()) {
            switch (button.id) {
                case 10:
                    break;
                case 11:
                    break;
                case 12:
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
            }
        }
    }

    private void sendPacketTrainProtection(TrainProtectionType type) {
        if (this.tcc == null) {
            this.tcc = new TrainControllerClient();
        }
        this.tcc.setTrainProtectionType(type);
        this.player.openGui(ATSAssistCore.INSTANCE, ATSAssistCore.guiId_TrainProtectionSelector,
                this.player.worldObj,
                MathHelper.ceiling_double_int(this.player.posX),
                MathHelper.ceiling_double_int(this.player.posY),
                MathHelper.ceiling_double_int(this.player.posZ));
        ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketTrainProtectionSetter(type));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
