package org.spigotmc;

import com.google.common.collect.ImmutableList;
import net.minecraft.server.ChatComponentText;
import net.minecraft.server.ChunkCoordinates;
import net.minecraft.server.EntityMinecartCommandBlock;
import net.minecraft.server.IChatBaseComponent;
import net.minecraft.server.ICommandListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TileEntityCommand;
import net.minecraft.server.World;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.command.CraftBlockCommandSender;
import org.bukkit.craftbukkit.entity.CraftMinecartCommand;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class VanillaCommandWrapper
{

    public static final HashSet<String> allowedCommands = new HashSet<String>();

    public static int dispatch(CommandSender sender, String commandLine)
    {
        int pos = commandLine.indexOf( ' ' );
        if ( pos == -1 )
        {
            pos = commandLine.length();
        }
        String name = commandLine.substring( 0, pos );
        if ( !allowedCommands.contains( name ) )
        {
            return -1;
        }
        if ( !sender.hasPermission( "bukkit.command." + name ) )
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission for this command" );
            return 0;
        }
        ICommandListener listener = getListener( sender );
        if ( listener == null )
        {
            return -1;
        }
        return MinecraftServer.getServer().getCommandHandler().a( listener, commandLine );
    }

    public static List<String> complete(CommandSender sender, String commandLine)
    {
        int pos = commandLine.indexOf( ' ' );
        if ( pos == -1 )
        {
            List<String> completions = new ArrayList<String>();
            commandLine = commandLine.toLowerCase();
            for ( String command : allowedCommands )
            {
                if ( command.startsWith( commandLine ) && sender.hasPermission( "bukkit.command." + command ) )
                {
                    completions.add( "/" + command );
                }
            }
            return completions;
        }
        String name = commandLine.substring( 0, pos );
        if ( !allowedCommands.contains( name ) || !sender.hasPermission( "bukkit.command." + name ) )
        {
            return ImmutableList.<String>of();
        }
        ICommandListener listener = getListener( sender );
        if ( listener == null )
        {
            return ImmutableList.<String>of();
        }
        return MinecraftServer.getServer().getCommandHandler().b( listener, commandLine );
    }

    private static ICommandListener getListener(CommandSender sender)
    {
        if ( sender instanceof CraftPlayer )
        {
            return new PlayerListener( ( (CraftPlayer) sender ).getHandle() );
        }
        if ( sender instanceof CraftBlockCommandSender )
        {
            CraftBlockCommandSender commandBlock = (CraftBlockCommandSender) sender;
            Block block = commandBlock.getBlock();
            return ( (TileEntityCommand) ( (CraftWorld) block.getWorld() ).getTileEntityAt( block.getX(), block.getY(), block.getZ() ) ).a();
        }
        if ( sender instanceof CraftMinecartCommand )
        {
            return ( (EntityMinecartCommandBlock) ( (CraftMinecartCommand) sender ).getHandle() ).e();
        }
        return new ConsoleListener(sender); // Assume console/rcon
    }

    private static class PlayerListener implements ICommandListener
    {

        private final ICommandListener handle;

        public PlayerListener(ICommandListener handle)
        {
            this.handle = handle;
        }

        @Override
        public String getName()
        {
            return handle.getName();
        }

        @Override
        public IChatBaseComponent getScoreboardDisplayName()
        {
            return handle.getScoreboardDisplayName();
        }

        @Override
        public void sendMessage(IChatBaseComponent iChatBaseComponent)
        {
            handle.sendMessage( iChatBaseComponent );
        }

        @Override
        public boolean a(int i, String s)
        {
            return true;
        }

        @Override
        public ChunkCoordinates getChunkCoordinates()
        {
            return handle.getChunkCoordinates();
        }

        @Override
        public World getWorld()
        {
            return handle.getWorld();
        }
    }

    private static class ConsoleListener implements ICommandListener {

        private final CommandSender sender;

        public ConsoleListener( CommandSender sender )
        {
            this.sender = sender;
        }

        @Override
        public String getName()
        {
            return sender.getName();
        }

        @Override
        public IChatBaseComponent getScoreboardDisplayName()
        {
            return new ChatComponentText( getName() );
        }

        @Override
        public void sendMessage( IChatBaseComponent iChatBaseComponent )
        {
            sender.sendMessage( iChatBaseComponent.e() );
        }

        @Override
        public boolean a( int i, String s )
        {
            return true;
        }

        @Override
        public ChunkCoordinates getChunkCoordinates()
        {
            return new ChunkCoordinates( 0, 0, 0 );
        }

        @Override
        public World getWorld()
        {
            return MinecraftServer.getServer().getWorld();
        }
    }
}
