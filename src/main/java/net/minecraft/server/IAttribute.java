package net.minecraft.server;

import javax.annotation.Nullable;

public interface IAttribute {

    String getName();

    double a(double d0);

    double b();

    boolean c();

    @Nullable
    IAttribute d();
}
