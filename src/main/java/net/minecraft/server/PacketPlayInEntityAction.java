package net.minecraft.server;

import java.io.IOException;

public class PacketPlayInEntityAction implements Packet<PacketListenerPlayIn> {

    private int a;
    private PacketPlayInEntityAction.EnumPlayerAction animation;
    private int c;

    public PacketPlayInEntityAction() {}

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.g();
        this.animation = (PacketPlayInEntityAction.EnumPlayerAction) packetdataserializer.a(PacketPlayInEntityAction.EnumPlayerAction.class);
        this.c = packetdataserializer.g();
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.d(this.a);
        packetdataserializer.a((Enum) this.animation);
        packetdataserializer.d(this.c);
    }

    public void a(PacketListenerPlayIn packetlistenerplayin) {
        packetlistenerplayin.a(this);
    }

    public PacketPlayInEntityAction.EnumPlayerAction b() {
        return this.animation;
    }

    public int c() {
        return this.c;
    }

    public static enum EnumPlayerAction {

        START_SNEAKING, STOP_SNEAKING, STOP_SLEEPING, START_SPRINTING, STOP_SPRINTING, START_RIDING_JUMP, STOP_RIDING_JUMP, OPEN_INVENTORY, START_FALL_FLYING;

        private EnumPlayerAction() {}
    }
}
