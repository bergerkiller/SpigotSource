package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public class CommandOp extends CommandAbstract {

    public CommandOp() {}

    public String getCommand() {
        return "op";
    }

    public int a() {
        return 3;
    }

    public String getUsage(ICommandListener icommandlistener) {
        return "commands.op.usage";
    }

    public void execute(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring) throws CommandException {
        if (astring.length == 1 && astring[0].length() > 0) {
            GameProfile gameprofile = minecraftserver.getUserCache().getProfile(astring[0]);

            if (gameprofile == null) {
                throw new CommandException("commands.op.failed", new Object[] { astring[0]});
            } else {
                minecraftserver.getPlayerList().addOp(gameprofile);
                a(icommandlistener, (ICommand) this, "commands.op.success", new Object[] { astring[0]});
            }
        } else {
            throw new ExceptionUsage("commands.op.usage", new Object[0]);
        }
    }

    public List<String> tabComplete(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring, @Nullable BlockPosition blockposition) {
        if (astring.length == 1) {
            String s = astring[astring.length - 1];
            ArrayList arraylist = Lists.newArrayList();
            GameProfile[] agameprofile = minecraftserver.K();
            int i = agameprofile.length;

            for (int j = 0; j < i; ++j) {
                GameProfile gameprofile = agameprofile[j];

                if (!minecraftserver.getPlayerList().isOp(gameprofile) && a(s, gameprofile.getName())) {
                    arraylist.add(gameprofile.getName());
                }
            }

            return arraylist;
        } else {
            return Collections.emptyList();
        }
    }
}
