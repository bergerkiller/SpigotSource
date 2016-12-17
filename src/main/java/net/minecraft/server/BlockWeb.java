package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockWeb extends Block {

    public BlockWeb() {
        super(Material.WEB);
        this.a(CreativeModeTab.c);
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
        entity.aQ();
    }

    public boolean b(IBlockData iblockdata) {
        return false;
    }

    @Nullable
    public AxisAlignedBB a(IBlockData iblockdata, World world, BlockPosition blockposition) {
        return BlockWeb.k;
    }

    public boolean c(IBlockData iblockdata) {
        return false;
    }

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return Items.STRING;
    }

    protected boolean o() {
        return true;
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, @Nullable ItemStack itemstack) {
        if (!world.isClientSide && itemstack != null && itemstack.getItem() == Items.SHEARS) {
            entityhuman.b(StatisticList.a((Block) this));
            a(world, blockposition, new ItemStack(Item.getItemOf(this), 1));
        } else {
            super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
        }
    }
}
