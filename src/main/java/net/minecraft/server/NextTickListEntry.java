package net.minecraft.server;

public class NextTickListEntry implements Comparable {

    private static long g;
    public int a;
    public int b;
    public int c;
    public int d;
    public long e;
    public int f;
    private long h;

    public NextTickListEntry(int i, int j, int k, int l) {
        this.h = (long) (g++);
        this.a = i;
        this.b = j;
        this.c = k;
        this.d = l;
    }

    public boolean equals(Object object) {
        if (!(object instanceof NextTickListEntry)) {
            return false;
        } else {
            NextTickListEntry nextticklistentry = (NextTickListEntry) object;

            return this.a == nextticklistentry.a && this.b == nextticklistentry.b && this.c == nextticklistentry.c && Block.b(this.d, nextticklistentry.d);
        }
    }

    public int hashCode() {
        return (this.a * 257) ^ this.b ^ (this.c * 60217); // Spigot - better hash
    }

    public NextTickListEntry a(long i) {
        this.e = i;
        return this;
    }

    public void a(int i) {
        this.f = i;
    }

    public int compareTo(NextTickListEntry nextticklistentry) {
        return this.e < nextticklistentry.e ? -1 : (this.e > nextticklistentry.e ? 1 : (this.f != nextticklistentry.f ? this.f - nextticklistentry.f : (this.h < nextticklistentry.h ? -1 : (this.h > nextticklistentry.h ? 1 : 0))));
    }

    public String toString() {
        return this.d + ": (" + this.a + ", " + this.b + ", " + this.c + "), " + this.e + ", " + this.f + ", " + this.h;
    }

    public int compareTo(Object object) {
        return this.compareTo((NextTickListEntry) object);
    }
}
