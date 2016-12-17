package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public abstract class StructurePiece {

    protected StructureBoundingBox l;
    @Nullable
    private EnumDirection a;
    private EnumBlockMirror b;
    private EnumBlockRotation c;
    protected int m;

    public StructurePiece() {}

    protected StructurePiece(int i) {
        this.m = i;
    }

    public final NBTTagCompound b() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        nbttagcompound.setString("id", WorldGenFactory.a(this));
        nbttagcompound.set("BB", this.l.g());
        EnumDirection enumdirection = this.e();

        nbttagcompound.setInt("O", enumdirection == null ? -1 : enumdirection.get2DRotationValue());
        nbttagcompound.setInt("GD", this.m);
        this.a(nbttagcompound);
        return nbttagcompound;
    }

    protected abstract void a(NBTTagCompound nbttagcompound);

    public void a(World world, NBTTagCompound nbttagcompound) {
        if (nbttagcompound.hasKey("BB")) {
            this.l = new StructureBoundingBox(nbttagcompound.getIntArray("BB"));
        }

        int i = nbttagcompound.getInt("O");

        this.a(i == -1 ? null : EnumDirection.fromType2(i));
        this.m = nbttagcompound.getInt("GD");
        this.b(nbttagcompound);
    }

    protected abstract void b(NBTTagCompound nbttagcompound);

    public void a(StructurePiece structurepiece, List<StructurePiece> list, Random random) {}

    public abstract boolean a(World world, Random random, StructureBoundingBox structureboundingbox);

    public StructureBoundingBox c() {
        return this.l;
    }

    public int d() {
        return this.m;
    }

    public static StructurePiece a(List<StructurePiece> list, StructureBoundingBox structureboundingbox) {
        Iterator iterator = list.iterator();

        StructurePiece structurepiece;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            structurepiece = (StructurePiece) iterator.next();
        } while (structurepiece.c() == null || !structurepiece.c().a(structureboundingbox));

        return structurepiece;
    }

    public BlockPosition a() {
        return new BlockPosition(this.l.f());
    }

    protected boolean a(World world, StructureBoundingBox structureboundingbox) {
        int i = Math.max(this.l.a - 1, structureboundingbox.a);
        int j = Math.max(this.l.b - 1, structureboundingbox.b);
        int k = Math.max(this.l.c - 1, structureboundingbox.c);
        int l = Math.min(this.l.d + 1, structureboundingbox.d);
        int i1 = Math.min(this.l.e + 1, structureboundingbox.e);
        int j1 = Math.min(this.l.f + 1, structureboundingbox.f);
        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

        int k1;
        int l1;

        for (k1 = i; k1 <= l; ++k1) {
            for (l1 = k; l1 <= j1; ++l1) {
                if (world.getType(blockposition_mutableblockposition.c(k1, j, l1)).getMaterial().isLiquid()) {
                    return true;
                }

                if (world.getType(blockposition_mutableblockposition.c(k1, i1, l1)).getMaterial().isLiquid()) {
                    return true;
                }
            }
        }

        for (k1 = i; k1 <= l; ++k1) {
            for (l1 = j; l1 <= i1; ++l1) {
                if (world.getType(blockposition_mutableblockposition.c(k1, l1, k)).getMaterial().isLiquid()) {
                    return true;
                }

                if (world.getType(blockposition_mutableblockposition.c(k1, l1, j1)).getMaterial().isLiquid()) {
                    return true;
                }
            }
        }

        for (k1 = k; k1 <= j1; ++k1) {
            for (l1 = j; l1 <= i1; ++l1) {
                if (world.getType(blockposition_mutableblockposition.c(i, l1, k1)).getMaterial().isLiquid()) {
                    return true;
                }

                if (world.getType(blockposition_mutableblockposition.c(l, l1, k1)).getMaterial().isLiquid()) {
                    return true;
                }
            }
        }

        return false;
    }

    protected int a(int i, int j) {
        EnumDirection enumdirection = this.e();

        if (enumdirection == null) {
            return i;
        } else {
            switch (StructurePiece.SyntheticClass_1.a[enumdirection.ordinal()]) {
            case 1:
            case 2:
                return this.l.a + i;

            case 3:
                return this.l.d - j;

            case 4:
                return this.l.a + j;

            default:
                return i;
            }
        }
    }

    protected int d(int i) {
        return this.e() == null ? i : i + this.l.b;
    }

    protected int b(int i, int j) {
        EnumDirection enumdirection = this.e();

        if (enumdirection == null) {
            return j;
        } else {
            switch (StructurePiece.SyntheticClass_1.a[enumdirection.ordinal()]) {
            case 1:
                return this.l.f - j;

            case 2:
                return this.l.c + j;

            case 3:
            case 4:
                return this.l.c + i;

            default:
                return j;
            }
        }
    }

    protected void a(World world, IBlockData iblockdata, int i, int j, int k, StructureBoundingBox structureboundingbox) {
        BlockPosition blockposition = new BlockPosition(this.a(i, k), this.d(j), this.b(i, k));

        if (structureboundingbox.b((BaseBlockPosition) blockposition)) {
            if (this.b != EnumBlockMirror.NONE) {
                iblockdata = iblockdata.a(this.b);
            }

            if (this.c != EnumBlockRotation.NONE) {
                iblockdata = iblockdata.a(this.c);
            }

            world.setTypeAndData(blockposition, iblockdata, 2);
        }
    }

    protected IBlockData a(World world, int i, int j, int k, StructureBoundingBox structureboundingbox) {
        int l = this.a(i, k);
        int i1 = this.d(j);
        int j1 = this.b(i, k);
        BlockPosition blockposition = new BlockPosition(l, i1, j1);

        return !structureboundingbox.b((BaseBlockPosition) blockposition) ? Blocks.AIR.getBlockData() : world.getType(blockposition);
    }

    protected void a(World world, StructureBoundingBox structureboundingbox, int i, int j, int k, int l, int i1, int j1) {
        for (int k1 = j; k1 <= i1; ++k1) {
            for (int l1 = i; l1 <= l; ++l1) {
                for (int i2 = k; i2 <= j1; ++i2) {
                    this.a(world, Blocks.AIR.getBlockData(), l1, k1, i2, structureboundingbox);
                }
            }
        }

    }

    protected void a(World world, StructureBoundingBox structureboundingbox, int i, int j, int k, int l, int i1, int j1, IBlockData iblockdata, IBlockData iblockdata1, boolean flag) {
        for (int k1 = j; k1 <= i1; ++k1) {
            for (int l1 = i; l1 <= l; ++l1) {
                for (int i2 = k; i2 <= j1; ++i2) {
                    if (!flag || this.a(world, l1, k1, i2, structureboundingbox).getMaterial() != Material.AIR) {
                        if (k1 != j && k1 != i1 && l1 != i && l1 != l && i2 != k && i2 != j1) {
                            this.a(world, iblockdata1, l1, k1, i2, structureboundingbox);
                        } else {
                            this.a(world, iblockdata, l1, k1, i2, structureboundingbox);
                        }
                    }
                }
            }
        }

    }

    protected void a(World world, StructureBoundingBox structureboundingbox, int i, int j, int k, int l, int i1, int j1, boolean flag, Random random, StructurePiece.StructurePieceBlockSelector structurepiece_structurepieceblockselector) {
        for (int k1 = j; k1 <= i1; ++k1) {
            for (int l1 = i; l1 <= l; ++l1) {
                for (int i2 = k; i2 <= j1; ++i2) {
                    if (!flag || this.a(world, l1, k1, i2, structureboundingbox).getMaterial() != Material.AIR) {
                        structurepiece_structurepieceblockselector.a(random, l1, k1, i2, k1 == j || k1 == i1 || l1 == i || l1 == l || i2 == k || i2 == j1);
                        this.a(world, structurepiece_structurepieceblockselector.a(), l1, k1, i2, structureboundingbox);
                    }
                }
            }
        }

    }

    protected void a(World world, StructureBoundingBox structureboundingbox, Random random, float f, int i, int j, int k, int l, int i1, int j1, IBlockData iblockdata, IBlockData iblockdata1, boolean flag) {
        for (int k1 = j; k1 <= i1; ++k1) {
            for (int l1 = i; l1 <= l; ++l1) {
                for (int i2 = k; i2 <= j1; ++i2) {
                    if (random.nextFloat() <= f && (!flag || this.a(world, l1, k1, i2, structureboundingbox).getMaterial() != Material.AIR)) {
                        if (k1 != j && k1 != i1 && l1 != i && l1 != l && i2 != k && i2 != j1) {
                            this.a(world, iblockdata1, l1, k1, i2, structureboundingbox);
                        } else {
                            this.a(world, iblockdata, l1, k1, i2, structureboundingbox);
                        }
                    }
                }
            }
        }

    }

    protected void a(World world, StructureBoundingBox structureboundingbox, Random random, float f, int i, int j, int k, IBlockData iblockdata) {
        if (random.nextFloat() < f) {
            this.a(world, iblockdata, i, j, k, structureboundingbox);
        }

    }

    protected void a(World world, StructureBoundingBox structureboundingbox, int i, int j, int k, int l, int i1, int j1, IBlockData iblockdata, boolean flag) {
        float f = (float) (l - i + 1);
        float f1 = (float) (i1 - j + 1);
        float f2 = (float) (j1 - k + 1);
        float f3 = (float) i + f / 2.0F;
        float f4 = (float) k + f2 / 2.0F;

        for (int k1 = j; k1 <= i1; ++k1) {
            float f5 = (float) (k1 - j) / f1;

            for (int l1 = i; l1 <= l; ++l1) {
                float f6 = ((float) l1 - f3) / (f * 0.5F);

                for (int i2 = k; i2 <= j1; ++i2) {
                    float f7 = ((float) i2 - f4) / (f2 * 0.5F);

                    if (!flag || this.a(world, l1, k1, i2, structureboundingbox).getMaterial() != Material.AIR) {
                        float f8 = f6 * f6 + f5 * f5 + f7 * f7;

                        if (f8 <= 1.05F) {
                            this.a(world, iblockdata, l1, k1, i2, structureboundingbox);
                        }
                    }
                }
            }
        }

    }

    protected void b(World world, int i, int j, int k, StructureBoundingBox structureboundingbox) {
        BlockPosition blockposition = new BlockPosition(this.a(i, k), this.d(j), this.b(i, k));

        if (structureboundingbox.b((BaseBlockPosition) blockposition)) {
            while (!world.isEmpty(blockposition) && blockposition.getY() < 255) {
                world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 2);
                blockposition = blockposition.up();
            }

        }
    }

    protected void b(World world, IBlockData iblockdata, int i, int j, int k, StructureBoundingBox structureboundingbox) {
        int l = this.a(i, k);
        int i1 = this.d(j);
        int j1 = this.b(i, k);

        if (structureboundingbox.b((BaseBlockPosition) (new BlockPosition(l, i1, j1)))) {
            while ((world.isEmpty(new BlockPosition(l, i1, j1)) || world.getType(new BlockPosition(l, i1, j1)).getMaterial().isLiquid()) && i1 > 1) {
                world.setTypeAndData(new BlockPosition(l, i1, j1), iblockdata, 2);
                --i1;
            }

        }
    }

    protected boolean a(World world, StructureBoundingBox structureboundingbox, Random random, int i, int j, int k, MinecraftKey minecraftkey) {
        BlockPosition blockposition = new BlockPosition(this.a(i, k), this.d(j), this.b(i, k));

        if (structureboundingbox.b((BaseBlockPosition) blockposition) && world.getType(blockposition).getBlock() != Blocks.CHEST) {
            IBlockData iblockdata = Blocks.CHEST.getBlockData();

            world.setTypeAndData(blockposition, Blocks.CHEST.f(world, blockposition, iblockdata), 2);
            TileEntity tileentity = world.getTileEntity(blockposition);

            if (tileentity instanceof TileEntityChest) {
                ((TileEntityChest) tileentity).a(minecraftkey, random.nextLong());
            }

            return true;
        } else {
            return false;
        }
    }

    protected boolean a(World world, StructureBoundingBox structureboundingbox, Random random, int i, int j, int k, EnumDirection enumdirection, MinecraftKey minecraftkey) {
        BlockPosition blockposition = new BlockPosition(this.a(i, k), this.d(j), this.b(i, k));

        if (structureboundingbox.b((BaseBlockPosition) blockposition) && world.getType(blockposition).getBlock() != Blocks.DISPENSER) {
            this.a(world, Blocks.DISPENSER.getBlockData().set(BlockDispenser.FACING, enumdirection), i, j, k, structureboundingbox);
            TileEntity tileentity = world.getTileEntity(blockposition);

            if (tileentity instanceof TileEntityDispenser) {
                ((TileEntityDispenser) tileentity).a(minecraftkey, random.nextLong());
            }

            return true;
        } else {
            return false;
        }
    }

    protected void a(World world, StructureBoundingBox structureboundingbox, Random random, int i, int j, int k, EnumDirection enumdirection) {
        this.a(world, Blocks.WOODEN_DOOR.getBlockData().set(BlockDoor.FACING, enumdirection), i, j, k, structureboundingbox);
        this.a(world, Blocks.WOODEN_DOOR.getBlockData().set(BlockDoor.FACING, enumdirection).set(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), i, j + 1, k, structureboundingbox);
    }

    public void a(int i, int j, int k) {
        this.l.a(i, j, k);
    }

    @Nullable
    public EnumDirection e() {
        return this.a;
    }

    public void a(@Nullable EnumDirection enumdirection) {
        this.a = enumdirection;
        if (enumdirection == null) {
            this.c = EnumBlockRotation.NONE;
            this.b = EnumBlockMirror.NONE;
        } else {
            switch (StructurePiece.SyntheticClass_1.a[enumdirection.ordinal()]) {
            case 2:
                this.b = EnumBlockMirror.LEFT_RIGHT;
                this.c = EnumBlockRotation.NONE;
                break;

            case 3:
                this.b = EnumBlockMirror.LEFT_RIGHT;
                this.c = EnumBlockRotation.CLOCKWISE_90;
                break;

            case 4:
                this.b = EnumBlockMirror.NONE;
                this.c = EnumBlockRotation.CLOCKWISE_90;
                break;

            default:
                this.b = EnumBlockMirror.NONE;
                this.c = EnumBlockRotation.NONE;
            }
        }

    }

    static class SyntheticClass_1 {

        static final int[] a = new int[EnumDirection.values().length];

        static {
            try {
                StructurePiece.SyntheticClass_1.a[EnumDirection.NORTH.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                StructurePiece.SyntheticClass_1.a[EnumDirection.SOUTH.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                StructurePiece.SyntheticClass_1.a[EnumDirection.WEST.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                StructurePiece.SyntheticClass_1.a[EnumDirection.EAST.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

        }
    }

    public abstract static class StructurePieceBlockSelector {

        protected IBlockData a;

        protected StructurePieceBlockSelector() {
            this.a = Blocks.AIR.getBlockData();
        }

        public abstract void a(Random random, int i, int j, int k, boolean flag);

        public IBlockData a() {
            return this.a;
        }
    }
}
