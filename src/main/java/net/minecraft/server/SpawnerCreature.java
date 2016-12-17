package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

// CraftBukkit start
import org.bukkit.craftbukkit.util.LongHash;
import org.bukkit.craftbukkit.util.LongHashSet;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
// CraftBukkit end

public final class SpawnerCreature {

    private static final int a = (int) Math.pow(17.0D, 2.0D);
    private final LongHashSet b = new LongHashSet(); // CraftBukkit

    public SpawnerCreature() {}

    // Spigot start - get entity count only from chunks being processed in b
    private int getEntityCount(WorldServer server, Class oClass)
    {
        int i = 0;
        Iterator<Long> it = this.b.iterator();
        while ( it.hasNext() )
        {
            Long coord = it.next();
            int x = LongHash.msw( coord );
            int z = LongHash.lsw( coord );
            if ( !((ChunkProviderServer)server.chunkProvider).unloadQueue.contains( coord ) && server.isChunkLoaded( x, z, true ) )
            {
                i += server.getChunkAt( x, z ).entityCount.get( oClass );
            }
        }
        return i;
    }
    // Spigot end

    public int a(WorldServer worldserver, boolean flag, boolean flag1, boolean flag2) {
        if (!flag && !flag1) {
            return 0;
        } else {
            this.b.clear();
            int i = 0;
            Iterator iterator = worldserver.players.iterator();

            int j;
            int k;

            while (iterator.hasNext()) {
                EntityHuman entityhuman = (EntityHuman) iterator.next();

                if (!entityhuman.isSpectator()) {
                    int l = MathHelper.floor(entityhuman.locX / 16.0D);

                    j = MathHelper.floor(entityhuman.locZ / 16.0D);
                    byte b0 = 8;
                    // Spigot Start
                    b0 = worldserver.spigotConfig.mobSpawnRange;
                    b0 = ( b0 > worldserver.spigotConfig.viewDistance ) ? (byte) worldserver.spigotConfig.viewDistance : b0;
                    b0 = ( b0 > 8 ) ? 8 : b0;
                    // Spigot End

                    for (int i1 = -b0; i1 <= b0; ++i1) {
                        for (k = -b0; k <= b0; ++k) {
                            boolean flag3 = i1 == -b0 || i1 == b0 || k == -b0 || k == b0;
                            // CraftBukkit start - use LongHash and LongHashSet
                            // ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i1 + l, k + j);

                            long chunkCoords = LongHash.toLong(i1 + l, k + j);
                            if (!this.b.contains(chunkCoords)) {
                                ++i;
                                if (!flag3 && worldserver.getWorldBorder().isInBounds(i1 + l, k + j)) { // CraftBukkit
                                    PlayerChunk playerchunk = worldserver.getPlayerChunkMap().getChunk(i1 + l, k + j); // CraftBukkit

                                    if (playerchunk != null && playerchunk.e()) {
                                        this.b.add(chunkCoords);
                                    }
                                }
                            }
                            // CraftBukkit end
                        }
                    }
                }
            }

            int j1 = 0;
            BlockPosition blockposition = worldserver.getSpawn();
            EnumCreatureType[] aenumcreaturetype = EnumCreatureType.values();

            j = aenumcreaturetype.length;

            for (int k1 = 0; k1 < j; ++k1) {
                EnumCreatureType enumcreaturetype = aenumcreaturetype[k1];

               // CraftBukkit start - Use per-world spawn limits
                int limit = enumcreaturetype.b();
                switch (enumcreaturetype) {
                    case MONSTER:
                        limit = worldserver.getWorld().getMonsterSpawnLimit();
                        break;
                    case CREATURE:
                        limit = worldserver.getWorld().getAnimalSpawnLimit();
                        break;
                    case WATER_CREATURE:
                        limit = worldserver.getWorld().getWaterAnimalSpawnLimit();
                        break;
                    case AMBIENT:
                        limit = worldserver.getWorld().getAmbientSpawnLimit();
                        break;
                }

                if (limit == 0) {
                    continue;
                }
				int mobcnt = 0; // Spigot
                // CraftBukkit end

                if ((!enumcreaturetype.d() || flag1) && (enumcreaturetype.d() || flag) && (!enumcreaturetype.e() || flag2)) {
                    k = worldserver.a(enumcreaturetype.a());
                    int l1 = limit * i / a; // CraftBukkit - use per-world limits

                    if ((mobcnt = getEntityCount(worldserver, enumcreaturetype.a())) <= limit * i / 256) {
                        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
                        Iterator iterator1 = this.b.iterator();

                        int moblimit = (limit * i / 256) - mobcnt + 1; // Spigot - up to 1 more than limit
                        label120:
                        while (iterator1.hasNext() && (moblimit > 0)) { // Spigot - while more allowed
                            // CraftBukkit start = use LongHash and LongObjectHashMap
                            long key = ((Long) iterator1.next()).longValue();
                            BlockPosition blockposition1 = getRandomPosition(worldserver, LongHash.msw(key), LongHash.lsw(key));
                            // CraftBukkit
                            int i2 = blockposition1.getX();
                            int j2 = blockposition1.getY();
                            int k2 = blockposition1.getZ();
                            IBlockData iblockdata = worldserver.getType(blockposition1);

                            if (!iblockdata.l()) {
                                int l2 = 0;
                                int i3 = 0;

                                while (i3 < 3) {
                                    int j3 = i2;
                                    int k3 = j2;
                                    int l3 = k2;
                                    byte b1 = 6;
                                    BiomeBase.BiomeMeta biomebase_biomemeta = null;
                                    GroupDataEntity groupdataentity = null;
                                    int i4 = MathHelper.f(Math.random() * 4.0D);
                                    int j4 = 0;

                                    while (true) {
                                        if (j4 < i4) {
                                            label113: {
                                                j3 += worldserver.random.nextInt(b1) - worldserver.random.nextInt(b1);
                                                k3 += worldserver.random.nextInt(1) - worldserver.random.nextInt(1);
                                                l3 += worldserver.random.nextInt(b1) - worldserver.random.nextInt(b1);
                                                blockposition_mutableblockposition.c(j3, k3, l3);
                                                float f = (float) j3 + 0.5F;
                                                float f1 = (float) l3 + 0.5F;

                                                if (!worldserver.isPlayerNearby((double) f, (double) k3, (double) f1, 24.0D) && blockposition.distanceSquared((double) f, (double) k3, (double) f1) >= 576.0D) {
                                                    if (biomebase_biomemeta == null) {
                                                        biomebase_biomemeta = worldserver.a(enumcreaturetype, (BlockPosition) blockposition_mutableblockposition);
                                                        if (biomebase_biomemeta == null) {
                                                            break label113;
                                                        }
                                                    }

                                                    if (worldserver.a(enumcreaturetype, biomebase_biomemeta, (BlockPosition) blockposition_mutableblockposition) && a(EntityPositionTypes.a(biomebase_biomemeta.b), worldserver, blockposition_mutableblockposition)) {
                                                        EntityInsentient entityinsentient;

                                                        try {
                                                            entityinsentient = (EntityInsentient) biomebase_biomemeta.b.getConstructor(new Class[] { World.class}).newInstance(new Object[] { worldserver});
                                                        } catch (Exception exception) {
                                                            exception.printStackTrace();
                                                            return j1;
                                                        }

                                                        entityinsentient.setPositionRotation((double) f, (double) k3, (double) f1, worldserver.random.nextFloat() * 360.0F, 0.0F);
                                                        if (entityinsentient.cG() && entityinsentient.canSpawn()) {
                                                            groupdataentity = entityinsentient.prepare(worldserver.D(new BlockPosition(entityinsentient)), groupdataentity);
                                                            if (entityinsentient.canSpawn()) {
                                                                ++l2;
                                                                worldserver.addEntity(entityinsentient, SpawnReason.NATURAL); // CraftBukkit - Added a reason for spawning this creature
                                                            } else {
                                                                entityinsentient.die();
                                                            }

                                                            // Spigot start
                                                            if ( --moblimit <= 0 ) {
                                                                // If we're past limit, stop spawn
                                                                // Spigot end
                                                                continue label120;
                                                            }
                                                        }

                                                        j1 += l2;
                                                    }
                                                }

                                                ++j4;
                                                continue;
                                            }
                                        }

                                        ++i3;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return j1;
        }
    }

    protected static BlockPosition getRandomPosition(World world, int i, int j) {
        Chunk chunk = world.getChunkAt(i, j);
        int k = i * 16 + world.random.nextInt(16);
        int l = j * 16 + world.random.nextInt(16);
        int i1 = MathHelper.c(chunk.e(new BlockPosition(k, 0, l)) + 1, 16);
        int j1 = world.random.nextInt(i1 > 0 ? i1 : chunk.g() + 16 - 1);

        return new BlockPosition(k, j1, l);
    }

    public static boolean a(IBlockData iblockdata) {
        return iblockdata.k() ? false : (iblockdata.m() ? false : (iblockdata.getMaterial().isLiquid() ? false : !BlockMinecartTrackAbstract.i(iblockdata)));
    }

    public static boolean a(EntityInsentient.EnumEntityPositionType entityinsentient_enumentitypositiontype, World world, BlockPosition blockposition) {
        if (!world.getWorldBorder().a(blockposition)) {
            return false;
        } else {
            IBlockData iblockdata = world.getType(blockposition);

            if (entityinsentient_enumentitypositiontype == EntityInsentient.EnumEntityPositionType.IN_WATER) {
                return iblockdata.getMaterial().isLiquid() && world.getType(blockposition.down()).getMaterial().isLiquid() && !world.getType(blockposition.up()).l();
            } else {
                BlockPosition blockposition1 = blockposition.down();

                if (!world.getType(blockposition1).q()) {
                    return false;
                } else {
                    Block block = world.getType(blockposition1).getBlock();
                    boolean flag = block != Blocks.BEDROCK && block != Blocks.BARRIER;

                    return flag && a(iblockdata) && a(world.getType(blockposition.up()));
                }
            }
        }
    }

    public static void a(World world, BiomeBase biomebase, int i, int j, int k, int l, Random random) {
        List list = biomebase.getMobs(EnumCreatureType.CREATURE);

        if (!list.isEmpty()) {
            while (random.nextFloat() < biomebase.f()) {
                BiomeBase.BiomeMeta biomebase_biomemeta = (BiomeBase.BiomeMeta) WeightedRandom.a(world.random, list);
                int i1 = biomebase_biomemeta.c + random.nextInt(1 + biomebase_biomemeta.d - biomebase_biomemeta.c);
                GroupDataEntity groupdataentity = null;
                int j1 = i + random.nextInt(k);
                int k1 = j + random.nextInt(l);
                int l1 = j1;
                int i2 = k1;

                for (int j2 = 0; j2 < i1; ++j2) {
                    boolean flag = false;

                    for (int k2 = 0; !flag && k2 < 4; ++k2) {
                        BlockPosition blockposition = world.q(new BlockPosition(j1, 0, k1));

                        if (a(EntityInsentient.EnumEntityPositionType.ON_GROUND, world, blockposition)) {
                            EntityInsentient entityinsentient;

                            try {
                                entityinsentient = (EntityInsentient) biomebase_biomemeta.b.getConstructor(new Class[] { World.class}).newInstance(new Object[] { world});
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                continue;
                            }

                            entityinsentient.setPositionRotation((double) ((float) j1 + 0.5F), (double) blockposition.getY(), (double) ((float) k1 + 0.5F), random.nextFloat() * 360.0F, 0.0F);
                            // CraftBukkit start - Added a reason for spawning this creature, moved entityinsentient.prepare(groupdataentity) up
                            groupdataentity = entityinsentient.prepare(world.D(new BlockPosition(entityinsentient)), groupdataentity);
                            world.addEntity(entityinsentient, SpawnReason.CHUNK_GEN);
                            // CraftBukkit end
                            flag = true;
                        }

                        j1 += random.nextInt(5) - random.nextInt(5);

                        for (k1 += random.nextInt(5) - random.nextInt(5); j1 < i || j1 >= i + k || k1 < j || k1 >= j + k; k1 = i2 + random.nextInt(5) - random.nextInt(5)) {
                            j1 = l1 + random.nextInt(5) - random.nextInt(5);
                        }
                    }
                }
            }

        }
    }
}
