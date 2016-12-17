package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockObsidian extends Block {

    public BlockObsidian() {
        super(Material.STONE);
        this.a(CreativeModeTab.b);
    }

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return Item.getItemOf(Blocks.OBSIDIAN);
    }

    public MaterialMapColor r(IBlockData iblockdata) {
        return MaterialMapColor.E;
    }
}
