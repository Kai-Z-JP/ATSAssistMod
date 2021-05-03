package jp.kaiz.atsassistmod.gui;

import cpw.mods.fml.client.config.GuiCheckBox;
import jp.kaiz.atsassistmod.ATSAssistBlock;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.block.GroundUnitType;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
import jp.kaiz.atsassistmod.controller.trainprotection.TrainProtectionType;
import jp.kaiz.atsassistmod.gui.parts.GuiOptionSliderTrainProtection;
import jp.kaiz.atsassistmod.gui.parts.GuiOptionSliderTrainState;
import jp.kaiz.atsassistmod.gui.parts.GuiScreenCustom;
import jp.kaiz.atsassistmod.network.PacketGroundUnitTile;
import jp.kaiz.atsassistmod.network.PacketGroundUnitTileInit;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

public class GUIGroundUnit extends GuiScreenCustom {

    private final TileEntityGroundUnit tile;

    public GUIGroundUnit(TileEntityGroundUnit tile) {
        this.tile = tile;
    }

    //文字の描画
    //横はthis.width
    //縦はthis.height
    //this.fontRendererObj.drawString("ここに文字", 横座標, 縦座標, 白なら0xffffff);
    @Override
    public void drawScreen(int mouseX, int mouseZ, float partialTick) {
        super.drawScreen(mouseX, mouseZ, partialTick);

        switch (this.tile.getType()) {
            case None:
                this.fontRendererObj.drawString(/*"地上子機能選択メニュー",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.title"),
                        this.width / 4, 20, 0xffffff);
                this.fontRendererObj.drawString(/*"ATC",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.text.0"),
                        this.width / 2 - 170, this.height / 2 - 90, 0xffffff);
                this.fontRendererObj.drawString(/*"TASC",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.text.1"),
                        this.width / 2 - 170, this.height / 2 - 50, 0xffffff);
                this.fontRendererObj.drawString(/*"ATO",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.text.2"),
                        this.width / 2 - 170, this.height / 2 + 15, 0xffffff);
                this.fontRendererObj.drawString(/*"保安装置",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.text.3"),
                        this.width / 2 - 170, this.height / 2 + 55, 0xffffff);
                this.fontRendererObj.drawString(/*"その他",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.text.4"),
                        this.width / 2 + 70, this.height / 2 + 55, 0xffffff);
                return;
            case ATC_SpeedLimit_Notice:
                this.fontRendererObj.drawString(/*"地上子機能 : ATC(ATO) : 速度制限予告",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.1.title"),
                        this.width / 4, 20, 0xffffff);
                this.fontRendererObj.drawString(/*"速度制限    (km/h)",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.1.text.0"),
                        this.width / 2 - 100, this.height / 2 - 25, 0xffffff);
                this.fontRendererObj.drawString(/*"制限開始の距離 (m)",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.1.text.1"),
                        this.width / 2 - 100, this.height / 2, 0xffffff);
                this.fontRendererObj.drawString(/*"自動的に減速する",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.1.text.2"),
                        this.width / 2 - 100, this.height / 2 + 25, 0xffffff);
                this.fontRendererObj.drawString(/*"距離基準を車両先頭に",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.6.text.1"),
                        this.width / 2 - 100, this.height / 2 + 50, 0xffffff);
                break;
            case ATC_SpeedLimit_Cancel:
                this.fontRendererObj.drawString(/*"地上子機能 : ATC(ATO) : 速度制限解除",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.2.title"),
                        this.width / 4, 20, 0xffffff);

                this.fontRendererObj.drawString(/*"編成最後尾で解除",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.2.text.0"),
                        this.width / 2 - 100, this.height / 2 - 25, 0xffffff);
                break;
            case TASC_StopPotion_Notice:
                this.fontRendererObj.drawString(/*"地上子機能 : TASC : 距離設定 制御開始",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.4.title"),
                        this.width / 4, 20, 0xffffff);
                this.fontRendererObj.drawString(/*"停車位置までの距離",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.4.text.0"),
                        this.width / 2 - 100, this.height / 2 - 25, 0xffffff);
                this.fontRendererObj.drawString(/*"距離基準を車両先頭に",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.4.text.1"),
                        this.width / 2 - 100, this.height / 2, 0xffffff);
                break;
            case TASC_Cancel:
                this.fontRendererObj.drawString(/*"地上子機能 : TASC : 制御終了",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.5.title"),
                        this.width / 4, 20, 0xffffff);
                break;
            case TASC_StopPotion_Correction:
                this.fontRendererObj.drawString(/*"地上子機能 : TASC : 距離補正",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.6.title"),
                        this.width / 4, 20, 0xffffff);
                this.fontRendererObj.drawString(/*"停車位置までの距離",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.6.text.0"),
                        this.width / 2 - 100, this.height / 2 - 25, 0xffffff);
                this.fontRendererObj.drawString(/*"距離基準を車両先頭に",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.6.text.1"),
                        this.width / 2 - 100, this.height / 2, 0xffffff);
                break;
            case TASC_StopPotion:
                this.fontRendererObj.drawString(/*"地上子機能 : 停車検知",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.7.title"),
                        this.width / 4, 20, 0xffffff);
                this.fontRendererObj.drawString(/*"逆転ハンドル前以外でも検知",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.7.text.0"),
                        this.width / 2 - 100, this.height / 2 - 50, 0xffffff);
                return;
            case ATO_Departure_Signal:
                this.fontRendererObj.drawString(/*"地上子機能 : ATO : 出発信号 制御開始",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.9.title"),
                        this.width / 4, 20, 0xffffff);
                this.fontRendererObj.drawString(/*"目標速度",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.9.text.0"),
                        this.width / 2 - 100, this.height / 2 - 25, 0xffffff);
                break;
            case ATO_Cancel:
                this.fontRendererObj.drawString(/*"地上子機能 : ATO : 制御終了",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.10.title"),
                        this.width / 4, 20, 0xffffff);
                break;
            case ATO_Change_Speed:
                this.fontRendererObj.drawString(/*"地上子機能 : ATO : 目標速度変更",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.11.title"),
                        this.width / 4, 20, 0xffffff);
                this.fontRendererObj.drawString(/*"目標速度",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.11.text.0"),
                        this.width / 2 - 100, this.height / 2 - 25, 0xffffff);
                break;
            case TrainState_Set:
                this.fontRendererObj.drawString(/*"地上子機能 : TrainState : 車両状態変更",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.13.title"),
                        this.width / 4, 20, 0xffffff);
                this.fontRendererObj.drawString(/*"逆転ハンドル前以外でも有効",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.13.text.0"),
                        this.width / 2 + 10, this.height / 2 - 45, 0xffffff);
                return;
            case CHANGE_TP:
                this.fontRendererObj.drawString(/*"地上子機能 : 保安装置 : 強制変更",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.14.title"),
                        this.width / 4, 20, 0xffffff);
                break;
            case ATACS_Disable:
                this.fontRendererObj.drawString(/*"地上子機能 : ATACS : 制御終了",*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.15.title"),
                        this.width / 4, 20, 0xffffff);
                break;
        }

        this.fontRendererObj.drawString(/*"レッドストーン連動",*/
                I18n.format("ATSAssistMod.gui.GroudUnitMenu.common.text.0"),
                this.width / 2 - 100, this.height / 2 - 50, 0xffffff);
    }

    //チェックボックスも
    //ボタンはここ
    //this.buttonList.add(new GuiButton(id,横座標,縦座標,横長さ,縦長さ,文字列))
    @Override
    public void initGui() {
        super.initGui();

        GroundUnitType type = this.tile.getType();
        switch (type) {
            case None:
                this.buttonList.addAll(Arrays.asList(
                        new GuiButton(1, this.width / 2 - 170, this.height / 2 - 75, 100, 20,/* "速度制限予告"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.button.1")
                        ),
                        new GuiButton(2, this.width / 2 - 50, this.height / 2 - 75, 100, 20, /*"速度制限解除"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.button.2")
                        ),
                        new GuiButton(4, this.width / 2 - 170, this.height / 2 - 35, 100, 20, /*"停車位置予告"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.button.4")
                        ),
                        new GuiButton(5, this.width / 2 - 50, this.height / 2 - 35, 100, 20, /*"制御終了"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.button.5")
                        ),
                        new GuiButton(6, this.width / 2 - 170, this.height / 2 - 10, 100, 20, /*"停車距離補正"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.button.6")
                        ),
                        new GuiButton(7, this.width / 2 - 50, this.height / 2 - 10, 100, 20, /*"停車検知"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.button.7")
                        ),
                        new GuiButton(9, this.width / 2 - 170, this.height / 2 + 30, 100, 20, /*"出発信号"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.button.9")
                        ),
                        new GuiButton(10, this.width / 2 - 50, this.height / 2 + 30, 100, 20, /*"制御終了"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.button.10")
                        ),
                        new GuiButton(11, this.width / 2 + 70, this.height / 2 + 30, 100, 20, /*"目標速度変更"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.button.11")
                        ),
                        new GuiButton(13, this.width / 2 + 70, this.height / 2 + 70, 100, 20, /*"列車データ変更"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.button.13")
                        ),
                        new GuiButton(14, this.width / 2 - 170, this.height / 2 + 70, 100, 20, /*"強制変更"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.button.14")
                        ),
                        new GuiButton(20, this.width / 2 - 50, this.height - 25, 100, 20, /*"キャンセル"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.0.button.20")
                        )));
                break;
            case ATC_SpeedLimit_Notice: {
                this.addDownCommon();
                this.addGuiTextField(0, String.valueOf(((TileEntityGroundUnit.Speed) tile).getSpeedLimit()), 3);
                this.addGuiTextField(1, String.valueOf(((TileEntityGroundUnit.Distance) tile).getDistance()), 5);
                this.buttonList.add(new GuiCheckBox(31, this.width / 2 + 45, this.height / 2 + 25, "", ((TileEntityGroundUnit.ATCSpeedLimitNotice) tile).isAutoBrake()));
                this.buttonList.add(new GuiCheckBox(31, this.width / 2 + 45, this.height / 2 + 50, "", ((TileEntityGroundUnit.TrainDistance) tile).isUseTrainDistance()));
                break;
            }
            case ATC_SpeedLimit_Cancel: {
                this.addDownCommon();
                this.buttonList.add(new GuiCheckBox(31, this.width / 2 + 45, this.height / 2 - 25, "", ((TileEntityGroundUnit.TrainDistance) tile).isUseTrainDistance()));
                break;
            }
            case TASC_StopPotion_Correction:
            case TASC_StopPotion_Notice: {
                this.addDownCommon();
                this.addGuiTextField(0, String.valueOf(((TileEntityGroundUnit.Distance) tile).getDistance()), 5);
                this.buttonList.add(new GuiCheckBox(31, this.width / 2 + 45, this.height / 2, "", ((TileEntityGroundUnit.TrainDistance) tile).isUseTrainDistance()));
                break;
            }
            case TASC_Cancel:
            case TASC_StopPotion:
            case ATO_Cancel:
            case ATACS_Disable: {
                this.addDownCommon();
                break;
            }
            case ATO_Departure_Signal:
            case ATO_Change_Speed: {
                this.addDownCommon();
                this.addGuiTextField(0, String.valueOf(((TileEntityGroundUnit.Speed) tile).getSpeedLimit()), 3);
                break;
            }
            case CHANGE_TP: {
                this.addDownCommon();
                this.buttonList.add(new GuiOptionSliderTrainProtection(this.width / 2 - 75, this.height / 2 - 25, ((TileEntityGroundUnit.ChangeTrainProtection) tile).getTPType()));
                break;
            }
            case TrainState_Set: {
                this.buttonList.add(new GuiCheckBox(100, this.width / 2 + 145, this.height / 2 - 45, "", tile.isLinkRedStone()));

                this.buttonList.addAll(Arrays.asList(
                        new GuiButton(0, this.width / 4 + 160, 15, 50, 20, /*"リセット"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.common.button.0")
                        ),
                        new GuiButton(21, this.width / 2 - 110, this.height - 30, 100, 20, /*"決定"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.common.button.21")
                        ),
                        new GuiButton(20, this.width / 2 + 10, this.height - 30, 100, 20, /*"キャンセル"*/
                                I18n.format("ATSAssistMod.gui.GroudUnitMenu.common.button.20")
                        )));

                for (int i = 0; i < 12; i++) {
                    if (i == 3) {
                        continue;
                    }
                    TileEntityGroundUnit.TrainStateSet tileTS = (TileEntityGroundUnit.TrainStateSet) this.tile;
                    this.buttonList.add(new GuiOptionSliderTrainState(this.width / 2 - 160 + 170 * (i % 2), this.height / 2 - 75 + 25 * (i / 2), i, tileTS.getStates()[i]));
                }
                break;
            }
        }
    }

