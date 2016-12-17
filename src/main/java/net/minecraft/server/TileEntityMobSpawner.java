package net.minecraft.server;

import javax.annotation.Nullable;

public class TileEntityMobSpawner extends TileEntity implements ITickable {

    private final MobSpawnerAbstract a = new MobSpawnerAbstract() {
        public void a(int i) {
            TileEntityMobSpawner.this.world.playBlockAction(TileEntityMobSpawner.this.position, Blocks.MOB_SPAWNER, i, 0);
        }

        public World a() {
            return TileEntityMobSpawner.this.world;
        }

        public BlockPosition b() {
            return TileEntityMobSpawner.this.position;
        }

        public void a(MobSpawnerData mobspawnerdata) {
            super.a(mobspawnerdata);
            if (this.a() != null) {
                IBlockData iblockdata = this.a().getType(this.b());

                this.a().notify(TileEntityMobSpawner.this.position, iblockdata, iblockdata, 4);
            }

        }
    };

    public TileEntityMobSpawner() {}

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a.a(nbttagcompound);
    }

    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        this.a.b(nbttagcompound);
        return nbttagcompound;
    }

    public void c() {
        this.a.c();
    }

    @Nullable
    public PacketPlayOutTileEntityData getUpdatePacket() {
        return new PacketPlayOutTileEntityData(this.position, 1, this.E_());
    }

    public NBTTagCompound E_() {
        NBTTagCompound nbttagcompound = this.save(new NBTTagCompound());

        nbttagcompound.remove("SpawnPotentials");
        return nbttagcompound;
    }

    public boolean c(int i, int j) {
        return this.a.b(i) ? true : super.c(i, j);
    }

    public boolean isFilteredNBT() {
        return true;
    }

    public MobSpawnerAbstract getSpawner() {
        return this.a;
    }
}
