package net.minecraft.server;

public enum EnumHorseType {

    HORSE("horse", "horse_white", SoundEffects.co, SoundEffects.cv, SoundEffects.cs, LootTables.E), DONKEY("donkey", "donkey", SoundEffects.ax, SoundEffects.aB, SoundEffects.aA, LootTables.E), MULE("mule", "mule", SoundEffects.dy, SoundEffects.dA, SoundEffects.dz, LootTables.E), ZOMBIE("zombiehorse", "horse_zombie", SoundEffects.hm, SoundEffects.ho, SoundEffects.hn, LootTables.F), SKELETON("skeletonhorse", "horse_skeleton", SoundEffects.fk, SoundEffects.fm, SoundEffects.fl, LootTables.G);

    private final ChatMessage f;
    private final MinecraftKey g;
    private final SoundEffect h;
    private final SoundEffect i;
    private final SoundEffect j;
    private MinecraftKey k;

    private EnumHorseType(String s, String s1, SoundEffect soundeffect, SoundEffect soundeffect1, SoundEffect soundeffect2, MinecraftKey minecraftkey) {
        this.k = minecraftkey;
        this.f = new ChatMessage("entity." + s + ".name", new Object[0]);
        this.g = new MinecraftKey("textures/entity/horse/" + s1 + ".png");
        this.h = soundeffect1;
        this.i = soundeffect;
        this.j = soundeffect2;
    }

    public SoundEffect a() {
        return this.i;
    }

    public SoundEffect b() {
        return this.h;
    }

    public SoundEffect c() {
        return this.j;
    }

    public ChatMessage d() {
        return this.f;
    }

    public boolean f() {
        return this == EnumHorseType.DONKEY || this == EnumHorseType.MULE;
    }

    public boolean g() {
        return this == EnumHorseType.DONKEY || this == EnumHorseType.MULE;
    }

    public boolean h() {
        return this == EnumHorseType.ZOMBIE || this == EnumHorseType.SKELETON;
    }

    public boolean i() {
        return !this.h() && this != EnumHorseType.MULE;
    }

    public boolean j() {
        return this == EnumHorseType.HORSE;
    }

    public int k() {
        return this.ordinal();
    }

    public static EnumHorseType a(int i) {
        return values()[i];
    }

    public MinecraftKey l() {
        return this.k;
    }
}
