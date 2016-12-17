package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutEntityDestroy implements Packet<PacketListenerPlayOut> {

    private int[] a;

    public PacketPlayOutEntityDestroy() {}

    public PacketPlayOutEntityDestroy(int... aint) {
        this.a = aint;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = new int[packetdataserializer.g()];

        for (int i = 0; i < this.a.length; ++i) {
            this.a[i] = packetdataserializer.g();
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.d(this.a.length);

        for (int i = 0; i < this.a.length; ++i) {
            packetdataserializer.d(this.a[i]);
        }

    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }
}
