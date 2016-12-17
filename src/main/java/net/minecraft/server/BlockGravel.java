package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockGravel extends BlockFalling {

    public BlockGravel() {}

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        if (i > 3) {
            i = 3;
        }

        return random.nextInt(10 - i * 3) == 0 ? Items.FLINT : Item.getItemOf(this);
    }

    public MaterialMapColor r(IBlockData iblockdata) {
        return MaterialMapColor.m;
    }
}
