package net.minecraft.server;

import java.io.IOException;
import javax.annotation.Nullable;

public class PacketPlayOutSetSlot implements Packet<PacketListenerPlayOut> {

    private int a;
    private int b;
    private ItemStack c;

    public PacketPlayOutSetSlot() {}

    public PacketPlayOutSetSlot(int i, int j, @Nullable ItemStack itemstack) {
        this.a = i;
        this.b = j;
        this.c = itemstack == null ? null : itemstack.cloneItemStack();
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.readByte();
        this.b = packetdataserializer.readShort();
        this.c = packetdataserializer.k();
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.writeByte(this.a);
        packetdataserializer.writeShort(this.b);
        packetdataserializer.a(this.c);
    }
}
