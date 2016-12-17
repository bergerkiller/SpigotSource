package net.minecraft.server;

import java.util.Iterator;

public class BlockLadder extends Block {

    public static final BlockStateDirection FACING = BlockFacingHorizontal.FACING;
    protected static final AxisAlignedBB b = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);
    protected static final AxisAlignedBB c = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB d = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
    protected static final AxisAlignedBB e = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);

    protected BlockLadder() {
        super(Material.ORIENTABLE);
        this.w(this.blockStateList.getBlockData().set(BlockLadder.FACING, EnumDirection.NORTH));
        this.a(CreativeModeTab.c);
    }

    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        switch (BlockLadder.SyntheticClass_1.a[((EnumDirection) iblockdata.get(BlockLadder.FACING)).ordinal()]) {
        case 1:
            return BlockLadder.e;

        case 2:
            return BlockLadder.d;

        case 3:
            return BlockLadder.c;

        case 4:
        default:
            return BlockLadder.b;
        }
    }

    public boolean b(IBlockData iblockdata) {
        return false;
    }

    public boolean c(IBlockData iblockdata) {
        return false;
    }

    public boolean canPlace(World world, BlockPosition blockposition) {
        return world.getType(blockposition.west()).l() ? true : (world.getType(blockposition.east()).l() ? true : (world.getType(blockposition.north()).l() ? true : world.getType(blockposition.south()).l()));
    }

    public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
        if (enumdirection.k().c() && this.a(world, blockposition, enumdirection)) {
            return this.getBlockData().set(BlockLadder.FACING, enumdirection);
        } else {
            Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

            EnumDirection enumdirection1;

            do {
                if (!iterator.hasNext()) {
                    return this.getBlockData();
                }

                enumdirection1 = (EnumDirection) iterator.next();
            } while (!this.a(world, blockposition, enumdirection1));

            return this.getBlockData().set(BlockLadder.FACING, enumdirection1);
        }
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
        EnumDirection enumdirection = (EnumDirection) iblockdata.get(BlockLadder.FACING);

        if (!this.a(world, blockposition, enumdirection)) {
            this.b(world, blockposition, iblockdata, 0);
            world.setAir(blockposition);
        }

        super.a(iblockdata, world, blockposition, block);
    }

    protected boolean a(World world, BlockPosition blockposition, EnumDirection enumdirection) {
        return world.getType(blockposition.shift(enumdirection.opposite())).l();
    }

    public IBlockData fromLegacyData(int i) {
        EnumDirection enumdirection = EnumDirection.fromType1(i);

        if (enumdirection.k() == EnumDirection.EnumAxis.Y) {
            enumdirection = EnumDirection.NORTH;
        }

        return this.getBlockData().set(BlockLadder.FACING, enumdirection);
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((EnumDirection) iblockdata.get(BlockLadder.FACING)).a();
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return iblockdata.set(BlockLadder.FACING, enumblockrotation.a((EnumDirection) iblockdata.get(BlockLadder.FACING)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection) iblockdata.get(BlockLadder.FACING)));
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockLadder.FACING});
    }

    static class SyntheticClass_1 {

        static final int[] a = new int[EnumDirection.values().length];

        static {
            try {
                BlockLadder.SyntheticClass_1.a[EnumDirection.NORTH.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                BlockLadder.SyntheticClass_1.a[EnumDirection.SOUTH.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                BlockLadder.SyntheticClass_1.a[EnumDirection.WEST.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                BlockLadder.SyntheticClass_1.a[EnumDirection.EAST.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

        }
    }
}
