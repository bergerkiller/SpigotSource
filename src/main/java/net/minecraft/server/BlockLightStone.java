package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockLightStone extends Block {

    public BlockLightStone(Material material) {
        super(material);
        this.a(CreativeModeTab.b);
    }

    public int getDropCount(int i, Random random) {
        return MathHelper.clamp(this.a(random) + random.nextInt(i + 1), 1, 4);
    }

    public int a(Random random) {
        return 2 + random.nextInt(3);
    }

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return Items.GLOWSTONE_DUST;
    }

    public MaterialMapColor r(IBlockData iblockdata) {
        return MaterialMapColor.d;
    }
}
