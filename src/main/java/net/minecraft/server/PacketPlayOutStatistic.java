package net.minecraft.server;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class PacketPlayOutStatistic implements Packet<PacketListenerPlayOut> {

    private Map<Statistic, Integer> a;

    public PacketPlayOutStatistic() {}

    public PacketPlayOutStatistic(Map<Statistic, Integer> map) {
        this.a = map;
    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        int i = packetdataserializer.g();

        this.a = Maps.newHashMap();

        for (int j = 0; j < i; ++j) {
            Statistic statistic = StatisticList.getStatistic(packetdataserializer.e(32767));
            int k = packetdataserializer.g();

            if (statistic != null) {
                this.a.put(statistic, Integer.valueOf(k));
            }
        }

    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.d(this.a.size());
        Iterator iterator = this.a.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();

            packetdataserializer.a(((Statistic) entry.getKey()).name);
            packetdataserializer.d(((Integer) entry.getValue()).intValue());
        }

    }
}
