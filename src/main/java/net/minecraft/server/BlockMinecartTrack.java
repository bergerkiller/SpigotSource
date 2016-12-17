package net.minecraft.server;

public class BlockMinecartTrack extends BlockMinecartTrackAbstract {

    public static final BlockStateEnum<BlockMinecartTrackAbstract.EnumTrackPosition> SHAPE = BlockStateEnum.of("shape", BlockMinecartTrackAbstract.EnumTrackPosition.class);

    protected BlockMinecartTrack() {
        super(false);
        this.w(this.blockStateList.getBlockData().set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH));
    }

    protected void b(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
        if (block.getBlockData().m() && (new BlockMinecartTrackAbstract.MinecartTrackLogic(world, blockposition, iblockdata)).b() == 3) {
            this.a(world, blockposition, iblockdata, false);
        }

    }

    public IBlockState<BlockMinecartTrackAbstract.EnumTrackPosition> g() {
        return BlockMinecartTrack.SHAPE;
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.a(i));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((BlockMinecartTrackAbstract.EnumTrackPosition) iblockdata.get(BlockMinecartTrack.SHAPE)).a();
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch (BlockMinecartTrack.SyntheticClass_1.b[enumblockrotation.ordinal()]) {
        case 1:
            switch (BlockMinecartTrack.SyntheticClass_1.a[((BlockMinecartTrackAbstract.EnumTrackPosition) iblockdata.get(BlockMinecartTrack.SHAPE)).ordinal()]) {
            case 1:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST);

            case 2:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST);

            case 3:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH);

            case 4:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH);

            case 5:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST);

            case 6:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST);

            case 7:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST);

            case 8:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST);
            }

        case 2:
            switch (BlockMinecartTrack.SyntheticClass_1.a[((BlockMinecartTrackAbstract.EnumTrackPosition) iblockdata.get(BlockMinecartTrack.SHAPE)).ordinal()]) {
            case 1:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH);

            case 2:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH);

            case 3:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST);

            case 4:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST);

            case 5:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST);

            case 6:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST);

            case 7:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST);

            case 8:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST);

            case 9:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST);

            case 10:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH);
            }

        case 3:
            switch (BlockMinecartTrack.SyntheticClass_1.a[((BlockMinecartTrackAbstract.EnumTrackPosition) iblockdata.get(BlockMinecartTrack.SHAPE)).ordinal()]) {
            case 1:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH);

            case 2:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH);

            case 3:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST);

            case 4:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST);

            case 5:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST);

            case 6:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST);

            case 7:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST);

            case 8:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST);

            case 9:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST);

            case 10:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH);
            }

        default:
            return iblockdata;
        }
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition = (BlockMinecartTrackAbstract.EnumTrackPosition) iblockdata.get(BlockMinecartTrack.SHAPE);

        switch (BlockMinecartTrack.SyntheticClass_1.c[enumblockmirror.ordinal()]) {
        case 1:
            switch (BlockMinecartTrack.SyntheticClass_1.a[blockminecarttrackabstract_enumtrackposition.ordinal()]) {
            case 3:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH);

            case 4:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH);

            case 5:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST);

            case 6:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST);

            case 7:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST);

            case 8:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST);

            default:
                return super.a(iblockdata, enumblockmirror);
            }

        case 2:
            switch (BlockMinecartTrack.SyntheticClass_1.a[blockminecarttrackabstract_enumtrackposition.ordinal()]) {
            case 1:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST);

            case 2:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST);

            case 3:
            case 4:
            default:
                break;

            case 5:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST);

            case 6:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST);

            case 7:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST);

            case 8:
                return iblockdata.set(BlockMinecartTrack.SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST);
            }
        }

        return super.a(iblockdata, enumblockmirror);
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockMinecartTrack.SHAPE});
    }

    static class SyntheticClass_1 {

        static final int[] a;
        static final int[] b;
        static final int[] c = new int[EnumBlockMirror.values().length];

        static {
            try {
                BlockMinecartTrack.SyntheticClass_1.c[EnumBlockMirror.LEFT_RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                BlockMinecartTrack.SyntheticClass_1.c[EnumBlockMirror.FRONT_BACK.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            b = new int[EnumBlockRotation.values().length];

            try {
                BlockMinecartTrack.SyntheticClass_1.b[EnumBlockRotation.CLOCKWISE_180.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                BlockMinecartTrack.SyntheticClass_1.b[EnumBlockRotation.COUNTERCLOCKWISE_90.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                BlockMinecartTrack.SyntheticClass_1.b[EnumBlockRotation.CLOCKWISE_90.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            a = new int[BlockMinecartTrackAbstract.EnumTrackPosition.values().length];

            try {
                BlockMinecartTrack.SyntheticClass_1.a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                BlockMinecartTrack.SyntheticClass_1.a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                BlockMinecartTrack.SyntheticClass_1.a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                BlockMinecartTrack.SyntheticClass_1.a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                BlockMinecartTrack.SyntheticClass_1.a[BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                BlockMinecartTrack.SyntheticClass_1.a[BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                BlockMinecartTrack.SyntheticClass_1.a[BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                BlockMinecartTrack.SyntheticClass_1.a[BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                BlockMinecartTrack.SyntheticClass_1.a[BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                BlockMinecartTrack.SyntheticClass_1.a[BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST.ordinal()] = 10;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

        }
    }
}
