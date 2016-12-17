package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class PathfinderGoalTakeFlower extends PathfinderGoal {

    private EntityVillager a;
    private EntityIronGolem b;
    private int c;
    private boolean d;

    public PathfinderGoalTakeFlower(EntityVillager entityvillager) {
        this.a = entityvillager;
        this.a(3);
    }

    public boolean a() {
        if (this.a.getAge() >= 0) {
            return false;
        } else if (!this.a.world.B()) {
            return false;
        } else {
            List list = this.a.world.a(EntityIronGolem.class, this.a.getBoundingBox().grow(6.0D, 2.0D, 6.0D));

            if (list.isEmpty()) {
                return false;
            } else {
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    EntityIronGolem entityirongolem = (EntityIronGolem) iterator.next();

                    if (entityirongolem.db() > 0) {
                        this.b = entityirongolem;
                        break;
                    }
                }

                return this.b != null;
            }
        }
    }

    public boolean b() {
        return this.b.db() > 0;
    }

    public void c() {
        this.c = this.a.getRandom().nextInt(320);
        this.d = false;
        this.b.getNavigation().o();
    }

    public void d() {
        this.b = null;
        this.a.getNavigation().o();
    }

    public void e() {
        this.a.getControllerLook().a(this.b, 30.0F, 30.0F);
        if (this.b.db() == this.c) {
            this.a.getNavigation().a((Entity) this.b, 0.5D);
            this.d = true;
        }

        if (this.d && this.a.h(this.b) < 4.0D) {
            this.b.a(false);
            this.a.getNavigation().o();
        }

    }
}
