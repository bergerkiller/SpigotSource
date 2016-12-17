package net.minecraft.server;

import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class EntityGuardian extends EntityMonster {

    private static final DataWatcherObject<Byte> a = DataWatcher.a(EntityGuardian.class, DataWatcherRegistry.a);
    private static final DataWatcherObject<Integer> b = DataWatcher.a(EntityGuardian.class, DataWatcherRegistry.b);
    private float c;
    private float bw;
    private float bx;
    private float by;
    private float bz;
    private EntityLiving bA;
    private int bB;
    private boolean bC;
    public PathfinderGoalRandomStroll goalRandomStroll;

    public EntityGuardian(World world) {
        super(world);
        this.b_ = 10;
        this.setSize(0.85F, 0.85F);
        this.moveController = new EntityGuardian.ControllerMoveGuardian(this);
        this.bw = this.c = this.random.nextFloat();
    }

    protected void r() {
        this.goalSelector.a(4, new EntityGuardian.PathfinderGoalGuardianAttack(this));
        PathfinderGoalMoveTowardsRestriction pathfindergoalmovetowardsrestriction;

        this.goalSelector.a(5, pathfindergoalmovetowardsrestriction = new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(7, this.goalRandomStroll = new PathfinderGoalRandomStroll(this, 1.0D, 80));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityGuardian.class, 12.0F, 0.01F));
        this.goalSelector.a(9, new PathfinderGoalRandomLookaround(this));
        this.goalRandomStroll.a(3);
        pathfindergoalmovetowardsrestriction.a(3);
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityLiving.class, 10, true, false, new EntityGuardian.EntitySelectorGuardianTargetHumanSquid(this)));
    }

    public void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(6.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5D);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(30.0D);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setElder(nbttagcompound.getBoolean("Elder"));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setBoolean("Elder", this.isElder());
    }

    protected NavigationAbstract b(World world) {
        return new NavigationGuardian(this, world);
    }

    protected void i() {
        super.i();
        this.datawatcher.register(EntityGuardian.a, Byte.valueOf((byte) 0));
        this.datawatcher.register(EntityGuardian.b, Integer.valueOf(0));
    }

    private boolean a(int i) {
        return (((Byte) this.datawatcher.get(EntityGuardian.a)).byteValue() & i) != 0;
    }

    private void a(int i, boolean flag) {
        byte b0 = ((Byte) this.datawatcher.get(EntityGuardian.a)).byteValue();

        if (flag) {
            this.datawatcher.set(EntityGuardian.a, Byte.valueOf((byte) (b0 | i)));
        } else {
            this.datawatcher.set(EntityGuardian.a, Byte.valueOf((byte) (b0 & ~i)));
        }

    }

    public boolean o() {
        return this.a(2);
    }

    private void o(boolean flag) {
        this.a(2, flag);
    }

    public int db() {
        return this.isElder() ? 60 : 80;
    }

    public boolean isElder() {
        return this.a(4);
    }

    public void setElder(boolean flag) {
        this.a(4, flag);
        if (flag) {
            this.setSize(1.9975F, 1.9975F);
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30000001192092896D);
            this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(8.0D);
            this.getAttributeInstance(GenericAttributes.maxHealth).setValue(80.0D);
            this.cM();
            if (this.goalRandomStroll != null) {
                this.goalRandomStroll.setTimeBetweenMovement(400);
            }
        }

    }

    private void b(int i) {
        this.datawatcher.set(EntityGuardian.b, Integer.valueOf(i));
    }

    public boolean de() {
        return ((Integer) this.datawatcher.get(EntityGuardian.b)).intValue() != 0;
    }

    public EntityLiving df() {
        if (!this.de()) {
            return null;
        } else if (this.world.isClientSide) {
            if (this.bA != null) {
                return this.bA;
            } else {
                Entity entity = this.world.getEntity(((Integer) this.datawatcher.get(EntityGuardian.b)).intValue());

                if (entity instanceof EntityLiving) {
                    this.bA = (EntityLiving) entity;
                    return this.bA;
                } else {
                    return null;
                }
            }
        } else {
            return this.getGoalTarget();
        }
    }

    public void a(DataWatcherObject<?> datawatcherobject) {
        super.a(datawatcherobject);
        if (EntityGuardian.a.equals(datawatcherobject)) {
            if (this.isElder() && this.width < 1.0F) {
                this.setSize(1.9975F, 1.9975F);
            }
        } else if (EntityGuardian.b.equals(datawatcherobject)) {
            this.bB = 0;
            this.bA = null;
        }

    }

    public int C() {
        return 160;
    }

    protected SoundEffect G() {
        return this.isElder() ? (this.isInWater() ? SoundEffects.aD : SoundEffects.aE) : (this.isInWater() ? SoundEffects.cf : SoundEffects.cg);
    }

    protected SoundEffect bS() {
        return this.isElder() ? (this.isInWater() ? SoundEffects.aI : SoundEffects.aJ) : (this.isInWater() ? SoundEffects.cl : SoundEffects.cm);
    }

    protected SoundEffect bT() {
        return this.isElder() ? (this.isInWater() ? SoundEffects.aG : SoundEffects.aH) : (this.isInWater() ? SoundEffects.ci : SoundEffects.cj);
    }

    protected boolean playStepSound() {
        return false;
    }

    public float getHeadHeight() {
        return this.length * 0.5F;
    }

    public float a(BlockPosition blockposition) {
        return this.world.getType(blockposition).getMaterial() == Material.WATER ? 10.0F + this.world.n(blockposition) - 0.5F : super.a(blockposition);
    }

    public void n() {
        if (this.world.isClientSide) {
            this.bw = this.c;
            if (!this.isInWater()) {
                this.bx = 2.0F;
                if (this.motY > 0.0D && this.bC && !this.ad()) {
                    this.world.a(this.locX, this.locY, this.locZ, SoundEffects.ck, this.bA(), 1.0F, 1.0F, false);
                }

                this.bC = this.motY < 0.0D && this.world.d((new BlockPosition(this)).down(), false);
            } else if (this.o()) {
                if (this.bx < 0.5F) {
                    this.bx = 4.0F;
                } else {
                    this.bx += (0.5F - this.bx) * 0.1F;
                }
            } else {
                this.bx += (0.125F - this.bx) * 0.2F;
            }

            this.c += this.bx;
            this.bz = this.by;
            if (!this.isInWater()) {
                this.by = this.random.nextFloat();
            } else if (this.o()) {
                this.by += (0.0F - this.by) * 0.25F;
            } else {
                this.by += (1.0F - this.by) * 0.06F;
            }

            if (this.o() && this.isInWater()) {
                Vec3D vec3d = this.f(0.0F);

                for (int i = 0; i < 2; ++i) {
                    this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX + (this.random.nextDouble() - 0.5D) * (double) this.width - vec3d.x * 1.5D, this.locY + this.random.nextDouble() * (double) this.length - vec3d.y * 1.5D, this.locZ + (this.random.nextDouble() - 0.5D) * (double) this.width - vec3d.z * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]);
                }
            }

            if (this.de()) {
                if (this.bB < this.db()) {
                    ++this.bB;
                }

                EntityLiving entityliving = this.df();

                if (entityliving != null) {
                    this.getControllerLook().a(entityliving, 90.0F, 90.0F);
                    this.getControllerLook().a();
                    double d0 = (double) this.s(0.0F);
                    double d1 = entityliving.locX - this.locX;
                    double d2 = entityliving.locY + (double) (entityliving.length * 0.5F) - (this.locY + (double) this.getHeadHeight());
                    double d3 = entityliving.locZ - this.locZ;
                    double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);

                    d1 /= d4;
                    d2 /= d4;
                    d3 /= d4;
                    double d5 = this.random.nextDouble();

                    while (d5 < d4) {
                        d5 += 1.8D - d0 + this.random.nextDouble() * (1.7D - d0);
                        this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX + d1 * d5, this.locY + d2 * d5 + (double) this.getHeadHeight(), this.locZ + d3 * d5, 0.0D, 0.0D, 0.0D, new int[0]);
                    }
                }
            }
        }

        if (this.inWater) {
            this.setAirTicks(300);
        } else if (this.onGround) {
            this.motY += 0.5D;
            this.motX += (double) ((this.random.nextFloat() * 2.0F - 1.0F) * 0.4F);
            this.motZ += (double) ((this.random.nextFloat() * 2.0F - 1.0F) * 0.4F);
            this.yaw = this.random.nextFloat() * 360.0F;
            this.onGround = false;
            this.impulse = true;
        }

        if (this.de()) {
            this.yaw = this.aP;
        }

        super.n();
    }

    public float s(float f) {
        return ((float) this.bB + f) / (float) this.db();
    }

    protected void M() {
        super.M();
        if (this.isElder()) {
            boolean flag = true;
            boolean flag1 = true;
            boolean flag2 = true;
            boolean flag3 = true;

            if ((this.ticksLived + this.getId()) % 1200 == 0) {
                MobEffectList mobeffectlist = MobEffects.SLOWER_DIG;
                List list = this.world.b(EntityPlayer.class, new Predicate() {
                    public boolean a(@Nullable EntityPlayer entityplayer) {
                        return EntityGuardian.this.h(entityplayer) < 2500.0D && entityplayer.playerInteractManager.c();
                    }

                    public boolean apply(Object object) {
                        return this.a((EntityPlayer) object);
                    }
                });
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                    if (!entityplayer.hasEffect(mobeffectlist) || entityplayer.getEffect(mobeffectlist).getAmplifier() < 2 || entityplayer.getEffect(mobeffectlist).getDuration() < 1200) {
                        entityplayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(10, 0.0F));
                        entityplayer.addEffect(new MobEffect(mobeffectlist, 6000, 2));
                    }
                }
            }

            if (!this.cZ()) {
                this.a(new BlockPosition(this), 16);
            }
        }

    }

    @Nullable
    protected MinecraftKey J() {
        return this.isElder() ? LootTables.x : LootTables.w;
    }

    protected boolean s_() {
        return true;
    }

    public boolean canSpawn() {
        return this.world.a(this.getBoundingBox(), (Entity) this) && this.world.getCubes(this, this.getBoundingBox()).isEmpty();
    }

    public boolean cG() {
        return (this.random.nextInt(20) == 0 || !this.world.i(new BlockPosition(this))) && super.cG();
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (!this.o() && !damagesource.isMagic() && damagesource.i() instanceof EntityLiving) {
            EntityLiving entityliving = (EntityLiving) damagesource.i();

            if (!damagesource.isExplosion()) {
                entityliving.damageEntity(DamageSource.a(this), 2.0F);
            }
        }

        if (this.goalRandomStroll != null) {
            this.goalRandomStroll.f();
        }

        return super.damageEntity(damagesource, f);
    }

    public int N() {
        return 180;
    }

    public void g(float f, float f1) {
        if (this.cp()) {
            if (this.isInWater()) {
                this.a(f, f1, 0.1F);
                this.move(this.motX, this.motY, this.motZ);
                this.motX *= 0.8999999761581421D;
                this.motY *= 0.8999999761581421D;
                this.motZ *= 0.8999999761581421D;
                if (!this.o() && this.getGoalTarget() == null) {
                    this.motY -= 0.005D;
                }
            } else {
                super.g(f, f1);
            }
        } else {
            super.g(f, f1);
        }

    }

    static class ControllerMoveGuardian extends ControllerMove {

        private EntityGuardian i;

        public ControllerMoveGuardian(EntityGuardian entityguardian) {
            super(entityguardian);
            this.i = entityguardian;
        }

        public void c() {
            if (this.h == ControllerMove.Operation.MOVE_TO && !this.i.getNavigation().n()) {
                double d0 = this.b - this.i.locX;
                double d1 = this.c - this.i.locY;
                double d2 = this.d - this.i.locZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                d3 = (double) MathHelper.sqrt(d3);
                d1 /= d3;
                float f = (float) (MathHelper.b(d2, d0) * 57.2957763671875D) - 90.0F;

                this.i.yaw = this.a(this.i.yaw, f, 90.0F);
                this.i.aN = this.i.yaw;
                float f1 = (float) (this.e * this.i.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue());

                this.i.l(this.i.cl() + (f1 - this.i.cl()) * 0.125F);
                double d4 = Math.sin((double) (this.i.ticksLived + this.i.getId()) * 0.5D) * 0.05D;
                double d5 = Math.cos((double) (this.i.yaw * 0.017453292F));
                double d6 = Math.sin((double) (this.i.yaw * 0.017453292F));

                this.i.motX += d4 * d5;
                this.i.motZ += d4 * d6;
                d4 = Math.sin((double) (this.i.ticksLived + this.i.getId()) * 0.75D) * 0.05D;
                this.i.motY += d4 * (d6 + d5) * 0.25D;
                this.i.motY += (double) this.i.cl() * d1 * 0.1D;
                ControllerLook controllerlook = this.i.getControllerLook();
                double d7 = this.i.locX + d0 / d3 * 2.0D;
                double d8 = (double) this.i.getHeadHeight() + this.i.locY + d1 / d3;
                double d9 = this.i.locZ + d2 / d3 * 2.0D;
                double d10 = controllerlook.e();
                double d11 = controllerlook.f();
                double d12 = controllerlook.g();

                if (!controllerlook.b()) {
                    d10 = d7;
                    d11 = d8;
                    d12 = d9;
                }

                this.i.getControllerLook().a(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
                this.i.o(true);
            } else {
                this.i.l(0.0F);
                this.i.o(false);
            }
        }
    }

    static class PathfinderGoalGuardianAttack extends PathfinderGoal {

        private EntityGuardian a;
        private int b;

        public PathfinderGoalGuardianAttack(EntityGuardian entityguardian) {
            this.a = entityguardian;
            this.a(3);
        }

        public boolean a() {
            EntityLiving entityliving = this.a.getGoalTarget();

            return entityliving != null && entityliving.isAlive();
        }

        public boolean b() {
            return super.b() && (this.a.isElder() || this.a.h(this.a.getGoalTarget()) > 9.0D);
        }

        public void c() {
            this.b = -10;
            this.a.getNavigation().o();
            this.a.getControllerLook().a(this.a.getGoalTarget(), 90.0F, 90.0F);
            this.a.impulse = true;
        }

        public void d() {
            this.a.b(0);
            this.a.setGoalTarget((EntityLiving) null);
            this.a.goalRandomStroll.f();
        }

        public void e() {
            EntityLiving entityliving = this.a.getGoalTarget();

            this.a.getNavigation().o();
            this.a.getControllerLook().a(entityliving, 90.0F, 90.0F);
            if (!this.a.hasLineOfSight(entityliving)) {
                this.a.setGoalTarget((EntityLiving) null);
            } else {
                ++this.b;
                if (this.b == 0) {
                    this.a.b(this.a.getGoalTarget().getId());
                    this.a.world.broadcastEntityEffect(this.a, (byte) 21);
                } else if (this.b >= this.a.db()) {
                    float f = 1.0F;

                    if (this.a.world.getDifficulty() == EnumDifficulty.HARD) {
                        f += 2.0F;
                    }

                    if (this.a.isElder()) {
                        f += 2.0F;
                    }

                    entityliving.damageEntity(DamageSource.b(this.a, this.a), f);
                    entityliving.damageEntity(DamageSource.mobAttack(this.a), (float) this.a.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue());
                    this.a.setGoalTarget((EntityLiving) null);
                }

                super.e();
            }
        }
    }

    static class EntitySelectorGuardianTargetHumanSquid implements Predicate<EntityLiving> {

        private EntityGuardian a;

        public EntitySelectorGuardianTargetHumanSquid(EntityGuardian entityguardian) {
            this.a = entityguardian;
        }

        public boolean a(@Nullable EntityLiving entityliving) {
            return (entityliving instanceof EntityHuman || entityliving instanceof EntitySquid) && entityliving.h(this.a) > 9.0D;
        }

        public boolean apply(Object object) {
            return this.a((EntityLiving) object);
        }
    }
}
