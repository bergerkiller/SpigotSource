package net.minecraft.server;

import java.util.Random;

public class BiomeOcean extends BiomeBase {

    public BiomeOcean(BiomeBase.a biomebase_a) {
        super(biomebase_a);
        this.v.clear();
    }

    public BiomeBase.EnumTemperature h() {
        return BiomeBase.EnumTemperature.OCEAN;
    }

    public void a(World world, Random random, ChunkSnapshot chunksnapshot, int i, int j, double d0) {
        super.a(world, random, chunksnapshot, i, j, d0);
    }
}
