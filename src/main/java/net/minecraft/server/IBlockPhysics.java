package net.minecraft.server;

public interface IBlockPhysics {

    boolean a(World world, BlockPosition blockposition, int i, int j);

    void doPhysics(World world, BlockPosition blockposition, Block block);
}
