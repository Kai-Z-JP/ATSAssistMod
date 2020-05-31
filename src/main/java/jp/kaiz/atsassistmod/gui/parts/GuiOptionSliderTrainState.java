package jp.kaiz.atsassistmod.gui.parts;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jp.ngt.rtm.entity.train.util.TrainState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiOptionSliderTrainState extends GuiButton {
    public byte nowValue;
    private float _temp;
    public boolean dragging;
    private final TrainState.TrainStateType type;

    public GuiOptionSliderTrainState(int x, int y, int trainStateID, byte trainStateData) {
        super(100 + trainStateID, x, y, 150, 20, "");
        this.type = TrainState.getStateType(trainStateID);
        this.nowValue = trainStateData;

        _temp = (float) (this.nowValue - (this.type.min - 1)) / (this.type.max - (this.type.min - 1));

        this.displayString = getNormalizedValue();
    }

	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
	 * this button.
	 */
	public int getHoverState(boolean p_146114_1_) {
		return 0;
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
	 */
	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			if (this.dragging) {
				get(mouseX);
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(this.xPosition + (int) (_temp * (float) (this.width - 8)), this.yPosition, 0, 66, 4, 20);
			this.drawTexturedModalRect(this.xPosition + (int) (_temp * (float) (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
		}
	}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
	 * e).
	 */
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if (super.mousePressed(mc, mouseX, mouseY)) {
			get(mouseX);
			this.dragging = true;
			return true;
		} else {
			return false;
		}
	}

	private void get(int mouseX) {
		_temp = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);

		if (_temp < 0.0F) {
			this.nowValue = (byte) (this.type.min - 1);
			_temp = 0.0F;
		} else if (_temp > 1.0F) {
			this.nowValue = this.type.max;
			_temp = 1.0F;
		} else {
			this.nowValue = (byte) ((this.type.max - (this.type.min - 1)) * _temp + (this.type.min - 1));
			_temp = (float) (this.nowValue - (this.type.min - 1)) / (this.type.max - (this.type.min - 1));
		}

		this.displayString = getNormalizedValue();
	}

	/**
	 * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
	 */
	public void mouseReleased(int mouseX, int mouseY) {
		this.dragging = false;
	}

	private String getNormalizedValue() {
		String state, data = String.valueOf(this.nowValue);

		switch (this.type) {
			case State_TrainDir:
				state = "方向";
				break;
			case State_Notch:
				state = "ノッチ";
				break;
			case State_Signal:
				state = "信号";
				break;
			case State_Door:
				state = "ドア";
				switch (this.nowValue) {
					case 0:
						data = "両側 閉";
						break;
					case 1:
						data = "右側 開";
						break;
					case 2:
						data = "左側 開";
						break;
					case 3:
						data = "両側 開";
						break;
				}
				break;
			case State_Light:
				state = "前照灯";
				switch (this.nowValue) {
					case 0:
						data = "オフ";
						break;
					case 1:
						data = "オン";
						break;
					case 2:
						state = "前照灯・尾灯";
						data = "オン";
						break;
				}
				break;
			case State_Pantograph:
				state = "パンタグラフ";
				switch (this.nowValue) {
					case 0:
						data = "下げ";
						break;
					case 1:
						data = "上げ";
						break;
				}
				break;
			case State_ChunkLoader:
				state = "チャンクローダー";
				break;
			case State_Destination:
				state = "行先";
				break;
			case State_Announcement:
				state = "アナウンス";
				break;
			case State_Direction:
				state = "逆転ハンドル";
				switch (this.nowValue) {
					case 0:
						data = "前";
						break;
					case 1:
						data = "切";
						break;
					case 2:
						data = "後";
						break;
				}
				break;
			case State_InteriorLight:
				state = "車内灯";
				switch (this.nowValue) {
					case 0:
						data = "OFF";
						break;
					case 1:
						data = "ON";
						break;
					case 2:
						data = "Rainbow";
						break;
				}
				break;
			default:
				state = "";
				break;
		}
		if (this.nowValue < this.type.min) {
			data = "変更なし";
		}
		return state + ":" + data;
	}
}