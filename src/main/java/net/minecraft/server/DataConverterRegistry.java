package net.minecraft.server;

public class DataConverterRegistry {

    private static void a(DataConverterManager dataconvertermanager) {
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterEquipment()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.BLOCK_ENTITY, (IDataConverter) (new DataConverterSignText()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ITEM_INSTANCE, (IDataConverter) (new DataConverterMaterialId()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ITEM_INSTANCE, (IDataConverter) (new DataConverterPotionId()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ITEM_INSTANCE, (IDataConverter) (new DataConverterSpawnEgg()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterMinecart()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.BLOCK_ENTITY, (IDataConverter) (new DataConverterMobSpawner()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterUUID()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterHealth()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterSaddle()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterHanging()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterDropChances()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterRiding()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ENTITY, (IDataConverter) (new DataConverterArmorStand()));
        dataconvertermanager.a((DataConverterType) DataConverterTypes.ITEM_INSTANCE, (IDataConverter) (new DataConverterBook()));
    }

    public static DataConverterManager a() {
        DataConverterManager dataconvertermanager = new DataConverterManager(184);

        dataconvertermanager.a(DataConverterTypes.LEVEL, new DataInspector() {
            public NBTTagCompound a(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i) {
                if (nbttagcompound.hasKeyOfType("Player", 10)) {
                    nbttagcompound.set("Player", dataconverter.a(DataConverterTypes.PLAYER, nbttagcompound.getCompound("Player"), i));
                }

                return nbttagcompound;
            }
        });
        dataconvertermanager.a(DataConverterTypes.PLAYER, new DataInspector() {
            public NBTTagCompound a(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i) {
                DataConverterRegistry.b(dataconverter, nbttagcompound, i, "Inventory");
                DataConverterRegistry.b(dataconverter, nbttagcompound, i, "EnderItems");
                return nbttagcompound;
            }
        });
        dataconvertermanager.a(DataConverterTypes.CHUNK, new DataInspector() {
            public NBTTagCompound a(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i) {
                if (nbttagcompound.hasKeyOfType("Level", 10)) {
                    NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Level");
                    NBTTagList nbttaglist;
                    int j;

                    if (nbttagcompound1.hasKeyOfType("Entities", 9)) {
                        nbttaglist = nbttagcompound1.getList("Entities", 10);

                        for (j = 0; j < nbttaglist.size(); ++j) {
                            nbttaglist.a(j, dataconverter.a(DataConverterTypes.ENTITY, (NBTTagCompound) nbttaglist.h(j), i));
                        }
                    }

                    if (nbttagcompound1.hasKeyOfType("TileEntities", 9)) {
                        nbttaglist = nbttagcompound1.getList("TileEntities", 10);

                        for (j = 0; j < nbttaglist.size(); ++j) {
                            nbttaglist.a(j, dataconverter.a(DataConverterTypes.BLOCK_ENTITY, (NBTTagCompound) nbttaglist.h(j), i));
                        }
                    }
                }

                return nbttagcompound;
            }
        });
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItem("Item", new String[] { "Item"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItem("ThrownPotion", new String[] { "Potion"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItem("ItemFrame", new String[] { "Item"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItem("FireworksRocketEntity", new String[] { "FireworksItem"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItem("TippedArrow", new String[] { "Item"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("MinecartChest", new String[] { "Items"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("MinecartHopper", new String[] { "Items"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Enderman", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("ArmorStand", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Bat", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Blaze", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("CaveSpider", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Chicken", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Cow", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Creeper", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("EnderDragon", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Endermite", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Ghast", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Giant", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Guardian", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("LavaSlime", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Mob", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Monster", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("MushroomCow", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Ozelot", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Pig", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("PigZombie", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Rabbit", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Sheep", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Shulker", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Silverfish", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Skeleton", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Slime", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("SnowMan", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Spider", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Squid", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("VillagerGolem", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Witch", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("WitherBoss", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Wolf", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Zombie", new String[] { "ArmorItems", "HandItems"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("EntityHorse", new String[] { "ArmorItems", "HandItems", "Items"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItem("EntityHorse", new String[] { "ArmorItem", "SaddleItem"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, (DataInspector) (new DataInspectorItemList("Villager", new String[] { "ArmorItems", "HandItems", "Inventory"})));
        dataconvertermanager.a(DataConverterTypes.ENTITY, new DataInspector() {
            public NBTTagCompound a(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i) {
                if ("Villager".equals(nbttagcompound.getString("id")) && nbttagcompound.hasKeyOfType("Offers", 10)) {
                    NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Offers");

                    if (nbttagcompound1.hasKeyOfType("Recipes", 9)) {
                        NBTTagList nbttaglist = nbttagcompound1.getList("Recipes", 10);

                        for (int j = 0; j < nbttaglist.size(); ++j) {
                            NBTTagCompound nbttagcompound2 = nbttaglist.get(j);

                            DataConverterRegistry.a(dataconverter, nbttagcompound2, i, "buy");
                            DataConverterRegistry.a(dataconverter, nbttagcompound2, i, "buyB");
                            DataConverterRegistry.a(dataconverter, nbttagcompound2, i, "sell");
                            nbttaglist.a(j, nbttagcompound2);
                        }
                    }
                }

                return nbttagcompound;
            }
        });
        dataconvertermanager.a(DataConverterTypes.ENTITY, new DataInspector() {
            public NBTTagCompound a(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i) {
                if ("MinecartSpawner".equals(nbttagcompound.getString("id"))) {
                    nbttagcompound.setString("id", "MobSpawner");
                    dataconverter.a(DataConverterTypes.BLOCK_ENTITY, nbttagcompound, i);
                    nbttagcompound.setString("id", "MinecartSpawner");
                }

                return nbttagcompound;
            }
        });
        dataconvertermanager.a(DataConverterTypes.ENTITY, new DataInspector() {
            public NBTTagCompound a(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i) {
                if ("MinecartCommandBlock".equals(nbttagcompound.getString("id"))) {
                    nbttagcompound.setString("id", "Control");
                    dataconverter.a(DataConverterTypes.BLOCK_ENTITY, nbttagcompound, i);
                    nbttagcompound.setString("id", "MinecartCommandBlock");
                }

                return nbttagcompound;
            }
        });
        dataconvertermanager.a(DataConverterTypes.BLOCK_ENTITY, (DataInspector) (new DataInspectorItemList("Furnace", new String[] { "Items"})));
        dataconvertermanager.a(DataConverterTypes.BLOCK_ENTITY, (DataInspector) (new DataInspectorItemList("Chest", new String[] { "Items"})));
        dataconvertermanager.a(DataConverterTypes.BLOCK_ENTITY, (DataInspector) (new DataInspectorItemList("Trap", new String[] { "Items"})));
        dataconvertermanager.a(DataConverterTypes.BLOCK_ENTITY, (DataInspector) (new DataInspectorItemList("Dropper", new String[] { "Items"})));
        dataconvertermanager.a(DataConverterTypes.BLOCK_ENTITY, (DataInspector) (new DataInspectorItemList("Cauldron", new String[] { "Items"})));
        dataconvertermanager.a(DataConverterTypes.BLOCK_ENTITY, (DataInspector) (new DataInspectorItemList("Hopper", new String[] { "Items"})));
        dataconvertermanager.a(DataConverterTypes.BLOCK_ENTITY, (DataInspector) (new DataInspectorItem("RecordPlayer", new String[] { "RecordItem"})));
        dataconvertermanager.a(DataConverterTypes.BLOCK_ENTITY, new DataInspector() {
            public NBTTagCompound a(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i) {
                if ("MobSpawner".equals(nbttagcompound.getString("id"))) {
                    if (nbttagcompound.hasKeyOfType("SpawnPotentials", 9)) {
                        NBTTagList nbttaglist = nbttagcompound.getList("SpawnPotentials", 10);

                        for (int j = 0; j < nbttaglist.size(); ++j) {
                            NBTTagCompound nbttagcompound1 = nbttaglist.get(j);

                            nbttagcompound1.set("Entity", dataconverter.a(DataConverterTypes.ENTITY, nbttagcompound1.getCompound("Entity"), i));
                        }
                    }

                    nbttagcompound.set("SpawnData", dataconverter.a(DataConverterTypes.ENTITY, nbttagcompound.getCompound("SpawnData"), i));
                }

                return nbttagcompound;
            }
        });
        dataconvertermanager.a(DataConverterTypes.ITEM_INSTANCE, (DataInspector) (new DataInspectorBlockEntity()));
        dataconvertermanager.a(DataConverterTypes.ITEM_INSTANCE, (DataInspector) (new DataInspectorEntity()));
        a(dataconvertermanager);
        return dataconvertermanager;
    }

    public static NBTTagCompound a(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i, String s) {
        if (nbttagcompound.hasKeyOfType(s, 10)) {
            nbttagcompound.set(s, dataconverter.a(DataConverterTypes.ITEM_INSTANCE, nbttagcompound.getCompound(s), i));
        }

        return nbttagcompound;
    }

    public static NBTTagCompound b(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i, String s) {
        if (nbttagcompound.hasKeyOfType(s, 9)) {
            NBTTagList nbttaglist = nbttagcompound.getList(s, 10);

            for (int j = 0; j < nbttaglist.size(); ++j) {
                nbttaglist.a(j, dataconverter.a(DataConverterTypes.ITEM_INSTANCE, nbttaglist.get(j), i));
            }
        }

        return nbttagcompound;
    }
}
