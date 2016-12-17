package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutTabComplete implements Packet<PacketListenerPlayOut> {

    private String[] a;

    public PacketPlayOutTabComplete() {}

    public PacketPlayOutTabComplete(String[] astring) {
        this.a = astring;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = new String[packetdataserializer.g()];

        for (int i = 0; i < this.a.length; ++i) {
            this.a[i] = packetdataserializer.e(32767);
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.d(this.a.length);
        String[] astring = this.a;
        int i = astring.length;

        for (int j = 0; j < i; ++j) {
            String s = astring[j];

            packetdataserializer.a(s);
        }

    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }
}
