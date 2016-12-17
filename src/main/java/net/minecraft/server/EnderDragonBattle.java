package net.minecraft.server;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderDragonBattle {

    private static final Logger a = LogManager.getLogger();
    private static final Predicate<EntityPlayer> b = Predicates.and(IEntitySelector.a, IEntitySelector.a(0.0D, 128.0D, 0.0D, 192.0D));
    private final BossBattleServer c;
    private final WorldServer d;
    private final List<Integer> e;
    private final ShapeDetector f;
    private int g;
    private int h;
    private int i;
    private int j;
    private boolean k;
    private boolean l;
    private UUID m;
    private boolean n;
    private BlockPosition o;
    private EnumDragonRespawn p;
    private int q;
    private List<EntityEnderCrystal> r;

    public EnderDragonBattle(WorldServer worldserver, NBTTagCompound nbttagcompound) {
        this.c = (BossBattleServer) (new BossBattleServer(new ChatMessage("entity.EnderDragon.name", new Object[0]), BossBattle.BarColor.PINK, BossBattle.BarStyle.PROGRESS)).setPlayMusic(true).c(true);
        this.e = Lists.newArrayList();
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.k = false;
        this.l = false;
        this.m = null;
        this.n = false;
        this.o = null;
        this.q = 0;
        this.d = worldserver;
        if (nbttagcompound.hasKeyOfType("DragonKilled", 1)) {
            if (nbttagcompound.b("DragonUUID")) {
                this.m = nbttagcompound.a("DragonUUID");
            }

            this.k = nbttagcompound.getBoolean("DragonKilled");
            this.l = nbttagcompound.getBoolean("PreviouslyKilled");
            if (nbttagcompound.getBoolean("IsRespawning")) {
                this.p = EnumDragonRespawn.START;
            }

            if (nbttagcompound.hasKeyOfType("ExitPortalLocation", 10)) {
                this.o = GameProfileSerializer.c(nbttagcompound.getCompound("ExitPortalLocation"));
            }
        } else {
            this.n = true;
            this.k = true;
            this.l = true;
        }

        if (nbttagcompound.hasKeyOfType("Gateways", 9)) {
            NBTTagList nbttaglist = nbttagcompound.getList("Gateways", 3);

            for (int i = 0; i < nbttaglist.size(); ++i) {
                this.e.add(Integer.valueOf(nbttaglist.c(i)));
            }
        } else {
            this.e.addAll(ContiguousSet.create(Range.closedOpen(Integer.valueOf(0), Integer.valueOf(20)), DiscreteDomain.integers()));
            Collections.shuffle(this.e, new Random(worldserver.getSeed()));
        }

        this.f = ShapeDetectorBuilder.a().a(new String[] { "       ", "       ", "       ", "   #   ", "       ", "       ", "       "}).a(new String[] { "       ", "       ", "       ", "   #   ", "       ", "       ", "       "}).a(new String[] { "       ", "       ", "       ", "   #   ", "       ", "       ", "       "}).a(new String[] { "  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  "}).a(new String[] { "       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       "}).a('#', ShapeDetectorBlock.a((Predicate) BlockPredicate.a(Blocks.BEDROCK))).b();
    }

    public NBTTagCompound a() {
        if (this.n) {
            return new NBTTagCompound();
        } else {
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            if (this.m != null) {
                nbttagcompound.a("DragonUUID", this.m);
            }

            nbttagcompound.setBoolean("DragonKilled", this.k);
            nbttagcompound.setBoolean("PreviouslyKilled", this.l);
            if (this.o != null) {
                nbttagcompound.set("ExitPortalLocation", GameProfileSerializer.a(this.o));
            }

            NBTTagList nbttaglist = new NBTTagList();
            Iterator iterator = this.e.iterator();

            while (iterator.hasNext()) {
                int i = ((Integer) iterator.next()).intValue();

                nbttaglist.add(new NBTTagInt(i));
            }

            nbttagcompound.set("Gateways", nbttaglist);
            return nbttagcompound;
        }
    }

    public void b() {
        this.c.setVisible(!this.k);
        if (++this.j >= 20) {
            this.j();
            this.j = 0;
        }

        if (!this.c.getPlayers().isEmpty()) {
            List list;

            if (this.n) {
                EnderDragonBattle.a.info("Scanning for legacy world dragon fight...");
                this.i();
                this.n = false;
                if (this.g()) {
                    EnderDragonBattle.a.info("Found that the dragon has been killed in this world already.");
                    this.l = true;
                } else {
                    EnderDragonBattle.a.info("Found that the dragon has not yet been killed in this world.");
                    this.l = false;
                }

                list = this.d.a(EntityEnderDragon.class, IEntitySelector.a);
                if (!list.isEmpty()) {
                    EntityEnderDragon entityenderdragon = (EntityEnderDragon) list.get(0);

                    this.m = entityenderdragon.getUniqueID();
                    EnderDragonBattle.a.info("Found that there\'s a dragon still alive (" + entityenderdragon + ")");
                    this.k = false;
                } else {
                    this.k = true;
                }

                if (!this.l && this.k) {
                    this.k = false;
                }
            }

            if (this.p != null) {
                if (this.r == null) {
                    this.p = null;
                    this.e();
                }

                this.p.a(this.d, this, this.r, this.q++, this.o);
            }

            if (!this.k) {
                if (this.m == null || ++this.g >= 1200) {
                    this.i();
                    list = this.d.a(EntityEnderDragon.class, IEntitySelector.a);
                    if (!list.isEmpty()) {
                        EnderDragonBattle.a.debug("Haven\'t seen our dragon, but found another one to use.");
                        this.m = ((EntityEnderDragon) list.get(0)).getUniqueID();
                    } else {
                        EnderDragonBattle.a.debug("Haven\'t seen the dragon, respawning it");
                        this.m();
                        this.a(false);
                    }

                    this.g = 0;
                }

                if (++this.i >= 100) {
                    this.k();
                    this.i = 0;
                }
            }
        }

    }

    protected void a(EnumDragonRespawn enumdragonrespawn) {
        if (this.p == null) {
            throw new IllegalStateException("Dragon respawn isn\'t in progress, can\'t skip ahead in the animation.");
        } else {
            this.q = 0;
            if (enumdragonrespawn == EnumDragonRespawn.END) {
                this.p = null;
                this.k = false;
                this.m();
            } else {
                this.p = enumdragonrespawn;
            }

        }
    }

    private boolean g() {
        for (int i = -8; i <= 8; ++i) {
            int j = -8;

            label27:
            while (j <= 8) {
                Chunk chunk = this.d.getChunkAt(i, j);
                Iterator iterator = chunk.getTileEntities().values().iterator();

                TileEntity tileentity;

                do {
                    if (!iterator.hasNext()) {
                        ++j;
                        continue label27;
                    }

                    tileentity = (TileEntity) iterator.next();
                } while (!(tileentity instanceof TileEntityEnderPortal));

                return true;
            }
        }

        return false;
    }

    @Nullable
    private ShapeDetector.ShapeDetectorCollection h() {
        int i;
        int j;

        for (i = -8; i <= 8; ++i) {
            for (j = -8; j <= 8; ++j) {
                Chunk chunk = this.d.getChunkAt(i, j);
                Iterator iterator = chunk.getTileEntities().values().iterator();

                while (iterator.hasNext()) {
                    TileEntity tileentity = (TileEntity) iterator.next();

                    if (tileentity instanceof TileEntityEnderPortal) {
                        ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection = this.f.a(this.d, tileentity.getPosition());

                        if (shapedetector_shapedetectorcollection != null) {
                            BlockPosition blockposition = shapedetector_shapedetectorcollection.a(3, 3, 3).getPosition(); // CraftBukkit - 4 -> 3

                            if (this.o == null && blockposition.getX() == 0 && blockposition.getZ() == 0) {
                                this.o = blockposition;
                            }

                            return shapedetector_shapedetectorcollection;
                        }
                    }
                }
            }
        }

        i = this.d.getHighestBlockYAt(WorldGenEndTrophy.a).getY();

        for (j = i; j >= 0; --j) {
            ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection1 = this.f.a(this.d, new BlockPosition(WorldGenEndTrophy.a.getX(), j, WorldGenEndTrophy.a.getZ()));

            if (shapedetector_shapedetectorcollection1 != null) {
                if (this.o == null) {
                    this.o = shapedetector_shapedetectorcollection1.a(3, 3, 3).getPosition(); // CraftBukkit - 4 -> 3
                }

                return shapedetector_shapedetectorcollection1;
            }
        }

        return null;
    }

    private void i() {
        for (int i = -8; i <= 8; ++i) {
            for (int j = -8; j <= 8; ++j) {
                this.d.getChunkAt(i, j);
            }
        }

    }

    private void j() {
        HashSet hashset = Sets.newHashSet();
        Iterator iterator = this.d.b(EntityPlayer.class, EnderDragonBattle.b).iterator();

        while (iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer) iterator.next();

            this.c.addPlayer(entityplayer);
            hashset.add(entityplayer);
        }

        HashSet hashset1 = Sets.newHashSet(this.c.getPlayers());

        hashset1.removeAll(hashset);
        Iterator iterator1 = hashset1.iterator();

        while (iterator1.hasNext()) {
            EntityPlayer entityplayer1 = (EntityPlayer) iterator1.next();

            this.c.removePlayer(entityplayer1);
        }

    }

    private void k() {
        this.i = 0;
        this.h = 0;
        WorldGenEnder.Spike[] aworldgenender_spike = BiomeTheEndDecorator.a(this.d);
        int i = aworldgenender_spike.length;

        for (int j = 0; j < i; ++j) {
            WorldGenEnder.Spike worldgenender_spike = aworldgenender_spike[j];

            this.h += this.d.a(EntityEnderCrystal.class, worldgenender_spike.f()).size();
        }

        EnderDragonBattle.a.debug("Found {} end crystals still alive", new Object[] { Integer.valueOf(this.h)});
    }

    public void a(EntityEnderDragon entityenderdragon) {
        if (entityenderdragon.getUniqueID().equals(this.m)) {
            this.c.setProgress(0.0F);
            this.c.setVisible(false);
            this.a(true);
            this.l();
            if (!this.l) {
                this.d.setTypeUpdate(this.d.getHighestBlockYAt(WorldGenEndTrophy.a), Blocks.DRAGON_EGG.getBlockData());
            }

            this.l = true;
            this.k = true;
        }

    }

    private void l() {
        if (!this.e.isEmpty()) {
            int i = ((Integer) this.e.remove(this.e.size() - 1)).intValue();
            int j = (int) (96.0D * Math.cos(2.0D * (-3.141592653589793D + 0.15707963267948966D * (double) i)));
            int k = (int) (96.0D * Math.sin(2.0D * (-3.141592653589793D + 0.15707963267948966D * (double) i)));

            this.a(new BlockPosition(j, 75, k));
        }
    }

    private void a(BlockPosition blockposition) {
        this.d.triggerEffect(3000, blockposition, 0);
        (new WorldGenEndGateway()).generate(this.d, new Random(), blockposition);
    }

    private void a(boolean flag) {
        WorldGenEndTrophy worldgenendtrophy = new WorldGenEndTrophy(flag);

        if (this.o == null) {
            for (this.o = this.d.q(WorldGenEndTrophy.a).down(); this.d.getType(this.o).getBlock() == Blocks.BEDROCK && this.o.getY() > this.d.K(); this.o = this.o.down()) {
                ;
            }
        }

        worldgenendtrophy.generate(this.d, new Random(), this.o);
    }

    private void m() {
        this.d.getChunkAtWorldCoords(new BlockPosition(0, 128, 0));
        EntityEnderDragon entityenderdragon = new EntityEnderDragon(this.d);

        entityenderdragon.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);
        entityenderdragon.setPositionRotation(0.0D, 128.0D, 0.0D, this.d.random.nextFloat() * 360.0F, 0.0F);
        this.d.addEntity(entityenderdragon);
        this.m = entityenderdragon.getUniqueID();
    }

    public void b(EntityEnderDragon entityenderdragon) {
        if (entityenderdragon.getUniqueID().equals(this.m)) {
            this.c.setProgress(entityenderdragon.getHealth() / entityenderdragon.getMaxHealth());
            this.g = 0;
        }

    }

    public int c() {
        return this.h;
    }

    public void a(EntityEnderCrystal entityendercrystal, DamageSource damagesource) {
        if (this.p != null && this.r.contains(entityendercrystal)) {
            EnderDragonBattle.a.debug("Aborting respawn sequence");
            this.p = null;
            this.q = 0;
            this.f();
            this.a(true);
        } else {
            this.k();
            Entity entity = this.d.getEntity(this.m);

            if (entity instanceof EntityEnderDragon) {
                ((EntityEnderDragon) entity).a(entityendercrystal, new BlockPosition(entityendercrystal), damagesource);
            }
        }

    }

    public boolean d() {
        return this.l;
    }

    public void e() {
        if (this.k && this.p == null) {
            BlockPosition blockposition = this.o;

            if (blockposition == null) {
                EnderDragonBattle.a.debug("Tried to respawn, but need to find the portal first.");
                ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection = this.h();

                if (shapedetector_shapedetectorcollection == null) {
                    EnderDragonBattle.a.debug("Couldn\'t find a portal, so we made one.");
                    this.a(true);
                    blockposition = this.o;
                } else {
                    EnderDragonBattle.a.debug("Found the exit portal & temporarily using it.");
                    blockposition = shapedetector_shapedetectorcollection.a(3, 3, 3).getPosition();
                }
            }

            ArrayList arraylist = Lists.newArrayList();
            BlockPosition blockposition1 = blockposition.up(1);
            Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

            while (iterator.hasNext()) {
                EnumDirection enumdirection = (EnumDirection) iterator.next();
                List list = this.d.a(EntityEnderCrystal.class, new AxisAlignedBB(blockposition1.shift(enumdirection, 2)));

                if (list.isEmpty()) {
                    return;
                }

                arraylist.addAll(list);
            }

            EnderDragonBattle.a.debug("Found all crystals, respawning dragon.");
            this.a((List) arraylist);
        }

    }

    private void a(List<EntityEnderCrystal> list) {
        if (this.k && this.p == null) {
            for (ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection = this.h(); shapedetector_shapedetectorcollection != null; shapedetector_shapedetectorcollection = this.h()) {
                for (int i = 0; i < this.f.c(); ++i) {
                    for (int j = 0; j < this.f.b(); ++j) {
                        for (int k = 0; k < this.f.a(); ++k) {
                            ShapeDetectorBlock shapedetectorblock = shapedetector_shapedetectorcollection.a(i, j, k);

                            if (shapedetectorblock.a().getBlock() == Blocks.BEDROCK || shapedetectorblock.a().getBlock() == Blocks.END_PORTAL) {
                                this.d.setTypeUpdate(shapedetectorblock.getPosition(), Blocks.END_STONE.getBlockData());
                            }
                        }
                    }
                }
            }

            this.p = EnumDragonRespawn.START;
            this.q = 0;
            this.a(false);
            this.r = list;
        }

    }

    public void f() {
        WorldGenEnder.Spike[] aworldgenender_spike = BiomeTheEndDecorator.a(this.d);
        int i = aworldgenender_spike.length;

        for (int j = 0; j < i; ++j) {
            WorldGenEnder.Spike worldgenender_spike = aworldgenender_spike[j];
            List list = this.d.a(EntityEnderCrystal.class, worldgenender_spike.f());
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityEnderCrystal entityendercrystal = (EntityEnderCrystal) iterator.next();

                entityendercrystal.setInvulnerable(false);
                entityendercrystal.setBeamTarget((BlockPosition) null);
            }
        }

    }
}
