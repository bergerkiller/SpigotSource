package net.minecraft.server;

public enum EnchantmentSlotType {

    ALL, ARMOR, ARMOR_FEET, ARMOR_LEGS, ARMOR_CHEST, ARMOR_HEAD, WEAPON, DIGGER, FISHING_ROD, BREAKABLE, BOW;

    private EnchantmentSlotType() {}

    public boolean canEnchant(Item item) {
        if (this == EnchantmentSlotType.ALL) {
            return true;
        } else if (this == EnchantmentSlotType.BREAKABLE && item.usesDurability()) {
            return true;
        } else if (item instanceof ItemArmor) {
            if (this == EnchantmentSlotType.ARMOR) {
                return true;
            } else {
                ItemArmor itemarmor = (ItemArmor) item;

                return itemarmor.c == EnumItemSlot.HEAD ? this == EnchantmentSlotType.ARMOR_HEAD : (itemarmor.c == EnumItemSlot.LEGS ? this == EnchantmentSlotType.ARMOR_LEGS : (itemarmor.c == EnumItemSlot.CHEST ? this == EnchantmentSlotType.ARMOR_CHEST : (itemarmor.c == EnumItemSlot.FEET ? this == EnchantmentSlotType.ARMOR_FEET : false)));
            }
        } else {
            return item instanceof ItemSword ? this == EnchantmentSlotType.WEAPON : (item instanceof ItemTool ? this == EnchantmentSlotType.DIGGER : (item instanceof ItemBow ? this == EnchantmentSlotType.BOW : (item instanceof ItemFishingRod ? this == EnchantmentSlotType.FISHING_ROD : false)));
        }
    }
}
