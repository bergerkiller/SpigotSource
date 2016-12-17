package net.minecraft.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Collection;
import java.util.Random;

public class LootItem extends LotoSelectorEntry {

    protected final Item a;
    protected final LootItemFunction[] b;

    public LootItem(Item item, int i, int j, LootItemFunction[] alootitemfunction, LootItemCondition[] alootitemcondition) {
        super(i, j, alootitemcondition);
        this.a = item;
        this.b = alootitemfunction;
    }

    public void a(Collection<ItemStack> collection, Random random, LootTableInfo loottableinfo) {
        ItemStack itemstack = new ItemStack(this.a);
        int i = 0;

        for (int j = this.b.length; i < j; ++i) {
            LootItemFunction lootitemfunction = this.b[i];

            if (LootItemConditions.a(lootitemfunction.a(), random, loottableinfo)) {
                itemstack = lootitemfunction.a(itemstack, random, loottableinfo);
            }
        }

        if (itemstack.count > 0) {
            if (itemstack.count < this.a.getMaxStackSize()) {
                collection.add(itemstack);
            } else {
                i = itemstack.count;

                while (i > 0) {
                    ItemStack itemstack1 = itemstack.cloneItemStack();

                    itemstack1.count = Math.min(itemstack.getMaxStackSize(), i);
                    i -= itemstack1.count;
                    collection.add(itemstack1);
                }
            }
        }

    }

    protected void a(JsonObject jsonobject, JsonSerializationContext jsonserializationcontext) {
        if (this.b != null && this.b.length > 0) {
            jsonobject.add("functions", jsonserializationcontext.serialize(this.b));
        }

        MinecraftKey minecraftkey = (MinecraftKey) Item.REGISTRY.b(this.a);

        if (minecraftkey == null) {
            throw new IllegalArgumentException("Can\'t serialize unknown item " + this.a);
        } else {
            jsonobject.addProperty("name", minecraftkey.toString());
        }
    }

    public static LootItem a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext, int i, int j, LootItemCondition[] alootitemcondition) {
        Item item = ChatDeserializer.i(jsonobject, "name");
        LootItemFunction[] alootitemfunction;

        if (jsonobject.has("functions")) {
            alootitemfunction = (LootItemFunction[]) ChatDeserializer.a(jsonobject, "functions", jsondeserializationcontext, LootItemFunction[].class);
        } else {
            alootitemfunction = new LootItemFunction[0];
        }

        return new LootItem(item, i, j, alootitemfunction, alootitemcondition);
    }
}
