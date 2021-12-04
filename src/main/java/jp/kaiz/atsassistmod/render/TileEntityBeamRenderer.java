package jp.kaiz.atsassistmod.render;

import jp.kaiz.atsassistmod.block.tileentity.TileEntityCustom;
import jp.ngt.ngtlib.renderer.NGTTessellator;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TileEntityBeamRenderer extends TileEntitySpecialRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/beacon_beam.png");

    public void renderTileEntityAt(TileEntityCustom tileEntity, double x, double y, double z, float p_147500_8_) {
        GL11.glPushMatrix();

        if (tileEntity.isRenderBeam()) {
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            NGTTessellator tessellator = NGTTessellator.instance;
            this.bindTexture(TEXTURE);
//            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
//            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GL11.glDepthMask(false);

            tessellator.setBrightness(255);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(0, 190, 246, 32);

            double d7 = 0.5D - 0.15D;
            double d9 = 0.5D + 0.15D;
            double d23 = 256.0F - y;
            double d25 = 0.0D;
            double d27 = 1.0D;
            double d28 = -1.0F;
            double d29 = (double) 256.0F + d28;

            {
                tessellator.addVertexWithUV((float) (x + d7), (float) (y + d23), (float) (z + d9), (float) d27, (float) d29);
                tessellator.addVertexWithUV((float) (x + d7), (float) y, (float) (z + d9), (float) d27, (float) d28);
                tessellator.addVertexWithUV((float) (x + d9), (float) y, (float) (z + d9), (float) d25, (float) d28);
                tessellator.addVertexWithUV((float) (x + d9), (float) (y + d23), (float) (z + d9), (float) d25, (float) d29);
                tessellator.addVertexWithUV((float) (x + d7), (float) (y + d23), (float) (z + d7), (float) d27, (float) d29);
                tessellator.addVertexWithUV((float) (x + d7), (float) y, (float) (z + d7), (float) d27, (float) d28);
                tessellator.addVertexWithUV((float) (x + d9), (float) y, (float) (z + d7), (float) d25, (float) d28);
                tessellator.addVertexWithUV((float) (x + d9), (float) (y + d23), (float) (z + d7), (float) d25, (float) d29);
                tessellator.addVertexWithUV((float) (x + d9), (float) (y + d23), (float) (z + d9), (float) d27, (float) d29);
                tessellator.addVertexWithUV((float) (x + d9), (float) y, (float) (z + d9), (float) d27, (float) d28);
                tessellator.addVertexWithUV((float) (x + d9), (float) y, (float) (z + d7), (float) d25, (float) d28);
                tessellator.addVertexWithUV((float) (x + d9), (float) (y + d23), (float) (z + d7), (float) d25, (float) d29);
                tessellator.addVertexWithUV((float) (x + d7), (float) (y + d23), (float) (z + d7), (float) d27, (float) d29);
                tessellator.addVertexWithUV((float) (x + d7), (float) y, (float) (z + d7), (float) d27, (float) d28);
                tessellator.addVertexWithUV((float) (x + d7), (float) y, (float) (z + d9), (float) d25, (float) d28);
                tessellator.addVertexWithUV((float) (x + d7), (float) (y + d23), (float) (z + d9), (float) d25, (float) d29);
            }

            tessellator.draw();

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(true);
        }

        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_) {
        this.renderTileEntityAt((TileEntityCustom) tileEntity, x, y, z, p_147500_8_);
    }
}