package net.minecraft.server;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class BlockThin extends Block {

    public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
    public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
    public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
    public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
    protected static final AxisAlignedBB[] f = new AxisAlignedBB[] { new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 1.0D, 0.5625D), new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 0.5625D, 1.0D, 0.5625D), new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 0.5625D, 1.0D, 1.0D), new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 0.5625D), new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5625D, 1.0D, 0.5625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D), new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D), new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D), new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5625D), new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
    private final boolean a;

    protected BlockThin(Material material, boolean flag) {
        super(material);
        this.w(this.blockStateList.getBlockData().set(BlockThin.NORTH, Boolean.valueOf(false)).set(BlockThin.EAST, Boolean.valueOf(false)).set(BlockThin.SOUTH, Boolean.valueOf(false)).set(BlockThin.WEST, Boolean.valueOf(false)));
        this.a = flag;
        this.a(CreativeModeTab.c);
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, @Nullable Entity entity) {
        iblockdata = this.updateState(iblockdata, world, blockposition);
        a(blockposition, axisalignedbb, list, BlockThin.f[0]);
        if (((Boolean) iblockdata.get(BlockThin.NORTH)).booleanValue()) {
            a(blockposition, axisalignedbb, list, BlockThin.f[a(EnumDirection.NORTH)]);
        }

        if (((Boolean) iblockdata.get(BlockThin.SOUTH)).booleanValue()) {
            a(blockposition, axisalignedbb, list, BlockThin.f[a(EnumDirection.SOUTH)]);
        }

        if (((Boolean) iblockdata.get(BlockThin.EAST)).booleanValue()) {
            a(blockposition, axisalignedbb, list, BlockThin.f[a(EnumDirection.EAST)]);
        }

        if (((Boolean) iblockdata.get(BlockThin.WEST)).booleanValue()) {
            a(blockposition, axisalignedbb, list, BlockThin.f[a(EnumDirection.WEST)]);
        }

    }

    private static int a(EnumDirection enumdirection) {
        return 1 << enumdirection.get2DRotationValue();
    }

    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        iblockdata = this.updateState(iblockdata, iblockaccess, blockposition);
        return BlockThin.f[i(iblockdata)];
    }

    private static int i(IBlockData iblockdata) {
        int i = 0;

        if (((Boolean) iblockdata.get(BlockThin.NORTH)).booleanValue()) {
            i |= a(EnumDirection.NORTH);
        }

        if (((Boolean) iblockdata.get(BlockThin.EAST)).booleanValue()) {
            i |= a(EnumDirection.EAST);
        }

        if (((Boolean) iblockdata.get(BlockThin.SOUTH)).booleanValue()) {
            i |= a(EnumDirection.SOUTH);
        }

        if (((Boolean) iblockdata.get(BlockThin.WEST)).booleanValue()) {
            i |= a(EnumDirection.WEST);
        }

        return i;
    }

    public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockdata.set(BlockThin.NORTH, Boolean.valueOf(this.c(iblockaccess.getType(blockposition.north()).getBlock()))).set(BlockThin.SOUTH, Boolean.valueOf(this.c(iblockaccess.getType(blockposition.south()).getBlock()))).set(BlockThin.WEST, Boolean.valueOf(this.c(iblockaccess.getType(blockposition.west()).getBlock()))).set(BlockThin.EAST, Boolean.valueOf(this.c(iblockaccess.getType(blockposition.east()).getBlock())));
    }

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return !this.a ? null : super.getDropType(iblockdata, random, i);
    }

    public boolean b(IBlockData iblockdata) {
        return false;
    }

    public boolean c(IBlockData iblockdata) {
        return false;
    }

    public final boolean c(Block block) {
        return block.getBlockData().h() || block == this || block == Blocks.GLASS || block == Blocks.STAINED_GLASS || block == Blocks.STAINED_GLASS_PANE || block instanceof BlockThin;
    }

    protected boolean o() {
        return true;
    }

    public int toLegacyData(IBlockData iblockdata) {
        return 0;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch (BlockThin.SyntheticClass_1.a[enumblockrotation.ordinal()]) {
        case 1:
            return iblockdata.set(BlockThin.NORTH, iblockdata.get(BlockThin.SOUTH)).set(BlockThin.EAST, iblockdata.get(BlockThin.WEST)).set(BlockThin.SOUTH, iblockdata.get(BlockThin.NORTH)).set(BlockThin.WEST, iblockdata.get(BlockThin.EAST));

        case 2:
            return iblockdata.set(BlockThin.NORTH, iblockdata.get(BlockThin.EAST)).set(BlockThin.EAST, iblockdata.get(BlockThin.SOUTH)).set(BlockThin.SOUTH, iblockdata.get(BlockThin.WEST)).set(BlockThin.WEST, iblockdata.get(BlockThin.NORTH));

        case 3:
            return iblockdata.set(BlockThin.NORTH, iblockdata.get(BlockThin.WEST)).set(BlockThin.EAST, iblockdata.get(BlockThin.NORTH)).set(BlockThin.SOUTH, iblockdata.get(BlockThin.EAST)).set(BlockThin.WEST, iblockdata.get(BlockThin.SOUTH));

        default:
            return iblockdata;
        }
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        switch (BlockThin.SyntheticClass_1.b[enumblockmirror.ordinal()]) {
        case 1:
            return iblockdata.set(BlockThin.NORTH, iblockdata.get(BlockThin.SOUTH)).set(BlockThin.SOUTH, iblockdata.get(BlockThin.NORTH));

        case 2:
            return iblockdata.set(BlockThin.EAST, iblockdata.get(BlockThin.WEST)).set(BlockThin.WEST, iblockdata.get(BlockThin.EAST));

        default:
            return super.a(iblockdata, enumblockmirror);
        }
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockThin.NORTH, BlockThin.EAST, BlockThin.WEST, BlockThin.SOUTH});
    }

    static class SyntheticClass_1 {

        static final int[] a;
        static final int[] b = new int[EnumBlockMirror.values().length];

        static {
            try {
                BlockThin.SyntheticClass_1.b[EnumBlockMirror.LEFT_RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                BlockThin.SyntheticClass_1.b[EnumBlockMirror.FRONT_BACK.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            a = new int[EnumBlockRotation.values().length];

            try {
                BlockThin.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_180.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                BlockThin.SyntheticClass_1.a[EnumBlockRotation.COUNTERCLOCKWISE_90.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                BlockThin.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_90.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

        }
    }
}
