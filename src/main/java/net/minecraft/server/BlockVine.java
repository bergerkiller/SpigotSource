package net.minecraft.server;

import java.util.Iterator;
import java.util.Random;

import org.bukkit.craftbukkit.event.CraftEventFactory; // CraftBukkit

public class BlockVine extends Block {

    public static final BlockStateBoolean UP = BlockStateBoolean.of("up");
    public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
    public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
    public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
    public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
    public static final BlockStateBoolean[] P = new BlockStateBoolean[] { BlockVine.UP, BlockVine.NORTH, BlockVine.SOUTH, BlockVine.WEST, BlockVine.EAST};
    public static final int Q = b(EnumDirection.SOUTH);
    public static final int R = b(EnumDirection.NORTH);
    public static final int S = b(EnumDirection.EAST);
    public static final int T = b(EnumDirection.WEST);

    public BlockVine() {
        super(Material.REPLACEABLE_PLANT);
        this.j(this.blockStateList.getBlockData().set(BlockVine.UP, Boolean.valueOf(false)).set(BlockVine.NORTH, Boolean.valueOf(false)).set(BlockVine.EAST, Boolean.valueOf(false)).set(BlockVine.SOUTH, Boolean.valueOf(false)).set(BlockVine.WEST, Boolean.valueOf(false)));
        this.a(true);
        this.a(CreativeModeTab.c);
    }

