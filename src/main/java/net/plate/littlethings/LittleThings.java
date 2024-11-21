package net.plate.littlethings;

import net.fabricmc.api.ModInitializer;

import net.plate.littlethings.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LittleThings implements ModInitializer {
	public static final String MOD_ID = "littlethings";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
	}
}