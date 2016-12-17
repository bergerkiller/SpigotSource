package net.minecraft.server;

import javax.annotation.Nullable;

public class DragonControllerHold extends AbstractDragonController {

    private PathEntity b;
    private Vec3D c;
    private boolean d;

    public DragonControllerHold(EntityEnderDragon entityenderdragon) {
        super(entityenderdragon);
    }

    public DragonControllerPhase<DragonControllerHold> getControllerPhase() {
        return DragonControllerPhase.a;
    }

    public void c() {
        double d0 = this.c == null ? 0.0D : this.c.c(this.a.locX, this.a.locY, this.a.locZ);

        if (d0 < 100.0D || d0 > 22500.0D || this.a.positionChanged || this.a.B) {
            this.j();
        }

    }

    public void d() {
        this.b = null;
        this.c = null;
    }

    @Nullable
    public Vec3D g() {
        return this.c;
    }

    private void j() {
        int i;

        if (this.b != null && this.b.b()) {
            BlockPosition blockposition = this.a.world.q(new BlockPosition(WorldGenEndTrophy.a));

            i = this.a.cV() == null ? 0 : this.a.cV().c();
            if (this.a.getRandom().nextInt(i + 3) == 0) {
                this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.c);
                return;
            }

            double d0 = 64.0D;
            EntityHuman entityhuman = this.a.world.a(blockposition, d0, d0);

            if (entityhuman != null) {
                d0 = entityhuman.d(blockposition) / 512.0D;
            }

            if (entityhuman != null && (this.a.getRandom().nextInt(MathHelper.a((int) d0) + 2) == 0 || this.a.getRandom().nextInt(i + 2) == 0)) {
                this.a(entityhuman);
                return;
            }
        }

        if (this.b == null || this.b.b()) {
            int j = this.a.o();

            i = j;
            if (this.a.getRandom().nextInt(8) == 0) {
                this.d = !this.d;
                i = j + 6;
            }

            if (this.d) {
                ++i;
            } else {
                --i;
            }

            if (this.a.cV() != null && this.a.cV().c() >= 0) {
                i %= 12;
                if (i < 0) {
                    i += 12;
                }
            } else {
                i -= 12;
                i &= 7;
                i += 12;
            }

            this.b = this.a.a(j, i, (PathPoint) null);
            if (this.b != null) {
                this.b.a();
            }
        }

        this.k();
    }

    private void a(EntityHuman entityhuman) {
        this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.b);
        ((DragonControllerStrafe) this.a.getDragonControllerManager().b(DragonControllerPhase.b)).a(entityhuman);
    }

    private void k() {
        if (this.b != null && !this.b.b()) {
            Vec3D vec3d = this.b.f();

            this.b.a();
            double d0 = vec3d.x;
            double d1 = vec3d.z;

            double d2;

            do {
                d2 = vec3d.y + (double) (this.a.getRandom().nextFloat() * 20.0F);
            } while (d2 < vec3d.y);

            this.c = new Vec3D(d0, d2, d1);
        }

    }

    public void a(EntityEnderCrystal entityendercrystal, BlockPosition blockposition, DamageSource damagesource, @Nullable EntityHuman entityhuman) {
        if (entityhuman != null) {
            this.a(entityhuman);
        }

    }
}
