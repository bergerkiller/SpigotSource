package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class WorldGenEndCityPieces {

    public static final DefinedStructureManager a = new DefinedStructureManager();
    private static final DefinedStructureInfo b = (new DefinedStructureInfo()).a(true);
    private static final DefinedStructureInfo c = (new DefinedStructureInfo()).a(true).a(Blocks.AIR);
    private static final WorldGenEndCityPieces.PieceGenerator d = new WorldGenEndCityPieces.PieceGenerator() {
        public void a() {}

        public boolean a(int i, WorldGenEndCityPieces.Piece worldgenendcitypieces_piece, BlockPosition blockposition, List<StructurePiece> list, Random random) {
            if (i > 8) {
                return false;
            } else {
                EnumBlockRotation enumblockrotation = worldgenendcitypieces_piece.b.c();
                WorldGenEndCityPieces.Piece worldgenendcitypieces_piece1;

                list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece, blockposition, "base_floor", enumblockrotation, true));
                int j = random.nextInt(3);

                if (j == 0) {
                    list.add(WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(-1, 4, -1), "base_roof", enumblockrotation, true));
                } else if (j == 1) {
                    list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(-1, 0, -1), "second_floor_2", enumblockrotation, false));
                    list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(-1, 8, -1), "second_roof", enumblockrotation, false));
                    WorldGenEndCityPieces.b(WorldGenEndCityPieces.f, i + 1, worldgenendcitypieces_piece1, (BlockPosition) null, list, random);
                } else if (j == 2) {
                    list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(-1, 0, -1), "second_floor_2", enumblockrotation, false));
                    list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(-1, 4, -1), "third_floor_c", enumblockrotation, false));
                    list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(-1, 8, -1), "third_roof", enumblockrotation, true));
                    WorldGenEndCityPieces.b(WorldGenEndCityPieces.f, i + 1, worldgenendcitypieces_piece1, (BlockPosition) null, list, random);
                }

                return true;
            }
        }
    };
    private static final List<Tuple<EnumBlockRotation, BlockPosition>> e = Lists.newArrayList(new Tuple[] { new Tuple(EnumBlockRotation.NONE, new BlockPosition(1, -1, 0)), new Tuple(EnumBlockRotation.CLOCKWISE_90, new BlockPosition(6, -1, 1)), new Tuple(EnumBlockRotation.COUNTERCLOCKWISE_90, new BlockPosition(0, -1, 5)), new Tuple(EnumBlockRotation.CLOCKWISE_180, new BlockPosition(5, -1, 6))});
    private static final WorldGenEndCityPieces.PieceGenerator f = new WorldGenEndCityPieces.PieceGenerator() {
        public void a() {}

        public boolean a(int i, WorldGenEndCityPieces.Piece worldgenendcitypieces_piece, BlockPosition blockposition, List<StructurePiece> list, Random random) {
            EnumBlockRotation enumblockrotation = worldgenendcitypieces_piece.b.c();
            WorldGenEndCityPieces.Piece worldgenendcitypieces_piece1;

            list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece, new BlockPosition(3 + random.nextInt(2), -3, 3 + random.nextInt(2)), "tower_base", enumblockrotation, true));
            list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(0, 7, 0), "tower_piece", enumblockrotation, true));
            WorldGenEndCityPieces.Piece worldgenendcitypieces_piece2 = random.nextInt(3) == 0 ? worldgenendcitypieces_piece1 : null;
            int j = 1 + random.nextInt(3);

            for (int k = 0; k < j; ++k) {
                list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(0, 4, 0), "tower_piece", enumblockrotation, true));
                if (k < j - 1 && random.nextBoolean()) {
                    worldgenendcitypieces_piece2 = worldgenendcitypieces_piece1;
                }
            }

            if (worldgenendcitypieces_piece2 != null) {
                Iterator iterator = WorldGenEndCityPieces.e.iterator();

                while (iterator.hasNext()) {
                    Tuple tuple = (Tuple) iterator.next();

                    if (random.nextBoolean()) {
                        WorldGenEndCityPieces.Piece worldgenendcitypieces_piece3;

                        list.add(worldgenendcitypieces_piece3 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece2, (BlockPosition) tuple.b(), "bridge_end", enumblockrotation.a((EnumBlockRotation) tuple.a()), true));
                        WorldGenEndCityPieces.b(WorldGenEndCityPieces.g, i + 1, worldgenendcitypieces_piece3, (BlockPosition) null, list, random);
                    }
                }

                list.add(WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(-1, 4, -1), "tower_top", enumblockrotation, true));
            } else {
                if (i != 7) {
                    return WorldGenEndCityPieces.b(WorldGenEndCityPieces.i, i + 1, worldgenendcitypieces_piece1, (BlockPosition) null, list, random);
                }

                list.add(WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(-1, 4, -1), "tower_top", enumblockrotation, true));
            }

            return true;
        }
    };
    private static final WorldGenEndCityPieces.PieceGenerator g = new WorldGenEndCityPieces.PieceGenerator() {
        public boolean a;

        public void a() {
            this.a = false;
        }

        public boolean a(int i, WorldGenEndCityPieces.Piece worldgenendcitypieces_piece, BlockPosition blockposition, List<StructurePiece> list, Random random) {
            EnumBlockRotation enumblockrotation = worldgenendcitypieces_piece.b.c();
            int j = random.nextInt(4) + 1;
            WorldGenEndCityPieces.Piece worldgenendcitypieces_piece1;

            list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece, new BlockPosition(0, 0, -4), "bridge_piece", enumblockrotation, true));
            worldgenendcitypieces_piece1.m = -1;
            byte b0 = 0;

            for (int k = 0; k < j; ++k) {
                if (random.nextBoolean()) {
                    list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(0, b0, -4), "bridge_piece", enumblockrotation, true));
                    b0 = 0;
                } else {
                    if (random.nextBoolean()) {
                        list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(0, b0, -4), "bridge_steep_stairs", enumblockrotation, true));
                    } else {
                        list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(0, b0, -8), "bridge_gentle_stairs", enumblockrotation, true));
                    }

                    b0 = 4;
                }
            }

            if (!this.a && random.nextInt(10 - i) == 0) {
                list.add(WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(-8 + random.nextInt(8), b0, -70 + random.nextInt(10)), "ship", enumblockrotation, true));
                this.a = true;
            } else if (!WorldGenEndCityPieces.b(WorldGenEndCityPieces.d, i + 1, worldgenendcitypieces_piece1, new BlockPosition(-3, b0 + 1, -11), list, random)) {
                return false;
            }

            list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(4, b0, 0), "bridge_end", enumblockrotation.a(EnumBlockRotation.CLOCKWISE_180), true));
            worldgenendcitypieces_piece1.m = -1;
            return true;
        }
    };
    private static final List<Tuple<EnumBlockRotation, BlockPosition>> h = Lists.newArrayList(new Tuple[] { new Tuple(EnumBlockRotation.NONE, new BlockPosition(4, -1, 0)), new Tuple(EnumBlockRotation.CLOCKWISE_90, new BlockPosition(12, -1, 4)), new Tuple(EnumBlockRotation.COUNTERCLOCKWISE_90, new BlockPosition(0, -1, 8)), new Tuple(EnumBlockRotation.CLOCKWISE_180, new BlockPosition(8, -1, 12))});
    private static final WorldGenEndCityPieces.PieceGenerator i = new WorldGenEndCityPieces.PieceGenerator() {
        public void a() {}

        public boolean a(int i, WorldGenEndCityPieces.Piece worldgenendcitypieces_piece, BlockPosition blockposition, List<StructurePiece> list, Random random) {
            EnumBlockRotation enumblockrotation = worldgenendcitypieces_piece.b.c();
            WorldGenEndCityPieces.Piece worldgenendcitypieces_piece1;

            list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece, new BlockPosition(-3, 4, -3), "fat_tower_base", enumblockrotation, true));
            list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(0, 4, 0), "fat_tower_middle", enumblockrotation, true));

            for (int j = 0; j < 2 && random.nextInt(3) != 0; ++j) {
                list.add(worldgenendcitypieces_piece1 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(0, 8, 0), "fat_tower_middle", enumblockrotation, true));
                Iterator iterator = WorldGenEndCityPieces.h.iterator();

                while (iterator.hasNext()) {
                    Tuple tuple = (Tuple) iterator.next();

                    if (random.nextBoolean()) {
                        WorldGenEndCityPieces.Piece worldgenendcitypieces_piece2;

                        list.add(worldgenendcitypieces_piece2 = WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, (BlockPosition) tuple.b(), "bridge_end", enumblockrotation.a((EnumBlockRotation) tuple.a()), true));
                        WorldGenEndCityPieces.b(WorldGenEndCityPieces.g, i + 1, worldgenendcitypieces_piece2, (BlockPosition) null, list, random);
                    }
                }
            }

            list.add(WorldGenEndCityPieces.b(worldgenendcitypieces_piece1, new BlockPosition(-2, 8, -2), "fat_tower_top", enumblockrotation, true));
            return true;
        }
    };

    public static void a() {
        WorldGenFactory.a(WorldGenEndCityPieces.Piece.class, "ECP");
    }

    private static WorldGenEndCityPieces.Piece b(WorldGenEndCityPieces.Piece worldgenendcitypieces_piece, BlockPosition blockposition, String s, EnumBlockRotation enumblockrotation, boolean flag) {
        WorldGenEndCityPieces.Piece worldgenendcitypieces_piece1 = new WorldGenEndCityPieces.Piece(s, worldgenendcitypieces_piece.c, enumblockrotation, flag);
        BlockPosition blockposition1 = worldgenendcitypieces_piece.a.a(worldgenendcitypieces_piece.b, blockposition, worldgenendcitypieces_piece1.b, BlockPosition.ZERO);

        worldgenendcitypieces_piece1.a(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
        return worldgenendcitypieces_piece1;
    }

    public static void a(BlockPosition blockposition, EnumBlockRotation enumblockrotation, List<StructurePiece> list, Random random) {
        WorldGenEndCityPieces.i.a();
        WorldGenEndCityPieces.d.a();
        WorldGenEndCityPieces.g.a();
        WorldGenEndCityPieces.f.a();
        WorldGenEndCityPieces.Piece worldgenendcitypieces_piece = new WorldGenEndCityPieces.Piece("base_floor", blockposition, enumblockrotation, true);

        list.add(worldgenendcitypieces_piece);
        WorldGenEndCityPieces.Piece worldgenendcitypieces_piece1;

        list.add(worldgenendcitypieces_piece1 = b(worldgenendcitypieces_piece, new BlockPosition(-1, 0, -1), "second_floor", enumblockrotation, false));
        list.add(worldgenendcitypieces_piece1 = b(worldgenendcitypieces_piece1, new BlockPosition(-1, 4, -1), "third_floor", enumblockrotation, false));
        list.add(worldgenendcitypieces_piece1 = b(worldgenendcitypieces_piece1, new BlockPosition(-1, 8, -1), "third_roof", enumblockrotation, true));
        b(WorldGenEndCityPieces.f, 1, worldgenendcitypieces_piece1, (BlockPosition) null, list, random);
    }

    private static boolean b(WorldGenEndCityPieces.PieceGenerator worldgenendcitypieces_piecegenerator, int i, WorldGenEndCityPieces.Piece worldgenendcitypieces_piece, BlockPosition blockposition, List<StructurePiece> list, Random random) {
        if (i > 8) {
            return false;
        } else {
            ArrayList arraylist = Lists.newArrayList();

            if (worldgenendcitypieces_piecegenerator.a(i, worldgenendcitypieces_piece, blockposition, arraylist, random)) {
                boolean flag = false;
                int j = random.nextInt();
                Iterator iterator = arraylist.iterator();

                while (iterator.hasNext()) {
                    StructurePiece structurepiece = (StructurePiece) iterator.next();

                    structurepiece.m = j;
                    StructurePiece structurepiece1 = StructurePiece.a(list, structurepiece.c());

                    if (structurepiece1 != null && structurepiece1.m != worldgenendcitypieces_piece.m) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    list.addAll(arraylist);
                    return true;
                }
            }

            return false;
        }
    }

    interface PieceGenerator {

        void a();

        boolean a(int i, WorldGenEndCityPieces.Piece worldgenendcitypieces_piece, BlockPosition blockposition, List<StructurePiece> list, Random random);
    }

    public static class Piece extends DefinedStructurePiece {

        private String d;
        private EnumBlockRotation e;
        private boolean f;

        public Piece() {}

        public Piece(String s, BlockPosition blockposition, EnumBlockRotation enumblockrotation, boolean flag) {
            super(0);
            this.d = s;
            this.e = enumblockrotation;
            this.f = flag;
            this.a(blockposition);
        }

        private void a(BlockPosition blockposition) {
            DefinedStructure definedstructure = WorldGenEndCityPieces.a.a((MinecraftServer) null, new MinecraftKey("endcity/" + this.d));
            DefinedStructureInfo definedstructureinfo;

            if (this.f) {
                definedstructureinfo = WorldGenEndCityPieces.b.a().a(this.e);
            } else {
                definedstructureinfo = WorldGenEndCityPieces.c.a().a(this.e);
            }

            this.a(definedstructure, blockposition, definedstructureinfo);
        }

        protected void a(NBTTagCompound nbttagcompound) {
            super.a(nbttagcompound);
            nbttagcompound.setString("Template", this.d);
            nbttagcompound.setString("Rot", this.e.name());
            nbttagcompound.setBoolean("OW", this.f);
        }

        protected void b(NBTTagCompound nbttagcompound) {
            super.b(nbttagcompound);
            this.d = nbttagcompound.getString("Template");
            this.e = EnumBlockRotation.valueOf(nbttagcompound.getString("Rot"));
            this.f = nbttagcompound.getBoolean("OW");
            this.a(this.c);
        }

        protected void a(String s, BlockPosition blockposition, World world, Random random, StructureBoundingBox structureboundingbox) {
            if (s.startsWith("Chest")) {
                BlockPosition blockposition1 = blockposition.down();

                if (structureboundingbox.b((BaseBlockPosition) blockposition1)) {
                    TileEntity tileentity = world.getTileEntity(blockposition1);

                    if (tileentity instanceof TileEntityChest) {
                        ((TileEntityChest) tileentity).a(LootTables.c, random.nextLong());
                    }
                }
            } else if (s.startsWith("Sentry")) {
                EntityShulker entityshulker = new EntityShulker(world);

                entityshulker.setPosition((double) blockposition.getX() + 0.5D, (double) blockposition.getY() + 0.5D, (double) blockposition.getZ() + 0.5D);
                entityshulker.g(blockposition);
                world.addEntity(entityshulker);
            } else if (s.startsWith("Elytra")) {
                EntityItemFrame entityitemframe = new EntityItemFrame(world, blockposition, this.e.a(EnumDirection.SOUTH));

                entityitemframe.setItem(new ItemStack(Items.cR));
                world.addEntity(entityitemframe);
            }

        }
    }
}
