package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit

public abstract class BlockPressurePlateAbstract extends Block {

    protected static final AxisAlignedBB a = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.03125D, 0.9375D);
    protected static final AxisAlignedBB b = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.0625D, 0.9375D);
    protected static final AxisAlignedBB c = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.25D, 0.875D);

    protected BlockPressurePlateAbstract(Material material) {
        this(material, material.r());
    }

    protected BlockPressurePlateAbstract(Material material, MaterialMapColor materialmapcolor) {
        super(material, materialmapcolor);
        this.a(CreativeModeTab.d);
        this.a(true);
    }

    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        boolean flag = this.getPower(iblockdata) > 0;

        return flag ? BlockPressurePlateAbstract.a : BlockPressurePlateAbstract.b;
    }

    public int a(World world) {
        return 20;
    }

    @Nullable
    public AxisAlignedBB a(IBlockData iblockdata, World world, BlockPosition blockposition) {
        return BlockPressurePlateAbstract.k;
    }

    public boolean b(IBlockData iblockdata) {
        return false;
    }

    public boolean c(IBlockData iblockdata) {
        return false;
    }

    public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return true;
    }

    public boolean d() {
        return true;
    }

    public boolean canPlace(World world, BlockPosition blockposition) {
        return this.i(world, blockposition.down());
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
        if (!this.i(world, blockposition.down())) {
            this.b(world, blockposition, iblockdata, 0);
            world.setAir(blockposition);
        }

    }

    private boolean i(World world, BlockPosition blockposition) {
        return world.getType(blockposition).q() || world.getType(blockposition).getBlock() instanceof BlockFence;
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {}

    public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        if (!world.isClientSide) {
            int i = this.getPower(iblockdata);

            if (i > 0) {
                this.a(world, blockposition, iblockdata, i);
            }

        }
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
        if (!world.isClientSide) {
            int i = this.getPower(iblockdata);

            if (i == 0) {
                this.a(world, blockposition, iblockdata, i);
            }

        }
    }

    protected void a(World world, BlockPosition blockposition, IBlockData iblockdata, int i) {
        int j = this.e(world, blockposition);
        boolean flag = i > 0;
        boolean flag1 = j > 0;

        // CraftBukkit start - Interact Pressure Plate
        org.bukkit.World bworld = world.getWorld();
        org.bukkit.plugin.PluginManager manager = world.getServer().getPluginManager();

        if (flag != flag1) {
            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bworld.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), i, j);
            manager.callEvent(eventRedstone);

            flag1 = eventRedstone.getNewCurrent() > 0;
            j = eventRedstone.getNewCurrent();
        }
        // CraftBukkit end

        if (i != j) {
            iblockdata = this.a(iblockdata, j);
            world.setTypeAndData(blockposition, iblockdata, 2);
            this.d(world, blockposition);
            world.b(blockposition, blockposition);
        }

        if (!flag1 && flag) {
            this.c(world, blockposition);
        } else if (flag1 && !flag) {
            this.b(world, blockposition);
        }

        if (flag1) {
            world.a(new BlockPosition(blockposition), (Block) this, this.a(world));
        }

    }

    protected abstract void b(World world, BlockPosition blockposition);

    protected abstract void c(World world, BlockPosition blockposition);

    public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (this.getPower(iblockdata) > 0) {
            this.d(world, blockposition);
        }

        super.remove(world, blockposition, iblockdata);
    }

    protected void d(World world, BlockPosition blockposition) {
        world.applyPhysics(blockposition, this);
        world.applyPhysics(blockposition.down(), this);
    }

    public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        return this.getPower(iblockdata);
    }

    public int c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.UP ? this.getPower(iblockdata) : 0;
    }

    public boolean isPowerSource(IBlockData iblockdata) {
        return true;
    }

    public EnumPistonReaction h(IBlockData iblockdata) {
        return EnumPistonReaction.DESTROY;
    }

    protected abstract int e(World world, BlockPosition blockposition);

    protected abstract int getPower(IBlockData iblockdata);

    protected abstract IBlockData a(IBlockData iblockdata, int i);
}
