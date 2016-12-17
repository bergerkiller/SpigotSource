package net.minecraft.server;

public class BlockStainedGlassPane extends BlockThin {

    public static final BlockStateEnum<EnumColor> COLOR = BlockStateEnum.of("color", EnumColor.class);

    public BlockStainedGlassPane() {
        super(Material.SHATTERABLE, false);
        this.w(this.blockStateList.getBlockData().set(BlockStainedGlassPane.NORTH, Boolean.valueOf(false)).set(BlockStainedGlassPane.EAST, Boolean.valueOf(false)).set(BlockStainedGlassPane.SOUTH, Boolean.valueOf(false)).set(BlockStainedGlassPane.WEST, Boolean.valueOf(false)).set(BlockStainedGlassPane.COLOR, EnumColor.WHITE));
        this.a(CreativeModeTab.c);
    }

    public int getDropData(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockStainedGlassPane.COLOR)).getColorIndex();
    }

    public MaterialMapColor r(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockStainedGlassPane.COLOR)).e();
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockStainedGlassPane.COLOR, EnumColor.fromColorIndex(i));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((EnumColor) iblockdata.get(BlockStainedGlassPane.COLOR)).getColorIndex();
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch (BlockStainedGlassPane.SyntheticClass_1.a[enumblockrotation.ordinal()]) {
        case 1:
            return iblockdata.set(BlockStainedGlassPane.NORTH, iblockdata.get(BlockStainedGlassPane.SOUTH)).set(BlockStainedGlassPane.EAST, iblockdata.get(BlockStainedGlassPane.WEST)).set(BlockStainedGlassPane.SOUTH, iblockdata.get(BlockStainedGlassPane.NORTH)).set(BlockStainedGlassPane.WEST, iblockdata.get(BlockStainedGlassPane.EAST));

        case 2:
            return iblockdata.set(BlockStainedGlassPane.NORTH, iblockdata.get(BlockStainedGlassPane.EAST)).set(BlockStainedGlassPane.EAST, iblockdata.get(BlockStainedGlassPane.SOUTH)).set(BlockStainedGlassPane.SOUTH, iblockdata.get(BlockStainedGlassPane.WEST)).set(BlockStainedGlassPane.WEST, iblockdata.get(BlockStainedGlassPane.NORTH));

        case 3:
            return iblockdata.set(BlockStainedGlassPane.NORTH, iblockdata.get(BlockStainedGlassPane.WEST)).set(BlockStainedGlassPane.EAST, iblockdata.get(BlockStainedGlassPane.NORTH)).set(BlockStainedGlassPane.SOUTH, iblockdata.get(BlockStainedGlassPane.EAST)).set(BlockStainedGlassPane.WEST, iblockdata.get(BlockStainedGlassPane.SOUTH));

        default:
            return iblockdata;
        }
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        switch (BlockStainedGlassPane.SyntheticClass_1.b[enumblockmirror.ordinal()]) {
        case 1:
            return iblockdata.set(BlockStainedGlassPane.NORTH, iblockdata.get(BlockStainedGlassPane.SOUTH)).set(BlockStainedGlassPane.SOUTH, iblockdata.get(BlockStainedGlassPane.NORTH));

        case 2:
            return iblockdata.set(BlockStainedGlassPane.EAST, iblockdata.get(BlockStainedGlassPane.WEST)).set(BlockStainedGlassPane.WEST, iblockdata.get(BlockStainedGlassPane.EAST));

        default:
            return super.a(iblockdata, enumblockmirror);
        }
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockStainedGlassPane.NORTH, BlockStainedGlassPane.EAST, BlockStainedGlassPane.WEST, BlockStainedGlassPane.SOUTH, BlockStainedGlassPane.COLOR});
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

    static class SyntheticClass_1 {

        static final int[] a;
        static final int[] b = new int[EnumBlockMirror.values().length];

        static {
            try {
                BlockStainedGlassPane.SyntheticClass_1.b[EnumBlockMirror.LEFT_RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                BlockStainedGlassPane.SyntheticClass_1.b[EnumBlockMirror.FRONT_BACK.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            a = new int[EnumBlockRotation.values().length];

            try {
                BlockStainedGlassPane.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_180.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                BlockStainedGlassPane.SyntheticClass_1.a[EnumBlockRotation.COUNTERCLOCKWISE_90.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                BlockStainedGlassPane.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_90.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

        }
    }
}
