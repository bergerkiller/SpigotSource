package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockSnow extends Block {

    public static final BlockStateInteger LAYERS = BlockStateInteger.of("layers", 1, 8);
    protected static final AxisAlignedBB[] b = new AxisAlignedBB[] { new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};

    protected BlockSnow() {
        super(Material.PACKED_ICE);
        this.w(this.blockStateList.getBlockData().set(BlockSnow.LAYERS, Integer.valueOf(1)));
        this.a(true);
        this.a(CreativeModeTab.c);
    }

    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return BlockSnow.b[((Integer) iblockdata.get(BlockSnow.LAYERS)).intValue()];
    }

    public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return ((Integer) iblockaccess.getType(blockposition).get(BlockSnow.LAYERS)).intValue() < 5;
    }

    public boolean k(IBlockData iblockdata) {
        return ((Integer) iblockdata.get(BlockSnow.LAYERS)).intValue() == 7;
    }

    @Nullable
    public AxisAlignedBB a(IBlockData iblockdata, World world, BlockPosition blockposition) {
        int i = ((Integer) iblockdata.get(BlockSnow.LAYERS)).intValue() - 1;
        float f = 0.125F;
        AxisAlignedBB axisalignedbb = iblockdata.c(world, blockposition);

        return new AxisAlignedBB(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c, axisalignedbb.d, (double) ((float) i * f), axisalignedbb.f);
    }

    public boolean b(IBlockData iblockdata) {
        return false;
    }

    public boolean c(IBlockData iblockdata) {
        return false;
    }

    public boolean canPlace(World world, BlockPosition blockposition) {
        IBlockData iblockdata = world.getType(blockposition.down());
        Block block = iblockdata.getBlock();

        return block != Blocks.ICE && block != Blocks.PACKED_ICE ? (iblockdata.getMaterial() == Material.LEAVES ? true : (block == this && ((Integer) iblockdata.get(BlockSnow.LAYERS)).intValue() >= 7 ? true : iblockdata.p() && iblockdata.getMaterial().isSolid())) : false;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
        this.e(world, blockposition, iblockdata);
    }

    private boolean e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!this.canPlace(world, blockposition)) {
            this.b(world, blockposition, iblockdata, 0);
            world.setAir(blockposition);
            return false;
        } else {
            return true;
        }
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, @Nullable ItemStack itemstack) {
        a(world, blockposition, new ItemStack(Items.SNOWBALL, ((Integer) iblockdata.get(BlockSnow.LAYERS)).intValue() + 1, 0));
        world.setAir(blockposition);
        entityhuman.b(StatisticList.a((Block) this));
    }

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return Items.SNOWBALL;
    }

    public int a(Random random) {
        return 0;
    }

    public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        if (world.b(EnumSkyBlock.BLOCK, blockposition) > 11) {
            // CraftBukkit start
            if (org.bukkit.craftbukkit.event.CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), Blocks.AIR).isCancelled()) {
                return;
            }
            // CraftBukkit end
            this.b(world, blockposition, world.getType(blockposition), 0);
            world.setAir(blockposition);
        }

    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockSnow.LAYERS, Integer.valueOf((i & 7) + 1));
    }

    public boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return ((Integer) iblockaccess.getType(blockposition).get(BlockSnow.LAYERS)).intValue() == 1;
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((Integer) iblockdata.get(BlockSnow.LAYERS)).intValue() - 1;
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockSnow.LAYERS});
    }
}
