package net.plate.littlethings.item;


import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.plate.littlethings.LittleThings;

import java.util.function.Function;

import static net.minecraft.item.Items.register;

public class ModItems {
    public static final Item CRIMSON_DYE = registerItem("crimson_dye", CrimsonDyeItem::new, new Item.Settings());

    public static Item registerItem(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("littlethings", path));
        return Items.register(registryKey, factory, settings);
    }
    public static void registerModItems(){
        LittleThings.LOGGER.info("Registering Items for " + LittleThings.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(CRIMSON_DYE);
        });
    }
}
