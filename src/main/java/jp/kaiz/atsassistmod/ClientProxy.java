package jp.kaiz.atsassistmod;

import cpw.mods.fml.client.FMLClientHandler;
import jp.kaiz.atsassistmod.event.ATSAssistEventHandlerClient;
import jp.kaiz.atsassistmod.sound.ATSAssistSoundManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.lang.reflect.Field;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        ATSAssistEventHandlerClient handler = new ATSAssistEventHandlerClient(Minecraft.getMinecraft());
        MinecraftForge.EVENT_BUS.register(handler);
    }

    @Override
    public void init() {
        try {
            ATSAssistSoundManager asm = new ATSAssistSoundManager();
            SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();

            Field field = soundHandler.getClass().getDeclaredField("field_147697_e");
            field.setAccessible(true);
            SoundRegistry sReg = (SoundRegistry) field.get(soundHandler);
            for (Object key : sReg.getKeys()) {
                if (key instanceof ResourceLocation) {
                    ResourceLocation sLoc = (ResourceLocation) key;
                    asm.addList(sLoc);
                }
            }
            asm.getList().forEach(System.out::println);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
	}

	@Override
	public World getWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public EntityPlayer getPlayer() {
		return FMLClientHandler.instance().getClient().thePlayer;
	}
}
