package jp.kaiz.atsassistmod.gui.parts;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jp.kaiz.atsassistmod.controller.trainprotection.TrainProtectionType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class GuiOptionSliderTrainProtection extends GuiButton {
    public TrainProtectionType nowValue;
    private float _temp;
    public boolean dragging;

    public GuiOptionSliderTrainProtection(int x, int y, TrainProtectionType type) {
        super(100, x, y, 150, 20, "");
        this.nowValue = type;

        int number = Arrays.asList(TrainProtectionType.values()).indexOf(this.nowValue) + 1;
        int all = TrainProtectionType.values().length;
        _temp = ((float) number / (float) all);

        this.displayString = this.nowValue.name;
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
            this.nowValue = TrainProtectionType.values()[0];
            _temp = 0.0F;
        } else if (_temp > 1.0F) {
            this.nowValue = Arrays.asList(TrainProtectionType.values()).get(TrainProtectionType.values().length - 1);
            _temp = 1.0F;
        } else {
            int number = Arrays.asList(TrainProtectionType.values()).indexOf(this.nowValue) + 1;
            int all = TrainProtectionType.values().length;
            this.nowValue = TrainProtectionType.values()[(int) ((all - 1) * _temp)];
            _temp = ((float) number / (float) all);
        }

        this.displayString = this.nowValue.name;
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }

}