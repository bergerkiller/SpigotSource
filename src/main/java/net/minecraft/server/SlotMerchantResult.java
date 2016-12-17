package net.minecraft.server;

import javax.annotation.Nullable;

public class SlotMerchantResult extends Slot {

    private final InventoryMerchant a;
    private EntityHuman b;
    private int c;
    private final IMerchant h;

    public SlotMerchantResult(EntityHuman entityhuman, IMerchant imerchant, InventoryMerchant inventorymerchant, int i, int j, int k) {
        super(inventorymerchant, i, j, k);
        this.b = entityhuman;
        this.h = imerchant;
        this.a = inventorymerchant;
    }

    public boolean isAllowed(@Nullable ItemStack itemstack) {
        return false;
    }

    public ItemStack a(int i) {
        if (this.hasItem()) {
            this.c += Math.min(i, this.getItem().count);
        }

        return super.a(i);
    }

    protected void a(ItemStack itemstack, int i) {
        this.c += i;
        this.c(itemstack);
    }

    protected void c(ItemStack itemstack) {
        itemstack.a(this.b.world, this.b, this.c);
        this.c = 0;
    }

    public void a(EntityHuman entityhuman, ItemStack itemstack) {
        this.c(itemstack);
        MerchantRecipe merchantrecipe = this.a.getRecipe();

        if (merchantrecipe != null) {
            ItemStack itemstack1 = this.a.getItem(0);
            ItemStack itemstack2 = this.a.getItem(1);

            if (this.a(merchantrecipe, itemstack1, itemstack2) || this.a(merchantrecipe, itemstack2, itemstack1)) {
                this.h.a(merchantrecipe);
                entityhuman.b(StatisticList.I);
                if (itemstack1 != null && itemstack1.count <= 0) {
                    itemstack1 = null;
                }

                if (itemstack2 != null && itemstack2.count <= 0) {
                    itemstack2 = null;
                }

                this.a.setItem(0, itemstack1);
                this.a.setItem(1, itemstack2);
            }
        }

    }

    private boolean a(MerchantRecipe merchantrecipe, ItemStack itemstack, ItemStack itemstack1) {
        ItemStack itemstack2 = merchantrecipe.getBuyItem1();
        ItemStack itemstack3 = merchantrecipe.getBuyItem2();

        if (itemstack != null && itemstack.getItem() == itemstack2.getItem() && itemstack.count >= itemstack2.count) {
            if (itemstack3 != null && itemstack1 != null && itemstack3.getItem() == itemstack1.getItem() && itemstack1.count >= itemstack3.count) {
                itemstack.count -= itemstack2.count;
                itemstack1.count -= itemstack3.count;
                return true;
            }

            if (itemstack3 == null && itemstack1 == null) {
                itemstack.count -= itemstack2.count;
                return true;
            }
        }

        return false;
    }
}
