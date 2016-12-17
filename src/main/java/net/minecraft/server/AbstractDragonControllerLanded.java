package net.minecraft.server;

public abstract class AbstractDragonControllerLanded extends AbstractDragonController {

    public AbstractDragonControllerLanded(EntityEnderDragon entityenderdragon) {
        super(entityenderdragon);
    }

    public boolean a() {
        return true;
    }

    public float a(EntityComplexPart entitycomplexpart, DamageSource damagesource, float f) {
        if (damagesource.i() instanceof EntityArrow) {
            damagesource.i().setOnFire(1);
            return 0.0F;
        } else {
            return super.a(entitycomplexpart, damagesource, f);
        }
    }
}
