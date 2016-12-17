package net.minecraft.server;

public class PathfinderGoalInteractVillagers extends PathfinderGoalInteract {

    private int e;
    private EntityVillager f;

    public PathfinderGoalInteractVillagers(EntityVillager entityvillager) {
        super(entityvillager, EntityVillager.class, 3.0F, 0.02F);
        this.f = entityvillager;
    }

    public void c() {
        super.c();
        if (this.f.dh() && this.b instanceof EntityVillager && ((EntityVillager) this.b).di()) {
            this.e = 10;
        } else {
            this.e = 0;
        }

    }

    public void e() {
        super.e();
        if (this.e > 0) {
            --this.e;
            if (this.e == 0) {
                InventorySubcontainer inventorysubcontainer = this.f.df();

                for (int i = 0; i < inventorysubcontainer.getSize(); ++i) {
                    ItemStack itemstack = inventorysubcontainer.getItem(i);
                    ItemStack itemstack1 = null;

                    if (itemstack != null) {
                        Item item = itemstack.getItem();
                        int j;

                        if ((item == Items.BREAD || item == Items.POTATO || item == Items.CARROT || item == Items.BEETROOT) && itemstack.count > 3) {
                            j = itemstack.count / 2;
                            itemstack.count -= j;
                            itemstack1 = new ItemStack(item, j, itemstack.getData());
                        } else if (item == Items.WHEAT && itemstack.count > 5) {
                            j = itemstack.count / 2 / 3 * 3;
                            int k = j / 3;

                            itemstack.count -= j;
                            itemstack1 = new ItemStack(Items.BREAD, k, 0);
                        }

                        if (itemstack.count <= 0) {
                            inventorysubcontainer.setItem(i, (ItemStack) null);
                        }
                    }

                    if (itemstack1 != null) {
                        double d0 = this.f.locY - 0.30000001192092896D + (double) this.f.getHeadHeight();
                        EntityItem entityitem = new EntityItem(this.f.world, this.f.locX, d0, this.f.locZ, itemstack1);
                        float f = 0.3F;
                        float f1 = this.f.aP;
                        float f2 = this.f.pitch;

                        entityitem.motX = (double) (-MathHelper.sin(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F) * f);
                        entityitem.motZ = (double) (MathHelper.cos(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F) * f);
                        entityitem.motY = (double) (-MathHelper.sin(f2 * 0.017453292F) * f + 0.1F);
                        entityitem.q();
                        this.f.world.addEntity(entityitem);
                        break;
                    }
                }
            }
        }

    }
}
