package net.minecraft.server;

import com.google.common.collect.Maps;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class NBTTagCompound extends NBTBase {

    private Map<String, NBTBase> map = Maps.newHashMap();

    public NBTTagCompound() {}

    void write(DataOutput dataoutput) throws IOException {
        Iterator iterator = this.map.keySet().iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            NBTBase nbtbase = (NBTBase) this.map.get(s);

            a(s, nbtbase, dataoutput);
        }

        dataoutput.writeByte(0);
    }

    void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
        nbtreadlimiter.a(384L);
        if (i > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        } else {
            this.map.clear();

            byte b0;

            while ((b0 = a(datainput, nbtreadlimiter)) != 0) {
                String s = b(datainput, nbtreadlimiter);

                nbtreadlimiter.a((long) (224 + 16 * s.length()));
                NBTBase nbtbase = a(b0, s, datainput, i + 1, nbtreadlimiter);

                if (this.map.put(s, nbtbase) != null) {
                    nbtreadlimiter.a(288L);
                }
            }

        }
    }

    public Set<String> c() {
        return this.map.keySet();
    }

    public byte getTypeId() {
        return (byte) 10;
    }

    public int d() {
        return this.map.size();
    }

    public void set(String s, NBTBase nbtbase) {
        this.map.put(s, nbtbase);
    }

    public void setByte(String s, byte b0) {
        this.map.put(s, new NBTTagByte(b0));
    }

    public void setShort(String s, short short0) {
        this.map.put(s, new NBTTagShort(short0));
    }

    public void setInt(String s, int i) {
        this.map.put(s, new NBTTagInt(i));
    }

    public void setLong(String s, long i) {
        this.map.put(s, new NBTTagLong(i));
    }

    public void a(String s, UUID uuid) {
        this.setLong(s + "Most", uuid.getMostSignificantBits());
        this.setLong(s + "Least", uuid.getLeastSignificantBits());
    }

    @Nullable
    public UUID a(String s) {
        return new UUID(this.getLong(s + "Most"), this.getLong(s + "Least"));
    }

    public boolean b(String s) {
        return this.hasKeyOfType(s + "Most", 99) && this.hasKeyOfType(s + "Least", 99);
    }

    public void setFloat(String s, float f) {
        this.map.put(s, new NBTTagFloat(f));
    }

    public void setDouble(String s, double d0) {
        this.map.put(s, new NBTTagDouble(d0));
    }

    public void setString(String s, String s1) {
        this.map.put(s, new NBTTagString(s1));
    }

    public void setByteArray(String s, byte[] abyte) {
        this.map.put(s, new NBTTagByteArray(abyte));
    }

    public void setIntArray(String s, int[] aint) {
        this.map.put(s, new NBTTagIntArray(aint));
    }

    public void setBoolean(String s, boolean flag) {
        this.setByte(s, (byte) (flag ? 1 : 0));
    }

    public NBTBase get(String s) {
        return (NBTBase) this.map.get(s);
    }

    public byte d(String s) {
        NBTBase nbtbase = (NBTBase) this.map.get(s);

        return nbtbase == null ? 0 : nbtbase.getTypeId();
    }

    public boolean hasKey(String s) {
        return this.map.containsKey(s);
    }

    public boolean hasKeyOfType(String s, int i) {
        byte b0 = this.d(s);

        return b0 == i ? true : (i != 99 ? false : b0 == 1 || b0 == 2 || b0 == 3 || b0 == 4 || b0 == 5 || b0 == 6);
    }

    public byte getByte(String s) {
        try {
            if (this.hasKeyOfType(s, 99)) {
                return ((NBTBase.NBTNumber) this.map.get(s)).f();
            }
        } catch (ClassCastException classcastexception) {
            ;
        }

        return (byte) 0;
    }

    public short getShort(String s) {
        try {
            if (this.hasKeyOfType(s, 99)) {
                return ((NBTBase.NBTNumber) this.map.get(s)).e();
            }
        } catch (ClassCastException classcastexception) {
            ;
        }

        return (short) 0;
    }

    public int getInt(String s) {
        try {
            if (this.hasKeyOfType(s, 99)) {
                return ((NBTBase.NBTNumber) this.map.get(s)).d();
            }
        } catch (ClassCastException classcastexception) {
            ;
        }

        return 0;
    }

    public long getLong(String s) {
        try {
            if (this.hasKeyOfType(s, 99)) {
                return ((NBTBase.NBTNumber) this.map.get(s)).c();
            }
        } catch (ClassCastException classcastexception) {
            ;
        }

        return 0L;
    }

    public float getFloat(String s) {
        try {
            if (this.hasKeyOfType(s, 99)) {
                return ((NBTBase.NBTNumber) this.map.get(s)).h();
            }
        } catch (ClassCastException classcastexception) {
            ;
        }

        return 0.0F;
    }

    public double getDouble(String s) {
        try {
            if (this.hasKeyOfType(s, 99)) {
                return ((NBTBase.NBTNumber) this.map.get(s)).g();
            }
        } catch (ClassCastException classcastexception) {
            ;
        }

        return 0.0D;
    }

    public String getString(String s) {
        try {
            if (this.hasKeyOfType(s, 8)) {
                return ((NBTBase) this.map.get(s)).a_();
            }
        } catch (ClassCastException classcastexception) {
            ;
        }

        return "";
    }

    public byte[] getByteArray(String s) {
        try {
            if (this.hasKeyOfType(s, 7)) {
                return ((NBTTagByteArray) this.map.get(s)).c();
            }
        } catch (ClassCastException classcastexception) {
            throw new ReportedException(this.a(s, 7, classcastexception));
        }

        return new byte[0];
    }

    public int[] getIntArray(String s) {
        try {
            if (this.hasKeyOfType(s, 11)) {
                return ((NBTTagIntArray) this.map.get(s)).c();
            }
        } catch (ClassCastException classcastexception) {
            throw new ReportedException(this.a(s, 11, classcastexception));
        }

        return new int[0];
    }

    public NBTTagCompound getCompound(String s) {
        try {
            if (this.hasKeyOfType(s, 10)) {
                return (NBTTagCompound) this.map.get(s);
            }
        } catch (ClassCastException classcastexception) {
            throw new ReportedException(this.a(s, 10, classcastexception));
        }

        return new NBTTagCompound();
    }

    public NBTTagList getList(String s, int i) {
        try {
            if (this.d(s) == 9) {
                NBTTagList nbttaglist = (NBTTagList) this.map.get(s);

                if (!nbttaglist.isEmpty() && nbttaglist.d() != i) {
                    return new NBTTagList();
                }

                return nbttaglist;
            }
        } catch (ClassCastException classcastexception) {
            throw new ReportedException(this.a(s, 9, classcastexception));
        }

        return new NBTTagList();
    }

    public boolean getBoolean(String s) {
        return this.getByte(s) != 0;
    }

    public void remove(String s) {
        this.map.remove(s);
    }

    public String toString() {
        StringBuilder stringbuilder = new StringBuilder("{");

        Entry entry;

        for (Iterator iterator = this.map.entrySet().iterator(); iterator.hasNext(); stringbuilder.append((String) entry.getKey()).append(':').append(entry.getValue())) {
            entry = (Entry) iterator.next();
            if (stringbuilder.length() != 1) {
                stringbuilder.append(',');
            }
        }

        return stringbuilder.append('}').toString();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    private CrashReport a(final String s, final int i, ClassCastException classcastexception) {
        CrashReport crashreport = CrashReport.a(classcastexception, "Reading NBT data");
        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Corrupt NBT tag", 1);

        crashreportsystemdetails.a("Tag type found", new CrashReportCallable() {
            public String a() throws Exception {
                return NBTBase.a[((NBTBase) NBTTagCompound.this.map.get(s)).getTypeId()];
            }

            public Object call() throws Exception {
                return this.a();
            }
        });
        crashreportsystemdetails.a("Tag type expected", new CrashReportCallable() {
            public String a() throws Exception {
                return NBTBase.a[i];
            }

            public Object call() throws Exception {
                return this.a();
            }
        });
        crashreportsystemdetails.a("Tag name", (Object) s);
        return crashreport;
    }

    public NBTBase clone() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        Iterator iterator = this.map.keySet().iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();

            nbttagcompound.set(s, ((NBTBase) this.map.get(s)).clone());
        }

        return nbttagcompound;
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagCompound nbttagcompound = (NBTTagCompound) object;

            return this.map.entrySet().equals(nbttagcompound.map.entrySet());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.map.hashCode();
    }

    private static void a(String s, NBTBase nbtbase, DataOutput dataoutput) throws IOException {
        dataoutput.writeByte(nbtbase.getTypeId());
        if (nbtbase.getTypeId() != 0) {
            dataoutput.writeUTF(s);
            nbtbase.write(dataoutput);
        }
    }

    private static byte a(DataInput datainput, NBTReadLimiter nbtreadlimiter) throws IOException {
        return datainput.readByte();
    }

    private static String b(DataInput datainput, NBTReadLimiter nbtreadlimiter) throws IOException {
        return datainput.readUTF();
    }

    static NBTBase a(byte b0, String s, DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
        NBTBase nbtbase = NBTBase.createTag(b0);

        try {
            nbtbase.load(datainput, i, nbtreadlimiter);
            return nbtbase;
        } catch (IOException ioexception) {
            CrashReport crashreport = CrashReport.a(ioexception, "Loading NBT data");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("NBT Tag");

            crashreportsystemdetails.a("Tag name", (Object) s);
            crashreportsystemdetails.a("Tag type", (Object) Byte.valueOf(b0));
            throw new ReportedException(crashreport);
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        Iterator iterator = nbttagcompound.map.keySet().iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            NBTBase nbtbase = (NBTBase) nbttagcompound.map.get(s);

            if (nbtbase.getTypeId() == 10) {
                if (this.hasKeyOfType(s, 10)) {
                    NBTTagCompound nbttagcompound1 = this.getCompound(s);

                    nbttagcompound1.a((NBTTagCompound) nbtbase);
                } else {
                    this.set(s, nbtbase.clone());
                }
            } else {
                this.set(s, nbtbase.clone());
            }
        }

    }
}
