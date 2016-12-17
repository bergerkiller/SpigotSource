package net.minecraft.server;

import javax.annotation.Nullable;

public abstract class EntityAnimal extends EntityAgeable implements IAnimal {

    protected Block bz;
    private int bw;
    private EntityHuman bx;

    public EntityAnimal(World world) {
        super(world);
        this.bz = Blocks.GRASS;
    }

    protected void M() {
        if (this.getAge() != 0) {
            this.bw = 0;
        }

        super.M();
    }

    public void n() {
        super.n();
        if (this.getAge() != 0) {
            this.bw = 0;
        }

        if (this.bw > 0) {
            --this.bw;
            if (this.bw % 10 == 0) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;

                this.world.addParticle(EnumParticle.HEART, this.locX + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, this.locY + 0.5D + (double) (this.random.nextFloat() * this.length), this.locZ + (double) (this.random.nextFloat() * this.width * 2.0F) - (double) this.width, d0, d1, d2, new int[0]);
            }
        }

    }

    /* CraftBukkit start
    // Function disabled as it has no special function anymore after
    // setSitting is disabled.
    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable(damagesource)) {
            return false;
        } else {
            this.bw = 0;
            return super.damageEntity(damagesource, f);
        }
    }
    // CraftBukkit end */

    public float a(BlockPosition blockposition) {
        return this.world.getType(blockposition.down()).getBlock() == Blocks.GRASS ? 10.0F : this.world.n(blockposition) - 0.5F;
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("InLove", this.bw);
    }

    public double ax() {
        return 0.29D;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.bw = nbttagcompound.getInt("InLove");
    }

    public boolean cG() {
        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.getBoundingBox().b);
        int k = MathHelper.floor(this.locZ);
        BlockPosition blockposition = new BlockPosition(i, j, k);

        return this.world.getType(blockposition.down()).getBlock() == this.bz && this.world.j(blockposition) > 8 && super.cG();
    }

    public int C() {
        return 120;
    }

    protected boolean isTypeNotPersistent() {
        return false;
    }

    protected int getExpValue(EntityHuman entityhuman) {
        return 1 + this.world.random.nextInt(3);
    }

    public boolean e(@Nullable ItemStack itemstack) {
        return itemstack == null ? false : itemstack.getItem() == Items.WHEAT;
    }

    public boolean a(EntityHuman entityhuman, EnumHand enumhand, @Nullable ItemStack itemstack) {
        if (itemstack != null) {
            if (this.e(itemstack) && this.getAge() == 0 && this.bw <= 0) {
                this.a(entityhuman, itemstack);
                this.c(entityhuman);
                return true;
            }

            if (this.isBaby() && this.e(itemstack)) {
                this.a(entityhuman, itemstack);
                this.setAge((int) ((float) (-this.getAge() / 20) * 0.1F), true);
                return true;
            }
        }

        return super.a(entityhuman, enumhand, itemstack);
    }

    protected void a(EntityHuman entityhuman, ItemStack itemstack) {
        if (!entityhuman.abilities.canInstantlyBuild) {
            --itemstack.count;
        }

    }

    public void c(EntityHuman entityhuman) {
        this.bw = 600;
        this.bx = entityhuman;
        this.world.broadcastEntityEffect(this, (byte) 18);
    }

    public EntityHuman getBreedCause() {
        return this.bx;
    }

    public boolean isInLove() {
        return this.bw > 0;
    }

    public void resetLove() {
        this.bw = 0;
    }

    public boolean mate(EntityAnimal entityanimal) {
        return entityanimal == this ? false : (entityanimal.getClass() != this.getClass() ? false : this.isInLove() && entityanimal.isInLove());
    }
}
