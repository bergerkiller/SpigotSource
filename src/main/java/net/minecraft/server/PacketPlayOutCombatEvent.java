package net.minecraft.server;

import java.io.IOException;

public class PacketPlayOutCombatEvent implements Packet<PacketListenerPlayOut> {

    public PacketPlayOutCombatEvent.EnumCombatEventType a;
    public int b;
    public int c;
    public int d;
    public IChatBaseComponent e;

    public PacketPlayOutCombatEvent() {}

    public PacketPlayOutCombatEvent(CombatTracker combattracker, PacketPlayOutCombatEvent.EnumCombatEventType packetplayoutcombatevent_enumcombateventtype) {
        this(combattracker, packetplayoutcombatevent_enumcombateventtype, true);
    }

    public PacketPlayOutCombatEvent(CombatTracker combattracker, PacketPlayOutCombatEvent.EnumCombatEventType packetplayoutcombatevent_enumcombateventtype, boolean flag) {
        this.a = packetplayoutcombatevent_enumcombateventtype;
        EntityLiving entityliving = combattracker.c();

        switch (PacketPlayOutCombatEvent.SyntheticClass_1.a[packetplayoutcombatevent_enumcombateventtype.ordinal()]) {
        case 1:
            this.d = combattracker.f();
            this.c = entityliving == null ? -1 : entityliving.getId();
            break;

        case 2:
            this.b = combattracker.h().getId();
            this.c = entityliving == null ? -1 : entityliving.getId();
            if (flag) {
                this.e = combattracker.getDeathMessage();
            } else {
                this.e = new ChatComponentText("");
            }
        }

    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = (PacketPlayOutCombatEvent.EnumCombatEventType) packetdataserializer.a(PacketPlayOutCombatEvent.EnumCombatEventType.class);
        if (this.a == PacketPlayOutCombatEvent.EnumCombatEventType.END_COMBAT) {
            this.d = packetdataserializer.g();
            this.c = packetdataserializer.readInt();
        } else if (this.a == PacketPlayOutCombatEvent.EnumCombatEventType.ENTITY_DIED) {
            this.b = packetdataserializer.g();
            this.c = packetdataserializer.readInt();
            this.e = packetdataserializer.f();
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.a((Enum) this.a);
        if (this.a == PacketPlayOutCombatEvent.EnumCombatEventType.END_COMBAT) {
            packetdataserializer.d(this.d);
            packetdataserializer.writeInt(this.c);
        } else if (this.a == PacketPlayOutCombatEvent.EnumCombatEventType.ENTITY_DIED) {
            packetdataserializer.d(this.b);
            packetdataserializer.writeInt(this.c);
            packetdataserializer.a(this.e);
        }

    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    static class SyntheticClass_1 {

        static final int[] a = new int[PacketPlayOutCombatEvent.EnumCombatEventType.values().length];

        static {
            try {
                PacketPlayOutCombatEvent.SyntheticClass_1.a[PacketPlayOutCombatEvent.EnumCombatEventType.END_COMBAT.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                PacketPlayOutCombatEvent.SyntheticClass_1.a[PacketPlayOutCombatEvent.EnumCombatEventType.ENTITY_DIED.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

        }
    }

    public static enum EnumCombatEventType {

        ENTER_COMBAT, END_COMBAT, ENTITY_DIED;

        private EnumCombatEventType() {}
    }
}
