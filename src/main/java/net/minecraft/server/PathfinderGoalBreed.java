package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PathfinderGoalBreed extends PathfinderGoal {

    private EntityAnimal animal;
    World a;
    private EntityAnimal partner;
    int b;
    double c;

    public PathfinderGoalBreed(EntityAnimal entityanimal, double d0) {
        this.animal = entityanimal;
        this.a = entityanimal.world;
        this.c = d0;
        this.a(3);
    }

    public boolean a() {
        if (!this.animal.isInLove()) {
            return false;
        } else {
            this.partner = this.f();
            return this.partner != null;
        }
    }

    public boolean b() {
        return this.partner.isAlive() && this.partner.isInLove() && this.b < 60;
    }

    public void d() {
        this.partner = null;
        this.b = 0;
    }

    public void e() {
        this.animal.getControllerLook().a(this.partner, 10.0F, (float) this.animal.N());
        this.animal.getNavigation().a((Entity) this.partner, this.c);
        ++this.b;
        if (this.b >= 60 && this.animal.h(this.partner) < 9.0D) {
            this.i();
        }

    }

    private EntityAnimal f() {
        List list = this.a.a(this.animal.getClass(), this.animal.getBoundingBox().g(8.0D));
        double d0 = Double.MAX_VALUE;
        EntityAnimal entityanimal = null;
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            EntityAnimal entityanimal1 = (EntityAnimal) iterator.next();

            if (this.animal.mate(entityanimal1) && this.animal.h(entityanimal1) < d0) {
                entityanimal = entityanimal1;
                d0 = this.animal.h(entityanimal1);
            }
        }

        return entityanimal;
    }

    private void i() {
        EntityAgeable entityageable = this.animal.createChild(this.partner);

        if (entityageable != null) {
            // CraftBukkit start - set persistence for tame animals
            if (entityageable instanceof EntityTameableAnimal && ((EntityTameableAnimal) entityageable).isTamed()) {
                entityageable.persistent = true;
            }
            // CraftBukkit end
            EntityHuman entityhuman = this.animal.getBreedCause();

            if (entityhuman == null && this.partner.getBreedCause() != null) {
                entityhuman = this.partner.getBreedCause();
            }

            if (entityhuman != null) {
                entityhuman.b(StatisticList.C);
                if (this.animal instanceof EntityCow) {
                    entityhuman.b((Statistic) AchievementList.H);
                }
            }

            this.animal.setAgeRaw(6000);
            this.partner.setAgeRaw(6000);
            this.animal.resetLove();
            this.partner.resetLove();
            entityageable.setAgeRaw(-24000);
            entityageable.setPositionRotation(this.animal.locX, this.animal.locY, this.animal.locZ, 0.0F, 0.0F);
            this.a.addEntity(entityageable, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.BREEDING); // CraftBukkit - added SpawnReason
            Random random = this.animal.getRandom();

            for (int i = 0; i < 7; ++i) {
                double d0 = random.nextGaussian() * 0.02D;
                double d1 = random.nextGaussian() * 0.02D;
                double d2 = random.nextGaussian() * 0.02D;
                double d3 = random.nextDouble() * (double) this.animal.width * 2.0D - (double) this.animal.width;
                double d4 = 0.5D + random.nextDouble() * (double) this.animal.length;
                double d5 = random.nextDouble() * (double) this.animal.width * 2.0D - (double) this.animal.width;

                this.a.addParticle(EnumParticle.HEART, this.animal.locX + d3, this.animal.locY + d4, this.animal.locZ + d5, d0, d1, d2, new int[0]);
            }

            if (this.a.getGameRules().getBoolean("doMobLoot")) {
                this.a.addEntity(new EntityExperienceOrb(this.a, this.animal.locX, this.animal.locY, this.animal.locZ, random.nextInt(7) + 1));
            }

        }
    }
}
