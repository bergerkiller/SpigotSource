package net.minecraft.server;

public class BlockNetherbrick extends Block {

    public BlockNetherbrick() {
        super(Material.STONE);
        this.a(CreativeModeTab.b);
    }

    public MaterialMapColor r(IBlockData iblockdata) {
        return MaterialMapColor.K;
    }
}
