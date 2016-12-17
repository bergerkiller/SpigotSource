package net.minecraft.server;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public class CommandPlaySound extends CommandAbstract {

    public CommandPlaySound() {}

    public String getCommand() {
        return "playsound";
    }

    public int a() {
        return 2;
    }

    public String getUsage(ICommandListener icommandlistener) {
        return "commands.playsound.usage";
    }

    public void execute(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring) throws CommandException {
        if (astring.length < 2) {
            throw new ExceptionUsage(this.getUsage(icommandlistener), new Object[0]);
        } else {
            byte b0 = 0;
            int i = b0 + 1;
            String s = astring[b0];
            String s1 = astring[i++];
            SoundCategory soundcategory = SoundCategory.a(s1);

            if (soundcategory == null) {
                throw new CommandException("commands.playsound.unknownSoundSource", new Object[] { s1});
            } else {
                EntityPlayer entityplayer = a(minecraftserver, icommandlistener, astring[i++]);
                Vec3D vec3d = icommandlistener.d();
                double d0 = vec3d.x;

                if (astring.length > i) {
                    d0 = b(d0, astring[i++], true);
                }

                double d1 = vec3d.y;

                if (astring.length > i) {
                    d1 = b(d1, astring[i++], 0, 0, false);
                }

                double d2 = vec3d.z;

                if (astring.length > i) {
                    d2 = b(d2, astring[i++], true);
                }

                double d3 = 1.0D;

                if (astring.length > i) {
                    d3 = a(astring[i++], 0.0D, 3.4028234663852886E38D);
                }

                double d4 = 1.0D;

                if (astring.length > i) {
                    d4 = a(astring[i++], 0.0D, 2.0D);
                }

                double d5 = 0.0D;

                if (astring.length > i) {
                    d5 = a(astring[i], 0.0D, 1.0D);
                }

                double d6 = d3 > 1.0D ? d3 * 16.0D : 16.0D;
                double d7 = entityplayer.f(d0, d1, d2);

                if (d7 > d6) {
                    if (d5 <= 0.0D) {
                        throw new CommandException("commands.playsound.playerTooFar", new Object[] { entityplayer.getName()});
                    }

                    double d8 = d0 - entityplayer.locX;
                    double d9 = d1 - entityplayer.locY;
                    double d10 = d2 - entityplayer.locZ;
                    double d11 = Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);

                    if (d11 > 0.0D) {
                        d0 = entityplayer.locX + d8 / d11 * 2.0D;
                        d1 = entityplayer.locY + d9 / d11 * 2.0D;
                        d2 = entityplayer.locZ + d10 / d11 * 2.0D;
                    }

                    d3 = d5;
                }

                entityplayer.playerConnection.sendPacket(new PacketPlayOutCustomSoundEffect(s, soundcategory, d0, d1, d2, (float) d3, (float) d4));
                a(icommandlistener, (ICommand) this, "commands.playsound.success", new Object[] { s, entityplayer.getName()});
            }
        }
    }

    public List<String> tabComplete(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring, @Nullable BlockPosition blockposition) {
        return astring.length == 1 ? a(astring, (Collection) SoundEffect.a.keySet()) : (astring.length == 2 ? a(astring, (Collection) SoundCategory.b()) : (astring.length == 3 ? a(astring, minecraftserver.getPlayers()) : (astring.length > 3 && astring.length <= 6 ? a(astring, 2, blockposition) : Collections.emptyList())));
    }

    public boolean isListStart(String[] astring, int i) {
        return i == 2;
    }
}
