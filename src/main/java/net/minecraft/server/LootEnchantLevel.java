package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;

public class LootEnchantLevel extends LootItemFunction {

    private final LootValueBounds a;
    private final boolean b;

    public LootEnchantLevel(LootItemCondition[] alootitemcondition, LootValueBounds lootvaluebounds, boolean flag) {
        super(alootitemcondition);
        this.a = lootvaluebounds;
        this.b = flag;
    }

    public ItemStack a(ItemStack itemstack, Random random, LootTableInfo loottableinfo) {
        EnchantmentManager.a(random, itemstack, this.a.a(random), this.b);
        return itemstack;
    }

    public static class a extends LootItemFunction.a<LootEnchantLevel> {

        public a() {
            super(new MinecraftKey("enchant_with_levels"), LootEnchantLevel.class);
        }

        public void a(JsonObject jsonobject, LootEnchantLevel lootenchantlevel, JsonSerializationContext jsonserializationcontext) {
            jsonobject.add("levels", jsonserializationcontext.serialize(lootenchantlevel.a));
            jsonobject.addProperty("treasure", Boolean.valueOf(lootenchantlevel.b));
        }

        public LootEnchantLevel a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext, LootItemCondition[] alootitemcondition) {
            LootValueBounds lootvaluebounds = (LootValueBounds) ChatDeserializer.a(jsonobject, "levels", jsondeserializationcontext, LootValueBounds.class);
            boolean flag = ChatDeserializer.a(jsonobject, "treasure", false);

            return new LootEnchantLevel(alootitemcondition, lootvaluebounds, flag);
        }

        public LootItemFunction b(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext, LootItemCondition[] alootitemcondition) {
            return this.a(jsonobject, jsondeserializationcontext, alootitemcondition);
        }
    }
}
