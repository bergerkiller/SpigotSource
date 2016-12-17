package net.minecraft.server;

import javax.annotation.Nullable;

public class EntityEndermite extends EntityMonster {

    private int a = 0;
    private boolean b = false;

    public EntityEndermite(World world) {
        super(world);
        this.b_ = 3;
        this.setSize(0.4F, 0.3F);
    }

    protected void r() {
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, 1.0D, false));
        this.goalSelector.a(3, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
    }

    public float getHeadHeight() {
        return 0.1F;
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(2.0D);
    }

    protected boolean playStepSound() {
        return false;
    }

    protected SoundEffect G() {
        return SoundEffects.bb;
    }

    protected SoundEffect bS() {
        return SoundEffects.bd;
    }

    protected SoundEffect bT() {
        return SoundEffects.bc;
    }

    protected void a(BlockPosition blockposition, Block block) {
        this.a(SoundEffects.be, 0.15F, 1.0F);
    }

    @Nullable
    protected MinecraftKey J() {
        return LootTables.ah;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.getInt("Lifetime");
        this.b = nbttagcompound.getBoolean("PlayerSpawned");
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Lifetime", this.a);
        nbttagcompound.setBoolean("PlayerSpawned", this.b);
    }

    public void m() {
        this.aN = this.yaw;
        super.m();
    }

    public double ax() {
        return 0.3D;
    }

    public boolean o() {
        return this.b;
    }

    public void a(boolean flag) {
        this.b = flag;
    }

    public void n() {
        super.n();
        if (this.world.isClientSide) {
            for (int i = 0; i < 2; ++i) {
                this.world.addParticle(EnumParticle.PORTAL, this.locX + (this.random.nextDouble() - 0.5D) * (double) this.width, this.locY + this.random.nextDouble() * (double) this.length, this.locZ + (this.random.nextDouble() - 0.5D) * (double) this.width, (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D, new int[0]);
            }
        } else {
            if (!this.isPersistent()) {
                ++this.a;
            }

            if (this.a >= 2400) {
                this.die();
            }
        }

    }

    protected boolean s_() {
        return true;
    }

    public boolean cG() {
        if (super.cG()) {
            EntityHuman entityhuman = this.world.findNearbyPlayer(this, 5.0D);

            return entityhuman == null;
        } else {
            return false;
        }
    }

    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.ARTHROPOD;
    }
}
