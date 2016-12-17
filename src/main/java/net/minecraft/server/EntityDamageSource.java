package net.minecraft.server;

import javax.annotation.Nullable;

public class EntityDamageSource extends DamageSource {

    protected Entity s;
    private boolean t = false;

    public EntityDamageSource(String s, Entity entity) {
        super(s);
        this.s = entity;
    }

    public EntityDamageSource w() {
        this.t = true;
        return this;
    }

    public boolean x() {
        return this.t;
    }

    @Nullable
    public Entity getEntity() {
        return this.s;
    }

    public IChatBaseComponent getLocalizedDeathMessage(EntityLiving entityliving) {
        ItemStack itemstack = this.s instanceof EntityLiving ? ((EntityLiving) this.s).getItemInMainHand() : null;
        String s = "death.attack." + this.translationIndex;
        String s1 = s + ".item";

        return itemstack != null && itemstack.hasName() && LocaleI18n.c(s1) ? new ChatMessage(s1, new Object[] { entityliving.getScoreboardDisplayName(), this.s.getScoreboardDisplayName(), itemstack.B()}) : new ChatMessage(s, new Object[] { entityliving.getScoreboardDisplayName(), this.s.getScoreboardDisplayName()});
    }

    public boolean r() {
        return this.s != null && this.s instanceof EntityLiving && !(this.s instanceof EntityHuman);
    }

    @Nullable
    public Vec3D v() {
        return new Vec3D(this.s.locX, this.s.locY, this.s.locZ);
    }
}
