package net.minecraft.server;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;

public class DefinedStructure {

    private final List<DefinedStructure.BlockInfo> a = Lists.newArrayList();
    private final List<DefinedStructure.EntityInfo> b = Lists.newArrayList();
    private BlockPosition c;
    private String d;

    public DefinedStructure() {
        this.c = BlockPosition.ZERO;
        this.d = "?";
    }

    public BlockPosition a() {
        return this.c;
    }

    public void a(String s) {
        this.d = s;
    }

    public String b() {
        return this.d;
    }

    public void a(World world, BlockPosition blockposition, BlockPosition blockposition1, boolean flag, @Nullable Block block) {
        if (blockposition1.getX() >= 1 && blockposition1.getY() >= 1 && blockposition1.getZ() >= 1) {
            BlockPosition blockposition2 = blockposition.a(blockposition1).a(-1, -1, -1);
            ArrayList arraylist = Lists.newArrayList();
            ArrayList arraylist1 = Lists.newArrayList();
            ArrayList arraylist2 = Lists.newArrayList();
            BlockPosition blockposition3 = new BlockPosition(Math.min(blockposition.getX(), blockposition2.getX()), Math.min(blockposition.getY(), blockposition2.getY()), Math.min(blockposition.getZ(), blockposition2.getZ()));
            BlockPosition blockposition4 = new BlockPosition(Math.max(blockposition.getX(), blockposition2.getX()), Math.max(blockposition.getY(), blockposition2.getY()), Math.max(blockposition.getZ(), blockposition2.getZ()));

            this.c = blockposition1;
            Iterator iterator = BlockPosition.b(blockposition3, blockposition4).iterator();

            while (iterator.hasNext()) {
                BlockPosition.MutableBlockPosition blockposition_mutableblockposition = (BlockPosition.MutableBlockPosition) iterator.next();
                BlockPosition blockposition5 = blockposition_mutableblockposition.b(blockposition3);
                IBlockData iblockdata = world.getType(blockposition_mutableblockposition);

                if (block == null || block != iblockdata.getBlock()) {
                    TileEntity tileentity = world.getTileEntity(blockposition_mutableblockposition);

                    if (tileentity != null) {
                        NBTTagCompound nbttagcompound = tileentity.save(new NBTTagCompound());

                        nbttagcompound.remove("x");
                        nbttagcompound.remove("y");
                        nbttagcompound.remove("z");
                        arraylist1.add(new DefinedStructure.BlockInfo(blockposition5, iblockdata, nbttagcompound, null));
                    } else if (!iblockdata.b() && !iblockdata.h()) {
                        arraylist2.add(new DefinedStructure.BlockInfo(blockposition5, iblockdata, (NBTTagCompound) null, null));
                    } else {
                        arraylist.add(new DefinedStructure.BlockInfo(blockposition5, iblockdata, (NBTTagCompound) null, null));
                    }
                }
            }

            this.a.clear();
            this.a.addAll(arraylist);
            this.a.addAll(arraylist1);
            this.a.addAll(arraylist2);
            if (flag) {
                this.a(world, blockposition3, blockposition4.a(1, 1, 1));
            } else {
                this.b.clear();
            }

        }
    }

    private void a(World world, BlockPosition blockposition, BlockPosition blockposition1) {
        List list = world.a(Entity.class, new AxisAlignedBB(blockposition, blockposition1), new Predicate() {
            public boolean a(@Nullable Entity entity) {
                return !(entity instanceof EntityHuman);
            }

            public boolean apply(Object object) {
                return this.a((Entity) object);
            }
        });

        this.b.clear();

        Vec3D vec3d;
        NBTTagCompound nbttagcompound;
        BlockPosition blockposition2;

        for (Iterator iterator = list.iterator(); iterator.hasNext(); this.b.add(new DefinedStructure.EntityInfo(vec3d, blockposition2, nbttagcompound, null))) {
            Entity entity = (Entity) iterator.next();

            vec3d = new Vec3D(entity.locX - (double) blockposition.getX(), entity.locY - (double) blockposition.getY(), entity.locZ - (double) blockposition.getZ());
            nbttagcompound = new NBTTagCompound();
            entity.d(nbttagcompound);
            if (entity instanceof EntityPainting) {
                blockposition2 = ((EntityPainting) entity).getBlockPosition().b(blockposition);
            } else {
                blockposition2 = new BlockPosition(vec3d);
            }
        }

    }

