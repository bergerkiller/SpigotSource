package net.minecraft.server;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public abstract class DefinedStructurePiece extends StructurePiece {

    private static final DefinedStructureInfo d = new DefinedStructureInfo();
    protected DefinedStructure a;
    protected DefinedStructureInfo b;
    protected BlockPosition c;

    public DefinedStructurePiece() {
        this.b = DefinedStructurePiece.d.a(true).a(Blocks.AIR);
    }

    public DefinedStructurePiece(int i) {
        super(i);
        this.b = DefinedStructurePiece.d.a(true).a(Blocks.AIR);
    }

    protected void a(DefinedStructure definedstructure, BlockPosition blockposition, DefinedStructureInfo definedstructureinfo) {
        this.a = definedstructure;
        this.a(EnumDirection.NORTH);
        this.c = blockposition;
        this.b = definedstructureinfo;
        this.h();
    }

    protected void a(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInt("TPX", this.c.getX());
        nbttagcompound.setInt("TPY", this.c.getY());
        nbttagcompound.setInt("TPZ", this.c.getZ());
    }

    protected void b(NBTTagCompound nbttagcompound) {
        this.c = new BlockPosition(nbttagcompound.getInt("TPX"), nbttagcompound.getInt("TPY"), nbttagcompound.getInt("TPZ"));
    }

    public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
        this.b.a(structureboundingbox);
        this.a.b(world, this.c, this.b);
        Map map = this.a.a(this.c, this.b);
        Iterator iterator = map.keySet().iterator();

        while (iterator.hasNext()) {
            BlockPosition blockposition = (BlockPosition) iterator.next();
            String s = (String) map.get(blockposition);

            this.a(s, blockposition, world, random, structureboundingbox);
        }

        return true;
    }

    protected abstract void a(String s, BlockPosition blockposition, World world, Random random, StructureBoundingBox structureboundingbox);

    private void h() {
        EnumBlockRotation enumblockrotation = this.b.c();
        BlockPosition blockposition = this.a.a(enumblockrotation);

        this.l = new StructureBoundingBox(0, 0, 0, blockposition.getX(), blockposition.getY() - 1, blockposition.getZ());
        switch (DefinedStructurePiece.SyntheticClass_1.a[enumblockrotation.ordinal()]) {
        case 1:
        default:
            break;

        case 2:
            this.l.a(-blockposition.getX(), 0, 0);
            break;

        case 3:
            this.l.a(0, 0, -blockposition.getZ());
            break;

        case 4:
            this.l.a(-blockposition.getX(), 0, -blockposition.getZ());
        }

        this.l.a(this.c.getX(), this.c.getY(), this.c.getZ());
    }

    public void a(int i, int j, int k) {
        super.a(i, j, k);
        this.c = this.c.a(i, j, k);
    }

    static class SyntheticClass_1 {

        static final int[] a = new int[EnumBlockRotation.values().length];

        static {
            try {
                DefinedStructurePiece.SyntheticClass_1.a[EnumBlockRotation.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                DefinedStructurePiece.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_90.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                DefinedStructurePiece.SyntheticClass_1.a[EnumBlockRotation.COUNTERCLOCKWISE_90.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                DefinedStructurePiece.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_180.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

        }
    }
}
