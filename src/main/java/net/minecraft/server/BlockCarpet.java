package net.minecraft.server;

public class BlockCarpet extends Block {

    public static final BlockStateEnum<EnumColor> COLOR = BlockStateEnum.of("color", EnumColor.class);
    protected static final AxisAlignedBB b = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

    protected BlockCarpet() {
        super(Material.WOOL);
        this.w(this.blockStateList.getBlockData().set(BlockCarpet.COLOR, EnumColor.WHITE));
        this.a(true);
        this.a(CreativeModeTab.c);
    }

    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return BlockCarpet.b;
    }

    public MaterialMapColor r(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockCarpet.COLOR)).e();
    }

    public boolean b(IBlockData iblockdata) {
        return false;
    }

    public boolean c(IBlockData iblockdata) {
        return false;
    }

    public boolean canPlace(World world, BlockPosition blockposition) {
        return super.canPlace(world, blockposition) && this.b(world, blockposition);
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
        this.e(world, blockposition, iblockdata);
    }

    private boolean e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!this.b(world, blockposition)) {
            this.b(world, blockposition, iblockdata, 0);
            world.setAir(blockposition);
            return false;
        } else {
            return true;
        }
    }

    private boolean b(World world, BlockPosition blockposition) {
        return !world.isEmpty(blockposition.down());
    }

    public int getDropData(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockCarpet.COLOR)).getColorIndex();
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockCarpet.COLOR, EnumColor.fromColorIndex(i));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockCarpet.COLOR)).getColorIndex();
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockCarpet.COLOR});
    }
}
