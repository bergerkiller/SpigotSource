package net.minecraft.server;

public class CommandSaveOff extends CommandAbstract {

    public CommandSaveOff() {}

    public String getCommand() {
        return "save-off";
    }

    public String getUsage(ICommandListener icommandlistener) {
        return "commands.save-off.usage";
    }

    public void execute(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring) throws CommandException {
        boolean flag = false;

        for (int i = 0; i < minecraftserver.worldServer.length; ++i) {
            if (minecraftserver.worldServer[i] != null) {
                WorldServer worldserver = minecraftserver.worldServer[i];

                if (!worldserver.savingDisabled) {
                    worldserver.savingDisabled = true;
                    flag = true;
                }
            }
        }

        if (flag) {
            a(icommandlistener, (ICommand) this, "commands.save.disabled", new Object[0]);
        } else {
            throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
        }
    }
}
