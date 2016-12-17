package net.minecraft.server;

public class DataInspectorItem extends DataInspectorTagged {

    private final String[] a;

    public DataInspectorItem(String s, String... astring) {
        super("id", s);
        this.a = astring;
    }

    NBTTagCompound b(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i) {
        int j = 0;

        for (int k = this.a.length; j < k; ++j) {
            nbttagcompound = DataConverterRegistry.a(dataconverter, nbttagcompound, i, this.a[j]);
        }

        return nbttagcompound;
    }
}
