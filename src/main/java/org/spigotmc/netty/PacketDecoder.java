package org.spigotmc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet254GetInfo;

/**
 * Packet decoding class backed by a reusable {@link DataInputStream} which
 * backs the input {@link ByteBuf}. Reads an unsigned byte packet header and
 * then decodes the packet accordingly.
 */
public class PacketDecoder extends ReplayingDecoder<ReadState>
{

    private DataInput input;
    private Packet packet;
    private boolean shutdown;

    public PacketDecoder()
    {
        super( ReadState.HEADER );
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        if ( shutdown )
        {
            in.readByte(); // Discard
            return;
        }

        if ( input == null )
        {
            input = new ByteBufInputStream( in );
        }

        try
        {
            while ( true )
            {
                switch ( state() )
                {
                    case HEADER:
                        int packetId = input.readUnsignedByte();
                        packet = Packet.a( MinecraftServer.getServer().getLogger(), packetId );
                        if ( packet == null )
                        {
                            throw new IOException( "Bad packet id " + packetId );
                        }
                        checkpoint( ReadState.DATA );
                    case DATA:
                        packet.a( input );
                        checkpoint( ReadState.HEADER );
                        out.add( packet );
                        if ( packet instanceof Packet254GetInfo )
                        {
                            shutdown = true;
                            return;
                        }
                        packet = null;
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }
        } catch ( EOFException ex )
        {
        }
    }
}
