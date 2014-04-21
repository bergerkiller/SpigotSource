package net.minecraft.server;

import java.io.IOException;

import net.minecraft.util.com.google.common.collect.BiMap;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.handler.codec.MessageToByteEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class PacketEncoder extends MessageToByteEncoder {

    private static final Logger a = LogManager.getLogger();
    private static final Marker b = MarkerManager.getMarker("PACKET_SENT", NetworkManager.b);
    private final NetworkStatistics c;

    public PacketEncoder(NetworkStatistics networkstatistics) {
        this.c = networkstatistics;
    }

    protected void a(ChannelHandlerContext channelhandlercontext, Packet packet, ByteBuf bytebuf) throws IOException {
        Integer integer = (Integer) ((BiMap) channelhandlercontext.channel().attr(NetworkManager.f).get()).inverse().get(packet.getClass());

        if (a.isDebugEnabled()) {
            a.debug(b, "OUT: [{}:{}] {}[{}]", new Object[] { channelhandlercontext.channel().attr(NetworkManager.d).get(), integer, packet.getClass().getName(), packet.b()});
        }

        if (integer == null) {
            throw new IOException("Can\'t serialize unregistered packet");
        } else {
            PacketDataSerializer packetdataserializer = new PacketDataSerializer(bytebuf, NetworkManager.getVersion(channelhandlercontext.channel())); // Spigot

            packetdataserializer.b(integer.intValue());
            packet.b(packetdataserializer);
            this.c.b(integer.intValue(), (long) packetdataserializer.readableBytes());
        }
    }

    protected void encode(ChannelHandlerContext channelhandlercontext, Object object, ByteBuf bytebuf) throws IOException {
        this.a(channelhandlercontext, (Packet) object, bytebuf);
    }
}
