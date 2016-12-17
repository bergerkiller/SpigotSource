package net.minecraft.server;

public class ItemWithAuxData extends ItemBlock {

    private String[] b;

    public ItemWithAuxData(Block block, boolean flag) {
        super(block);
        if (flag) {
            this.setMaxDurability(0);
            this.a(true);
        }

    }

    public int filterData(int i) {
        return i;
    }

    public ItemWithAuxData a(String[] astring) {
        this.b = astring;
        return this;
    }

    public String f_(ItemStack itemstack) {
        if (this.b == null) {
            return super.f_(itemstack);
        } else {
            int i = itemstack.getData();

            return i >= 0 && i < this.b.length ? super.f_(itemstack) + "." + this.b[i] : super.f_(itemstack);
        }
    }
}
