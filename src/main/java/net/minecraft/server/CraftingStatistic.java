package net.minecraft.server;

public class CraftingStatistic extends Statistic {

    private final Item a;

    public CraftingStatistic(String s, String s1, IChatBaseComponent ichatbasecomponent, Item item) {
        super(s + s1, ichatbasecomponent);
        this.a = item;
    }
}
