package net.minecraft.server;

public class PathfinderGoalRandomStroll extends PathfinderGoal {

    private EntityCreature a;
    private double b;
    private double c;
    private double d;
    private double e;
    private int f;
    private boolean g;

    public PathfinderGoalRandomStroll(EntityCreature entitycreature, double d0) {
        this(entitycreature, d0, 120);
    }

    public PathfinderGoalRandomStroll(EntityCreature entitycreature, double d0, int i) {
        this.a = entitycreature;
        this.e = d0;
        this.f = i;
        this.a(1);
    }

    public boolean a() {
        if (!this.g) {
            if (this.a.bL() >= 100) {
                return false;
            }

            if (this.a.getRandom().nextInt(this.f) != 0) {
                return false;
            }
        }

        Vec3D vec3d = RandomPositionGenerator.a(this.a, 10, 7);

        if (vec3d == null) {
            return false;
        } else {
            this.b = vec3d.x;
            this.c = vec3d.y;
            this.d = vec3d.z;
            this.g = false;
            return true;
        }
    }

    public boolean b() {
        return !this.a.getNavigation().n();
    }

    public void c() {
        this.a.getNavigation().a(this.b, this.c, this.d, this.e);
    }

    public void f() {
        this.g = true;
    }

    public void setTimeBetweenMovement(int i) {
        this.f = i;
    }
}
