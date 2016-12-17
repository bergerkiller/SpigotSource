package net.minecraft.server;

// CraftBukkit start
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.player.PlayerTeleportEvent;
// CraftBukkit end

public class EntityEnderPearl extends EntityProjectile {

    private EntityLiving d;

    public EntityEnderPearl(World world) {
        super(world);
    }

    public EntityEnderPearl(World world, EntityLiving entityliving) {
        super(world, entityliving);
        this.d = entityliving;
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        EntityLiving entityliving = this.getShooter();

        if (movingobjectposition.entity != null) {
            if (movingobjectposition.entity == this.d) {
                return;
            }

            movingobjectposition.entity.damageEntity(DamageSource.projectile(this, entityliving), 0.0F);
        }

        if (movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
            BlockPosition blockposition = movingobjectposition.a();
            TileEntity tileentity = this.world.getTileEntity(blockposition);

            if (tileentity instanceof TileEntityEndGateway) {
                TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway) tileentity;

                if (entityliving != null) {
                    tileentityendgateway.a((Entity) entityliving);
                    this.die();
                    return;
                }

                tileentityendgateway.a((Entity) this);
                return;
            }
        }

        for (int i = 0; i < 32; ++i) {
            this.world.addParticle(EnumParticle.PORTAL, this.locX, this.locY + this.random.nextDouble() * 2.0D, this.locZ, this.random.nextGaussian(), 0.0D, this.random.nextGaussian(), new int[0]);
        }

        if (!this.world.isClientSide) {
            if (entityliving instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) entityliving;

                if (entityplayer.playerConnection.a().isConnected() && entityplayer.world == this.world && !entityplayer.isSleeping()) {
                    // CraftBukkit start - Fire PlayerTeleportEvent
                    org.bukkit.craftbukkit.entity.CraftPlayer player = entityplayer.getBukkitEntity();
                    org.bukkit.Location location = getBukkitEntity().getLocation();
                    location.setPitch(player.getLocation().getPitch());
                    location.setYaw(player.getLocation().getYaw());

                    PlayerTeleportEvent teleEvent = new PlayerTeleportEvent(player, player.getLocation(), location, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                    Bukkit.getPluginManager().callEvent(teleEvent);

                    if (!teleEvent.isCancelled() && !entityplayer.playerConnection.isDisconnected()) {
                        if (this.random.nextFloat() < 0.05F && this.world.getGameRules().getBoolean("doMobSpawning")) {
                            EntityEndermite entityendermite = new EntityEndermite(this.world);

                            entityendermite.a(true);
                            entityendermite.setPositionRotation(entityliving.locX, entityliving.locY, entityliving.locZ, entityliving.yaw, entityliving.pitch);
                            this.world.addEntity(entityendermite);
                        }

                        if (entityliving.isPassenger()) {
                            entityliving.stopRiding();
                        }

                        entityplayer.playerConnection.teleport(teleEvent.getTo());
                        entityliving.fallDistance = 0.0F;
                        CraftEventFactory.entityDamage = this;
                        entityliving.damageEntity(DamageSource.FALL, 5.0F);
                        CraftEventFactory.entityDamage = null;
                    }
                    // CraftBukkit end
                }
            } else if (entityliving != null) {
                entityliving.enderTeleportTo(this.locX, this.locY, this.locZ);
                entityliving.fallDistance = 0.0F;
            }

            this.die();
        }

    }

    public void m() {
        EntityLiving entityliving = this.getShooter();

        if (entityliving != null && entityliving instanceof EntityHuman && !entityliving.isAlive()) {
            this.die();
        } else {
            super.m();
        }

    }
}
