package net.minecraft.server;

public class EntityEnderSignal extends Entity {

    private double a;
    private double b;
    private double c;
    private int d;
    private boolean e;

    public EntityEnderSignal(World world) {
        super(world);
        this.setSize(0.25F, 0.25F);
    }

    protected void i() {}

    public EntityEnderSignal(World world, double d0, double d1, double d2) {
        super(world);
        this.d = 0;
        this.setSize(0.25F, 0.25F);
        this.setPosition(d0, d1, d2);
    }

    public void a(BlockPosition blockposition) {
        double d0 = (double) blockposition.getX();
        int i = blockposition.getY();
        double d1 = (double) blockposition.getZ();
        double d2 = d0 - this.locX;
        double d3 = d1 - this.locZ;
        float f = MathHelper.sqrt(d2 * d2 + d3 * d3);

        if (f > 12.0F) {
            this.a = this.locX + d2 / (double) f * 12.0D;
            this.c = this.locZ + d3 / (double) f * 12.0D;
            this.b = this.locY + 8.0D;
        } else {
            this.a = d0;
            this.b = (double) i;
            this.c = d1;
        }

        this.d = 0;
        this.e = this.random.nextInt(5) > 0;
    }

    public void m() {
        this.M = this.locX;
        this.N = this.locY;
        this.O = this.locZ;
        super.m();
        this.locX += this.motX;
        this.locY += this.motY;
        this.locZ += this.motZ;
        float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);

        this.yaw = (float) (MathHelper.b(this.motX, this.motZ) * 57.2957763671875D);

        for (this.pitch = (float) (MathHelper.b(this.motY, (double) f) * 57.2957763671875D); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {
            ;
        }

        while (this.pitch - this.lastPitch >= 180.0F) {
            this.lastPitch += 360.0F;
        }

        while (this.yaw - this.lastYaw < -180.0F) {
            this.lastYaw -= 360.0F;
        }

        while (this.yaw - this.lastYaw >= 180.0F) {
            this.lastYaw += 360.0F;
        }

        this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
        this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
        if (!this.world.isClientSide) {
            double d0 = this.a - this.locX;
            double d1 = this.c - this.locZ;
            float f1 = (float) Math.sqrt(d0 * d0 + d1 * d1);
            float f2 = (float) MathHelper.b(d1, d0);
            double d2 = (double) f + (double) (f1 - f) * 0.0025D;

            if (f1 < 1.0F) {
                d2 *= 0.8D;
                this.motY *= 0.8D;
            }

            this.motX = Math.cos((double) f2) * d2;
            this.motZ = Math.sin((double) f2) * d2;
            if (this.locY < this.b) {
                this.motY += (1.0D - this.motY) * 0.014999999664723873D;
            } else {
                this.motY += (-1.0D - this.motY) * 0.014999999664723873D;
            }
        }

        float f3 = 0.25F;

        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX - this.motX * (double) f3, this.locY - this.motY * (double) f3, this.locZ - this.motZ * (double) f3, this.motX, this.motY, this.motZ, new int[0]);
            }
        } else {
            this.world.addParticle(EnumParticle.PORTAL, this.locX - this.motX * (double) f3 + this.random.nextDouble() * 0.6D - 0.3D, this.locY - this.motY * (double) f3 - 0.5D, this.locZ - this.motZ * (double) f3 + this.random.nextDouble() * 0.6D - 0.3D, this.motX, this.motY, this.motZ, new int[0]);
        }

        if (!this.world.isClientSide) {
            this.setPosition(this.locX, this.locY, this.locZ);
            ++this.d;
            if (this.d > 80 && !this.world.isClientSide) {
                this.die();
                if (this.e) {
                    this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Items.ENDER_EYE)));
                } else {
                    this.world.triggerEffect(2003, new BlockPosition(this), 0);
                }
            }
        }

    }

    public void b(NBTTagCompound nbttagcompound) {}

    public void a(NBTTagCompound nbttagcompound) {}

    public float e(float f) {
        return 1.0F;
    }

    public boolean aT() {
        return false;
    }
}
