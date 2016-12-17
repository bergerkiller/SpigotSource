package net.minecraft.server;

import javax.annotation.Nullable;

public class DragonControllerFly extends AbstractDragonController {

    private boolean b;
    private PathEntity c;
    private Vec3D d;

    public DragonControllerFly(EntityEnderDragon entityenderdragon) {
        super(entityenderdragon);
    }

    public void c() {
        if (this.b) {
            this.b = false;
            this.j();
        } else {
            BlockPosition blockposition = this.a.world.q(WorldGenEndTrophy.a);
            double d0 = this.a.d(blockposition);

            if (d0 > 100.0D) {
                this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);
            }
        }

    }

    public void d() {
        this.b = true;
        this.c = null;
        this.d = null;
    }

    private void j() {
        int i = this.a.o();
        Vec3D vec3d = this.a.a(1.0F);
        int j = this.a.l(-vec3d.x * 40.0D, 105.0D, -vec3d.z * 40.0D);

        if (this.a.cV() != null && this.a.cV().c() >= 0) {
            j %= 12;
            if (j < 0) {
                j += 12;
            }
        } else {
            j -= 12;
            j &= 7;
            j += 12;
        }

        this.c = this.a.a(i, j, (PathPoint) null);
        if (this.c != null) {
            this.c.a();
            this.k();
        }

    }

    private void k() {
        Vec3D vec3d = this.c.f();

        this.c.a();

        double d0;

        do {
            d0 = vec3d.y + (double) (this.a.getRandom().nextFloat() * 20.0F);
        } while (d0 < vec3d.y);

        this.d = new Vec3D(vec3d.x, d0, vec3d.z);
    }

    @Nullable
    public Vec3D g() {
        return this.d;
    }

    public DragonControllerPhase<DragonControllerFly> getControllerPhase() {
        return DragonControllerPhase.e;
    }
}
