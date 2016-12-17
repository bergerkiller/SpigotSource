package net.minecraft.server;

import com.google.gson.JsonParseException;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandTitle extends CommandAbstract {

    private static final Logger a = LogManager.getLogger();

    public CommandTitle() {}

    public String getCommand() {
        return "title";
    }

    public int a() {
        return 2;
    }

    public String getUsage(ICommandListener icommandlistener) {
        return "commands.title.usage";
    }

    public void execute(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring) throws CommandException {
        if (astring.length < 2) {
            throw new ExceptionUsage("commands.title.usage", new Object[0]);
        } else {
            if (astring.length < 3) {
                if ("title".equals(astring[1]) || "subtitle".equals(astring[1])) {
                    throw new ExceptionUsage("commands.title.usage.title", new Object[0]);
                }

                if ("times".equals(astring[1])) {
                    throw new ExceptionUsage("commands.title.usage.times", new Object[0]);
                }
            }

            EntityPlayer entityplayer = a(minecraftserver, icommandlistener, astring[0]);
            PacketPlayOutTitle.EnumTitleAction packetplayouttitle_enumtitleaction = PacketPlayOutTitle.EnumTitleAction.a(astring[1]);

            if (packetplayouttitle_enumtitleaction != PacketPlayOutTitle.EnumTitleAction.CLEAR && packetplayouttitle_enumtitleaction != PacketPlayOutTitle.EnumTitleAction.RESET) {
                if (packetplayouttitle_enumtitleaction == PacketPlayOutTitle.EnumTitleAction.TIMES) {
                    if (astring.length != 5) {
                        throw new ExceptionUsage("commands.title.usage", new Object[0]);
                    } else {
                        int i = a(astring[2]);
                        int j = a(astring[3]);
                        int k = a(astring[4]);
                        PacketPlayOutTitle packetplayouttitle = new PacketPlayOutTitle(i, j, k);

                        entityplayer.playerConnection.sendPacket(packetplayouttitle);
                        a(icommandlistener, (ICommand) this, "commands.title.success", new Object[0]);
                    }
                } else if (astring.length < 3) {
                    throw new ExceptionUsage("commands.title.usage", new Object[0]);
                } else {
                    String s = a(astring, 2);

                    IChatBaseComponent ichatbasecomponent;

                    try {
                        ichatbasecomponent = IChatBaseComponent.ChatSerializer.a(s);
                    } catch (JsonParseException jsonparseexception) {
                        throw a(jsonparseexception);
                    }

                    PacketPlayOutTitle packetplayouttitle1 = new PacketPlayOutTitle(packetplayouttitle_enumtitleaction, ChatComponentUtils.filterForDisplay(icommandlistener, ichatbasecomponent, entityplayer));

                    entityplayer.playerConnection.sendPacket(packetplayouttitle1);
                    a(icommandlistener, (ICommand) this, "commands.title.success", new Object[0]);
                }
            } else if (astring.length != 2) {
                throw new ExceptionUsage("commands.title.usage", new Object[0]);
            } else {
                PacketPlayOutTitle packetplayouttitle2 = new PacketPlayOutTitle(packetplayouttitle_enumtitleaction, (IChatBaseComponent) null);

                entityplayer.playerConnection.sendPacket(packetplayouttitle2);
                a(icommandlistener, (ICommand) this, "commands.title.success", new Object[0]);
            }
        }
    }

    public List<String> tabComplete(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring, @Nullable BlockPosition blockposition) {
        return astring.length == 1 ? a(astring, minecraftserver.getPlayers()) : (astring.length == 2 ? a(astring, PacketPlayOutTitle.EnumTitleAction.a()) : Collections.emptyList());
    }

    public boolean isListStart(String[] astring, int i) {
        return i == 0;
    }
}