    public Map<BlockPosition, String> a(BlockPosition blockposition, DefinedStructureInfo definedstructureinfo) {
        HashMap hashmap = Maps.newHashMap();
        StructureBoundingBox structureboundingbox = definedstructureinfo.g();
        Iterator iterator = this.a.iterator();

        while (iterator.hasNext()) {
            DefinedStructure.BlockInfo definedstructure_blockinfo = (DefinedStructure.BlockInfo) iterator.next();
            BlockPosition blockposition1 = a(definedstructureinfo, definedstructure_blockinfo.a).a(blockposition);

            if (structureboundingbox == null || structureboundingbox.b((BaseBlockPosition) blockposition1)) {
                IBlockData iblockdata = definedstructure_blockinfo.b;

                if (iblockdata.getBlock() == Blocks.STRUCTURE_BLOCK && definedstructure_blockinfo.c != null) {
                    TileEntityStructure.UsageMode tileentitystructure_usagemode = TileEntityStructure.UsageMode.valueOf(definedstructure_blockinfo.c.getString("mode"));

                    if (tileentitystructure_usagemode == TileEntityStructure.UsageMode.DATA) {
                        hashmap.put(blockposition1, definedstructure_blockinfo.c.getString("metadata"));
                    }
                }
            }
        }

        return hashmap;
    }

    public BlockPosition a(DefinedStructureInfo definedstructureinfo, BlockPosition blockposition, DefinedStructureInfo definedstructureinfo1, BlockPosition blockposition1) {
        BlockPosition blockposition2 = a(definedstructureinfo, blockposition);
        BlockPosition blockposition3 = a(definedstructureinfo1, blockposition1);

        return blockposition2.b(blockposition3);
    }

    public static BlockPosition a(DefinedStructureInfo definedstructureinfo, BlockPosition blockposition) {
        return a(blockposition, definedstructureinfo.b(), definedstructureinfo.c());
    }

    public void a(World world, BlockPosition blockposition, DefinedStructureInfo definedstructureinfo) {
        definedstructureinfo.i();
        this.b(world, blockposition, definedstructureinfo);
    }

    public void b(World world, BlockPosition blockposition, DefinedStructureInfo definedstructureinfo) {
        if (!this.a.isEmpty() && this.c.getX() >= 1 && this.c.getY() >= 1 && this.c.getZ() >= 1) {
            Block block = definedstructureinfo.f();
            StructureBoundingBox structureboundingbox = definedstructureinfo.g();
            Iterator iterator = this.a.iterator();

            DefinedStructure.BlockInfo definedstructure_blockinfo;

            while (iterator.hasNext()) {
                definedstructure_blockinfo = (DefinedStructure.BlockInfo) iterator.next();
                Block block1 = definedstructure_blockinfo.b.getBlock();

                if ((block == null || block != block1) && (!definedstructureinfo.h() || block1 != Blocks.STRUCTURE_BLOCK)) {
                    BlockPosition blockposition1 = a(definedstructureinfo, definedstructure_blockinfo.a).a(blockposition);

                    if (structureboundingbox == null || structureboundingbox.b((BaseBlockPosition) blockposition1)) {
                        IBlockData iblockdata = definedstructure_blockinfo.b.a(definedstructureinfo.b());
                        IBlockData iblockdata1 = iblockdata.a(definedstructureinfo.c());
                        TileEntity tileentity;

                        if (definedstructure_blockinfo.c != null) {
                            tileentity = world.getTileEntity(blockposition1);
                            if (tileentity != null) {
                                if (tileentity instanceof IInventory) {
                                    ((IInventory) tileentity).l();
                                }

                                world.setTypeAndData(blockposition1, Blocks.BARRIER.getBlockData(), 4);
                            }
                        }

                        if (world.setTypeAndData(blockposition1, iblockdata1, 2) && definedstructure_blockinfo.c != null) {
                            tileentity = world.getTileEntity(blockposition1);
                            if (tileentity != null) {
                                definedstructure_blockinfo.c.setInt("x", blockposition1.getX());
                                definedstructure_blockinfo.c.setInt("y", blockposition1.getY());
                                definedstructure_blockinfo.c.setInt("z", blockposition1.getZ());
                                tileentity.a(definedstructure_blockinfo.c);
                            }
                        }
                    }
                }
            }

            iterator = this.a.iterator();

            while (iterator.hasNext()) {
                definedstructure_blockinfo = (DefinedStructure.BlockInfo) iterator.next();
                if (block == null || block != definedstructure_blockinfo.b.getBlock()) {
                    BlockPosition blockposition2 = a(definedstructureinfo, definedstructure_blockinfo.a).a(blockposition);

                    if (structureboundingbox == null || structureboundingbox.b((BaseBlockPosition) blockposition2)) {
                        world.update(blockposition2, definedstructure_blockinfo.b.getBlock());
                        if (definedstructure_blockinfo.c != null) {
                            TileEntity tileentity1 = world.getTileEntity(blockposition2);

                            if (tileentity1 != null) {
                                tileentity1.update();
                            }
                        }
                    }
                }
            }

            if (!definedstructureinfo.e()) {
                this.a(world, blockposition, definedstructureinfo.b(), definedstructureinfo.c(), structureboundingbox);
            }

        }
    }