    @Override
    public void keyTyped(char par1, int par2) {
        if (par2 == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
            return;
        }
        if ((par2 >= Keyboard.KEY_1 && par2 <= Keyboard.KEY_0) ||
                (par2 >= Keyboard.KEY_NUMPAD7 && par2 <= Keyboard.KEY_NUMPAD9) ||
                (par2 >= Keyboard.KEY_NUMPAD4 && par2 <= Keyboard.KEY_NUMPAD6) ||
                (par2 >= Keyboard.KEY_NUMPAD1 && par2 <= Keyboard.KEY_NUMPAD3) ||
                par2 == Keyboard.KEY_NUMPAD0 ||
                par2 == Keyboard.KEY_LEFT ||
                par2 == Keyboard.KEY_RIGHT ||
                par2 == Keyboard.KEY_BACK ||
                par2 == Keyboard.KEY_DELETE) {
            this.textFieldList.forEach(textField -> textField.textboxKeyTyped(par1, par2));
        }

        if (par2 == Keyboard.KEY_PERIOD ||
                par2 == Keyboard.KEY_DECIMAL) {
            this.textFieldList.stream().filter(textField -> textField.getMaxStringLength() == 5).forEach(textField -> textField.textboxKeyTyped(par1, par2));
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == 20) {
            this.mc.displayGuiScreen(null);
            return;
        } else if (button.id == 21) {
            boolean linkRedStone = ((GuiCheckBox) this.buttonList.get(0)).isChecked();
            PacketGroundUnitTile packet = null;
            switch (this.tile.getType()) {
                case TrainState_Set:
                    packet = new PacketGroundUnitTile(
                            this.tile,
                            linkRedStone,
                            this.getTrainStateBytes());
                    break;
                case ATC_SpeedLimit_Notice:
                    packet = new PacketGroundUnitTile(
                            this.tile,
                            linkRedStone,
                            this.getIntGuiTextFieldText(0),
                            this.getDoubleGuiTextFieldText(1),
                            ((GuiCheckBox) this.buttonList.get(4)).isChecked(),
                            ((GuiCheckBox) this.buttonList.get(5)).isChecked());
                    break;
                case ATC_SpeedLimit_Cancel:
                    packet = new PacketGroundUnitTile(
                            this.tile,
                            linkRedStone,
                            ((GuiCheckBox) this.buttonList.get(4)).isChecked());
                    break;
                case TASC_StopPotion_Notice:
                case TASC_StopPotion_Correction:
                    packet = new PacketGroundUnitTile(
                            this.tile,
                            linkRedStone,
                            this.getDoubleGuiTextFieldText(0),
                            ((GuiCheckBox) this.buttonList.get(4)).isChecked());
                    break;
                case ATO_Departure_Signal:
                case ATO_Change_Speed:
                    packet = new PacketGroundUnitTile(
                            this.tile,
                            linkRedStone,
                            this.getIntGuiTextFieldText(0));
                    break;
                case TASC_Cancel:
                case TASC_StopPotion:
                case ATO_Cancel:
                case ATACS_Disable:
                    packet = new PacketGroundUnitTile(
                            this.tile,
                            linkRedStone);
                    break;
                case CHANGE_TP:
                    packet = new PacketGroundUnitTile(
                            this.tile,
                            linkRedStone,
                            this.getTrainProtection());
                    break;
            }
            if (packet != null) {
                ATSAssistCore.NETWORK_WRAPPER.sendToServer(packet);
            }
            this.mc.displayGuiScreen(null);
            return;
        }

        if (button.id == 0 || this.tile.getType() == GroundUnitType.None) {
            this.sendPacket(button.id);
            this.mc.theWorld.setBlock(tile.xCoord, tile.yCoord, tile.zCoord, ATSAssistBlock.blockGroundUnit, button.id, 3);
        }
    }

