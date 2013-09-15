package org.spigotmc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;
import io.netty.handler.codec.EncoderException;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import net.minecraft.server.Packet;
import net.minecraft.server.PendingConnection;

/**
 * Netty encoder which takes a packet and encodes it, and adds a byte packet id
 * header.
 */
public class PacketWriter
{

    private static final int FLUSH_TIME = 1;
    /*========================================================================*/
    long lastFlush;
    private final Queue<Packet> queue = new ArrayDeque<Packet>( 64 );

    void write(Channel channel, NettyNetworkManager networkManager, Packet msg)
    {
        // Append messages to queue
        queue.add( msg );

        // If we are not in the pending connect phase, and we have not reached our timer
        if ( !( networkManager.connection instanceof PendingConnection ) && System.currentTimeMillis() - lastFlush < FLUSH_TIME )
        {
            return;
        }
        // Update our last write time
        lastFlush = System.currentTimeMillis();

        // Since we are writing in batches it can be useful to guess the size of our output to limit memcpy
        int estimatedSize = 0;
        for ( Packet packet : queue )
        {
            estimatedSize += packet.a();
        }
        // Allocate an output buffer of estimated size
        ByteBuf outBuf = channel.alloc().buffer( estimatedSize );
        // And a stream to which we can write this buffer to
        DataOutput dataOut = new ByteBufOutputStream( outBuf );
        // If we aren't a success, we free the buf in the finally
        boolean success = false;

        try
        {
            // Iterate through all packets, this is safe as we know we will only ever get packets in the pipeline
            for ( Packet packet : queue )
            {
                // Write packet ID
                outBuf.writeByte( packet.n() );
                // Write packet data
                try
                {
                    packet.a( dataOut );
                } catch ( IOException ex )
                {
                    throw new EncoderException( ex );
                }
            }
            // Add to the courtesy API providing number of written bytes
            networkManager.addWrittenBytes( outBuf.readableBytes() );
            // Let Netty handle any errors from here on
            success = true;
            // Write down our single ByteBuf
            channel.writeAndFlush( outBuf );
        } finally
        {
            // Reset packet queue
            queue.clear();
            // If Netty didn't handle the freeing because we didn't get there, we must
            if ( !success )
            {
                outBuf.release();
            }
        }
    }
}
