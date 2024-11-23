package net.plate.littlethings;

import net.plate.littlethings.GradientDye.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.plate.littlethings.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.plate.littlethings.GradientDye.Dye.checkAndUpdateArmor;


public class LittleThings implements ModInitializer {
	public static final String MOD_ID = "littlethings";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final int TICK_INTERVAL = 3;
	private static int tickCounter = 0;

	public static int colorIndex = 0;

	@Override
	public void onInitialize() {
		Dye CRIMSON = new Dye(75,0,0,229,0,52);
		ModItems.registerModItems();

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			tickCounter++;
			if (tickCounter >= TICK_INTERVAL) {
				tickCounter = 0;
				colorIndex = (colorIndex + 1) % 15;

				for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
					checkAndUpdateArmor(player, CRIMSON);
					//System.out.println("checked");
				}
			}
		});

		LOGGER.info("Initializing " + MOD_ID);
	}




}