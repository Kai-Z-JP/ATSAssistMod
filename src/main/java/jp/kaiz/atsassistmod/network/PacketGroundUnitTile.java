package jp.kaiz.atsassistmod.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import jp.kaiz.atsassistmod.block.tileentity.TileEntityGroundUnit;
import jp.kaiz.atsassistmod.controller.trainprotection.TrainProtectionType;
import jp.ngt.ngtlib.network.PacketNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGroundUnitTile implements IMessage, IMessageHandler<PacketGroundUnitTile, IMessage> {
    private int id;
    private long pos;
    private boolean linkRedStone;
    private boolean useTrainDistance;
    private int speed;
    private int par0;
    private double distance;
    private byte[] par1ByteArray;
    private boolean autoBrake;

    public PacketGroundUnitTile() {
    }

    public PacketGroundUnitTile(TileEntityGroundUnit tile, boolean linkRedStone) {
        this.id = tile.getType().id;
        this.pos = tile.getPos().toLong();
        this.linkRedStone = linkRedStone;
    }

    public PacketGroundUnitTile(TileEntityGroundUnit tile, boolean linkRedStone, boolean useTrainDistance) {
        this(tile, linkRedStone);
        this.useTrainDistance = useTrainDistance;
    }

    public PacketGroundUnitTile(TileEntityGroundUnit tile, boolean linkRedStone, double distance, boolean useTrainDistance) {
        this(tile, linkRedStone);
        this.distance = distance;
        this.useTrainDistance = useTrainDistance;
    }

    public PacketGroundUnitTile(TileEntityGroundUnit tile, boolean linkRedStone, int speed) {
        this(tile, linkRedStone);
        this.speed = speed;
    }

    public PacketGroundUnitTile(TileEntityGroundUnit tile, boolean linkRedStone, int speed, double distance, boolean autoBrake, boolean useTrainDistance) {
        this(tile, linkRedStone);
        this.speed = speed;
        this.distance = distance;
        this.autoBrake = autoBrake;
        this.useTrainDistance = useTrainDistance;
    }

    public PacketGroundUnitTile(TileEntityGroundUnit tile, boolean linkRedStone, byte[] par1ByteArray) {
        this(tile, linkRedStone);
        this.par1ByteArray = par1ByteArray;
    }

    public PacketGroundUnitTile(TileEntityGroundUnit tile, boolean linkRedStone, TrainProtectionType tpType) {
        this(tile, linkRedStone);
        this.par0 = tpType.id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = buf.readInt();
        this.pos = buf.readLong();
        this.linkRedStone = buf.readBoolean();
        switch (this.id) {
            case 1:
                //距離とスピード
                this.speed = buf.readInt();
                this.distance = buf.readDouble();
                this.autoBrake = buf.readBoolean();
                this.useTrainDistance = buf.readBoolean();
                break;
            case 2:
                //遅れて実行するか
                this.useTrainDistance = buf.readBoolean();
                break;
            case 4://距離のみ
            case 6://距離のみ
                this.distance = buf.readDouble();
                this.useTrainDistance = buf.readBoolean();
                break;
            case 9://スピードのみ
            case 11://スピードのみ
                this.speed = buf.readInt();
                break;
            case 13:
                this.par1ByteArray = ByteBufUtil.getBytes(buf);
                break;
            case 14:
                this.par0 = buf.readInt();
                break;
            case 5:
            case 7:
            case 10:
                //なし
                break;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.id);
        buf.writeLong(this.pos);
        buf.writeBoolean(this.linkRedStone);
        switch (this.id) {
            case 1:
                //距離とスピード
                buf.writeInt(this.speed);
                buf.writeDouble(this.distance);
                buf.writeBoolean(this.autoBrake);
                buf.writeBoolean(this.useTrainDistance);
                break;
            case 2:
                //遅れて実行するか
                buf.writeBoolean(this.useTrainDistance);
                break;
            case 4://距離のみ
            case 6://距離のみ
                buf.writeDouble(this.distance);
                buf.writeBoolean(this.useTrainDistance);
                break;
            case 9://スピードのみ
            case 11://スピードのみ
                buf.writeInt(this.speed);
                break;
            case 13:
                buf.writeBytes(this.par1ByteArray);
                break;
            case 14:
                buf.writeInt(this.par0);
                break;
            case 5:
            case 7:
            case 10:
                //なし
                break;
        }
    }

    @Override
    public IMessage onMessage(PacketGroundUnitTile message, MessageContext ctx) {
        WorldServer world = ctx.getServerHandler().player.getServerWorld();
        BlockPos blockPos = BlockPos.fromLong(message.pos);
        TileEntityGroundUnit tile = (TileEntityGroundUnit) world.getTileEntity(blockPos);
        tile.setLinkRedStone(message.linkRedStone);
        switch (message.id) {
            case 1:
                //距離とスピード
                ((TileEntityGroundUnit.Speed) tile).setSpeedLimit(message.speed);
                ((TileEntityGroundUnit.Distance) tile).setDistance(message.distance);
                ((TileEntityGroundUnit.ATCSpeedLimitNotice) tile).setAutoBrake(message.autoBrake);
                ((TileEntityGroundUnit.TrainDistance) tile).setUseTrainDistance(message.useTrainDistance);
                break;
            case 2:
                ((TileEntityGroundUnit.TrainDistance) tile).setUseTrainDistance(message.useTrainDistance);
                break;
            case 4:
            case 6:
                //距離のみ
                ((TileEntityGroundUnit.Distance) tile).setDistance(message.distance);
                ((TileEntityGroundUnit.TrainDistance) tile).setUseTrainDistance(message.useTrainDistance);
                break;
            case 9:
            case 11:
                //スピードのみ
                ((TileEntityGroundUnit.Speed) tile).setSpeedLimit(message.speed);
                break;
            case 13:
                ((TileEntityGroundUnit.TrainStateSet) tile).setStates(message.par1ByteArray);
                break;
            case 14:
                ((TileEntityGroundUnit.ChangeTrainProtection) tile).setTPType(TrainProtectionType.getType(message.par0));
                break;
            case 5:
            case 7:
            case 10:
                //なし
                break;
        }
        PacketNBT.sendToClient(tile);
        tile.markDirty();
        return null;
    }
}
