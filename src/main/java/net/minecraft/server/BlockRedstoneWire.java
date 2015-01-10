package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit

public class BlockRedstoneWire extends Block {

    public static final BlockStateEnum NORTH = BlockStateEnum.of("north", EnumRedstoneWireConnection.class);
    public static final BlockStateEnum EAST = BlockStateEnum.of("east", EnumRedstoneWireConnection.class);
    public static final BlockStateEnum SOUTH = BlockStateEnum.of("south", EnumRedstoneWireConnection.class);
    public static final BlockStateEnum WEST = BlockStateEnum.of("west", EnumRedstoneWireConnection.class);
    public static final BlockStateInteger POWER = BlockStateInteger.of("power", 0, 15);
    private boolean P = true;
    private final Set Q = Sets.newHashSet();

    public BlockRedstoneWire() {
        super(Material.ORIENTABLE);
        this.j(this.blockStateList.getBlockData().set(BlockRedstoneWire.NORTH, EnumRedstoneWireConnection.NONE).set(BlockRedstoneWire.EAST, EnumRedstoneWireConnection.NONE).set(BlockRedstoneWire.SOUTH, EnumRedstoneWireConnection.NONE).set(BlockRedstoneWire.WEST, EnumRedstoneWireConnection.NONE).set(BlockRedstoneWire.POWER, Integer.valueOf(0)));
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        iblockdata = iblockdata.set(BlockRedstoneWire.WEST, this.c(iblockaccess, blockposition, EnumDirection.WEST));
        iblockdata = iblockdata.set(BlockRedstoneWire.EAST, this.c(iblockaccess, blockposition, EnumDirection.EAST));
        iblockdata = iblockdata.set(BlockRedstoneWire.NORTH, this.c(iblockaccess, blockposition, EnumDirection.NORTH));
        iblockdata = iblockdata.set(BlockRedstoneWire.SOUTH, this.c(iblockaccess, blockposition, EnumDirection.SOUTH));
        return iblockdata;
    }

    private EnumRedstoneWireConnection c(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        BlockPosition blockposition1 = blockposition.shift(enumdirection);
        Block block = iblockaccess.getType(blockposition.shift(enumdirection)).getBlock();

        if (!a(iblockaccess.getType(blockposition1), enumdirection) && (block.s() || !d(iblockaccess.getType(blockposition1.down())))) {
            Block block1 = iblockaccess.getType(blockposition.up()).getBlock();

            return !block1.s() && block.s() && d(iblockaccess.getType(blockposition1.up())) ? EnumRedstoneWireConnection.UP : EnumRedstoneWireConnection.NONE;
        } else {
            return EnumRedstoneWireConnection.SIDE;
        }
    }

    public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return null;
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public boolean canPlace(World world, BlockPosition blockposition) {
        return World.a((IBlockAccess) world, blockposition.down()) || world.getType(blockposition.down()).getBlock() == Blocks.GLOWSTONE;
    }

    private IBlockData e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        iblockdata = this.a(world, blockposition, blockposition, iblockdata);
        ArrayList arraylist = Lists.newArrayList(this.Q);

        this.Q.clear();
        Iterator iterator = arraylist.iterator();

        while (iterator.hasNext()) {
            BlockPosition blockposition1 = (BlockPosition) iterator.next();

            world.applyPhysics(blockposition1, this);
        }

