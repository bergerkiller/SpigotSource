package net.minecraft.server;

public abstract class DataInspectorTagged implements DataInspector {

    private final String a;
    private final String b;

    public DataInspectorTagged(String s, String s1) {
        this.a = s;
        this.b = s1;
    }

    public NBTTagCompound a(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i) {
        if (nbttagcompound.getString(this.a).equals(this.b)) {
            nbttagcompound = this.b(dataconverter, nbttagcompound, i);
        }

        return nbttagcompound;
    }

    abstract NBTTagCompound b(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i);
}
