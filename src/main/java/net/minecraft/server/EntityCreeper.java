package net.minecraft.server;

import javax.annotation.Nullable;
// CraftBukkit start
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.entity.ExplosionPrimeEvent;
// CraftBukkit end

public class EntityCreeper extends EntityMonster {

    private static final DataWatcherObject<Integer> a = DataWatcher.a(EntityCreeper.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Boolean> b = DataWatcher.a(EntityCreeper.class, DataWatcherRegistry.h);
    private static final DataWatcherObject<Boolean> c = DataWatcher.a(EntityCreeper.class, DataWatcherRegistry.h);
    private int bw;
    private int fuseTicks;
    private int maxFuseTicks = 30;
    private int explosionRadius = 3;
    private int bA = 0;

    public EntityCreeper(World world) {
        super(world);
        this.setSize(0.6F, 1.7F);
    }

    protected void r() {
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalSwell(this));
        this.goalSelector.a(3, new PathfinderGoalAvoidTarget(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, false));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.8D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
    }

    public int aW() {
        return this.getGoalTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
    }

    public void e(float f, float f1) {
        super.e(f, f1);
        this.fuseTicks = (int) ((float) this.fuseTicks + f * 1.5F);
        if (this.fuseTicks > this.maxFuseTicks - 5) {
            this.fuseTicks = this.maxFuseTicks - 5;
        }

    }

    protected void i() {
        super.i();
        this.datawatcher.register(EntityCreeper.a, Integer.valueOf(-1));
        this.datawatcher.register(EntityCreeper.b, Boolean.valueOf(false));
        this.datawatcher.register(EntityCreeper.c, Boolean.valueOf(false));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (((Boolean) this.datawatcher.get(EntityCreeper.b)).booleanValue()) {
            nbttagcompound.setBoolean("powered", true);
        }

        nbttagcompound.setShort("Fuse", (short) this.maxFuseTicks);
        nbttagcompound.setByte("ExplosionRadius", (byte) this.explosionRadius);
        nbttagcompound.setBoolean("ignited", this.isIgnited());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.datawatcher.set(EntityCreeper.b, Boolean.valueOf(nbttagcompound.getBoolean("powered")));
        if (nbttagcompound.hasKeyOfType("Fuse", 99)) {
            this.maxFuseTicks = nbttagcompound.getShort("Fuse");
        }

        if (nbttagcompound.hasKeyOfType("ExplosionRadius", 99)) {
            this.explosionRadius = nbttagcompound.getByte("ExplosionRadius");
        }

        if (nbttagcompound.getBoolean("ignited")) {
            this.dd();
        }

    }

    public void m() {
        if (this.isAlive()) {
            this.bw = this.fuseTicks;
            if (this.isIgnited()) {
                this.a(1);
            }

            int i = this.db();

            if (i > 0 && this.fuseTicks == 0) {
                this.a(SoundEffects.at, 1.0F, 0.5F);
            }

            this.fuseTicks += i;
            if (this.fuseTicks < 0) {
                this.fuseTicks = 0;
            }

            if (this.fuseTicks >= this.maxFuseTicks) {
                this.fuseTicks = this.maxFuseTicks;
                this.dg();
            }
        }

        super.m();
    }

    protected SoundEffect bS() {
        return SoundEffects.as;
    }

    protected SoundEffect bT() {
        return SoundEffects.ar;
    }

    public void die(DamageSource damagesource) {
        // super.die(damagesource); // CraftBukkit - Moved to end
        if (this.world.getGameRules().getBoolean("doMobLoot")) {
            if (damagesource.getEntity() instanceof EntitySkeleton) {
                int i = Item.getId(Items.RECORD_13);
                int j = Item.getId(Items.RECORD_WAIT);
                int k = i + this.random.nextInt(j - i + 1);

                this.a(Item.getById(k), 1);
            } else if (damagesource.getEntity() instanceof EntityCreeper && damagesource.getEntity() != this && ((EntityCreeper) damagesource.getEntity()).isPowered() && ((EntityCreeper) damagesource.getEntity()).canCauseHeadDrop()) {
                ((EntityCreeper) damagesource.getEntity()).setCausedHeadDrop();
                this.a(new ItemStack(Items.SKULL, 1, 4), 0.0F);
            }
        }
        super.die(damagesource); // CraftBukkit - Moved from above

    }

    public boolean B(Entity entity) {
        return true;
    }

    public boolean isPowered() {
        return ((Boolean) this.datawatcher.get(EntityCreeper.b)).booleanValue();
    }

    @Nullable
    protected MinecraftKey J() {
        return LootTables.q;
    }

    public int db() {
        return ((Integer) this.datawatcher.get(EntityCreeper.a)).intValue();
    }

    public void a(int i) {
        this.datawatcher.set(EntityCreeper.a, Integer.valueOf(i));
    }

    public void onLightningStrike(EntityLightning entitylightning) {
        super.onLightningStrike(entitylightning);
        // CraftBukkit start
        if (CraftEventFactory.callCreeperPowerEvent(this, entitylightning, org.bukkit.event.entity.CreeperPowerEvent.PowerCause.LIGHTNING).isCancelled()) {
            return;
        }

        this.setPowered(true);
    }

    public void setPowered(boolean powered) {
        this.datawatcher.set(EntityCreeper.b, powered);
    }
    // CraftBukkit end

    protected boolean a(EntityHuman entityhuman, EnumHand enumhand, @Nullable ItemStack itemstack) {
        if (itemstack != null && itemstack.getItem() == Items.FLINT_AND_STEEL) {
            this.world.a(entityhuman, this.locX, this.locY, this.locZ, SoundEffects.bx, this.bA(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            entityhuman.a(enumhand);
            if (!this.world.isClientSide) {
                this.dd();
                itemstack.damage(1, entityhuman);
                return true;
            }
        }

        return super.a(entityhuman, enumhand, itemstack);
    }

    private void dg() {
        if (!this.world.isClientSide) {
            boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
            float f = this.isPowered() ? 2.0F : 1.0F;

            // CraftBukkit start
            ExplosionPrimeEvent event = new ExplosionPrimeEvent(this.getBukkitEntity(), this.explosionRadius * f, false);
            this.world.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                this.aU = true;
                this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), flag);
                this.die();
            } else {
                fuseTicks = 0;
            }
            // CraftBukkit end
        }

    }

    public boolean isIgnited() {
        return ((Boolean) this.datawatcher.get(EntityCreeper.c)).booleanValue();
    }

    public void dd() {
        this.datawatcher.set(EntityCreeper.c, Boolean.valueOf(true));
    }

    public boolean canCauseHeadDrop() {
        return this.bA < 1 && this.world.getGameRules().getBoolean("doMobLoot");
    }

    public void setCausedHeadDrop() {
        ++this.bA;
    }
}
