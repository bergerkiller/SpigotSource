package org.spigotmc.netty;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.PrivateKey;
import java.util.AbstractList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import net.minecraft.server.Connection;
import net.minecraft.server.INetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet252KeyResponse;
import net.minecraft.server.Packet255KickDisconnect;
import net.minecraft.server.PendingConnection;
import net.minecraft.server.PlayerConnection;

/**
 * This class forms the basis of the Netty integration. It implements
 * {@link INetworkManager} and handles all events and inbound messages provided
 * by the upstream Netty process.
 */
public class NettyNetworkManager extends SimpleChannelInboundHandler<Packet> implements INetworkManager
{

    private static final ExecutorService threadPool = Executors.newCachedThreadPool( new ThreadFactoryBuilder().setNameFormat( "Async Packet Handler - %1$d" ).build() );
    private static final MinecraftServer server = MinecraftServer.getServer();
    private static final PrivateKey key = server.H().getPrivate();
    private static final NettyServerConnection serverConnection = (NettyServerConnection) server.ag();
    /*========================================================================*/
    private final Queue<Packet> syncPackets = new ConcurrentLinkedQueue<Packet>();
    private final List<Packet> highPriorityQueue = new AbstractList<Packet>()
    {
        @Override
        public void add(int index, Packet element)
        {
            // NOP
        }

        @Override
        public Packet get(int index)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size()
        {
            return 0;
        }
    };
    private volatile boolean connected;
    private Channel channel;
    private SocketAddress address;
    Connection connection;
    private SecretKey secret;
    private String dcReason;
    private Object[] dcArgs;
    private Socket socketAdaptor;
    private long writtenBytes;
    private PacketWriter writer;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        // Channel and address groundwork first
        channel = ctx.channel();
        address = channel.remoteAddress();
        // Then the socket adaptor
        socketAdaptor = NettySocketAdaptor.adapt( (SocketChannel) channel );
        // Followed by their first handler
        connection = new PendingConnection( server, this );
        writer = new PacketWriter();
        // Finally register the connection
        connected = true;
        serverConnection.register( (PendingConnection) connection );
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        a( "disconnect.endOfStream", new Object[ 0 ] );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        // TODO: Remove this once we are more stable
        // Bukkit.getServer().getLogger().severe("======================= Start Netty Debug Log =======================");
        // Bukkit.getServer().getLogger().log(Level.SEVERE, "Error caught whilst handling " + channel, cause);
        // Bukkit.getServer().getLogger().severe("======================= End Netty Debug Log =======================");
        // Disconnect with generic reason + exception
        a( "disconnect.genericReason", new Object[]
        {
            "Internal exception: " + cause
        } );
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, final Packet msg) throws Exception
    {
        if ( connected )
        {
            if ( msg instanceof Packet252KeyResponse )
            {
                secret = ( (Packet252KeyResponse) msg ).a( key );
                Cipher decrypt = NettyServerConnection.getCipher( Cipher.DECRYPT_MODE, secret );
                channel.pipeline().addBefore( "decoder", "decrypt", new CipherDecoder( decrypt ) );
            }

            if ( msg.a_() )
            {
                threadPool.submit( new Runnable()
                {
                    public void run()
                    {
                        Packet packet = PacketListener.callReceived( NettyNetworkManager.this, connection, msg );
                        if ( packet != null )
                        {
                            packet.handle( connection );
                        }
                    }
                } );
            } else
            {
                syncPackets.add( msg );
            }
        }
    }

    public Socket getSocket()
    {
        return socketAdaptor;
    }

    /**
     * setHandler. Set the {@link NetHandler} used to process received packets.
     *
     * @param nh the new {@link NetHandler} instance
     */
    public void a(Connection nh)
    {
        connection = nh;
    }

    /**
     * queue. Queue a packet for sending, or in this case send it to be write it
     * straight to the channel.
     *
     * @param packet the packet to queue
     */
    public void queue(final Packet packet)
    {
        // Only send if channel is still connected
        if ( connected )
        {
            // Process packet via handler
            final Packet packet0 = PacketListener.callQueued( this, connection, packet );
            highPriorityQueue.add( packet0 );
            // If handler indicates packet send
            if ( packet0 != null )
            {
                if ( channel.eventLoop().inEventLoop() )
                {
                    queue0( packet0 );
                } else
                {
                    channel.eventLoop().execute( new Runnable()
                    {
                        public void run()
                        {
                            queue0( packet0 );
                        }
                    } );
                }
            }
        }
    }

    private void queue0(Packet packet)
    {
        if ( packet instanceof Packet255KickDisconnect )
        {
            writer.lastFlush = 0;
        }

        writer.write( channel, this, packet );
        if ( packet instanceof Packet252KeyResponse )
        {
            Cipher encrypt = NettyServerConnection.getCipher( Cipher.ENCRYPT_MODE, secret );
            channel.pipeline().addBefore( "decoder", "encrypt", new CipherEncoder( encrypt ) );
        }
    }

    /**
     * wakeThreads. In Vanilla this method will interrupt the network read and
     * write threads, thus waking them.
     */
    public void a()
    {
    }

    /**
     * processPackets. Remove up to 1000 packets from the queue and process
     * them. This method should only be called from the main server thread.
     */
    public void b()
    {
        for ( int i = 1000; !syncPackets.isEmpty() && i >= 0; i-- )
        {
            if ( connection instanceof PendingConnection ? ( (PendingConnection) connection ).b : ( (PlayerConnection) connection ).disconnected )
            {
                syncPackets.clear();
                break;
            }

            Packet packet = PacketListener.callReceived( this, connection, syncPackets.poll() );
            if ( packet != null )
            {
                packet.handle( connection );
            }
        }

        // Disconnect via the handler - this performs all plugin related cleanup + logging
        if ( !connected && ( dcReason != null || dcArgs != null ) )
        {
            connection.a( dcReason, dcArgs );
        }
    }

    /**
     * getSocketAddress. Return the remote address of the connected user. It is
     * important that this method returns a value even after disconnect.
     *
     * @return the remote address of this connection
     */
    public SocketAddress getSocketAddress()
    {
        return address;
    }

    public void setSocketAddress(SocketAddress address)
    {
        this.address = address;
    }

    /**
     * close. Close and release all resources associated with this connection.
     */
    public void d()
    {
        if ( connected )
        {
            connected = false;
            channel.close();
        }
    }

    /**
     * queueSize. Return the number of packets in the low priority queue. In a
     * NIO environment this will always be 0.
     *
     * @return the size of the packet send queue
     */
    public int e()
    {
        return 0;
    }

    /**
     * networkShutdown. Shuts down this connection, storing the reason and
     * parameters, used to notify the current {@link Connection}.
     *
     * @param reason the main disconnect reason
     * @param arguments additional disconnect arguments, for example, the
     * exception which triggered the disconnect.
     */
    public void a(String reason, Object... arguments)
    {
        if ( connected )
        {
            dcReason = reason;
            dcArgs = arguments;
            d();
        }
    }

    public long getWrittenBytes()
    {
        return writtenBytes;
    }

    public void addWrittenBytes(int written)
    {
        writtenBytes += written;
    }
}
