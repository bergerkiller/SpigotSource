package net.minecraft.server;

import java.io.IOException;

public class PacketPlayInBlockPlace implements Packet<PacketListenerPlayIn> {

    private EnumHand a;

    public long timestamp; // CraftBukkit

    public PacketPlayInBlockPlace() {}

    public PacketPlayInBlockPlace(EnumHand enumhand) {
        this.a = enumhand;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.timestamp = System.currentTimeMillis(); // CraftBukkit
        this.a = (EnumHand) packetdataserializer.a(EnumHand.class);
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.a((Enum) this.a);
    }

    public void a(PacketListenerPlayIn packetlistenerplayin) {
        packetlistenerplayin.a(this);
    }

    public EnumHand a() {
        return this.a;
    }
}
