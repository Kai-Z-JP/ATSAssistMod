package jp.kaiz.atsassistmod;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityCustom;
import jp.kaiz.atsassistmod.event.ATSAssistEventHandlerClient;
import jp.kaiz.atsassistmod.event.ATSAssistKeyHandler;
import jp.kaiz.atsassistmod.render.TileEntityBeamRenderer;
import jp.kaiz.atsassistmod.sound.ATSAMovingSoundTileEntity;
import jp.ngt.ngtlib.util.NGTUtilClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        ATSAssistEventHandlerClient handler = new ATSAssistEventHandlerClient(this.getMinecraft());
        MinecraftForge.EVENT_BUS.register(handler);
        FMLCommonHandler.instance().bus().register(new ATSAssistKeyHandler());
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

    @Override
    public void playSounds(TileEntity tile, List<int[]> posList, List<Object> orderList, float volume) {
        new Thread(() -> {
            SoundHandler soundHandler = NGTUtilClient.getMinecraft().getSoundHandler();
            orderList.stream().filter(Objects::nonNull).map(Object::toString).forEach(order -> {
                try {
                    if (order.contains(":")) {
                        String[] domainPath = order.split(":");
                        ResourceLocation src = new ResourceLocation(domainPath[0], domainPath[1]);

                        List<ISound> trackList = new ArrayList<>();
                        posList.stream().filter(Objects::nonNull).map(pos -> new ATSAMovingSoundTileEntity(tile, pos, src, false, volume)).forEach(trackList::add);
                        trackList.forEach(soundHandler::playSound);
                        Thread.sleep(10L);
                        while (soundHandler.isSoundPlaying(trackList.get(0))) {
                        }
                    } else if (NumberUtils.isNumber(order)) {
                        Thread.sleep((long) (1000L * Double.parseDouble(order)));
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }
}
