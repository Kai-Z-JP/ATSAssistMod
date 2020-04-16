package jp.kaiz.atsassistmod.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jp.kaiz.atsassistmod.api.TrainControllerClient;
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
					return;
				}
			}
			TrainControllerClient.setEntityID(0);
		}
	}

	private void renderTrainGui(EntityTrainBase train) {
		if (TrainControllerClient.isEnable(train.getEntityId())) {
			FontRenderer fontrenderer = this.mc.fontRenderer;
			ModelSetVehicleBase<TrainConfig> model = train.getModelSet();

			int ato = TrainControllerClient.getATOSpeed();
			int tasc = TrainControllerClient.getTASCDistance();
			int atc = TrainControllerClient.getATCSpeed();
			int atacs = TrainControllerClient.getATACSSpeed();
			boolean atoStatus = TrainControllerClient.isATO();
			boolean tascStatus = TrainControllerClient.isTASC();
			boolean atacsStatus = TrainControllerClient.isATACS();


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

				//ATACS
//				fontrenderer.drawStringWithShadow("ATACS: " + atacs, k + 160, this.height - 10, 0x00FF00);
				if (atacsStatus) {
					if (atacs == Integer.MAX_VALUE) {
						fontrenderer.drawStringWithShadow("ATACS: ---", k + 160, this.height - 10, 0x00FF00);
					} else {
						fontrenderer.drawStringWithShadow("ATACS: " + atacs, k + 160, this.height - 10, 0x00FF00);
					}
				} else {
					fontrenderer.drawStringWithShadow("ATACS: off", k + 160, this.height - 10, 0x00FF00);
				}

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
				if (atacsStatus) {
					if (atacs == Integer.MAX_VALUE) {
						fontrenderer.drawStringWithShadow("ATACS : ---", 2, this.height - 60, 16777215);
					} else {
						fontrenderer.drawStringWithShadow("ATACS : " + atacs, 2, this.height - 60, 16777215);
					}
				} else {
					fontrenderer.drawStringWithShadow("ATACS : off", 2, this.height - 60, 16777215);
				}
			}
		}
	}
}