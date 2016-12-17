package net.minecraft.server;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;

public class ShapeDetectorBlock {

    private final World a;
    private final BlockPosition b;
    private final boolean c;
    private IBlockData d;
    private TileEntity e;
    private boolean f;

    public ShapeDetectorBlock(World world, BlockPosition blockposition, boolean flag) {
        this.a = world;
        this.b = blockposition;
        this.c = flag;
    }

    public IBlockData a() {
        if (this.d == null && (this.c || this.a.isLoaded(this.b))) {
            this.d = this.a.getType(this.b);
        }

        return this.d;
    }

    @Nullable
    public TileEntity b() {
        if (this.e == null && !this.f) {
            this.e = this.a.getTileEntity(this.b);
            this.f = true;
        }

        return this.e;
    }

    public BlockPosition getPosition() {
        return this.b;
    }

    public static Predicate<ShapeDetectorBlock> a(final Predicate<IBlockData> predicate) {
        return new Predicate() {
            public boolean a(@Nullable ShapeDetectorBlock shapedetectorblock) {
                return shapedetectorblock != null && predicate.apply(shapedetectorblock.a());
            }

            public boolean apply(Object object) {
                return this.a((ShapeDetectorBlock) object);
            }
        };
    }

    public static Predicate<ShapeDetectorBlock> a(final IBlockData iblockdata) {
        return new Predicate() {
            public boolean a(@Nullable ShapeDetectorBlock shapedetectorblock) {
                return shapedetectorblock != null && shapedetectorblock.a().equals(iblockdata);
            }

            public boolean apply(Object object) {
                return this.a((ShapeDetectorBlock) object);
            }
        };
    }
}