        return iblockdata;
    }

    private IBlockData a(World world, BlockPosition blockposition, BlockPosition blockposition1, IBlockData iblockdata) {
        IBlockData iblockdata1 = iblockdata;
        int i = ((Integer) iblockdata.get(BlockRedstoneWire.POWER)).intValue();
        byte b0 = 0;
        int j = this.getPower(world, blockposition1, b0);

        this.P = false;
        int k = world.A(blockposition);

        this.P = true;
        if (k > 0 && k > j - 1) {
            j = k;
        }

        int l = 0;
        Iterator iterator = EnumDirectionLimit.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            EnumDirection enumdirection = (EnumDirection) iterator.next();
            BlockPosition blockposition2 = blockposition.shift(enumdirection);
            boolean flag = blockposition2.getX() != blockposition1.getX() || blockposition2.getZ() != blockposition1.getZ();

            if (flag) {
                l = this.getPower(world, blockposition2, l);
            }

            if (world.getType(blockposition2).getBlock().isOccluding() && !world.getType(blockposition.up()).getBlock().isOccluding()) {
                if (flag && blockposition.getY() >= blockposition1.getY()) {
                    l = this.getPower(world, blockposition2.up(), l);
                }
            } else if (!world.getType(blockposition2).getBlock().isOccluding() && flag && blockposition.getY() <= blockposition1.getY()) {
                l = this.getPower(world, blockposition2.down(), l);
            }
        }

        if (l > j) {
            j = l - 1;
        } else if (j > 0) {
            --j;
        } else {
            j = 0;
        }

        if (k > j - 1) {
            j = k;
        }
        
        // CraftBukkit start
        if (i != j) {
            BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), i, j);
            world.getServer().getPluginManager().callEvent(event);

            j = event.getNewCurrent();
        }
        // CraftBukkit end

        if (i != j) {
            iblockdata = iblockdata.set(BlockRedstoneWire.POWER, Integer.valueOf(j));
            if (world.getType(blockposition) == iblockdata1) {
                world.setTypeAndData(blockposition, iblockdata, 2);
            }

            this.Q.add(blockposition);
            EnumDirection[] aenumdirection = EnumDirection.values();
            int i1 = aenumdirection.length;

            for (int j1 = 0; j1 < i1; ++j1) {
                EnumDirection enumdirection1 = aenumdirection[j1];

                this.Q.add(blockposition.shift(enumdirection1));
            }
        }

        return iblockdata;
    }

    private void d(World world, BlockPosition blockposition) {
        if (world.getType(blockposition).getBlock() == this) {
            world.applyPhysics(blockposition, this);
            EnumDirection[] aenumdirection = EnumDirection.values();
            int i = aenumdirection.length;

            for (int j = 0; j < i; ++j) {
                EnumDirection enumdirection = aenumdirection[j];

                world.applyPhysics(blockposition.shift(enumdirection), this);
            }

        }
    }

    public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
        if (!world.isStatic) {
            this.e(world, blockposition, iblockdata);
            Iterator iterator = EnumDirectionLimit.VERTICAL.iterator();

            EnumDirection enumdirection;

            while (iterator.hasNext()) {
                enumdirection = (EnumDirection) iterator.next();
                world.applyPhysics(blockposition.shift(enumdirection), this);
            }

            iterator = EnumDirectionLimit.HORIZONTAL.iterator();

            while (iterator.hasNext()) {
                enumdirection = (EnumDirection) iterator.next();
                this.d(world, blockposition.shift(enumdirection));
            }

            iterator = EnumDirectionLimit.HORIZONTAL.iterator();

            while (iterator.hasNext()) {
                enumdirection = (EnumDirection) iterator.next();
                BlockPosition blockposition1 = blockposition.shift(enumdirection);

                if (world.getType(blockposition1).getBlock().isOccluding()) {
                    this.d(world, blockposition1.up());
                } else {
                    this.d(world, blockposition1.down());
                }
            }

        }
    }

    public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
        super.remove(world, blockposition, iblockdata);
        if (!world.isStatic) {
            EnumDirection[] aenumdirection = EnumDirection.values();
            int i = aenumdirection.length;

            for (int j = 0; j < i; ++j) {
                EnumDirection enumdirection = aenumdirection[j];

                world.applyPhysics(blockposition.shift(enumdirection), this);
            }

            this.e(world, blockposition, iblockdata);
            Iterator iterator = EnumDirectionLimit.HORIZONTAL.iterator();

            EnumDirection enumdirection1;

            while (iterator.hasNext()) {
                enumdirection1 = (EnumDirection) iterator.next();
                this.d(world, blockposition.shift(enumdirection1));
            }

            iterator = EnumDirectionLimit.HORIZONTAL.iterator();

            while (iterator.hasNext()) {
                enumdirection1 = (EnumDirection) iterator.next();
                BlockPosition blockposition1 = blockposition.shift(enumdirection1);

                if (world.getType(blockposition1).getBlock().isOccluding()) {
                    this.d(world, blockposition1.up());
                } else {
                    this.d(world, blockposition1.down());
                }
            }

        }
    }

    public int getPower(World world, BlockPosition blockposition, int i) {
        if (world.getType(blockposition).getBlock() != this) {
            return i;
        } else {
            int j = ((Integer) world.getType(blockposition).get(BlockRedstoneWire.POWER)).intValue();

            return j > i ? j : i;
        }
    }

    public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
        if (!world.isStatic) {
            if (this.canPlace(world, blockposition)) {
                this.e(world, blockposition, iblockdata);
            } else {
                this.b(world, blockposition, iblockdata, 0);
                world.setAir(blockposition);
            }

        }
    }

    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return Items.REDSTONE;
    }

    public int b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
        return !this.P ? 0 : this.a(iblockaccess, blockposition, iblockdata, enumdirection);
    }

    public int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
        if (!this.P) {
            return 0;
        } else {
            int i = ((Integer) iblockdata.get(BlockRedstoneWire.POWER)).intValue();

            if (i == 0) {
                return 0;
            } else if (enumdirection == EnumDirection.UP) {
                return i;
            } else {
                EnumSet enumset = EnumSet.noneOf(EnumDirection.class);
                Iterator iterator = EnumDirectionLimit.HORIZONTAL.iterator();

                while (iterator.hasNext()) {
                    EnumDirection enumdirection1 = (EnumDirection) iterator.next();

                    if (this.d(iblockaccess, blockposition, enumdirection1)) {
                        enumset.add(enumdirection1);
                    }
                }

                if (enumdirection.k().c() && enumset.isEmpty()) {
                    return i;
                } else if (enumset.contains(enumdirection) && !enumset.contains(enumdirection.f()) && !enumset.contains(enumdirection.e())) {
                    return i;
                } else {
                    return 0;
                }
            }
        }
    }

    private boolean d(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        BlockPosition blockposition1 = blockposition.shift(enumdirection);
        IBlockData iblockdata = iblockaccess.getType(blockposition1);
        Block block = iblockdata.getBlock();
        boolean flag = block.isOccluding();
        boolean flag1 = iblockaccess.getType(blockposition.up()).getBlock().isOccluding();

        return !flag1 && flag && e(iblockaccess, blockposition1.up()) ? true : (a(iblockdata, enumdirection) ? true : (block == Blocks.POWERED_REPEATER && iblockdata.get(BlockDiodeAbstract.FACING) == enumdirection ? true : !flag && e(iblockaccess, blockposition1.down())));
    }

    protected static boolean e(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return d(iblockaccess.getType(blockposition));
    }

    protected static boolean d(IBlockData iblockdata) {
        return a(iblockdata, (EnumDirection) null);
    }

    protected static boolean a(IBlockData iblockdata, EnumDirection enumdirection) {
        Block block = iblockdata.getBlock();

        if (block == Blocks.REDSTONE_WIRE) {
            return true;
        } else if (Blocks.UNPOWERED_REPEATER.e(block)) {
            EnumDirection enumdirection1 = (EnumDirection) iblockdata.get(BlockRepeater.FACING);

            return enumdirection1 == enumdirection || enumdirection1.opposite() == enumdirection;
        } else {
            return block.isPowerSource() && enumdirection != null;
        }
    }

    public boolean isPowerSource() {
        return this.P;
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockRedstoneWire.POWER, Integer.valueOf(i));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((Integer) iblockdata.get(BlockRedstoneWire.POWER)).intValue();
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockRedstoneWire.NORTH, BlockRedstoneWire.EAST, BlockRedstoneWire.SOUTH, BlockRedstoneWire.WEST, BlockRedstoneWire.POWER});
    }
}
