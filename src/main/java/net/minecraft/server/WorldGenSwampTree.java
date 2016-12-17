package net.minecraft.server;

import java.util.Random;

public class WorldGenSwampTree extends WorldGenTreeAbstract {

    private static final IBlockData a = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.OAK);
    private static final IBlockData b = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.OAK).set(BlockLeaves1.CHECK_DECAY, Boolean.valueOf(false));

    public WorldGenSwampTree() {
        super(false);
    }

    public boolean generate(World world, Random random, BlockPosition blockposition) {
        int i;

        for (i = random.nextInt(4) + 5; world.getType(blockposition.down()).getMaterial() == Material.WATER; blockposition = blockposition.down()) {
            ;
        }

        boolean flag = true;

        if (blockposition.getY() >= 1 && blockposition.getY() + i + 1 <= 256) {
            int j;
            int k;

            for (int l = blockposition.getY(); l <= blockposition.getY() + 1 + i; ++l) {
                byte b0 = 1;

                if (l == blockposition.getY()) {
                    b0 = 0;
                }

                if (l >= blockposition.getY() + 1 + i - 2) {
                    b0 = 3;
                }

                BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

                for (j = blockposition.getX() - b0; j <= blockposition.getX() + b0 && flag; ++j) {
                    for (k = blockposition.getZ() - b0; k <= blockposition.getZ() + b0 && flag; ++k) {
                        if (l >= 0 && l < 256) {
                            IBlockData iblockdata = world.getType(blockposition_mutableblockposition.c(j, l, k));
                            Block block = iblockdata.getBlock();

                            if (iblockdata.getMaterial() != Material.AIR && iblockdata.getMaterial() != Material.LEAVES) {
                                if (block != Blocks.WATER && block != Blocks.FLOWING_WATER) {
                                    flag = false;
                                } else if (l > blockposition.getY()) {
                                    flag = false;
                                }
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else {
                Block block1 = world.getType(blockposition.down()).getBlock();

                if ((block1 == Blocks.GRASS || block1 == Blocks.DIRT) && blockposition.getY() < 256 - i - 1) {
                    this.a(world, blockposition.down());

                    BlockPosition blockposition1;
                    int i1;
                    int j1;
                    int k1;
                    int l1;

                    for (i1 = blockposition.getY() - 3 + i; i1 <= blockposition.getY() + i; ++i1) {
                        j1 = i1 - (blockposition.getY() + i);
                        j = 2 - j1 / 2;

                        for (k = blockposition.getX() - j; k <= blockposition.getX() + j; ++k) {
                            k1 = k - blockposition.getX();

                            for (l1 = blockposition.getZ() - j; l1 <= blockposition.getZ() + j; ++l1) {
                                int i2 = l1 - blockposition.getZ();

                                if (Math.abs(k1) != j || Math.abs(i2) != j || random.nextInt(2) != 0 && j1 != 0) {
                                    blockposition1 = new BlockPosition(k, i1, l1);
                                    if (!world.getType(blockposition1).b()) {
                                        this.a(world, blockposition1, WorldGenSwampTree.b);
                                    }
                                }
                            }
                        }
                    }

                    for (i1 = 0; i1 < i; ++i1) {
                        IBlockData iblockdata1 = world.getType(blockposition.up(i1));
                        Block block2 = iblockdata1.getBlock();

                        if (iblockdata1.getMaterial() == Material.AIR || iblockdata1.getMaterial() == Material.LEAVES || block2 == Blocks.FLOWING_WATER || block2 == Blocks.WATER) {
                            this.a(world, blockposition.up(i1), WorldGenSwampTree.a);
                        }
                    }

                    for (i1 = blockposition.getY() - 3 + i; i1 <= blockposition.getY() + i; ++i1) {
                        j1 = i1 - (blockposition.getY() + i);
                        j = 2 - j1 / 2;
                        BlockPosition.MutableBlockPosition blockposition_mutableblockposition1 = new BlockPosition.MutableBlockPosition();

                        for (k1 = blockposition.getX() - j; k1 <= blockposition.getX() + j; ++k1) {
                            for (l1 = blockposition.getZ() - j; l1 <= blockposition.getZ() + j; ++l1) {
                                blockposition_mutableblockposition1.c(k1, i1, l1);
                                if (world.getType(blockposition_mutableblockposition1).getMaterial() == Material.LEAVES) {
                                    BlockPosition blockposition2 = blockposition_mutableblockposition1.west();

                                    blockposition1 = blockposition_mutableblockposition1.east();
                                    BlockPosition blockposition3 = blockposition_mutableblockposition1.north();
                                    BlockPosition blockposition4 = blockposition_mutableblockposition1.south();

                                    if (random.nextInt(4) == 0 && world.getType(blockposition2).getMaterial() == Material.AIR) {
                                        this.a(world, blockposition2, BlockVine.EAST);
                                    }

                                    if (random.nextInt(4) == 0 && world.getType(blockposition1).getMaterial() == Material.AIR) {
                                        this.a(world, blockposition1, BlockVine.WEST);
                                    }

                                    if (random.nextInt(4) == 0 && world.getType(blockposition3).getMaterial() == Material.AIR) {
                                        this.a(world, blockposition3, BlockVine.SOUTH);
                                    }

                                    if (random.nextInt(4) == 0 && world.getType(blockposition4).getMaterial() == Material.AIR) {
                                        this.a(world, blockposition4, BlockVine.NORTH);
                                    }
                                }
                            }
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    private void a(World world, BlockPosition blockposition, BlockStateBoolean blockstateboolean) {
        IBlockData iblockdata = Blocks.VINE.getBlockData().set(blockstateboolean, Boolean.valueOf(true));

        this.a(world, blockposition, iblockdata);
        int i = 4;

        for (blockposition = blockposition.down(); world.getType(blockposition).getMaterial() == Material.AIR && i > 0; --i) {
            this.a(world, blockposition, iblockdata);
            blockposition = blockposition.down();
        }

    }
}
