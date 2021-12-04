package jp.kaiz.atsassistmod.gui.parts;

import jp.kaiz.atsassistmod.gui.GuiTextureManager;
import jp.kaiz.atsassistmod.ifttt.IFTTTContainer;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiDummyButtonIFTTTContainer extends GuiDummyButtonLayer {
    private final IFTTTContainer ifcb;

    public GuiDummyButtonIFTTTContainer(int xPos, int yPos, IFTTTContainer ifcb) {
        super(xPos, yPos, ifcb instanceof IFTTTContainer.This ? GuiTextureManager.IFTTTContainer0.texture : GuiTextureManager.IFTTTContainer1.texture, 90 / 256F);
        this.ifcb = ifcb;
    }

    @Override
    public void drawButton(Minecraft mc, int x, int y, float partialTick) {
        super.drawButton(mc, x, y, partialTick);
        if (this.visible) {
            GL11.glPushMatrix();
            GL11.glTranslatef(1.0F, 1.0F, 1.0F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            mc.fontRenderer.drawStringWithShadow(this.ifcb.getTitle(), this.x + 3, this.y, 0xFFFFFF);
            String[] explanation = this.ifcb.getExplanation();
            switch (explanation.length) {
                case 2:
                    mc.fontRenderer.drawString(explanation[1], this.x + 10, this.y + 21, 0x000000);
                case 1:
                    mc.fontRenderer.drawString(explanation[0], this.x + 10, this.y + 11, 0x000000);
            }
            GL11.glPopMatrix();
        }
    }
}
