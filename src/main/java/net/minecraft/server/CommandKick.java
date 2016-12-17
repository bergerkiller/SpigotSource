package net.minecraft.server;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public class CommandKick extends CommandAbstract {

    public CommandKick() {}

    public String getCommand() {
        return "kick";
    }

    public int a() {
        return 3;
    }

    public String getUsage(ICommandListener icommandlistener) {
        return "commands.kick.usage";
    }

    public void execute(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring) throws CommandException {
        if (astring.length > 0 && astring[0].length() > 1) {
            EntityPlayer entityplayer = minecraftserver.getPlayerList().getPlayer(astring[0]);
            String s = "Kicked by an operator.";
            boolean flag = false;

            if (entityplayer == null) {
                throw new ExceptionPlayerNotFound();
            } else {
                if (astring.length >= 2) {
                    s = a(icommandlistener, astring, 1).toPlainText();
                    flag = true;
                }

                entityplayer.playerConnection.disconnect(s);
                if (flag) {
                    a(icommandlistener, (ICommand) this, "commands.kick.success.reason", new Object[] { entityplayer.getName(), s});
                } else {
                    a(icommandlistener, (ICommand) this, "commands.kick.success", new Object[] { entityplayer.getName()});
                }

            }
        } else {
            throw new ExceptionUsage("commands.kick.usage", new Object[0]);
        }
    }

    public List<String> tabComplete(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring, @Nullable BlockPosition blockposition) {
        return astring.length >= 1 ? a(astring, minecraftserver.getPlayers()) : Collections.emptyList();
    }
}
