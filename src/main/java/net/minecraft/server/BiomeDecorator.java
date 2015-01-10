package net.minecraft.server;

import java.util.Random;

public class BiomeDecorator {

    protected World a;
    protected Random b;
    protected BlockPosition c;
    protected CustomWorldSettingsFinal d;
    protected WorldGenerator e = new WorldGenClay(4);
    protected WorldGenerator f;
    protected WorldGenerator g;
    protected WorldGenerator h;
    protected WorldGenerator i;
    protected WorldGenerator j;
    protected WorldGenerator k;
    protected WorldGenerator l;
    protected WorldGenerator m;
    protected WorldGenerator n;
    protected WorldGenerator o;
    protected WorldGenerator p;
    protected WorldGenerator q;
    protected WorldGenerator r;
    protected WorldGenFlowers s;
    protected WorldGenerator t;
    protected WorldGenerator u;
    protected WorldGenerator v;
    protected WorldGenerator w;
    protected WorldGenerator x;
    protected WorldGenerator y;
    protected int z;
    protected int A;
    protected int B;
    protected int C;
    protected int D;
    protected int E;
    protected int F;
    protected int G;
    protected int H;
    protected int I;
    protected int J;
    protected int K;
    public boolean L;

    public BiomeDecorator() {
        this.f = new WorldGenSand(Blocks.SAND, 7);
        this.g = new WorldGenSand(Blocks.GRAVEL, 6);
        this.s = new WorldGenFlowers(Blocks.YELLOW_FLOWER, EnumFlowerVarient.DANDELION);
        this.t = new WorldGenMushrooms(Blocks.BROWN_MUSHROOM);
        this.u = new WorldGenMushrooms(Blocks.RED_MUSHROOM);
        this.v = new WorldGenHugeMushroom();
        this.w = new WorldGenReed();
        this.x = new WorldGenCactus();
        this.y = new WorldGenWaterLily();
        this.B = 2;
        this.C = 1;
        this.H = 1;
        this.I = 3;
        this.J = 1;
        this.L = true;
    }

