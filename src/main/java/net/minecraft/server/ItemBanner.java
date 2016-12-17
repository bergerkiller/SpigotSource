package net.minecraft.server;

public class ItemBanner extends ItemBlock {

    public ItemBanner() {
        super(Blocks.STANDING_BANNER);
        this.maxStackSize = 16;
        this.a(CreativeModeTab.c);
        this.a(true);
        this.setMaxDurability(0);
    }

    public EnumInteractionResult a(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumHand enumhand, EnumDirection enumdirection, float f, float f1, float f2) {
        IBlockData iblockdata = world.getType(blockposition);
        boolean flag = iblockdata.getBlock().a((IBlockAccess) world, blockposition);

        if (enumdirection != EnumDirection.DOWN && (iblockdata.getMaterial().isBuildable() || flag) && (!flag || enumdirection == EnumDirection.UP)) {
            blockposition = blockposition.shift(enumdirection);
            if (entityhuman.a(blockposition, enumdirection, itemstack) && Blocks.STANDING_BANNER.canPlace(world, blockposition)) {
                if (world.isClientSide) {
                    return EnumInteractionResult.SUCCESS;
                } else {
                    blockposition = flag ? blockposition.down() : blockposition;
                    if (enumdirection == EnumDirection.UP) {
                        int i = MathHelper.floor((double) ((entityhuman.yaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;

                        world.setTypeAndData(blockposition, Blocks.STANDING_BANNER.getBlockData().set(BlockFloorSign.ROTATION, Integer.valueOf(i)), 3);
                    } else {
                        world.setTypeAndData(blockposition, Blocks.WALL_BANNER.getBlockData().set(BlockWallSign.FACING, enumdirection), 3);
                    }

                    --itemstack.count;
                    TileEntity tileentity = world.getTileEntity(blockposition);

                    if (tileentity instanceof TileEntityBanner) {
                        ((TileEntityBanner) tileentity).a(itemstack);
                    }

                    return EnumInteractionResult.SUCCESS;
                }
            } else {
                return EnumInteractionResult.FAIL;
            }
        } else {
            return EnumInteractionResult.FAIL;
        }
    }

    public String a(ItemStack itemstack) {
        String s = "item.banner.";
        EnumColor enumcolor = b(itemstack);

        s = s + enumcolor.d() + ".name";
        return LocaleI18n.get(s);
    }

    public static EnumColor b(ItemStack itemstack) {
        NBTTagCompound nbttagcompound = itemstack.a("BlockEntityTag", false);
        EnumColor enumcolor = null;

        if (nbttagcompound != null && nbttagcompound.hasKey("Base")) {
            enumcolor = EnumColor.fromInvColorIndex(nbttagcompound.getInt("Base"));
        } else {
            enumcolor = EnumColor.fromInvColorIndex(itemstack.getData());
        }

        return enumcolor;
    }
}
