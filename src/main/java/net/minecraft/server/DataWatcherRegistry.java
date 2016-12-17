package net.minecraft.server;

import com.google.common.base.Optional;
import java.util.UUID;
import javax.annotation.Nullable;

public class DataWatcherRegistry {

    private static final RegistryID<DataWatcherSerializer<?>> n = new RegistryID(16);
    public static final DataWatcherSerializer<Byte> a = new DataWatcherSerializer() {
        public void a(PacketDataSerializer packetdataserializer, Byte obyte) {
            packetdataserializer.writeByte(obyte.byteValue());
        }

        public Byte b(PacketDataSerializer packetdataserializer) {
            return Byte.valueOf(packetdataserializer.readByte());
        }

        public DataWatcherObject<Byte> a(int i) {
            return new DataWatcherObject(i, this);
        }

        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Integer> b = new DataWatcherSerializer() {
        public void a(PacketDataSerializer packetdataserializer, Integer integer) {
            packetdataserializer.d(integer.intValue());
        }

        public Integer b(PacketDataSerializer packetdataserializer) {
            return Integer.valueOf(packetdataserializer.g());
        }

        public DataWatcherObject<Integer> a(int i) {
            return new DataWatcherObject(i, this);
        }

        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Float> c = new DataWatcherSerializer() {
        public void a(PacketDataSerializer packetdataserializer, Float float) {
            packetdataserializer.writeFloat(float.floatValue());
        }

        public Float b(PacketDataSerializer packetdataserializer) {
            return Float.valueOf(packetdataserializer.readFloat());
        }

        public DataWatcherObject<Float> a(int i) {
            return new DataWatcherObject(i, this);
        }

        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<String> d = new DataWatcherSerializer() {
        public void a(PacketDataSerializer packetdataserializer, String s) {
            packetdataserializer.a(s);
        }

        public String b(PacketDataSerializer packetdataserializer) {
            return packetdataserializer.e(32767);
        }

        public DataWatcherObject<String> a(int i) {
            return new DataWatcherObject(i, this);
        }

        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<IChatBaseComponent> e = new DataWatcherSerializer() {
        public void a(PacketDataSerializer packetdataserializer, IChatBaseComponent ichatbasecomponent) {
            packetdataserializer.a(ichatbasecomponent);
        }

        public IChatBaseComponent b(PacketDataSerializer packetdataserializer) {
            return packetdataserializer.f();
        }

        public DataWatcherObject<IChatBaseComponent> a(int i) {
            return new DataWatcherObject(i, this);
        }

        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Optional<ItemStack>> f = new DataWatcherSerializer() {
        public void a(PacketDataSerializer packetdataserializer, Optional<ItemStack> optional) {
            packetdataserializer.a((ItemStack) optional.orNull());
        }

        public Optional<ItemStack> b(PacketDataSerializer packetdataserializer) {
            return Optional.fromNullable(packetdataserializer.k());
        }

        public DataWatcherObject<Optional<ItemStack>> a(int i) {
            return new DataWatcherObject(i, this);
        }

        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Optional<IBlockData>> g = new DataWatcherSerializer() {
        public void a(PacketDataSerializer packetdataserializer, Optional<IBlockData> optional) {
            if (optional.isPresent()) {
                packetdataserializer.d(Block.getCombinedId((IBlockData) optional.get()));
            } else {
                packetdataserializer.d(0);
            }

        }

        public Optional<IBlockData> b(PacketDataSerializer packetdataserializer) {
            int i = packetdataserializer.g();

            return i == 0 ? Optional.absent() : Optional.of(Block.getByCombinedId(i));
        }

        public DataWatcherObject<Optional<IBlockData>> a(int i) {
            return new DataWatcherObject(i, this);
        }

        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Boolean> h = new DataWatcherSerializer() {
        public void a(PacketDataSerializer packetdataserializer, Boolean obool) {
            packetdataserializer.writeBoolean(obool.booleanValue());
        }

        public Boolean b(PacketDataSerializer packetdataserializer) {
            return Boolean.valueOf(packetdataserializer.readBoolean());
        }

        public DataWatcherObject<Boolean> a(int i) {
            return new DataWatcherObject(i, this);
        }

        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Vector3f> i = new DataWatcherSerializer() {
        public void a(PacketDataSerializer packetdataserializer, Vector3f vector3f) {
            packetdataserializer.writeFloat(vector3f.getX());
            packetdataserializer.writeFloat(vector3f.getY());
            packetdataserializer.writeFloat(vector3f.getZ());
        }

        public Vector3f b(PacketDataSerializer packetdataserializer) {
            return new Vector3f(packetdataserializer.readFloat(), packetdataserializer.readFloat(), packetdataserializer.readFloat());
        }

        public DataWatcherObject<Vector3f> a(int i) {
            return new DataWatcherObject(i, this);
        }

        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<BlockPosition> j = new DataWatcherSerializer() {
        public void a(PacketDataSerializer packetdataserializer, BlockPosition blockposition) {
            packetdataserializer.a(blockposition);
        }

        public BlockPosition b(PacketDataSerializer packetdataserializer) {
            return packetdataserializer.e();
        }

        public DataWatcherObject<BlockPosition> a(int i) {
            return new DataWatcherObject(i, this);
        }

        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Optional<BlockPosition>> k = new DataWatcherSerializer() {
        public void a(PacketDataSerializer packetdataserializer, Optional<BlockPosition> optional) {
            packetdataserializer.writeBoolean(optional.isPresent());
            if (optional.isPresent()) {
                packetdataserializer.a((BlockPosition) optional.get());
            }

        }

        public Optional<BlockPosition> b(PacketDataSerializer packetdataserializer) {
            return !packetdataserializer.readBoolean() ? Optional.absent() : Optional.of(packetdataserializer.e());
        }

        public DataWatcherObject<Optional<BlockPosition>> a(int i) {
            return new DataWatcherObject(i, this);
        }

        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<EnumDirection> l = new DataWatcherSerializer() {
        public void a(PacketDataSerializer packetdataserializer, EnumDirection enumdirection) {
            packetdataserializer.a((Enum) enumdirection);
        }

        public EnumDirection b(PacketDataSerializer packetdataserializer) {
            return (EnumDirection) packetdataserializer.a(EnumDirection.class);
        }

        public DataWatcherObject<EnumDirection> a(int i) {
            return new DataWatcherObject(i, this);
        }

        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };
    public static final DataWatcherSerializer<Optional<UUID>> m = new DataWatcherSerializer() {
        public void a(PacketDataSerializer packetdataserializer, Optional<UUID> optional) {
            packetdataserializer.writeBoolean(optional.isPresent());
            if (optional.isPresent()) {
                packetdataserializer.a((UUID) optional.get());
            }

        }

        public Optional<UUID> b(PacketDataSerializer packetdataserializer) {
            return !packetdataserializer.readBoolean() ? Optional.absent() : Optional.of(packetdataserializer.i());
        }

        public DataWatcherObject<Optional<UUID>> a(int i) {
            return new DataWatcherObject(i, this);
        }

        public Object a(PacketDataSerializer packetdataserializer) {
            return this.b(packetdataserializer);
        }
    };

    public static void a(DataWatcherSerializer<?> datawatcherserializer) {
        DataWatcherRegistry.n.c(datawatcherserializer);
    }

    @Nullable
    public static DataWatcherSerializer<?> a(int i) {
        return (DataWatcherSerializer) DataWatcherRegistry.n.fromId(i);
    }

    public static int b(DataWatcherSerializer<?> datawatcherserializer) {
        return DataWatcherRegistry.n.getId(datawatcherserializer);
    }

    static {
        a(DataWatcherRegistry.a);
        a(DataWatcherRegistry.b);
        a(DataWatcherRegistry.c);
        a(DataWatcherRegistry.d);
        a(DataWatcherRegistry.e);
        a(DataWatcherRegistry.f);
        a(DataWatcherRegistry.h);
        a(DataWatcherRegistry.i);
        a(DataWatcherRegistry.j);
        a(DataWatcherRegistry.k);
        a(DataWatcherRegistry.l);
        a(DataWatcherRegistry.m);
        a(DataWatcherRegistry.g);
    }
}
