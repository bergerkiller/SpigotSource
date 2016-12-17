package net.minecraft.server;

import javax.annotation.Nullable;

public class EntityMagmaCube extends EntitySlime {

    public EntityMagmaCube(World world) {
        super(world);
        this.fireProof = true;
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
    }

    public boolean cG() {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    public boolean canSpawn() {
        return this.world.a(this.getBoundingBox(), (Entity) this) && this.world.getCubes(this, this.getBoundingBox()).isEmpty() && !this.world.containsLiquid(this.getBoundingBox());
    }

    protected void setSize(int i) {
        super.setSize(i);
        this.getAttributeInstance(GenericAttributes.g).setValue((double) (i * 3));
    }

    public float e(float f) {
        return 1.0F;
    }

    protected EnumParticle o() {
        return EnumParticle.FLAME;
    }

    protected EntitySlime cU() {
        return new EntityMagmaCube(this.world);
    }

    @Nullable
    protected MinecraftKey J() {
        return !this.dc() ? LootTables.ae : LootTables.a;
    }

    public boolean isBurning() {
        return false;
    }

    protected int cV() {
        return super.cV() * 4;
    }

    protected void cW() {
        this.a *= 0.9F;
    }

    protected void ci() {
        this.motY = (double) (0.42F + (float) this.getSize() * 0.1F);
        this.impulse = true;
    }

    protected void ck() {
        this.motY = (double) (0.22F + (float) this.getSize() * 0.05F);
        this.impulse = true;
    }

    public void e(float f, float f1) {}

    protected boolean cX() {
        return true;
    }

    protected int cY() {
        return super.cY() + 2;
    }

    protected SoundEffect bS() {
        return this.dc() ? SoundEffects.fB : SoundEffects.dl;
    }

    protected SoundEffect bT() {
        return this.dc() ? SoundEffects.fA : SoundEffects.dk;
    }

    protected SoundEffect cZ() {
        return this.dc() ? SoundEffects.fC : SoundEffects.dn;
    }

    protected SoundEffect da() {
        return SoundEffects.dm;
    }
}
