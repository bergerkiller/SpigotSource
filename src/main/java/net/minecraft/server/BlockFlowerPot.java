package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockFlowerPot extends BlockTileEntity {

    public static final BlockStateInteger LEGACY_DATA = BlockStateInteger.of("legacy_data", 0, 15);
    public static final BlockStateEnum<BlockFlowerPot.EnumFlowerPotContents> CONTENTS = BlockStateEnum.of("contents", BlockFlowerPot.EnumFlowerPotContents.class);
    protected static final AxisAlignedBB c = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.375D, 0.6875D);

    public BlockFlowerPot() {
        super(Material.ORIENTABLE);
        this.w(this.blockStateList.getBlockData().set(BlockFlowerPot.CONTENTS, BlockFlowerPot.EnumFlowerPotContents.EMPTY).set(BlockFlowerPot.LEGACY_DATA, Integer.valueOf(0)));
    }

    public String getName() {
        return LocaleI18n.get("item.flowerPot.name");
    }

    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return BlockFlowerPot.c;
    }

    public boolean b(IBlockData iblockdata) {
        return false;
    }

    public EnumRenderType a(IBlockData iblockdata) {
        return EnumRenderType.MODEL;
    }

    public boolean c(IBlockData iblockdata) {
        return false;
    }

    public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumHand enumhand, @Nullable ItemStack itemstack, EnumDirection enumdirection, float f, float f1, float f2) {
        if (itemstack != null && itemstack.getItem() instanceof ItemBlock) {
            TileEntityFlowerPot tileentityflowerpot = this.c(world, blockposition);

            if (tileentityflowerpot == null) {
                return false;
            } else if (tileentityflowerpot.d() != null) {
                return false;
            } else {
                Block block = Block.asBlock(itemstack.getItem());

                if (!this.a(block, itemstack.getData())) {
                    return false;
                } else {
                    tileentityflowerpot.a(itemstack.getItem(), itemstack.getData());
                    tileentityflowerpot.update();
                    world.notify(blockposition, iblockdata, iblockdata, 3);
                    entityhuman.b(StatisticList.V);
                    if (!entityhuman.abilities.canInstantlyBuild) {
                        --itemstack.count;
                    }

                    return true;
                }
            }
        } else {
            return false;
        }
    }

    private boolean a(@Nullable Block block, int i) {
        return block != Blocks.YELLOW_FLOWER && block != Blocks.RED_FLOWER && block != Blocks.CACTUS && block != Blocks.BROWN_MUSHROOM && block != Blocks.RED_MUSHROOM && block != Blocks.SAPLING && block != Blocks.DEADBUSH ? block == Blocks.TALLGRASS && i == BlockLongGrass.EnumTallGrassType.FERN.a() : true;
    }

    public ItemStack a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        TileEntityFlowerPot tileentityflowerpot = this.c(world, blockposition);

        if (tileentityflowerpot != null) {
            ItemStack itemstack = tileentityflowerpot.c();

            if (itemstack != null) {
                return itemstack;
            }
        }

        return new ItemStack(Items.FLOWER_POT);
    }

    public boolean canPlace(World world, BlockPosition blockposition) {
        return super.canPlace(world, blockposition) && world.getType(blockposition.down()).q();
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
        if (!world.getType(blockposition.down()).q()) {
            this.b(world, blockposition, iblockdata, 0);
            world.setAir(blockposition);
        }

    }

    public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
        TileEntityFlowerPot tileentityflowerpot = this.c(world, blockposition);

        if (tileentityflowerpot != null && tileentityflowerpot.d() != null) {
            a(world, blockposition, new ItemStack(tileentityflowerpot.d(), 1, tileentityflowerpot.e()));
        }

        super.remove(world, blockposition, iblockdata);
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
        super.a(world, blockposition, iblockdata, entityhuman);
        if (entityhuman.abilities.canInstantlyBuild) {
            TileEntityFlowerPot tileentityflowerpot = this.c(world, blockposition);

            if (tileentityflowerpot != null) {
                tileentityflowerpot.a((Item) null, 0);
            }
        }

    }

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return Items.FLOWER_POT;
    }

    @Nullable
    private TileEntityFlowerPot c(World world, BlockPosition blockposition) {
        TileEntity tileentity = world.getTileEntity(blockposition);

        return tileentity instanceof TileEntityFlowerPot ? (TileEntityFlowerPot) tileentity : null;
    }

    public TileEntity a(World world, int i) {
        Object object = null;
        int j = 0;

        switch (i) {
        case 1:
            object = Blocks.RED_FLOWER;
            j = BlockFlowers.EnumFlowerVarient.POPPY.b();
            break;

        case 2:
            object = Blocks.YELLOW_FLOWER;
            break;

        case 3:
            object = Blocks.SAPLING;
            j = BlockWood.EnumLogVariant.OAK.a();
            break;

        case 4:
            object = Blocks.SAPLING;
            j = BlockWood.EnumLogVariant.SPRUCE.a();
            break;

        case 5:
            object = Blocks.SAPLING;
            j = BlockWood.EnumLogVariant.BIRCH.a();
            break;

        case 6:
            object = Blocks.SAPLING;
            j = BlockWood.EnumLogVariant.JUNGLE.a();
            break;

        case 7:
            object = Blocks.RED_MUSHROOM;
            break;

        case 8:
            object = Blocks.BROWN_MUSHROOM;
            break;

        case 9:
            object = Blocks.CACTUS;
            break;

        case 10:
            object = Blocks.DEADBUSH;
            break;

        case 11:
            object = Blocks.TALLGRASS;
            j = BlockLongGrass.EnumTallGrassType.FERN.a();
            break;

        case 12:
            object = Blocks.SAPLING;
            j = BlockWood.EnumLogVariant.ACACIA.a();
            break;

        case 13:
            object = Blocks.SAPLING;
            j = BlockWood.EnumLogVariant.DARK_OAK.a();
        }

        return new TileEntityFlowerPot(Item.getItemOf((Block) object), j);
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockFlowerPot.CONTENTS, BlockFlowerPot.LEGACY_DATA});
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((Integer) iblockdata.get(BlockFlowerPot.LEGACY_DATA)).intValue();
    }

    public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        BlockFlowerPot.EnumFlowerPotContents blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.EMPTY;
        TileEntity tileentity = iblockaccess.getTileEntity(blockposition);

        if (tileentity instanceof TileEntityFlowerPot) {
            TileEntityFlowerPot tileentityflowerpot = (TileEntityFlowerPot) tileentity;
            Item item = tileentityflowerpot.d();

            if (item instanceof ItemBlock) {
                int i = tileentityflowerpot.e();
                Block block = Block.asBlock(item);

                if (block == Blocks.SAPLING) {
                    switch (BlockFlowerPot.SyntheticClass_1.a[BlockWood.EnumLogVariant.a(i).ordinal()]) {
                    case 1:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.OAK_SAPLING;
                        break;

                    case 2:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.SPRUCE_SAPLING;
                        break;

                    case 3:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.BIRCH_SAPLING;
                        break;

                    case 4:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.JUNGLE_SAPLING;
                        break;

                    case 5:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.ACACIA_SAPLING;
                        break;

                    case 6:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.DARK_OAK_SAPLING;
                        break;

                    default:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.EMPTY;
                    }
                } else if (block == Blocks.TALLGRASS) {
                    switch (i) {
                    case 0:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.DEAD_BUSH;
                        break;

                    case 2:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.FERN;
                        break;

                    default:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.EMPTY;
                    }
                } else if (block == Blocks.YELLOW_FLOWER) {
                    blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.DANDELION;
                } else if (block == Blocks.RED_FLOWER) {
                    switch (BlockFlowerPot.SyntheticClass_1.b[BlockFlowers.EnumFlowerVarient.a(BlockFlowers.EnumFlowerType.RED, i).ordinal()]) {
                    case 1:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.POPPY;
                        break;

                    case 2:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.BLUE_ORCHID;
                        break;

                    case 3:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.ALLIUM;
                        break;

                    case 4:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.HOUSTONIA;
                        break;

                    case 5:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.RED_TULIP;
                        break;

                    case 6:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.ORANGE_TULIP;
                        break;

                    case 7:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.WHITE_TULIP;
                        break;

                    case 8:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.PINK_TULIP;
                        break;

                    case 9:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.OXEYE_DAISY;
                        break;

                    default:
                        blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.EMPTY;
                    }
                } else if (block == Blocks.RED_MUSHROOM) {
                    blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.MUSHROOM_RED;
                } else if (block == Blocks.BROWN_MUSHROOM) {
                    blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.MUSHROOM_BROWN;
                } else if (block == Blocks.DEADBUSH) {
                    blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.DEAD_BUSH;
                } else if (block == Blocks.CACTUS) {
                    blockflowerpot_enumflowerpotcontents = BlockFlowerPot.EnumFlowerPotContents.CACTUS;
                }
            }
        }

        return iblockdata.set(BlockFlowerPot.CONTENTS, blockflowerpot_enumflowerpotcontents);
    }

    static class SyntheticClass_1 {

        static final int[] a;
        static final int[] b = new int[BlockFlowers.EnumFlowerVarient.values().length];

        static {
            try {
                BlockFlowerPot.SyntheticClass_1.b[BlockFlowers.EnumFlowerVarient.POPPY.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                BlockFlowerPot.SyntheticClass_1.b[BlockFlowers.EnumFlowerVarient.BLUE_ORCHID.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                BlockFlowerPot.SyntheticClass_1.b[BlockFlowers.EnumFlowerVarient.ALLIUM.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                BlockFlowerPot.SyntheticClass_1.b[BlockFlowers.EnumFlowerVarient.HOUSTONIA.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                BlockFlowerPot.SyntheticClass_1.b[BlockFlowers.EnumFlowerVarient.RED_TULIP.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                BlockFlowerPot.SyntheticClass_1.b[BlockFlowers.EnumFlowerVarient.ORANGE_TULIP.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                BlockFlowerPot.SyntheticClass_1.b[BlockFlowers.EnumFlowerVarient.WHITE_TULIP.ordinal()] = 7;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                BlockFlowerPot.SyntheticClass_1.b[BlockFlowers.EnumFlowerVarient.PINK_TULIP.ordinal()] = 8;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                BlockFlowerPot.SyntheticClass_1.b[BlockFlowers.EnumFlowerVarient.OXEYE_DAISY.ordinal()] = 9;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            a = new int[BlockWood.EnumLogVariant.values().length];

            try {
                BlockFlowerPot.SyntheticClass_1.a[BlockWood.EnumLogVariant.OAK.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                BlockFlowerPot.SyntheticClass_1.a[BlockWood.EnumLogVariant.SPRUCE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                BlockFlowerPot.SyntheticClass_1.a[BlockWood.EnumLogVariant.BIRCH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                BlockFlowerPot.SyntheticClass_1.a[BlockWood.EnumLogVariant.JUNGLE.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                BlockFlowerPot.SyntheticClass_1.a[BlockWood.EnumLogVariant.ACACIA.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                BlockFlowerPot.SyntheticClass_1.a[BlockWood.EnumLogVariant.DARK_OAK.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

        }
    }

    public static enum EnumFlowerPotContents implements INamable {

        EMPTY("empty"), POPPY("rose"), BLUE_ORCHID("blue_orchid"), ALLIUM("allium"), HOUSTONIA("houstonia"), RED_TULIP("red_tulip"), ORANGE_TULIP("orange_tulip"), WHITE_TULIP("white_tulip"), PINK_TULIP("pink_tulip"), OXEYE_DAISY("oxeye_daisy"), DANDELION("dandelion"), OAK_SAPLING("oak_sapling"), SPRUCE_SAPLING("spruce_sapling"), BIRCH_SAPLING("birch_sapling"), JUNGLE_SAPLING("jungle_sapling"), ACACIA_SAPLING("acacia_sapling"), DARK_OAK_SAPLING("dark_oak_sapling"), MUSHROOM_RED("mushroom_red"), MUSHROOM_BROWN("mushroom_brown"), DEAD_BUSH("dead_bush"), FERN("fern"), CACTUS("cactus");

        private final String w;

        private EnumFlowerPotContents(String s) {
            this.w = s;
        }

        public String toString() {
            return this.w;
        }

        public String getName() {
            return this.w;
        }
    }
}
