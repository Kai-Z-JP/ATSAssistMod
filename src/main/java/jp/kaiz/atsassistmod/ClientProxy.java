package jp.kaiz.atsassistmod;

import jp.kaiz.atsassistmod.block.tileentity.TileEntityCustom;
import jp.kaiz.atsassistmod.event.ATSAssistEventHandlerClient;
import jp.kaiz.atsassistmod.event.ATSAssistKeyHandler;
import jp.kaiz.atsassistmod.render.TileEntityBeamRenderer;
import jp.kaiz.atsassistmod.sound.ATSAMovingSoundEntity;
import jp.kaiz.atsassistmod.sound.ATSAMovingSoundTileEntity;
import jp.ngt.ngtlib.util.NGTUtilClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        ATSAssistEventHandlerClient handler = new ATSAssistEventHandlerClient(this.getMinecraft());
        MinecraftForge.EVENT_BUS.register(handler);
        FMLCommonHandler.instance().bus().register(handler);
        FMLCommonHandler.instance().bus().register(new ATSAssistKeyHandler());

        ATSAssistBlock.initClient();
        ATSAssistItem.initClient();
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
        return this.getMinecraft().world;
    }

    @Override
    public EntityPlayer getPlayer() {
        return this.getMinecraft().player;
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

                        List<ISound> trackList = posList.stream().filter(Objects::nonNull).map(pos -> new ATSAMovingSoundTileEntity(tile, pos, src, false, volume)).collect(Collectors.toList());
                        ATSAssistEventHandlerClient.soundList.addAll(trackList);
                        Thread.sleep(50L);
                        while (soundHandler.isSoundPlaying(trackList.get(0))) {
                            Thread.sleep(50L);
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

    @Override
    public void playSounds(Entity entity, List<Object> orderList, float volume) {
        new Thread(() -> {
            SoundHandler soundHandler = NGTUtilClient.getMinecraft().getSoundHandler();
            orderList.stream().filter(Objects::nonNull).map(Object::toString).forEach(order -> {
                try {
                    if (order.contains(":")) {
                        String[] domainPath = order.split(":");
                        ResourceLocation src = new ResourceLocation(domainPath[0], domainPath[1]);

                        ISound track = new ATSAMovingSoundEntity(entity, src, false, volume);
                        ATSAssistEventHandlerClient.soundList.add(track);
                        Thread.sleep(50L);
                        while (soundHandler.isSoundPlaying(track)) {
                            Thread.sleep(50L);
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