    public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockdata.set(BlockVine.UP, Boolean.valueOf(iblockaccess.getType(blockposition.up()).getBlock().s()));
    }

    public void h() {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public boolean f(World world, BlockPosition blockposition) {
        return true;
    }

    public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
        float f = 0.0625F;
        float f1 = 1.0F;
        float f2 = 1.0F;
        float f3 = 1.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag = false;

        if (((Boolean) iblockaccess.getType(blockposition).get(BlockVine.WEST)).booleanValue()) {
            f4 = Math.max(f4, 0.0625F);
            f1 = 0.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
            flag = true;
        }

        if (((Boolean) iblockaccess.getType(blockposition).get(BlockVine.EAST)).booleanValue()) {
            f1 = Math.min(f1, 0.9375F);
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
            flag = true;
        }

        if (((Boolean) iblockaccess.getType(blockposition).get(BlockVine.NORTH)).booleanValue()) {
            f6 = Math.max(f6, 0.0625F);
            f3 = 0.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag = true;
        }

        if (((Boolean) iblockaccess.getType(blockposition).get(BlockVine.SOUTH)).booleanValue()) {
            f3 = Math.min(f3, 0.9375F);
            f6 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag = true;
        }

        if (!flag && this.c(iblockaccess.getType(blockposition.up()).getBlock())) {
            f2 = Math.min(f2, 0.9375F);
            f5 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
        }

        this.a(f1, f2, f3, f4, f5, f6);
    }

    public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return null;
    }

    public boolean canPlace(World world, BlockPosition blockposition, EnumDirection enumdirection) {
        switch (SwitchHelperDirection12.a[enumdirection.ordinal()]) {
        case 1:
            return this.c(world.getType(blockposition.up()).getBlock());

        case 2:
        case 3:
        case 4:
        case 5:
            return this.c(world.getType(blockposition.shift(enumdirection.opposite())).getBlock());

        default:
            return false;
        }
    }

    private boolean c(Block block) {
        return block.d() && block.material.isSolid();
    }

    private boolean e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        IBlockData iblockdata1 = iblockdata;
        Iterator iterator = EnumDirectionLimit.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            EnumDirection enumdirection = (EnumDirection) iterator.next();
            BlockStateBoolean blockstateboolean = a(enumdirection);

            if (((Boolean) iblockdata.get(blockstateboolean)).booleanValue() && !this.c(world.getType(blockposition.shift(enumdirection)).getBlock())) {
                IBlockData iblockdata2 = world.getType(blockposition.up());

                if (iblockdata2.getBlock() != this || !((Boolean) iblockdata2.get(blockstateboolean)).booleanValue()) {
                    iblockdata = iblockdata.set(blockstateboolean, Boolean.valueOf(false));
                }
            }
        }

        if (d(iblockdata) == 0) {
            return false;
        } else {
            if (iblockdata1 != iblockdata) {
                world.setTypeAndData(blockposition, iblockdata, 2);
            }

            return true;
        }
    }

    public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
        if (!world.isStatic && !this.e(world, blockposition, iblockdata)) {
            this.b(world, blockposition, iblockdata, 0);
            world.setAir(blockposition);
        }

    }

    public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        if (!world.isStatic) {
            if (world.random.nextInt(4) == 0) {
                byte b0 = 4;
                int i = 5;
                boolean flag = false;

                label189:
                for (int j = -b0; j <= b0; ++j) {
                    for (int k = -b0; k <= b0; ++k) {
                        for (int l = -1; l <= 1; ++l) {
                            if (world.getType(blockposition.a(j, l, k)).getBlock() == this) {
                                --i;
                                if (i <= 0) {
                                    flag = true;
                                    break label189;
                                }
                            }
                        }
                    }
                }

                EnumDirection enumdirection = EnumDirection.a(random);
                EnumDirection enumdirection1;

                if (enumdirection == EnumDirection.UP && blockposition.getY() < 255 && world.isEmpty(blockposition.up())) {
                    if (!flag) {
                        IBlockData iblockdata1 = iblockdata;
                        Iterator iterator = EnumDirectionLimit.HORIZONTAL.iterator();

                        while (iterator.hasNext()) {
                            enumdirection1 = (EnumDirection) iterator.next();
                            if (random.nextBoolean() || !this.c(world.getType(blockposition.shift(enumdirection1).up()).getBlock())) {
                                iblockdata1 = iblockdata1.set(a(enumdirection1), Boolean.valueOf(false));
                            }
                        }

                        if (((Boolean) iblockdata1.get(BlockVine.NORTH)).booleanValue() || ((Boolean) iblockdata1.get(BlockVine.EAST)).booleanValue() || ((Boolean) iblockdata1.get(BlockVine.SOUTH)).booleanValue() || ((Boolean) iblockdata1.get(BlockVine.WEST)).booleanValue()) {
                            // CraftBukkit start - Call BlockSpreadEvent
                            // world.setTypeAndData(blockposition.up(), iblockdata1, 2);
                            BlockPosition target = blockposition.up();
                            org.bukkit.block.Block source = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
                            org.bukkit.block.Block block = world.getWorld().getBlockAt(target.getX(), target.getY(), target.getZ());
                            CraftEventFactory.handleBlockSpreadEvent(block, source, this, toLegacyData(iblockdata1));
                            // CraftBukkit end
                        }

                    }
                } else {
                    BlockPosition blockposition1;

                    if (enumdirection.k().c() && !((Boolean) iblockdata.get(a(enumdirection))).booleanValue()) {
                        if (!flag) {
                            blockposition1 = blockposition.shift(enumdirection);
                            Block block = world.getType(blockposition1).getBlock();

                            if (block.material == Material.AIR) {
                                enumdirection1 = enumdirection.e();
                                EnumDirection enumdirection2 = enumdirection.f();
                                boolean flag1 = ((Boolean) iblockdata.get(a(enumdirection1))).booleanValue();
                                boolean flag2 = ((Boolean) iblockdata.get(a(enumdirection2))).booleanValue();
                                BlockPosition blockposition2 = blockposition1.shift(enumdirection1);
                                BlockPosition blockposition3 = blockposition1.shift(enumdirection2);
                                
                                // CraftBukkit start - Call BlockSpreadEvent
                                org.bukkit.block.Block source = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
                                org.bukkit.block.Block bukkitBlock = world.getWorld().getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());

                                if (flag1 && this.c(world.getType(blockposition2).getBlock())) {
                                    // world.setTypeAndData(blockposition1, this.getBlockData().set(a(enumdirection1), Boolean.valueOf(true)), 2);
                                    CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, block, toLegacyData(this.getBlockData().set(a(enumdirection1), Boolean.valueOf(true))));
                                } else if (flag2 && this.c(world.getType(blockposition3).getBlock())) {
                                    // world.setTypeAndData(blockposition1, this.getBlockData().set(a(enumdirection2), Boolean.valueOf(true)), 2);
                                    CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, block, toLegacyData(this.getBlockData().set(a(enumdirection2), Boolean.valueOf(true))));
                                } else if (flag1 && world.isEmpty(blockposition2) && this.c(world.getType(blockposition.shift(enumdirection1)).getBlock())) {
                                    // world.setTypeAndData(blockposition2, this.getBlockData().set(a(enumdirection.opposite()), Boolean.valueOf(true)), 2);
                                    bukkitBlock = world.getWorld().getBlockAt(blockposition2.getX(), blockposition2.getY(), blockposition2.getZ());
                                    CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, block, toLegacyData(this.getBlockData().set(a(enumdirection.opposite()), Boolean.valueOf(true))));
                                } else if (flag2 && world.isEmpty(blockposition3) && this.c(world.getType(blockposition.shift(enumdirection2)).getBlock())) {
                                    // world.setTypeAndData(blockposition3, this.getBlockData().set(a(enumdirection.opposite()), Boolean.valueOf(true)), 2);
                                    bukkitBlock = world.getWorld().getBlockAt(blockposition3.getX(), blockposition3.getY(), blockposition3.getZ());
                                    CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, block, toLegacyData(this.getBlockData().set(a(enumdirection.opposite()), Boolean.valueOf(true))));
                                } else if (this.c(world.getType(blockposition1.up()).getBlock())) {
                                    // world.setTypeAndData(blockposition1, this.getBlockData(), 2);
                                    CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, block, toLegacyData(this.getBlockData()));
                                }
                                // CraftBukkit end
                            } else if (block.material.k() && block.d()) {
                                world.setTypeAndData(blockposition, iblockdata.set(a(enumdirection), Boolean.valueOf(true)), 2);
                            }

                        }
                    } else {
                        if (blockposition.getY() > 1) {
                            blockposition1 = blockposition.down();
                            IBlockData iblockdata2 = world.getType(blockposition1);
                            Block block1 = iblockdata2.getBlock();
                            IBlockData iblockdata3;
                            Iterator iterator1;
                            EnumDirection enumdirection3;

                            if (block1.material == Material.AIR) {
                                iblockdata3 = iblockdata;
                                iterator1 = EnumDirectionLimit.HORIZONTAL.iterator();

                                while (iterator1.hasNext()) {
                                    enumdirection3 = (EnumDirection) iterator1.next();
                                    if (random.nextBoolean()) {
                                        iblockdata3 = iblockdata3.set(a(enumdirection3), Boolean.valueOf(false));
                                    }
                                }

                                if (((Boolean) iblockdata3.get(BlockVine.NORTH)).booleanValue() || ((Boolean) iblockdata3.get(BlockVine.EAST)).booleanValue() || ((Boolean) iblockdata3.get(BlockVine.SOUTH)).booleanValue() || ((Boolean) iblockdata3.get(BlockVine.WEST)).booleanValue()) {
                                    // CraftBukkit start - Call BlockSpreadEvent                                    
                                    // world.setTypeAndData(blockposition1, iblockdata3, 2);
                                    org.bukkit.block.Block source = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
                                    org.bukkit.block.Block bukkitBlock = world.getWorld().getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
                                    CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, toLegacyData(iblockdata3));
                                    // CraftBukkit end
                                }
                            } else if (block1 == this) {
                                iblockdata3 = iblockdata2;
                                iterator1 = EnumDirectionLimit.HORIZONTAL.iterator();

                                while (iterator1.hasNext()) {
                                    enumdirection3 = (EnumDirection) iterator1.next();
                                    BlockStateBoolean blockstateboolean = a(enumdirection3);

                                    if (random.nextBoolean() || !((Boolean) iblockdata.get(blockstateboolean)).booleanValue()) {
                                        iblockdata3 = iblockdata3.set(blockstateboolean, Boolean.valueOf(false));
                                    }
                                }

                                if (((Boolean) iblockdata3.get(BlockVine.NORTH)).booleanValue() || ((Boolean) iblockdata3.get(BlockVine.EAST)).booleanValue() || ((Boolean) iblockdata3.get(BlockVine.SOUTH)).booleanValue() || ((Boolean) iblockdata3.get(BlockVine.WEST)).booleanValue()) {
                                    world.setTypeAndData(blockposition1, iblockdata3, 2);
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private static int b(EnumDirection enumdirection) {
        return 1 << enumdirection.b();
    }

    public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
        IBlockData iblockdata = this.getBlockData().set(BlockVine.UP, Boolean.valueOf(false)).set(BlockVine.NORTH, Boolean.valueOf(false)).set(BlockVine.EAST, Boolean.valueOf(false)).set(BlockVine.SOUTH, Boolean.valueOf(false)).set(BlockVine.WEST, Boolean.valueOf(false));

        return enumdirection.k().c() ? iblockdata.set(a(enumdirection.opposite()), Boolean.valueOf(true)) : iblockdata;
    }

    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return null;
    }

    public int a(Random random) {
        return 0;
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, TileEntity tileentity) {
        if (!world.isStatic && entityhuman.bY() != null && entityhuman.bY().getItem() == Items.SHEARS) {
            entityhuman.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
            a(world, blockposition, new ItemStack(Blocks.VINE, 1, 0));
        } else {
            super.a(world, entityhuman, blockposition, iblockdata, tileentity);
        }

    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockVine.NORTH, Boolean.valueOf((i & BlockVine.R) > 0)).set(BlockVine.EAST, Boolean.valueOf((i & BlockVine.S) > 0)).set(BlockVine.SOUTH, Boolean.valueOf((i & BlockVine.Q) > 0)).set(BlockVine.WEST, Boolean.valueOf((i & BlockVine.T) > 0));
    }

    public int toLegacyData(IBlockData iblockdata) {
        int i = 0;

        if (((Boolean) iblockdata.get(BlockVine.NORTH)).booleanValue()) {
            i |= BlockVine.R;
        }

        if (((Boolean) iblockdata.get(BlockVine.EAST)).booleanValue()) {
            i |= BlockVine.S;
        }

        if (((Boolean) iblockdata.get(BlockVine.SOUTH)).booleanValue()) {
            i |= BlockVine.Q;
        }

        if (((Boolean) iblockdata.get(BlockVine.WEST)).booleanValue()) {
            i |= BlockVine.T;
        }

        return i;
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockVine.UP, BlockVine.NORTH, BlockVine.EAST, BlockVine.SOUTH, BlockVine.WEST});
    }

    public static BlockStateBoolean a(EnumDirection enumdirection) {
        switch (SwitchHelperDirection12.a[enumdirection.ordinal()]) {
        case 1:
            return BlockVine.UP;

        case 2:
            return BlockVine.NORTH;

        case 3:
            return BlockVine.SOUTH;

        case 4:
            return BlockVine.EAST;

        case 5:
            return BlockVine.WEST;

        default:
            throw new IllegalArgumentException(enumdirection + " is an invalid choice");
        }
    }

    public static int d(IBlockData iblockdata) {
        int i = 0;
        BlockStateBoolean[] ablockstateboolean = BlockVine.P;
        int j = ablockstateboolean.length;

        for (int k = 0; k < j; ++k) {
            BlockStateBoolean blockstateboolean = ablockstateboolean[k];

            if (((Boolean) iblockdata.get(blockstateboolean)).booleanValue()) {
                ++i;
            }
        }

        return i;
    }
}
