package net.minecraft.server;

import java.util.Random;

public class WorldGenEndCity extends StructureGenerator {

    private int a = 20;
    private int b = 11;
    private final ChunkProviderTheEnd d;

    public WorldGenEndCity(ChunkProviderTheEnd chunkprovidertheend) {
        this.d = chunkprovidertheend;
    }

    public String a() {
        return "EndCity";
    }

    protected boolean a(int i, int j) {
        int k = i;
        int l = j;

        if (i < 0) {
            i -= this.a - 1;
        }

        if (j < 0) {
            j -= this.a - 1;
        }

        int i1 = i / this.a;
        int j1 = j / this.a;
        Random random = this.g.a(i1, j1, 10387313);

        i1 *= this.a;
        j1 *= this.a;
        i1 += (random.nextInt(this.a - this.b) + random.nextInt(this.a - this.b)) / 2;
        j1 += (random.nextInt(this.a - this.b) + random.nextInt(this.a - this.b)) / 2;
        return k == i1 && l == j1 && this.d.c(k, l);
    }

    protected StructureStart b(int i, int j) {
        return new WorldGenEndCity.Start(this.g, this.d, this.f, i, j);
    }

    public static class Start extends StructureStart {

        private boolean c;

        public Start() {}

        public Start(World world, ChunkProviderTheEnd chunkprovidertheend, Random random, int i, int j) {
            super(i, j);
            this.a(world, chunkprovidertheend, random, i, j);
        }

        private void a(World world, ChunkProviderTheEnd chunkprovidertheend, Random random, int i, int j) {
            EnumBlockRotation enumblockrotation = EnumBlockRotation.values()[random.nextInt(EnumBlockRotation.values().length)];
            ChunkSnapshot chunksnapshot = new ChunkSnapshot();

            chunkprovidertheend.a(i, j, chunksnapshot);
            byte b0 = 5;
            byte b1 = 5;

            if (enumblockrotation == EnumBlockRotation.CLOCKWISE_90) {
                b0 = -5;
            } else if (enumblockrotation == EnumBlockRotation.CLOCKWISE_180) {
                b0 = -5;
                b1 = -5;
            } else if (enumblockrotation == EnumBlockRotation.COUNTERCLOCKWISE_90) {
                b1 = -5;
            }

            int k = chunksnapshot.a(7, 7);
            int l = chunksnapshot.a(7, 7 + b1);
            int i1 = chunksnapshot.a(7 + b0, 7);
            int j1 = chunksnapshot.a(7 + b0, 7 + b1);
            int k1 = Math.min(Math.min(k, l), Math.min(i1, j1));

            if (k1 < 60) {
                this.c = false;
            } else {
                BlockPosition blockposition = new BlockPosition(i * 16 + 8, k1, j * 16 + 8);

                WorldGenEndCityPieces.a(blockposition, enumblockrotation, this.a, random);
                this.d();
                this.c = true;
            }
        }

        public boolean a() {
            return this.c;
        }

        public void a(NBTTagCompound nbttagcompound) {
            super.a(nbttagcompound);
        }

        public void b(NBTTagCompound nbttagcompound) {
            super.b(nbttagcompound);
        }
    }
}
