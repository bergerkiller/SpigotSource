package net.minecraft.server;

public class EnchantmentInfiniteArrows extends Enchantment {

    public EnchantmentInfiniteArrows(Enchantment.Rarity enchantment_rarity, EnumItemSlot... aenumitemslot) {
        super(enchantment_rarity, EnchantmentSlotType.BOW, aenumitemslot);
        this.c("arrowInfinite");
    }

    public int a(int i) {
        return 20;
    }

    public int b(int i) {
        return 50;
    }

    public int getMaxLevel() {
        return 1;
    }
}
