package net.minecraft.server;

public class SoundEffectType {

    public static final SoundEffectType a = new SoundEffectType(1.0F, 1.0F, SoundEffects.gY, SoundEffects.hg, SoundEffects.hd, SoundEffects.hc, SoundEffects.hb);
    public static final SoundEffectType b = new SoundEffectType(1.0F, 1.0F, SoundEffects.ca, SoundEffects.ce, SoundEffects.cd, SoundEffects.cc, SoundEffects.cb);
    public static final SoundEffectType c = new SoundEffectType(1.0F, 1.0F, SoundEffects.bV, SoundEffects.bZ, SoundEffects.bY, SoundEffects.bX, SoundEffects.bW);
    public static final SoundEffectType d = new SoundEffectType(1.0F, 1.0F, SoundEffects.ga, SoundEffects.gi, SoundEffects.gf, SoundEffects.ge, SoundEffects.gd);
    public static final SoundEffectType e = new SoundEffectType(1.0F, 1.5F, SoundEffects.do, SoundEffects.du, SoundEffects.dr, SoundEffects.dq, SoundEffects.dp);
    public static final SoundEffectType f = new SoundEffectType(1.0F, 1.0F, SoundEffects.bQ, SoundEffects.bU, SoundEffects.bT, SoundEffects.bS, SoundEffects.bR);
    public static final SoundEffectType g = new SoundEffectType(1.0F, 1.0F, SoundEffects.ag, SoundEffects.ak, SoundEffects.aj, SoundEffects.ai, SoundEffects.ah);
    public static final SoundEffectType h = new SoundEffectType(1.0F, 1.0F, SoundEffects.eH, SoundEffects.eL, SoundEffects.eK, SoundEffects.eJ, SoundEffects.eI);
    public static final SoundEffectType i = new SoundEffectType(1.0F, 1.0F, SoundEffects.fM, SoundEffects.fQ, SoundEffects.fP, SoundEffects.fO, SoundEffects.fN);
    public static final SoundEffectType j = new SoundEffectType(1.0F, 1.0F, SoundEffects.cW, SoundEffects.da, SoundEffects.cZ, SoundEffects.cY, SoundEffects.cX);
    public static final SoundEffectType k = new SoundEffectType(0.3F, 1.0F, SoundEffects.b, SoundEffects.h, SoundEffects.g, SoundEffects.e, SoundEffects.d);
    public static final SoundEffectType l = new SoundEffectType(1.0F, 1.0F, SoundEffects.fr, SoundEffects.fz, SoundEffects.fx, SoundEffects.fu, SoundEffects.ft);
    public final float m;
    public final float n;
    private final SoundEffect o;
    private final SoundEffect p;
    private final SoundEffect q;
    private final SoundEffect r;
    private final SoundEffect s;

    public SoundEffectType(float f, float f1, SoundEffect soundeffect, SoundEffect soundeffect1, SoundEffect soundeffect2, SoundEffect soundeffect3, SoundEffect soundeffect4) {
        this.m = f;
        this.n = f1;
        this.o = soundeffect;
        this.p = soundeffect1;
        this.q = soundeffect2;
        this.r = soundeffect3;
        this.s = soundeffect4;
    }

    public float a() {
        return this.m;
    }

    public float b() {
        return this.n;
    }

    public SoundEffect d() {
        return this.p;
    }

    public SoundEffect e() {
        return this.q;
    }

    public SoundEffect g() {
        return this.s;
    }
}
