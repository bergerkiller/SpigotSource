package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutScoreboardObjective implements Packet<PacketListenerPlayOut> {

    private String a;
    private String b;
    private IScoreboardCriteria.EnumScoreboardHealthDisplay c;
    private int d;

    public PacketPlayOutScoreboardObjective() {}

    public PacketPlayOutScoreboardObjective(ScoreboardObjective scoreboardobjective, int i) {
        this.a = scoreboardobjective.getName();
        this.b = scoreboardobjective.getDisplayName();
        this.c = scoreboardobjective.getCriteria().c();
        this.d = i;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.e(16);
        this.d = packetdataserializer.readByte();
        if (this.d == 0 || this.d == 2) {
            this.b = packetdataserializer.e(32);
            this.c = IScoreboardCriteria.EnumScoreboardHealthDisplay.a(packetdataserializer.e(16));
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.a(this.a);
        packetdataserializer.writeByte(this.d);
        if (this.d == 0 || this.d == 2) {
            packetdataserializer.a(this.b);
            packetdataserializer.a(this.c.a());
        }

    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }
}
