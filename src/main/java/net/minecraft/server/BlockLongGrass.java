package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockLongGrass extends BlockPlant implements IBlockFragilePlantElement {

    public static final BlockStateEnum<BlockLongGrass.EnumTallGrassType> TYPE = BlockStateEnum.of("type", BlockLongGrass.EnumTallGrassType.class);
    protected static final AxisAlignedBB c = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    protected BlockLongGrass() {
        super(Material.REPLACEABLE_PLANT);
        this.w(this.blockStateList.getBlockData().set(BlockLongGrass.TYPE, BlockLongGrass.EnumTallGrassType.DEAD_BUSH));
    }

    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return BlockLongGrass.c;
    }

    public boolean f(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return this.i(world.getType(blockposition.down()));
    }

    public boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return true;
    }

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return random.nextInt(8) == 0 ? Items.WHEAT_SEEDS : null;
    }

    public int getDropCount(int i, Random random) {
        return 1 + random.nextInt(i * 2 + 1);
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, @Nullable ItemStack itemstack) {
        if (!world.isClientSide && itemstack != null && itemstack.getItem() == Items.SHEARS) {
            entityhuman.b(StatisticList.a((Block) this));
            a(world, blockposition, new ItemStack(Blocks.TALLGRASS, 1, ((BlockLongGrass.EnumTallGrassType) iblockdata.get(BlockLongGrass.TYPE)).a()));
        } else {
            super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
        }

    }

    public ItemStack a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return new ItemStack(this, 1, iblockdata.getBlock().toLegacyData(iblockdata));
    }

    public boolean a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
        return iblockdata.get(BlockLongGrass.TYPE) != BlockLongGrass.EnumTallGrassType.DEAD_BUSH;
    }

    public boolean a(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
        return true;
    }

    public void b(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
        BlockTallPlant.EnumTallFlowerVariants blocktallplant_enumtallflowervariants = BlockTallPlant.EnumTallFlowerVariants.GRASS;

        if (iblockdata.get(BlockLongGrass.TYPE) == BlockLongGrass.EnumTallGrassType.FERN) {
            blocktallplant_enumtallflowervariants = BlockTallPlant.EnumTallFlowerVariants.FERN;
        }

        if (Blocks.DOUBLE_PLANT.canPlace(world, blockposition)) {
            Blocks.DOUBLE_PLANT.a(world, blockposition, blocktallplant_enumtallflowervariants, 2);
        }

    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockLongGrass.TYPE, BlockLongGrass.EnumTallGrassType.a(i));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((BlockLongGrass.EnumTallGrassType) iblockdata.get(BlockLongGrass.TYPE)).a();
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockLongGrass.TYPE});
    }

    public static enum EnumTallGrassType implements INamable {

        DEAD_BUSH(0, "dead_bush"), GRASS(1, "tall_grass"), FERN(2, "fern");

        private static final BlockLongGrass.EnumTallGrassType[] d = new BlockLongGrass.EnumTallGrassType[values().length];
        private final int e;
        private final String f;

        private EnumTallGrassType(int i, String s) {
            this.e = i;
            this.f = s;
        }

        public int a() {
            return this.e;
        }

        public String toString() {
            return this.f;
        }

        public static BlockLongGrass.EnumTallGrassType a(int i) {
            if (i < 0 || i >= BlockLongGrass.EnumTallGrassType.d.length) {
                i = 0;
            }

            return BlockLongGrass.EnumTallGrassType.d[i];
        }

        public String getName() {
            return this.f;
        }

        static {
            BlockLongGrass.EnumTallGrassType[] ablocklonggrass_enumtallgrasstype = values();
            int i = ablocklonggrass_enumtallgrasstype.length;

            for (int j = 0; j < i; ++j) {
                BlockLongGrass.EnumTallGrassType blocklonggrass_enumtallgrasstype = ablocklonggrass_enumtallgrasstype[j];

                BlockLongGrass.EnumTallGrassType.d[blocklonggrass_enumtallgrasstype.a()] = blocklonggrass_enumtallgrasstype;
            }

        }
    }
}
