package net.minecraft.server;

public class ItemSnow extends ItemBlock {

    public ItemSnow(Block block) {
        super(block);
        this.setMaxDurability(0);
        this.a(true);
    }

    public EnumInteractionResult a(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumHand enumhand, EnumDirection enumdirection, float f, float f1, float f2) {
        if (itemstack.count != 0 && entityhuman.a(blockposition, enumdirection, itemstack)) {
            IBlockData iblockdata = world.getType(blockposition);
            Block block = iblockdata.getBlock();
            BlockPosition blockposition1 = blockposition;

            if ((enumdirection != EnumDirection.UP || block != this.a) && !block.a((IBlockAccess) world, blockposition)) {
                blockposition1 = blockposition.shift(enumdirection);
                iblockdata = world.getType(blockposition1);
                block = iblockdata.getBlock();
            }

            if (block == this.a) {
                int i = ((Integer) iblockdata.get(BlockSnow.LAYERS)).intValue();

                if (i <= 7) {
                    IBlockData iblockdata1 = iblockdata.set(BlockSnow.LAYERS, Integer.valueOf(i + 1));
                    AxisAlignedBB axisalignedbb = iblockdata1.d(world, blockposition1);

                    if (axisalignedbb != Block.k && world.c(axisalignedbb.a(blockposition1)) && world.setTypeAndData(blockposition1, iblockdata1, 10)) {
                        SoundEffectType soundeffecttype = this.a.w();

                        world.a(entityhuman, blockposition1, soundeffecttype.e(), SoundCategory.BLOCKS, (soundeffecttype.a() + 1.0F) / 2.0F, soundeffecttype.b() * 0.8F);
                        --itemstack.count;
                        return EnumInteractionResult.SUCCESS;
                    }
                }
            }

            return super.a(itemstack, entityhuman, world, blockposition1, enumhand, enumdirection, f, f1, f2);
        } else {
            return EnumInteractionResult.FAIL;
        }
    }

    public int filterData(int i) {
        return i;
    }
}
