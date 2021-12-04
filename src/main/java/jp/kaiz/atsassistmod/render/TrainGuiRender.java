package jp.kaiz.atsassistmod.render;

import jp.kaiz.atsassistmod.api.TrainControllerClient;
import jp.kaiz.atsassistmod.api.TrainControllerClientManager;
import jp.kaiz.atsassistmod.controller.trainprotection.TrainProtectionType;
import jp.ngt.rtm.entity.train.EntityTrainBase;
import jp.ngt.rtm.modelpack.cfg.TrainConfig;
import jp.ngt.rtm.modelpack.modelset.ModelSetVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TrainGuiRender extends GuiScreen {

    public TrainGuiRender(Minecraft mc) {
        super();
        this.mc = mc;
    }

    public void onRenderGui(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            Minecraft mc = this.mc;
            EntityPlayer player = mc.player;
            if (!player.isRiding() || mc.gameSettings.thirdPersonView != 0) {
                return;
            }

            this.width = event.getResolution().getScaledWidth();
            this.height = event.getResolution().getScaledHeight();

            if (player.getRidingEntity() instanceof EntityTrainBase) {
                if (((EntityTrainBase) player.getRidingEntity()).isControlCar()) {
                    this.renderTrainGui((EntityTrainBase) player.getRidingEntity());
                }
            }
        }
    }

    private void renderTrainGui(EntityTrainBase train) {
        TrainControllerClient tcc;
        if ((tcc = TrainControllerClientManager.getTCC(train)) != null) {
            if (tcc.isNotShowHud()) {
                return;
            }
            FontRenderer fontrenderer = this.mc.fontRenderer;
            ModelSetVehicleBase<TrainConfig> model = train.getResourceState().getResourceSet();

            String atoSpeed = tcc.isATO() ? String.valueOf(tcc.getATOSpeed()) : "off";
            String tascSpeed = tcc.isTASC() ? String.valueOf(tcc.getTASCDistance()) : "off";
            int atc = tcc.getATCSpeed();
            String limitSpeed = atc == Integer.MAX_VALUE ? "---" : String.valueOf(atc);
            String tpSpeed = tcc.getTrainProtectionSpeed() == Integer.MAX_VALUE ? "---" : String.valueOf(tcc.getTrainProtectionSpeed());
            TrainProtectionType tpType = tcc.getTrainProtectionType();


            if (model != null && !model.getConfig().notDisplayCab) {
                //cab表示あり
                int k = this.width / 2;

                int color = tcc.isManualDrive() ? 0xFF0000 : 0x00FF00;
                //ATO
                fontrenderer.drawStringWithShadow("ATO : " + atoSpeed, k + 160, this.height - 40, color);

                //TASC
                fontrenderer.drawStringWithShadow("TASC : " + tascSpeed, k + 160, this.height - 30, color);

                //Limit
                fontrenderer.drawStringWithShadow("Limit: " + limitSpeed, k + 160, this.height - 20, 0x00FF00);

                //TrainProtection
                if (tpType != TrainProtectionType.NONE) {
                    fontrenderer.drawStringWithShadow(tpType.getDisplayName() + ": " + tpSpeed, k + 160, this.height - 10, 0x00FF00);
                }

            } else {
                int fixHeight = 50;

                int color = tcc.isManualDrive() ? 0xFF0000 : 16777215;
                //cab表示なし
                //TrainProtection
                if (tpType != TrainProtectionType.NONE) {
                    fontrenderer.drawStringWithShadow(tpType.getDisplayName() + " : " + tpSpeed, 2, this.height - (fixHeight += 10), 16777215);
                }

                //Limit
                fontrenderer.drawStringWithShadow("Limit : " + limitSpeed, 2, this.height - (fixHeight += 10), 16777215);

                //TASC
                fontrenderer.drawStringWithShadow("TASC : " + tascSpeed, 2, this.height - (fixHeight += 10), color);

                //ATO
                fontrenderer.drawStringWithShadow("ATO : " + atoSpeed, 2, this.height - (fixHeight += 10), color);
            }
        }
    }
}