package net.minecraft.server;

import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;

public abstract class EntityProjectile extends Entity implements IProjectile {

    private int blockX;
    private int blockY;
    private int blockZ;
    private Block inBlockId;
    protected boolean inGround;
    public int shake;
    public EntityLiving shooter;
    public String shooterName;
    private int au;
    private int av;
    public Entity c;
    private int aw;

    public EntityProjectile(World world) {
        super(world);
        this.blockX = -1;
        this.blockY = -1;
        this.blockZ = -1;
        this.setSize(0.25F, 0.25F);
    }

    public EntityProjectile(World world, double d0, double d1, double d2) {
        this(world);
        this.setPosition(d0, d1, d2);
    }

    public EntityProjectile(World world, EntityLiving entityliving) {
        this(world, entityliving.locX, entityliving.locY + (double) entityliving.getHeadHeight() - 0.10000000149011612D, entityliving.locZ);
        this.shooter = entityliving;
        this.projectileSource = (org.bukkit.entity.LivingEntity) entityliving.getBukkitEntity(); // CraftBukkit
    }

    protected void i() {}

    public void a(Entity entity, float f, float f1, float f2, float f3, float f4) {
        float f5 = -MathHelper.sin(f1 * 0.017453292F) * MathHelper.cos(f * 0.017453292F);
        float f6 = -MathHelper.sin((f + f2) * 0.017453292F);
        float f7 = MathHelper.cos(f1 * 0.017453292F) * MathHelper.cos(f * 0.017453292F);

        this.shoot((double) f5, (double) f6, (double) f7, f3, f4);
        this.motX += entity.motX;
        this.motZ += entity.motZ;
        if (!entity.onGround) {
            this.motY += entity.motY;
        }

    }

    public void shoot(double d0, double d1, double d2, float f, float f1) {
        float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);

