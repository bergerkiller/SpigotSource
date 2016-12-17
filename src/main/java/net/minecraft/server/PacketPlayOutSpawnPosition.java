package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutSpawnPosition implements Packet<PacketListenerPlayOut> {

    public BlockPosition position;

    public PacketPlayOutSpawnPosition() {}

    public PacketPlayOutSpawnPosition(BlockPosition blockposition) {
        this.position = blockposition;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.position = packetdataserializer.e();
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.a(this.position);
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }
}
