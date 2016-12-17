package org.bukkit.craftbukkit.util;

import java.util.HashSet;
import java.util.List;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import org.bukkit.entity.Player;

public class LazyPlayerSet extends LazyHashSet<Player> {

    private final MinecraftServer server;

    public LazyPlayerSet(MinecraftServer server) {
        this.server = server;
    }

    @Override
    HashSet<Player> makeReference() {
        if (reference != null) {
            throw new IllegalStateException("Reference already created!");
        }
        List<EntityPlayer> players = server.getPlayerList().players;
        HashSet<Player> reference = new HashSet<Player>(players.size());
        for (EntityPlayer player : players) {
            reference.add(player.getBukkitEntity());
        }
        return reference;
    }
}
