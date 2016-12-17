package net.minecraft.server;

public abstract class CreativeModeTab {

    public static final CreativeModeTab[] a = new CreativeModeTab[12];
    public static final CreativeModeTab b = new CreativeModeTab(0, "buildingBlocks") {
    };
    public static final CreativeModeTab c = new CreativeModeTab(1, "decorations") {
    };
    public static final CreativeModeTab d = new CreativeModeTab(2, "redstone") {
    };
    public static final CreativeModeTab e = new CreativeModeTab(3, "transportation") {
    };
    public static final CreativeModeTab f = (new CreativeModeTab(4, "misc") {
    }).a(new EnchantmentSlotType[] { EnchantmentSlotType.ALL});
    public static final CreativeModeTab g = (new CreativeModeTab(5, "search") {
    }).a("item_search.png");
    public static final CreativeModeTab h = new CreativeModeTab(6, "food") {
    };
    public static final CreativeModeTab i = (new CreativeModeTab(7, "tools") {
    }).a(new EnchantmentSlotType[] { EnchantmentSlotType.DIGGER, EnchantmentSlotType.FISHING_ROD, EnchantmentSlotType.BREAKABLE});
    public static final CreativeModeTab j = (new CreativeModeTab(8, "combat") {
    }).a(new EnchantmentSlotType[] { EnchantmentSlotType.ARMOR, EnchantmentSlotType.ARMOR_FEET, EnchantmentSlotType.ARMOR_HEAD, EnchantmentSlotType.ARMOR_LEGS, EnchantmentSlotType.ARMOR_CHEST, EnchantmentSlotType.BOW, EnchantmentSlotType.WEAPON});
    public static final CreativeModeTab k = new CreativeModeTab(9, "brewing") {
    };
    public static final CreativeModeTab l = new CreativeModeTab(10, "materials") {
    };
    public static final CreativeModeTab m = (new CreativeModeTab(11, "inventory") {
    }).a("inventory.png").k().i();
    private final int n;
    private final String o;
    private String p = "items.png";
    private boolean q = true;
    private boolean r = true;
    private EnchantmentSlotType[] s;

    public CreativeModeTab(int i, String s) {
        this.n = i;
        this.o = s;
        CreativeModeTab.a[i] = this;
    }

    public CreativeModeTab a(String s) {
        this.p = s;
        return this;
    }

    public CreativeModeTab i() {
        this.r = false;
        return this;
    }

    public CreativeModeTab k() {
        this.q = false;
        return this;
    }

    public CreativeModeTab a(EnchantmentSlotType... aenchantmentslottype) {
        this.s = aenchantmentslottype;
        return this;
    }
}
