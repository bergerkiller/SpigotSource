package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Map;

public class ChatClickable {

    private final ChatClickable.EnumClickAction a;
    private final String b;

    public ChatClickable(ChatClickable.EnumClickAction chatclickable_enumclickaction, String s) {
        this.a = chatclickable_enumclickaction;
        this.b = s;
    }

    public ChatClickable.EnumClickAction a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object != null && this.getClass() == object.getClass()) {
            ChatClickable chatclickable = (ChatClickable) object;

            if (this.a != chatclickable.a) {
                return false;
            } else {
                if (this.b != null) {
                    if (!this.b.equals(chatclickable.b)) {
                        return false;
                    }
                } else if (chatclickable.b != null) {
                    return false;
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public String toString() {
        return "ClickEvent{action=" + this.a + ", value=\'" + this.b + '\'' + '}';
    }

    public int hashCode() {
        int i = this.a.hashCode();

        i = 31 * i + (this.b != null ? this.b.hashCode() : 0);
        return i;
    }

    public static enum EnumClickAction {

        OPEN_URL("open_url", true), OPEN_FILE("open_file", false), RUN_COMMAND("run_command", true), SUGGEST_COMMAND("suggest_command", true), CHANGE_PAGE("change_page", true);

        private static final Map<String, ChatClickable.EnumClickAction> f = Maps.newHashMap();
        private final boolean g;
        private final String h;

        private EnumClickAction(String s, boolean flag) {
            this.h = s;
            this.g = flag;
        }

        public boolean a() {
            return this.g;
        }

        public String b() {
            return this.h;
        }

        public static ChatClickable.EnumClickAction a(String s) {
            return (ChatClickable.EnumClickAction) ChatClickable.EnumClickAction.f.get(s);
        }

        static {
            ChatClickable.EnumClickAction[] achatclickable_enumclickaction = values();
            int i = achatclickable_enumclickaction.length;

            for (int j = 0; j < i; ++j) {
                ChatClickable.EnumClickAction chatclickable_enumclickaction = achatclickable_enumclickaction[j];

                ChatClickable.EnumClickAction.f.put(chatclickable_enumclickaction.b(), chatclickable_enumclickaction);
            }

        }
    }
}
