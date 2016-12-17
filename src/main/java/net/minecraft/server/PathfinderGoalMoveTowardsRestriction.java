package net.minecraft.server;

public class PathfinderGoalMoveTowardsRestriction extends PathfinderGoal {

    private EntityCreature a;
    private double b;
    private double c;
    private double d;
    private double e;

    public PathfinderGoalMoveTowardsRestriction(EntityCreature entitycreature, double d0) {
        this.a = entitycreature;
        this.e = d0;
        this.a(1);
    }

    public boolean a() {
        if (this.a.cV()) {
            return false;
        } else {
            BlockPosition blockposition = this.a.cW();
            Vec3D vec3d = RandomPositionGenerator.a(this.a, 16, 7, new Vec3D((double) blockposition.getX(), (double) blockposition.getY(), (double) blockposition.getZ()));

            if (vec3d == null) {
                return false;
            } else {
                this.b = vec3d.x;
                this.c = vec3d.y;
                this.d = vec3d.z;
                return true;
            }
        }
    }

    public boolean b() {
        return !this.a.getNavigation().n();
    }

    public void c() {
        this.a.getNavigation().a(this.b, this.c, this.d, this.e);
    }
}
