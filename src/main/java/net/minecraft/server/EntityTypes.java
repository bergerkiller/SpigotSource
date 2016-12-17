package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityTypes {

    private static final Logger b = LogManager.getLogger();
    private static final Map<String, Class<? extends Entity>> c = Maps.newHashMap();
    private static final Map<Class<? extends Entity>, String> d = Maps.newHashMap();
    private static final Map<Integer, Class<? extends Entity>> e = Maps.newHashMap();
    private static final Map<Class<? extends Entity>, Integer> f = Maps.newHashMap();
    private static final Map<String, Integer> g = Maps.newHashMap();
    public static final Map<String, EntityTypes.MonsterEggInfo> eggInfo = Maps.newLinkedHashMap();

    private static void a(Class<? extends Entity> oclass, String s, int i) {
        if (EntityTypes.c.containsKey(s)) {
            throw new IllegalArgumentException("ID is already registered: " + s);
        } else if (EntityTypes.e.containsKey(Integer.valueOf(i))) {
            throw new IllegalArgumentException("ID is already registered: " + i);
        } else if (i == 0) {
            throw new IllegalArgumentException("Cannot register to reserved id: " + i);
        } else if (oclass == null) {
            throw new IllegalArgumentException("Cannot register null clazz for id: " + i);
        } else {
            EntityTypes.c.put(s, oclass);
            EntityTypes.d.put(oclass, s);
            EntityTypes.e.put(Integer.valueOf(i), oclass);
            EntityTypes.f.put(oclass, Integer.valueOf(i));
            EntityTypes.g.put(s, Integer.valueOf(i));
        }
    }

    private static void a(Class<? extends Entity> oclass, String s, int i, int j, int k) {
        a(oclass, s, i);
        EntityTypes.eggInfo.put(s, new EntityTypes.MonsterEggInfo(s, j, k));
    }

    @Nullable
    public static Entity createEntityByName(String s, World world) {
        Entity entity = null;

        try {
            Class oclass = (Class) EntityTypes.c.get(s);

            if (oclass != null) {
                entity = (Entity) oclass.getConstructor(new Class[] { World.class}).newInstance(new Object[] { world});
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return entity;
    }

    @Nullable
    public static Entity a(NBTTagCompound nbttagcompound, World world) {
        Entity entity = null;

        try {
            Class oclass = (Class) EntityTypes.c.get(nbttagcompound.getString("id"));

            if (oclass != null) {
                entity = (Entity) oclass.getConstructor(new Class[] { World.class}).newInstance(new Object[] { world});
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (entity != null) {
            entity.f(nbttagcompound);
        } else {
            EntityTypes.b.warn("Skipping Entity with id " + nbttagcompound.getString("id"));
        }

        return entity;
    }

    @Nullable
    public static Entity a(int i, World world) {
        Entity entity = null;

        try {
            Class oclass = a(i);

            if (oclass != null) {
                entity = (Entity) oclass.getConstructor(new Class[] { World.class}).newInstance(new Object[] { world});
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (entity == null) {
            EntityTypes.b.warn("Skipping Entity with id " + i);
        }

        return entity;
    }

    @Nullable
    public static Entity b(String s, World world) {
        return a(a(s), world);
    }

    public static int a(Entity entity) {
        Integer integer = (Integer) EntityTypes.f.get(entity.getClass());

        return integer == null ? 0 : integer.intValue();
    }

    @Nullable
    public static Class<? extends Entity> a(int i) {
        return (Class) EntityTypes.e.get(Integer.valueOf(i));
    }

    public static String b(Entity entity) {
        return getName(entity.getClass());
    }

    public static String getName(Class<? extends Entity> oclass) {
        return (String) EntityTypes.d.get(oclass);
    }

    public static int a(String s) {
        Integer integer = (Integer) EntityTypes.g.get(s);

        return integer == null ? 90 : integer.intValue();
    }

    public static void a() {}

    public static List<String> b() {
        Set set = EntityTypes.c.keySet();
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            Class oclass = (Class) EntityTypes.c.get(s);

            if ((oclass.getModifiers() & 1024) != 1024) {
                arraylist.add(s);
            }
        }

        arraylist.add("LightningBolt");
        return arraylist;
    }

    public static boolean a(Entity entity, String s) {
        String s1 = b(entity);

        if (s1 == null && entity instanceof EntityHuman) {
            s1 = "Player";
        } else if (s1 == null && entity instanceof EntityLightning) {
            s1 = "LightningBolt";
        }

        return s.equals(s1);
    }

    public static boolean b(String s) {
        return "Player".equals(s) || b().contains(s);
    }

    static {
        a(EntityItem.class, "Item", 1);
        a(EntityExperienceOrb.class, "XPOrb", 2);
        a(EntityAreaEffectCloud.class, "AreaEffectCloud", 3);
        a(EntityEgg.class, "ThrownEgg", 7);
        a(EntityLeash.class, "LeashKnot", 8);
        a(EntityPainting.class, "Painting", 9);
        a(EntityTippedArrow.class, "Arrow", 10);
        a(EntitySnowball.class, "Snowball", 11);
        a(EntityLargeFireball.class, "Fireball", 12);
        a(EntitySmallFireball.class, "SmallFireball", 13);
        a(EntityEnderPearl.class, "ThrownEnderpearl", 14);
        a(EntityEnderSignal.class, "EyeOfEnderSignal", 15);
        a(EntityPotion.class, "ThrownPotion", 16);
        a(EntityThrownExpBottle.class, "ThrownExpBottle", 17);
        a(EntityItemFrame.class, "ItemFrame", 18);
        a(EntityWitherSkull.class, "WitherSkull", 19);
        a(EntityTNTPrimed.class, "PrimedTnt", 20);
        a(EntityFallingBlock.class, "FallingSand", 21);
        a(EntityFireworks.class, "FireworksRocketEntity", 22);
        a(EntitySpectralArrow.class, "SpectralArrow", 24);
        a(EntityShulkerBullet.class, "ShulkerBullet", 25);
        a(EntityDragonFireball.class, "DragonFireball", 26);
        a(EntityArmorStand.class, "ArmorStand", 30);
        a(EntityBoat.class, "Boat", 41);
        a(EntityMinecartRideable.class, EntityMinecartAbstract.EnumMinecartType.RIDEABLE.b(), 42);
        a(EntityMinecartChest.class, EntityMinecartAbstract.EnumMinecartType.CHEST.b(), 43);
        a(EntityMinecartFurnace.class, EntityMinecartAbstract.EnumMinecartType.FURNACE.b(), 44);
        a(EntityMinecartTNT.class, EntityMinecartAbstract.EnumMinecartType.TNT.b(), 45);
        a(EntityMinecartHopper.class, EntityMinecartAbstract.EnumMinecartType.HOPPER.b(), 46);
        a(EntityMinecartMobSpawner.class, EntityMinecartAbstract.EnumMinecartType.SPAWNER.b(), 47);
        a(EntityMinecartCommandBlock.class, EntityMinecartAbstract.EnumMinecartType.COMMAND_BLOCK.b(), 40);
        a(EntityInsentient.class, "Mob", 48);
        a(EntityMonster.class, "Monster", 49);
        a(EntityCreeper.class, "Creeper", 50, 894731, 0);
        a(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
        a(EntitySpider.class, "Spider", 52, 3419431, 11013646);
        a(EntityGiantZombie.class, "Giant", 53);
        a(EntityZombie.class, "Zombie", 54, '\uafaf', 7969893);
        a(EntitySlime.class, "Slime", 55, 5349438, 8306542);
        a(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
        a(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
        a(EntityEnderman.class, "Enderman", 58, 1447446, 0);
        a(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
        a(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
        a(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
        a(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
        a(EntityEnderDragon.class, "EnderDragon", 63);
        a(EntityWither.class, "WitherBoss", 64);
        a(EntityBat.class, "Bat", 65, 4996656, 986895);
        a(EntityWitch.class, "Witch", 66, 3407872, 5349438);
        a(EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
        a(EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
        a(EntityShulker.class, "Shulker", 69, 9725844, 5060690);
        a(EntityPig.class, "Pig", 90, 15771042, 14377823);
        a(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
        a(EntityCow.class, "Cow", 92, 4470310, 10592673);
        a(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
        a(EntitySquid.class, "Squid", 94, 2243405, 7375001);
        a(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
        a(EntityMushroomCow.class, "MushroomCow", 96, 10489616, 12040119);
        a(EntitySnowman.class, "SnowMan", 97);
        a(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
        a(EntityIronGolem.class, "VillagerGolem", 99);
        a(EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
        a(EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
        a(EntityVillager.class, "Villager", 120, 5651507, 12422002);
        a(EntityEnderCrystal.class, "EnderCrystal", 200);
    }

    public static class MonsterEggInfo {

        public final String a;
        public final int b;
        public final int c;
        public final Statistic killEntityStatistic;
        public final Statistic killedByEntityStatistic;

        public MonsterEggInfo(String s, int i, int j) {
            this.a = s;
            this.b = i;
            this.c = j;
            this.killEntityStatistic = StatisticList.a(this);
            this.killedByEntityStatistic = StatisticList.b(this);
        }
    }
}
