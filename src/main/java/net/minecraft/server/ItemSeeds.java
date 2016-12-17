package net.minecraft.server;

public class ItemSeeds extends Item {

    private Block a;
    private Block b;

    public ItemSeeds(Block block, Block block1) {
        this.a = block;
        this.b = block1;
        this.a(CreativeModeTab.l);
    }

    public EnumInteractionResult a(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumHand enumhand, EnumDirection enumdirection, float f, float f1, float f2) {
        if (enumdirection == EnumDirection.UP && entityhuman.a(blockposition.shift(enumdirection), enumdirection, itemstack) && world.getType(blockposition).getBlock() == this.b && world.isEmpty(blockposition.up())) {
            world.setTypeUpdate(blockposition.up(), this.a.getBlockData());
            --itemstack.count;
            return EnumInteractionResult.SUCCESS;
        } else {
            return EnumInteractionResult.FAIL;
        }
    }
}
