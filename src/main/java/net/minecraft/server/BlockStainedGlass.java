package net.minecraft.server;

import java.util.Random;

public class BlockStainedGlass extends BlockHalfTransparent {

    public static final BlockStateEnum<EnumColor> COLOR = BlockStateEnum.of("color", EnumColor.class);

    public BlockStainedGlass(Material material) {
        super(material, false);
        this.w(this.blockStateList.getBlockData().set(BlockStainedGlass.COLOR, EnumColor.WHITE));
        this.a(CreativeModeTab.b);
    }

    public int getDropData(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockStainedGlass.COLOR)).getColorIndex();
    }

    public MaterialMapColor r(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockStainedGlass.COLOR)).e();
    }

    public int a(Random random) {
        return 0;
    }

    protected boolean o() {
        return true;
    }

    public boolean c(IBlockData iblockdata) {
        return false;
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockStainedGlass.COLOR, EnumColor.fromColorIndex(i));
    }

    public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!world.isClientSide) {
            BlockBeacon.c(world, blockposition);
        }

    }

    public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!world.isClientSide) {
            BlockBeacon.c(world, blockposition);
        }

    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockStainedGlass.COLOR)).getColorIndex();
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockStainedGlass.COLOR});
    }
}
