package net.minecraft.server;

import java.util.UUID;
import javax.annotation.Nullable;

public class EntityPigZombie extends EntityZombie {

    private static final UUID b = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
    private static final AttributeModifier c = (new AttributeModifier(EntityPigZombie.b, "Attacking speed boost", 0.05D, 0)).a(false);
    public int angerLevel;
    private int soundDelay;
    private UUID hurtBy;

    public EntityPigZombie(World world) {
        super(world);
        this.fireProof = true;
    }

    public void a(@Nullable EntityLiving entityliving) {
        super.a(entityliving);
        if (entityliving != null) {
            this.hurtBy = entityliving.getUniqueID();
        }

    }

    protected void o() {
        this.targetSelector.a(1, new EntityPigZombie.PathfinderGoalAngerOther(this));
        this.targetSelector.a(2, new EntityPigZombie.PathfinderGoalAnger(this));
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(EntityPigZombie.a).setValue(0.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.23000000417232513D);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(5.0D);
    }

    public void m() {
        super.m();
    }

    protected void M() {
        AttributeInstance attributeinstance = this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);

        if (this.db()) {
            if (!this.isBaby() && !attributeinstance.a(EntityPigZombie.c)) {
                attributeinstance.b(EntityPigZombie.c);
            }

            --this.angerLevel;
        } else if (attributeinstance.a(EntityPigZombie.c)) {
            attributeinstance.c(EntityPigZombie.c);
        }

        if (this.soundDelay > 0 && --this.soundDelay == 0) {
            this.a(SoundEffects.hs, this.ce() * 2.0F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
        }

        if (this.angerLevel > 0 && this.hurtBy != null && this.getLastDamager() == null) {
            EntityHuman entityhuman = this.world.b(this.hurtBy);

            this.a((EntityLiving) entityhuman);
            this.killer = entityhuman;
            this.lastDamageByPlayerTime = this.bI();
        }

        super.M();
    }

    public boolean cG() {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    public boolean canSpawn() {
        return this.world.a(this.getBoundingBox(), (Entity) this) && this.world.getCubes(this, this.getBoundingBox()).isEmpty() && !this.world.containsLiquid(this.getBoundingBox());
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setShort("Anger", (short) this.angerLevel);
        if (this.hurtBy != null) {
            nbttagcompound.setString("HurtBy", this.hurtBy.toString());
        } else {
            nbttagcompound.setString("HurtBy", "");
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.angerLevel = nbttagcompound.getShort("Anger");
        String s = nbttagcompound.getString("HurtBy");

        if (!s.isEmpty()) {
            this.hurtBy = UUID.fromString(s);
            EntityHuman entityhuman = this.world.b(this.hurtBy);

            this.a((EntityLiving) entityhuman);
            if (entityhuman != null) {
                this.killer = entityhuman;
                this.lastDamageByPlayerTime = this.bI();
            }
        }

    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable(damagesource)) {
            return false;
        } else {
            Entity entity = damagesource.getEntity();

            if (entity instanceof EntityHuman) {
                this.a(entity);
            }

            return super.damageEntity(damagesource, f);
        }
    }

    private void a(Entity entity) {
        this.angerLevel = 400 + this.random.nextInt(400);
        this.soundDelay = this.random.nextInt(40);
        if (entity instanceof EntityLiving) {
            this.a((EntityLiving) entity);
        }

    }

    public boolean db() {
        return this.angerLevel > 0;
    }

    protected SoundEffect G() {
        return SoundEffects.hr;
    }

    protected SoundEffect bS() {
        return SoundEffects.hu;
    }

    protected SoundEffect bT() {
        return SoundEffects.ht;
    }

    @Nullable
    protected MinecraftKey J() {
        return LootTables.aj;
    }

    public boolean a(EntityHuman entityhuman, EnumHand enumhand, @Nullable ItemStack itemstack) {
        return false;
    }

    protected void a(DifficultyDamageScaler difficultydamagescaler) {
        this.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
    }

    @Nullable
    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, @Nullable GroupDataEntity groupdataentity) {
        super.prepare(difficultydamagescaler, groupdataentity);
        this.clearVillagerType();
        return groupdataentity;
    }

    static class PathfinderGoalAnger extends PathfinderGoalNearestAttackableTarget<EntityHuman> {

        public PathfinderGoalAnger(EntityPigZombie entitypigzombie) {
            super(entitypigzombie, EntityHuman.class, true);
        }

        public boolean a() {
            return ((EntityPigZombie) this.e).db() && super.a();
        }
    }

    static class PathfinderGoalAngerOther extends PathfinderGoalHurtByTarget {

        public PathfinderGoalAngerOther(EntityPigZombie entitypigzombie) {
            super(entitypigzombie, true, new Class[0]);
        }

        protected void a(EntityCreature entitycreature, EntityLiving entityliving) {
            super.a(entitycreature, entityliving);
            if (entitycreature instanceof EntityPigZombie) {
                ((EntityPigZombie) entitycreature).a((Entity) entityliving);
            }

        }
    }
}
