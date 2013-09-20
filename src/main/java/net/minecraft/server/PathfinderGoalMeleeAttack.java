package net.minecraft.server;

import org.bukkit.event.entity.EntityTargetEvent; // CraftBukkit

public class PathfinderGoalMeleeAttack extends PathfinderGoal {

    World a;
    EntityCreature b;
    int c;
    double d;
    boolean e;
    PathEntity f;
    Class g;
    private int h;

    public PathfinderGoalMeleeAttack(EntityCreature entitycreature, Class oclass, double d0, boolean flag) {
        this(entitycreature, d0, flag);
        this.g = oclass;
    }

    public PathfinderGoalMeleeAttack(EntityCreature entitycreature, double d0, boolean flag) {
        this.b = entitycreature;
        this.a = entitycreature.world;
        this.d = d0;
        this.e = flag;
        this.a(3);
    }

    public boolean a() {
        EntityLiving entityliving = this.b.getGoalTarget();

        if (entityliving == null) {
            return false;
        } else if (!entityliving.isAlive()) {
            return false;
        } else if (this.g != null && !this.g.isAssignableFrom(entityliving.getClass())) {
            return false;
        } else {
            this.f = this.b.getNavigation().a(entityliving);
            return this.f != null;
        }
    }

    public boolean b() {
        EntityLiving entityliving = this.b.getGoalTarget();

        // CraftBukkit start
        EntityTargetEvent.TargetReason reason = this.b.getGoalTarget() == null ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
        if (this.b.getGoalTarget() == null || (this.b.getGoalTarget() != null && !this.b.getGoalTarget().isAlive())) {
            org.bukkit.craftbukkit.event.CraftEventFactory.callEntityTargetEvent(b, null, reason);
        }
        // CraftBukkit end

        return entityliving == null ? false : (!entityliving.isAlive() ? false : (!this.e ? !this.b.getNavigation().g() : this.b.b(MathHelper.floor(entityliving.locX), MathHelper.floor(entityliving.locY), MathHelper.floor(entityliving.locZ))));
    }

    public void c() {
        this.b.getNavigation().a(this.f, this.d);
        this.h = 0;
    }

    public void d() {
        this.b.getNavigation().h();
    }

    // Spigot start
    private double pathX;
    private double pathY;
    private double pathZ;
    private boolean prevPathOK;
    private int fullRangeSearchDelay;
    // Spigot end
    public void e() {
        EntityLiving entityliving = this.b.getGoalTarget();

        this.b.getControllerLook().a(entityliving, 30.0F, 30.0F);
        double goalDistanceSq = this.b.e( entityliving.locX, entityliving.boundingBox.b, entityliving.locZ ); // Spigot
        if ((this.e || this.b.getEntitySenses().canSee(entityliving)) && --this.h <= 0) {
            // Spigot start
            double targetMovement = entityliving.e( pathX, pathY, pathZ );
            // If this is true, then we are re-pathing
            if ( ( this.h <= 0 && targetMovement >= 1.0D ) || ( this.h <= 0 && this.b.aD().nextInt( 200 ) == 0 ) ) /* EntityCreature random instance */

            {
                AttributeInstance rangeAttr = this.b.getAttributeInstance( GenericAttributes.b );
                double origRange = rangeAttr.getValue();
                if ( fullRangeSearchDelay > 0 )
                {

                    double dist = Math.sqrt( goalDistanceSq );
                    if ( dist <= 8.0D )
                    {
                        dist = 8.0D;
                    }
                    if ( dist > origRange )
                    {
                        dist = origRange;
                    }
                    rangeAttr.setValue( dist );
                }

                prevPathOK = this.b.getNavigation().a( (Entity) entityliving, this.d );

                if ( fullRangeSearchDelay > 0 )
                {
                    fullRangeSearchDelay--;
                    if ( origRange > 40.0D )
                    {
                        origRange = 40.0D;
                    }
                    rangeAttr.setValue( origRange );
                }

                pathX = entityliving.locX;
                pathY = entityliving.boundingBox.b;
                pathZ = entityliving.locZ;
                this.h = 4 + this.b.aD().nextInt( 7 ); /* EntityCreature random instance */

                if ( goalDistanceSq > 256.0D )
                {
                    if ( goalDistanceSq > 1024.0D )
                    {
                        this.h += 8;
                    } else
                    {
                        this.h += 16;
                    }
                } else if ( !prevPathOK )
                {
                    this.h += 24;
                }

                if ( !prevPathOK || goalDistanceSq <= 256.0D )
                {
                    if ( fullRangeSearchDelay <= 0 )
                    {
                        fullRangeSearchDelay = 4 + this.b.aD().nextInt( 4 ); /* EntityCreature random instance */
                    }
                }
            }
        }
        // Spigot end

        this.c = Math.max(this.c - 1, 0);
        double d0 = (double) (this.b.width * 2.0F * this.b.width * 2.0F + entityliving.width);

        if (goalDistanceSq <= d0) { // Spigot
            if (this.c <= 0) {
                this.c = 20;
                if (this.b.aZ() != null) {
                    this.b.aV();
                }

                this.b.m(entityliving);
            }
        }
    }
}
