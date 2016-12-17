package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;

import org.bukkit.craftbukkit.event.CraftEventFactory; // CraftBukkit

public class EntityFallingBlock extends Entity {

    private IBlockData block;
    public int ticksLived;
    public boolean dropItem = true;
    private boolean f;
    public boolean hurtEntities;
    private int fallHurtMax = 40;
    private float fallHurtAmount = 2.0F;
    public NBTTagCompound tileEntityData;
    protected static final DataWatcherObject<BlockPosition> d = DataWatcher.a(EntityFallingBlock.class, DataWatcherRegistry.j);

    public EntityFallingBlock(World world) {
        super(world);
    }

    public EntityFallingBlock(World world, double d0, double d1, double d2, IBlockData iblockdata) {
        super(world);
        this.block = iblockdata;
        this.i = true;
        this.setSize(0.98F, 0.98F);
        this.setPosition(d0, d1 + (double) ((1.0F - this.length) / 2.0F), d2);
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
        this.lastX = d0;
        this.lastY = d1;
        this.lastZ = d2;
        this.a(new BlockPosition(this));
    }

    public void a(BlockPosition blockposition) {
        this.datawatcher.set(EntityFallingBlock.d, blockposition);
    }

    protected boolean playStepSound() {
        return false;
    }

    protected void i() {
        this.datawatcher.register(EntityFallingBlock.d, BlockPosition.ZERO);
    }

    public boolean isInteractable() {
        return !this.dead;
    }

    public void m() {
        Block block = this.block.getBlock();

        if (this.block.getMaterial() == Material.AIR) {
            this.die();
        } else {
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            BlockPosition blockposition;

            if (this.ticksLived++ == 0) {
                blockposition = new BlockPosition(this);
                if (this.world.getType(blockposition).getBlock() == block && !CraftEventFactory.callEntityChangeBlockEvent(this, blockposition.getX(), blockposition.getY(), blockposition.getZ(), Blocks.AIR, 0).isCancelled()) {
                    this.world.setAir(blockposition);
                } else if (!this.world.isClientSide) {
                    this.die();
                    return;
                }
            }

            this.motY -= 0.03999999910593033D;
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.9800000190734863D;
            this.motY *= 0.9800000190734863D;
            this.motZ *= 0.9800000190734863D;
            if (!this.world.isClientSide) {
                blockposition = new BlockPosition(this);
                if (this.onGround) {
                    IBlockData iblockdata = this.world.getType(blockposition);

                    if (BlockFalling.i(this.world.getType(new BlockPosition(this.locX, this.locY - 0.009999999776482582D, this.locZ)))) {
                        this.onGround = false;
                        // return; // CraftBukkit
                    }

                    this.motX *= 0.699999988079071D;
                    this.motZ *= 0.699999988079071D;
                    this.motY *= -0.5D;
                    if (iblockdata.getBlock() != Blocks.PISTON_EXTENSION) {
                        this.die();
                        if (!this.f) {
                            // CraftBukkit start
                            if (this.world.a(block, blockposition, true, EnumDirection.UP, (Entity) null, (ItemStack) null) && !BlockFalling.i(this.world.getType(blockposition.down()))) {
                                if (CraftEventFactory.callEntityChangeBlockEvent(this, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this.block.getBlock(), this.block.getBlock().toLegacyData(this.block)).isCancelled()) {
                                    return;
                                }
                                this.world.setTypeAndData(blockposition, this.block, 3);
                                // CraftBukkit end
                                if (block instanceof BlockFalling) {
                                    ((BlockFalling) block).a_(this.world, blockposition);
                                }

                                if (this.tileEntityData != null && block instanceof ITileEntity) {
                                    TileEntity tileentity = this.world.getTileEntity(blockposition);

                                    if (tileentity != null) {
                                        NBTTagCompound nbttagcompound = tileentity.save(new NBTTagCompound());
                                        Iterator iterator = this.tileEntityData.c().iterator();

                                        while (iterator.hasNext()) {
                                            String s = (String) iterator.next();
                                            NBTBase nbtbase = this.tileEntityData.get(s);

                                            if (!s.equals("x") && !s.equals("y") && !s.equals("z")) {
                                                nbttagcompound.set(s, nbtbase.clone());
                                            }
                                        }

                                        tileentity.a(nbttagcompound);
                                        tileentity.update();
                                    }
                                }
                            } else if (this.dropItem && this.world.getGameRules().getBoolean("doEntityDrops")) {
                                this.a(new ItemStack(block, 1, block.getDropData(this.block)), 0.0F);
                            }
                        }
                    }
                } else if (this.ticksLived > 100 && !this.world.isClientSide && (blockposition.getY() < 1 || blockposition.getY() > 256) || this.ticksLived > 600) {
                    if (this.dropItem && this.world.getGameRules().getBoolean("doEntityDrops")) {
                        this.a(new ItemStack(block, 1, block.getDropData(this.block)), 0.0F);
                    }

                    this.die();
                }
            }

        }
    }

