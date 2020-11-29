package jp.kaiz.atsassistmod.gui.parts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiDummyButtonLayer extends GuiButton {
    ResourceLocation texture;
    float scale;

    public GuiDummyButtonLayer(int xPos, int yPos, ResourceLocation texture, float scale) {
        super(-1, xPos, yPos, 0, 0, "");
        this.enabled = false;
        this.texture = texture;
        this.scale = scale;
    }

    @Override
    public void drawButton(Minecraft mc, int x, int y) {
        if (this.visible) {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glTranslatef(this.xPosition, this.yPosition, 1.0F);
            GL11.glScalef(this.scale, this.scale, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(this.texture);
            this.drawTexturedModalRect(0, 0, 0, 0, 256, 256);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }
}
