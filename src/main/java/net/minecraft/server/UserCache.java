package net.minecraft.server;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.io.IOUtils;

public class UserCache {

    public static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    private final Map c = Maps.newHashMap();
    private final Map d = Maps.newHashMap();
    private final LinkedList e = Lists.newLinkedList();
    private final MinecraftServer f;
    protected final Gson b;
    private final File g;
    private static final ParameterizedType h = new UserCacheEntryType();

    public UserCache(MinecraftServer minecraftserver, File file) {
        this.f = minecraftserver;
        this.g = file;
        GsonBuilder gsonbuilder = new GsonBuilder();

        gsonbuilder.registerTypeHierarchyAdapter(UserCacheEntry.class, new BanEntrySerializer(this, (GameProfileLookup) null));
        this.b = gsonbuilder.create();
        this.b();
    }

    private static GameProfile a(MinecraftServer minecraftserver, String s) {
        GameProfile[] agameprofile = new GameProfile[1];
        GameProfileLookup gameprofilelookup = new GameProfileLookup(agameprofile);

        minecraftserver.getGameProfileRepository().findProfilesByNames(new String[] { s}, Agent.MINECRAFT, gameprofilelookup);
        if (!minecraftserver.getOnlineMode() && agameprofile[0] == null) {
            UUID uuid = EntityHuman.a(new GameProfile((UUID) null, s));
            GameProfile gameprofile = new GameProfile(uuid, s);

            gameprofilelookup.onProfileLookupSucceeded(gameprofile);
        }

        return agameprofile[0];
    }

    public void a(GameProfile gameprofile) {
        this.a(gameprofile, (Date) null);
    }

    private void a(GameProfile gameprofile, Date date) {
        UUID uuid = gameprofile.getId();

        if (date == null) {
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(new Date());
            calendar.add(2, 1);
            date = calendar.getTime();
        }

        String s = gameprofile.getName().toLowerCase(Locale.ROOT);
        UserCacheEntry usercacheentry = new UserCacheEntry(this, gameprofile, date, (GameProfileLookup) null);

        if (this.d.containsKey(uuid)) {
            UserCacheEntry usercacheentry1 = (UserCacheEntry) this.d.get(uuid);

            this.c.remove(usercacheentry1.a().getName().toLowerCase(Locale.ROOT));
            this.c.put(gameprofile.getName().toLowerCase(Locale.ROOT), usercacheentry);
            this.e.remove(gameprofile);
        } else {
            this.d.put(uuid, usercacheentry);
            this.c.put(s, usercacheentry);
        }

        this.e.addFirst(gameprofile);
    }

    public GameProfile getProfile(String s) {
        String s1 = s.toLowerCase(Locale.ROOT);
        UserCacheEntry usercacheentry = (UserCacheEntry) this.c.get(s1);

        if (usercacheentry != null && (new Date()).getTime() >= UserCacheEntry.a(usercacheentry).getTime()) {
            this.d.remove(usercacheentry.a().getId());
            this.c.remove(usercacheentry.a().getName().toLowerCase(Locale.ROOT));
            this.e.remove(usercacheentry.a());
            usercacheentry = null;
        }

        GameProfile gameprofile;

        if (usercacheentry != null) {
            gameprofile = usercacheentry.a();
            this.e.remove(gameprofile);
            this.e.addFirst(gameprofile);
        } else {
            gameprofile = a(this.f, s); // Spigot - use correct case for offline players
            if (gameprofile != null) {
                this.a(gameprofile);
                usercacheentry = (UserCacheEntry) this.c.get(s1);
            }
        }

        if( !org.spigotmc.SpigotConfig.saveUserCacheOnStopOnly ) this.c(); // Spigot - skip saving if disabled
        return usercacheentry == null ? null : usercacheentry.a();
    }

    public String[] a() {
        ArrayList arraylist = Lists.newArrayList(this.c.keySet());

        return (String[]) arraylist.toArray(new String[arraylist.size()]);
    }

    public GameProfile a(UUID uuid) {
        UserCacheEntry usercacheentry = (UserCacheEntry) this.d.get(uuid);

        return usercacheentry == null ? null : usercacheentry.a();
    }

    private UserCacheEntry b(UUID uuid) {
        UserCacheEntry usercacheentry = (UserCacheEntry) this.d.get(uuid);

        if (usercacheentry != null) {
            GameProfile gameprofile = usercacheentry.a();

            this.e.remove(gameprofile);
            this.e.addFirst(gameprofile);
        }

        return usercacheentry;
    }

    public void b() {
        List list = null;
        BufferedReader bufferedreader = null;

        label64: {
            try {
                bufferedreader = Files.newReader(this.g, Charsets.UTF_8);
                list = (List) this.b.fromJson(bufferedreader, UserCache.h);
                break label64;
            } catch (FileNotFoundException filenotfoundexception) {
                ;
            // Spigot Start
            } catch (com.google.gson.JsonSyntaxException ex) {
                JsonList.a.warn( "Usercache.json is corrupted or has bad formatting. Deleting it to prevent further issues." );
                this.g.delete();
            // Spigot End
            } finally {
                IOUtils.closeQuietly(bufferedreader);
            }

            return;
        }

        if (list != null) {
            this.c.clear();
            this.d.clear();
            this.e.clear();
            list = Lists.reverse(list);
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                UserCacheEntry usercacheentry = (UserCacheEntry) iterator.next();

                if (usercacheentry != null) {
                    this.a(usercacheentry.a(), usercacheentry.b());
                }
            }
        }

    }

    public void c() {
        String s = this.b.toJson(this.a(org.spigotmc.SpigotConfig.userCacheCap));
        BufferedWriter bufferedwriter = null;

        try {
            bufferedwriter = Files.newWriter(this.g, Charsets.UTF_8);
            bufferedwriter.write(s);
            return;
        } catch (FileNotFoundException filenotfoundexception) {
            return;
        } catch (IOException ioexception) {
            ;
        } finally {
            IOUtils.closeQuietly(bufferedwriter);
        }

    }

    private List a(int i) {
        ArrayList arraylist = Lists.newArrayList();
        ArrayList arraylist1 = Lists.newArrayList(Iterators.limit(this.e.iterator(), i));
        Iterator iterator = arraylist1.iterator();

        while (iterator.hasNext()) {
            GameProfile gameprofile = (GameProfile) iterator.next();
            UserCacheEntry usercacheentry = this.b(gameprofile.getId());

            if (usercacheentry != null) {
                arraylist.add(usercacheentry);
            }
        }

        return arraylist;
    }

}
