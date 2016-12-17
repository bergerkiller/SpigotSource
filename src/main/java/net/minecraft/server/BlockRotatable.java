package net.minecraft.server;

public class BlockRotatable extends Block {

    public static final BlockStateEnum<EnumDirection.EnumAxis> AXIS = BlockStateEnum.of("axis", EnumDirection.EnumAxis.class);

    protected BlockRotatable(Material material) {
        super(material, material.r());
    }

    protected BlockRotatable(Material material, MaterialMapColor materialmapcolor) {
        super(material, materialmapcolor);
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch (BlockRotatable.SyntheticClass_1.b[enumblockrotation.ordinal()]) {
        case 1:
        case 2:
            switch (BlockRotatable.SyntheticClass_1.a[((EnumDirection.EnumAxis) iblockdata.get(BlockRotatable.AXIS)).ordinal()]) {
            case 1:
                return iblockdata.set(BlockRotatable.AXIS, EnumDirection.EnumAxis.Z);

            case 2:
                return iblockdata.set(BlockRotatable.AXIS, EnumDirection.EnumAxis.X);

            default:
                return iblockdata;
            }

        default:
            return iblockdata;
        }
    }

    public IBlockData fromLegacyData(int i) {
        EnumDirection.EnumAxis enumdirection_enumaxis = EnumDirection.EnumAxis.Y;
        int j = i & 12;

        if (j == 4) {
            enumdirection_enumaxis = EnumDirection.EnumAxis.X;
        } else if (j == 8) {
            enumdirection_enumaxis = EnumDirection.EnumAxis.Z;
        }

        return this.getBlockData().set(BlockRotatable.AXIS, enumdirection_enumaxis);
    }

    public int toLegacyData(IBlockData iblockdata) {
        int i = 0;
        EnumDirection.EnumAxis enumdirection_enumaxis = (EnumDirection.EnumAxis) iblockdata.get(BlockRotatable.AXIS);

        if (enumdirection_enumaxis == EnumDirection.EnumAxis.X) {
            i |= 4;
        } else if (enumdirection_enumaxis == EnumDirection.EnumAxis.Z) {
            i |= 8;
        }

        return i;
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockRotatable.AXIS});
    }

    protected ItemStack u(IBlockData iblockdata) {
        return new ItemStack(Item.getItemOf(this));
    }

    public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
        return super.getPlacedState(world, blockposition, enumdirection, f, f1, f2, i, entityliving).set(BlockRotatable.AXIS, enumdirection.k());
    }

    static class SyntheticClass_1 {

        static final int[] a;
        static final int[] b = new int[EnumBlockRotation.values().length];

        static {
            try {
                BlockRotatable.SyntheticClass_1.b[EnumBlockRotation.COUNTERCLOCKWISE_90.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                BlockRotatable.SyntheticClass_1.b[EnumBlockRotation.CLOCKWISE_90.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            a = new int[EnumDirection.EnumAxis.values().length];

            try {
                BlockRotatable.SyntheticClass_1.a[EnumDirection.EnumAxis.X.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                BlockRotatable.SyntheticClass_1.a[EnumDirection.EnumAxis.Z.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

        }
    }
}
