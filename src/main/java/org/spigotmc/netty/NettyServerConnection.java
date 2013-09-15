package org.spigotmc.netty;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PendingConnection;
import net.minecraft.server.ServerConnection;
import org.bukkit.Bukkit;
import org.spigotmc.SpigotConfig;

/**
 * This is the NettyServerConnection class. It implements
 * {@link ServerConnection} and is the main interface between the Minecraft
 * server and this NIO implementation. It handles starting, stopping and
 * processing the Netty backend.
 */
public class NettyServerConnection extends ServerConnection
{

    private final ChannelFuture socket;
    private static EventLoopGroup group;
    private final Map<InetAddress, Long> throttle = new HashMap<InetAddress, Long>();
    private final List<PendingConnection> pending = Collections.synchronizedList( new ArrayList<PendingConnection>() );

    public void unThrottle(InetAddress address)
    {
        if ( address != null )
        {
            synchronized ( throttle )
            {
                throttle.remove( address );
            }
        }
    }

    public boolean throttle(InetAddress address)
    {
        long currentTime = System.currentTimeMillis();
        synchronized ( throttle )
        {
            Long value = throttle.get( address );
            if ( value != null && !address.isLoopbackAddress() && currentTime - value < d().server.getConnectionThrottle() )
            {
                throttle.put( address, currentTime );
                return true;
            }

            throttle.put( address, currentTime );
        }
        return false;
    }

    public NettyServerConnection(final MinecraftServer ms, InetAddress host, int port)
    {
        super( ms );
        if ( group == null )
        {
            group = new NioEventLoopGroup( SpigotConfig.nettyThreads, new ThreadFactoryBuilder().setNameFormat( "Netty IO Thread - %1$d" ).build() );
        }

        socket = new ServerBootstrap().channel( NioServerSocketChannel.class ).childHandler( new ChannelInitializer()
        {
            @Override
            public void initChannel(Channel ch) throws Exception
            {
                // Check the throttle
                if ( throttle( ( (InetSocketAddress) ch.remoteAddress() ).getAddress() ) )
                {
                    ch.close();
                    return;
                }
                // Set IP_TOS
                try
                {
                    ch.config().setOption( ChannelOption.IP_TOS, 0x18 );
                } catch ( ChannelException ex )
                {
                    // IP_TOS is not supported (Windows XP / Windows Server 2003)
                }
                try
                {
                    ch.config().setOption( ChannelOption.TCP_NODELAY, false );
                } catch ( ChannelException ex )
                {
                    // TCP_NODELAY is not supported (Mac)
                }

                NettyNetworkManager networkManager = new NettyNetworkManager();
                ch.pipeline()
                        .addLast( "timer", new ReadTimeoutHandler( 30 ) )
                        .addLast( "decoder", new PacketDecoder() )
                        .addLast( "manager", networkManager );
            }
        } ).group( group ).localAddress( host, port ).bind().syncUninterruptibly();
    }

    /**
     * Shutdown. This method is called when the server is shutting down and the
     * server socket and all clients should be terminated with no further
     * action.
     */
    @Override
    public void a()
    {
        socket.channel().close().syncUninterruptibly();
    }

    @Override
    public void b()
    {
        super.b(); // pulse PlayerConnections
        for ( int i = 0; i < pending.size(); ++i )
        {
            PendingConnection connection = pending.get( i );

            try
            {
                connection.d();
            } catch ( Exception ex )
            {
                connection.disconnect( "Internal server error" );
                Bukkit.getServer().getLogger().log( Level.WARNING, "Failed to handle packet: " + ex, ex );
            }

            if ( connection.b )
            {
                pending.remove( i-- );
            }
        }
    }

    public void register(PendingConnection conn)
    {
        pending.add( conn );
    }

    /**
     * Return a Minecraft compatible cipher instance from the specified key.
     *
     * @param opMode the mode to initialize the cipher in
     * @param key to use as the initial vector
     * @return the initialized cipher
     */
    public static Cipher getCipher(int opMode, Key key)
    {
        try
        {
            Cipher cip = Cipher.getInstance( "AES/CFB8/NoPadding" );
            cip.init( opMode, key, new IvParameterSpec( key.getEncoded() ) );
            return cip;
        } catch ( GeneralSecurityException ex )
        {
            throw new RuntimeException( ex );
        }
    }
}
