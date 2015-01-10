package net.minecraft.server;

import java.util.Iterator;
import java.util.Random;

// CraftBukkit start
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.craftbukkit.event.CraftEventFactory;
// CraftBukkit end

public class BlockSoil extends Block {

    public static final BlockStateInteger MOISTURE = BlockStateInteger.of("moisture", 0, 7);

    protected BlockSoil() {
        super(Material.EARTH);
        this.j(this.blockStateList.getBlockData().set(BlockSoil.MOISTURE, Integer.valueOf(0)));
        this.a(true);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
        this.e(255);
    }

    public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return new AxisAlignedBB((double) blockposition.getX(), (double) blockposition.getY(), (double) blockposition.getZ(), (double) (blockposition.getX() + 1), (double) (blockposition.getY() + 1), (double) (blockposition.getZ() + 1));
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        int i = ((Integer) iblockdata.get(BlockSoil.MOISTURE)).intValue();

        if (!this.e(world, blockposition) && !world.isRainingAt(blockposition.up())) {
            if (i > 0) {
                world.setTypeAndData(blockposition, iblockdata.set(BlockSoil.MOISTURE, Integer.valueOf(i - 1)), 2);
            } else if (!this.d(world, blockposition)) {
                // CraftBukkit start
                org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
                if (CraftEventFactory.callBlockFadeEvent(block, Blocks.DIRT).isCancelled()) {
                    return;
                }
                // CraftBukkit end
                world.setTypeUpdate(blockposition, Blocks.DIRT.getBlockData());
            }
        } else if (i < 7) {
            world.setTypeAndData(blockposition, iblockdata.set(BlockSoil.MOISTURE, Integer.valueOf(7)), 2);
        }

    }

    public void a(World world, BlockPosition blockposition, Entity entity, float f) {
        if (entity instanceof EntityLiving) {
            if (!world.isStatic && world.random.nextFloat() < f - 0.5F) {
                if (!(entity instanceof EntityHuman) && !world.getGameRules().getBoolean("mobGriefing")) {
                    return;
                }

                // CraftBukkit start - Interact soil
                org.bukkit.event.Cancellable cancellable;
                if (entity instanceof EntityHuman) {
                    cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman) entity, org.bukkit.event.block.Action.PHYSICAL, blockposition, null, null);
                } else {
                    cancellable = new EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()));
                    world.getServer().getPluginManager().callEvent((EntityInteractEvent) cancellable);
                }

                if (cancellable.isCancelled()) {
                    return;
                }

                if (CraftEventFactory.callEntityChangeBlockEvent(entity, blockposition.getX(), blockposition.getY(), blockposition.getZ(), Blocks.DIRT, 0).isCancelled()) {
                    return;
                }
                // CraftBukkit end

                world.setTypeUpdate(blockposition, Blocks.DIRT.getBlockData());
            }

            super.a(world, blockposition, entity, f);
        }
    }

    private boolean d(World world, BlockPosition blockposition) {
        Block block = world.getType(blockposition.up()).getBlock();

        return block instanceof BlockCrops || block instanceof BlockStem;
    }

    private boolean e(World world, BlockPosition blockposition) {
        Iterator iterator = BlockPosition.b(blockposition.a(-4, 0, -4), blockposition.a(4, 1, 4)).iterator();

        MutableBlockPosition mutableblockposition;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            mutableblockposition = (MutableBlockPosition) iterator.next();
        } while (world.getType(mutableblockposition).getBlock().getMaterial() != Material.WATER);

        return true;
    }

    public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
        super.doPhysics(world, blockposition, iblockdata, block);
        if (world.getType(blockposition.up()).getBlock().getMaterial().isBuildable()) {
            world.setTypeUpdate(blockposition, Blocks.DIRT.getBlockData());
        }

    }

    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return Blocks.DIRT.getDropType(Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, EnumDirtVariant.DIRT), random, i);
    }

    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockSoil.MOISTURE, Integer.valueOf(i & 7));
    }

    public int toLegacyData(IBlockData iblockdata) {
        return ((Integer) iblockdata.get(BlockSoil.MOISTURE)).intValue();
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, new IBlockState[] { BlockSoil.MOISTURE});
    }
}
