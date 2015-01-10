package net.minecraft.server;

public class ControllerMove {

    protected EntityInsentient a;
    protected double b;
    protected double c;
    protected double d;
    protected double e;
    protected boolean f;

    public ControllerMove(EntityInsentient entityinsentient) {
        this.a = entityinsentient;
        this.b = entityinsentient.locX;
        this.c = entityinsentient.locY;
        this.d = entityinsentient.locZ;
    }

    public boolean a() {
        return this.f;
    }

    public double b() {
        return this.e;
    }

    public void a(double d0, double d1, double d2, double d3) {
        this.b = d0;
        this.c = d1;
        this.d = d2;
        this.e = d3;
        this.f = true;
    }

    public void c() {
        this.a.m(0.0F);
        if (this.f) {
            this.f = false;
            int i = MathHelper.floor(this.a.getBoundingBox().b + 0.5D);
            double d0 = this.b - this.a.locX;
            double d1 = this.d - this.a.locZ;
            double d2 = this.c - (double) i;
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;

            if (d3 >= 2.500000277905201E-7D) {
                // CraftBukkit - Math -> TrigMath
                float f = (float) (org.bukkit.craftbukkit.TrigMath.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - 90.0F;

                this.a.yaw = this.a(this.a.yaw, f, 30.0F);
                this.a.j((float) (this.e * this.a.getAttributeInstance(GenericAttributes.d).getValue()));
                if (d2 > 0.0D && d0 * d0 + d1 * d1 < 1.0D) {
                    this.a.getControllerJump().a();
                }

            }
        }
    }

    protected float a(float f, float f1, float f2) {
        float f3 = MathHelper.g(f1 - f);

        if (f3 > f2) {
            f3 = f2;
        }

        if (f3 < -f2) {
            f3 = -f2;
        }

        float f4 = f + f3;

        if (f4 < 0.0F) {
            f4 += 360.0F;
        } else if (f4 > 360.0F) {
            f4 -= 360.0F;
        }

        return f4;
    }

    public double d() {
        return this.b;
    }

    public double e() {
        return this.c;
    }

    public double f() {
        return this.d;
    }
}
