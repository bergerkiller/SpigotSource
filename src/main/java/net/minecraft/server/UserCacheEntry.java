package net.minecraft.server;

import com.mojang.authlib.GameProfile;
import java.util.Date;

class UserCacheEntry {

    private final GameProfile b;
    private final Date c;
    final UserCache a;

    private UserCacheEntry(UserCache usercache, GameProfile gameprofile, Date date) {
        this.a = usercache;
        this.b = gameprofile;
        this.c = date;
    }

    public GameProfile a() {
        return this.b;
    }

    public Date b() {
        return this.c;
    }

    UserCacheEntry(UserCache usercache, GameProfile gameprofile, Date date, GameProfileLookup gameprofilelookup) {
        this(usercache, gameprofile, date);
    }

    static Date a(UserCacheEntry usercacheentry) {
        return usercacheentry.c;
    }
}
