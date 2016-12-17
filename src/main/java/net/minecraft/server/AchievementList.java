package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.List;

public class AchievementList {

    public static int a;
    public static int b;
    public static int c;
    public static int d;
    public static final List<Achievement> e = Lists.newArrayList();
    public static final Achievement f = (new Achievement("achievement.openInventory", "openInventory", 0, 0, Items.BOOK, (Achievement) null)).a().c();
    public static final Achievement g = (new Achievement("achievement.mineWood", "mineWood", 2, 1, Blocks.LOG, AchievementList.f)).c();
    public static final Achievement h = (new Achievement("achievement.buildWorkBench", "buildWorkBench", 4, -1, Blocks.CRAFTING_TABLE, AchievementList.g)).c();
    public static final Achievement i = (new Achievement("achievement.buildPickaxe", "buildPickaxe", 4, 2, Items.WOODEN_PICKAXE, AchievementList.h)).c();
    public static final Achievement j = (new Achievement("achievement.buildFurnace", "buildFurnace", 3, 4, Blocks.FURNACE, AchievementList.i)).c();
    public static final Achievement k = (new Achievement("achievement.acquireIron", "acquireIron", 1, 4, Items.IRON_INGOT, AchievementList.j)).c();
    public static final Achievement l = (new Achievement("achievement.buildHoe", "buildHoe", 2, -3, Items.WOODEN_HOE, AchievementList.h)).c();
    public static final Achievement m = (new Achievement("achievement.makeBread", "makeBread", -1, -3, Items.BREAD, AchievementList.l)).c();
    public static final Achievement n = (new Achievement("achievement.bakeCake", "bakeCake", 0, -5, Items.CAKE, AchievementList.l)).c();
    public static final Achievement o = (new Achievement("achievement.buildBetterPickaxe", "buildBetterPickaxe", 6, 2, Items.STONE_PICKAXE, AchievementList.i)).c();
    public static final Achievement p = (new Achievement("achievement.cookFish", "cookFish", 2, 6, Items.COOKED_FISH, AchievementList.j)).c();
    public static final Achievement q = (new Achievement("achievement.onARail", "onARail", 2, 3, Blocks.RAIL, AchievementList.k)).b().c();
    public static final Achievement r = (new Achievement("achievement.buildSword", "buildSword", 6, -1, Items.WOODEN_SWORD, AchievementList.h)).c();
    public static final Achievement s = (new Achievement("achievement.killEnemy", "killEnemy", 8, -1, Items.BONE, AchievementList.r)).c();
    public static final Achievement t = (new Achievement("achievement.killCow", "killCow", 7, -3, Items.LEATHER, AchievementList.r)).c();
    public static final Achievement u = (new Achievement("achievement.flyPig", "flyPig", 9, -3, Items.SADDLE, AchievementList.t)).b().c();
    public static final Achievement v = (new Achievement("achievement.snipeSkeleton", "snipeSkeleton", 7, 0, Items.BOW, AchievementList.s)).b().c();
    public static final Achievement w = (new Achievement("achievement.diamonds", "diamonds", -1, 5, Blocks.DIAMOND_ORE, AchievementList.k)).c();
    public static final Achievement x = (new Achievement("achievement.diamondsToYou", "diamondsToYou", -1, 2, Items.DIAMOND, AchievementList.w)).c();
    public static final Achievement y = (new Achievement("achievement.portal", "portal", -1, 7, Blocks.OBSIDIAN, AchievementList.w)).c();
    public static final Achievement z = (new Achievement("achievement.ghast", "ghast", -4, 8, Items.GHAST_TEAR, AchievementList.y)).b().c();
    public static final Achievement A = (new Achievement("achievement.blazeRod", "blazeRod", 0, 9, Items.BLAZE_ROD, AchievementList.y)).c();
    public static final Achievement B = (new Achievement("achievement.potion", "potion", 2, 8, Items.POTION, AchievementList.A)).c();
    public static final Achievement C = (new Achievement("achievement.theEnd", "theEnd", 3, 10, Items.ENDER_EYE, AchievementList.A)).b().c();
    public static final Achievement D = (new Achievement("achievement.theEnd2", "theEnd2", 4, 13, Blocks.DRAGON_EGG, AchievementList.C)).b().c();
    public static final Achievement E = (new Achievement("achievement.enchantments", "enchantments", -4, 4, Blocks.ENCHANTING_TABLE, AchievementList.w)).c();
    public static final Achievement F = (new Achievement("achievement.overkill", "overkill", -4, 1, Items.DIAMOND_SWORD, AchievementList.E)).b().c();
    public static final Achievement G = (new Achievement("achievement.bookcase", "bookcase", -3, 6, Blocks.BOOKSHELF, AchievementList.E)).c();
    public static final Achievement H = (new Achievement("achievement.breedCow", "breedCow", 7, -5, Items.WHEAT, AchievementList.t)).c();
    public static final Achievement I = (new Achievement("achievement.spawnWither", "spawnWither", 7, 12, new ItemStack(Items.SKULL, 1, 1), AchievementList.D)).c();
    public static final Achievement J = (new Achievement("achievement.killWither", "killWither", 7, 10, Items.NETHER_STAR, AchievementList.I)).c();
    public static final Achievement K = (new Achievement("achievement.fullBeacon", "fullBeacon", 7, 8, Blocks.BEACON, AchievementList.J)).b().c();
    public static final Achievement L = (new Achievement("achievement.exploreAllBiomes", "exploreAllBiomes", 4, 8, Items.DIAMOND_BOOTS, AchievementList.C)).a(AchievementSet.class).b().c();
    public static final Achievement M = (new Achievement("achievement.overpowered", "overpowered", 6, 4, new ItemStack(Items.GOLDEN_APPLE, 1, 1), AchievementList.o)).b().c();

    public static void a() {}
}
