package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockSlowSand extends Block {

    protected static final AxisAlignedBB a = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

    public BlockSlowSand() {
        super(Material.SAND, MaterialMapColor.B);
        this.a(CreativeModeTab.b);
    }

    @Nullable
    public AxisAlignedBB a(IBlockData iblockdata, World world, BlockPosition blockposition) {
        return BlockSlowSand.a;
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
        entity.motX *= 0.4D;
        entity.motZ *= 0.4D;
    }
}
