package net.minecraft.server;

public class BiomeBeach extends BiomeBase {

    public BiomeBeach(BiomeBase.a biomebase_a) {
        super(biomebase_a);
        this.v.clear();
        this.r = Blocks.SAND.getBlockData();
        this.s = Blocks.SAND.getBlockData();
        this.t.z = -999;
        this.t.C = 0;
        this.t.E = 0;
        this.t.F = 0;
    }
}
