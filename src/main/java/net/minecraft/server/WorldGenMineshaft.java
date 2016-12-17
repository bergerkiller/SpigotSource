package net.minecraft.server;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class WorldGenMineshaft extends StructureGenerator {

    private double a = 0.004D;

    public WorldGenMineshaft() {}

    public String a() {
        return "Mineshaft";
    }

    public WorldGenMineshaft(Map<String, String> map) {
        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();

            if (((String) entry.getKey()).equals("chance")) {
                this.a = MathHelper.a((String) entry.getValue(), this.a);
            }
        }

    }

    protected boolean a(int i, int j) {
        return this.f.nextDouble() < this.a && this.f.nextInt(80) < Math.max(Math.abs(i), Math.abs(j));
    }

    protected StructureStart b(int i, int j) {
        return new WorldGenMineshaftStart(this.g, this.f, i, j);
    }
}
