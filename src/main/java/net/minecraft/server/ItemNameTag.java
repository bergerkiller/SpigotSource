package net.minecraft.server;

public class ItemNameTag extends Item {

    public ItemNameTag() {
        this.a(CreativeModeTab.i);
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, EntityLiving entityliving, EnumHand enumhand) {
        if (!itemstack.hasName()) {
            return false;
        } else if (entityliving instanceof EntityInsentient) {
            EntityInsentient entityinsentient = (EntityInsentient) entityliving;

            entityinsentient.setCustomName(itemstack.getName());
            entityinsentient.cM();
            --itemstack.count;
            return true;
        } else {
            return super.a(itemstack, entityhuman, entityliving, enumhand);
        }
    }
}
