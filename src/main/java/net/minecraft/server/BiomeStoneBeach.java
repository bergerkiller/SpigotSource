package net.minecraft.server;

public class BiomeStoneBeach extends BiomeBase {

    public BiomeStoneBeach(BiomeBase.a biomebase_a) {
        super(biomebase_a);
        this.v.clear();
        this.r = Blocks.STONE.getBlockData();
        this.s = Blocks.STONE.getBlockData();
        this.t.z = -999;
        this.t.C = 0;
        this.t.E = 0;
        this.t.F = 0;
    }
}
