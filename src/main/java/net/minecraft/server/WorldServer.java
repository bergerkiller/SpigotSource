package net.minecraft.server;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// CraftBukkit start
import java.util.*;
import java.util.logging.Level;

import org.bukkit.WeatherType;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.util.LongHash;

import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
// CraftBukkit end

public class WorldServer extends World implements IAsyncTaskHandler {

    private static final Logger a = LogManager.getLogger();
    private final MinecraftServer server;
    public EntityTracker tracker;
    private final PlayerChunkMap manager;
    private final Set L = Sets.newHashSet();
    private final TreeSet M = new TreeSet();
    private final Map entitiesByUUID = Maps.newHashMap();
    public ChunkProviderServer chunkProviderServer;
    public boolean savingDisabled;
    private boolean O;
    private int emptyTime;
    private final PortalTravelAgent Q;
    private final SpawnerCreature R = new SpawnerCreature();
    protected final VillageSiege siegeManager = new VillageSiege(this);
    private BlockActionDataList[] S = new BlockActionDataList[] { new BlockActionDataList((EntitySelectorLiving2) null), new BlockActionDataList((EntitySelectorLiving2) null)};
    private int T;
    private static final List U = Lists.newArrayList(new StructurePieceTreasure[] { new StructurePieceTreasure(Items.STICK, 0, 1, 3, 10), new StructurePieceTreasure(Item.getItemOf(Blocks.PLANKS), 0, 1, 3, 10), new StructurePieceTreasure(Item.getItemOf(Blocks.LOG), 0, 1, 3, 10), new StructurePieceTreasure(Items.STONE_AXE, 0, 1, 1, 3), new StructurePieceTreasure(Items.WOODEN_AXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.STONE_PICKAXE, 0, 1, 1, 3), new StructurePieceTreasure(Items.WOODEN_PICKAXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.APPLE, 0, 2, 3, 5), new StructurePieceTreasure(Items.BREAD, 0, 2, 3, 3), new StructurePieceTreasure(Item.getItemOf(Blocks.LOG2), 0, 1, 3, 10)});
    private List V = Lists.newArrayList();

    // CraftBukkit start 
    public final int dimension;

