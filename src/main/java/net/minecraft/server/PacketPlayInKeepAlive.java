package net.minecraft.server;

import java.io.IOException;

public class PacketPlayInKeepAlive implements Packet<PacketListenerPlayIn> {

    private int a;

    public PacketPlayInKeepAlive() {}

    public void a(PacketListenerPlayIn packetlistenerplayin) {
        packetlistenerplayin.a(this);
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.g();
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.d(this.a);
    }

    public int a() {
        return this.a;
    }
}
