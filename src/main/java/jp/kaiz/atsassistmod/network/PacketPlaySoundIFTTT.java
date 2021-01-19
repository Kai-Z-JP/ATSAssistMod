package jp.kaiz.atsassistmod.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityIFTTT;
import jp.kaiz.atsassistmod.ifttt.IFTTTContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class PacketPlaySoundIFTTT implements IMessage, IMessageHandler<PacketPlaySoundIFTTT, IMessage> {
    private boolean finish;

    private int tilePosX;
    private int tilePosY;
    private int tilePosZ;
    private int posX;
    private int posY;
    private int posZ;
    private ResourceLocation src;
    private boolean repeat;

    public PacketPlaySoundIFTTT() {

    }

    public PacketPlaySoundIFTTT(TileEntity tile) {
        this.finish = true;
        this.tilePosX = tile.xCoord;
        this.tilePosY = tile.yCoord;
        this.tilePosZ = tile.zCoord;
    }

    public PacketPlaySoundIFTTT(TileEntity tile, int[] pos, ResourceLocation src, boolean repeat) {
        this.finish = false;
        this.tilePosX = tile.xCoord;
        this.tilePosY = tile.yCoord;
        this.tilePosZ = tile.zCoord;
        this.posX = pos[0];
        this.posY = pos[1];
        this.posZ = pos[2];
        this.src = src;
        this.repeat = repeat;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.finish);
        buf.writeInt(this.tilePosX);
        buf.writeInt(this.tilePosY);
        buf.writeInt(this.tilePosZ);
        if (!finish) {
            buf.writeInt(this.posX);
            buf.writeInt(this.posY);
            buf.writeInt(this.posZ);
            ByteBufUtils.writeUTF8String(buf, this.src.getResourceDomain());
            ByteBufUtils.writeUTF8String(buf, this.src.getResourcePath());
            buf.writeBoolean(this.repeat);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.finish = buf.readBoolean();
        this.tilePosX = buf.readInt();
        this.tilePosY = buf.readInt();
        this.tilePosZ = buf.readInt();
        if (!finish) {
            this.posX = buf.readInt();
            this.posY = buf.readInt();
            this.posZ = buf.readInt();
            String domain = ByteBufUtils.readUTF8String(buf);
            String path = ByteBufUtils.readUTF8String(buf);
            this.src = new ResourceLocation(domain, path);
            this.repeat = buf.readBoolean();
        }
    }

    @Override
    public IMessage onMessage(PacketPlaySoundIFTTT message, MessageContext ctx) {
        World world = Minecraft.getMinecraft().theWorld;
        TileEntity tile = world.getTileEntity(message.tilePosX, message.tilePosY, message.tilePosZ);
        if (tile instanceof TileEntityIFTTT) {
            ((TileEntityIFTTT) tile).getThatList().stream()
                    .filter(IFTTTContainer.That.Minecraft.PlaySound.class::isInstance)
                    .map(IFTTTContainer.That.Minecraft.PlaySound.class::cast)
                    .forEach(iftttContainer -> {
                        if (message.finish) {
                            iftttContainer.finishSound();
                        } else {
                            iftttContainer.playSound((TileEntityIFTTT) tile);
                        }
                    });
        }
        return null;
    }
}