    // Add env and gen to constructor
    public WorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, WorldData worlddata, int i, MethodProfiler methodprofiler,  org.bukkit.World.Environment env, org.bukkit.generator.ChunkGenerator gen) {
        super(idatamanager, worlddata, WorldProvider.byDimension(env.getId()), methodprofiler, false, gen, env);
        this.dimension = i;
        this.pvpMode = minecraftserver.getPVP();
        // CraftBukkit end
        this.server = minecraftserver;
        this.tracker = new EntityTracker(this);
        this.manager = new PlayerChunkMap(this, spigotConfig.viewDistance); // Spigot
        this.worldProvider.a(this);
        this.chunkProvider = this.k();
        this.Q = new org.bukkit.craftbukkit.CraftTravelAgent(this); // CraftBukkit
        this.B();
        this.C();
        this.af().a(minecraftserver.aG());
    }

    public World b() {
        this.worldMaps = new PersistentCollection(this.dataManager);
        String s = PersistentVillage.a(this.worldProvider);
        PersistentVillage persistentvillage = (PersistentVillage) this.worldMaps.get(PersistentVillage.class, s);

        if (persistentvillage == null) {
            this.villages = new PersistentVillage(this);
            this.worldMaps.a(s, this.villages);
        } else {
            this.villages = persistentvillage;
            this.villages.a((World) this);
        }

        this.scoreboard = new ScoreboardServer(this.server);
        PersistentScoreboard persistentscoreboard = (PersistentScoreboard) this.worldMaps.get(PersistentScoreboard.class, "scoreboard");

        if (persistentscoreboard == null) {
            persistentscoreboard = new PersistentScoreboard();
            this.worldMaps.a("scoreboard", persistentscoreboard);
        }

        persistentscoreboard.a(this.scoreboard);
        ((ScoreboardServer) this.scoreboard).a(persistentscoreboard);
        this.af().c(this.worldData.C(), this.worldData.D());
        this.af().c(this.worldData.I());
        this.af().b(this.worldData.H());
        this.af().c(this.worldData.J());
        this.af().b(this.worldData.K());
        if (this.worldData.F() > 0L) {
            this.af().a(this.worldData.E(), this.worldData.G(), this.worldData.F());
        } else {
            this.af().a(this.worldData.E());
        }

        return this;
    }
    
    // CraftBukkit start
    @Override
    public TileEntity getTileEntity(BlockPosition pos) {
        TileEntity result = super.getTileEntity(pos);
        Block type = getType(pos).getBlock();

        if (type == Blocks.CHEST || type == Blocks.TRAPPED_CHEST) { // Spigot
            if (!(result instanceof TileEntityChest)) {
                result = fixTileEntity(pos, type, result);
            }
        } else if (type == Blocks.FURNACE) {
            if (!(result instanceof TileEntityFurnace)) {
                result = fixTileEntity(pos, type, result);
            }
        } else if (type == Blocks.DROPPER) {
            if (!(result instanceof TileEntityDropper)) {
                result = fixTileEntity(pos, type, result);
            }
        } else if (type == Blocks.DISPENSER) {
            if (!(result instanceof TileEntityDispenser)) {
                result = fixTileEntity(pos, type, result);
            }
        } else if (type == Blocks.JUKEBOX) {
            if (!(result instanceof TileEntityRecordPlayer)) {
                result = fixTileEntity(pos, type, result);
            }
        } else if (type == Blocks.NOTEBLOCK) {
            if (!(result instanceof TileEntityNote)) {
                result = fixTileEntity(pos, type, result);
            }
        } else if (type == Blocks.MOB_SPAWNER) {
            if (!(result instanceof TileEntityMobSpawner)) {
                result = fixTileEntity(pos, type, result);
            }
        } else if ((type == Blocks.STANDING_SIGN) || (type == Blocks.WALL_SIGN)) {
            if (!(result instanceof TileEntitySign)) {
                result = fixTileEntity(pos, type, result);
            }
        } else if (type == Blocks.ENDER_CHEST) {
            if (!(result instanceof TileEntityEnderChest)) {
                result = fixTileEntity(pos, type, result);
            }
        } else if (type == Blocks.BREWING_STAND) {
            if (!(result instanceof TileEntityBrewingStand)) {
                result = fixTileEntity(pos, type, result);
            }
        } else if (type == Blocks.BEACON) {
            if (!(result instanceof TileEntityBeacon)) {
                result = fixTileEntity(pos, type, result);
            }
        } else if (type == Blocks.HOPPER) {
            if (!(result instanceof TileEntityHopper)) {
                result = fixTileEntity(pos, type, result);
            }
        }

        return result;
    }

    private TileEntity fixTileEntity(BlockPosition pos, Block type, TileEntity found) {
        this.getServer().getLogger().log(Level.SEVERE, "Block at {0},{1},{2} is {3} but has {4}" + ". "
                + "Bukkit will attempt to fix this, but there may be additional damage that we cannot recover.", new Object[]{pos.getX(), pos.getY(), pos.getZ(), org.bukkit.Material.getMaterial(Block.getId(type)).toString(), found});

        if (type instanceof IContainer) {
            TileEntity replacement = ((IContainer) type).a(this, type.toLegacyData(this.getType(pos)));
            replacement.world = this;
            this.setTileEntity(pos, replacement);
            return replacement;
        } else {
            this.getServer().getLogger().severe("Don't know how to fix for this type... Can't do anything! :(");
            return found;
        }
    }

    private boolean canSpawn(int x, int z) {
        if (this.generator != null) {
            return this.generator.canSpawn(this.getWorld(), x, z);
        } else {
            return this.worldProvider.canSpawn(x, z);
        }
    }
    // CraftBukkit end

    public void doTick() {
        super.doTick();
        if (this.getWorldData().isHardcore() && this.getDifficulty() != EnumDifficulty.HARD) {
            this.getWorldData().setDifficulty(EnumDifficulty.HARD);
        }

        this.worldProvider.m().b();
        if (this.everyoneDeeplySleeping()) {
            if (this.getGameRules().getBoolean("doDaylightCycle")) {
                long i = this.worldData.getDayTime() + 24000L;

                this.worldData.setDayTime(i - i % 24000L);
            }

            this.e();
        }

        this.methodProfiler.a("mobSpawner");
        // CraftBukkit start - Only call spawner if we have players online and the world allows for mobs or animals
        long time = this.worldData.getTime();
        if (this.getGameRules().getBoolean("doMobSpawning") && this.worldData.getType() != WorldType.DEBUG_ALL_BLOCK_STATES && (this.allowMonsters || this.allowAnimals) && (this instanceof WorldServer && this.players.size() > 0)) {
            timings.mobSpawn.startTiming(); // Spigot
            this.R.a(this, this.allowMonsters && (this.ticksPerMonsterSpawns != 0 && time % this.ticksPerMonsterSpawns == 0L), this.allowAnimals && (this.ticksPerAnimalSpawns != 0 && time % this.ticksPerAnimalSpawns == 0L), this.worldData.getTime() % 400L == 0L);
            timings.mobSpawn.stopTiming(); // Spigot
            // CraftBukkit end
        }
        // CraftBukkit end
        timings.doChunkUnload.startTiming(); // Spigot
        this.methodProfiler.c("chunkSource");
        this.chunkProvider.unloadChunks();
        int j = this.a(1.0F);

        if (j != this.ab()) {
            this.b(j);
        }

        this.worldData.setTime(this.worldData.getTime() + 1L);
        if (this.getGameRules().getBoolean("doDaylightCycle")) {
            this.worldData.setDayTime(this.worldData.getDayTime() + 1L);
        }

        timings.doChunkUnload.stopTiming(); // Spigot
        this.methodProfiler.c("tickPending");
        timings.doTickPending.startTiming(); // Spigot
        this.a(false);
        timings.doTickPending.stopTiming(); // Spigot
        this.methodProfiler.c("tickBlocks");
        timings.doTickTiles.startTiming(); // Spigot
        this.h();
        timings.doTickTiles.stopTiming(); // Spigot
        this.methodProfiler.c("chunkMap");
        timings.doChunkMap.startTiming(); // Spigot
        this.manager.flush();
        timings.doChunkMap.stopTiming(); // Spigot
        this.methodProfiler.c("village");
        timings.doVillages.startTiming(); // Spigot
        this.villages.tick();
        this.siegeManager.a();
        timings.doVillages.stopTiming(); // Spigot
        this.methodProfiler.c("portalForcer");
        timings.doPortalForcer.startTiming(); // Spigot
        this.Q.a(this.getTime());
        timings.doPortalForcer.stopTiming(); // Spigot
        this.methodProfiler.b();
        timings.doSounds.startTiming(); // Spigot
        this.ak();
        
        this.getWorld().processChunkGC(); // CraftBukkit
        timings.doChunkGC.stopTiming(); // Spigot
    }

    public BiomeMeta a(EnumCreatureType enumcreaturetype, BlockPosition blockposition) {
        List list = this.N().getMobsFor(enumcreaturetype, blockposition);

        return list != null && !list.isEmpty() ? (BiomeMeta) WeightedRandom.a(this.random, list) : null;
    }

    public boolean a(EnumCreatureType enumcreaturetype, BiomeMeta biomemeta, BlockPosition blockposition) {
        List list = this.N().getMobsFor(enumcreaturetype, blockposition);

        return list != null && !list.isEmpty() ? list.contains(biomemeta) : false;
    }

    public void everyoneSleeping() {
        this.O = false;
        if (!this.players.isEmpty()) {
            int i = 0;
            int j = 0;
            Iterator iterator = this.players.iterator();

            while (iterator.hasNext()) {
                EntityHuman entityhuman = (EntityHuman) iterator.next();

                if (entityhuman.v()) {
                    ++i;
                } else if (entityhuman.isSleeping() || entityhuman.fauxSleeping) { // CraftBukkit
                    ++j;
                }
            }

            this.O = j > 0 && j >= this.players.size() - i;
        }

    }

    protected void e() {
        this.O = false;
        Iterator iterator = this.players.iterator();

        while (iterator.hasNext()) {
            EntityHuman entityhuman = (EntityHuman) iterator.next();

            if (entityhuman.isSleeping()) {
                entityhuman.a(false, false, true);
            }
        }

        this.ag();
    }

    private void ag() {
        // CraftBukkit start
        WeatherChangeEvent weather = new WeatherChangeEvent(this.getWorld(), false);
        this.getServer().getPluginManager().callEvent(weather);

        ThunderChangeEvent thunder = new ThunderChangeEvent(this.getWorld(), false);
        this.getServer().getPluginManager().callEvent(thunder);
        if (!weather.isCancelled()) {
            this.worldData.setWeatherDuration(0);
            this.worldData.setStorm(false);
        }
        if (!thunder.isCancelled()) {
            this.worldData.setThunderDuration(0);
            this.worldData.setThundering(false);
        }
        // CraftBukkit end
    }

    public boolean everyoneDeeplySleeping() {
        if (this.O && !this.isStatic) {
            Iterator iterator = this.players.iterator();

            // CraftBukkit - This allows us to assume that some people are in bed but not really, allowing time to pass in spite of AFKers
            boolean foundActualSleepers = false;
            
            EntityHuman entityhuman;

            do {
                if (!iterator.hasNext()) {
                    return foundActualSleepers;
                }

                entityhuman = (EntityHuman) iterator.next();
                // CraftBukkit start
                if (entityhuman.isDeeplySleeping()) {
                    foundActualSleepers = true;
                }
            } while (!entityhuman.v() && (entityhuman.isDeeplySleeping() || entityhuman.fauxSleeping));
            // CraftBukkit end
            
            return false;
        } else {
            return false;
        }
    }

    protected void h() {
        super.h();
        if (this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES) {
            // Spigot start
           gnu.trove.iterator.TLongShortIterator iterator = this.chunkTickList.iterator();

            while (iterator.hasNext()) {
                iterator.advance();
                long chunkCoord = iterator.key();

                this.getChunkAt(World.keyToX( chunkCoord ), World.keyToZ( chunkCoord )).b(false);
                // Spigot end
            }

        } else {
            int i = 0;
            int j = 0;
            
            // CraftBukkit start
            //for (Iterator iterator1 = this.chunkTickList.iterator(); iterator1.hasNext(); this.methodProfiler.b()) {
            //    ChunkCoordIntPair chunkcoordintpair1 = (ChunkCoordIntPair) iterator1.next();
            //    int k = chunkcoordintpair1.x * 16;
            //    int l = chunkcoordintpair1.z * 16;
            // Spigot start
            for (gnu.trove.iterator.TLongShortIterator iter = chunkTickList.iterator(); iter.hasNext(); )
            {
                iter.advance();
                long chunkCoord = iter.key();
                int chunkX = World.keyToX( chunkCoord );
                int chunkZ = World.keyToZ( chunkCoord );
                // If unloaded, or in procedd of being unloaded, drop it
                if ( ( !this.chunkProvider.isChunkLoaded( chunkX, chunkZ ) ) || ( this.chunkProviderServer.unloadQueue.contains( chunkX, chunkZ ) ) )
                {
                    iter.remove();
                    continue;
                }
                // Spigot end
                // ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair) iterator.next();
                int k = chunkX * 16;
                int l = chunkZ * 16;
                
                this.methodProfiler.a("getChunk");
                Chunk chunk = this.getChunkAt(chunkX, chunkZ);
                // CraftBukkit end
                
                this.a(k, l, chunk);
                this.methodProfiler.c("tickChunk");
                chunk.b(false);
                this.methodProfiler.c("thunder");
                int i1;
                BlockPosition blockposition;

                if (this.random.nextInt(100000) == 0 && this.S() && this.R()) {
                    this.m = this.m * 3 + 1013904223;
                    i1 = this.m >> 2;
                    blockposition = this.a(new BlockPosition(k + (i1 & 15), 0, l + (i1 >> 8 & 15)));
                    if (this.isRainingAt(blockposition)) {
                        this.strikeLightning(new EntityLightning(this, (double) blockposition.getX(), (double) blockposition.getY(), (double) blockposition.getZ()));
                    }
                }

                this.methodProfiler.c("iceandsnow");
                if (this.random.nextInt(16) == 0) {
                    this.m = this.m * 3 + 1013904223;
                    i1 = this.m >> 2;
                    blockposition = this.q(new BlockPosition(k + (i1 & 15), 0, l + (i1 >> 8 & 15)));
                    BlockPosition blockposition1 = blockposition.down();

                    if (this.w(blockposition1)) {
                        // CraftBukkit start
                        BlockState blockState = this.getWorld().getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ()).getState();
                        blockState.setTypeId(Block.getId(Blocks.ICE));

                        BlockFormEvent iceBlockForm = new BlockFormEvent(blockState.getBlock(), blockState);
                        this.getServer().getPluginManager().callEvent(iceBlockForm);
                        if (!iceBlockForm.isCancelled()) {
                            blockState.update(true);
                        }
                        // CraftBukkit end
                    }

                    if (this.S() && this.f(blockposition, true)) {
                        // CraftBukkit start
                        BlockState blockState = this.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()).getState();
                        blockState.setTypeId(Block.getId(Blocks.SNOW_LAYER));

                        BlockFormEvent snow = new BlockFormEvent(blockState.getBlock(), blockState);
                        this.getServer().getPluginManager().callEvent(snow);
                        if (!snow.isCancelled()) {
                            blockState.update(true);
                        }
                        // CraftBukkit end
                    }

                    if (this.S() && this.getBiome(blockposition1).e()) {
                        this.getType(blockposition1).getBlock().k(this, blockposition1);
                    }
                }

                this.methodProfiler.c("tickBlocks");
                i1 = this.getGameRules().c("randomTickSpeed");
                if (i1 > 0) {
                    ChunkSection[] achunksection = chunk.getSections();
                    int j1 = achunksection.length;

                    for (int k1 = 0; k1 < j1; ++k1) {
                        ChunkSection chunksection = achunksection[k1];

                        if (chunksection != null && chunksection.shouldTick()) {
                            for (int l1 = 0; l1 < i1; ++l1) {
                                this.m = this.m * 3 + 1013904223;
                                int i2 = this.m >> 2;
                                int j2 = i2 & 15;
                                int k2 = i2 >> 8 & 15;
                                int l2 = i2 >> 16 & 15;

                                ++j;
                                BlockPosition blockposition2 = new BlockPosition(j2 + k, l2 + chunksection.getYPosition(), k2 + l);
                                IBlockData iblockdata = chunksection.getType(j2, l2, k2);
                                Block block = iblockdata.getBlock();

                                if (block.isTicking()) {
                                    ++i;
                                    block.a((World) this, blockposition2, iblockdata, this.random);
                                }
                            }
                        }
                    }
                }
            }

        }
        // Spigot Start
        if ( spigotConfig.clearChunksOnTick )
        {
            chunkTickList.clear();
        }
        // Spigot End
    }

    protected BlockPosition a(BlockPosition blockposition) {
        BlockPosition blockposition1 = this.q(blockposition);
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(blockposition1, new BlockPosition(blockposition1.getX(), this.getHeight(), blockposition1.getZ()))).grow(3.0D, 3.0D, 3.0D);
        List list = this.a(EntityLiving.class, axisalignedbb, (Predicate) (new EntitySelectorLiving2(this)));

        return !list.isEmpty() ? ((EntityLiving) list.get(this.random.nextInt(list.size()))).getChunkCoordinates() : blockposition1;
    }

    public boolean a(BlockPosition blockposition, Block block) {
        NextTickListEntry nextticklistentry = new NextTickListEntry(blockposition, block);

        return this.V.contains(nextticklistentry);
    }

    public void a(BlockPosition blockposition, Block block, int i) {
        this.a(blockposition, block, i, 0);
    }

    public void a(BlockPosition blockposition, Block block, int i, int j) {
        NextTickListEntry nextticklistentry = new NextTickListEntry(blockposition, block);
        byte b0 = 0;

        if (this.e && block.getMaterial() != Material.AIR) {
            if (block.M()) {
                b0 = 8;
                if (this.areChunksLoadedBetween(nextticklistentry.a.a(-b0, -b0, -b0), nextticklistentry.a.a(b0, b0, b0))) {
                    IBlockData iblockdata = this.getType(nextticklistentry.a);

                    if (iblockdata.getBlock().getMaterial() != Material.AIR && iblockdata.getBlock() == nextticklistentry.a()) {
                        iblockdata.getBlock().b((World) this, nextticklistentry.a, iblockdata, this.random);
                    }
                }

                return;
            }

            i = 1;
        }

        if (this.areChunksLoadedBetween(blockposition.a(-b0, -b0, -b0), blockposition.a(b0, b0, b0))) {
            if (block.getMaterial() != Material.AIR) {
                nextticklistentry.a((long) i + this.worldData.getTime());
                nextticklistentry.a(j);
            }

            if (!this.L.contains(nextticklistentry)) {
                this.L.add(nextticklistentry);
                this.M.add(nextticklistentry);
            }
        }

    }

    public void b(BlockPosition blockposition, Block block, int i, int j) {
        NextTickListEntry nextticklistentry = new NextTickListEntry(blockposition, block);

        nextticklistentry.a(j);
        if (block.getMaterial() != Material.AIR) {
            nextticklistentry.a((long) i + this.worldData.getTime());
        }

        if (!this.L.contains(nextticklistentry)) {
            this.L.add(nextticklistentry);
            this.M.add(nextticklistentry);
        }

    }

    public void tickEntities() {
        if (false && this.players.isEmpty()) {  // CraftBukkit - this prevents entity cleanup, other issues on servers with no players
            if (this.emptyTime++ >= 1200) {
                return;
            }
        } else {
            this.j();
        }

        super.tickEntities();
        spigotConfig.currentPrimedTnt = 0; // Spigot
    }

    public void j() {
        this.emptyTime = 0;
    }

    public boolean a(boolean flag) {
        if (this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES) {
            return false;
        } else {
            int i = this.M.size();

            if (i != this.L.size()) {
                throw new IllegalStateException("TickNextTick list out of synch");
            } else {
                if (i > 1000) {
                    // CraftBukkit start - If the server has too much to process over time, try to alleviate that
                    if (i > 20 * 1000) {
                        i = i / 20;
                    } else {
                        i = 1000;
                    }
                    // CraftBukkit end                    
                }

                this.methodProfiler.a("cleaning");

                NextTickListEntry nextticklistentry;

                for (int j = 0; j < i; ++j) {
                    nextticklistentry = (NextTickListEntry) this.M.first();
                    if (!flag && nextticklistentry.b > this.worldData.getTime()) {
                        break;
                    }

                    this.M.remove(nextticklistentry);
                    this.L.remove(nextticklistentry);
                    this.V.add(nextticklistentry);
                }

                this.methodProfiler.b();
                this.methodProfiler.a("ticking");
                Iterator iterator = this.V.iterator();

                while (iterator.hasNext()) {
                    nextticklistentry = (NextTickListEntry) iterator.next();
                    iterator.remove();
                    byte b0 = 0;

                    if (this.areChunksLoadedBetween(nextticklistentry.a.a(-b0, -b0, -b0), nextticklistentry.a.a(b0, b0, b0))) {
                        IBlockData iblockdata = this.getType(nextticklistentry.a);

                        if (iblockdata.getBlock().getMaterial() != Material.AIR && Block.a(iblockdata.getBlock(), nextticklistentry.a())) {
                            try {
                                iblockdata.getBlock().b((World) this, nextticklistentry.a, iblockdata, this.random);
                            } catch (Throwable throwable) {
                                CrashReport crashreport = CrashReport.a(throwable, "Exception while ticking a block");
                                CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being ticked");

                                CrashReportSystemDetails.a(crashreportsystemdetails, nextticklistentry.a, iblockdata);
                                throw new ReportedException(crashreport);
                            }
                        }
                    } else {
                        this.a(nextticklistentry.a, nextticklistentry.a(), 0);
                    }
                }

                this.methodProfiler.b();
                this.V.clear();
                return !this.M.isEmpty();
            }
        }
    }

    public List a(Chunk chunk, boolean flag) {
        ChunkCoordIntPair chunkcoordintpair = chunk.j();
        int i = (chunkcoordintpair.x << 4) - 2;
        int j = i + 16 + 2;
        int k = (chunkcoordintpair.z << 4) - 2;
        int l = k + 16 + 2;

        return this.a(new StructureBoundingBox(i, 0, k, j, 256, l), flag);
    }

    public List a(StructureBoundingBox structureboundingbox, boolean flag) {
        ArrayList arraylist = null;

        for (int i = 0; i < 2; ++i) {
            Iterator iterator;

            if (i == 0) {
                iterator = this.M.iterator();
            } else {
                iterator = this.V.iterator();
                if (!this.V.isEmpty()) {
                    WorldServer.a.debug("toBeTicked = " + this.V.size());
                }
            }

            while (iterator.hasNext()) {
                NextTickListEntry nextticklistentry = (NextTickListEntry) iterator.next();
                BlockPosition blockposition = nextticklistentry.a;

                if (blockposition.getX() >= structureboundingbox.a && blockposition.getX() < structureboundingbox.d && blockposition.getZ() >= structureboundingbox.c && blockposition.getZ() < structureboundingbox.f) {
                    if (flag) {
                        this.L.remove(nextticklistentry);
                        iterator.remove();
                    }

                    if (arraylist == null) {
                        arraylist = Lists.newArrayList();
                    }

                    arraylist.add(nextticklistentry);
                }
            }
        }

        return arraylist;
    }

    /* CraftBukkit start - We prevent spawning in general, so this butchering is not needed
    public void entityJoinedWorld(Entity entity, boolean flag) {
        if (!this.getSpawnAnimals() && (entity instanceof EntityAnimal || entity instanceof EntityWaterAnimal)) {
            entity.die();
        }

        if (!this.getSpawnNPCs() && entity instanceof NPC) {
            entity.die();
        }

        super.entityJoinedWorld(entity, flag);

    }
    // CraftBukkit end */

    private boolean getSpawnNPCs() {
        return this.server.getSpawnNPCs();
    }

    private boolean getSpawnAnimals() {
        return this.server.getSpawnAnimals();
    }

    protected IChunkProvider k() {
        IChunkLoader ichunkloader = this.dataManager.createChunkLoader(this.worldProvider);
        
        // CraftBukkit start
        org.bukkit.craftbukkit.generator.InternalChunkGenerator gen;

        if (this.generator != null) {
            gen = new org.bukkit.craftbukkit.generator.CustomChunkGenerator(this, this.getSeed(), this.generator);
        } else if (this.worldProvider instanceof WorldProviderHell) {
            gen = new org.bukkit.craftbukkit.generator.NetherChunkGenerator(this, this.getSeed());
        } else if (this.worldProvider instanceof WorldProviderTheEnd) {
            gen = new org.bukkit.craftbukkit.generator.SkyLandsChunkGenerator(this, this.getSeed());
        } else {
            gen = new org.bukkit.craftbukkit.generator.NormalChunkGenerator(this, this.getSeed());
        }

        this.chunkProviderServer = new ChunkProviderServer(this, ichunkloader, gen);
        // CraftBukkit end
        return this.chunkProviderServer;
    }

    public List getTileEntities(int i, int j, int k, int l, int i1, int j1) {
        ArrayList arraylist = Lists.newArrayList();
        
        // CraftBukkit start - Get tile entities from chunks instead of world
        for (int chunkX = (i >> 4); chunkX <= ((l - 1) >> 4); chunkX++) {
            for (int chunkZ = (k >> 4); chunkZ <= ((j1 - 1) >> 4); chunkZ++) {
                Chunk chunk = getChunkAt(chunkX, chunkZ);
                if (chunk == null) {
                    continue;
                }
                for (Object te : chunk.tileEntities.values()) {
                    TileEntity tileentity = (TileEntity) te;
                    if ((tileentity.position.getX() >= i) && (tileentity.position.getY() >= j) && (tileentity.position.getZ() >= k) && (tileentity.position.getX() < l) && (tileentity.position.getY() < i1) && (tileentity.position.getZ() < j1)) {
                        arraylist.add(tileentity);
                    }
                }
            }
        }
        /*
        for (int k1 = 0; k1 < this.h.size(); ++k1) {
            TileEntity tileentity = (TileEntity) this.h.get(k1);
            BlockPosition blockposition = tileentity.getPosition();

            if (blockposition.getX() >= i && blockposition.getY() >= j && blockposition.getZ() >= k && blockposition.getX() < l && blockposition.getY() < i1 && blockposition.getZ() < j1) {
                arraylist.add(tileentity);
            }
        }
        */
        // CraftBukkit end

        return arraylist;
    }

    public boolean a(EntityHuman entityhuman, BlockPosition blockposition) {
        return !this.server.a(this, blockposition, entityhuman) && this.af().a(blockposition);
    }

    public void a(WorldSettings worldsettings) {
        if (!this.worldData.w()) {
            try {
                this.b(worldsettings);
                if (this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES) {
                    this.aj();
                }

                super.a(worldsettings);
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Exception initializing level");

                try {
                    this.a(crashreport);
                } catch (Throwable throwable1) {
                    ;
                }

                throw new ReportedException(crashreport);
            }

            this.worldData.d(true);
        }

    }

    private void aj() {
        this.worldData.f(false);
        this.worldData.c(true);
        this.worldData.setStorm(false);
        this.worldData.setThundering(false);
        this.worldData.i(1000000000);
        this.worldData.setDayTime(6000L);
        this.worldData.setGameType(EnumGamemode.SPECTATOR);
        this.worldData.g(false);
        this.worldData.setDifficulty(EnumDifficulty.PEACEFUL);
        this.worldData.e(true);
        this.getGameRules().set("doDaylightCycle", "false");
    }

    private void b(WorldSettings worldsettings) {
        if (!this.worldProvider.e()) {
            this.worldData.setSpawn(BlockPosition.ZERO.up(this.worldProvider.getSeaLevel()));
        } else if (this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES) {
            this.worldData.setSpawn(BlockPosition.ZERO.up());
        } else {
            this.isLoading = true;
            WorldChunkManager worldchunkmanager = this.worldProvider.m();
            List list = worldchunkmanager.a();
            Random random = new Random(this.getSeed());
            BlockPosition blockposition = worldchunkmanager.a(0, 0, 256, list, random);
            int i = 0;
            int j = this.worldProvider.getSeaLevel();
            int k = 0;
            
            // CraftBukkit start
            if (this.generator != null) {
                Random rand = new Random(this.getSeed());
                org.bukkit.Location spawn = this.generator.getFixedSpawnLocation(((WorldServer) this).getWorld(), rand);

                if (spawn != null) {
                    if (spawn.getWorld() != ((WorldServer) this).getWorld()) {
                        throw new IllegalStateException("Cannot set spawn point for " + this.worldData.getName() + " to be in another world (" + spawn.getWorld().getName() + ")");
                    } else {
                        this.worldData.setSpawn(new BlockPosition(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ()));
                        this.isLoading = false;
                        return;
                    }
                }
            }
            // CraftBukkit end            

            if (blockposition != null) {
                i = blockposition.getX();
                k = blockposition.getZ();
            } else {
                WorldServer.a.warn("Unable to find spawn biome");
            }

            int l = 0;

            while (!this.canSpawn(i, k)) { // CraftBukkit - use our own canSpawn
                i += random.nextInt(64) - random.nextInt(64);
                k += random.nextInt(64) - random.nextInt(64);
                ++l;
                if (l == 1000) {
                    break;
                }
            }

            this.worldData.setSpawn(new BlockPosition(i, j, k));
            this.isLoading = false;
            if (worldsettings.c()) {
                this.l();
            }

        }
    }

    protected void l() {
        WorldGenBonusChest worldgenbonuschest = new WorldGenBonusChest(WorldServer.U, 10);

        for (int i = 0; i < 10; ++i) {
            int j = this.worldData.c() + this.random.nextInt(6) - this.random.nextInt(6);
            int k = this.worldData.e() + this.random.nextInt(6) - this.random.nextInt(6);
            BlockPosition blockposition = this.r(new BlockPosition(j, 0, k)).up();

            if (worldgenbonuschest.generate(this, this.random, blockposition)) {
                break;
            }
        }

    }

    public BlockPosition getDimensionSpawn() {
        return this.worldProvider.h();
    }

    public void save(boolean flag, IProgressUpdate iprogressupdate) throws ExceptionWorldConflict { // CraftBukkit - added throws
        if (this.chunkProvider.canSave()) {
            if (iprogressupdate != null) {
                iprogressupdate.a("Saving level");
            }

            this.a();
            if (iprogressupdate != null) {
                iprogressupdate.c("Saving chunks");
            }

            this.chunkProvider.saveChunks(flag, iprogressupdate);
            // CraftBukkit - ArrayList -> Collection
            Collection list = this.chunkProviderServer.a();
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                Chunk chunk = (Chunk) iterator.next();

                if (!this.manager.a(chunk.locX, chunk.locZ)) {
                    this.chunkProviderServer.queueUnload(chunk.locX, chunk.locZ);
                }
            }

        }
    }

    public void flushSave() {
        if (this.chunkProvider.canSave()) {
            this.chunkProvider.c();
        }
    }

    protected void a() throws ExceptionWorldConflict { // CraftBukkit - added throws
        this.checkSession();
        this.worldData.a(this.af().h());
        this.worldData.d(this.af().f());
        this.worldData.c(this.af().g());
        this.worldData.e(this.af().m());
        this.worldData.f(this.af().n());
        this.worldData.j(this.af().q());
        this.worldData.k(this.af().p());
        this.worldData.b(this.af().j());
        this.worldData.e(this.af().i());
        this.dataManager.saveWorldData(this.worldData, this.server.getPlayerList().u());
        // CraftBukkit start - save worldMaps once, rather than once per shared world
        if (!(this instanceof SecondaryWorldServer)) {
            this.worldMaps.a();
        }
        // CraftBukkit end
    }

    protected void a(Entity entity) {
        super.a(entity);
        this.entitiesById.a(entity.getId(), entity);
        this.entitiesByUUID.put(entity.getUniqueID(), entity);
        Entity[] aentity = entity.aC();

        if (aentity != null) {
            for (int i = 0; i < aentity.length; ++i) {
                this.entitiesById.a(aentity[i].getId(), aentity[i]);
            }
        }

    }

    protected void b(Entity entity) {
        super.b(entity);
        this.entitiesById.d(entity.getId());
        this.entitiesByUUID.remove(entity.getUniqueID());
        Entity[] aentity = entity.aC();

        if (aentity != null) {
            for (int i = 0; i < aentity.length; ++i) {
                this.entitiesById.d(aentity[i].getId());
            }
        }

    }

    public boolean strikeLightning(Entity entity) {
        // CraftBukkit start
        LightningStrikeEvent lightning = new LightningStrikeEvent(this.getWorld(), (org.bukkit.entity.LightningStrike) entity.getBukkitEntity());
        this.getServer().getPluginManager().callEvent(lightning);

        if (lightning.isCancelled()) {
            return false;
        }
        if (super.strikeLightning(entity)) {
            this.server.getPlayerList().sendPacketNearby(entity.locX, entity.locY, entity.locZ, 512.0D, this.dimension, new PacketPlayOutSpawnEntityWeather(entity));
            // CraftBukkit end
            return true;
        } else {
            return false;
        }
    }

    public void broadcastEntityEffect(Entity entity, byte b0) {
        this.getTracker().sendPacketToEntity(entity, new PacketPlayOutEntityStatus(entity, b0));
    }

    public Explosion createExplosion(Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
        // CraftBukkit start
        Explosion explosion = super.createExplosion(entity, d0, d1, d2, f, flag, flag1);

        if (explosion.wasCanceled) {
            return explosion;
        }
 
        /* Remove
        Explosion explosion = new Explosion(this, entity, d0, d1, d2, f, flag, flag1);

        explosion.a();
        explosion.a(false);
        */
        // CraftBukkit end - TODO: Check if explosions are still properly implemented
        if (!flag1) {
            explosion.clearBlocks();
        }

        Iterator iterator = this.players.iterator();

        while (iterator.hasNext()) {
            EntityHuman entityhuman = (EntityHuman) iterator.next();

            if (entityhuman.e(d0, d1, d2) < 4096.0D) {
                ((EntityPlayer) entityhuman).playerConnection.sendPacket(new PacketPlayOutExplosion(d0, d1, d2, f, explosion.getBlocks(), (Vec3D) explosion.b().get(entityhuman)));
            }
        }

        return explosion;
    }

    public void playBlockAction(BlockPosition blockposition, Block block, int i, int j) {
        BlockActionData blockactiondata = new BlockActionData(blockposition, block, i, j);
        Iterator iterator = this.S[this.T].iterator();

        BlockActionData blockactiondata1;

        do {
            if (!iterator.hasNext()) {
                this.S[this.T].add(blockactiondata);
                return;
            }

            blockactiondata1 = (BlockActionData) iterator.next();
        } while (!blockactiondata1.equals(blockactiondata));

    }

    private void ak() {
        while (!this.S[this.T].isEmpty()) {
            int i = this.T;

            this.T ^= 1;
            Iterator iterator = this.S[i].iterator();

            while (iterator.hasNext()) {
                BlockActionData blockactiondata = (BlockActionData) iterator.next();

                if (this.a(blockactiondata)) {
                    // CraftBukkit - this.worldProvider.dimension -> this.dimension
                    this.server.getPlayerList().sendPacketNearby((double) blockactiondata.a().getX(), (double) blockactiondata.a().getY(), (double) blockactiondata.a().getZ(), 64.0D, this.dimension, new PacketPlayOutBlockAction(blockactiondata.a(), blockactiondata.d(), blockactiondata.b(), blockactiondata.c()));
                }
            }

            this.S[i].clear();
        }

    }

    private boolean a(BlockActionData blockactiondata) {
        IBlockData iblockdata = this.getType(blockactiondata.a());

        return iblockdata.getBlock() == blockactiondata.d() ? iblockdata.getBlock().a(this, blockactiondata.a(), iblockdata, blockactiondata.b(), blockactiondata.c()) : false;
    }

    public void saveLevel() {
        this.dataManager.a();
    }

    protected void p() {
        boolean flag = this.S();

        super.p();
        /* CraftBukkit start
        if (this.o != this.p) {
            this.server.getPlayerList().a(new PacketPlayOutGameStateChange(7, this.p), this.worldProvider.getDimension());
        }

        if (this.q != this.r) {
            this.server.getPlayerList().a(new PacketPlayOutGameStateChange(8, this.r), this.worldProvider.getDimension());
        }

        if (flag != this.S()) {
            if (flag) {
                this.server.getPlayerList().sendAll(new PacketPlayOutGameStateChange(2, 0.0F));
            } else {
                this.server.getPlayerList().sendAll(new PacketPlayOutGameStateChange(1, 0.0F));
            }

            this.server.getPlayerList().sendAll(new PacketPlayOutGameStateChange(7, this.p));
            this.server.getPlayerList().sendAll(new PacketPlayOutGameStateChange(8, this.r));
        }
        // */
        if (flag != this.S()) {
            // Only send weather packets to those affected
            for (int i = 0; i < this.players.size(); ++i) {
                if (((EntityPlayer) this.players.get(i)).world == this) {
                    ((EntityPlayer) this.players.get(i)).setPlayerWeather((!flag ? WeatherType.DOWNFALL : WeatherType.CLEAR), false);
                }
            }
            // CraftBukkit end
        }

    }

    protected int q() {
        return this.server.getPlayerList().t();
    }

    public MinecraftServer getMinecraftServer() {
        return this.server;
    }

    public EntityTracker getTracker() {
        return this.tracker;
    }

    public PlayerChunkMap getPlayerChunkMap() {
        return this.manager;
    }

    public PortalTravelAgent getTravelAgent() {
        return this.Q;
    }

    public void a(EnumParticle enumparticle, double d0, double d1, double d2, int i, double d3, double d4, double d5, double d6, int... aint) {
        this.a(enumparticle, false, d0, d1, d2, i, d3, d4, d5, d6, aint);
    }

    public void a(EnumParticle enumparticle, boolean flag, double d0, double d1, double d2, int i, double d3, double d4, double d5, double d6, int... aint) {
        // CraftBukkit - visibility api support
        sendParticles(null, enumparticle, flag, d0, d1, d2, i, d3, d4, d5, d6, aint);
    }
    
    public void sendParticles(EntityPlayer sender, EnumParticle enumparticle, boolean flag, double d0, double d1, double d2, int i, double d3, double d4, double d5, double d6, int... aint) {
        // CraftBukkit end
        PacketPlayOutWorldParticles packetplayoutworldparticles = new PacketPlayOutWorldParticles(enumparticle, flag, (float) d0, (float) d1, (float) d2, (float) d3, (float) d4, (float) d5, (float) d6, i, aint);

        for (int j = 0; j < this.players.size(); ++j) {
            EntityPlayer entityplayer = (EntityPlayer) this.players.get(j);
            if (sender != null && !sender.getBukkitEntity().canSee(entityplayer.getBukkitEntity())) continue; // CraftBukkit
            BlockPosition blockposition = entityplayer.getChunkCoordinates();
            double d7 = blockposition.c(d0, d1, d2);

            if (d7 <= 256.0D || flag && d7 <= 65536.0D) {
                entityplayer.playerConnection.sendPacket(packetplayoutworldparticles);
            }
        }

    }

    public Entity getEntity(UUID uuid) {
        return (Entity) this.entitiesByUUID.get(uuid);
    }

    public ListenableFuture postToMainThread(Runnable runnable) {
        return this.server.postToMainThread(runnable);
    }

    public boolean isMainThread() {
        return this.server.isMainThread();
    }
}
