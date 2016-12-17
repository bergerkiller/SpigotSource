package net.minecraft.server;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class PotionUtil {

    public static List<MobEffect> getEffects(ItemStack itemstack) {
        return a(itemstack.getTag());
    }

    public static List<MobEffect> a(PotionRegistry potionregistry, Collection<MobEffect> collection) {
        ArrayList arraylist = Lists.newArrayList();

        arraylist.addAll(potionregistry.a());
        arraylist.addAll(collection);
        return arraylist;
    }

    public static List<MobEffect> a(@Nullable NBTTagCompound nbttagcompound) {
        ArrayList arraylist = Lists.newArrayList();

        arraylist.addAll(c(nbttagcompound).a());
        a(nbttagcompound, (List) arraylist);
        return arraylist;
    }

    public static List<MobEffect> b(ItemStack itemstack) {
        return b(itemstack.getTag());
    }

    public static List<MobEffect> b(@Nullable NBTTagCompound nbttagcompound) {
        ArrayList arraylist = Lists.newArrayList();

        a(nbttagcompound, (List) arraylist);
        return arraylist;
    }

    public static void a(@Nullable NBTTagCompound nbttagcompound, List<MobEffect> list) {
        if (nbttagcompound != null && nbttagcompound.hasKeyOfType("CustomPotionEffects", 9)) {
            NBTTagList nbttaglist = nbttagcompound.getList("CustomPotionEffects", 10);

            for (int i = 0; i < nbttaglist.size(); ++i) {
                NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
                MobEffect mobeffect = MobEffect.b(nbttagcompound1);

                if (mobeffect != null) {
                    list.add(mobeffect);
                }
            }
        }

    }

    public static int a(Collection<MobEffect> collection) {
        int i = 3694022;

        if (collection.isEmpty()) {
            return 3694022;
        } else {
            float f = 0.0F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            int j = 0;
            Iterator iterator = collection.iterator();

            while (iterator.hasNext()) {
                MobEffect mobeffect = (MobEffect) iterator.next();

                if (mobeffect.isShowParticles()) {
                    int k = mobeffect.getMobEffect().getColor();
                    int l = mobeffect.getAmplifier() + 1;

                    f += (float) (l * (k >> 16 & 255)) / 255.0F;
                    f1 += (float) (l * (k >> 8 & 255)) / 255.0F;
                    f2 += (float) (l * (k >> 0 & 255)) / 255.0F;
                    j += l;
                }
            }

            if (j == 0) {
                return 0;
            } else {
                f = f / (float) j * 255.0F;
                f1 = f1 / (float) j * 255.0F;
                f2 = f2 / (float) j * 255.0F;
                return (int) f << 16 | (int) f1 << 8 | (int) f2;
            }
        }
    }

    public static PotionRegistry c(ItemStack itemstack) {
        return c(itemstack.getTag());
    }

    public static PotionRegistry c(@Nullable NBTTagCompound nbttagcompound) {
        return nbttagcompound == null ? Potions.b : PotionRegistry.a(nbttagcompound.getString("Potion"));
    }

    public static ItemStack a(ItemStack itemstack, PotionRegistry potionregistry) {
        MinecraftKey minecraftkey = (MinecraftKey) PotionRegistry.a.b(potionregistry);

        if (minecraftkey != null) {
            NBTTagCompound nbttagcompound = itemstack.hasTag() ? itemstack.getTag() : new NBTTagCompound();

            nbttagcompound.setString("Potion", minecraftkey.toString());
            itemstack.setTag(nbttagcompound);
        }

        return itemstack;
    }

    public static ItemStack a(ItemStack itemstack, Collection<MobEffect> collection) {
        if (collection.isEmpty()) {
            return itemstack;
        } else {
            NBTTagCompound nbttagcompound = (NBTTagCompound) Objects.firstNonNull(itemstack.getTag(), new NBTTagCompound());
            NBTTagList nbttaglist = nbttagcompound.getList("CustomPotionEffects", 9);
            Iterator iterator = collection.iterator();

            while (iterator.hasNext()) {
                MobEffect mobeffect = (MobEffect) iterator.next();

                nbttaglist.add(mobeffect.a(new NBTTagCompound()));
            }

            nbttagcompound.set("CustomPotionEffects", nbttaglist);
            itemstack.setTag(nbttagcompound);
            return itemstack;
        }
    }
}
