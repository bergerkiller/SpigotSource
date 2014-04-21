package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutOpenWindow extends Packet {

    private int a;
    private int b;
    private String c;
    private int d;
    private boolean e;
    private int f;

    public PacketPlayOutOpenWindow() {}

    public PacketPlayOutOpenWindow(int i, int j, String s, int k, boolean flag) {
        if (s.length() > 32) s = s.substring( 0, 32 ); // Spigot - Cap window name to prevent client disconnects
        this.a = i;
        this.b = j;
        this.c = s;
        this.d = k;
        this.e = flag;
    }

    public PacketPlayOutOpenWindow(int i, int j, String s, int k, boolean flag, int l) {
        this(i, j, s, k, flag);
        this.f = l;
    }

    public void a(PacketPlayOutListener packetplayoutlistener) {
        packetplayoutlistener.a(this);
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.readUnsignedByte();
        this.b = packetdataserializer.readUnsignedByte();
        this.c = packetdataserializer.c(32);
        this.d = packetdataserializer.readUnsignedByte();
        this.e = packetdataserializer.readBoolean();
        if (this.b == 11) {
            this.f = packetdataserializer.readInt();
        }
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.writeByte(this.a);
        packetdataserializer.writeByte(this.b);
        packetdataserializer.a(this.c);
        packetdataserializer.writeByte(this.d);
        packetdataserializer.writeBoolean(this.e);
        if (this.b == 11) {
            packetdataserializer.writeInt(this.f);
        }
    }

    public void handle(PacketListener packetlistener) {
        this.a((PacketPlayOutListener) packetlistener);
    }
}
