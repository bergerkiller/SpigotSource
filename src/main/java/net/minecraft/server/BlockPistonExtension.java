package net.minecraft.server;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class BlockPistonExtension extends BlockDirectional {

    public static final BlockStateEnum<BlockPistonExtension.EnumPistonType> TYPE = BlockStateEnum.of("type", BlockPistonExtension.EnumPistonType.class);
    public static final BlockStateBoolean SHORT = BlockStateBoolean.of("short");
    protected static final AxisAlignedBB c = new AxisAlignedBB(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB d = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);
    protected static final AxisAlignedBB e = new AxisAlignedBB(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB f = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
    protected static final AxisAlignedBB g = new AxisAlignedBB(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB B = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
    protected static final AxisAlignedBB C = new AxisAlignedBB(0.375D, -0.25D, 0.375D, 0.625D, 0.75D, 0.625D);
    protected static final AxisAlignedBB D = new AxisAlignedBB(0.375D, 0.25D, 0.375D, 0.625D, 1.25D, 0.625D);
    protected static final AxisAlignedBB E = new AxisAlignedBB(0.375D, 0.375D, -0.25D, 0.625D, 0.625D, 0.75D);
    protected static final AxisAlignedBB F = new AxisAlignedBB(0.375D, 0.375D, 0.25D, 0.625D, 0.625D, 1.25D);
    protected static final AxisAlignedBB G = new AxisAlignedBB(-0.25D, 0.375D, 0.375D, 0.75D, 0.625D, 0.625D);
    protected static final AxisAlignedBB I = new AxisAlignedBB(0.25D, 0.375D, 0.375D, 1.25D, 0.625D, 0.625D);

    public BlockPistonExtension() {
        super(Material.PISTON);
        this.w(this.blockStateList.getBlockData().set(BlockPistonExtension.FACING, EnumDirection.NORTH).set(BlockPistonExtension.TYPE, BlockPistonExtension.EnumPistonType.DEFAULT).set(BlockPistonExtension.SHORT, Boolean.valueOf(false)));
        this.a(SoundEffectType.d);
        this.c(0.5F);
    }

    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        switch (BlockPistonExtension.SyntheticClass_1.a[((EnumDirection) iblockdata.get(BlockPistonExtension.FACING)).ordinal()]) {
        case 1:
        default:
            return BlockPistonExtension.B;

        case 2:
            return BlockPistonExtension.g;

        case 3:
            return BlockPistonExtension.f;

        case 4:
            return BlockPistonExtension.e;

        case 5:
            return BlockPistonExtension.d;

        case 6:
            return BlockPistonExtension.c;
        }
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, @Nullable Entity entity) {
        a(blockposition, axisalignedbb, list, iblockdata.c(world, blockposition));
        a(blockposition, axisalignedbb, list, this.i(iblockdata));
    }

    private AxisAlignedBB i(IBlockData iblockdata) {
        switch (BlockPistonExtension.SyntheticClass_1.a[((EnumDirection) iblockdata.get(BlockPistonExtension.FACING)).ordinal()]) {
        case 1:
        default:
            return BlockPistonExtension.D;

        case 2:
            return BlockPistonExtension.C;

        case 3:
            return BlockPistonExtension.F;

        case 4:
            return BlockPistonExtension.E;

        case 5:
            return BlockPistonExtension.I;

        case 6:
            return BlockPistonExtension.G;
        }
    }

    public boolean k(IBlockData iblockdata) {
        return iblockdata.get(BlockPistonExtension.FACING) == EnumDirection.UP;
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
        if (entityhuman.abilities.canInstantlyBuild) {
            BlockPosition blockposition1 = blockposition.shift(((EnumDirection) iblockdata.get(BlockPistonExtension.FACING)).opposite());
            Block block = world.getType(blockposition1).getBlock();

            if (block == Blocks.PISTON || block == Blocks.STICKY_PISTON) {
                world.setAir(blockposition1);
            }
        }

        super.a(world, blockposition, iblockdata, entityhuman);
    }

    public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
        super.remove(world, blockposition, iblockdata);
        EnumDirection enumdirection = ((EnumDirection) iblockdata.get(BlockPistonExtension.FACING)).opposite();

        blockposition = blockposition.shift(enumdirection);
        IBlockData iblockdata1 = world.getType(blockposition);

        if ((iblockdata1.getBlock() == Blocks.PISTON || iblockdata1.getBlock() == Blocks.STICKY_PISTON) && ((Boolean) iblockdata1.get(BlockPiston.EXTENDED)).booleanValue()) {
            iblockdata1.getBlock().b(world, blockposition, iblockdata1, 0);
            world.setAir(blockposition);
        }

    }

    public boolean b(IBlockData iblockdata) {
        return false;
    }

    public boolean c(IBlockData iblockdata) {
        return false;
    }

    public boolean canPlace(World world, BlockPosition blockposition) {
        return false;
    }

    public boolean canPlace(World world, BlockPosition blockposition, EnumDirection enumdirection) {
        return false;
    }

    public int a(Random random) {
        return 0;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
        EnumDirection enumdirection = (EnumDirection) iblockdata.get(BlockPistonExtension.FACING);
        BlockPosition blockposition1 = blockposition.shift(enumdirection.opposite());
        IBlockData iblockdata1 = world.getType(blockposition1);

        if (iblockdata1.getBlock() != Blocks.PISTON && iblockdata1.getBlock() != Blocks.STICKY_PISTON) {
            world.setAir(blockposition);
        } else {
            iblockdata1.doPhysics(world, blockposition1, block);
        }

    }

    @Nullable
    public static EnumDirection e(int i) {
        int j = i & 7;

        return j > 5 ? null : EnumDirection.fromType1(j);
    }

    public ItemStack a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return new ItemStack(iblockdata.get(BlockPistonExtension.TYPE) == BlockPistonExtension.EnumPistonType.STICKY ? Blocks.STICKY_PISTON : Blocks.PISTON);
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockPistonExtension.FACING, e(i)).set(BlockPistonExtension.TYPE, (i & 8) > 0 ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
    }

    public int toLegacyData(IBlockData iblockdata) {
        byte b0 = 0;
        int i = b0 | ((EnumDirection) iblockdata.get(BlockPistonExtension.FACING)).a();

        if (iblockdata.get(BlockPistonExtension.TYPE) == BlockPistonExtension.EnumPistonType.STICKY) {
            i |= 8;
        }

        return i;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return iblockdata.set(BlockPistonExtension.FACING, enumblockrotation.a((EnumDirection) iblockdata.get(BlockPistonExtension.FACING)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection) iblockdata.get(BlockPistonExtension.FACING)));
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockPistonExtension.FACING, BlockPistonExtension.TYPE, BlockPistonExtension.SHORT});
    }

    static class SyntheticClass_1 {

        static final int[] a = new int[EnumDirection.values().length];

        static {
            try {
                BlockPistonExtension.SyntheticClass_1.a[EnumDirection.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                BlockPistonExtension.SyntheticClass_1.a[EnumDirection.UP.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                BlockPistonExtension.SyntheticClass_1.a[EnumDirection.NORTH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                BlockPistonExtension.SyntheticClass_1.a[EnumDirection.SOUTH.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                BlockPistonExtension.SyntheticClass_1.a[EnumDirection.WEST.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                BlockPistonExtension.SyntheticClass_1.a[EnumDirection.EAST.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

        }
    }

    public static enum EnumPistonType implements INamable {

        DEFAULT("normal"), STICKY("sticky");

        private final String c;

        private EnumPistonType(String s) {
            this.c = s;
        }

        public String toString() {
            return this.c;
        }

        public String getName() {
            return this.c;
        }
    }
}
