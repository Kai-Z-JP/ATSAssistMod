package jp.kaiz.atsassistmod.gui;

import cpw.mods.fml.client.config.GuiCheckBox;
import jp.kaiz.atsassistmod.ATSAssistCore;
import jp.kaiz.atsassistmod.block.GroundUnitType;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
import jp.kaiz.atsassistmod.network.PacketGroundUnitTile;
import jp.kaiz.atsassistmod.network.PacketGroundUnitTileInit;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class GroundUnitGUI extends GuiScreen {

	private TileEntityGroundUnit tile;
	private List<GuiTextField> textFieldList = new ArrayList<>();

	public GroundUnitGUI(TileEntityGroundUnit tile) {
		this.tile = tile;
	}

	//文字の描画
	//横はthis.width
	//縦はthis.height
	//this.fontRendererObj.drawString("ここに文字", 横座標, 縦座標, 白なら0xffffff);
	@Override
	public void drawScreen(int mouseX, int mouseZ, float partialTick) {
		this.drawDefaultBackground();
		this.textFieldList.forEach(GuiTextField::drawTextBox);
		super.drawScreen(mouseX, mouseZ, partialTick);

		switch (this.tile.getType()) {
			case None:
				this.fontRendererObj.drawString("地上子機能選択メニュー",
						this.width / 4, 20, 0xffffff);
				this.fontRendererObj.drawString("ATC",
						this.width / 2 - 170, this.height / 2 - 90, 0xffffff);
				this.fontRendererObj.drawString("TASC",
						this.width / 2 - 170, this.height / 2 - 50, 0xffffff);
				this.fontRendererObj.drawString("ATO",
						this.width / 2 - 170, this.height / 2 + 15, 0xffffff);
				this.fontRendererObj.drawString("ATACS",
						this.width / 2 - 170, this.height / 2 + 55, 0xffffff);
				this.fontRendererObj.drawString("その他",
						this.width / 2 + 70, this.height / 2 + 55, 0xffffff);
				return;
			case ATC_SpeedLimit_Notice:
				this.fontRendererObj.drawString("地上子機能 : ATC(ATO) : 速度制限予告",
						this.width / 4, 20, 0xffffff);
				this.fontRendererObj.drawString("速度制限    (km/h)",
						this.width / 2 - 100, this.height / 2 - 25, 0xffffff);
				this.fontRendererObj.drawString("制限開始の距離 (m)",
						this.width / 2 - 100, this.height / 2, 0xffffff);
				break;
			case ATC_SpeedLimit_Cancel:
				this.fontRendererObj.drawString("地上子機能 : ATC(ATO) : 速度制限解除",
						this.width / 4, 20, 0xffffff);

				this.fontRendererObj.drawString("編成最後尾で解除",
						this.width / 2 - 100, this.height / 2 - 25, 0xffffff);
				break;
			case TASC_StopPotion_Notice:
				this.fontRendererObj.drawString("地上子機能 : TASC : 距離設定 制御開始",
						this.width / 4, 20, 0xffffff);
				this.fontRendererObj.drawString("停車位置までの距離",
						this.width / 2 - 100, this.height / 2 - 25, 0xffffff);
				break;
			case TASC_Cancel:
				this.fontRendererObj.drawString("地上子機能 : TASC : 制御終了",
						this.width / 4, 20, 0xffffff);
				break;
			case TASC_StopPotion_Correction:
				this.fontRendererObj.drawString("地上子機能 : TASC : 距離補正",
						this.width / 4, 20, 0xffffff);
				this.fontRendererObj.drawString("停車位置までの距離",
						this.width / 2 - 100, this.height / 2 - 25, 0xffffff);
				break;
			case TASC_StopPotion:
				this.fontRendererObj.drawString("地上子機能 : 停車検知",
						this.width / 4, 20, 0xffffff);

				this.fontRendererObj.drawString("逆転ハンドル前以外でも検知",
						this.width / 2 - 100, this.height / 2 - 50, 0xffffff);
				return;
			case ATO_Departure_Signal:
				this.fontRendererObj.drawString("地上子機能 : ATO : 出発信号 制御開始",
						this.width / 4, 20, 0xffffff);
				this.fontRendererObj.drawString("目標速度",
						this.width / 2 - 100, this.height / 2 - 25, 0xffffff);
				break;
			case ATO_Cancel:
				this.fontRendererObj.drawString("地上子機能 : ATO : 制御終了",
						this.width / 4, 20, 0xffffff);
				break;
			case ATO_Change_Speed:
				this.fontRendererObj.drawString("地上子機能 : ATO : 目標速度変更",
						this.width / 4, 20, 0xffffff);
				this.fontRendererObj.drawString("目標速度",
						this.width / 2 - 100, this.height / 2 - 25, 0xffffff);
				break;
			case TrainState_Set:
				this.fontRendererObj.drawString("地上子機能 : TrainState : 車両状態変更",
						this.width / 4, 20, 0xffffff);
				this.fontRendererObj.drawString("逆転ハンドル前以外でも有効",
						this.width / 2 + 10, this.height / 2 - 45, 0xffffff);
				return;
			case ATACS_Enable:
				this.fontRendererObj.drawString("地上子機能 : ATACS : 制御開始",
						this.width / 4, 20, 0xffffff);
				break;
			case ATACS_Disable:
				this.fontRendererObj.drawString("地上子機能 : ATACS : 制御終了",
						this.width / 4, 20, 0xffffff);
				break;
		}

		this.fontRendererObj.drawString("レッドストーン連動",
				this.width / 2 - 100, this.height / 2 - 50, 0xffffff);
	}

	//チェックボックスも
	//ボタンはここ
	//this.buttonList.add(new GuiButton(id,横座標,縦座標,横長さ,縦長さ,文字列))
	@Override
	public void initGui() {
		super.initGui();

		this.buttonList.clear();
		this.textFieldList.clear();
		GroundUnitType type = this.tile.getType();
		switch (type) {
			case None:
				this.buttonList.add(
						new GuiButton(1, this.width / 2 - 170, this.height / 2 - 75, 100, 20, "速度制限予告"));

				this.buttonList.add(
						new GuiButton(2, this.width / 2 - 50, this.height / 2 - 75, 100, 20, "速度制限解除"));


				this.buttonList.add(
						new GuiButton(4, this.width / 2 - 170, this.height / 2 - 35, 100, 20, "停車位置予告"));

				this.buttonList.add(
						new GuiButton(5, this.width / 2 - 50, this.height / 2 - 35, 100, 20, "制御終了"));

				this.buttonList.add(
						new GuiButton(6, this.width / 2 - 170, this.height / 2 - 10, 100, 20, "停車距離補正"));

				this.buttonList.add(
						new GuiButton(7, this.width / 2 - 50, this.height / 2 - 10, 100, 20, "停車検知"));


				this.buttonList.add(
						new GuiButton(9, this.width / 2 - 170, this.height / 2 + 30, 100, 20, "出発信号"));

				this.buttonList.add(
						new GuiButton(10, this.width / 2 - 50, this.height / 2 + 30, 100, 20, "制御終了"));

				this.buttonList.add(
						new GuiButton(11, this.width / 2 + 70, this.height / 2 + 30, 100, 20, "目標速度変更"));

				this.buttonList.add(
						new GuiButton(13, this.width / 2 + 70, this.height / 2 + 70, 100, 20, "列車データ変更"));

				this.buttonList.add(
						new GuiButton(14, this.width / 2 - 170, this.height / 2 + 70, 100, 20, "制御開始"));

				this.buttonList.add(
						new GuiButton(15, this.width / 2 - 50, this.height / 2 + 70, 100, 20, "制御終了"));

				this.buttonList.add(
						new GuiButton(20, this.width / 2 - 50, this.height - 25, 100, 20, "キャンセル"));
				break;
			case ATC_SpeedLimit_Notice:
				this.addDownCommon();
				this.addGuiTextField(0, String.valueOf(((TileEntityGroundUnit.Speed) tile).getSpeedLimit()), 3);
				this.addGuiTextField(1, String.valueOf(((TileEntityGroundUnit.Distance) tile).getDistance()), 5);
				break;
			case ATC_SpeedLimit_Cancel:
				this.addDownCommon();
				GuiCheckBox checkBox1 = new GuiCheckBox(31, this.width / 2 + 45, this.height / 2 - 25, "", false);
				checkBox1.setIsChecked(((TileEntityGroundUnit.ATCSpeedLimitCancel) tile).isLateCancel());
				this.buttonList.add(checkBox1);
				break;
			case TASC_StopPotion_Correction:
			case TASC_StopPotion_Notice:
				this.addDownCommon();
				this.addGuiTextField(0, String.valueOf(((TileEntityGroundUnit.Distance) tile).getDistance()), 5);
				break;
			case TASC_Cancel:
			case TASC_StopPotion:
			case ATO_Cancel:
			case ATACS_Enable:
			case ATACS_Disable:
				this.addDownCommon();
				break;
			case ATO_Departure_Signal:
			case ATO_Change_Speed:
				this.addDownCommon();
				this.addGuiTextField(0, String.valueOf(((TileEntityGroundUnit.Speed) tile).getSpeedLimit()), 3);
				break;
			case TrainState_Set:
				GuiCheckBox checkBox2 = new GuiCheckBox(100, this.width / 2 + 145, this.height / 2 - 45, "", false);
				checkBox2.setIsChecked(tile.isLinkRedStone());
				this.buttonList.add(checkBox2);

				this.buttonList.add(
						new GuiButton(0, this.width / 4 + 160, 15, 50, 20, "リセット"));

				this.buttonList.add(
						new GuiButton(21, this.width / 2 - 110, this.height - 30, 100, 20, "決定"));

				this.buttonList.add(
						new GuiButton(20, this.width / 2 + 10, this.height - 30, 100, 20, "キャンセル"));

				for (int i = 0; i < 12; i++) {
					if (i == 3) {
						continue;
					}
					TileEntityGroundUnit.TrainStateSet tileTS = (TileEntityGroundUnit.TrainStateSet) this.tile;
					this.buttonList.add(new GuiOptionSliderCustom(this.width / 2 - 160 + 170 * (i % 2), this.height / 2 - 75 + 25 * (i / 2), i, tileTS.getStates()[i]));
				}
				break;
		}
	}

	@Override
	public void keyTyped(char par1, int par2) {
		if (par2 == 1) {
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
			this.textFieldList.forEach(guiTextField -> guiTextField.textboxKeyTyped(par1, par2));
		}
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
	public void actionPerformed(GuiButton button) {
		if (button.id == 20) {
			this.mc.displayGuiScreen(null);
			return;
		} else if (button.id == 21) {
			boolean linkRedStone = ((GuiCheckBox) this.buttonList.get(0)).isChecked();
			switch (this.tile.getType()) {
				case TrainState_Set:
					ATSAssistCore.NETWORK_WRAPPER.sendToServer(
							new PacketGroundUnitTile(
									this.tile,
									linkRedStone,
									this.getTrainStateBytes()));
					break;
				case ATC_SpeedLimit_Notice:
					ATSAssistCore.NETWORK_WRAPPER.sendToServer(
							new PacketGroundUnitTile(
									this.tile,
									linkRedStone,
									this.getGuiTextFieldText(0),
									this.getGuiTextFieldText(1)));
					break;
				case ATC_SpeedLimit_Cancel:
					ATSAssistCore.NETWORK_WRAPPER.sendToServer(
							new PacketGroundUnitTile(
									this.tile,
									linkRedStone,
									((GuiCheckBox) this.buttonList.get(4)).isChecked()));
					break;
				case TASC_StopPotion_Notice:
				case TASC_StopPotion_Correction:
				case ATO_Departure_Signal:
				case ATO_Change_Speed:
					ATSAssistCore.NETWORK_WRAPPER.sendToServer(
							new PacketGroundUnitTile(
									this.tile,
									linkRedStone,
									this.getGuiTextFieldText(0)));
					break;
				case TASC_Cancel:
				case TASC_StopPotion:
				case ATO_Cancel:
				case ATACS_Enable:
				case ATACS_Disable:
					ATSAssistCore.NETWORK_WRAPPER.sendToServer(
							new PacketGroundUnitTile(
									this.tile,
									linkRedStone));
					break;
			}
			this.mc.displayGuiScreen(null);
			return;
		}

		if (button.id == 0 || this.tile.getType() == GroundUnitType.None) {
			this.sendPacket(button.id);
			this.mc.theWorld.setBlock(tile.xCoord, tile.yCoord, tile.zCoord, ATSAssistCore.blockGroundUnit, button.id, 3);
//			this.mc.thePlayer.openGui(ATSAssistCore.INSTANCE, ATSAssistCore.guiId_GroundUnit, this.mc.theWorld, tile.xCoord, tile.yCoord, tile.zCoord);
		}
	}

	private byte[] getTrainStateBytes() {
		byte[] bytes = new byte[12];
		for (Object button : this.buttonList) {
			if (button instanceof GuiOptionSliderCustom) {
				if (((GuiButton) button).id >= 100 && ((GuiButton) button).id <= 111) {
					GuiOptionSliderCustom slider = (GuiOptionSliderCustom) button;
					bytes[slider.id - 100] = slider.nowValue;
				}
			}
		}
		return bytes;
	}

	private void sendPacket(int id) {
		ATSAssistCore.NETWORK_WRAPPER.sendToServer(new PacketGroundUnitTileInit(id, this.tile));
	}

	private void addDownCommon() {
		GuiCheckBox checkBox = new GuiCheckBox(100, this.width / 2 + 45, this.height / 2 - 50, "", false);
		checkBox.setIsChecked(tile.isLinkRedStone());
		this.buttonList.add(checkBox);

		this.buttonList.add(
				new GuiButton(0, this.width / 4 + 160, 15, 50, 20, "リセット"));

		this.buttonList.add(
				new GuiButton(21, this.width / 2 - 110, this.height - 30, 100, 20, "決定"));

		this.buttonList.add(
				new GuiButton(20, this.width / 2 + 10, this.height - 30, 100, 20, "キャンセル"));
	}

	private void addGuiTextField(int number, String str, int maxLength) {
		GuiTextField text = new GuiTextField(this.fontRendererObj, this.width / 2, this.height / 2 - 30 + (25 * number), 100, 20);
		text.setFocused(false);
		text.setMaxStringLength(maxLength);
		text.setText(str);
		this.textFieldList.add(text);
	}

	private int getGuiTextFieldText(int number) {
		return ((this.textFieldList.get(number).getText() == null) || !this.textFieldList.get(number).getText().equals("")) ? Integer.parseInt(this.textFieldList.get(number).getText()) : 0;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	//別に要らんかも
	@Override
	public void onGuiClosed() {

	}
}
