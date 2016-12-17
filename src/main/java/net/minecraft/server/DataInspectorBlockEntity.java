package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataInspectorBlockEntity implements DataInspector {

    private static final Logger a = LogManager.getLogger();
    private static final Map<String, String> b = Maps.newHashMap();

    public DataInspectorBlockEntity() {}

    @Nullable
    private static String a(String s) {
        return (String) DataInspectorBlockEntity.b.get((new MinecraftKey(s)).toString());
    }

    public NBTTagCompound a(DataConverter dataconverter, NBTTagCompound nbttagcompound, int i) {
        if (!nbttagcompound.hasKeyOfType("tag", 10)) {
            return nbttagcompound;
        } else {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("tag");

            if (nbttagcompound1.hasKeyOfType("BlockEntityTag", 10)) {
                NBTTagCompound nbttagcompound2 = nbttagcompound1.getCompound("BlockEntityTag");
                String s = nbttagcompound.getString("id");
                String s1 = a(s);
                boolean flag;

                if (s1 == null) {
                    DataInspectorBlockEntity.a.warn("Unable to resolve BlockEntity for ItemInstance: {}", new Object[] { s});
                    flag = false;
                } else {
                    flag = !nbttagcompound2.hasKey("id");
                    nbttagcompound2.setString("id", s1);
                }

                dataconverter.a(DataConverterTypes.BLOCK_ENTITY, nbttagcompound2, i);
                if (flag) {
                    nbttagcompound2.remove("id");
                }
            }

            return nbttagcompound;
        }
    }

    static {
        Map map = DataInspectorBlockEntity.b;

        map.put("minecraft:furnace", "Furnace");
        map.put("minecraft:lit_furnace", "Furnace");
        map.put("minecraft:chest", "Chest");
        map.put("minecraft:trapped_chest", "Chest");
        map.put("minecraft:ender_chest", "EnderChest");
        map.put("minecraft:jukebox", "RecordPlayer");
        map.put("minecraft:dispenser", "Trap");
        map.put("minecraft:dropper", "Dropper");
        map.put("minecraft:sign", "Sign");
        map.put("minecraft:mob_spawner", "MobSpawner");
        map.put("minecraft:noteblock", "Music");
        map.put("minecraft:brewing_stand", "Cauldron");
        map.put("minecraft:enhanting_table", "EnchantTable");
        map.put("minecraft:command_block", "CommandBlock");
        map.put("minecraft:beacon", "Beacon");
        map.put("minecraft:skull", "Skull");
        map.put("minecraft:daylight_detector", "DLDetector");
        map.put("minecraft:hopper", "Hopper");
        map.put("minecraft:banner", "Banner");
        map.put("minecraft:flower_pot", "FlowerPot");
        map.put("minecraft:repeating_command_block", "CommandBlock");
        map.put("minecraft:chain_command_block", "CommandBlock");
        map.put("minecraft:standing_sign", "Sign");
        map.put("minecraft:wall_sign", "Sign");
        map.put("minecraft:piston_head", "Piston");
        map.put("minecraft:daylight_detector_inverted", "DLDetector");
        map.put("minecraft:unpowered_comparator", "Comparator");
        map.put("minecraft:powered_comparator", "Comparator");
        map.put("minecraft:wall_banner", "Banner");
        map.put("minecraft:standing_banner", "Banner");
        map.put("minecraft:structure_block", "Structure");
        map.put("minecraft:end_portal", "Airportal");
        map.put("minecraft:end_gateway", "EndGateway");
        map.put("minecraft:shield", "Shield");
    }
}
