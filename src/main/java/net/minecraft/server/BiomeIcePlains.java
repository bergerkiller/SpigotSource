package net.minecraft.server;

import java.util.Random;

public class BiomeIcePlains extends BiomeBase {

    private boolean y;
    private WorldGenPackedIce2 z = new WorldGenPackedIce2();
    private WorldGenPackedIce1 A = new WorldGenPackedIce1(4);

    public BiomeIcePlains(boolean flag, BiomeBase.a biomebase_a) {
        super(biomebase_a);
        this.y = flag;
        if (flag) {
            this.r = Blocks.SNOW.getBlockData();
        }

        this.v.clear();
        this.v.add(new BiomeBase.BiomeMeta(EntityRabbit.class, 4, 2, 3));
    }

    public void a(World world, Random random, BlockPosition blockposition) {
        if (this.y) {
            int i;
            int j;
            int k;

            for (i = 0; i < 3; ++i) {
                j = random.nextInt(16) + 8;
                k = random.nextInt(16) + 8;
                this.z.generate(world, random, world.getHighestBlockYAt(blockposition.a(j, 0, k)));
            }

            for (i = 0; i < 2; ++i) {
                j = random.nextInt(16) + 8;
                k = random.nextInt(16) + 8;
                this.A.generate(world, random, world.getHighestBlockYAt(blockposition.a(j, 0, k)));
            }
        }

        super.a(world, random, blockposition);
    }

    public WorldGenTreeAbstract a(Random random) {
        return new WorldGenTaiga2(false);
    }
}
