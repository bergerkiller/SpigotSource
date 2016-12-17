package net.minecraft.server;

public class ItemRedstone extends Item {

    public ItemRedstone() {
        this.a(CreativeModeTab.d);
    }

    public EnumInteractionResult a(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumHand enumhand, EnumDirection enumdirection, float f, float f1, float f2) {
        boolean flag = world.getType(blockposition).getBlock().a((IBlockAccess) world, blockposition);
        BlockPosition blockposition1 = flag ? blockposition : blockposition.shift(enumdirection);

        if (entityhuman.a(blockposition1, enumdirection, itemstack) && world.a(world.getType(blockposition1).getBlock(), blockposition1, false, enumdirection, (Entity) null, itemstack) && Blocks.REDSTONE_WIRE.canPlace(world, blockposition1)) {
            --itemstack.count;
            world.setTypeUpdate(blockposition1, Blocks.REDSTONE_WIRE.getBlockData());
            return EnumInteractionResult.SUCCESS;
        } else {
            return EnumInteractionResult.FAIL;
        }
    }
}
