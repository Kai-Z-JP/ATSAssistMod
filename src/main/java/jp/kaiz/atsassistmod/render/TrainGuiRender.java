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
			if (tcc.isDontShowHUD()) {
				return;
			}
			FontRenderer fontrenderer = this.mc.fontRenderer;
			ModelSetVehicleBase<TrainConfig> model = train.getModelSet();

			String atoSpeed = tcc.isATO() ? String.valueOf(tcc.getATOSpeed()) : "off";
			String tascSpeed = tcc.isTASC() ? String.valueOf(tcc.getTASCDistance()) : "off";
			int atc = tcc.getATCSpeed();
			String limitSpeed = atc == Integer.MAX_VALUE ? "---" : String.valueOf(atc);
			String tpSpeed = tcc.getTrainProtectionSpeed() == Integer.MAX_VALUE ? "---" : String.valueOf(tcc.getTrainProtectionSpeed());
			TrainProtectionType tpType = tcc.getTrainProtectionType();


			if (model != null && !model.getConfig().notDisplayCab) {
				//cab表示あり
				int k = this.width / 2;

				//ATO
				fontrenderer.drawStringWithShadow("ATO : " + atoSpeed, k + 160, this.height - 40, 0x00FF00);

				//TASC
				fontrenderer.drawStringWithShadow("TASC : " + tascSpeed, k + 160, this.height - 30, 0x00FF00);

				//Limit
				fontrenderer.drawStringWithShadow("Limit: " + limitSpeed, k + 160, this.height - 20, 0x00FF00);

				//TrainProtection
				fontrenderer.drawStringWithShadow(tpType.name + ": " + tpSpeed, k + 160, this.height - 10, 0x00FF00);

			} else {
				//cab表示なし

				//ATO
				fontrenderer.drawStringWithShadow("ATO : " + atoSpeed, 2, this.height - 90, 16777215);

				//TASC
				fontrenderer.drawStringWithShadow("TASC : " + tascSpeed, 2, this.height - 80, 16777215);

				//Limit
				fontrenderer.drawStringWithShadow("Limit : " + limitSpeed, 2, this.height - 70, 16777215);

				//TrainProtection
				fontrenderer.drawStringWithShadow(tpType.name + " : " + tpSpeed, 2, this.height - 60, 16777215);
			}
		}
	}
}