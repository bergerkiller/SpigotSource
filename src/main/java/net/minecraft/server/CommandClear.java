package net.minecraft.server;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public class CommandClear extends CommandAbstract {

    public CommandClear() {}

    public String getCommand() {
        return "clear";
    }

    public String getUsage(ICommandListener icommandlistener) {
        return "commands.clear.usage";
    }

    public int a() {
        return 2;
    }

    public void execute(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring) throws CommandException {
        EntityPlayer entityplayer = astring.length == 0 ? a(icommandlistener) : a(minecraftserver, icommandlistener, astring[0]);
        Item item = astring.length >= 2 ? a(icommandlistener, astring[1]) : null;
        int i = astring.length >= 3 ? a(astring[2], -1) : -1;
        int j = astring.length >= 4 ? a(astring[3], -1) : -1;
        NBTTagCompound nbttagcompound = null;

        if (astring.length >= 5) {
            try {
                nbttagcompound = MojangsonParser.parse(a(astring, 4));
            } catch (MojangsonParseException mojangsonparseexception) {
                throw new CommandException("commands.clear.tagError", new Object[] { mojangsonparseexception.getMessage()});
            }
        }

        if (astring.length >= 2 && item == null) {
            throw new CommandException("commands.clear.failure", new Object[] { entityplayer.getName()});
        } else {
            int k = entityplayer.inventory.a(item, i, j, nbttagcompound);

            entityplayer.defaultContainer.b();
            if (!entityplayer.abilities.canInstantlyBuild) {
                entityplayer.broadcastCarriedItem();
            }

            icommandlistener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, k);
            if (k == 0) {
                throw new CommandException("commands.clear.failure", new Object[] { entityplayer.getName()});
            } else {
                if (j == 0) {
                    icommandlistener.sendMessage(new ChatMessage("commands.clear.testing", new Object[] { entityplayer.getName(), Integer.valueOf(k)}));
                } else {
                    a(icommandlistener, (ICommand) this, "commands.clear.success", new Object[] { entityplayer.getName(), Integer.valueOf(k)});
                }

            }
        }
    }

    public List<String> tabComplete(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring, @Nullable BlockPosition blockposition) {
        return astring.length == 1 ? a(astring, minecraftserver.getPlayers()) : (astring.length == 2 ? a(astring, (Collection) Item.REGISTRY.keySet()) : Collections.emptyList());
    }

    public boolean isListStart(String[] astring, int i) {
        return i == 0;
    }
}
