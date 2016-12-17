package net.minecraft.server;

public class MobEffectAbsorption extends MobEffectList {

    protected MobEffectAbsorption(boolean flag, int i) {
        super(flag, i);
    }

    public void a(EntityLiving entityliving, AttributeMapBase attributemapbase, int i) {
        entityliving.setAbsorptionHearts(entityliving.getAbsorptionHearts() - (float) (4 * (i + 1)));
        super.a(entityliving, attributemapbase, i);
    }

    public void b(EntityLiving entityliving, AttributeMapBase attributemapbase, int i) {
        entityliving.setAbsorptionHearts(entityliving.getAbsorptionHearts() + (float) (4 * (i + 1)));
        super.b(entityliving, attributemapbase, i);
    }
}
