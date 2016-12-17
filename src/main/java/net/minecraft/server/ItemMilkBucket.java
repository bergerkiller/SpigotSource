package net.minecraft.server;

import javax.annotation.Nullable;

public class ItemMilkBucket extends Item {

    public ItemMilkBucket() {
        this.d(1);
        this.a(CreativeModeTab.f);
    }

    @Nullable
    public ItemStack a(ItemStack itemstack, World world, EntityLiving entityliving) {
        if (entityliving instanceof EntityHuman && !((EntityHuman) entityliving).abilities.canInstantlyBuild) {
            --itemstack.count;
        }

        if (!world.isClientSide) {
            entityliving.removeAllEffects();
        }

        if (entityliving instanceof EntityHuman) {
            ((EntityHuman) entityliving).b(StatisticList.b((Item) this));
        }

        return itemstack.count <= 0 ? new ItemStack(Items.BUCKET) : itemstack;
    }

    public int e(ItemStack itemstack) {
        return 32;
    }

    public EnumAnimation f(ItemStack itemstack) {
        return EnumAnimation.DRINK;
    }

    public InteractionResultWrapper<ItemStack> a(ItemStack itemstack, World world, EntityHuman entityhuman, EnumHand enumhand) {
        entityhuman.c(enumhand);
        return new InteractionResultWrapper(EnumInteractionResult.SUCCESS, itemstack);
    }
}
