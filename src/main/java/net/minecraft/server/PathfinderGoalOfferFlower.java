package net.minecraft.server;

public class PathfinderGoalOfferFlower extends PathfinderGoal {

    private EntityIronGolem a;
    private EntityVillager b;
    private int c;

    public PathfinderGoalOfferFlower(EntityIronGolem entityirongolem) {
        this.a = entityirongolem;
        this.a(3);
    }

    public boolean a() {
        if (!this.a.world.B()) {
            return false;
        } else if (this.a.getRandom().nextInt(8000) != 0) {
            return false;
        } else {
            this.b = (EntityVillager) this.a.world.a(EntityVillager.class, this.a.getBoundingBox().grow(6.0D, 2.0D, 6.0D), (Entity) this.a);
            return this.b != null;
        }
    }

    public boolean b() {
        return this.c > 0;
    }

    public void c() {
        this.c = 400;
        this.a.a(true);
    }

    public void d() {
        this.a.a(false);
        this.b = null;
    }

    public void e() {
        this.a.getControllerLook().a(this.b, 30.0F, 30.0F);
        --this.c;
    }
}
