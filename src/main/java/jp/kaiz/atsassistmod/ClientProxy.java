package jp.kaiz.atsassistmod;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityCustom;
import jp.kaiz.atsassistmod.event.ATSAssistEventHandlerClient;
import jp.kaiz.atsassistmod.render.TileEntityBeamRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		ATSAssistEventHandlerClient handler = new ATSAssistEventHandlerClient(this.getMinecraft());
		MinecraftForge.EVENT_BUS.register(handler);
	}

	@Override
	public void init() {
//        try {
//            ATSAssistSoundManager asm = new ATSAssistSoundManager();
//            SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();
//
//            Field field = soundHandler.getClass().getDeclaredField("field_147697_e");
//            field.setAccessible(true);
//            SoundRegistry sReg = (SoundRegistry) field.get(soundHandler);
//            for (Object key : sReg.getKeys()) {
//                if (key instanceof ResourceLocation) {
//                    ResourceLocation sLoc = (ResourceLocation) key;
//                    asm.addList(sLoc);
//                }
//            }
//            asm.getList().forEach(System.out::println);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCustom.class, new TileEntityBeamRenderer());
	}

	@Override
	public World getWorld() {
		return this.getMinecraft().theWorld;
	}

	@Override
	public EntityPlayer getPlayer() {
		return this.getMinecraft().thePlayer;
	}

	@Override
	public Minecraft getMinecraft() {
		return FMLClientHandler.instance().getClient();
	}
}
