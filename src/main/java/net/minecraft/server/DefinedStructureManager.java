package net.minecraft.server;

import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;

public class DefinedStructureManager {

    private final Map<String, DefinedStructure> a;
    private final String b;

    public DefinedStructureManager() {
        this("structures");
    }

    public DefinedStructureManager(String s) {
        this.a = Maps.newHashMap();
        this.b = s;
    }

    public DefinedStructure a(@Nullable MinecraftServer minecraftserver, MinecraftKey minecraftkey) {
        String s = minecraftkey.a();

        if (this.a.containsKey(s)) {
            return (DefinedStructure) this.a.get(s);
        } else {
            if (minecraftserver != null) {
                this.b(minecraftserver, minecraftkey);
            } else {
                this.a(minecraftkey);
            }

            if (this.a.containsKey(s)) {
                return (DefinedStructure) this.a.get(s);
            } else {
                DefinedStructure definedstructure = new DefinedStructure();

                this.a.put(s, definedstructure);
                return definedstructure;
            }
        }
    }

    public boolean b(MinecraftServer minecraftserver, MinecraftKey minecraftkey) {
        String s = minecraftkey.a();
        File file = minecraftserver.d(this.b);
        File file1 = new File(file, s + ".nbt");

        if (!file1.exists()) {
            return this.a(minecraftkey);
        } else {
            FileInputStream fileinputstream = null;

            boolean flag;

            try {
                fileinputstream = new FileInputStream(file1);
                this.a(s, (InputStream) fileinputstream);
                return true;
            } catch (Throwable throwable) {
                flag = false;
            } finally {
                IOUtils.closeQuietly(fileinputstream);
            }

            return flag;
        }
    }

    private boolean a(MinecraftKey minecraftkey) {
        String s = minecraftkey.b();
        String s1 = minecraftkey.a();
        InputStream inputstream = null;

        boolean flag;

        try {
            inputstream = MinecraftServer.class.getResourceAsStream("/assets/" + s + "/structures/" + s1 + ".nbt");
            this.a(s1, inputstream);
            return true;
        } catch (Throwable throwable) {
            flag = false;
        } finally {
            IOUtils.closeQuietly(inputstream);
        }

        return flag;
    }

    private void a(String s, InputStream inputstream) throws IOException {
        NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(inputstream);
        DefinedStructure definedstructure = new DefinedStructure();

        definedstructure.b(nbttagcompound);
        this.a.put(s, definedstructure);
    }

    public boolean c(MinecraftServer minecraftserver, MinecraftKey minecraftkey) {
        String s = minecraftkey.a();

        if (!this.a.containsKey(s)) {
            return false;
        } else {
            File file = minecraftserver.d(this.b);

            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return false;
                }
            } else if (!file.isDirectory()) {
                return false;
            }

            File file1 = new File(file, s + ".nbt");
            DefinedStructure definedstructure = (DefinedStructure) this.a.get(s);
            FileOutputStream fileoutputstream = null;

            boolean flag;

            try {
                NBTTagCompound nbttagcompound = definedstructure.a(new NBTTagCompound());

                fileoutputstream = new FileOutputStream(file1);
                NBTCompressedStreamTools.a(nbttagcompound, (OutputStream) fileoutputstream);
                return true;
            } catch (Throwable throwable) {
                flag = false;
            } finally {
                IOUtils.closeQuietly(fileoutputstream);
            }

            return flag;
        }
    }
}
