package net.minecraft.server;

public interface IDispenseBehavior {

    IDispenseBehavior NONE = new IDispenseBehavior() {
        public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
            return itemstack;
        }
    };

    ItemStack a(ISourceBlock isourceblock, ItemStack itemstack);
}
