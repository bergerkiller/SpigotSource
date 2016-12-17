package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockHugeMushroom extends Block {

    public static final BlockStateEnum<BlockHugeMushroom.EnumHugeMushroomVariant> VARIANT = BlockStateEnum.of("variant", BlockHugeMushroom.EnumHugeMushroomVariant.class);
    private final Block b;

    public BlockHugeMushroom(Material material, MaterialMapColor materialmapcolor, Block block) {
        super(material, materialmapcolor);
        this.w(this.blockStateList.getBlockData().set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.ALL_OUTSIDE));
        this.b = block;
    }

    public int a(Random random) {
        return Math.max(0, random.nextInt(10) - 7);
    }

    public MaterialMapColor r(IBlockData iblockdata) {
        switch (BlockHugeMushroom.SyntheticClass_1.a[((BlockHugeMushroom.EnumHugeMushroomVariant) iblockdata.get(BlockHugeMushroom.VARIANT)).ordinal()]) {
        case 1:
            return MaterialMapColor.e;

        case 2:
            return MaterialMapColor.d;

        case 3:
            return MaterialMapColor.d;

        default:
            return super.r(iblockdata);
        }
    }

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return Item.getItemOf(this.b);
    }

    public ItemStack a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return new ItemStack(this.b);
    }

    public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
        return this.getBlockData();
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.a(i));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((BlockHugeMushroom.EnumHugeMushroomVariant) iblockdata.get(BlockHugeMushroom.VARIANT)).a();
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch (BlockHugeMushroom.SyntheticClass_1.b[enumblockrotation.ordinal()]) {
        case 1:
            switch (BlockHugeMushroom.SyntheticClass_1.a[((BlockHugeMushroom.EnumHugeMushroomVariant) iblockdata.get(BlockHugeMushroom.VARIANT)).ordinal()]) {
            case 3:
                break;

            case 4:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_EAST);

            case 5:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH);

            case 6:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_WEST);

            case 7:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.EAST);

            case 8:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.WEST);

            case 9:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_EAST);

            case 10:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH);

            case 11:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_WEST);

            default:
                return iblockdata;
            }

        case 2:
            switch (BlockHugeMushroom.SyntheticClass_1.a[((BlockHugeMushroom.EnumHugeMushroomVariant) iblockdata.get(BlockHugeMushroom.VARIANT)).ordinal()]) {
            case 3:
                break;

            case 4:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_WEST);

            case 5:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.WEST);

            case 6:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_WEST);

            case 7:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH);

            case 8:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH);

            case 9:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_EAST);

            case 10:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.EAST);

            case 11:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_EAST);

            default:
                return iblockdata;
            }

        case 3:
            switch (BlockHugeMushroom.SyntheticClass_1.a[((BlockHugeMushroom.EnumHugeMushroomVariant) iblockdata.get(BlockHugeMushroom.VARIANT)).ordinal()]) {
            case 3:
                break;

            case 4:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_EAST);

            case 5:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.EAST);

            case 6:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_EAST);

            case 7:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH);

            case 8:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH);

            case 9:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_WEST);

            case 10:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.WEST);

            case 11:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_WEST);

            default:
                return iblockdata;
            }

        default:
            return iblockdata;
        }
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        BlockHugeMushroom.EnumHugeMushroomVariant blockhugemushroom_enumhugemushroomvariant = (BlockHugeMushroom.EnumHugeMushroomVariant) iblockdata.get(BlockHugeMushroom.VARIANT);

        switch (BlockHugeMushroom.SyntheticClass_1.c[enumblockmirror.ordinal()]) {
        case 1:
            switch (BlockHugeMushroom.SyntheticClass_1.a[blockhugemushroom_enumhugemushroomvariant.ordinal()]) {
            case 4:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_WEST);

            case 5:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH);

            case 6:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_EAST);

            case 7:
            case 8:
            default:
                return super.a(iblockdata, enumblockmirror);

            case 9:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_WEST);

            case 10:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH);

            case 11:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_EAST);
            }

        case 2:
            switch (BlockHugeMushroom.SyntheticClass_1.a[blockhugemushroom_enumhugemushroomvariant.ordinal()]) {
            case 4:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_EAST);

            case 5:
            case 10:
            default:
                break;

            case 6:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_WEST);

            case 7:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.EAST);

            case 8:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.WEST);

            case 9:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_EAST);

            case 11:
                return iblockdata.set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_WEST);
            }
        }

        return super.a(iblockdata, enumblockmirror);
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockHugeMushroom.VARIANT});
    }

    static class SyntheticClass_1 {

        static final int[] a;
        static final int[] b;
        static final int[] c = new int[EnumBlockMirror.values().length];

        static {
            try {
                BlockHugeMushroom.SyntheticClass_1.c[EnumBlockMirror.LEFT_RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                BlockHugeMushroom.SyntheticClass_1.c[EnumBlockMirror.FRONT_BACK.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            b = new int[EnumBlockRotation.values().length];

            try {
                BlockHugeMushroom.SyntheticClass_1.b[EnumBlockRotation.CLOCKWISE_180.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                BlockHugeMushroom.SyntheticClass_1.b[EnumBlockRotation.COUNTERCLOCKWISE_90.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                BlockHugeMushroom.SyntheticClass_1.b[EnumBlockRotation.CLOCKWISE_90.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            a = new int[BlockHugeMushroom.EnumHugeMushroomVariant.values().length];

            try {
                BlockHugeMushroom.SyntheticClass_1.a[BlockHugeMushroom.EnumHugeMushroomVariant.ALL_STEM.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                BlockHugeMushroom.SyntheticClass_1.a[BlockHugeMushroom.EnumHugeMushroomVariant.ALL_INSIDE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                BlockHugeMushroom.SyntheticClass_1.a[BlockHugeMushroom.EnumHugeMushroomVariant.STEM.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                BlockHugeMushroom.SyntheticClass_1.a[BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_WEST.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                BlockHugeMushroom.SyntheticClass_1.a[BlockHugeMushroom.EnumHugeMushroomVariant.NORTH.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                BlockHugeMushroom.SyntheticClass_1.a[BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_EAST.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                BlockHugeMushroom.SyntheticClass_1.a[BlockHugeMushroom.EnumHugeMushroomVariant.WEST.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                BlockHugeMushroom.SyntheticClass_1.a[BlockHugeMushroom.EnumHugeMushroomVariant.EAST.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                BlockHugeMushroom.SyntheticClass_1.a[BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_WEST.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                BlockHugeMushroom.SyntheticClass_1.a[BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                BlockHugeMushroom.SyntheticClass_1.a[BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_EAST.ordinal()] = 11;
            } catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }

        }
    }

    public static enum EnumHugeMushroomVariant implements INamable {

        NORTH_WEST(1, "north_west"), NORTH(2, "north"), NORTH_EAST(3, "north_east"), WEST(4, "west"), CENTER(5, "center"), EAST(6, "east"), SOUTH_WEST(7, "south_west"), SOUTH(8, "south"), SOUTH_EAST(9, "south_east"), STEM(10, "stem"), ALL_INSIDE(0, "all_inside"), ALL_OUTSIDE(14, "all_outside"), ALL_STEM(15, "all_stem");

        private static final BlockHugeMushroom.EnumHugeMushroomVariant[] n = new BlockHugeMushroom.EnumHugeMushroomVariant[16];
        private final int o;
        private final String p;

        private EnumHugeMushroomVariant(int i, String s) {
            this.o = i;
            this.p = s;
        }

        public int a() {
            return this.o;
        }

        public String toString() {
            return this.p;
        }

        public static BlockHugeMushroom.EnumHugeMushroomVariant a(int i) {
            if (i < 0 || i >= BlockHugeMushroom.EnumHugeMushroomVariant.n.length) {
                i = 0;
            }

            BlockHugeMushroom.EnumHugeMushroomVariant blockhugemushroom_enumhugemushroomvariant = BlockHugeMushroom.EnumHugeMushroomVariant.n[i];

            return blockhugemushroom_enumhugemushroomvariant == null ? BlockHugeMushroom.EnumHugeMushroomVariant.n[0] : blockhugemushroom_enumhugemushroomvariant;
        }

        public String getName() {
            return this.p;
        }

        static {
            BlockHugeMushroom.EnumHugeMushroomVariant[] ablockhugemushroom_enumhugemushroomvariant = values();
            int i = ablockhugemushroom_enumhugemushroomvariant.length;

            for (int j = 0; j < i; ++j) {
                BlockHugeMushroom.EnumHugeMushroomVariant blockhugemushroom_enumhugemushroomvariant = ablockhugemushroom_enumhugemushroomvariant[j];

                BlockHugeMushroom.EnumHugeMushroomVariant.n[blockhugemushroom_enumhugemushroomvariant.a()] = blockhugemushroom_enumhugemushroomvariant;
            }

        }
    }
}
