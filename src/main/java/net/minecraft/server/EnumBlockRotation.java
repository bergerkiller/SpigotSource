package net.minecraft.server;

public enum EnumBlockRotation {

    NONE("rotate_0"), CLOCKWISE_90("rotate_90"), CLOCKWISE_180("rotate_180"), COUNTERCLOCKWISE_90("rotate_270");

    private final String e;
    private static String[] f = new String[values().length];

    private EnumBlockRotation(String s) {
        this.e = s;
    }

    public EnumBlockRotation a(EnumBlockRotation enumblockrotation) {
        switch (EnumBlockRotation.SyntheticClass_1.a[enumblockrotation.ordinal()]) {
        case 3:
            switch (EnumBlockRotation.SyntheticClass_1.a[this.ordinal()]) {
            case 1:
                return EnumBlockRotation.CLOCKWISE_180;

            case 2:
                return EnumBlockRotation.COUNTERCLOCKWISE_90;

            case 3:
                return EnumBlockRotation.NONE;

            case 4:
                return EnumBlockRotation.CLOCKWISE_90;
            }

        case 4:
            switch (EnumBlockRotation.SyntheticClass_1.a[this.ordinal()]) {
            case 1:
                return EnumBlockRotation.COUNTERCLOCKWISE_90;

            case 2:
                return EnumBlockRotation.NONE;

            case 3:
                return EnumBlockRotation.CLOCKWISE_90;

            case 4:
                return EnumBlockRotation.CLOCKWISE_180;
            }

        case 2:
            switch (EnumBlockRotation.SyntheticClass_1.a[this.ordinal()]) {
            case 1:
                return EnumBlockRotation.CLOCKWISE_90;

            case 2:
                return EnumBlockRotation.CLOCKWISE_180;

            case 3:
                return EnumBlockRotation.COUNTERCLOCKWISE_90;

            case 4:
                return EnumBlockRotation.NONE;
            }

        default:
            return this;
        }
    }

    public EnumDirection a(EnumDirection enumdirection) {
        if (enumdirection.k() == EnumDirection.EnumAxis.Y) {
            return enumdirection;
        } else {
            switch (EnumBlockRotation.SyntheticClass_1.a[this.ordinal()]) {
            case 2:
                return enumdirection.e();

            case 3:
                return enumdirection.opposite();

            case 4:
                return enumdirection.f();

            default:
                return enumdirection;
            }
        }
    }

    public int a(int i, int j) {
        switch (EnumBlockRotation.SyntheticClass_1.a[this.ordinal()]) {
        case 2:
            return (i + j / 4) % j;

        case 3:
            return (i + j / 2) % j;

        case 4:
            return (i + j * 3 / 4) % j;

        default:
            return i;
        }
    }

    static {
        int i = 0;
        EnumBlockRotation[] aenumblockrotation = values();
        int j = aenumblockrotation.length;

        for (int k = 0; k < j; ++k) {
            EnumBlockRotation enumblockrotation = aenumblockrotation[k];

            EnumBlockRotation.f[i++] = enumblockrotation.e;
        }

    }

    static class SyntheticClass_1 {

        static final int[] a = new int[EnumBlockRotation.values().length];

        static {
            try {
                EnumBlockRotation.SyntheticClass_1.a[EnumBlockRotation.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                EnumBlockRotation.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_90.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                EnumBlockRotation.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_180.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                EnumBlockRotation.SyntheticClass_1.a[EnumBlockRotation.COUNTERCLOCKWISE_90.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

        }
    }
}
