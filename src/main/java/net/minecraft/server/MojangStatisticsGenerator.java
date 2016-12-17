package net.minecraft.server;

import com.google.common.collect.Maps;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MojangStatisticsGenerator {

    private final Map<String, Object> a = Maps.newHashMap();
    private final Map<String, Object> b = Maps.newHashMap();
    private final String c = UUID.randomUUID().toString();
    private final URL d;
    private final IMojangStatistics e;
    private final Timer f = new Timer("Snooper Timer", true);
    private final Object g = new Object();
    private final long h;
    private boolean i;
    private int j;

    public MojangStatisticsGenerator(String s, IMojangStatistics imojangstatistics, long i) {
        try {
            this.d = new URL("http://snoop.minecraft.net/" + s + "?version=" + 2);
        } catch (MalformedURLException malformedurlexception) {
            throw new IllegalArgumentException();
        }

        this.e = imojangstatistics;
        this.h = i;
    }

    public void a() {
        if (!this.i) {
            this.i = true;
            this.h();
            this.f.schedule(new TimerTask() {
                public void run() {
                    if (MojangStatisticsGenerator.this.e.getSnooperEnabled()) {
                        HashMap hashmap;

                        synchronized (MojangStatisticsGenerator.this.g) {
                            hashmap = Maps.newHashMap(MojangStatisticsGenerator.this.b);
                            if (MojangStatisticsGenerator.this.j == 0) {
                                hashmap.putAll(MojangStatisticsGenerator.this.a);
                            }

                            hashmap.put("snooper_count", Integer.valueOf(MojangStatisticsGenerator.f(MojangStatisticsGenerator.this)));
                            hashmap.put("snooper_token", MojangStatisticsGenerator.this.c);
                        }

                        MinecraftServer minecraftserver = MojangStatisticsGenerator.this.e instanceof MinecraftServer ? (MinecraftServer) MojangStatisticsGenerator.this.e : null;

                        HttpUtilities.a(MojangStatisticsGenerator.this.d, (Map) hashmap, true, minecraftserver == null ? null : minecraftserver.au());
                    }
                }
            }, 0L, 900000L);
        }
    }

    private void h() {
        this.i();
        this.a("snooper_token", this.c);
        this.b("snooper_token", this.c);
        this.b("os_name", System.getProperty("os.name"));
        this.b("os_version", System.getProperty("os.version"));
        this.b("os_architecture", System.getProperty("os.arch"));
        this.b("java_version", System.getProperty("java.version"));
        this.a("version", "1.9.4");
        this.e.b(this);
    }

    private void i() {
        RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
        List list = runtimemxbean.getInputArguments();
        int i = 0;
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();

            if (s.startsWith("-X")) {
                this.a("jvm_arg[" + i++ + "]", s);
            }
        }

        this.a("jvm_args", Integer.valueOf(i));
    }

    public void b() {
        this.b("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
        this.b("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
        this.b("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
        this.b("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
        this.e.a(this);
    }

    public void a(String s, Object object) {
        Object object1 = this.g;

        synchronized (this.g) {
            this.b.put(s, object);
        }
    }

    public void b(String s, Object object) {
        Object object1 = this.g;

        synchronized (this.g) {
            this.a.put(s, object);
        }
    }

    public boolean d() {
        return this.i;
    }

    public void e() {
        this.f.cancel();
    }

    public long g() {
        return this.h;
    }

    static int f(MojangStatisticsGenerator mojangstatisticsgenerator) {
        return mojangstatisticsgenerator.j++;
    }
}
