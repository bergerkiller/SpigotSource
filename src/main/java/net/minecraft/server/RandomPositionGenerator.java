package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class RandomPositionGenerator {

    private static Vec3D a = Vec3D.a;

    @Nullable
    public static Vec3D a(EntityCreature entitycreature, int i, int j) {
        return c(entitycreature, i, j, (Vec3D) null);
    }

    @Nullable
    public static Vec3D a(EntityCreature entitycreature, int i, int j, Vec3D vec3d) {
        RandomPositionGenerator.a = vec3d.a(entitycreature.locX, entitycreature.locY, entitycreature.locZ);
        return c(entitycreature, i, j, RandomPositionGenerator.a);
    }

    @Nullable
    public static Vec3D b(EntityCreature entitycreature, int i, int j, Vec3D vec3d) {
        RandomPositionGenerator.a = (new Vec3D(entitycreature.locX, entitycreature.locY, entitycreature.locZ)).d(vec3d);
        return c(entitycreature, i, j, RandomPositionGenerator.a);
    }

    @Nullable
    private static Vec3D c(EntityCreature entitycreature, int i, int j, @Nullable Vec3D vec3d) {
        NavigationAbstract navigationabstract = entitycreature.getNavigation();
        Random random = entitycreature.getRandom();
        boolean flag = false;
        int k = 0;
        int l = 0;
        int i1 = 0;
        float f = -99999.0F;
        boolean flag1;

        if (entitycreature.cZ()) {
            double d0 = entitycreature.cW().distanceSquared((double) MathHelper.floor(entitycreature.locX), (double) MathHelper.floor(entitycreature.locY), (double) MathHelper.floor(entitycreature.locZ)) + 4.0D;
            double d1 = (double) (entitycreature.cX() + (float) i);

            flag1 = d0 < d1 * d1;
        } else {
            flag1 = false;
        }

        for (int j1 = 0; j1 < 10; ++j1) {
            int k1 = random.nextInt(2 * i + 1) - i;
            int l1 = random.nextInt(2 * j + 1) - j;
            int i2 = random.nextInt(2 * i + 1) - i;

            if (vec3d == null || (double) k1 * vec3d.x + (double) i2 * vec3d.z >= 0.0D) {
                BlockPosition blockposition;

                if (entitycreature.cZ() && i > 1) {
                    blockposition = entitycreature.cW();
                    if (entitycreature.locX > (double) blockposition.getX()) {
                        k1 -= random.nextInt(i / 2);
                    } else {
                        k1 += random.nextInt(i / 2);
                    }

                    if (entitycreature.locZ > (double) blockposition.getZ()) {
                        i2 -= random.nextInt(i / 2);
                    } else {
                        i2 += random.nextInt(i / 2);
                    }
                }

                k1 += MathHelper.floor(entitycreature.locX);
                l1 += MathHelper.floor(entitycreature.locY);
                i2 += MathHelper.floor(entitycreature.locZ);
                blockposition = new BlockPosition(k1, l1, i2);
                if ((!flag1 || entitycreature.f(blockposition)) && navigationabstract.b(blockposition)) {
                    float f1 = entitycreature.a(blockposition);

                    if (f1 > f) {
                        f = f1;
                        k = k1;
                        l = l1;
                        i1 = i2;
                        flag = true;
                    }
                }
            }
        }

        if (flag) {
            return new Vec3D((double) k, (double) l, (double) i1);
        } else {
            return null;
        }
    }
}
