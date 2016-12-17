package net.minecraft.server;

import java.util.List;
import javax.annotation.Nullable;

public class BlockFence extends Block {

    public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
    public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
    public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
    public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
    protected static final AxisAlignedBB[] e = new AxisAlignedBB[] { new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
    public static final AxisAlignedBB f = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.5D, 0.625D);
    public static final AxisAlignedBB g = new AxisAlignedBB(0.375D, 0.0D, 0.625D, 0.625D, 1.5D, 1.0D);
    public static final AxisAlignedBB B = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.375D, 1.5D, 0.625D);
    public static final AxisAlignedBB C = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.5D, 0.375D);
    public static final AxisAlignedBB D = new AxisAlignedBB(0.625D, 0.0D, 0.375D, 1.0D, 1.5D, 0.625D);

    public BlockFence(Material material, MaterialMapColor materialmapcolor) {
        super(material, materialmapcolor);
        this.w(this.blockStateList.getBlockData().set(BlockFence.NORTH, Boolean.valueOf(false)).set(BlockFence.EAST, Boolean.valueOf(false)).set(BlockFence.SOUTH, Boolean.valueOf(false)).set(BlockFence.WEST, Boolean.valueOf(false)));
        this.a(CreativeModeTab.c);
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, @Nullable Entity entity) {
        iblockdata = iblockdata.b((IBlockAccess) world, blockposition);
        a(blockposition, axisalignedbb, list, BlockFence.f);
        if (((Boolean) iblockdata.get(BlockFence.NORTH)).booleanValue()) {
            a(blockposition, axisalignedbb, list, BlockFence.C);
        }

        if (((Boolean) iblockdata.get(BlockFence.EAST)).booleanValue()) {
            a(blockposition, axisalignedbb, list, BlockFence.D);
        }

        if (((Boolean) iblockdata.get(BlockFence.SOUTH)).booleanValue()) {
            a(blockposition, axisalignedbb, list, BlockFence.g);
        }

        if (((Boolean) iblockdata.get(BlockFence.WEST)).booleanValue()) {
            a(blockposition, axisalignedbb, list, BlockFence.B);
        }

    }

    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        iblockdata = this.updateState(iblockdata, iblockaccess, blockposition);
        return BlockFence.e[i(iblockdata)];
    }

    private static int i(IBlockData iblockdata) {
        int i = 0;

        if (((Boolean) iblockdata.get(BlockFence.NORTH)).booleanValue()) {
            i |= 1 << EnumDirection.NORTH.get2DRotationValue();
        }

        if (((Boolean) iblockdata.get(BlockFence.EAST)).booleanValue()) {
            i |= 1 << EnumDirection.EAST.get2DRotationValue();
        }

        if (((Boolean) iblockdata.get(BlockFence.SOUTH)).booleanValue()) {
            i |= 1 << EnumDirection.SOUTH.get2DRotationValue();
        }

        if (((Boolean) iblockdata.get(BlockFence.WEST)).booleanValue()) {
            i |= 1 << EnumDirection.WEST.get2DRotationValue();
        }

        return i;
    }

    public boolean b(IBlockData iblockdata) {
        return false;
    }

    public boolean c(IBlockData iblockdata) {
        return false;
    }

    public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return false;
    }

    public boolean c(IBlockAccess iblockaccess, BlockPosition blockposition) {
        IBlockData iblockdata = iblockaccess.getType(blockposition);
        Block block = iblockdata.getBlock();

        return block == Blocks.BARRIER ? false : ((!(block instanceof BlockFence) || block.material != this.material) && !(block instanceof BlockFenceGate) ? (block.material.k() && iblockdata.h() ? block.material != Material.PUMPKIN : false) : true);
    }

    public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumHand enumhand, @Nullable ItemStack itemstack, EnumDirection enumdirection, float f, float f1, float f2) {
        return world.isClientSide ? true : ItemLeash.a(entityhuman, world, blockposition);
    }

    public int toLegacyData(IBlockData iblockdata) {
        return 0;
    }

    public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockdata.set(BlockFence.NORTH, Boolean.valueOf(this.c(iblockaccess, blockposition.north()))).set(BlockFence.EAST, Boolean.valueOf(this.c(iblockaccess, blockposition.east()))).set(BlockFence.SOUTH, Boolean.valueOf(this.c(iblockaccess, blockposition.south()))).set(BlockFence.WEST, Boolean.valueOf(this.c(iblockaccess, blockposition.west())));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch (BlockFence.SyntheticClass_1.a[enumblockrotation.ordinal()]) {
        case 1:
            return iblockdata.set(BlockFence.NORTH, iblockdata.get(BlockFence.SOUTH)).set(BlockFence.EAST, iblockdata.get(BlockFence.WEST)).set(BlockFence.SOUTH, iblockdata.get(BlockFence.NORTH)).set(BlockFence.WEST, iblockdata.get(BlockFence.EAST));

        case 2:
            return iblockdata.set(BlockFence.NORTH, iblockdata.get(BlockFence.EAST)).set(BlockFence.EAST, iblockdata.get(BlockFence.SOUTH)).set(BlockFence.SOUTH, iblockdata.get(BlockFence.WEST)).set(BlockFence.WEST, iblockdata.get(BlockFence.NORTH));

        case 3:
            return iblockdata.set(BlockFence.NORTH, iblockdata.get(BlockFence.WEST)).set(BlockFence.EAST, iblockdata.get(BlockFence.NORTH)).set(BlockFence.SOUTH, iblockdata.get(BlockFence.EAST)).set(BlockFence.WEST, iblockdata.get(BlockFence.SOUTH));

        default:
            return iblockdata;
        }
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        switch (BlockFence.SyntheticClass_1.b[enumblockmirror.ordinal()]) {
        case 1:
            return iblockdata.set(BlockFence.NORTH, iblockdata.get(BlockFence.SOUTH)).set(BlockFence.SOUTH, iblockdata.get(BlockFence.NORTH));

        case 2:
            return iblockdata.set(BlockFence.EAST, iblockdata.get(BlockFence.WEST)).set(BlockFence.WEST, iblockdata.get(BlockFence.EAST));

        default:
            return super.a(iblockdata, enumblockmirror);
        }
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockFence.NORTH, BlockFence.EAST, BlockFence.WEST, BlockFence.SOUTH});
    }

    static class SyntheticClass_1 {

        static final int[] a;
        static final int[] b = new int[EnumBlockMirror.values().length];

        static {
            try {
                BlockFence.SyntheticClass_1.b[EnumBlockMirror.LEFT_RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                BlockFence.SyntheticClass_1.b[EnumBlockMirror.FRONT_BACK.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            a = new int[EnumBlockRotation.values().length];

            try {
                BlockFence.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_180.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                BlockFence.SyntheticClass_1.a[EnumBlockRotation.COUNTERCLOCKWISE_90.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                BlockFence.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_90.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

        }
    }
}
