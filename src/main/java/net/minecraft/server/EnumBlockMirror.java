package net.minecraft.server;

public enum EnumBlockMirror {

    NONE("no_mirror"), LEFT_RIGHT("mirror_left_right"), FRONT_BACK("mirror_front_back");

    private final String d;
    private static String[] e = new String[values().length];

    private EnumBlockMirror(String s) {
        this.d = s;
    }

    public int a(int i, int j) {
        int k = j / 2;
        int l = i > k ? i - j : i;

        switch (EnumBlockMirror.SyntheticClass_1.a[this.ordinal()]) {
        case 1:
            return (j - l) % j;

        case 2:
            return (k - l + j) % j;

        default:
            return i;
        }
    }

    public EnumBlockRotation a(EnumDirection enumdirection) {
        EnumDirection.EnumAxis enumdirection_enumaxis = enumdirection.k();

        return (this != EnumBlockMirror.LEFT_RIGHT || enumdirection_enumaxis != EnumDirection.EnumAxis.Z) && (this != EnumBlockMirror.FRONT_BACK || enumdirection_enumaxis != EnumDirection.EnumAxis.X) ? EnumBlockRotation.NONE : EnumBlockRotation.CLOCKWISE_180;
    }

    public EnumDirection b(EnumDirection enumdirection) {
        switch (EnumBlockMirror.SyntheticClass_1.a[this.ordinal()]) {
        case 1:
            if (enumdirection == EnumDirection.WEST) {
                return EnumDirection.EAST;
            } else {
                if (enumdirection == EnumDirection.EAST) {
                    return EnumDirection.WEST;
                }

                return enumdirection;
            }

        case 2:
            if (enumdirection == EnumDirection.NORTH) {
                return EnumDirection.SOUTH;
            } else {
                if (enumdirection == EnumDirection.SOUTH) {
                    return EnumDirection.NORTH;
                }

                return enumdirection;
            }

        default:
            return enumdirection;
        }
    }

    static {
        int i = 0;
        EnumBlockMirror[] aenumblockmirror = values();
        int j = aenumblockmirror.length;

        for (int k = 0; k < j; ++k) {
            EnumBlockMirror enumblockmirror = aenumblockmirror[k];

            EnumBlockMirror.e[i++] = enumblockmirror.d;
        }

    }

    static class SyntheticClass_1 {

        static final int[] a = new int[EnumBlockMirror.values().length];

        static {
            try {
                EnumBlockMirror.SyntheticClass_1.a[EnumBlockMirror.LEFT_RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                EnumBlockMirror.SyntheticClass_1.a[EnumBlockMirror.FRONT_BACK.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

        }
    }
}
