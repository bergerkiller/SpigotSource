package net.minecraft.server;

public final class WorldSettings {

    private final long a;
    private final WorldSettings.EnumGamemode b;
    private final boolean c;
    private final boolean d;
    private final WorldType e;
    private boolean f;
    private boolean g;
    private String h;

    public WorldSettings(long i, WorldSettings.EnumGamemode worldsettings_enumgamemode, boolean flag, boolean flag1, WorldType worldtype) {
        this.h = "";
        this.a = i;
        this.b = worldsettings_enumgamemode;
        this.c = flag;
        this.d = flag1;
        this.e = worldtype;
    }

    public WorldSettings(WorldData worlddata) {
        this(worlddata.getSeed(), worlddata.getGameType(), worlddata.shouldGenerateMapFeatures(), worlddata.isHardcore(), worlddata.getType());
    }

    public WorldSettings a() {
        this.g = true;
        return this;
    }

    public WorldSettings setGeneratorSettings(String s) {
        this.h = s;
        return this;
    }

    public boolean c() {
        return this.g;
    }

    public long d() {
        return this.a;
    }

    public WorldSettings.EnumGamemode e() {
        return this.b;
    }

    public boolean f() {
        return this.d;
    }

    public boolean g() {
        return this.c;
    }

    public WorldType h() {
        return this.e;
    }

    public boolean i() {
        return this.f;
    }

    public static WorldSettings.EnumGamemode a(int i) {
        return WorldSettings.EnumGamemode.getById(i);
    }

    public String j() {
        return this.h;
    }

    public static enum EnumGamemode {

        NOT_SET(-1, "", ""), SURVIVAL(0, "survival", "s"), CREATIVE(1, "creative", "c"), ADVENTURE(2, "adventure", "a"), SPECTATOR(3, "spectator", "sp");

        int f;
        String g;
        String h;

        private EnumGamemode(int i, String s, String s1) {
            this.f = i;
            this.g = s;
            this.h = s1;
        }

        public int getId() {
            return this.f;
        }

        public String b() {
            return this.g;
        }

        public void a(PlayerAbilities playerabilities) {
            if (this == WorldSettings.EnumGamemode.CREATIVE) {
                playerabilities.canFly = true;
                playerabilities.canInstantlyBuild = true;
                playerabilities.isInvulnerable = true;
            } else if (this == WorldSettings.EnumGamemode.SPECTATOR) {
                playerabilities.canFly = true;
                playerabilities.canInstantlyBuild = false;
                playerabilities.isInvulnerable = true;
                playerabilities.isFlying = true;
            } else {
                playerabilities.canFly = false;
                playerabilities.canInstantlyBuild = false;
                playerabilities.isInvulnerable = false;
                playerabilities.isFlying = false;
            }

            playerabilities.mayBuild = !this.c();
        }

        public boolean c() {
            return this == WorldSettings.EnumGamemode.ADVENTURE || this == WorldSettings.EnumGamemode.SPECTATOR;
        }

        public boolean isCreative() {
            return this == WorldSettings.EnumGamemode.CREATIVE;
        }

        public boolean e() {
            return this == WorldSettings.EnumGamemode.SURVIVAL || this == WorldSettings.EnumGamemode.ADVENTURE;
        }

        public static WorldSettings.EnumGamemode getById(int i) {
            return a(i, WorldSettings.EnumGamemode.SURVIVAL);
        }

        public static WorldSettings.EnumGamemode a(int i, WorldSettings.EnumGamemode worldsettings_enumgamemode) {
            WorldSettings.EnumGamemode[] aworldsettings_enumgamemode = values();
            int j = aworldsettings_enumgamemode.length;

            for (int k = 0; k < j; ++k) {
                WorldSettings.EnumGamemode worldsettings_enumgamemode1 = aworldsettings_enumgamemode[k];

                if (worldsettings_enumgamemode1.f == i) {
                    return worldsettings_enumgamemode1;
                }
            }

            return worldsettings_enumgamemode;
        }

        public static WorldSettings.EnumGamemode a(String s, WorldSettings.EnumGamemode worldsettings_enumgamemode) {
            WorldSettings.EnumGamemode[] aworldsettings_enumgamemode = values();
            int i = aworldsettings_enumgamemode.length;

            for (int j = 0; j < i; ++j) {
                WorldSettings.EnumGamemode worldsettings_enumgamemode1 = aworldsettings_enumgamemode[j];

                if (worldsettings_enumgamemode1.g.equals(s) || worldsettings_enumgamemode1.h.equals(s)) {
                    return worldsettings_enumgamemode1;
                }
            }

            return worldsettings_enumgamemode;
        }
    }
}
