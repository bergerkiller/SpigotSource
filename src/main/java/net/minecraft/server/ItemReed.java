package net.minecraft.server;

public class ItemReed extends Item {

    private Block a;

    public ItemReed(Block block) {
        this.a = block;
    }

    public EnumInteractionResult a(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumHand enumhand, EnumDirection enumdirection, float f, float f1, float f2) {
        IBlockData iblockdata = world.getType(blockposition);
        Block block = iblockdata.getBlock();

        if (block == Blocks.SNOW_LAYER && ((Integer) iblockdata.get(BlockSnow.LAYERS)).intValue() < 1) {
            enumdirection = EnumDirection.UP;
        } else if (!block.a((IBlockAccess) world, blockposition)) {
            blockposition = blockposition.shift(enumdirection);
        }

        if (entityhuman.a(blockposition, enumdirection, itemstack) && itemstack.count != 0 && world.a(this.a, blockposition, false, enumdirection, (Entity) null, itemstack)) {
            IBlockData iblockdata1 = this.a.getPlacedState(world, blockposition, enumdirection, f, f1, f2, 0, entityhuman);

            if (!world.setTypeAndData(blockposition, iblockdata1, 11)) {
                return EnumInteractionResult.FAIL;
            } else {
                iblockdata1 = world.getType(blockposition);
                if (iblockdata1.getBlock() == this.a) {
                    ItemBlock.a(world, entityhuman, blockposition, itemstack);
                    iblockdata1.getBlock().postPlace(world, blockposition, iblockdata1, entityhuman, itemstack);
                }

                SoundEffectType soundeffecttype = this.a.w();

                world.a(entityhuman, blockposition, soundeffecttype.e(), SoundCategory.BLOCKS, (soundeffecttype.a() + 1.0F) / 2.0F, soundeffecttype.b() * 0.8F);
                --itemstack.count;
                return EnumInteractionResult.SUCCESS;
            }
        } else {
            return EnumInteractionResult.FAIL;
        }
    }
}