    public void e(float f, float f1) {
        Block block = this.block.getBlock();

        if (this.hurtEntities) {
            int i = MathHelper.f(f - 1.0F);

            if (i > 0) {
                ArrayList arraylist = Lists.newArrayList(this.world.getEntities(this, this.getBoundingBox()));
                boolean flag = block == Blocks.ANVIL;
                DamageSource damagesource = flag ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
                Iterator iterator = arraylist.iterator();

                while (iterator.hasNext()) {
                    Entity entity = (Entity) iterator.next();

                    CraftEventFactory.entityDamage = this; // CraftBukkit
                    entity.damageEntity(damagesource, (float) Math.min(MathHelper.d((float) i * this.fallHurtAmount), this.fallHurtMax));
                    CraftEventFactory.entityDamage = null; // CraftBukkit
                }

                if (flag && (double) this.random.nextFloat() < 0.05000000074505806D + (double) i * 0.05D) {
                    int j = ((Integer) this.block.get(BlockAnvil.DAMAGE)).intValue();

                    ++j;
                    if (j > 2) {
                        this.f = true;
                    } else {
                        this.block = this.block.set(BlockAnvil.DAMAGE, Integer.valueOf(j));
                    }
                }
            }
        }

    }

    protected void b(NBTTagCompound nbttagcompound) {
        Block block = this.block != null ? this.block.getBlock() : Blocks.AIR;
        MinecraftKey minecraftkey = (MinecraftKey) Block.REGISTRY.b(block);

        nbttagcompound.setString("Block", minecraftkey == null ? "" : minecraftkey.toString());
        nbttagcompound.setByte("Data", (byte) block.toLegacyData(this.block));
        nbttagcompound.setInt("Time", this.ticksLived);
        nbttagcompound.setBoolean("DropItem", this.dropItem);
        nbttagcompound.setBoolean("HurtEntities", this.hurtEntities);
        nbttagcompound.setFloat("FallHurtAmount", this.fallHurtAmount);
        nbttagcompound.setInt("FallHurtMax", this.fallHurtMax);
        if (this.tileEntityData != null) {
            nbttagcompound.set("TileEntityData", this.tileEntityData);
        }

    }

    protected void a(NBTTagCompound nbttagcompound) {
        int i = nbttagcompound.getByte("Data") & 255;

        if (nbttagcompound.hasKeyOfType("Block", 8)) {
            this.block = Block.getByName(nbttagcompound.getString("Block")).fromLegacyData(i);
        } else if (nbttagcompound.hasKeyOfType("TileID", 99)) {
            this.block = Block.getById(nbttagcompound.getInt("TileID")).fromLegacyData(i);
        } else {
            this.block = Block.getById(nbttagcompound.getByte("Tile") & 255).fromLegacyData(i);
        }

        this.ticksLived = nbttagcompound.getInt("Time");
        Block block = this.block.getBlock();

        if (nbttagcompound.hasKeyOfType("HurtEntities", 99)) {
            this.hurtEntities = nbttagcompound.getBoolean("HurtEntities");
            this.fallHurtAmount = nbttagcompound.getFloat("FallHurtAmount");
            this.fallHurtMax = nbttagcompound.getInt("FallHurtMax");
        } else if (block == Blocks.ANVIL) {
            this.hurtEntities = true;
        }

        if (nbttagcompound.hasKeyOfType("DropItem", 99)) {
            this.dropItem = nbttagcompound.getBoolean("DropItem");
        }

        if (nbttagcompound.hasKeyOfType("TileEntityData", 10)) {
            this.tileEntityData = nbttagcompound.getCompound("TileEntityData");
        }

        if (block == null || block.getBlockData().getMaterial() == Material.AIR) {
            this.block = Blocks.SAND.getBlockData();
        }

    }

    public void a(boolean flag) {
        this.hurtEntities = flag;
    }

    public void appendEntityCrashDetails(CrashReportSystemDetails crashreportsystemdetails) {
        super.appendEntityCrashDetails(crashreportsystemdetails);
        if (this.block != null) {
            Block block = this.block.getBlock();

            crashreportsystemdetails.a("Immitating block ID", (Object) Integer.valueOf(Block.getId(block)));
            crashreportsystemdetails.a("Immitating block data", (Object) Integer.valueOf(block.toLegacyData(this.block)));
        }

    }

    @Nullable
    public IBlockData getBlock() {
        return this.block;
    }

    public boolean bs() {
        return true;
    }
}
