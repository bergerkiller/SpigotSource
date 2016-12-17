package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockBanner extends BlockTileEntity {

    public static final BlockStateDirection FACING = BlockFacingHorizontal.FACING;
    public static final BlockStateInteger ROTATION = BlockStateInteger.of("rotation", 0, 15);
    protected static final AxisAlignedBB c = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);

    protected BlockBanner() {
        super(Material.WOOD);
    }

    public String getName() {
        return LocaleI18n.get("item.banner.white.name");
    }

    @Nullable
    public AxisAlignedBB a(IBlockData iblockdata, World world, BlockPosition blockposition) {
        return BlockBanner.k;
    }

    public boolean c(IBlockData iblockdata) {
        return false;
    }

    public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return true;
    }

    public boolean b(IBlockData iblockdata) {
        return false;
    }

    public boolean d() {
        return true;
    }

    public TileEntity a(World world, int i) {
        return new TileEntityBanner();
    }

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return Items.BANNER;
    }

    @Nullable
    private ItemStack e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        TileEntity tileentity = world.getTileEntity(blockposition);

        if (tileentity instanceof TileEntityBanner) {
            ItemStack itemstack = new ItemStack(Items.BANNER, 1, ((TileEntityBanner) tileentity).c());
            NBTTagCompound nbttagcompound = tileentity.save(new NBTTagCompound());

            nbttagcompound.remove("x");
            nbttagcompound.remove("y");
            nbttagcompound.remove("z");
            nbttagcompound.remove("id");
            itemstack.a("BlockEntityTag", (NBTBase) nbttagcompound);
            return itemstack;
        } else {
            return null;
        }
    }

    public ItemStack a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        ItemStack itemstack = this.e(world, blockposition, iblockdata);

        return itemstack != null ? itemstack : new ItemStack(Items.BANNER);
    }

    public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
        ItemStack itemstack = this.e(world, blockposition, iblockdata);

        if (itemstack != null) {
            a(world, blockposition, itemstack);
        } else {
            super.dropNaturally(world, blockposition, iblockdata, f, i);
        }

    }

    public boolean canPlace(World world, BlockPosition blockposition) {
        return !this.b(world, blockposition) && super.canPlace(world, blockposition);
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, @Nullable ItemStack itemstack) {
        if (tileentity instanceof TileEntityBanner) {
            TileEntityBanner tileentitybanner = (TileEntityBanner) tileentity;
            ItemStack itemstack1 = new ItemStack(Items.BANNER, 1, ((TileEntityBanner) tileentity).c());
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            TileEntityBanner.a(nbttagcompound, tileentitybanner.c(), tileentitybanner.e());
            itemstack1.a("BlockEntityTag", (NBTBase) nbttagcompound);
            a(world, blockposition, itemstack1);
        } else {
            super.a(world, entityhuman, blockposition, iblockdata, (TileEntity) null, itemstack);
        }

    }

    static class SyntheticClass_1 {

        static final int[] a = new int[EnumDirection.values().length];

        static {
            try {
                BlockBanner.SyntheticClass_1.a[EnumDirection.NORTH.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                BlockBanner.SyntheticClass_1.a[EnumDirection.SOUTH.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                BlockBanner.SyntheticClass_1.a[EnumDirection.WEST.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                BlockBanner.SyntheticClass_1.a[EnumDirection.EAST.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

        }
    }

    public static class BlockStandingBanner extends BlockBanner {

        public BlockStandingBanner() {
            this.w(this.blockStateList.getBlockData().set(BlockBanner.BlockStandingBanner.ROTATION, Integer.valueOf(0)));
        }

        public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
            return BlockBanner.BlockStandingBanner.c;
        }

        public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
            return iblockdata.set(BlockBanner.BlockStandingBanner.ROTATION, Integer.valueOf(enumblockrotation.a(((Integer) iblockdata.get(BlockBanner.BlockStandingBanner.ROTATION)).intValue(), 16)));
        }

        public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
            return iblockdata.set(BlockBanner.BlockStandingBanner.ROTATION, Integer.valueOf(enumblockmirror.a(((Integer) iblockdata.get(BlockBanner.BlockStandingBanner.ROTATION)).intValue(), 16)));
        }

        public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
            if (!world.getType(blockposition.down()).getMaterial().isBuildable()) {
                this.b(world, blockposition, iblockdata, 0);
                world.setAir(blockposition);
            }

            super.a(iblockdata, world, blockposition, block);
        }

        public IBlockData fromLegacyData(int i) {
            return this.getBlockData().set(BlockBanner.BlockStandingBanner.ROTATION, Integer.valueOf(i));
        }

        public int toLegacyData(IBlockData iblockdata) {
            return ((Integer) iblockdata.get(BlockBanner.BlockStandingBanner.ROTATION)).intValue();
        }

        protected BlockStateList getStateList() {
            return new BlockStateList(this, new IBlockState[] { BlockBanner.BlockStandingBanner.ROTATION});
        }
    }

    public static class BlockWallBanner extends BlockBanner {

        protected static final AxisAlignedBB d = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 0.78125D, 1.0D);
        protected static final AxisAlignedBB e = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.78125D, 0.125D);
        protected static final AxisAlignedBB f = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 0.78125D, 1.0D);
        protected static final AxisAlignedBB g = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 0.78125D, 1.0D);

        public BlockWallBanner() {
            this.w(this.blockStateList.getBlockData().set(BlockBanner.BlockWallBanner.FACING, EnumDirection.NORTH));
        }

        public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
            return iblockdata.set(BlockBanner.BlockWallBanner.FACING, enumblockrotation.a((EnumDirection) iblockdata.get(BlockBanner.BlockWallBanner.FACING)));
        }

        public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
            return iblockdata.a(enumblockmirror.a((EnumDirection) iblockdata.get(BlockBanner.BlockWallBanner.FACING)));
        }

        public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
            switch (BlockBanner.SyntheticClass_1.a[((EnumDirection) iblockdata.get(BlockBanner.BlockWallBanner.FACING)).ordinal()]) {
            case 1:
            default:
                return BlockBanner.BlockWallBanner.d;

            case 2:
                return BlockBanner.BlockWallBanner.e;

            case 3:
                return BlockBanner.BlockWallBanner.f;

            case 4:
                return BlockBanner.BlockWallBanner.g;
            }
        }

        public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
            EnumDirection enumdirection = (EnumDirection) iblockdata.get(BlockBanner.BlockWallBanner.FACING);

            if (!world.getType(blockposition.shift(enumdirection.opposite())).getMaterial().isBuildable()) {
                this.b(world, blockposition, iblockdata, 0);
                world.setAir(blockposition);
            }

            super.a(iblockdata, world, blockposition, block);
        }

        public IBlockData fromLegacyData(int i) {
            EnumDirection enumdirection = EnumDirection.fromType1(i);

            if (enumdirection.k() == EnumDirection.EnumAxis.Y) {
                enumdirection = EnumDirection.NORTH;
            }

            return this.getBlockData().set(BlockBanner.BlockWallBanner.FACING, enumdirection);
        }

        public int toLegacyData(IBlockData iblockdata) {
            return ((EnumDirection) iblockdata.get(BlockBanner.BlockWallBanner.FACING)).a();
        }

        protected BlockStateList getStateList() {
            return new BlockStateList(this, new IBlockState[] { BlockBanner.BlockWallBanner.FACING});
        }
    }
}
