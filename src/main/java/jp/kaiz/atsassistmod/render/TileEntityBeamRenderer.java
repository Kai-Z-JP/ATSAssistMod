package jp.kaiz.atsassistmod.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityCustom;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TileEntityBeamRenderer extends TileEntitySpecialRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/beacon_beam.png");

    public void renderTileEntityAt(TileEntityCustom tileEntity, double x, double y, double z, float p_147500_8_) {
        GL11.glPushMatrix();

        if (tileEntity.isRenderBeam()) {
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            Tessellator tessellator = Tessellator.instance;
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
            tessellator.setColorRGBA(0, 190, 246, 32);

            double d7 = 0.5D - 0.15D;
            double d9 = 0.5D + 0.15D;
            double d23 = 256.0F - y;
            double d25 = 0.0D;
            double d27 = 1.0D;
            double d28 = -1.0F;
            double d29 = (double) 256.0F + d28;

            {
                tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d27, d29);
                tessellator.addVertexWithUV(x + d7, y, z + d9, d27, d28);
                tessellator.addVertexWithUV(x + d9, y, z + d9, d25, d28);
                tessellator.addVertexWithUV(x + d9, y + d23, z + d9, d25, d29);
                tessellator.addVertexWithUV(x + d7, y + d23, z + d7, d27, d29);
                tessellator.addVertexWithUV(x + d7, y, z + d7, d27, d28);
                tessellator.addVertexWithUV(x + d9, y, z + d7, d25, d28);
                tessellator.addVertexWithUV(x + d9, y + d23, z + d7, d25, d29);
                tessellator.addVertexWithUV(x + d9, y + d23, z + d9, d27, d29);
                tessellator.addVertexWithUV(x + d9, y, z + d9, d27, d28);
                tessellator.addVertexWithUV(x + d9, y, z + d7, d25, d28);
                tessellator.addVertexWithUV(x + d9, y + d23, z + d7, d25, d29);
                tessellator.addVertexWithUV(x + d7, y + d23, z + d7, d27, d29);
                tessellator.addVertexWithUV(x + d7, y, z + d7, d27, d28);
                tessellator.addVertexWithUV(x + d7, y, z + d9, d25, d28);
                tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d25, d29);
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