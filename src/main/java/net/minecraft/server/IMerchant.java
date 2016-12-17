package net.minecraft.server;

public interface IMerchant {

    void setTradingPlayer(EntityHuman entityhuman);

    EntityHuman getTrader();

    MerchantRecipeList getOffers(EntityHuman entityhuman);

    void a(MerchantRecipe merchantrecipe);

    void a(ItemStack itemstack);

    IChatBaseComponent getScoreboardDisplayName();
}
