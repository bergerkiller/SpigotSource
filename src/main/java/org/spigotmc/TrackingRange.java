package org.spigotmc;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityExperienceOrb;
import net.minecraft.server.EntityGhast;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityItemFrame;
import net.minecraft.server.EntityPainting;
import net.minecraft.server.EntityPlayer;

public class TrackingRange
{

    /**
     * Gets the range an entity should be 'tracked' by players and visible in
     * the client.
     *
     * @param entity
     * @param defaultRange Default range defined by Mojang
     * @return
     */
    public static int getEntityTrackingRange(Entity entity, int defaultRange)
    {
        SpigotWorldConfig config = entity.world.spigotConfig;
        int range = defaultRange;
        if ( entity instanceof EntityPlayer )
        {
            range = config.playerTrackingRange;
        } else if ( entity.defaultActivationState || entity instanceof EntityGhast )
        {
            range = defaultRange;
        } else if ( entity.activationType == 1 )
        {
            range = config.monsterTrackingRange;
        } else if ( entity.activationType == 2 )
        {
            range = config.animalTrackingRange;
        } else if ( entity instanceof EntityItemFrame || entity instanceof EntityPainting || entity instanceof EntityItem || entity instanceof EntityExperienceOrb )
        {
            range = config.miscTrackingRange;
        }

        return Math.min( config.maxTrackingRange, range );
    }
}
