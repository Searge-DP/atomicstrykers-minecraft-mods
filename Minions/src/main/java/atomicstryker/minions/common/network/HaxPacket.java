package atomicstryker.minions.common.network;

import atomicstryker.minions.common.network.NetworkHelper.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class HaxPacket implements IPacket
{
    
    private String user;
    
    public HaxPacket() {}
    
    public HaxPacket(String username)
    {
        user = username;
    }

    @Override
    public void writeBytes(ChannelHandlerContext ctx, ByteBuf bytes)
    {
        ByteBufUtils.writeUTF8String(bytes, user);
    }

    @Override
    public void readBytes(ChannelHandlerContext ctx, ByteBuf bytes)
    {
        user = ByteBufUtils.readUTF8String(bytes);
        MinecraftServer.getServer().addScheduledTask(new ScheduledCode());
    }
    
    class ScheduledCode implements Runnable
    {

        @Override
        public void run()
        {
            EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(user);
            if (player != null)
            {
                player.addExperience(200);
            }
        }
    }

}