    private void a(World world, BlockPosition blockposition, EnumBlockMirror enumblockmirror, EnumBlockRotation enumblockrotation, @Nullable StructureBoundingBox structureboundingbox) {
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            DefinedStructure.EntityInfo definedstructure_entityinfo = (DefinedStructure.EntityInfo) iterator.next();
            BlockPosition blockposition1 = a(definedstructure_entityinfo.b, enumblockmirror, enumblockrotation).a(blockposition);

            if (structureboundingbox == null || structureboundingbox.b((BaseBlockPosition) blockposition1)) {
                NBTTagCompound nbttagcompound = definedstructure_entityinfo.c;
                Vec3D vec3d = a(definedstructure_entityinfo.a, enumblockmirror, enumblockrotation);
                Vec3D vec3d1 = vec3d.add((double) blockposition.getX(), (double) blockposition.getY(), (double) blockposition.getZ());
                NBTTagList nbttaglist = new NBTTagList();

                nbttaglist.add(new NBTTagDouble(vec3d1.x));
                nbttaglist.add(new NBTTagDouble(vec3d1.y));
                nbttaglist.add(new NBTTagDouble(vec3d1.z));
                nbttagcompound.set("Pos", nbttaglist);
                nbttagcompound.a("UUID", UUID.randomUUID());

                Entity entity;

                try {
                    entity = EntityTypes.a(nbttagcompound, world);
                } catch (Exception exception) {
                    entity = null;
                }

                if (entity != null) {
                    if (entity instanceof EntityPainting) {
                        entity.a(enumblockmirror);
                        entity.a(enumblockrotation);
                        entity.setPosition((double) blockposition1.getX(), (double) blockposition1.getY(), (double) blockposition1.getZ());
                        entity.setPositionRotation(vec3d1.x, vec3d1.y, vec3d1.z, entity.yaw, entity.pitch);
                    } else {
                        float f = entity.a(enumblockmirror);

                        f += entity.yaw - entity.a(enumblockrotation);
                        entity.setPositionRotation(vec3d1.x, vec3d1.y, vec3d1.z, f, entity.pitch);
                    }

                    world.addEntity(entity);
                }
            }
        }

    }

    public BlockPosition a(EnumBlockRotation enumblockrotation) {
        switch (DefinedStructure.SyntheticClass_1.a[enumblockrotation.ordinal()]) {
        case 1:
        case 2:
            return new BlockPosition(this.c.getZ(), this.c.getY(), this.c.getX());

        default:
            return this.c;
        }
    }

    private static BlockPosition a(BlockPosition blockposition, EnumBlockMirror enumblockmirror, EnumBlockRotation enumblockrotation) {
        int i = blockposition.getX();
        int j = blockposition.getY();
        int k = blockposition.getZ();
        boolean flag = true;

        switch (DefinedStructure.SyntheticClass_1.b[enumblockmirror.ordinal()]) {
        case 1:
            k = -k;
            break;

        case 2:
            i = -i;
            break;

        default:
            flag = false;
        }

        switch (DefinedStructure.SyntheticClass_1.a[enumblockrotation.ordinal()]) {
        case 1:
            return new BlockPosition(k, j, -i);

        case 2:
            return new BlockPosition(-k, j, i);

        case 3:
            return new BlockPosition(-i, j, -k);

        default:
            return flag ? new BlockPosition(i, j, k) : blockposition;
        }
    }

    private static Vec3D a(Vec3D vec3d, EnumBlockMirror enumblockmirror, EnumBlockRotation enumblockrotation) {
        double d0 = vec3d.x;
        double d1 = vec3d.y;
        double d2 = vec3d.z;
        boolean flag = true;

        switch (DefinedStructure.SyntheticClass_1.b[enumblockmirror.ordinal()]) {
        case 1:
            d2 = 1.0D - d2;
            break;

        case 2:
            d0 = 1.0D - d0;
            break;

        default:
            flag = false;
        }

        switch (DefinedStructure.SyntheticClass_1.a[enumblockrotation.ordinal()]) {
        case 1:
            return new Vec3D(d2, d1, 1.0D - d0);

        case 2:
            return new Vec3D(1.0D - d2, d1, d0);

        case 3:
            return new Vec3D(1.0D - d0, d1, 1.0D - d2);

        default:
            return flag ? new Vec3D(d0, d1, d2) : vec3d;
        }
    }

    public NBTTagCompound a(NBTTagCompound nbttagcompound) {
        NBTTagList nbttaglist = new NBTTagList();

        NBTTagCompound nbttagcompound1;

        for (Iterator iterator = this.a.iterator(); iterator.hasNext(); nbttaglist.add(nbttagcompound1)) {
            DefinedStructure.BlockInfo definedstructure_blockinfo = (DefinedStructure.BlockInfo) iterator.next();

            nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.set("pos", this.a(new int[] { definedstructure_blockinfo.a.getX(), definedstructure_blockinfo.a.getY(), definedstructure_blockinfo.a.getZ()}));
            nbttagcompound1.setInt("state", Block.getCombinedId(definedstructure_blockinfo.b));
            if (definedstructure_blockinfo.c != null) {
                nbttagcompound1.set("nbt", definedstructure_blockinfo.c);
            }
        }

        NBTTagList nbttaglist1 = new NBTTagList();

        NBTTagCompound nbttagcompound2;

        for (Iterator iterator1 = this.b.iterator(); iterator1.hasNext(); nbttaglist1.add(nbttagcompound2)) {
            DefinedStructure.EntityInfo definedstructure_entityinfo = (DefinedStructure.EntityInfo) iterator1.next();

            nbttagcompound2 = new NBTTagCompound();
            nbttagcompound2.set("pos", this.a(new double[] { definedstructure_entityinfo.a.x, definedstructure_entityinfo.a.y, definedstructure_entityinfo.a.z}));
            nbttagcompound2.set("blockPos", this.a(new int[] { definedstructure_entityinfo.b.getX(), definedstructure_entityinfo.b.getY(), definedstructure_entityinfo.b.getZ()}));
            if (definedstructure_entityinfo.c != null) {
                nbttagcompound2.set("nbt", definedstructure_entityinfo.c);
            }
        }

        nbttagcompound.set("blocks", nbttaglist);
        nbttagcompound.set("entities", nbttaglist1);
        nbttagcompound.set("size", this.a(new int[] { this.c.getX(), this.c.getY(), this.c.getZ()}));
        nbttagcompound.setInt("version", 1);
        nbttagcompound.setString("author", this.d);
        return nbttagcompound;
    }

    public void b(NBTTagCompound nbttagcompound) {
        this.a.clear();
        this.b.clear();
        NBTTagList nbttaglist = nbttagcompound.getList("size", 3);

        this.c = new BlockPosition(nbttaglist.c(0), nbttaglist.c(1), nbttaglist.c(2));
        this.d = nbttagcompound.getString("author");
        NBTTagList nbttaglist1 = nbttagcompound.getList("blocks", 10);

        for (int i = 0; i < nbttaglist1.size(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist1.get(i);
            NBTTagList nbttaglist2 = nbttagcompound1.getList("pos", 3);
            BlockPosition blockposition = new BlockPosition(nbttaglist2.c(0), nbttaglist2.c(1), nbttaglist2.c(2));
            int j = nbttagcompound1.getInt("state");
            IBlockData iblockdata = Block.getByCombinedId(j);
            NBTTagCompound nbttagcompound2;

            if (nbttagcompound1.hasKey("nbt")) {
                nbttagcompound2 = nbttagcompound1.getCompound("nbt");
            } else {
                nbttagcompound2 = null;
            }

            this.a.add(new DefinedStructure.BlockInfo(blockposition, iblockdata, nbttagcompound2, null));
        }

        NBTTagList nbttaglist3 = nbttagcompound.getList("entities", 10);

        for (int k = 0; k < nbttaglist3.size(); ++k) {
            NBTTagCompound nbttagcompound3 = nbttaglist3.get(k);
            NBTTagList nbttaglist4 = nbttagcompound3.getList("pos", 6);
            Vec3D vec3d = new Vec3D(nbttaglist4.e(0), nbttaglist4.e(1), nbttaglist4.e(2));
            NBTTagList nbttaglist5 = nbttagcompound3.getList("blockPos", 3);
            BlockPosition blockposition1 = new BlockPosition(nbttaglist5.c(0), nbttaglist5.c(1), nbttaglist5.c(2));

            if (nbttagcompound3.hasKey("nbt")) {
                NBTTagCompound nbttagcompound4 = nbttagcompound3.getCompound("nbt");

                this.b.add(new DefinedStructure.EntityInfo(vec3d, blockposition1, nbttagcompound4, null));
            }
        }

    }

    private NBTTagList a(int... aint) {
        NBTTagList nbttaglist = new NBTTagList();
        int[] aint1 = aint;
        int i = aint.length;

        for (int j = 0; j < i; ++j) {
            int k = aint1[j];

            nbttaglist.add(new NBTTagInt(k));
        }

        return nbttaglist;
    }

    private NBTTagList a(double... adouble) {
        NBTTagList nbttaglist = new NBTTagList();
        double[] adouble1 = adouble;
        int i = adouble.length;

        for (int j = 0; j < i; ++j) {
            double d0 = adouble1[j];

            nbttaglist.add(new NBTTagDouble(d0));
        }

        return nbttaglist;
    }

    static class SyntheticClass_1 {

        static final int[] a;
        static final int[] b = new int[EnumBlockMirror.values().length];

        static {
            try {
                DefinedStructure.SyntheticClass_1.b[EnumBlockMirror.LEFT_RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                DefinedStructure.SyntheticClass_1.b[EnumBlockMirror.FRONT_BACK.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            a = new int[EnumBlockRotation.values().length];

            try {
                DefinedStructure.SyntheticClass_1.a[EnumBlockRotation.COUNTERCLOCKWISE_90.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                DefinedStructure.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_90.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                DefinedStructure.SyntheticClass_1.a[EnumBlockRotation.CLOCKWISE_180.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

        }
    }

    static class EntityInfo {

        public final Vec3D a;
        public final BlockPosition b;
        public final NBTTagCompound c;

        private EntityInfo(Vec3D vec3d, BlockPosition blockposition, NBTTagCompound nbttagcompound) {
            this.a = vec3d;
            this.b = blockposition;
            this.c = nbttagcompound;
        }

        EntityInfo(Vec3D vec3d, BlockPosition blockposition, NBTTagCompound nbttagcompound, Object object) {
            this(vec3d, blockposition, nbttagcompound);
        }
    }

    static class BlockInfo {

        public final BlockPosition a;
        public final IBlockData b;
        public final NBTTagCompound c;

        private BlockInfo(BlockPosition blockposition, IBlockData iblockdata, @Nullable NBTTagCompound nbttagcompound) {
            this.a = blockposition;
            this.b = iblockdata;
            this.c = nbttagcompound;
        }

        BlockInfo(BlockPosition blockposition, IBlockData iblockdata, NBTTagCompound nbttagcompound, Object object) {
            this(blockposition, iblockdata, nbttagcompound);
        }
    }
}
