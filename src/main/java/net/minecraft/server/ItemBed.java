package net.minecraft.server;

public class ItemBed extends Item {

    public ItemBed() {
        this.a(CreativeModeTab.c);
    }

    public EnumInteractionResult a(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumHand enumhand, EnumDirection enumdirection, float f, float f1, float f2) {
        if (world.isClientSide) {
            return EnumInteractionResult.SUCCESS;
        } else if (enumdirection != EnumDirection.UP) {
            return EnumInteractionResult.FAIL;
        } else {
            IBlockData iblockdata = world.getType(blockposition);
            Block block = iblockdata.getBlock();
            boolean flag = block.a((IBlockAccess) world, blockposition);

            if (!flag) {
                blockposition = blockposition.up();
            }

            int i = MathHelper.floor((double) (entityhuman.yaw * 4.0F / 360.0F) + 0.5D) & 3;
            EnumDirection enumdirection1 = EnumDirection.fromType2(i);
            BlockPosition blockposition1 = blockposition.shift(enumdirection1);

            if (entityhuman.a(blockposition, enumdirection, itemstack) && entityhuman.a(blockposition1, enumdirection, itemstack)) {
                boolean flag1 = world.getType(blockposition1).getBlock().a((IBlockAccess) world, blockposition1);
                boolean flag2 = flag || world.isEmpty(blockposition);
                boolean flag3 = flag1 || world.isEmpty(blockposition1);

                if (flag2 && flag3 && world.getType(blockposition.down()).q() && world.getType(blockposition1.down()).q()) {
                    IBlockData iblockdata1 = Blocks.BED.getBlockData().set(BlockBed.OCCUPIED, Boolean.valueOf(false)).set(BlockBed.FACING, enumdirection1).set(BlockBed.PART, BlockBed.EnumBedPart.FOOT);

                    if (world.setTypeAndData(blockposition, iblockdata1, 11)) {
                        IBlockData iblockdata2 = iblockdata1.set(BlockBed.PART, BlockBed.EnumBedPart.HEAD);

                        world.setTypeAndData(blockposition1, iblockdata2, 11);
                    }

                    SoundEffectType soundeffecttype = iblockdata1.getBlock().w();

                    world.a((EntityHuman) null, blockposition, soundeffecttype.e(), SoundCategory.BLOCKS, (soundeffecttype.a() + 1.0F) / 2.0F, soundeffecttype.b() * 0.8F);
                    --itemstack.count;
                    return EnumInteractionResult.SUCCESS;
                } else {
                    return EnumInteractionResult.FAIL;
                }
            } else {
                return EnumInteractionResult.FAIL;
            }
        }
    }
}
