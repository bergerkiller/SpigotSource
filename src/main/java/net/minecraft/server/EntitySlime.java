package net.minecraft.server;

import javax.annotation.Nullable;
// CraftBukkit start
import org.bukkit.event.entity.SlimeSplitEvent;
// CraftBukkit end

public class EntitySlime extends EntityInsentient implements IMonster {

    private static final DataWatcherObject<Integer> bu = DataWatcher.a(EntitySlime.class, DataWatcherRegistry.b);
    public float a;
    public float b;
    public float c;
    private boolean bv;

    public EntitySlime(World world) {
        super(world);
        this.moveController = new EntitySlime.ControllerMoveSlime(this);
    }

    protected void r() {
        this.goalSelector.a(1, new EntitySlime.PathfinderGoalSlimeRandomJump(this));
        this.goalSelector.a(2, new EntitySlime.PathfinderGoalSlimeNearestPlayer(this));
        this.goalSelector.a(3, new EntitySlime.PathfinderGoalSlimeRandomDirection(this));
        this.goalSelector.a(5, new EntitySlime.PathfinderGoalSlimeIdle(this));
        this.targetSelector.a(1, new PathfinderGoalTargetNearestPlayer(this));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTargetInsentient(this, EntityIronGolem.class));
    }

    protected void i() {
        super.i();
        this.datawatcher.register(EntitySlime.bu, Integer.valueOf(1));
    }

    public void setSize(int i) {
        this.datawatcher.set(EntitySlime.bu, Integer.valueOf(i));
        this.setSize(0.51000005F * (float) i, 0.51000005F * (float) i);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue((double) (i * i));
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue((double) (0.2F + 0.1F * (float) i));
        this.setHealth(this.getMaxHealth());
        this.b_ = i;
    }

    public int getSize() {
        return ((Integer) this.datawatcher.get(EntitySlime.bu)).intValue();
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Size", this.getSize() - 1);
        nbttagcompound.setBoolean("wasOnGround", this.bv);
    }

    public boolean dc() {
        return this.getSize() <= 1;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        int i = nbttagcompound.getInt("Size");

        if (i < 0) {
            i = 0;
        }

        this.setSize(i + 1);
        this.bv = nbttagcompound.getBoolean("wasOnGround");
    }

    protected EnumParticle o() {
        return EnumParticle.SLIME;
    }

    public void m() {
        if (!this.world.isClientSide && this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.getSize() > 0) {
            this.dead = true;
        }

        this.b += (this.a - this.b) * 0.5F;
        this.c = this.b;
        super.m();
        if (this.onGround && !this.bv) {
            int i = this.getSize();

            for (int j = 0; j < i * 8; ++j) {
                float f = this.random.nextFloat() * 6.2831855F;
                float f1 = this.random.nextFloat() * 0.5F + 0.5F;
                float f2 = MathHelper.sin(f) * (float) i * 0.5F * f1;
                float f3 = MathHelper.cos(f) * (float) i * 0.5F * f1;
                World world = this.world;
                EnumParticle enumparticle = this.o();
                double d0 = this.locX + (double) f2;
                double d1 = this.locZ + (double) f3;

                world.addParticle(enumparticle, d0, this.getBoundingBox().b, d1, 0.0D, 0.0D, 0.0D, new int[0]);
            }

            this.a(this.cZ(), this.ce(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.a = -0.5F;
        } else if (!this.onGround && this.bv) {
            this.a = 1.0F;
        }

        this.bv = this.onGround;
        this.cW();
    }

    protected void cW() {
        this.a *= 0.6F;
    }

    protected int cV() {
        return this.random.nextInt(20) + 10;
    }

    protected EntitySlime cU() {
        return new EntitySlime(this.world);
    }

    public void a(DataWatcherObject<?> datawatcherobject) {
        if (EntitySlime.bu.equals(datawatcherobject)) {
            int i = this.getSize();

            this.setSize(0.51000005F * (float) i, 0.51000005F * (float) i);
            this.yaw = this.aP;
            this.aN = this.aP;
            if (this.isInWater() && this.random.nextInt(20) == 0) {
                this.ak();
            }
        }

        super.a(datawatcherobject);
    }

    public void die() {
        int i = this.getSize();

        if (!this.world.isClientSide && i > 1 && this.getHealth() <= 0.0F) {
            int j = 2 + this.random.nextInt(3);

            // CraftBukkit start
            SlimeSplitEvent event = new SlimeSplitEvent((org.bukkit.entity.Slime) this.getBukkitEntity(), j);
            this.world.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled() && event.getCount() > 0) {
                j = event.getCount();
            } else {
                super.die();
                return;
            }
            // CraftBukkit end

            for (int k = 0; k < j; ++k) {
                float f = ((float) (k % 2) - 0.5F) * (float) i / 4.0F;
                float f1 = ((float) (k / 2) - 0.5F) * (float) i / 4.0F;
                EntitySlime entityslime = this.cU();

                if (this.hasCustomName()) {
                    entityslime.setCustomName(this.getCustomName());
                }

                if (this.isPersistent()) {
                    entityslime.cM();
                }

                entityslime.setSize(i / 2);
                entityslime.setPositionRotation(this.locX + (double) f, this.locY + 0.5D, this.locZ + (double) f1, this.random.nextFloat() * 360.0F, 0.0F);
                this.world.addEntity(entityslime, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SLIME_SPLIT); // CraftBukkit - SpawnReason
            }
        }

        super.die();
    }

    public void collide(Entity entity) {
        super.collide(entity);
        if (entity instanceof EntityIronGolem && this.cX()) {
            this.d((EntityLiving) entity);
        }

    }

    public void d(EntityHuman entityhuman) {
        if (this.cX()) {
            this.d((EntityLiving) entityhuman);
        }

    }

    protected void d(EntityLiving entityliving) {
        int i = this.getSize();

        if (this.hasLineOfSight(entityliving) && this.h(entityliving) < 0.6D * (double) i * 0.6D * (double) i && entityliving.damageEntity(DamageSource.mobAttack(this), (float) this.cY())) {
            this.a(SoundEffects.fq, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.a((EntityLiving) this, (Entity) entityliving);
        }

    }

    public float getHeadHeight() {
        return 0.625F * this.length;
    }

    protected boolean cX() {
        return !this.dc();
    }

    protected int cY() {
        return this.getSize();
    }

    protected SoundEffect bS() {
        return this.dc() ? SoundEffects.fE : SoundEffects.fv;
    }

    protected SoundEffect bT() {
        return this.dc() ? SoundEffects.fD : SoundEffects.fs;
    }

    protected SoundEffect cZ() {
        return this.dc() ? SoundEffects.fG : SoundEffects.fy;
    }

    protected Item getLoot() {
        return this.getSize() == 1 ? Items.SLIME : null;
    }

    @Nullable
    protected MinecraftKey J() {
        return this.getSize() == 1 ? LootTables.ad : LootTables.a;
    }

    public boolean cG() {
        BlockPosition blockposition = new BlockPosition(MathHelper.floor(this.locX), 0, MathHelper.floor(this.locZ));
        Chunk chunk = this.world.getChunkAtWorldCoords(blockposition);

        if (this.world.getWorldData().getType() == WorldType.FLAT && this.random.nextInt(4) != 1) {
            return false;
        } else {
            if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
                BiomeBase biomebase = this.world.getBiome(blockposition);

                if (biomebase == Biomes.h && this.locY > 50.0D && this.locY < 70.0D && this.random.nextFloat() < 0.5F && this.random.nextFloat() < this.world.E() && this.world.getLightLevel(new BlockPosition(this)) <= this.random.nextInt(8)) {
                    return super.cG();
                }

                if (this.random.nextInt(10) == 0 && chunk.a(987234911L).nextInt(10) == 0 && this.locY < 40.0D) {
                    return super.cG();
                }
            }

            return false;
        }
    }

    protected float ce() {
        return 0.4F * (float) this.getSize();
    }

    public int N() {
        return 0;
    }

    protected boolean dd() {
        return this.getSize() > 0;
    }

    protected void ci() {
        this.motY = 0.41999998688697815D;
        this.impulse = true;
    }

    @Nullable
    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, @Nullable GroupDataEntity groupdataentity) {
        int i = this.random.nextInt(3);

        if (i < 2 && this.random.nextFloat() < 0.5F * difficultydamagescaler.c()) {
            ++i;
        }

        int j = 1 << i;

        this.setSize(j);
        return super.prepare(difficultydamagescaler, groupdataentity);
    }

    protected SoundEffect da() {
        return this.dc() ? SoundEffects.fF : SoundEffects.fw;
    }

    static class PathfinderGoalSlimeIdle extends PathfinderGoal {

        private EntitySlime a;

        public PathfinderGoalSlimeIdle(EntitySlime entityslime) {
            this.a = entityslime;
            this.a(5);
        }

        public boolean a() {
            return true;
        }

        public void e() {
            ((EntitySlime.ControllerMoveSlime) this.a.getControllerMove()).a(1.0D);
        }
    }

    static class PathfinderGoalSlimeRandomJump extends PathfinderGoal {

        private EntitySlime a;

        public PathfinderGoalSlimeRandomJump(EntitySlime entityslime) {
            this.a = entityslime;
            this.a(5);
            ((Navigation) entityslime.getNavigation()).c(true);
        }

        public boolean a() {
            return this.a.isInWater() || this.a.an();
        }

        public void e() {
            if (this.a.getRandom().nextFloat() < 0.8F) {
                this.a.getControllerJump().a();
            }

            ((EntitySlime.ControllerMoveSlime) this.a.getControllerMove()).a(1.2D);
        }
    }

    static class PathfinderGoalSlimeRandomDirection extends PathfinderGoal {

        private EntitySlime a;
        private float b;
        private int c;

        public PathfinderGoalSlimeRandomDirection(EntitySlime entityslime) {
            this.a = entityslime;
            this.a(2);
        }

        public boolean a() {
            return this.a.getGoalTarget() == null && (this.a.onGround || this.a.isInWater() || this.a.an() || this.a.hasEffect(MobEffects.LEVITATION));
        }

        public void e() {
            if (--this.c <= 0) {
                this.c = 40 + this.a.getRandom().nextInt(60);
                this.b = (float) this.a.getRandom().nextInt(360);
            }

            ((EntitySlime.ControllerMoveSlime) this.a.getControllerMove()).a(this.b, false);
        }
    }

    static class PathfinderGoalSlimeNearestPlayer extends PathfinderGoal {

        private EntitySlime a;
        private int b;

        public PathfinderGoalSlimeNearestPlayer(EntitySlime entityslime) {
            this.a = entityslime;
            this.a(2);
        }

        public boolean a() {
            EntityLiving entityliving = this.a.getGoalTarget();

            return entityliving == null ? false : (!entityliving.isAlive() ? false : !(entityliving instanceof EntityHuman) || !((EntityHuman) entityliving).abilities.isInvulnerable);
        }

        public void c() {
            this.b = 300;
            super.c();
        }

        public boolean b() {
            EntityLiving entityliving = this.a.getGoalTarget();

            return entityliving == null ? false : (!entityliving.isAlive() ? false : (entityliving instanceof EntityHuman && ((EntityHuman) entityliving).abilities.isInvulnerable ? false : --this.b > 0));
        }

        public void e() {
            this.a.a((Entity) this.a.getGoalTarget(), 10.0F, 10.0F);
            ((EntitySlime.ControllerMoveSlime) this.a.getControllerMove()).a(this.a.yaw, this.a.cX());
        }
    }

    static class ControllerMoveSlime extends ControllerMove {

        private float i;
        private int j;
        private EntitySlime k;
        private boolean l;

        public ControllerMoveSlime(EntitySlime entityslime) {
            super(entityslime);
            this.k = entityslime;
            this.i = 180.0F * entityslime.yaw / 3.1415927F;
        }

        public void a(float f, boolean flag) {
            this.i = f;
            this.l = flag;
        }

        public void a(double d0) {
            this.e = d0;
            this.h = ControllerMove.Operation.MOVE_TO;
        }

        public void c() {
            this.a.yaw = this.a(this.a.yaw, this.i, 90.0F);
            this.a.aP = this.a.yaw;
            this.a.aN = this.a.yaw;
            if (this.h != ControllerMove.Operation.MOVE_TO) {
                this.a.o(0.0F);
            } else {
                this.h = ControllerMove.Operation.WAIT;
                if (this.a.onGround) {
                    this.a.l((float) (this.e * this.a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue()));
                    if (this.j-- <= 0) {
                        this.j = this.k.cV();
                        if (this.l) {
                            this.j /= 3;
                        }

                        this.k.getControllerJump().a();
                        if (this.k.dd()) {
                            this.k.a(this.k.da(), this.k.ce(), ((this.k.getRandom().nextFloat() - this.k.getRandom().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                        }
                    } else {
                        this.k.be = this.k.bf = 0.0F;
                        this.a.l(0.0F);
                    }
                } else {
                    this.a.l((float) (this.e * this.a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue()));
                }

            }
        }
    }
}
