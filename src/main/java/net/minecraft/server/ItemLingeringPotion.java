package net.minecraft.server;

public class ItemLingeringPotion extends ItemPotion {

    public ItemLingeringPotion() {}

    public String a(ItemStack itemstack) {
        return LocaleI18n.get(PotionUtil.c(itemstack).b("lingering_potion.effect."));
    }

    public InteractionResultWrapper<ItemStack> a(ItemStack itemstack, World world, EntityHuman entityhuman, EnumHand enumhand) {
        if (!entityhuman.abilities.canInstantlyBuild) {
            --itemstack.count;
        }

        world.a((EntityHuman) null, entityhuman.locX, entityhuman.locY, entityhuman.locZ, SoundEffects.dj, SoundCategory.NEUTRAL, 0.5F, 0.4F / (ItemLingeringPotion.j.nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide) {
            EntityPotion entitypotion = new EntityPotion(world, entityhuman, itemstack);

            entitypotion.a(entityhuman, entityhuman.pitch, entityhuman.yaw, -20.0F, 0.5F, 1.0F);
            world.addEntity(entitypotion);
        }

        entityhuman.b(StatisticList.b((Item) this));
        return new InteractionResultWrapper(EnumInteractionResult.SUCCESS, itemstack);
    }
}
