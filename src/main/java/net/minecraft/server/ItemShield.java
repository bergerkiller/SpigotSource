package net.minecraft.server;

public class ItemShield extends Item {

    public ItemShield() {
        this.maxStackSize = 1;
        this.a(CreativeModeTab.j);
        this.setMaxDurability(336);
        this.a(new MinecraftKey("blocking"), new IDynamicTexture() {
        });
    }

    public EnumInteractionResult a(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumHand enumhand, EnumDirection enumdirection, float f, float f1, float f2) {
        return super.a(itemstack, entityhuman, world, blockposition, enumhand, enumdirection, f, f1, f2);
    }

    public String a(ItemStack itemstack) {
        if (itemstack.a("BlockEntityTag", false) != null) {
            String s = "item.shield.";
            EnumColor enumcolor = ItemBanner.b(itemstack);

            s = s + enumcolor.d() + ".name";
            return LocaleI18n.get(s);
        } else {
            return LocaleI18n.get("item.shield.name");
        }
    }

    public EnumAnimation f(ItemStack itemstack) {
        return EnumAnimation.BLOCK;
    }

    public int e(ItemStack itemstack) {
        return 72000;
    }

    public InteractionResultWrapper<ItemStack> a(ItemStack itemstack, World world, EntityHuman entityhuman, EnumHand enumhand) {
        entityhuman.c(enumhand);
        return new InteractionResultWrapper(EnumInteractionResult.SUCCESS, itemstack);
    }

    public boolean a(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack1.getItem() == Item.getItemOf(Blocks.PLANKS) ? true : super.a(itemstack, itemstack1);
    }
}