    public void a(World world, Random random, BiomeBase biomebase, BlockPosition blockposition) {
        if (this.a != null) {
            throw new RuntimeException("Already decorating");
        } else {
            this.a = world;
            String s = world.getWorldData().getGeneratorOptions();

            if (s != null) {
                this.d = CustomWorldSettings.a(s).b();
            } else {
                this.d = CustomWorldSettings.a("").b();
            }

            this.b = random;
            this.c = blockposition;
            this.h = new WorldGenMinable(Blocks.DIRT.getBlockData(), this.d.I);
            this.i = new WorldGenMinable(Blocks.GRAVEL.getBlockData(), this.d.M);
            this.j = new WorldGenMinable(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, EnumStoneVariant.GRANITE), this.d.Q);
            this.k = new WorldGenMinable(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, EnumStoneVariant.DIORITE), this.d.U);
            this.l = new WorldGenMinable(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, EnumStoneVariant.ANDESITE), this.d.Y);
            this.m = new WorldGenMinable(Blocks.COAL_ORE.getBlockData(), this.d.ac);
            this.n = new WorldGenMinable(Blocks.IRON_ORE.getBlockData(), this.d.ag);
            this.o = new WorldGenMinable(Blocks.GOLD_ORE.getBlockData(), this.d.ak);
            this.p = new WorldGenMinable(Blocks.REDSTONE_ORE.getBlockData(), this.d.ao);
            this.q = new WorldGenMinable(Blocks.DIAMOND_ORE.getBlockData(), this.d.as);
            this.r = new WorldGenMinable(Blocks.LAPIS_ORE.getBlockData(), this.d.aw);
            this.a(biomebase);
            this.a = null;
            this.b = null;
        }
    }

    protected void a(BiomeBase biomebase) {
        this.a();

        int i;
        int j;
        int k;

        for (i = 0; i < this.I; ++i) {
            j = this.b.nextInt(16) + 8;
            k = this.b.nextInt(16) + 8;
            this.f.generate(this.a, this.b, this.a.r(this.c.a(j, 0, k)));
        }

        for (i = 0; i < this.J; ++i) {
            j = this.b.nextInt(16) + 8;
            k = this.b.nextInt(16) + 8;
            this.e.generate(this.a, this.b, this.a.r(this.c.a(j, 0, k)));
        }

        for (i = 0; i < this.H; ++i) {
            j = this.b.nextInt(16) + 8;
            k = this.b.nextInt(16) + 8;
            this.g.generate(this.a, this.b, this.a.r(this.c.a(j, 0, k)));
        }

        i = this.A;
        if (this.b.nextInt(10) == 0) {
            ++i;
        }

        int l;
        BlockPosition blockposition;

        for (j = 0; j < i; ++j) {
            k = this.b.nextInt(16) + 8;
            l = this.b.nextInt(16) + 8;
            WorldGenTreeAbstract worldgentreeabstract = biomebase.a(this.b);

            worldgentreeabstract.e();
            blockposition = this.a.getHighestBlockYAt(this.c.a(k, 0, l));
            if (worldgentreeabstract.generate(this.a, this.b, blockposition)) {
                worldgentreeabstract.a(this.a, this.b, blockposition);
            }
        }

        for (j = 0; j < this.K; ++j) {
            k = this.b.nextInt(16) + 8;
            l = this.b.nextInt(16) + 8;
            this.v.generate(this.a, this.b, this.a.getHighestBlockYAt(this.c.a(k, 0, l)));
        }

        int i1;

        for (j = 0; j < this.B; ++j) {
            k = this.b.nextInt(16) + 8;
            l = this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(this.a.getHighestBlockYAt(this.c.a(k, 0, l)).getY() + 32);
            blockposition = this.c.a(k, i1, l);
            EnumFlowerVarient enumflowervarient = biomebase.a(this.b, blockposition);
            BlockFlowers blockflowers = enumflowervarient.a().a();

            if (blockflowers.getMaterial() != Material.AIR) {
                this.s.a(blockflowers, enumflowervarient);
                this.s.generate(this.a, this.b, blockposition);
            }
        }

        for (j = 0; j < this.C; ++j) {
            k = this.b.nextInt(16) + 8;
            l = this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(this.a.getHighestBlockYAt(this.c.a(k, 0, l)).getY() * 2);
            biomebase.b(this.b).generate(this.a, this.b, this.c.a(k, i1, l));
        }

        for (j = 0; j < this.D; ++j) {
            k = this.b.nextInt(16) + 8;
            l = this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(this.a.getHighestBlockYAt(this.c.a(k, 0, l)).getY() * 2);
            (new WorldGenDeadBush()).generate(this.a, this.b, this.c.a(k, i1, l));
        }

        j = 0;

        while (j < this.z) {
            k = this.b.nextInt(16) + 8;
            l = this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(this.a.getHighestBlockYAt(this.c.a(k, 0, l)).getY() * 2);
            blockposition = this.c.a(k, i1, l);

            while (true) {
                if (blockposition.getY() > 0) {
                    BlockPosition blockposition1 = blockposition.down();

                    if (this.a.isEmpty(blockposition1)) {
                        blockposition = blockposition1;
                        continue;
                    }
                }

                this.y.generate(this.a, this.b, blockposition);
                ++j;
                break;
            }
        }

        for (j = 0; j < this.E; ++j) {
            if (this.b.nextInt(4) == 0) {
                k = this.b.nextInt(16) + 8;
                l = this.b.nextInt(16) + 8;
                BlockPosition blockposition2 = this.a.getHighestBlockYAt(this.c.a(k, 0, l));

                this.t.generate(this.a, this.b, blockposition2);
            }

            if (this.b.nextInt(8) == 0) {
                k = this.b.nextInt(16) + 8;
                l = this.b.nextInt(16) + 8;
                i1 = this.b.nextInt(this.a.getHighestBlockYAt(this.c.a(k, 0, l)).getY() * 2);
                blockposition = this.c.a(k, i1, l);
                this.u.generate(this.a, this.b, blockposition);
            }
        }

        if (this.b.nextInt(4) == 0) {
            j = this.b.nextInt(16) + 8;
            k = this.b.nextInt(16) + 8;
            l = this.b.nextInt(this.a.getHighestBlockYAt(this.c.a(j, 0, k)).getY() * 2);
            this.t.generate(this.a, this.b, this.c.a(j, l, k));
        }

        if (this.b.nextInt(8) == 0) {
            j = this.b.nextInt(16) + 8;
            k = this.b.nextInt(16) + 8;
            l = this.b.nextInt(this.a.getHighestBlockYAt(this.c.a(j, 0, k)).getY() * 2);
            this.u.generate(this.a, this.b, this.c.a(j, l, k));
        }

        for (j = 0; j < this.F; ++j) {
            k = this.b.nextInt(16) + 8;
            l = this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(this.a.getHighestBlockYAt(this.c.a(k, 0, l)).getY() * 2);
            this.w.generate(this.a, this.b, this.c.a(k, i1, l));
        }

        for (j = 0; j < 10; ++j) {
            k = this.b.nextInt(16) + 8;
            l = this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(this.a.getHighestBlockYAt(this.c.a(k, 0, l)).getY() * 2);
            this.w.generate(this.a, this.b, this.c.a(k, i1, l));
        }

        if (this.b.nextInt(32) == 0) {
            j = this.b.nextInt(16) + 8;
            k = this.b.nextInt(16) + 8;
            l = this.b.nextInt(this.a.getHighestBlockYAt(this.c.a(j, 0, k)).getY() * 2);
            (new WorldGenPumpkin()).generate(this.a, this.b, this.c.a(j, l, k));
        }

        for (j = 0; j < this.G; ++j) {
            k = this.b.nextInt(16) + 8;
            l = this.b.nextInt(16) + 8;
            i1 = this.b.nextInt(this.a.getHighestBlockYAt(this.c.a(k, 0, l)).getY() * 2);
            this.x.generate(this.a, this.b, this.c.a(k, i1, l));
        }

        if (this.L) {
            BlockPosition blockposition3;

            for (j = 0; j < 50; ++j) {
                blockposition3 = this.c.a(this.b.nextInt(16) + 8, this.b.nextInt(this.b.nextInt(248) + 8), this.b.nextInt(16) + 8);
                (new WorldGenLiquids(Blocks.FLOWING_WATER)).generate(this.a, this.b, blockposition3);
            }

            for (j = 0; j < 20; ++j) {
                blockposition3 = this.c.a(this.b.nextInt(16) + 8, this.b.nextInt(this.b.nextInt(this.b.nextInt(240) + 8) + 8), this.b.nextInt(16) + 8);
                (new WorldGenLiquids(Blocks.FLOWING_LAVA)).generate(this.a, this.b, blockposition3);
            }
        }

    }

    protected void a(int i, WorldGenerator worldgenerator, int j, int k) {
        int l;

        if (k < j) {
            l = j;
            j = k;
            k = l;
        } else if (k == j) {
            if (j < 255) {
                ++k;
            } else {
                --j;
            }
        }

        for (l = 0; l < i; ++l) {
            BlockPosition blockposition = this.c.a(this.b.nextInt(16), this.b.nextInt(k - j) + j, this.b.nextInt(16));

            worldgenerator.generate(this.a, this.b, blockposition);
        }

    }

    protected void b(int i, WorldGenerator worldgenerator, int j, int k) {
        for (int l = 0; l < i; ++l) {
            BlockPosition blockposition = this.c.a(this.b.nextInt(16), this.b.nextInt(k) + this.b.nextInt(k) + j - k, this.b.nextInt(16));

            worldgenerator.generate(this.a, this.b, blockposition);
        }

    }

    protected void a() {
        this.a(this.d.J, this.h, this.d.K, this.d.L);
        this.a(this.d.N, this.i, this.d.O, this.d.P);
        this.a(this.d.V, this.k, this.d.W, this.d.X);
        this.a(this.d.R, this.j, this.d.S, this.d.T);
        this.a(this.d.Z, this.l, this.d.aa, this.d.ab);
        this.a(this.d.ad, this.m, this.d.ae, this.d.af);
        this.a(this.d.ah, this.n, this.d.ai, this.d.aj);
        this.a(this.d.al, this.o, this.d.am, this.d.an);
        this.a(this.d.ap, this.p, this.d.aq, this.d.ar);
        this.a(this.d.at, this.q, this.d.au, this.d.av);
        this.b(this.d.ax, this.r, this.d.ay, this.d.az);
    }
}
