package net.minecraft.server;

import java.io.IOException;
import org.apache.commons.lang3.Validate;

public class PacketPlayOutNamedSoundEffect implements Packet<PacketListenerPlayOut> {

    private SoundEffect a;
    private SoundCategory b;
    private int c;
    private int d;
    private int e;
    private float f;
    private int g;

    public PacketPlayOutNamedSoundEffect() {}

    public PacketPlayOutNamedSoundEffect(SoundEffect soundeffect, SoundCategory soundcategory, double d0, double d1, double d2, float f, float f1) {
        Validate.notNull(soundeffect, "sound", new Object[0]);
        this.a = soundeffect;
        this.b = soundcategory;
        this.c = (int) (d0 * 8.0D);
        this.d = (int) (d1 * 8.0D);
        this.e = (int) (d2 * 8.0D);
        this.f = f;
        this.g = (int) (f1 * 63.0F);
        f1 = MathHelper.a(f1, 0.0F, 255.0F);
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = (SoundEffect) SoundEffect.a.getId(packetdataserializer.g());
        this.b = (SoundCategory) packetdataserializer.a(SoundCategory.class);
        this.c = packetdataserializer.readInt();
        this.d = packetdataserializer.readInt();
        this.e = packetdataserializer.readInt();
        this.f = packetdataserializer.readFloat();
        this.g = packetdataserializer.readUnsignedByte();
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.d(SoundEffect.a.a((Object) this.a));
        packetdataserializer.a((Enum) this.b);
        packetdataserializer.writeInt(this.c);
        packetdataserializer.writeInt(this.d);
        packetdataserializer.writeInt(this.e);
        packetdataserializer.writeFloat(this.f);
        packetdataserializer.writeByte(this.g);
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }
}
