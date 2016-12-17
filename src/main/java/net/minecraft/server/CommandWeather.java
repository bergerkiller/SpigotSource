package net.minecraft.server;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class CommandWeather extends CommandAbstract {

    public CommandWeather() {}

    public String getCommand() {
        return "weather";
    }

    public int a() {
        return 2;
    }

    public String getUsage(ICommandListener icommandlistener) {
        return "commands.weather.usage";
    }

    public void execute(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring) throws CommandException {
        if (astring.length >= 1 && astring.length <= 2) {
            int i = (300 + (new Random()).nextInt(600)) * 20;

            if (astring.length >= 2) {
                i = a(astring[1], 1, 1000000) * 20;
            }

            WorldServer worldserver = minecraftserver.worldServer[0];
            WorldData worlddata = worldserver.getWorldData();

            if ("clear".equalsIgnoreCase(astring[0])) {
                worlddata.i(i);
                worlddata.setWeatherDuration(0);
                worlddata.setThunderDuration(0);
                worlddata.setStorm(false);
                worlddata.setThundering(false);
                a(icommandlistener, (ICommand) this, "commands.weather.clear", new Object[0]);
            } else if ("rain".equalsIgnoreCase(astring[0])) {
                worlddata.i(0);
                worlddata.setWeatherDuration(i);
                worlddata.setThunderDuration(i);
                worlddata.setStorm(true);
                worlddata.setThundering(false);
                a(icommandlistener, (ICommand) this, "commands.weather.rain", new Object[0]);
            } else {
                if (!"thunder".equalsIgnoreCase(astring[0])) {
                    throw new ExceptionUsage("commands.weather.usage", new Object[0]);
                }

                worlddata.i(0);
                worlddata.setWeatherDuration(i);
                worlddata.setThunderDuration(i);
                worlddata.setStorm(true);
                worlddata.setThundering(true);
                a(icommandlistener, (ICommand) this, "commands.weather.thunder", new Object[0]);
            }

        } else {
            throw new ExceptionUsage("commands.weather.usage", new Object[0]);
        }
    }

    public List<String> tabComplete(MinecraftServer minecraftserver, ICommandListener icommandlistener, String[] astring, @Nullable BlockPosition blockposition) {
        return astring.length == 1 ? a(astring, new String[] { "clear", "rain", "thunder"}) : Collections.emptyList();
    }
}
