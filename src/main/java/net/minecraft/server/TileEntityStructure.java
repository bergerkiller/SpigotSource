package net.minecraft.server;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class TileEntityStructure extends TileEntity {

    private String a = "";
    private String f = "";
    private String g = "";
    private BlockPosition h = new BlockPosition(1, 1, 1);
    private BlockPosition i;
    private EnumBlockMirror j;
    private EnumBlockRotation k;
    private TileEntityStructure.UsageMode l;
    private boolean m;

    public TileEntityStructure() {
        this.i = BlockPosition.ZERO;
        this.j = EnumBlockMirror.NONE;
        this.k = EnumBlockRotation.NONE;
        this.l = TileEntityStructure.UsageMode.DATA;
    }

    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        nbttagcompound.setString("name", this.a);
        nbttagcompound.setString("author", this.f);
        nbttagcompound.setString("metadata", this.g);
        nbttagcompound.setInt("posX", this.h.getX());
        nbttagcompound.setInt("posY", this.h.getY());
        nbttagcompound.setInt("posZ", this.h.getZ());
        nbttagcompound.setInt("sizeX", this.i.getX());
        nbttagcompound.setInt("sizeY", this.i.getY());
        nbttagcompound.setInt("sizeZ", this.i.getZ());
        nbttagcompound.setString("rotation", this.k.toString());
        nbttagcompound.setString("mirror", this.j.toString());
        nbttagcompound.setString("mode", this.l.toString());
        nbttagcompound.setBoolean("ignoreEntities", this.m);
        return nbttagcompound;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.getString("name");
        this.f = nbttagcompound.getString("author");
        this.g = nbttagcompound.getString("metadata");
        this.h = new BlockPosition(nbttagcompound.getInt("posX"), nbttagcompound.getInt("posY"), nbttagcompound.getInt("posZ"));
        this.i = new BlockPosition(nbttagcompound.getInt("sizeX"), nbttagcompound.getInt("sizeY"), nbttagcompound.getInt("sizeZ"));

        try {
            this.k = EnumBlockRotation.valueOf(nbttagcompound.getString("rotation"));
        } catch (IllegalArgumentException illegalargumentexception) {
            this.k = EnumBlockRotation.NONE;
        }

        try {
            this.j = EnumBlockMirror.valueOf(nbttagcompound.getString("mirror"));
        } catch (IllegalArgumentException illegalargumentexception1) {
            this.j = EnumBlockMirror.NONE;
        }

        try {
            this.l = TileEntityStructure.UsageMode.valueOf(nbttagcompound.getString("mode"));
        } catch (IllegalArgumentException illegalargumentexception2) {
            this.l = TileEntityStructure.UsageMode.DATA;
        }

        this.m = nbttagcompound.getBoolean("ignoreEntities");
    }

    @Nullable
    public PacketPlayOutTileEntityData getUpdatePacket() {
        return new PacketPlayOutTileEntityData(this.position, 7, this.E_());
    }

    public NBTTagCompound E_() {
        return this.save(new NBTTagCompound());
    }

    public void a(String s) {
        this.a = s;
    }

    public void b(BlockPosition blockposition) {
        this.h = blockposition;
    }

    public void c(BlockPosition blockposition) {
        this.i = blockposition;
    }

    public void a(EnumBlockMirror enumblockmirror) {
        this.j = enumblockmirror;
    }

    public void a(EnumBlockRotation enumblockrotation) {
        this.k = enumblockrotation;
    }

    public void b(String s) {
        this.g = s;
    }

    public void a(TileEntityStructure.UsageMode tileentitystructure_usagemode) {
        this.l = tileentitystructure_usagemode;
        IBlockData iblockdata = this.world.getType(this.getPosition());

        if (iblockdata.getBlock() == Blocks.STRUCTURE_BLOCK) {
            this.world.setTypeAndData(this.getPosition(), iblockdata.set(BlockStructure.a, tileentitystructure_usagemode), 2);
        }

    }

    public void a(boolean flag) {
        this.m = flag;
    }

    public boolean m() {
        if (this.l != TileEntityStructure.UsageMode.SAVE) {
            return false;
        } else {
            BlockPosition blockposition = this.getPosition();
            boolean flag = true;
            BlockPosition blockposition1 = new BlockPosition(blockposition.getX() - 128, 0, blockposition.getZ() - 128);
            BlockPosition blockposition2 = new BlockPosition(blockposition.getX() + 128, 255, blockposition.getZ() + 128);
            List list = this.a(blockposition1, blockposition2);
            List list1 = this.a(list);

            if (list1.size() < 1) {
                return false;
            } else {
                StructureBoundingBox structureboundingbox = this.a(blockposition, list1);

                if (structureboundingbox.d - structureboundingbox.a > 1 && structureboundingbox.e - structureboundingbox.b > 1 && structureboundingbox.f - structureboundingbox.c > 1) {
                    this.h = new BlockPosition(structureboundingbox.a - blockposition.getX() + 1, structureboundingbox.b - blockposition.getY() + 1, structureboundingbox.c - blockposition.getZ() + 1);
                    this.i = new BlockPosition(structureboundingbox.d - structureboundingbox.a - 1, structureboundingbox.e - structureboundingbox.b - 1, structureboundingbox.f - structureboundingbox.c - 1);
                    this.update();
                    IBlockData iblockdata = this.world.getType(blockposition);

                    this.world.notify(blockposition, iblockdata, iblockdata, 3);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    private List<TileEntityStructure> a(List<TileEntityStructure> list) {
        Iterable iterable = Iterables.filter(list, new Predicate() {
            public boolean a(@Nullable TileEntityStructure tileentitystructure) {
                return tileentitystructure.l == TileEntityStructure.UsageMode.CORNER && TileEntityStructure.this.a.equals(tileentitystructure.a);
            }

            public boolean apply(Object object) {
                return this.a((TileEntityStructure) object);
            }
        });

        return Lists.newArrayList(iterable);
    }

    private List<TileEntityStructure> a(BlockPosition blockposition, BlockPosition blockposition1) {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = BlockPosition.b(blockposition, blockposition1).iterator();

        while (iterator.hasNext()) {
            BlockPosition.MutableBlockPosition blockposition_mutableblockposition = (BlockPosition.MutableBlockPosition) iterator.next();
            IBlockData iblockdata = this.world.getType(blockposition_mutableblockposition);

            if (iblockdata.getBlock() == Blocks.STRUCTURE_BLOCK) {
                TileEntity tileentity = this.world.getTileEntity(blockposition_mutableblockposition);

                if (tileentity != null && tileentity instanceof TileEntityStructure) {
                    arraylist.add((TileEntityStructure) tileentity);
                }
            }
        }

        return arraylist;
    }

    private StructureBoundingBox a(BlockPosition blockposition, List<TileEntityStructure> list) {
        StructureBoundingBox structureboundingbox;

        if (list.size() > 1) {
            BlockPosition blockposition1 = ((TileEntityStructure) list.get(0)).getPosition();

            structureboundingbox = new StructureBoundingBox(blockposition1, blockposition1);
        } else {
            structureboundingbox = new StructureBoundingBox(blockposition, blockposition);
        }

        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            TileEntityStructure tileentitystructure = (TileEntityStructure) iterator.next();
            BlockPosition blockposition2 = tileentitystructure.getPosition();

            if (blockposition2.getX() < structureboundingbox.a) {
                structureboundingbox.a = blockposition2.getX();
            } else if (blockposition2.getX() > structureboundingbox.d) {
                structureboundingbox.d = blockposition2.getX();
            }

            if (blockposition2.getY() < structureboundingbox.b) {
                structureboundingbox.b = blockposition2.getY();
            } else if (blockposition2.getY() > structureboundingbox.e) {
                structureboundingbox.e = blockposition2.getY();
            }

            if (blockposition2.getZ() < structureboundingbox.c) {
                structureboundingbox.c = blockposition2.getZ();
            } else if (blockposition2.getZ() > structureboundingbox.f) {
                structureboundingbox.f = blockposition2.getZ();
            }
        }

        return structureboundingbox;
    }

    public boolean n() {
        if (this.l == TileEntityStructure.UsageMode.SAVE && !this.world.isClientSide) {
            BlockPosition blockposition = this.getPosition().a(this.h);
            WorldServer worldserver = (WorldServer) this.world;
            MinecraftServer minecraftserver = this.world.getMinecraftServer();
            DefinedStructureManager definedstructuremanager = worldserver.y();
            DefinedStructure definedstructure = definedstructuremanager.a(minecraftserver, new MinecraftKey(this.a));

            definedstructure.a(this.world, blockposition, this.i, !this.m, Blocks.BARRIER);
            definedstructure.a(this.f);
            definedstructuremanager.c(minecraftserver, new MinecraftKey(this.a));
            return true;
        } else {
            return false;
        }
    }

    public boolean o() {
        if (this.l == TileEntityStructure.UsageMode.LOAD && !this.world.isClientSide) {
            BlockPosition blockposition = this.getPosition().a(this.h);
            WorldServer worldserver = (WorldServer) this.world;
            MinecraftServer minecraftserver = this.world.getMinecraftServer();
            DefinedStructureManager definedstructuremanager = worldserver.y();
            DefinedStructure definedstructure = definedstructuremanager.a(minecraftserver, new MinecraftKey(this.a));

            if (!UtilColor.b(definedstructure.b())) {
                this.f = definedstructure.b();
            }

            if (!this.i.equals(definedstructure.a())) {
                this.i = definedstructure.a();
                return false;
            } else {
                BlockPosition blockposition1 = definedstructure.a(this.k);
                Iterator iterator = this.world.getEntities((Entity) null, new AxisAlignedBB(blockposition, blockposition1.a(blockposition).a(-1, -1, -1))).iterator();

                while (iterator.hasNext()) {
                    Entity entity = (Entity) iterator.next();

                    this.world.removeEntity(entity);
                }

                DefinedStructureInfo definedstructureinfo = (new DefinedStructureInfo()).a(this.j).a(this.k).a(this.m).a((ChunkCoordIntPair) null).a((Block) null).b(false);

                definedstructure.a(this.world, blockposition, definedstructureinfo);
                return true;
            }
        } else {
            return false;
        }
    }

    public static enum UsageMode implements INamable {

        SAVE("save", 0), LOAD("load", 1), CORNER("corner", 2), DATA("data", 3);

        private static final TileEntityStructure.UsageMode[] e = new TileEntityStructure.UsageMode[values().length];
        private final String f;
        private final int g;

        private UsageMode(String s, int i) {
            this.f = s;
            this.g = i;
        }

        public String getName() {
            return this.f;
        }

        public int a() {
            return this.g;
        }

        public static TileEntityStructure.UsageMode a(int i) {
            if (i < 0 || i >= TileEntityStructure.UsageMode.e.length) {
                i = 0;
            }

            return TileEntityStructure.UsageMode.e[i];
        }

        static {
            TileEntityStructure.UsageMode[] atileentitystructure_usagemode = values();
            int i = atileentitystructure_usagemode.length;

            for (int j = 0; j < i; ++j) {
                TileEntityStructure.UsageMode tileentitystructure_usagemode = atileentitystructure_usagemode[j];

                TileEntityStructure.UsageMode.e[tileentitystructure_usagemode.a()] = tileentitystructure_usagemode;
            }

        }
    }
}
