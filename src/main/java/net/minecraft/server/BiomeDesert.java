package net.minecraft.server;

import java.util.Random;

public class BiomeDesert extends BiomeBase {

    public BiomeDesert(BiomeBase.a biomebase_a) {
        super(biomebase_a);
        this.v.clear();
        this.r = Blocks.SAND.getBlockData();
        this.s = Blocks.SAND.getBlockData();
        this.t.z = -999;
        this.t.C = 2;
        this.t.E = 50;
        this.t.F = 10;
        this.v.clear();
        this.v.add(new BiomeBase.BiomeMeta(EntityRabbit.class, 4, 2, 3));
    }

    public void a(World world, Random random, BlockPosition blockposition) {
        super.a(world, random, blockposition);
        if (random.nextInt(1000) == 0) {
            int i = random.nextInt(16) + 8;
            int j = random.nextInt(16) + 8;
            BlockPosition blockposition1 = world.getHighestBlockYAt(blockposition.a(i, 0, j)).up();

            (new WorldGenDesertWell()).generate(world, random, blockposition1);
        }

    }
}
