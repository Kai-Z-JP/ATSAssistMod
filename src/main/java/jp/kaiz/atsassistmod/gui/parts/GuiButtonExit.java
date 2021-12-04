package jp.kaiz.atsassistmod.gui.parts;

import jp.kaiz.atsassistmod.gui.GuiTextureManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiButtonExit extends GuiButton {

    public GuiButtonExit(int id, int xPos, int yPos) {
        super(id, xPos, yPos, 7, 7, "");
    }

    @Override
    public void drawButton(Minecraft mc, int x, int y, float partialTick) {
        if (this.visible) {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef(this.x, this.y, 1.0F);
            GL11.glScalef(7 / 256F, 7 / 256F, 1.0F);
            this.hovered = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            switch (this.getHoverState(this.hovered)) {
                case 1:
                    mc.getTextureManager().bindTexture(GuiTextureManager.Exit.texture);
                    break;
                case 2:
                    mc.getTextureManager().bindTexture(GuiTextureManager.ExitRed.texture);
                    break;
                default:
                    return;
            }
            this.drawTexturedModalRect(0, 0, 0, 0, 256, 256);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
            this.mouseDragged(mc, x, y);
        }
    }
}
