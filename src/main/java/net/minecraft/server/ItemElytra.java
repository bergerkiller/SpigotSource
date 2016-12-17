package net.minecraft.server;

public class ItemElytra extends Item {

    public ItemElytra() {
        this.maxStackSize = 1;
        this.setMaxDurability(432);
        this.a(CreativeModeTab.e);
        this.a(new MinecraftKey("broken"), new IDynamicTexture() {
        });
        BlockDispenser.REGISTRY.a(this, ItemArmor.b);
    }

    public static boolean d(ItemStack itemstack) {
        return itemstack.h() < itemstack.j() - 1;
    }

    public boolean a(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack1.getItem() == Items.LEATHER;
    }

    public InteractionResultWrapper<ItemStack> a(ItemStack itemstack, World world, EntityHuman entityhuman, EnumHand enumhand) {
        EnumItemSlot enumitemslot = EntityInsentient.d(itemstack);
        ItemStack itemstack1 = entityhuman.getEquipment(enumitemslot);

        if (itemstack1 == null) {
            entityhuman.setSlot(enumitemslot, itemstack.cloneItemStack());
            itemstack.count = 0;
            return new InteractionResultWrapper(EnumInteractionResult.SUCCESS, itemstack);
        } else {
            return new InteractionResultWrapper(EnumInteractionResult.FAIL, itemstack);
        }
    }
}
