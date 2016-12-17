package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class PathfinderGoalHurtByTarget extends PathfinderGoalTarget {

    private final boolean a;
    private int b;
    private final Class<?>[] c;

    public PathfinderGoalHurtByTarget(EntityCreature entitycreature, boolean flag, Class<?>... aclass) {
        super(entitycreature, true);
        this.a = flag;
        this.c = aclass;
        this.a(1);
    }

    public boolean a() {
        int i = this.e.bI();
        EntityLiving entityliving = this.e.getLastDamager();

        return i != this.b && entityliving != null && this.a(entityliving, false);
    }

    public void c() {
        this.e.setGoalTarget(this.e.getLastDamager(), org.bukkit.event.entity.EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY, true); // CraftBukkit - reason
        this.g = this.e.getGoalTarget();
        this.b = this.e.bI();
        this.h = 300;
        if (this.a) {
            double d0 = this.f();
            List list = this.e.world.a(this.e.getClass(), (new AxisAlignedBB(this.e.locX, this.e.locY, this.e.locZ, this.e.locX + 1.0D, this.e.locY + 1.0D, this.e.locZ + 1.0D)).grow(d0, 10.0D, d0));
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityCreature entitycreature = (EntityCreature) iterator.next();

                if (this.e != entitycreature && entitycreature.getGoalTarget() == null && (!(this.e instanceof EntityTameableAnimal) || ((EntityTameableAnimal) this.e).getOwner() == ((EntityTameableAnimal) entitycreature).getOwner()) && !entitycreature.r(this.e.getLastDamager())) {
                    boolean flag = false;
                    Class[] aclass = this.c;
                    int i = aclass.length;

                    for (int j = 0; j < i; ++j) {
                        Class oclass = aclass[j];

                        if (entitycreature.getClass() == oclass) {
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        this.a(entitycreature, this.e.getLastDamager());
                    }
                }
            }
        }

        super.c();
    }

    protected void a(EntityCreature entitycreature, EntityLiving entityliving) {
        entitycreature.setGoalTarget(entityliving, org.bukkit.event.entity.EntityTargetEvent.TargetReason.TARGET_ATTACKED_NEARBY_ENTITY, true); // CraftBukkit - reason
    }
}
