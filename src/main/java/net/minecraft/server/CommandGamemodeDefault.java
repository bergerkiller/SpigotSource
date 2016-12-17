package net.minecraft.server;

import java.util.Iterator;

public class CommandGamemodeDefault extends CommandGamemode {

    public CommandGamemodeDefault() {}

    public String getCommand() {
        return "defaultgamemode";
    }

    public String getUsage(ICommandListener icommandlistener) {
        return "commands.defaultgamemode.usage";
    }

    public void execute(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring) throws CommandException {
        if (astring.length <= 0) {
            throw new ExceptionUsage("commands.defaultgamemode.usage", new Object[0]);
        } else {
            WorldSettings.EnumGamemode worldsettings_enumgamemode = this.c(icommandlistener, astring[0]);

            this.a(worldsettings_enumgamemode, minecraftserver);
            a(icommandlistener, (ICommand) this, "commands.defaultgamemode.success", new Object[] { new ChatMessage("gameMode." + worldsettings_enumgamemode.b(), new Object[0])});
        }
    }

    protected void a(WorldSettings.EnumGamemode worldsettings_enumgamemode, MinecraftServer minecraftserver) {
        minecraftserver.setGamemode(worldsettings_enumgamemode);
        if (minecraftserver.getForceGamemode()) {
            Iterator iterator = minecraftserver.getPlayerList().v().iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                entityplayer.a(worldsettings_enumgamemode);
            }
        }

    }
}
