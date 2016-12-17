package net.minecraft.server;

public class ItemTippedArrow extends ItemArrow {

    public ItemTippedArrow() {}

    public EntityArrow a(World world, ItemStack itemstack, EntityLiving entityliving) {
        EntityTippedArrow entitytippedarrow = new EntityTippedArrow(world, entityliving);

        entitytippedarrow.a(itemstack);
        return entitytippedarrow;
    }

    public String a(ItemStack itemstack) {
        return LocaleI18n.get(PotionUtil.c(itemstack).b("tipped_arrow.effect."));
    }
}
