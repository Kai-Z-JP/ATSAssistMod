package jp.kaiz.atsassistmod.ifttt;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.FMLServerHandler;

import javax.annotation.Nullable;

public class IFTTTCommandSender extends CommandBlockBaseLogic {
    private final TileEntity tile;

    public IFTTTCommandSender(TileEntity tile) {
        this.tile = tile;
    }

    @Override
    public String getName() {
        return "ATSA IFTTT Executer" + String.format("(%s, %s, %s)", this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
    }

    @Override
    public boolean canUseCommand(int p_70003_1_, String p_70003_2_) {
        return p_70003_1_ <= 2;
    }

    @Override
    public BlockPos getPosition() {
        return this.tile.getPos();
    }

    @Override
    public World getEntityWorld() {
        return this.tile != null ? this.tile.getWorld() : null;
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return FMLServerHandler.instance().getServer();
    }

    public void executeCommand(String command) {
        MinecraftServer server = FMLServerHandler.instance().getServer();

        if (server != null && server.isCommandBlockEnabled()) {
            server.getCommandManager().executeCommand(this, command);
        }
    }

    @Override
    public void updateCommand() {
    }

    @Override
    public int getCommandBlockType() {
        return 0;
    }

    @Override
    public void fillInInfo(ByteBuf buf) {
    }
}
