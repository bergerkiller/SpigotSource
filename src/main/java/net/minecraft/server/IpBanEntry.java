package net.minecraft.server;

import com.google.gson.JsonObject;
import java.util.Date;

public class IpBanEntry extends ExpirableListEntry<String> {

    public IpBanEntry(String s) {
        this(s, (Date) null, (String) null, (Date) null, (String) null);
    }

    public IpBanEntry(String s, Date date, String s1, Date date1, String s2) {
        super(s, date, s1, date1, s2);
    }

    public IpBanEntry(JsonObject jsonobject) {
        super(b(jsonobject), jsonobject);
    }

    private static String b(JsonObject jsonobject) {
        return jsonobject.has("ip") ? jsonobject.get("ip").getAsString() : null;
    }

    protected void a(JsonObject jsonobject) {
        if (this.getKey() != null) {
            jsonobject.addProperty("ip", (String) this.getKey());
            super.a(jsonobject);
        }
    }
}
