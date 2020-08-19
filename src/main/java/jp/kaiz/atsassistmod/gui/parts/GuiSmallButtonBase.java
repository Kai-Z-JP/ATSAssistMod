package jp.kaiz.atsassistmod.gui.parts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class GuiSmallButtonBase extends GuiButton {
	protected ResourceLocation texture;

	public GuiSmallButtonBase(int id, int xPos, int yPos, String screenString, ResourceLocation texture) {
		super(id, xPos, yPos, 22, 9, screenString);
		this.texture = texture;
	}

	@Override
	public void drawButton(Minecraft mc, int x, int y) {
		if (this.visible) {
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef(this.xPosition, this.yPosition, 1.0F);
			GL11.glScalef((45 / 2F) / 256F, (45 / 2F) / 256F, 1.0F);
			mc.getTextureManager().bindTexture(this.texture);
			this.drawTexturedModalRect(0, 0, 0, 0, 256, 256);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();

			this.mouseDragged(mc, x, y);

			GL11.glPushMatrix();
			GL11.glTranslatef(1.0F, 1.0F, 1.0F);
			this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
			int l = 14737632;
			if (packedFGColour != 0) {
				l = packedFGColour;
			} else if (!this.enabled) {
				l = 10526880;
			} else if (this.field_146123_n) {
				l = 16777120;
			}
			this.drawCenteredString(mc.fontRenderer, this.displayString, this.xPosition + (this.width - 1) / 2, this.yPosition + (this.height - 11) / 2, l);
			GL11.glPopMatrix();
		}
	}
}
