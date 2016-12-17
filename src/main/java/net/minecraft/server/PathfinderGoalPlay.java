package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class PathfinderGoalPlay extends PathfinderGoal {

    private EntityVillager a;
    private EntityLiving b;
    private double c;
    private int d;

    public PathfinderGoalPlay(EntityVillager entityvillager, double d0) {
        this.a = entityvillager;
        this.c = d0;
        this.a(1);
    }

    public boolean a() {
        if (this.a.getAge() >= 0) {
            return false;
        } else if (this.a.getRandom().nextInt(400) != 0) {
            return false;
        } else {
            List list = this.a.world.a(EntityVillager.class, this.a.getBoundingBox().grow(6.0D, 3.0D, 6.0D));
            double d0 = Double.MAX_VALUE;
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityVillager entityvillager = (EntityVillager) iterator.next();

                if (entityvillager != this.a && !entityvillager.dc() && entityvillager.getAge() < 0) {
                    double d1 = entityvillager.h(this.a);

                    if (d1 <= d0) {
                        d0 = d1;
                        this.b = entityvillager;
                    }
                }
            }

            if (this.b == null) {
                Vec3D vec3d = RandomPositionGenerator.a(this.a, 16, 3);

                if (vec3d == null) {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean b() {
        return this.d > 0;
    }

    public void c() {
        if (this.b != null) {
            this.a.p(true);
        }

        this.d = 1000;
    }

    public void d() {
        this.a.p(false);
        this.b = null;
    }

    public void e() {
        --this.d;
        if (this.b != null) {
            if (this.a.h(this.b) > 4.0D) {
                this.a.getNavigation().a((Entity) this.b, this.c);
            }
        } else if (this.a.getNavigation().n()) {
            Vec3D vec3d = RandomPositionGenerator.a(this.a, 16, 3);

            if (vec3d == null) {
                return;
            }

            this.a.getNavigation().a(vec3d.x, vec3d.y, vec3d.z, this.c);
        }

    }
}
