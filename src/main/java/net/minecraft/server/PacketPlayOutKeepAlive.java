package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutKeepAlive implements Packet<PacketListenerPlayOut> {

    private int a;

    public PacketPlayOutKeepAlive() {}

    public PacketPlayOutKeepAlive(int i) {
        this.a = i;
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.g();
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.d(this.a);
    }
}
