package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class BossBattleServer extends BossBattle {

    private final Set<EntityPlayer> h = Sets.newHashSet();
    private final Set<EntityPlayer> i;
    public boolean visible;

    public BossBattleServer(IChatBaseComponent ichatbasecomponent, BossBattle.BarColor bossbattle_barcolor, BossBattle.BarStyle bossbattle_barstyle) {
        super(MathHelper.a(), ichatbasecomponent, bossbattle_barcolor, bossbattle_barstyle);
        this.i = Collections.unmodifiableSet(this.h);
        this.visible = true;
    }

    public void setProgress(float f) {
        if (f != this.b) {
            super.a(f);
            this.sendUpdate(PacketPlayOutBoss.Action.UPDATE_PCT);
        }

    }

    public BossBattle setDarkenSky(boolean flag) {
        if (flag != this.e) {
            super.a(flag);
            this.sendUpdate(PacketPlayOutBoss.Action.UPDATE_PROPERTIES);
        }

        return this;
    }

    public BossBattle setPlayMusic(boolean flag) {
        if (flag != this.f) {
            super.b(flag);
            this.sendUpdate(PacketPlayOutBoss.Action.UPDATE_PROPERTIES);
        }

        return this;
    }

    public BossBattle setCreateFog(boolean flag) {
        if (flag != this.g) {
            super.c(flag);
            this.sendUpdate(PacketPlayOutBoss.Action.UPDATE_PROPERTIES);
        }

        return this;
    }

    public void sendUpdate(PacketPlayOutBoss.Action packetplayoutboss_action) {
        if (this.visible) {
            PacketPlayOutBoss packetplayoutboss = new PacketPlayOutBoss(packetplayoutboss_action, this);
            Iterator iterator = this.h.iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                entityplayer.playerConnection.sendPacket(packetplayoutboss);
            }
        }

    }

    public void addPlayer(EntityPlayer entityplayer) {
        if (this.h.add(entityplayer) && this.visible) {
            entityplayer.playerConnection.sendPacket(new PacketPlayOutBoss(PacketPlayOutBoss.Action.ADD, this));
        }

    }

    public void removePlayer(EntityPlayer entityplayer) {
        if (this.h.remove(entityplayer) && this.visible) {
            entityplayer.playerConnection.sendPacket(new PacketPlayOutBoss(PacketPlayOutBoss.Action.REMOVE, this));
        }

    }

    public void setVisible(boolean flag) {
        if (flag != this.visible) {
            this.visible = flag;
            Iterator iterator = this.h.iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                entityplayer.playerConnection.sendPacket(new PacketPlayOutBoss(flag ? PacketPlayOutBoss.Action.ADD : PacketPlayOutBoss.Action.REMOVE, this));
            }
        }

    }

    public Collection<EntityPlayer> getPlayers() {
        return this.i;
    }
}
