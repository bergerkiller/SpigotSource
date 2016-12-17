package net.minecraft.server;

public class CommandSaveOn extends CommandAbstract {

    public CommandSaveOn() {}

    public String getCommand() {
        return "save-on";
    }

    public String getUsage(ICommandListener icommandlistener) {
        return "commands.save-on.usage";
    }

    public void execute(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring) throws CommandException {
        boolean flag = false;

        for (int i = 0; i < minecraftserver.worldServer.length; ++i) {
            if (minecraftserver.worldServer[i] != null) {
                WorldServer worldserver = minecraftserver.worldServer[i];

                if (worldserver.savingDisabled) {
                    worldserver.savingDisabled = false;
                    flag = true;
                }
            }
        }

        if (flag) {
            a(icommandlistener, (ICommand) this, "commands.save.enabled", new Object[0]);
        } else {
            throw new CommandException("commands.save-on.alreadyOn", new Object[0]);
        }
    }
}
