package net.minecraft.server;

public class ItemCoal extends Item {

    public ItemCoal() {
        this.a(true);
        this.setMaxDurability(0);
        this.a(CreativeModeTab.l);
    }

    public String f_(ItemStack itemstack) {
        return itemstack.getData() == 1 ? "item.charcoal" : "item.coal";
    }
}
