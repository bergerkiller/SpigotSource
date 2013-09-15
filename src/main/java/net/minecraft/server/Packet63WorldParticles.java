package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;

public class Packet63WorldParticles extends Packet {

    private String a;
    private float b;
    private float c;
    private float d;
    private float e;
    private float f;
    private float g;
    private float h;
    private int i;

    public Packet63WorldParticles() {}

    // Spigot start - Added constructor
    public Packet63WorldParticles(String particleName, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        a = particleName;
        b = x;
        c = y;
        d = z;
        e = offsetX;
        f = offsetY;
        g = offsetZ;
        h = speed;
        i = count;
    }
    // Spigot end

    public void a(DataInput datainput) throws java.io.IOException { // Spigot - throws
        this.a = a(datainput, 64);
        this.b = datainput.readFloat();
        this.c = datainput.readFloat();
        this.d = datainput.readFloat();
        this.e = datainput.readFloat();
        this.f = datainput.readFloat();
        this.g = datainput.readFloat();
        this.h = datainput.readFloat();
        this.i = datainput.readInt();
    }

    public void a(DataOutput dataoutput) throws java.io.IOException { // Spigot - throws
        a(this.a, dataoutput);
        dataoutput.writeFloat(this.b);
        dataoutput.writeFloat(this.c);
        dataoutput.writeFloat(this.d);
        dataoutput.writeFloat(this.e);
        dataoutput.writeFloat(this.f);
        dataoutput.writeFloat(this.g);
        dataoutput.writeFloat(this.h);
        dataoutput.writeInt(this.i);
    }

    public void handle(Connection connection) {
        connection.a(this);
    }

    public int a() {
        return 64;
    }
}
