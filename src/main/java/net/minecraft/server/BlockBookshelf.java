package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockBookshelf extends Block {

    public BlockBookshelf() {
        super(Material.WOOD);
        this.a(CreativeModeTab.b);
    }

    public int a(Random random) {
        return 3;
    }

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return Items.BOOK;
    }
}
