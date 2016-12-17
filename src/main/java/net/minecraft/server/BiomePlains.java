package net.minecraft.server;

import java.util.Random;

public class BiomePlains extends BiomeBase {

    protected boolean y;

    protected BiomePlains(boolean flag, BiomeBase.a biomebase_a) {
        super(biomebase_a);
        this.y = flag;
        this.v.add(new BiomeBase.BiomeMeta(EntityHorse.class, 5, 2, 6));
        this.t.z = -999;
        this.t.A = 4;
        this.t.B = 10;
    }

    public BlockFlowers.EnumFlowerVarient a(Random random, BlockPosition blockposition) {
        double d0 = BiomePlains.l.a((double) blockposition.getX() / 200.0D, (double) blockposition.getZ() / 200.0D);
        int i;

        if (d0 < -0.8D) {
            i = random.nextInt(4);
            switch (i) {
            case 0:
                return BlockFlowers.EnumFlowerVarient.ORANGE_TULIP;

            case 1:
                return BlockFlowers.EnumFlowerVarient.RED_TULIP;

            case 2:
                return BlockFlowers.EnumFlowerVarient.PINK_TULIP;

            case 3:
            default:
                return BlockFlowers.EnumFlowerVarient.WHITE_TULIP;
            }
        } else if (random.nextInt(3) > 0) {
            i = random.nextInt(3);
            return i == 0 ? BlockFlowers.EnumFlowerVarient.POPPY : (i == 1 ? BlockFlowers.EnumFlowerVarient.HOUSTONIA : BlockFlowers.EnumFlowerVarient.OXEYE_DAISY);
        } else {
            return BlockFlowers.EnumFlowerVarient.DANDELION;
        }
    }

    public void a(World world, Random random, BlockPosition blockposition) {
        double d0 = BiomePlains.l.a((double) (blockposition.getX() + 8) / 200.0D, (double) (blockposition.getZ() + 8) / 200.0D);
        int i;
        int j;
        int k;
        int l;

        if (d0 < -0.8D) {
            this.t.A = 15;
            this.t.B = 5;
        } else {
            this.t.A = 4;
            this.t.B = 10;
            BiomePlains.m.a(BlockTallPlant.EnumTallFlowerVariants.GRASS);

            for (i = 0; i < 7; ++i) {
                j = random.nextInt(16) + 8;
                k = random.nextInt(16) + 8;
                l = random.nextInt(world.getHighestBlockYAt(blockposition.a(j, 0, k)).getY() + 32);
                BiomePlains.m.generate(world, random, blockposition.a(j, l, k));
            }
        }

        if (this.y) {
            BiomePlains.m.a(BlockTallPlant.EnumTallFlowerVariants.SUNFLOWER);

            for (i = 0; i < 10; ++i) {
                j = random.nextInt(16) + 8;
                k = random.nextInt(16) + 8;
                l = random.nextInt(world.getHighestBlockYAt(blockposition.a(j, 0, k)).getY() + 32);
                BiomePlains.m.generate(world, random, blockposition.a(j, l, k));
            }
        }

        super.a(world, random, blockposition);
    }
}
