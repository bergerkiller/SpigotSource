package net.minecraft.server;

public class ItemSaddle extends Item {

    public ItemSaddle() {
        this.maxStackSize = 1;
        this.a(CreativeModeTab.e);
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, EntityLiving entityliving, EnumHand enumhand) {
        if (entityliving instanceof EntityPig) {
            EntityPig entitypig = (EntityPig) entityliving;

            if (!entitypig.hasSaddle() && !entitypig.isBaby()) {
                entitypig.setSaddle(true);
                entitypig.world.a(entityhuman, entitypig.locX, entitypig.locY, entitypig.locZ, SoundEffects.dT, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                --itemstack.count;
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        this.a(itemstack, (EntityHuman) null, entityliving, EnumHand.MAIN_HAND);
        return true;
    }
}
