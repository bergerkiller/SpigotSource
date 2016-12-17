package net.minecraft.server;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;

public class EntityEnderman extends EntityMonster {

    private static final UUID a = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier b = (new AttributeModifier(EntityEnderman.a, "Attacking speed boost", 0.15000000596046448D, 0)).a(false);
    private static final Set<Block> c = Sets.newIdentityHashSet();
    private static final DataWatcherObject<Optional<IBlockData>> bw = DataWatcher.a(EntityEnderman.class, DataWatcherRegistry.g);
    private static final DataWatcherObject<Boolean> bx = DataWatcher.a(EntityEnderman.class, DataWatcherRegistry.h);
    private int by = 0;
    private int bz = 0;

    public EntityEnderman(World world) {
        super(world);
        this.setSize(0.6F, 2.9F);
        this.P = 1.0F;
        this.a(PathType.WATER, -1.0F);
    }

    protected void r() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, 1.0D, false));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.goalSelector.a(10, new EntityEnderman.PathfinderGoalEndermanPlaceBlock(this));
        this.goalSelector.a(11, new EntityEnderman.PathfinderGoalEndermanPickupBlock(this));
        this.targetSelector.a(1, new EntityEnderman.PathfinderGoalPlayerWhoLookedAtTarget(this));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityEndermite.class, 10, true, false, new Predicate() {
            public boolean a(@Nullable EntityEndermite entityendermite) {
                return entityendermite.o();
            }

            public boolean apply(Object object) {
                return this.a((EntityEndermite) object);
            }
        }));
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(40.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30000001192092896D);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(7.0D);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(64.0D);
    }

    public void setGoalTarget(@Nullable EntityLiving entityliving) {
        // CraftBukkit start - fire event
        setGoalTarget(entityliving, EntityTargetEvent.TargetReason.UNKNOWN, true);
    }

    @Override
    public boolean setGoalTarget(EntityLiving entityliving, org.bukkit.event.entity.EntityTargetEvent.TargetReason reason, boolean fireEvent) {
        if (!super.setGoalTarget(entityliving, reason, fireEvent)) {
            return false;
        }
        entityliving = getGoalTarget();
        // CraftBukkit end
        AttributeInstance attributeinstance = this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);

        if (entityliving == null) {
            this.bz = 0;
            this.datawatcher.set(EntityEnderman.bx, Boolean.valueOf(false));
            attributeinstance.c(EntityEnderman.b);
        } else {
            this.bz = this.ticksLived;
            this.datawatcher.set(EntityEnderman.bx, Boolean.valueOf(true));
            if (!attributeinstance.a(EntityEnderman.b)) {
                attributeinstance.b(EntityEnderman.b);
            }
        }
        return true;

    }

    protected void i() {
        super.i();
        this.datawatcher.register(EntityEnderman.bw, Optional.absent());
        this.datawatcher.register(EntityEnderman.bx, Boolean.valueOf(false));
    }

    public void o() {
        if (this.ticksLived >= this.by + 400) {
            this.by = this.ticksLived;
            if (!this.ad()) {
                this.world.a(this.locX, this.locY + (double) this.getHeadHeight(), this.locZ, SoundEffects.aZ, this.bA(), 2.5F, 1.0F, false);
            }
        }

    }

    public void a(DataWatcherObject<?> datawatcherobject) {
        if (EntityEnderman.bx.equals(datawatcherobject) && this.dd() && this.world.isClientSide) {
            this.o();
        }

        super.a(datawatcherobject);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        IBlockData iblockdata = this.getCarried();

        if (iblockdata != null) {
            nbttagcompound.setShort("carried", (short) Block.getId(iblockdata.getBlock()));
            nbttagcompound.setShort("carriedData", (short) iblockdata.getBlock().toLegacyData(iblockdata));
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        IBlockData iblockdata;

        if (nbttagcompound.hasKeyOfType("carried", 8)) {
            iblockdata = Block.getByName(nbttagcompound.getString("carried")).fromLegacyData(nbttagcompound.getShort("carriedData") & '\uffff');
        } else {
            iblockdata = Block.getById(nbttagcompound.getShort("carried")).fromLegacyData(nbttagcompound.getShort("carriedData") & '\uffff');
        }

        if (iblockdata == null || iblockdata.getBlock() == null || iblockdata.getMaterial() == Material.AIR) {
            iblockdata = null;
        }

        this.setCarried(iblockdata);
    }

    private boolean c(EntityHuman entityhuman) {
        ItemStack itemstack = entityhuman.inventory.armor[3];

        if (itemstack != null && itemstack.getItem() == Item.getItemOf(Blocks.PUMPKIN)) {
            return false;
        } else {
            Vec3D vec3d = entityhuman.f(1.0F).a();
            Vec3D vec3d1 = new Vec3D(this.locX - entityhuman.locX, this.getBoundingBox().b + (double) this.getHeadHeight() - (entityhuman.locY + (double) entityhuman.getHeadHeight()), this.locZ - entityhuman.locZ);
            double d0 = vec3d1.b();

            vec3d1 = vec3d1.a();
            double d1 = vec3d.b(vec3d1);

            return d1 > 1.0D - 0.025D / d0 ? entityhuman.hasLineOfSight(this) : false;
        }
    }

    public float getHeadHeight() {
        return 2.55F;
    }

    public void n() {
        if (this.world.isClientSide) {
            for (int i = 0; i < 2; ++i) {
                this.world.addParticle(EnumParticle.PORTAL, this.locX + (this.random.nextDouble() - 0.5D) * (double) this.width, this.locY + this.random.nextDouble() * (double) this.length - 0.25D, this.locZ + (this.random.nextDouble() - 0.5D) * (double) this.width, (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D, new int[0]);
            }
        }

        this.bd = false;
        super.n();
    }

    protected void M() {
        if (this.ah()) {
            this.damageEntity(DamageSource.DROWN, 1.0F);
        }

        if (this.world.B() && this.ticksLived >= this.bz + 600) {
            float f = this.e(1.0F);

            if (f > 0.5F && this.world.h(new BlockPosition(this)) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
                this.setGoalTarget((EntityLiving) null);
                this.db();
            }
        }

        super.M();
    }

    protected boolean db() {
        double d0 = this.locX + (this.random.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.locY + (double) (this.random.nextInt(64) - 32);
        double d2 = this.locZ + (this.random.nextDouble() - 0.5D) * 64.0D;

        return this.l(d0, d1, d2);
    }

    protected boolean a(Entity entity) {
        Vec3D vec3d = new Vec3D(this.locX - entity.locX, this.getBoundingBox().b + (double) (this.length / 2.0F) - entity.locY + (double) entity.getHeadHeight(), this.locZ - entity.locZ);

        vec3d = vec3d.a();
        double d0 = 16.0D;
        double d1 = this.locX + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.x * d0;
        double d2 = this.locY + (double) (this.random.nextInt(16) - 8) - vec3d.y * d0;
        double d3 = this.locZ + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.z * d0;

        return this.l(d1, d2, d3);
    }

    private boolean l(double d0, double d1, double d2) {
        boolean flag = this.k(d0, d1, d2);

        if (flag) {
            this.world.a((EntityHuman) null, this.lastX, this.lastY, this.lastZ, SoundEffects.ba, this.bA(), 1.0F, 1.0F);
            this.a(SoundEffects.ba, 1.0F, 1.0F);
        }

        return flag;
    }

    protected SoundEffect G() {
        return this.dd() ? SoundEffects.aY : SoundEffects.aV;
    }

    protected SoundEffect bS() {
        return SoundEffects.aX;
    }

    protected SoundEffect bT() {
        return SoundEffects.aW;
    }

    protected void dropEquipment(boolean flag, int i) {
        super.dropEquipment(flag, i);
        IBlockData iblockdata = this.getCarried();

        if (iblockdata != null) {
            this.a(new ItemStack(iblockdata.getBlock(), 1, iblockdata.getBlock().toLegacyData(iblockdata)), 0.0F);
        }

    }

    @Nullable
    protected MinecraftKey J() {
        return LootTables.v;
    }

    public void setCarried(@Nullable IBlockData iblockdata) {
        this.datawatcher.set(EntityEnderman.bw, Optional.fromNullable(iblockdata));
    }

    @Nullable
    public IBlockData getCarried() {
        return (IBlockData) ((Optional) this.datawatcher.get(EntityEnderman.bw)).orNull();
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable(damagesource)) {
            return false;
        } else if (damagesource instanceof EntityDamageSourceIndirect) {
            for (int i = 0; i < 64; ++i) {
                if (this.db()) {
                    return true;
                }
            }

            return false;
        } else {
            boolean flag = super.damageEntity(damagesource, f);

            if (damagesource.ignoresArmor() && this.random.nextInt(10) != 0) {
                this.db();
            }

            return flag;
        }
    }

    public boolean dd() {
        return ((Boolean) this.datawatcher.get(EntityEnderman.bx)).booleanValue();
    }

    static {
        EntityEnderman.c.add(Blocks.GRASS);
        EntityEnderman.c.add(Blocks.DIRT);
        EntityEnderman.c.add(Blocks.SAND);
        EntityEnderman.c.add(Blocks.GRAVEL);
        EntityEnderman.c.add(Blocks.YELLOW_FLOWER);
        EntityEnderman.c.add(Blocks.RED_FLOWER);
        EntityEnderman.c.add(Blocks.BROWN_MUSHROOM);
        EntityEnderman.c.add(Blocks.RED_MUSHROOM);
        EntityEnderman.c.add(Blocks.TNT);
        EntityEnderman.c.add(Blocks.CACTUS);
        EntityEnderman.c.add(Blocks.CLAY);
        EntityEnderman.c.add(Blocks.PUMPKIN);
        EntityEnderman.c.add(Blocks.MELON_BLOCK);
        EntityEnderman.c.add(Blocks.MYCELIUM);
    }

    static class PathfinderGoalEndermanPickupBlock extends PathfinderGoal {

        private final EntityEnderman enderman;

        public PathfinderGoalEndermanPickupBlock(EntityEnderman entityenderman) {
            this.enderman = entityenderman;
        }

        public boolean a() {
            return this.enderman.getCarried() != null ? false : (!this.enderman.world.getGameRules().getBoolean("mobGriefing") ? false : this.enderman.getRandom().nextInt(20) == 0);
        }

        public void e() {
            Random random = this.enderman.getRandom();
            World world = this.enderman.world;
            int i = MathHelper.floor(this.enderman.locX - 2.0D + random.nextDouble() * 4.0D);
            int j = MathHelper.floor(this.enderman.locY + random.nextDouble() * 3.0D);
            int k = MathHelper.floor(this.enderman.locZ - 2.0D + random.nextDouble() * 4.0D);
            BlockPosition blockposition = new BlockPosition(i, j, k);
            IBlockData iblockdata = world.getType(blockposition);
            Block block = iblockdata.getBlock();
            MovingObjectPosition movingobjectposition = world.rayTrace(new Vec3D((double) ((float) MathHelper.floor(this.enderman.locX) + 0.5F), (double) ((float) j + 0.5F), (double) ((float) MathHelper.floor(this.enderman.locZ) + 0.5F)), new Vec3D((double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F)), false, true, false);
            boolean flag = movingobjectposition != null && movingobjectposition.a().equals(blockposition);

            if (EntityEnderman.c.contains(block) && flag) {
                // CraftBukkit start - Pickup event
                if (!org.bukkit.craftbukkit.event.CraftEventFactory.callEntityChangeBlockEvent(this.enderman, this.enderman.world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), org.bukkit.Material.AIR).isCancelled()) {
                    this.enderman.setCarried(iblockdata);
                    world.setAir(blockposition);
                }
                // CraftBukkit end
            }

        }
    }

    static class PathfinderGoalEndermanPlaceBlock extends PathfinderGoal {

        private final EntityEnderman a;

        public PathfinderGoalEndermanPlaceBlock(EntityEnderman entityenderman) {
            this.a = entityenderman;
        }

        public boolean a() {
            return this.a.getCarried() == null ? false : (!this.a.world.getGameRules().getBoolean("mobGriefing") ? false : this.a.getRandom().nextInt(2000) == 0);
        }

        public void e() {
            Random random = this.a.getRandom();
            World world = this.a.world;
            int i = MathHelper.floor(this.a.locX - 1.0D + random.nextDouble() * 2.0D);
            int j = MathHelper.floor(this.a.locY + random.nextDouble() * 2.0D);
            int k = MathHelper.floor(this.a.locZ - 1.0D + random.nextDouble() * 2.0D);
            BlockPosition blockposition = new BlockPosition(i, j, k);
            IBlockData iblockdata = world.getType(blockposition);
            IBlockData iblockdata1 = world.getType(blockposition.down());
            IBlockData iblockdata2 = this.a.getCarried();

            if (iblockdata2 != null && this.a(world, blockposition, iblockdata2.getBlock(), iblockdata, iblockdata1)) {
                // CraftBukkit start - Place event
                if (!org.bukkit.craftbukkit.event.CraftEventFactory.callEntityChangeBlockEvent(this.a, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this.a.getCarried().getBlock(), this.a.getCarried().getBlock().toLegacyData(this.a.getCarried())).isCancelled()) {
                world.setTypeAndData(blockposition, iblockdata2, 3);
                this.a.setCarried((IBlockData) null);
                }
                // CraftBukkit end
            }

        }

        private boolean a(World world, BlockPosition blockposition, Block block, IBlockData iblockdata, IBlockData iblockdata1) {
            return !block.canPlace(world, blockposition) ? false : (iblockdata.getMaterial() != Material.AIR ? false : (iblockdata1.getMaterial() == Material.AIR ? false : iblockdata1.h()));
        }
    }

    static class PathfinderGoalPlayerWhoLookedAtTarget extends PathfinderGoalNearestAttackableTarget<EntityHuman> {

        private final EntityEnderman i;
        private EntityHuman j;
        private int k;
        private int l;

        public PathfinderGoalPlayerWhoLookedAtTarget(EntityEnderman entityenderman) {
            super(entityenderman, EntityHuman.class, false);
            this.i = entityenderman;
        }

        public boolean a() {
            double d0 = this.f();

            this.j = this.i.world.a(this.i.locX, this.i.locY, this.i.locZ, d0, d0, (Function) null, new Predicate() {
                public boolean a(@Nullable EntityHuman entityhuman) {
                    return entityhuman != null && PathfinderGoalPlayerWhoLookedAtTarget.this.i.c(entityhuman);
                }

                public boolean apply(Object object) {
                    return this.a((EntityHuman) object);
                }
            });
            return this.j != null;
        }

        public void c() {
            this.k = 5;
            this.l = 0;
        }

        public void d() {
            this.j = null;
            super.d();
        }

        public boolean b() {
            if (this.j != null) {
                if (!this.i.c(this.j)) {
                    return false;
                } else {
                    this.i.a((Entity) this.j, 10.0F, 10.0F);
                    return true;
                }
            } else {
                return this.d != null && ((EntityHuman) this.d).isAlive() ? true : super.b();
            }
        }

        public void e() {
            if (this.j != null) {
                if (--this.k <= 0) {
                    this.d = this.j;
                    this.j = null;
                    super.c();
                }
            } else {
                if (this.d != null) {
                    if (this.i.c((EntityHuman) this.d)) {
                        if (((EntityHuman) this.d).h(this.i) < 16.0D) {
                            this.i.db();
                        }

                        this.l = 0;
                    } else if (((EntityHuman) this.d).h(this.i) > 256.0D && this.l++ >= 30 && this.i.a((Entity) this.d)) {
                        this.l = 0;
                    }
                }

                super.e();
            }

        }
    }
}
