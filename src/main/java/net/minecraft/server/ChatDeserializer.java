package net.minecraft.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class ChatDeserializer {

    public static boolean a(JsonObject jsonobject, String s) {
        return !f(jsonobject, s) ? false : jsonobject.getAsJsonPrimitive(s).isString();
    }

    public static boolean b(JsonElement jsonelement) {
        return !jsonelement.isJsonPrimitive() ? false : jsonelement.getAsJsonPrimitive().isNumber();
    }

    public static boolean d(JsonObject jsonobject, String s) {
        return !g(jsonobject, s) ? false : jsonobject.get(s).isJsonArray();
    }

    public static boolean f(JsonObject jsonobject, String s) {
        return !g(jsonobject, s) ? false : jsonobject.get(s).isJsonPrimitive();
    }

    public static boolean g(JsonObject jsonobject, String s) {
        return jsonobject == null ? false : jsonobject.get(s) != null;
    }

    public static String a(JsonElement jsonelement, String s) {
        if (jsonelement.isJsonPrimitive()) {
            return jsonelement.getAsString();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a string, was " + d(jsonelement));
        }
    }

    public static String h(JsonObject jsonobject, String s) {
        if (jsonobject.has(s)) {
            return a(jsonobject.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a string");
        }
    }

    public static Item b(JsonElement jsonelement, String s) {
        if (jsonelement.isJsonPrimitive()) {
            String s1 = jsonelement.getAsString();
            Item item = Item.d(s1);

            if (item == null) {
                throw new JsonSyntaxException("Expected " + s + " to be an item, was unknown string \'" + s1 + "\'");
            } else {
                return item;
            }
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be an item, was " + d(jsonelement));
        }
    }

    public static Item i(JsonObject jsonobject, String s) {
        if (jsonobject.has(s)) {
            return b(jsonobject.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find an item");
        }
    }

    public static boolean c(JsonElement jsonelement, String s) {
        if (jsonelement.isJsonPrimitive()) {
            return jsonelement.getAsBoolean();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a Boolean, was " + d(jsonelement));
        }
    }

    public static boolean a(JsonObject jsonobject, String s, boolean flag) {
        return jsonobject.has(s) ? c(jsonobject.get(s), s) : flag;
    }

    public static float e(JsonElement jsonelement, String s) {
        if (jsonelement.isJsonPrimitive() && jsonelement.getAsJsonPrimitive().isNumber()) {
            return jsonelement.getAsFloat();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a Float, was " + d(jsonelement));
        }
    }

    public static float l(JsonObject jsonobject, String s) {
        if (jsonobject.has(s)) {
            return e(jsonobject.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a Float");
        }
    }

    public static float a(JsonObject jsonobject, String s, float f) {
        return jsonobject.has(s) ? e(jsonobject.get(s), s) : f;
    }

    public static int g(JsonElement jsonelement, String s) {
        if (jsonelement.isJsonPrimitive() && jsonelement.getAsJsonPrimitive().isNumber()) {
            return jsonelement.getAsInt();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a Int, was " + d(jsonelement));
        }
    }

    public static int n(JsonObject jsonobject, String s) {
        if (jsonobject.has(s)) {
            return g(jsonobject.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a Int");
        }
    }

    public static int a(JsonObject jsonobject, String s, int i) {
        return jsonobject.has(s) ? g(jsonobject.get(s), s) : i;
    }

    public static JsonObject m(JsonElement jsonelement, String s) {
        if (jsonelement.isJsonObject()) {
            return jsonelement.getAsJsonObject();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a JsonObject, was " + d(jsonelement));
        }
    }

    public static JsonObject t(JsonObject jsonobject, String s) {
        if (jsonobject.has(s)) {
            return m(jsonobject.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a JsonObject");
        }
    }

    public static JsonArray n(JsonElement jsonelement, String s) {
        if (jsonelement.isJsonArray()) {
            return jsonelement.getAsJsonArray();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a JsonArray, was " + d(jsonelement));
        }
    }

    public static JsonArray u(JsonObject jsonobject, String s) {
        if (jsonobject.has(s)) {
            return n(jsonobject.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a JsonArray");
        }
    }

    public static <T> T a(@Nullable JsonElement jsonelement, String s, JsonDeserializationContext jsondeserializationcontext, Class<? extends T> oclass) {
        if (jsonelement != null) {
            return jsondeserializationcontext.deserialize(jsonelement, oclass);
        } else {
            throw new JsonSyntaxException("Missing " + s);
        }
    }

    public static <T> T a(JsonObject jsonobject, String s, JsonDeserializationContext jsondeserializationcontext, Class<? extends T> oclass) {
        if (jsonobject.has(s)) {
            return a(jsonobject.get(s), s, jsondeserializationcontext, oclass);
        } else {
            throw new JsonSyntaxException("Missing " + s);
        }
    }

    public static <T> T a(JsonObject jsonobject, String s, T t0, JsonDeserializationContext jsondeserializationcontext, Class<? extends T> oclass) {
        return jsonobject.has(s) ? a(jsonobject.get(s), s, jsondeserializationcontext, oclass) : t0;
    }

    public static String d(JsonElement jsonelement) {
        String s = StringUtils.abbreviateMiddle(String.valueOf(jsonelement), "...", 10);

        if (jsonelement == null) {
            return "null (missing)";
        } else if (jsonelement.isJsonNull()) {
            return "null (json)";
        } else if (jsonelement.isJsonArray()) {
            return "an array (" + s + ")";
        } else if (jsonelement.isJsonObject()) {
            return "an object (" + s + ")";
        } else {
            if (jsonelement.isJsonPrimitive()) {
                JsonPrimitive jsonprimitive = jsonelement.getAsJsonPrimitive();

                if (jsonprimitive.isNumber()) {
                    return "a number (" + s + ")";
                }

                if (jsonprimitive.isBoolean()) {
                    return "a boolean (" + s + ")";
                }
            }

            return s;
        }
    }

    public static <T> T a(Gson gson, Reader reader, Class<T> oclass, boolean flag) {
        try {
            JsonReader jsonreader = new JsonReader(reader);

            jsonreader.setLenient(flag);
            return gson.getAdapter(oclass).read(jsonreader);
        } catch (IOException ioexception) {
            throw new JsonParseException(ioexception);
        }
    }

    public static <T> T a(Gson gson, String s, Class<T> oclass) {
        return a(gson, s, oclass, false);
    }

    public static <T> T a(Gson gson, String s, Class<T> oclass, boolean flag) {
        return a(gson, (Reader) (new StringReader(s)), oclass, flag);
    }
}
