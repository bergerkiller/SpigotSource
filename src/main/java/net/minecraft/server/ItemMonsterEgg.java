package net.minecraft.server;

public class ItemMonsterEgg extends Item {

    public ItemMonsterEgg() {
        this.a(true);
        this.a(CreativeModeTab.f);
    }

    public String a(ItemStack itemstack) {
        String s = ("" + LocaleI18n.get(this.getName() + ".name")).trim();
        String s1 = EntityTypes.b(itemstack.getData());

        if (s1 != null) {
            s = s + " " + LocaleI18n.get("entity." + s1 + ".name");
        }

        return s;
    }

    public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2) {
        // CraftBukkit - check ItemStack data
        if (world.isStatic || itemstack.getData() == 48 || itemstack.getData() == 49 || itemstack.getData() == 63 || itemstack.getData() == 64) {
            return true;
        } else if (!entityhuman.a(blockposition.shift(enumdirection), enumdirection, itemstack)) {
            return false;
        } else {
            IBlockData iblockdata = world.getType(blockposition);

            if (iblockdata.getBlock() == Blocks.MOB_SPAWNER) {
                TileEntity tileentity = world.getTileEntity(blockposition);

                if (tileentity instanceof TileEntityMobSpawner) {
                    MobSpawnerAbstract mobspawnerabstract = ((TileEntityMobSpawner) tileentity).getSpawner();

                    mobspawnerabstract.setMobName(EntityTypes.b(itemstack.getData()));
                    tileentity.update();
                    world.notify(blockposition);
                    if (!entityhuman.abilities.canInstantlyBuild) {
                        --itemstack.count;
                    }

                    return true;
                }
            }

            blockposition = blockposition.shift(enumdirection);
            double d0 = 0.0D;

            if (enumdirection == EnumDirection.UP && iblockdata instanceof BlockFence) {
                d0 = 0.5D;
            }

            Entity entity = a(world, itemstack.getData(), (double) blockposition.getX() + 0.5D, (double) blockposition.getY() + d0, (double) blockposition.getZ() + 0.5D);

            if (entity != null) {
                if (entity instanceof EntityLiving && itemstack.hasName()) {
                    entity.setCustomName(itemstack.getName());
                }

                if (!entityhuman.abilities.canInstantlyBuild) {
                    --itemstack.count;
                }
            }

            return true;
        }
    }

    public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
        if (world.isStatic) {
            return itemstack;
        } else {
            MovingObjectPosition movingobjectposition = this.a(world, entityhuman, true);

            if (movingobjectposition == null) {
                return itemstack;
            } else {
                if (movingobjectposition.type == EnumMovingObjectType.BLOCK) {
                    BlockPosition blockposition = movingobjectposition.a();

                    if (!world.a(entityhuman, blockposition)) {
                        return itemstack;
                    }

                    if (!entityhuman.a(blockposition, movingobjectposition.direction, itemstack)) {
                        return itemstack;
                    }

                    if (world.getType(blockposition).getBlock() instanceof BlockFluids) {
                        Entity entity = a(world, itemstack.getData(), (double) blockposition.getX() + 0.5D, (double) blockposition.getY() + 0.5D, (double) blockposition.getZ() + 0.5D);

                        if (entity != null) {
                            if (entity instanceof EntityLiving && itemstack.hasName()) {
                                ((EntityInsentient) entity).setCustomName(itemstack.getName());
                            }

                            if (!entityhuman.abilities.canInstantlyBuild) {
                                --itemstack.count;
                            }

                            entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
                        }
                    }
                }

                return itemstack;
            }
        }
    }

    public static Entity a(World world, int i, double d0, double d1, double d2) {
        // CraftBukkit start - delegate to spawnCreature
        return spawnCreature(world, i, d0, d1, d2, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
    }

    public static Entity spawnCreature(World world, int i, double d0, double d1, double d2, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason spawnReason) {
        // CraftBukkit end
        if (!EntityTypes.eggInfo.containsKey(Integer.valueOf(i))) {
            return null;
        } else {
            Entity entity = null;

            for (int j = 0; j < 1; ++j) {
                entity = EntityTypes.a(i, world);
                if (entity instanceof EntityLiving) {
                    EntityInsentient entityinsentient = (EntityInsentient) entity;

                    entity.setPositionRotation(d0, d1, d2, MathHelper.g(world.random.nextFloat() * 360.0F), 0.0F);
                    entityinsentient.aI = entityinsentient.yaw;
                    entityinsentient.aG = entityinsentient.yaw;
                    entityinsentient.prepare(world.E(new BlockPosition(entityinsentient)), (GroupDataEntity) null);
                    world.addEntity(entity);
                    entityinsentient.x();
                }
            }

            return entity;
        }
    }
}
