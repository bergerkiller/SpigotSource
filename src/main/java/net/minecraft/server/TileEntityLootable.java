package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public abstract class TileEntityLootable extends TileEntityContainer implements ILootable {

    protected MinecraftKey m;
    protected long n;

    public TileEntityLootable() {}

    protected boolean d(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.hasKeyOfType("LootTable", 8)) {
            this.m = new MinecraftKey(nbttagcompound.getString("LootTable"));
            this.n = nbttagcompound.getLong("LootTableSeed");
            return true;
        } else {
            return false;
        }
    }

    protected boolean e(NBTTagCompound nbttagcompound) {
        if (this.m != null) {
            nbttagcompound.setString("LootTable", this.m.toString());
            if (this.n != 0L) {
                nbttagcompound.setLong("LootTableSeed", this.n);
            }

            return true;
        } else {
            return false;
        }
    }

    protected void d(@Nullable EntityHuman entityhuman) {
        if (this.m != null) {
            LootTable loottable = this.world.ak().a(this.m);

            this.m = null;
            Random random;

            if (this.n == 0L) {
                random = new Random();
            } else {
                random = new Random(this.n);
            }

            LootTableInfo.a loottableinfo_a = new LootTableInfo.a((WorldServer) this.world);

            if (entityhuman != null) {
                loottableinfo_a.a(entityhuman.dc());
            }

            loottable.a(this, random, loottableinfo_a.a());
        }

    }

    public MinecraftKey b() {
        return this.m;
    }

    public void a(MinecraftKey minecraftkey, long i) {
        this.m = minecraftkey;
        this.n = i;
    }
}
