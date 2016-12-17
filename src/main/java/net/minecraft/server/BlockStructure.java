package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockStructure extends BlockTileEntity {

    public static final BlockStateEnum<TileEntityStructure.UsageMode> a = BlockStateEnum.of("mode", TileEntityStructure.UsageMode.class);

    public BlockStructure() {
        super(Material.ORE, MaterialMapColor.x);
        this.w(this.blockStateList.getBlockData());
    }

    public TileEntity a(World world, int i) {
        return new TileEntityStructure();
    }

    public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumHand enumhand, @Nullable ItemStack itemstack, EnumDirection enumdirection, float f, float f1, float f2) {
        return false;
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {}

    @Nullable
    public ItemStack a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return null;
    }

    public int a(Random random) {
        return 0;
    }

    public EnumRenderType a(IBlockData iblockdata) {
        return EnumRenderType.MODEL;
    }

    public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
        return this.getBlockData().set(BlockStructure.a, TileEntityStructure.UsageMode.DATA);
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockStructure.a, TileEntityStructure.UsageMode.a(i));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((TileEntityStructure.UsageMode) iblockdata.get(BlockStructure.a)).a();
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockStructure.a});
    }
}
