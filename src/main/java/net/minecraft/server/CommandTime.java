package net.minecraft.server;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public class CommandTime extends CommandAbstract {

    public CommandTime() {}

    public String getCommand() {
        return "time";
    }

    public int a() {
        return 2;
    }

    public String getUsage(ICommandListener icommandlistener) {
        return "commands.time.usage";
    }

    public void execute(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring) throws CommandException {
        if (astring.length > 1) {
            int i;

            if (astring[0].equals("set")) {
                if (astring[1].equals("day")) {
                    i = 1000;
                } else if (astring[1].equals("night")) {
                    i = 13000;
                } else {
                    i = a(astring[1], 0);
                }

                this.a(minecraftserver, i);
                a(icommandlistener, (ICommand) this, "commands.time.set", new Object[] { Integer.valueOf(i)});
                return;
            }

            if (astring[0].equals("add")) {
                i = a(astring[1], 0);
                this.b(minecraftserver, i);
                a(icommandlistener, (ICommand) this, "commands.time.added", new Object[] { Integer.valueOf(i)});
                return;
            }

            if (astring[0].equals("query")) {
                if (astring[1].equals("daytime")) {
                    i = (int) (icommandlistener.getWorld().getDayTime() % 24000L);
                    icommandlistener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, i);
                    a(icommandlistener, (ICommand) this, "commands.time.query", new Object[] { Integer.valueOf(i)});
                    return;
                }

                if (astring[1].equals("day")) {
                    i = (int) (icommandlistener.getWorld().getDayTime() / 24000L % 2147483647L);
                    icommandlistener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, i);
                    a(icommandlistener, (ICommand) this, "commands.time.query", new Object[] { Integer.valueOf(i)});
                    return;
                }

                if (astring[1].equals("gametime")) {
                    i = (int) (icommandlistener.getWorld().getTime() % 2147483647L);
                    icommandlistener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, i);
                    a(icommandlistener, (ICommand) this, "commands.time.query", new Object[] { Integer.valueOf(i)});
                    return;
                }
            }
        }

        throw new ExceptionUsage("commands.time.usage", new Object[0]);
    }

    public List<String> tabComplete(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring, @Nullable BlockPosition blockposition) {
        return astring.length == 1 ? a(astring, new String[] { "set", "add", "query"}) : (astring.length == 2 && astring[0].equals("set") ? a(astring, new String[] { "day", "night"}) : (astring.length == 2 && astring[0].equals("query") ? a(astring, new String[] { "daytime", "gametime", "day"}) : Collections.emptyList()));
    }

    protected void a(MinecraftServer minecraftserver, int i) {
        for (int j = 0; j < minecraftserver.worldServer.length; ++j) {
            minecraftserver.worldServer[j].setDayTime((long) i);
        }

    }

    protected void b(MinecraftServer minecraftserver, int i) {
        for (int j = 0; j < minecraftserver.worldServer.length; ++j) {
            WorldServer worldserver = minecraftserver.worldServer[j];

            worldserver.setDayTime(worldserver.getDayTime() + (long) i);
        }

    }
}
