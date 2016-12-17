package net.minecraft.server;

import java.util.Iterator;

public abstract class BlockLogAbstract extends BlockRotatable {

    public static final BlockStateEnum<BlockLogAbstract.EnumLogRotation> AXIS = BlockStateEnum.of("axis", BlockLogAbstract.EnumLogRotation.class);

    public BlockLogAbstract() {
        super(Material.WOOD);
        this.a(CreativeModeTab.b);
        this.c(2.0F);
        this.a(SoundEffectType.a);
    }

    public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
        byte b0 = 4;
        int i = b0 + 1;

        if (world.areChunksLoadedBetween(blockposition.a(-i, -i, -i), blockposition.a(i, i, i))) {
            Iterator iterator = BlockPosition.a(blockposition.a(-b0, -b0, -b0), blockposition.a(b0, b0, b0)).iterator();

            while (iterator.hasNext()) {
                BlockPosition blockposition1 = (BlockPosition) iterator.next();
                IBlockData iblockdata1 = world.getType(blockposition1);

                if (iblockdata1.getMaterial() == Material.LEAVES && !((Boolean) iblockdata1.get(BlockLeaves.CHECK_DECAY)).booleanValue()) {
                    world.setTypeAndData(blockposition1, iblockdata1.set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(true)), 4);
                }
            }

        }
    }

    public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
        return this.fromLegacyData(i).set(BlockLogAbstract.AXIS, BlockLogAbstract.EnumLogRotation.a(enumdirection.k()));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch (BlockLogAbstract.SyntheticClass_1.b[enumblockrotation.ordinal()]) {
        case 1:
        case 2:
            switch (BlockLogAbstract.SyntheticClass_1.a[((BlockLogAbstract.EnumLogRotation) iblockdata.get(BlockLogAbstract.AXIS)).ordinal()]) {
            case 1:
                return iblockdata.set(BlockLogAbstract.AXIS, BlockLogAbstract.EnumLogRotation.Z);

            case 2:
                return iblockdata.set(BlockLogAbstract.AXIS, BlockLogAbstract.EnumLogRotation.X);

            default:
                return iblockdata;
            }

        default:
            return iblockdata;
        }
    }

    static class SyntheticClass_1 {

        static final int[] a;
        static final int[] b;
        static final int[] c = new int[EnumDirection.EnumAxis.values().length];

        static {
            try {
                BlockLogAbstract.SyntheticClass_1.c[EnumDirection.EnumAxis.X.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                BlockLogAbstract.SyntheticClass_1.c[EnumDirection.EnumAxis.Y.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                BlockLogAbstract.SyntheticClass_1.c[EnumDirection.EnumAxis.Z.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            b = new int[EnumBlockRotation.values().length];

            try {
                BlockLogAbstract.SyntheticClass_1.b[EnumBlockRotation.COUNTERCLOCKWISE_90.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                BlockLogAbstract.SyntheticClass_1.b[EnumBlockRotation.CLOCKWISE_90.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            a = new int[BlockLogAbstract.EnumLogRotation.values().length];

            try {
                BlockLogAbstract.SyntheticClass_1.a[BlockLogAbstract.EnumLogRotation.X.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                BlockLogAbstract.SyntheticClass_1.a[BlockLogAbstract.EnumLogRotation.Z.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

        }
    }

    public static enum EnumLogRotation implements INamable {

        X("x"), Y("y"), Z("z"), NONE("none");

        private final String e;

        private EnumLogRotation(String s) {
            this.e = s;
        }

        public String toString() {
            return this.e;
        }

        public static BlockLogAbstract.EnumLogRotation a(EnumDirection.EnumAxis enumdirection_enumaxis) {
            switch (BlockLogAbstract.SyntheticClass_1.c[enumdirection_enumaxis.ordinal()]) {
            case 1:
                return BlockLogAbstract.EnumLogRotation.X;

            case 2:
                return BlockLogAbstract.EnumLogRotation.Y;

            case 3:
                return BlockLogAbstract.EnumLogRotation.Z;

            default:
                return BlockLogAbstract.EnumLogRotation.NONE;
            }
        }

        public String getName() {
            return this.e;
        }
    }
}
