package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class EnchantmentManager {

    private static final Random a = new Random();
    private static final EnchantmentManager.EnchantmentModifierProtection b = new EnchantmentManager.EnchantmentModifierProtection((EnchantmentManager.SyntheticClass_1) null);
    private static final EnchantmentManager.EnchantmentModifierDamage c = new EnchantmentManager.EnchantmentModifierDamage((EnchantmentManager.SyntheticClass_1) null);
    private static final EnchantmentManager.EnchantmentModifierThorns d = new EnchantmentManager.EnchantmentModifierThorns((EnchantmentManager.SyntheticClass_1) null);
    private static final EnchantmentManager.EnchantmentModifierArthropods e = new EnchantmentManager.EnchantmentModifierArthropods((EnchantmentManager.SyntheticClass_1) null);

    public static int getEnchantmentLevel(Enchantment enchantment, @Nullable ItemStack itemstack) {
        if (itemstack == null) {
            return 0;
        } else {
            NBTTagList nbttaglist = itemstack.getEnchantments();

            if (nbttaglist == null) {
                return 0;
            } else {
                for (int i = 0; i < nbttaglist.size(); ++i) {
                    Enchantment enchantment1 = Enchantment.c(nbttaglist.get(i).getShort("id"));
                    short short0 = nbttaglist.get(i).getShort("lvl");

                    if (enchantment1 == enchantment) {
                        return short0;
                    }
                }

                return 0;
            }
        }
    }

    public static Map<Enchantment, Integer> a(ItemStack itemstack) {
        LinkedHashMap linkedhashmap = Maps.newLinkedHashMap();
        NBTTagList nbttaglist = itemstack.getItem() == Items.ENCHANTED_BOOK ? Items.ENCHANTED_BOOK.h(itemstack) : itemstack.getEnchantments();

        if (nbttaglist != null) {
            for (int i = 0; i < nbttaglist.size(); ++i) {
                Enchantment enchantment = Enchantment.c(nbttaglist.get(i).getShort("id"));
                short short0 = nbttaglist.get(i).getShort("lvl");

                linkedhashmap.put(enchantment, Integer.valueOf(short0));
            }
        }

        return linkedhashmap;
    }

    public static void a(Map<Enchantment, Integer> map, ItemStack itemstack) {
        NBTTagList nbttaglist = new NBTTagList();
        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            Enchantment enchantment = (Enchantment) entry.getKey();

            if (enchantment != null) {
                int i = ((Integer) entry.getValue()).intValue();
                NBTTagCompound nbttagcompound = new NBTTagCompound();

                nbttagcompound.setShort("id", (short) Enchantment.getId(enchantment));
                nbttagcompound.setShort("lvl", (short) i);
                nbttaglist.add(nbttagcompound);
                if (itemstack.getItem() == Items.ENCHANTED_BOOK) {
                    Items.ENCHANTED_BOOK.a(itemstack, new WeightedRandomEnchant(enchantment, i));
                }
            }
        }

        if (nbttaglist.isEmpty()) {
            if (itemstack.hasTag()) {
                itemstack.getTag().remove("ench");
            }
        } else if (itemstack.getItem() != Items.ENCHANTED_BOOK) {
            itemstack.a("ench", (NBTBase) nbttaglist);
        }

    }

    private static void a(EnchantmentManager.EnchantmentModifier enchantmentmanager_enchantmentmodifier, ItemStack itemstack) {
        if (itemstack != null) {
            NBTTagList nbttaglist = itemstack.getEnchantments();

            if (nbttaglist != null) {
                for (int i = 0; i < nbttaglist.size(); ++i) {
                    short short0 = nbttaglist.get(i).getShort("id");
                    short short1 = nbttaglist.get(i).getShort("lvl");

                    if (Enchantment.c(short0) != null) {
                        enchantmentmanager_enchantmentmodifier.a(Enchantment.c(short0), short1);
                    }
                }

            }
        }
    }

    private static void a(EnchantmentManager.EnchantmentModifier enchantmentmanager_enchantmentmodifier, Iterable<ItemStack> iterable) {
        Iterator iterator = iterable.iterator();

        while (iterator.hasNext()) {
            ItemStack itemstack = (ItemStack) iterator.next();

            a(enchantmentmanager_enchantmentmodifier, itemstack);
        }

    }

    public static int a(Iterable<ItemStack> iterable, DamageSource damagesource) {
        EnchantmentManager.b.a = 0;
        EnchantmentManager.b.b = damagesource;
        a((EnchantmentManager.EnchantmentModifier) EnchantmentManager.b, iterable);
        return EnchantmentManager.b.a;
    }

    public static float a(ItemStack itemstack, EnumMonsterType enummonstertype) {
        EnchantmentManager.c.a = 0.0F;
        EnchantmentManager.c.b = enummonstertype;
        a((EnchantmentManager.EnchantmentModifier) EnchantmentManager.c, itemstack);
        return EnchantmentManager.c.a;
    }

    public static void a(EntityLiving entityliving, Entity entity) {
        EnchantmentManager.d.b = entity;
        EnchantmentManager.d.a = entityliving;
        if (entityliving != null) {
            a((EnchantmentManager.EnchantmentModifier) EnchantmentManager.d, entityliving.aG());
        }

        if (entity instanceof EntityHuman) {
            a((EnchantmentManager.EnchantmentModifier) EnchantmentManager.d, entityliving.getItemInMainHand());
        }

    }

    public static void b(EntityLiving entityliving, Entity entity) {
        EnchantmentManager.e.a = entityliving;
        EnchantmentManager.e.b = entity;
        if (entityliving != null) {
            a((EnchantmentManager.EnchantmentModifier) EnchantmentManager.e, entityliving.aG());
        }

        if (entityliving instanceof EntityHuman) {
            a((EnchantmentManager.EnchantmentModifier) EnchantmentManager.e, entityliving.getItemInMainHand());
        }

    }

    public static int a(Enchantment enchantment, EntityLiving entityliving) {
        Iterable iterable = enchantment.a(entityliving);

        if (iterable == null) {
            return 0;
        } else {
            int i = 0;
            Iterator iterator = iterable.iterator();

            while (iterator.hasNext()) {
                ItemStack itemstack = (ItemStack) iterator.next();
                int j = getEnchantmentLevel(enchantment, itemstack);

                if (j > i) {
                    i = j;
                }
            }

            return i;
        }
    }

    public static int a(EntityLiving entityliving) {
        return a(Enchantments.KNOCKBACK, entityliving);
    }

    public static int getFireAspectEnchantmentLevel(EntityLiving entityliving) {
        return a(Enchantments.FIRE_ASPECT, entityliving);
    }

    public static int getOxygenEnchantmentLevel(EntityLiving entityliving) {
        return a(Enchantments.OXYGEN, entityliving);
    }

    public static int d(EntityLiving entityliving) {
        return a(Enchantments.DEPTH_STRIDER, entityliving);
    }

    public static int getDigSpeedEnchantmentLevel(EntityLiving entityliving) {
        return a(Enchantments.DIG_SPEED, entityliving);
    }

    public static int f(EntityLiving entityliving) {
        return a(Enchantments.LUCK, entityliving);
    }

    public static int g(EntityLiving entityliving) {
        return a(Enchantments.LURE, entityliving);
    }

    public static int h(EntityLiving entityliving) {
        return a(Enchantments.LOOT_BONUS_MOBS, entityliving);
    }

    public static boolean i(EntityLiving entityliving) {
        return a(Enchantments.WATER_WORKER, entityliving) > 0;
    }

    @Nullable
    public static ItemStack b(Enchantment enchantment, EntityLiving entityliving) {
        Iterable iterable = enchantment.a(entityliving);

        if (iterable == null) {
            return null;
        } else {
            ArrayList arraylist = Lists.newArrayList();
            Iterator iterator = iterable.iterator();

            while (iterator.hasNext()) {
                ItemStack itemstack = (ItemStack) iterator.next();

                if (itemstack != null && getEnchantmentLevel(enchantment, itemstack) > 0) {
                    arraylist.add(itemstack);
                }
            }

            return arraylist.isEmpty() ? null : (ItemStack) arraylist.get(entityliving.getRandom().nextInt(arraylist.size()));
        }
    }

    public static int a(Random random, int i, int j, ItemStack itemstack) {
        Item item = itemstack.getItem();
        int k = item.c();

        if (k <= 0) {
            return 0;
        } else {
            if (j > 15) {
                j = 15;
            }

            int l = random.nextInt(8) + 1 + (j >> 1) + random.nextInt(j + 1);

            return i == 0 ? Math.max(l / 3, 1) : (i == 1 ? l * 2 / 3 + 1 : Math.max(l, j * 2));
        }
    }

    public static ItemStack a(Random random, ItemStack itemstack, int i, boolean flag) {
        boolean flag1 = itemstack.getItem() == Items.BOOK;
        List list = b(random, itemstack, i, flag);

        if (flag1) {
            itemstack.setItem(Items.ENCHANTED_BOOK);
        }

        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            WeightedRandomEnchant weightedrandomenchant = (WeightedRandomEnchant) iterator.next();

            if (flag1) {
                Items.ENCHANTED_BOOK.a(itemstack, weightedrandomenchant);
            } else {
                itemstack.addEnchantment(weightedrandomenchant.enchantment, weightedrandomenchant.level);
            }
        }

        return itemstack;
    }

    public static List<WeightedRandomEnchant> b(Random random, ItemStack itemstack, int i, boolean flag) {
        ArrayList arraylist = Lists.newArrayList();
        Item item = itemstack.getItem();
        int j = item.c();

        if (j <= 0) {
            return arraylist;
        } else {
            i += 1 + random.nextInt(j / 4 + 1) + random.nextInt(j / 4 + 1);
            float f = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;

            i = MathHelper.clamp(Math.round((float) i + (float) i * f), 1, Integer.MAX_VALUE);
            List list = a(i, itemstack, flag);

            if (!list.isEmpty()) {
                arraylist.add(WeightedRandom.a(random, list));

                while (random.nextInt(50) <= i) {
                    a(list, (WeightedRandomEnchant) SystemUtils.a(arraylist));
                    if (list.isEmpty()) {
                        break;
                    }

                    arraylist.add(WeightedRandom.a(random, list));
                    i /= 2;
                }
            }

            return arraylist;
        }
    }

    public static void a(List<WeightedRandomEnchant> list, WeightedRandomEnchant weightedrandomenchant) {
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            if (!weightedrandomenchant.enchantment.a(((WeightedRandomEnchant) iterator.next()).enchantment)) {
                iterator.remove();
            }
        }

    }

    public static List<WeightedRandomEnchant> a(int i, ItemStack itemstack, boolean flag) {
        ArrayList arraylist = Lists.newArrayList();
        Item item = itemstack.getItem();
        boolean flag1 = itemstack.getItem() == Items.BOOK;
        Iterator iterator = Enchantment.enchantments.iterator();

        while (iterator.hasNext()) {
            Enchantment enchantment = (Enchantment) iterator.next();

            if ((!enchantment.e() || flag) && (enchantment.itemTarget.canEnchant(item) || flag1)) {
                for (int j = enchantment.getMaxLevel(); j > enchantment.getStartLevel() - 1; --j) {
                    if (i >= enchantment.a(j) && i <= enchantment.b(j)) {
                        arraylist.add(new WeightedRandomEnchant(enchantment, j));
                        break;
                    }
                }
            }
        }

        return arraylist;
    }

    static class SyntheticClass_1 {    }

    static final class EnchantmentModifierArthropods implements EnchantmentManager.EnchantmentModifier {

        public EntityLiving a;
        public Entity b;

        private EnchantmentModifierArthropods() {}

        public void a(Enchantment enchantment, int i) {
            enchantment.a(this.a, this.b, i);
        }

        EnchantmentModifierArthropods(EnchantmentManager.SyntheticClass_1 enchantmentmanager_syntheticclass_1) {
            this();
        }
    }

    static final class EnchantmentModifierThorns implements EnchantmentManager.EnchantmentModifier {

        public EntityLiving a;
        public Entity b;

        private EnchantmentModifierThorns() {}

        public void a(Enchantment enchantment, int i) {
            enchantment.b(this.a, this.b, i);
        }

        EnchantmentModifierThorns(EnchantmentManager.SyntheticClass_1 enchantmentmanager_syntheticclass_1) {
            this();
        }
    }

    static final class EnchantmentModifierDamage implements EnchantmentManager.EnchantmentModifier {

        public float a;
        public EnumMonsterType b;

        private EnchantmentModifierDamage() {}

        public void a(Enchantment enchantment, int i) {
            this.a += enchantment.a(i, this.b);
        }

        EnchantmentModifierDamage(EnchantmentManager.SyntheticClass_1 enchantmentmanager_syntheticclass_1) {
            this();
        }
    }

    static final class EnchantmentModifierProtection implements EnchantmentManager.EnchantmentModifier {

        public int a;
        public DamageSource b;

        private EnchantmentModifierProtection() {}

        public void a(Enchantment enchantment, int i) {
            this.a += enchantment.a(i, this.b);
        }

        EnchantmentModifierProtection(EnchantmentManager.SyntheticClass_1 enchantmentmanager_syntheticclass_1) {
            this();
        }
    }

    interface EnchantmentModifier {

        void a(Enchantment enchantment, int i);
    }
}
