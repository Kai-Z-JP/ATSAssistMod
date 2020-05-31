package jp.kaiz.atsassistmod.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

@SideOnly(Side.CLIENT)
public class TrainGuiRender extends GuiScreen {

    public TrainGuiRender(Minecraft mc) {
        super();
        this.mc = mc;
    }

    public void onRenderGui(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            Minecraft mc = this.mc;
            EntityPlayer player = mc.thePlayer;
            if (!player.isRiding() || mc.gameSettings.thirdPersonView != 0) {
                return;
            }

            this.width = event.resolution.getScaledWidth();
            this.height = event.resolution.getScaledHeight();

            if (player.ridingEntity instanceof EntityTrainBase) {
                if (((EntityTrainBase) player.ridingEntity).isControlCar()) {
                    this.renderTrainGui((EntityTrainBase) player.ridingEntity);
                }
            }
        }
    }

    private void renderTrainGui(EntityTrainBase train) {
        TrainControllerClient tcc;
        if ((tcc = TrainControllerClientManager.getTCC(train)) != null) {
            FontRenderer fontrenderer = this.mc.fontRenderer;
            ModelSetVehicleBase<TrainConfig> model = train.getModelSet();

            int ato = tcc.getATOSpeed();
            int tasc = tcc.getTASCDistance();
            int atc = tcc.getATCSpeed();
            boolean atoStatus = tcc.isATO();
            boolean tascStatus = tcc.isTASC();
            TrainProtectionType tpType = tcc.getTrainProtectionType();


            if (model != null && !model.getConfig().notDisplayCab) {
                //cab表示あり
                int k = this.width / 2;

                //ATO
                if (atoStatus) {
                    fontrenderer.drawStringWithShadow("ATO : " + ato, k + 160, this.height - 40, 0x00FF00);
                } else {
                    fontrenderer.drawStringWithShadow("ATO : off", k + 160, this.height - 40, 0x00FF00);
                }

                //TASC
//				fontrenderer.drawStringWithShadow("TASC : " + tasc, k + 160, this.height - 30, 0x00FF00);
                if (tascStatus) {
                    fontrenderer.drawStringWithShadow("TASC : " + tasc, k + 160, this.height - 30, 0x00FF00);
                } else {
                    fontrenderer.drawStringWithShadow("TASC : off", k + 160, this.height - 30, 0x00FF00);
                }

                //Limit
//				fontrenderer.drawStringWithShadow("Limit: " + atc, k + 160, this.height - 20, 0x00FF00);
                if (atc == Integer.MAX_VALUE) {
                    fontrenderer.drawStringWithShadow("Limit: ---", k + 160, this.height - 20, 0x00FF00);
                } else {
                    fontrenderer.drawStringWithShadow("Limit: " + atc, k + 160, this.height - 20, 0x00FF00);
                }

                //TrainProtection
//				fontrenderer.drawStringWithShadow("ATACS: " + atacs, k + 160, this.height - 10, 0x00FF00);
                String tpSpeed = tcc.getTrainProtectionSpeed() == Integer.MAX_VALUE ? ": ---" : ": " + tcc.getTrainProtectionSpeed();
                fontrenderer.drawStringWithShadow(tpType.name + tpSpeed, k + 160, this.height - 10, 0x00FF00);

//                if (atacsStatus) {
//                    if (atacs == Integer.MAX_VALUE) {
//                        fontrenderer.drawStringWithShadow("ATACS: ---", k + 160, this.height - 10, 0x00FF00);
//                    } else {
//                        fontrenderer.drawStringWithShadow("ATACS: " + atacs, k + 160, this.height - 10, 0x00FF00);
//                    }
//                } else {
//                    fontrenderer.drawStringWithShadow("ATACS: off", k + 160, this.height - 10, 0x00FF00);
//                }

            } else {
                //cab表示なし

                //ATO
//				fontrenderer.drawStringWithShadow("ATO : " + ato, 2, this.height - 90, 16777215);
                if (atoStatus) {
                    fontrenderer.drawStringWithShadow("ATO : " + ato, 2, this.height - 90, 16777215);
                } else {
                    fontrenderer.drawStringWithShadow("ATO : off", 2, this.height - 90, 16777215);
                }

                //TASC
//				fontrenderer.drawStringWithShadow("TASC : " + tasc, 2, this.height - 80, 16777215);
                if (tascStatus) {
                    fontrenderer.drawStringWithShadow("TASC : " + tasc, 2, this.height - 80, 16777215);
                } else {
                    fontrenderer.drawStringWithShadow("TASC : off", 2, this.height - 80, 16777215);
                }

                //Limit
//				fontrenderer.drawStringWithShadow("Limit : " + atc, 2, this.height - 70, 16777215);
                if (atc == Integer.MAX_VALUE) {
                    fontrenderer.drawStringWithShadow("Limit : ---", 2, this.height - 70, 16777215);
                } else {
                    fontrenderer.drawStringWithShadow("Limit : " + atc, 2, this.height - 70, 16777215);
                }

                //ATACS
//				fontrenderer.drawStringWithShadow("ATACS : " + atacs, 2, this.height - 60, 16777215);
                String tpSpeed = tcc.getTrainProtectionSpeed() == Integer.MAX_VALUE ? " : ---" : " : " + tcc.getTrainProtectionSpeed();
                fontrenderer.drawStringWithShadow(tpType.name + tpSpeed, 2, this.height - 60, 16777215);

//                if (atacsStatus) {
//                    if (atacs == Integer.MAX_VALUE) {
//                        fontrenderer.drawStringWithShadow("ATACS : ---", 2, this.height - 60, 16777215);
//                    } else {
//                        fontrenderer.drawStringWithShadow("ATACS : " + atacs, 2, this.height - 60, 16777215);
//                    }
//                } else {
//                    fontrenderer.drawStringWithShadow("ATACS : off", 2, this.height - 60, 16777215);
//                }
            }
        }
    }
}