package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutBlockAction implements Packet<PacketListenerPlayOut> {

    private BlockPosition a;
    private int b;
    private int c;
    private Block d;

    public PacketPlayOutBlockAction() {}

    public PacketPlayOutBlockAction(BlockPosition blockposition, Block block, int i, int j) {
        this.a = blockposition;
        this.b = i;
        this.c = j;
        this.d = block;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.e();
        this.b = packetdataserializer.readUnsignedByte();
        this.c = packetdataserializer.readUnsignedByte();
        this.d = Block.getById(packetdataserializer.g() & 4095);
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.a(this.a);
        packetdataserializer.writeByte(this.b);
        packetdataserializer.writeByte(this.c);
        packetdataserializer.d(Block.getId(this.d) & 4095);
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }
}