    private byte[] getTrainStateBytes() {
        byte[] bytes = new byte[12];
        ((List<GuiButton>) this.buttonList).stream().filter(GuiOptionSliderTrainState.class::isInstance).filter(button -> button.id >= 100 && button.id <= 111).map(GuiOptionSliderTrainState.class::cast).forEach(slider -> bytes[slider.id - 100] = slider.nowValue);
        return bytes;
    }

    private TrainProtectionType getTrainProtection() {
        return ((List<GuiButton>) this.buttonList).stream().filter(GuiOptionSliderTrainProtection.class::isInstance).filter(button -> button.id == 100).findFirst().map(button -> ((GuiOptionSliderTrainProtection) button).nowValue).orElse(TrainProtectionType.NONE);
    }

    private void sendPacket(int id) {
        ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketGroundUnitTileInit(id, this.tile));
    }

    private void addDownCommon() {
        GuiCheckBox checkBox = new GuiCheckBox(100, this.width / 2 + 45, this.height / 2 - 50, "", false);
        checkBox.setIsChecked(tile.isLinkRedStone());
        this.buttonList.add(checkBox);

        this.buttonList.addAll(Arrays.asList(
                new GuiButton(0, this.width / 4 + 160, 15, 50, 20, /*"リセット"*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.common.button.0")),
                new GuiButton(21, this.width / 2 - 110, this.height - 30, 100, 20, /*"決定"*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.common.button.21")),
                new GuiButton(20, this.width / 2 + 10, this.height - 30, 100, 20, /*"キャンセル"*/
                        I18n.format("ATSAssistMod.gui.GroudUnitMenu.common.button.20")
                )));
    }

    private void addGuiTextField(int number, String str, int maxLength) {
        GuiTextField text = new GuiTextField(this.fontRendererObj, this.width / 2, this.height / 2 - 30 + (25 * number), 100, 20);
        text.setFocused(false);
        text.setMaxStringLength(maxLength);
        text.setText(str);
        this.textFieldList.add(text);
    }

    private int getIntGuiTextFieldText(int number) {
        String str = this.textFieldList.get(number).getText();
        int i = 0;
        if (str == null || str.equals("")) {
            return i;
        }

        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return i;
        }
        return i;
    }

    private double getDoubleGuiTextFieldText(int number) {
        String str = this.textFieldList.get(number).getText();
        double d = 0d;
        if (str == null || str.equals("")) {
            return d;
        }

        try {
            d = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return d;
        }
        return d;
    }
}