        d0 /= (double) f2;
        d1 /= (double) f2;
        d2 /= (double) f2;
        d0 += this.random.nextGaussian() * 0.007499999832361937D * (double) f1;
        d1 += this.random.nextGaussian() * 0.007499999832361937D * (double) f1;
        d2 += this.random.nextGaussian() * 0.007499999832361937D * (double) f1;
        d0 *= (double) f;
        d1 *= (double) f;
        d2 *= (double) f;
        this.motX = d0;
        this.motY = d1;
        this.motZ = d2;
        float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);

        this.lastYaw = this.yaw = (float) (MathHelper.b(d0, d2) * 57.2957763671875D);
        this.lastPitch = this.pitch = (float) (MathHelper.b(d1, (double) f3) * 57.2957763671875D);
        this.au = 0;
    }

    public void m() {
        this.M = this.locX;
        this.N = this.locY;
        this.O = this.locZ;
        super.m();
        if (this.shake > 0) {
            --this.shake;
        }

        if (this.inGround) {
            if (this.world.getType(new BlockPosition(this.blockX, this.blockY, this.blockZ)).getBlock() == this.inBlockId) {
                ++this.au;
                if (this.au == 1200) {
                    this.die();
                }

                return;
            }

            this.inGround = false;
            this.motX *= (double) (this.random.nextFloat() * 0.2F);
            this.motY *= (double) (this.random.nextFloat() * 0.2F);
            this.motZ *= (double) (this.random.nextFloat() * 0.2F);
            this.au = 0;
            this.av = 0;
        } else {
            ++this.av;
        }

        Vec3D vec3d = new Vec3D(this.locX, this.locY, this.locZ);
        Vec3D vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
        MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d1);

        vec3d = new Vec3D(this.locX, this.locY, this.locZ);
        vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
        if (movingobjectposition != null) {
            vec3d1 = new Vec3D(movingobjectposition.pos.x, movingobjectposition.pos.y, movingobjectposition.pos.z);
        }

        Entity entity = null;
        List list = this.world.getEntities(this, this.getBoundingBox().a(this.motX, this.motY, this.motZ).g(1.0D));
        double d0 = 0.0D;
        boolean flag = false;

        for (int i = 0; i < list.size(); ++i) {
            Entity entity1 = (Entity) list.get(i);

            if (entity1.isInteractable()) {
                if (entity1 == this.c) {
                    flag = true;
                } else if (this.ticksLived < 2 && this.c == null) {
                    this.c = entity1;
                    flag = true;
                } else {
                    flag = false;
                    AxisAlignedBB axisalignedbb = entity1.getBoundingBox().g(0.30000001192092896D);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);

                    if (movingobjectposition1 != null) {
                        double d1 = vec3d.distanceSquared(movingobjectposition1.pos);

                        if (d1 < d0 || d0 == 0.0D) {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }
        }

        if (this.c != null) {
            if (flag) {
                this.aw = 2;
            } else if (this.aw-- <= 0) {
                this.c = null;
            }
        }

        if (entity != null) {
            movingobjectposition = new MovingObjectPosition(entity);
        }

        if (movingobjectposition != null) {
            if (movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK && this.world.getType(movingobjectposition.a()).getBlock() == Blocks.PORTAL) {
                this.e(movingobjectposition.a());
            } else {
                this.a(movingobjectposition);
                // CraftBukkit start
                if (this.dead) {
                    org.bukkit.craftbukkit.event.CraftEventFactory.callProjectileHitEvent(this);
                }
                // CraftBukkit end
            }
        }

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
        float f1 = 0.99F;
        float f2 = this.j();

        if (this.isInWater()) {
            for (int j = 0; j < 4; ++j) {
                float f3 = 0.25F;

                this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX - this.motX * (double) f3, this.locY - this.motY * (double) f3, this.locZ - this.motZ * (double) f3, this.motX, this.motY, this.motZ, new int[0]);
            }

            f1 = 0.8F;
        }

        this.motX *= (double) f1;
        this.motY *= (double) f1;
        this.motZ *= (double) f1;
        this.motY -= (double) f2;
        this.setPosition(this.locX, this.locY, this.locZ);
    }

    protected float j() {
        return 0.03F;
    }

    protected abstract void a(MovingObjectPosition movingobjectposition);

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInt("xTile", this.blockX);
        nbttagcompound.setInt("yTile", this.blockY);
        nbttagcompound.setInt("zTile", this.blockZ);
        MinecraftKey minecraftkey = (MinecraftKey) Block.REGISTRY.b(this.inBlockId);

        nbttagcompound.setString("inTile", minecraftkey == null ? "" : minecraftkey.toString());
        nbttagcompound.setByte("shake", (byte) this.shake);
        nbttagcompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        if ((this.shooterName == null || this.shooterName.isEmpty()) && this.shooter instanceof EntityHuman) {
            this.shooterName = this.shooter.getName();
        }

        nbttagcompound.setString("ownerName", this.shooterName == null ? "" : this.shooterName);
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.blockX = nbttagcompound.getInt("xTile");
        this.blockY = nbttagcompound.getInt("yTile");
        this.blockZ = nbttagcompound.getInt("zTile");
        if (nbttagcompound.hasKeyOfType("inTile", 8)) {
            this.inBlockId = Block.getByName(nbttagcompound.getString("inTile"));
        } else {
            this.inBlockId = Block.getById(nbttagcompound.getByte("inTile") & 255);
        }

        this.shake = nbttagcompound.getByte("shake") & 255;
        this.inGround = nbttagcompound.getByte("inGround") == 1;
        this.shooter = null;
        this.shooterName = nbttagcompound.getString("ownerName");
        if (this.shooterName != null && this.shooterName.isEmpty()) {
            this.shooterName = null;
        }

        this.shooter = this.getShooter();
    }

    @Nullable
    public EntityLiving getShooter() {
        if (this.shooter == null && this.shooterName != null && !this.shooterName.isEmpty()) {
            this.shooter = this.world.a(this.shooterName);
            if (this.shooter == null && this.world instanceof WorldServer) {
                try {
                    Entity entity = ((WorldServer) this.world).getEntity(UUID.fromString(this.shooterName));

                    if (entity instanceof EntityLiving) {
                        this.shooter = (EntityLiving) entity;
                    }
                } catch (Throwable throwable) {
                    this.shooter = null;
                }
            }
        }

        return this.shooter;
    }
}
