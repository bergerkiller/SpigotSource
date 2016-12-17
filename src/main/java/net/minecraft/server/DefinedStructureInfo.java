package net.minecraft.server;

import javax.annotation.Nullable;

public class DefinedStructureInfo {

    private EnumBlockMirror a;
    private EnumBlockRotation b;
    private boolean c;
    private Block d;
    private ChunkCoordIntPair e;
    private StructureBoundingBox f;
    private boolean g;

    public DefinedStructureInfo() {
        this(EnumBlockMirror.NONE, EnumBlockRotation.NONE, false, (Block) null, (StructureBoundingBox) null);
    }

    public DefinedStructureInfo(EnumBlockMirror enumblockmirror, EnumBlockRotation enumblockrotation, boolean flag, @Nullable Block block, @Nullable StructureBoundingBox structureboundingbox) {
        this.b = enumblockrotation;
        this.a = enumblockmirror;
        this.c = flag;
        this.d = block;
        this.e = null;
        this.f = structureboundingbox;
        this.g = true;
    }

    public DefinedStructureInfo a() {
        return (new DefinedStructureInfo(this.a, this.b, this.c, this.d, this.f)).a(this.e).b(this.g);
    }

    public DefinedStructureInfo a(EnumBlockMirror enumblockmirror) {
        this.a = enumblockmirror;
        return this;
    }

    public DefinedStructureInfo a(EnumBlockRotation enumblockrotation) {
        this.b = enumblockrotation;
        return this;
    }

    public DefinedStructureInfo a(boolean flag) {
        this.c = flag;
        return this;
    }

    public DefinedStructureInfo a(Block block) {
        this.d = block;
        return this;
    }

    public DefinedStructureInfo a(ChunkCoordIntPair chunkcoordintpair) {
        this.e = chunkcoordintpair;
        return this;
    }

    public DefinedStructureInfo a(StructureBoundingBox structureboundingbox) {
        this.f = structureboundingbox;
        return this;
    }

    public EnumBlockMirror b() {
        return this.a;
    }

    public DefinedStructureInfo b(boolean flag) {
        this.g = flag;
        return this;
    }

    public EnumBlockRotation c() {
        return this.b;
    }

    public boolean e() {
        return this.c;
    }

    public Block f() {
        return this.d;
    }

    @Nullable
    public StructureBoundingBox g() {
        if (this.f == null && this.e != null) {
            this.i();
        }

        return this.f;
    }

    public boolean h() {
        return this.g;
    }

    void i() {
        this.f = this.b(this.e);
    }

    @Nullable
    private StructureBoundingBox b(@Nullable ChunkCoordIntPair chunkcoordintpair) {
        if (chunkcoordintpair == null) {
            return null;
        } else {
            int i = chunkcoordintpair.x * 16;
            int j = chunkcoordintpair.z * 16;

            return new StructureBoundingBox(i, 0, j, i + 16 - 1, 255, j + 16 - 1);
        }
    }
}
