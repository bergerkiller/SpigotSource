package net.minecraft.server;

import javax.annotation.Nullable;

public class ContainerUtil {

    @Nullable
    public static ItemStack a(ItemStack[] aitemstack, int i, int j) {
        if (i >= 0 && i < aitemstack.length && aitemstack[i] != null && j > 0) {
            ItemStack itemstack = aitemstack[i].cloneAndSubtract(j);

            if (aitemstack[i].count == 0) {
                aitemstack[i] = null;
            }

            return itemstack;
        } else {
            return null;
        }
    }

    @Nullable
    public static ItemStack a(ItemStack[] aitemstack, int i) {
        if (i >= 0 && i < aitemstack.length) {
            ItemStack itemstack = aitemstack[i];

            aitemstack[i] = null;
            return itemstack;
        } else {
            return null;
        }
    }
}
