package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;

public class LootEnchantFunction extends LootItemFunction {

    private final LootValueBounds a;

    public LootEnchantFunction(LootItemCondition[] alootitemcondition, LootValueBounds lootvaluebounds) {
        super(alootitemcondition);
        this.a = lootvaluebounds;
    }

    public ItemStack a(ItemStack itemstack, Random random, LootTableInfo loottableinfo) {
        Entity entity = loottableinfo.c();

        if (entity instanceof EntityLiving) {
            int i = EnchantmentManager.h((EntityLiving) entity);

            if (i == 0) {
                return itemstack;
            }

            float f = (float) i * this.a.b(random);

            itemstack.count += Math.round(f);
        }

        return itemstack;
    }

    public static class a extends LootItemFunction.a<LootEnchantFunction> {

        protected a() {
            super(new MinecraftKey("looting_enchant"), LootEnchantFunction.class);
        }

        public void a(JsonObject jsonobject, LootEnchantFunction lootenchantfunction, JsonSerializationContext jsonserializationcontext) {
            jsonobject.add("count", jsonserializationcontext.serialize(lootenchantfunction.a));
        }

        public LootEnchantFunction a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext, LootItemCondition[] alootitemcondition) {
            return new LootEnchantFunction(alootitemcondition, (LootValueBounds) ChatDeserializer.a(jsonobject, "count", jsondeserializationcontext, LootValueBounds.class));
        }

        public LootItemFunction b(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext, LootItemCondition[] alootitemcondition) {
            return this.a(jsonobject, jsondeserializationcontext, alootitemcondition);
        }
    }
}
